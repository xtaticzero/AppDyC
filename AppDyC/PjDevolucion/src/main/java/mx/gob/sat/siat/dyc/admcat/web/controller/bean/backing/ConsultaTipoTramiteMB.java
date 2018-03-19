package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.ConsultaTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;

import org.apache.log4j.Logger;

import org.primefaces.component.column.Column;
import org.primefaces.component.outputlabel.OutputLabel;


@ManagedBean(name = "consultaTipoTramite")
@ViewScoped
public class ConsultaTipoTramiteMB {
    
    private String titulo;
    private Integer idTipoTramite;
    private ConsultaTipoTramiteVO datosDelTramite;
    
    private static final String GUION_ESPACIO  = " - ";
    private static final String SALTO_DE_LINEA = "<br/>";
    
    private static final Logger LOGGER = Logger.getLogger(ConsultaTipoTramiteMB.class);
    
    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    public ConsultaTipoTramiteMB() {
        super();
        titulo = "Consulta de Trámites";
    }
    
    @PostConstruct
    public void init() {
        UtilsValidaSesion.validarSesion(serviceObtenerSesion.execute(), Procesos.DYC00014);
        try {
            obtenerIdTramite();
            datosDelTramite = catalogoTipoTramiteService.consultarDatosDeUnTramite(idTipoTramite);
            llenarConEtiquetasTipoTramite("panelInfoARequerir");
            llenarConEtiquetasTipoUnidadesAdministrativas("columnaUnidadesAdmvasTipo");
            llenarConEtiquetasRoles("columnaRoles");
            llenarConEtiquetasAnexos("columnaAnexos");
            llenarConEtiquetasSubtramites("columnaSubtramites");
            llenarConEtiquetasSubOrigenSaldo("columnaSubOrigenSaldo");
            llenarConEtiquetasTipoPeriodo("columnaTipoPeriodo");
            
        } catch (SIATException siate) {
            LOGGER.info("Hubo un errror al obtener los datos: "+siate);
        }
    }
    
    /**
     * Obtiene el tipo de tramite que se ha mandado como parametro de la pantalla de tipo de tramite.
     */
    private void obtenerIdTramite() {
        idTipoTramite = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTramite"));
    }
    
    /**
     * Regresa a la pagina de catalogosTipoTramite
     *
     * @return
     */
    public String redireccionar() {
        return "catalogoTipoTramite";
    }
    
    /**
     * Llenar con etiquetas de texto el componente que se le pide.
     *
     * @param componenteALenar
     */
    private void llenarConEtiquetasTipoTramite(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        DyccInfoTramiteDTO objetoInfoTramite = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getGuardadoTipoTramiteVO().getInfoTramite().iterator();
        
        while (it.hasNext()) {
            objetoInfoTramite = (DyccInfoTramiteDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoInfoTramite.getDyccInfoARequerirDTO().getDescripcion());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }
    
    /**
     *
     * @param componenteALenar
     */
    private void llenarConEtiquetasTipoUnidadesAdministrativas(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        AdmcUnidAdmvaTipoDTO objetoUnidadAdmvaTipo = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getListaUnidadAdmvaTipo().iterator();
        
        while (it.hasNext()) {
            objetoUnidadAdmvaTipo = (AdmcUnidAdmvaTipoDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoUnidadAdmvaTipo.getDescripcion());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }
    
    /**
     * Consulta los roles con los cuales esta asociado el tipo de tramite.
     *
     * @param componenteALenar
     */
    private void llenarConEtiquetasRoles(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        DyccTramiteRolDTO objetoTramiteRol = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getGuardadoTipoTramiteVO().getTramiteRol().iterator();
        
        while (it.hasNext()) {
            objetoTramiteRol = (DyccTramiteRolDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoTramiteRol.getDyccRolDTO().getDescripcion());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }
    
    /**
     * Convierte los datos de los anexos a etiquetas de texto.
     *
     * @param componenteALenar
     */
    private void llenarConEtiquetasAnexos(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        DyccAnexoTramiteDTO objetoAnexo = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getGuardadoTipoTramiteVO().getAnexoTramite().iterator();
        
        while (it.hasNext()) {
            objetoAnexo = (DyccAnexoTramiteDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoAnexo.getDyccMatrizAnexosDTO().getDescripcionAnexo());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }
    
    /**
     * Convierte los datos de los Subtramites a etiquetas de texto.
     * 
     * @param componenteALenar
     */
    private void llenarConEtiquetasSubtramites(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        DyccTtSubtramiteDTO objetoSubtramite = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getGuardadoTipoTramiteVO().getTtSubtramite().iterator();
        
        while (it.hasNext()) {
            objetoSubtramite = (DyccTtSubtramiteDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoSubtramite.getDyccTipoTramiteDTO().getDescripcion());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }
    
    /**
     * Convierte los datos de los Suborigenes del saldo a etiquetas de texto.
     *
     * @param componenteALenar
     */
    private void llenarConEtiquetasSubOrigenSaldo(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        DyccSubSaldoTramDTO objetoSubOrigenSaldo = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getGuardadoTipoTramiteVO().getSubSaldoTram().iterator();
        
        while (it.hasNext()) {
            objetoSubOrigenSaldo = (DyccSubSaldoTramDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoSubOrigenSaldo.getDyccSuborigSaldoDTO().getDescripcion());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }
    
    /**
     * Convierte los datos los tipos de periodo a etiquetas de texto.
     *
     * @param componenteALenar
     */
    private void llenarConEtiquetasTipoPeriodo(String componenteALenar) {
        
        OutputLabel etiqueta     = null;
        HtmlOutputText linebreak = null;  
        DycaTipoPeriodoTtDTO objetoTipoPeriodo = null;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Column columna = (Column)rootView.findComponent(componenteALenar);
    
        Iterator it = datosDelTramite.getGuardadoTipoTramiteVO().getTipoPeriodoTt().iterator();
        
        while (it.hasNext()) {
            objetoTipoPeriodo = (DycaTipoPeriodoTtDTO)it.next();
            etiqueta = new OutputLabel();
            etiqueta.setValue(GUION_ESPACIO+objetoTipoPeriodo.getDyccTipoPeriodoDTO().getDescripcion());
            etiqueta.setFor(componenteALenar);
            
            linebreak = new HtmlOutputText();  
            linebreak.setValue(SALTO_DE_LINEA);  
            linebreak.setEscape(false);
            
            columna.getChildren().add(etiqueta);
            columna.getChildren().add(linebreak);
        }
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setCatalogoTipoTramiteService(CatalogoTipoTramiteService catalogoTipoTramiteService) {
        this.catalogoTipoTramiteService = catalogoTipoTramiteService;
    }

    public CatalogoTipoTramiteService getCatalogoTipoTramiteService() {
        return catalogoTipoTramiteService;
    }

    public void setDatosDelTramite(ConsultaTipoTramiteVO datosDelTramite) {
        this.datosDelTramite = datosDelTramite;
    }

    public ConsultaTipoTramiteVO getDatosDelTramite() {
        return datosDelTramite;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
