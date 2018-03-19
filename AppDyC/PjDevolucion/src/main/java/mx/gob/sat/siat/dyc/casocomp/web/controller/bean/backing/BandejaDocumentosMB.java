package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.siat.dyc.casocomp.service.BandejaDocumentosService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridDocumentosBean;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes.EstadosResolucion;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


@ManagedBean(name = "bandDocsCCMB")
@ViewScoped
public class BandejaDocumentosMB {
    private static final Logger LOG = Logger.getLogger(BandejaDocumentosMB.class);

    @ManagedProperty("#{serviceBandejaDocumentos}")
    private BandejaDocumentosService service;

    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceACC;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    private DocumentoDataModel documentos;
    private FilaGridDocumentosBean documentoSeleccionado;

    private List<ItemComboBean> superiores;
    private Integer idSuperior;

    /** private StreamedContent file; */

    public BandejaDocumentosMB() {
    }

    @PostConstruct
    public void cargar() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numEmpleado", mbSession.getNumEmpleado());
        this.setDocumentos(new DocumentoDataModel(service.obtenerDocumentos(params)));
        LOG.debug("mbSession.getAccesoUsr() ->" + mbSession.getAccesoUsr());
        superiores = serviceACC.obtenerSuperiores(mbSession.getClaveAdm(),Integer.parseInt(mbSession.getAccesoUsr().getCentroCosto()));
    }

    public void enviarAAprobacion() {
        LOG.debug("INICIO enviarAAprobacion || idSuperior ->" + idSuperior);
        LOG.debug("documentoSeleccionado.getEstadoDocumento() ->" + documentoSeleccionado.getEstadoDocumento() + "<-");
        if (EstadosResolucion.EN_APROBACION.getDescripcion().equals(documentoSeleccionado.getEstadoDocumento())) {
            JSFUtils.messageGlobal("El documento ya se encuentra en Aprobación", FacesMessage.SEVERITY_INFO);
            return;
        }
        try {
            service.enviarAAprobacion(documentoSeleccionado.getNumControlDoc(), idSuperior);
            JSFUtils.messageGlobal("El documento se envío a aprobación satisfactoriamente",
                                   FacesMessage.SEVERITY_INFO);
        } catch (SIATException se) {
            LOG.error("###\n\n" +
                    se.getMessage());
            JSFUtils.messageGlobal(se.getMessage(), FacesMessage.SEVERITY_INFO);
        }
    }

    public StreamedContent getFile() {
        StreamedContent file = null;
        if (documentoSeleccionado != null) {
            Map<String, Object> info;
            try {
                info = service.obtenerInfoDescargarDocumento(documentoSeleccionado.getNumControlDoc());
                byte[] contenido = null;
                try {
                    File fileTmp = new File((String)info.get("pathArchivo"));
                    if (LOG.isInfoEnabled()) {
                        LOG.info("pathArchivo ->" + fileTmp.getAbsoluteFile() + "<-");
                    }
                    contenido = IOUtils.toByteArray(fileTmp.toURI());
                    file =
new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(fileTmp),
                           documentoSeleccionado.getNombreDocumento());

                } catch (FileNotFoundException e) {
                    LOG.error("\n\n:::::::::::::::::::\n ERROR AL DESCARGAR ARCHIVO ->" +
                              (String)info.get("pathArchivo") + "<- para el documento ->" +
                              documentoSeleccionado.getNumControlDoc() + "\n\n:::::::::::::::::::\n\n" +
                            e.getMessage());
                } catch (IOException ex) {
                    LOG.error("\n\n:::::::::::::::::::\n ERROR AL DESCARGAR ARCHIVO ->" +
                              (String)info.get("pathArchivo") + "<- para el documento ->" +
                              documentoSeleccionado.getNumControlDoc() + "\n\n:::::::::::::::::::\n\n" +
                            ex.getMessage());
                }
            } catch (SIATException se) {
                LOG.error("###\n\n" +
                        se.getMessage());
                JSFUtils.messageGlobal(se.getMessage(), FacesMessage.SEVERITY_INFO);
            }
        }
        return file;
    }

    public DocumentoDataModel getDocumentos() {
        return documentos;
    }

    public void setDocumentos(DocumentoDataModel documentos) {
        this.documentos = documentos;
    }

    public FilaGridDocumentosBean getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(FilaGridDocumentosBean documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public BandejaDocumentosService getService() {
        return service;
    }

    public void setService(BandejaDocumentosService service) {
        this.service = service;
    }

    public List<ItemComboBean> getSuperiores() {
        return superiores;
    }

    public void setSuperiores(List<ItemComboBean> superiores) {
        this.superiores = superiores;
    }

    public Integer getIdSuperior() {
        return idSuperior;
    }

    public void setIdSuperior(Integer idSuperior) {
        this.idSuperior = idSuperior;
    }

    public IAdmCasosCompensacionService getServiceACC() {
        return serviceACC;
    }

    public void setServiceACC(IAdmCasosCompensacionService serviceACC) {
        this.serviceACC = serviceACC;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }
}
