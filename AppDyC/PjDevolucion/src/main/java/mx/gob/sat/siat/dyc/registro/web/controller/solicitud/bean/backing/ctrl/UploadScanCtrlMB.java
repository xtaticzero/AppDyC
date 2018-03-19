/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.ctrl;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;


import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.AdicionarSolicitudMB;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCaracteres;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.primefaces.model.UploadedFile;


/**
 * @author JEFG
 * */
@ManagedBean(name = "uploadScanCtrlMB")
@RequestScoped
public class UploadScanCtrlMB {

    private static final Logger LOGGER = Logger.getLogger(UploadScanCtrlMB.class);


    /** PageMB  */
    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB solDevPage;

    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;

    private ArchivoCargaDescarga cargaArchivo;
    private String fileName;

    private Mensaje mensaje;

    @PostConstruct
    void init() {
        fileName = "";
        cargaArchivo = new ArchivoCargaDescarga();
        mensaje = new Mensaje();
    }

    /** metodo para subir los anexos de la solicitud  */
    public void fileUploadAnexos() throws SIATException {
        UploadedFile file = solDevPage.getFileAnexo();
        if (null == file) {
            solDevPage.setAnexo(null);
            solDevPage.getFlagsSolicitud().setShowUploadFile(Boolean.TRUE);
            JSFUtils.messageGlobal(ConstantesArchivo.ARCHIVO_VALIDO, FacesMessage.SEVERITY_WARN);
            return;
        }
        ArchivoVO select = solDevPage.getAnexo();
        if (!isAnexoValido(contentType(file.getFileName()), select.getId())) {
            fileName = getNombreDocumento(ValidaDatosSolicitud.validaCaracteres(file.getFileName()));
            if (fileName.length() > ConstantesDyC.MAX_LENGTH) {
                JSFUtils.messageGlobal(ValidaDatosSolicitud.MENSAJE_ARCHIVO, FacesMessage.SEVERITY_ERROR);
            } else {
                try {
                    guardarAnexo(select.getId(), fileName, file.getInputstream());
                } catch (IOException e) {
                    throw new SIATException("ERROR AL ESCRIBIR ARCHIVO EN DISCO", e);
                }
                solDevPage.setAnexo(null);
                solDevPage.getFlagsSolicitud().setShowUploadFile(Boolean.TRUE);
                showMessages(ConstantesDyCNumerico.VALOR_35, FacesMessage.SEVERITY_INFO);
            }
        }
    }

    public void guardarAnexo(int id, String nomCorrecto, InputStream file) throws SIATException {
        int i = 0;
        for (ArchivoVO objeto : solDevPage.getTramite().getAnexos()) {
            if (objeto.getId().equals(id)) {
                objeto.setEstado(ConstantesArchivo.EDO_ANEXO_ADJUNTADO);
                objeto.setUrl(solDevPage.getCarpetaTemp().toString());
                objeto.setDescripcion(objeto.getNombre());
                objeto.setNombreArchivo(nomCorrecto);
                cargaArchivo.escribirArchivo(nomCorrecto, file,
                                             solDevPage.getCarpetaTemp().toString().concat(ConstantesDyCURL.TIPO_ARCHIVO_ANEXOS),
                                             ConstantesDyCNumerico.VALOR_4096);
                solDevPage.getTramite().getAnexos().set(i, objeto);
            }
            i++;
        }
    }

