package mx.gob.sat.siat.dyc.generico.web.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.primefaces.model.UploadedFile;


/**
 * @author FOGJ
 * */
@FacesValidator(value = "ArchivoValidator")
public class ArchivoValidator implements Validator {

    private static final Logger LOGGER = Logger.getLogger(ArchivoValidator.class);
    private String nameMessage;

    @Override
    public void validate(FacesContext facesContext, UIComponent component, Object object){
        String message = null;
        if (null != object) {
            UploadedFile archivo = (UploadedFile)object;
            String fileName = archivo.getFileName();
            fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
            if(fileName.length()<=ConstantesDyCNumerico.VALOR_80) {
                nameMessage = component.getClientId();
                HttpServletRequest request = (HttpServletRequest)JSFUtils.getExternalContext().getRequest();
                validarCabeceras(request);
            } else {
                FacesMessage mensaje = new FacesMessage(message, "");
                mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
                mensaje.setDetail("El nombre del archivo no debe tener mas de 80 caracteres.");
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                throw new ValidatorException(mensaje);
            }
        }
    }

    private void validarCabeceras(HttpServletRequest request){
        String message = null;
        if (request.getHeader("X-Content-Scanning") != null || request.getHeader("Virus_Detected") != null) {
            LOGGER.info("Entro a cabecera: X-Content-Scanning");
            message = "Se detectó virus en el archivo que intenta subir.";
        }
        if (request.getHeader("Max_Filesize_Reached") != null) {
            LOGGER.info("Entro a cabecera: Max_Filesize_Reached");
            message = "El tamaño del archivo es mayor a 4 MB.";
        }
        if (request.getHeader("Invalid_extension") != null) {
            LOGGER.info("Entro a cabecera: Invalid_extension");
            message = "El archivo no es de tipo válido.";
        }
        if (null != message) {
            FacesMessage mensaje = new FacesMessage(message, "");
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            String idMessage = nameMessage.equals("formCargarArchivo:fileCarga") ? "msgDocumentosAnexos" : null;
            FacesContext.getCurrentInstance().addMessage(idMessage, mensaje);
            throw new ValidatorException(mensaje);
        } else {
            LOGGER.info("***No se detecto ninguna cabecera, archivo valido***");
        }
    }
}
