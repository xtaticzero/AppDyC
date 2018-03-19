package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.support.DyccTipoTramiteVO;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;

import org.apache.log4j.Logger;


/**
 * Pantalla que se utiliza para administrar el catalogo de los tipos de tramite.
 *
 * @author Jesus Alfredo Hernandez Orozco
 * @version 0.1
 */
@ManagedBean(name = "catalogoTipoTramite")
@ViewScoped
public class CatalogoTipoTramiteMB extends AbstractPage {
    
    private String headerDialog;
    private boolean banderaBotones;
    private FacesMessage mensajePantalla;
    private DyccTipoTramiteVO tramiteSeleccionado;
    private List<DyccTipoTramiteVO> listaTiposTramite;
    private List<DyccTipoTramiteVO> listaFiltroTramite;
    private static final Integer ERROR_MODIFICACION   = 3;
    private static final Integer MOIFICACION_CORRECTA = 2;
    private static final Logger LOGGER = Logger.getLogger(CatalogoTipoTramiteMB.class);
    private static final String ERROR_CONSULTA = "Hubo un error al recoger los datos del tamite";
    private static final long ID_ORIGEN_SALDO = -1;
    
    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    public CatalogoTipoTramiteMB() {
        headerDialog="activar";
    }
    
    /**
     * Consulta todos los datos necesarios para mostrar los datos en pantalla
     */
    @PostConstruct
    public void init() {
        UtilsValidaSesion.validarSesion(serviceObtenerSesion.execute(), Procesos.DYC00014);
        try {
            obtenerIdTramite();
            banderaBotones = Boolean.TRUE;
            setDataModel(new SIATDataModel());
            listaTiposTramite = catalogoTipoTramiteService.obtieneTipoTramite(ID_ORIGEN_SALDO);
            
        } catch (SIATException siate) {
            LOGGER.error(ERROR_CONSULTA);
            mensajePantalla = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ERROR_CONSULTA);
        }
    }
    
    /**
     * Obtiene el tipo de tramite que se ha mandado como parametro de la pantalla de tipo de tramite.
     */
    private void obtenerIdTramite() {
        Integer resultado=null;
        if ((FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("resultado"))!=null) {
            resultado=Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("resultado"));
        }
        
        if (resultado!=null) {
            if (resultado==1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El trámite se agregó correctamente."));
                
            } else if (resultado.equals(MOIFICACION_CORRECTA)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El trámite se modificó correctamente."));
            
            } else if (resultado.equals(ERROR_MODIFICACION)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia!", 
                                                                                    "Existen datos dependientes al tipo de trámite que intenta modificar" +
                                                                                    ", no se realizó el cambio."));
            }
        }
    }
    
    /**
     * Cuando se selecciona un elemento de la lista, se activan los botones de editar, eliminar y modificar.
     */
    public void onRowSelect() {
        if (tramiteSeleccionado != null) {
            banderaBotones = false;
        }
    }
    
    /**
     * <html>
     * <body>
     * Redirecciona a la pantalla de alta de tipo de trámite.
     * </body>
     * </html>
     *
     * @return
     */
    public String addTipoTramiteListener() {
        return "altaTipoTramite";
    }
    
    /**
     * Manda a llamar la pantalla de modificacion del tipo de tramite.
     */
    public void modifyTipoTramiteLitener() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+
                                                                            "/faces/resources/pages/admcat/modificarTipoTramite.jsf?idTramite="
                                                                            +tramiteSeleccionado.getDyccTipoTramiteDTO().getIdTipoTramite());
            
        } catch (Exception e) {
            LOGGER.error("Hubo un error al tratar de modificar el tipo de trámite, intente mas tarde.");
        }
    }
    
    public void removeTipoTramiteLitener(ActionEvent e) {  
        try {
            if(tramiteSeleccionado.getDyccTipoTramiteDTO().getFechaFin()!=null) {
                catalogoTipoTramiteService.eliminarTramite(tramiteSeleccionado.getDyccTipoTramiteDTO().getIdTipoTramite(), 1);
            } else {
                catalogoTipoTramiteService.eliminarTramite(tramiteSeleccionado.getDyccTipoTramiteDTO().getIdTipoTramite(), 0);
            }
            
            listaTiposTramite = catalogoTipoTramiteService.obtieneTipoTramite(ID_ORIGEN_SALDO);
            setTramiteSeleccionado(null);
            
        } catch (SIATException siate) {
            LOGGER.error("Hubo un error al tratar de borrar el tipo de trámite, intente mas tarde.");
        }
    }
    
    /**
     * Consulta los datos del tipo de tramite.
     */
    public void selectTipoTramiteLitener() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+
                                                                            "/faces/resources/pages/admcat/consultaTipoTramite.jsf?idTramite="
                                                                            +tramiteSeleccionado.getDyccTipoTramiteDTO().getIdTipoTramite());
            
        } catch (Exception e) {
            LOGGER.error("Hubo un error al tratar de modificar el tipo de trámite, intente mas tarde.");
        }
    }

    public void setCatalogoTipoTramiteService(CatalogoTipoTramiteService catalogoTipoTramiteService) {
        this.catalogoTipoTramiteService = catalogoTipoTramiteService;
    }

    public CatalogoTipoTramiteService getCatalogoTipoTramiteService() {
        return catalogoTipoTramiteService;
    }

    public void setListaTiposTramite(List<DyccTipoTramiteVO> listaTiposTramite) {
        this.listaTiposTramite = listaTiposTramite;
    }

    public List<DyccTipoTramiteVO> getListaTiposTramite() {
        return listaTiposTramite;
    }

    public void setMensajePantalla (FacesMessage mensajePantalla) {
        this.mensajePantalla = mensajePantalla;
    }

    public FacesMessage getMensajePantalla() {
        return mensajePantalla;
    }

    public void setTramiteSeleccionado(DyccTipoTramiteVO tramiteSeleccionado) {
        if (tramiteSeleccionado!=null) {
            if(tramiteSeleccionado.getDyccTipoTramiteDTO().getFechaFin()!=null) {
                setHeaderDialog("activar");
            } else {
                setHeaderDialog("desactivar");
            }
        }
        this.tramiteSeleccionado = tramiteSeleccionado;
    }

    public DyccTipoTramiteVO getTramiteSeleccionado() {
        return tramiteSeleccionado;
    }

    public void setBanderaBotones(boolean banderaBotones) {
        this.banderaBotones = banderaBotones;
    }

    public boolean isBanderaBotones() {
        return banderaBotones;
    }

    public void setHeaderDialog(String headerDialog) {
        this.headerDialog = headerDialog;
    }

    public String getHeaderDialog() {
        return headerDialog;
    }

    public void setListaFiltroTramite(List<DyccTipoTramiteVO> listaFiltroTramite) {
        this.listaFiltroTramite = listaFiltroTramite;
    }

    public List<DyccTipoTramiteVO> getListaFiltroTramite() {
        return listaFiltroTramite;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