    /**metodod para adjuntar los documentos .ZIP  */
    public void fileUploadDocumentos() throws SIATException, UnsupportedEncodingException {
         UploadedFile file = solDevPage.getFileDoc();
         
        if (validaArchivo(file, solDevPage.getFlagsSolicitud().getNombreDocumento())) 
        {
           fileName = getNombreDocumento(ValidaDatosSolicitud.validaCaracteres(file.getFileName()));
            
            for(ArchivoVO object : solDevPage.getDocumentosAdjuntos())
            {
                 if(fileName.equals(object.getNombreArchivo()))
                {
                
                    mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS, "El documento ya existe.");
                    return ;
                }
            }
           
           ArchivoVO documento = new ArchivoVO();
            String campo = (new String(solDevPage.getFlagsSolicitud().getNombreDocumento().getBytes(ConstantesDyC.CODIFICACION_ISO),ConstantesDyC.CODIFICACION_UTF8).toUpperCase());
            documento.setDescripcion(campo);
            
           
            documento.setNombreArchivo(fileName);
            documento.setUrl(solDevPage.getCarpetaTemp().toString());
            documento.setId(0);

            try {
                cargaArchivo.escribirArchivo(fileName, file.getInputstream(),
                                             solDevPage.getCarpetaTemp().toString().concat(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS),
                                             ConstantesDyCNumerico.VALOR_4096);
            } catch (IOException e) {
                LOGGER.error(e);
                throw new SIATException("ERROR AL ESCRIBIR ARCHIVOI EN DISCO", e);
            }
            solDevPage.getTramite().getDocumentos().add(documento);
            solDevPage.getDocumentosAdjuntos().add(documento);
            solDevPage.getFlagsSolicitud().setNombreDocumento(ConstantesDyC.NULL);
            showMessages(ConstantesDyCNumerico.VALOR_35, FacesMessage.SEVERITY_INFO);
        }
    }

    /** metodo para adjuntar el edo cta de la solicitud de devolucion  */
    public void fileUploadEdoCta() throws SIATException, UnsupportedEncodingException  {
        if (clearEdoCta()) {
            try {
                ValidaDatosSolicitud.deleteDocumentos(solDevPage.getDocEdoCta().getUrl().concat(solDevPage.getDocEdoCta().getNombreArchivo()));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        UploadedFile file = solDevPage.getFileEdoCta();
        if (validaDocEdoCta(file)) {
            fileName = getNombreDocumento(ValidaDatosSolicitud.validaCaracteres(file.getFileName()));
            
            ArchivoVO documento = new ArchivoVO();
            documento.setDescripcion("Cuenta CLABE");
            documento.setNombreArchivo(fileName);
            DyctArchivoDTO docEdoCta = new DyctArchivoDTO();
            docEdoCta.setUrl(solDevPage.getCarpetaTemp().toString());
            docEdoCta.setIdArchivo(ConstantesArchivo.TIPO_ARCHIVO_CLABE);
            docEdoCta.setDescripcion("Cuenta CLABE");
            docEdoCta.setNombreArchivo(fileName);
            solDevPage.getTramite().getInstitucionFinanciera().setDyctArchivoDTO(docEdoCta);
            documento.setUrl(solDevPage.getCarpetaTemp().toString());

            try {
                cargaArchivo.escribirArchivo(fileName, file.getInputstream(),
                                             solDevPage.getCarpetaTemp().toString().concat(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS),
                                             ConstantesDyCNumerico.VALOR_4096);
            } catch (Exception e) {
                LOGGER.error("ERROR AL ESCRIBIR EN DISCO::::: " + e.getMessage());
                solDevPage.getTramite().getInstitucionFinanciera().setDyctArchivoDTO(null);
                JSFUtils.messageGlobal("ERROR AL ESCRIBIR EN DISCO", FacesMessage.SEVERITY_ERROR);
                return;
            }
            solDevPage.getDocumentosAdjuntos().add(documento);
            solDevPage.setDocEdoCta(documento);
            solDevPage.getFlagsSolicitud().setFlgEdoCta(Boolean.TRUE);
            /** solicitudUtils.setNomDocEdoCta(ConstantesDyC.NULL); */
            if(StringUtils.isBlank(solDevPage.getFlagsSolicitud().getMessageProtestaEdoCta())){
                 solDevPage.getFlagsSolicitud().setMessageProtestaEdoCta(getMessage(ConstantesDyCNumerico.VALOR_53));
            }
           
        }
    }

    private boolean validaDocEdoCta(UploadedFile file) {
        if (null == file) {
            showMessages(ConstantesDyCNumerico.VALOR_25, FacesMessage.SEVERITY_ERROR);
            solDevPage.getTramite().setInstitucionFinanciera(null);
            solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.TRUE);
            return false;
        }
        String contentType = file.getFileName();
        try {
            contentType = contentType.substring(contentType.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
        } catch (StringIndexOutOfBoundsException e) {
            LOGGER.error(e.getMessage());
            showMessages(ConstantesDyCNumerico.VALOR_25, FacesMessage.SEVERITY_ERROR);
            solDevPage.getTramite().setInstitucionFinanciera(null);
            solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.TRUE);
            return false;
        }

        if (!contentType.equals(ConstantesArchivo.ZIP)) {
            showMessages(ConstantesDyCNumerico.VALOR_36, FacesMessage.SEVERITY_ERROR);
            solDevPage.getFlagsSolicitud().setNombreDocumento(ConstantesDyC.NULL);
            solDevPage.setFileDoc(null);
            solDevPage.getTramite().setInstitucionFinanciera(null);
            solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.TRUE);
            return false;
        }
        return Boolean.TRUE;
    }

    private String getNombreDocumento(String string) {

        return string.substring(string.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, string.length());
    }

    private boolean clearEdoCta() {
        if (null != solDevPage.getDocEdoCta()) {
            solDevPage.getDocumentosAdjuntos().remove(solDevPage.getDocEdoCta());
            return Boolean.TRUE;
        }
        return false;
    }

    /**eliminar documento en disco */
    public void doDeleteDocumento() {
        ArchivoVO doc = solDevPage.getDocumento();
        if (null == doc) {
            JSFUtils.messageGlobal(ValidaDatosSolicitud.SELECT, FacesMessage.SEVERITY_INFO);
        } else {
            try {
                Runtime.getRuntime().exec(ValidaDatosSolicitud.DELETE +
                                          doc.getUrl().concat(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS).concat("/").concat(doc.getNombreArchivo()));
                solDevPage.getTramite().getDocumentos().remove(doc);
                solDevPage.getDocumentosAdjuntos().remove(doc);
                JSFUtils.messageGlobal(ValidaDatosSolicitud.DELETEDOC, FacesMessage.SEVERITY_INFO);
                solDevPage.setDocumento(null);
                LOGGER.info("Se elimino correctamente");
            } catch (IOException e) {
                LOGGER.info("No se pudo ejecutar el comando");
                JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    private boolean isAnexoValido(String contentType, int anexo) {
        String xlsx = ".xlsx", xls = ".xls", docx = ".docx";
        if (anexo == ConstantesDyCNumerico.VALOR_1 && !contentType.equals(docx)) {
            JSFUtils.messageGlobal("El anexo IVA del Sector Agropecuario debe ser tipo '.docx'",
                                   FacesMessage.SEVERITY_ERROR);
            solDevPage.setAnexo(null);
            solDevPage.getFlagsSolicitud().setShowUploadFile(Boolean.TRUE);
            return Boolean.TRUE;
        } else if (anexo != ConstantesDyCNumerico.VALOR_1 && !contentType.equals(xlsx) && !contentType.equals(xls)) {
            showMessages(ConstantesDyCNumerico.VALOR_39, FacesMessage.SEVERITY_ERROR);
            solDevPage.setAnexo(null);
            solDevPage.getFlagsSolicitud().setShowUploadFile(Boolean.TRUE);
            return Boolean.TRUE;
        }
        return false;
    }

    public void downloadPlantillaAnexo() {
        ArchivoVO select = solDevPage.getAnexo();
        if (null != select) {
            solDevPage.getFlagsSolicitud().setShowUploadFile(Boolean.FALSE);
            solDevPage.setFile(cargaArchivo.descargarArchivo(select.getUrlPlantillaAnexo()));            
        } else {
            JSFUtils.messageGlobal(ConstantesArchivo.SELECCIONE, FacesMessage.SEVERITY_INFO);
        }
    }


    private boolean validaArchivo(UploadedFile file, String nombreDoc) {
        boolean isArchivo = Boolean.TRUE;
        if (null == file) {
            showMessages(ConstantesDyCNumerico.VALOR_25, FacesMessage.SEVERITY_ERROR);
            solDevPage.getFlagsSolicitud().setNombreDocumento(ConstantesDyC.NULL);
            isArchivo = Boolean.FALSE;
        } else if (null == nombreDoc || nombreDoc.trim().equals(ConstantesCaracteres.CADENA_VACIA)) {
            JSFUtils.messageGlobal(ConstantesArchivo.NOMBRE_DOCUMENTO, FacesMessage.SEVERITY_ERROR);
            isArchivo = Boolean.FALSE;
        } else if (!contentType(file.getFileName()).equals(ConstantesArchivo.ZIP)) {
            showMessages(ConstantesDyCNumerico.VALOR_36, FacesMessage.SEVERITY_ERROR);
            solDevPage.getFlagsSolicitud().setNombreDocumento(ConstantesDyC.NULL);
            solDevPage.setFileDoc(null);
            isArchivo = Boolean.FALSE;
        }
        return isArchivo;
    }


    private String contentType(String type) {
        LOGGER.info(type);
        String strType = " ";
        try {
            strType = type.substring(type.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
        } catch (StringIndexOutOfBoundsException e) {
            LOGGER.error(e.getMessage());
        }
        return strType;
    }

    private String getMessage(int idMensaje) {
        return messageSolicitud.getMessage(idMensaje, ConstantesMensajes.CU_REGISTRO_SOLDEVLINEA);
    }

    private void showMessages(int idMensaje, Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }


    public void setSolDevPage(AdicionarSolicitudMB solDevPage) {
        this.solDevPage = solDevPage;
    }

    public AdicionarSolicitudMB getSolDevPage() {
        return solDevPage;
    }

    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setCargaArchivo(ArchivoCargaDescarga cargaArchivo) {
        this.cargaArchivo = cargaArchivo;
    }

    public ArchivoCargaDescarga getCargaArchivo() {
        return cargaArchivo;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
