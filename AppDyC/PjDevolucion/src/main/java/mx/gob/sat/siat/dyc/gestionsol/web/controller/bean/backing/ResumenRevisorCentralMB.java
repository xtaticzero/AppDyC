package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.sql.Date;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ResumenDevolucionService;
import mx.gob.sat.siat.dyc.gestionsol.service.revision.RevisionCentralService;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.ResumenRevisionVO;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.consultarexpediente.DyCConsultarExpedienteMB;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@ManagedBean(name = "resumenRevisorCentralMB")
@SessionScoped
public class ResumenRevisorCentralMB {
    
    private Integer statusDocumento;
    private Integer p;
    
    private String comentarios;
    private String numControlDoc;
    private AccesoUsr accesoUsr;
    private ResumenRevisionVO resumenRevisionVO;
    private StreamedContent filePapeles;
    private static final Logger LOGGER = Logger.getLogger(ResumenRevisorCentralMB.class);
    
    @ManagedProperty(value = "#{dyCConsultarExpedienteMB}")
    private DyCConsultarExpedienteMB dyCConsultarExpedienteMB;
    
    @ManagedProperty(value = "#{revisionCentralService}")
    private RevisionCentralService revisionCentralService;
    
    @ManagedProperty("#{resumenDevolucionSer}")
    private ResumenDevolucionService resumenDevolucionSer;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    /**
     * Obtiene de base de datos toda la información necesaria para mostrar en pantalla.
     */
    @PostConstruct
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        try {
            resumenRevisionVO=revisionCentralService.consultarResumen(numControlDoc);
        } catch (SIATException e) {
            try{
                resumenRevisionVO=revisionCentralService.consultarResumenCont(numControlDoc);
            }catch (SIATException ex){
                LOGGER.error("Hubo un error al obtener la información.");
            }
            LOGGER.error("No se obtuvo compensación, ni devolución.");
        }
    }
    
    /**
     * Consulta los datos del expediente.
     *
     * @return
     */
    public String consultarExpediente() {        
        dyCConsultarExpedienteMB.setNumControl(resumenRevisionVO.getNumControl());
        dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_5);
        dyCConsultarExpedienteMB.setClaveAdm(Integer.valueOf(accesoUsr.getClaveSir()));
        dyCConsultarExpedienteMB.setRfcContribuyente(resumenRevisionVO.getRfcContribuyente());
        dyCConsultarExpedienteMB.setImporteSolicitado(resumenRevisionVO.getImporteSolicitado());
        dyCConsultarExpedienteMB.init();        
        return "consultarExpedienteRevisor";
    }
    
    /**
     * Descarga el documento de que se ha creado por parte del dictaminador.
     */
    public void descargarDocumento() {
        byte[] contenido = null;
        File tempFile    = null;
        DyctDocumentoDTO objetoDocumento = null;
        StringBuilder ruta = new StringBuilder();
        try {
            objetoDocumento = resumenDevolucionSer.obtenerRutaDocumento(numControlDoc);
            ruta.append(objetoDocumento.getUrl().concat("/"));
            ruta.append(objetoDocumento.getNombreArchivo());
            tempFile = new File(ruta.toString());
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (Exception e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }
        filePapeles =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           objetoDocumento.getNombreArchivo());
    }
    
    /**
     * Aprueba el documento y procede a actualizar el estatus del documento y a guardar los datos del seguimiento.
     */
    private void aprobar() {
        FacesMessage facesMessage = new FacesMessage("");
        
        try {
            revisionCentralService.actualizarEstatusDeDocumento(ConstantesDyCNumerico.VALOR_10, numControlDoc, guardarDatosDelSeguimiento(1));
            
        } catch (SIATException e) {
            facesMessage.setDetail("Hubo un error al aprobar el documento.");
            facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
            LOGGER.error("ERROR: " + e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    private void rechazar() {
        FacesMessage facesMessage = new FacesMessage("");
        
        try {
            revisionCentralService.actualizarEstatusDeDocumento(ConstantesDyCNumerico.VALOR_11, numControlDoc, guardarDatosDelSeguimiento(ConstantesDyCNumerico.VALOR_5));
            
        } catch (SIATException e) {
            facesMessage.setDetail("Hubo un error al rechazar el documento.");
            facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
            LOGGER.error("ERROR: " + e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    /**
     * Aprueba el documento y procede a rechazar el estatus del documento y a guardar los datos del seguimiento.
     */
    public void guardar() {
        int resultado = 0;
        if (statusDocumento.equals(ConstantesDyCNumerico.VALOR_10)) {
            aprobar();
            resultado = 1;
        } else {
            rechazar();
            resultado = ConstantesDyCNumerico.VALOR_2;
        }
        redireccionar(resultado);
    }
    
    /**
     * Guarda los datos por los cuales el revisor central ha aprobado o rechazado la solicitud.
     *
     * @return
     */
    private SeguimientoDTO guardarDatosDelSeguimiento(Integer idAccion) {
        SeguimientoDTO objeto = new SeguimientoDTO();
        objeto.setIdAccionSeg(idAccion);
        objeto.setNumControlDoc(numControlDoc);
        objeto.setResponsable(accesoUsr.getNombreCompleto());
        objeto.setFecha(new Date(System.currentTimeMillis()));
        objeto.setComentarios(comentarios);
        return objeto;
    }
    
    /**
     * Regresa a la pagina de catalogosTipoTramite
     *
     * @param resultado si envia el valor de 1, quiere decir que el resultado fué exitoso. 3 en caso contrario.
     * @return catalogoTipoTramite.jsf
     */
    public void redireccionar(int resultado) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+
                                                                            "/faces/resources/pages/gestionsol/bandejaRevisorCentral.jsf?resultado="+
                                                                            resultado+"&p="+p);
        } catch (Exception e) {
            LOGGER.error("Hubo un error al tratar de modificar el tipo de trámite, intente mas tarde.");
        }
    }
    
    public void regresarABandeja() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+
                                                                            "/faces/resources/pages/gestionsol/bandejaRevisorCentral.jsf?p="+p);
        } catch (Exception e) {
            LOGGER.error("Hubo un error al tratar de modificar el tipo de trámite, intente mas tarde.");
        }
    }
    
    public void asignarAprobacipon(ActionEvent e) {
        statusDocumento = ConstantesDyCNumerico.VALOR_10;
    }
    
    public void asignarRechazo(ActionEvent e) {
        statusDocumento = ConstantesDyCNumerico.VALOR_11;
    }

    public void setRevisionCentralService(RevisionCentralService revisionCentralService) {
        this.revisionCentralService = revisionCentralService;
    }

    public RevisionCentralService getRevisionCentralService() {
        return revisionCentralService;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setResumenRevisionVO(ResumenRevisionVO resumenRevisionVO) {
        this.resumenRevisionVO = resumenRevisionVO;
    }

    public ResumenRevisionVO getResumenRevisionVO() {
        return resumenRevisionVO;
    }

    public void setDyCConsultarExpedienteMB(DyCConsultarExpedienteMB dyCConsultarExpedienteMB) {
        this.dyCConsultarExpedienteMB = dyCConsultarExpedienteMB;
    }

    public DyCConsultarExpedienteMB getDyCConsultarExpedienteMB() {
        return dyCConsultarExpedienteMB;
    }

    public void setResumenDevolucionSer(ResumenDevolucionService resumenDevolucionSer) {
        this.resumenDevolucionSer = resumenDevolucionSer;
    }

    public ResumenDevolucionService getResumenDevolucionSer() {
        return resumenDevolucionSer;
    }

    public void setFilePapeles(StreamedContent filePapeles) {
        this.filePapeles = filePapeles;
    }

    public StreamedContent getFilePapeles() {
        return filePapeles;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getP() {
        return p;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
