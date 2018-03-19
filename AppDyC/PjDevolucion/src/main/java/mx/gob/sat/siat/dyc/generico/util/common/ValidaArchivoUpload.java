package mx.gob.sat.siat.dyc.generico.util.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.primefaces.model.UploadedFile;


public class ValidaArchivoUpload {


    public ValidaArchivoUpload() {
        super();
    }

    public boolean validaTamano(long tamanoMax, UploadedFile file) {
        boolean validacion;

        if (file.getSize() > tamanoMax) {
            validacion =  Boolean.FALSE;
        } else {
            validacion =  Boolean.TRUE;
        }

        return validacion;
    }

    public boolean validaTipo(String[] tipo, UploadedFile file) {
        boolean validacion =  Boolean.FALSE;
        for (int a = 0; a < tipo.length; a++) {
            if (file.getFileName().lastIndexOf(tipo[a]) != -1) {
                validacion =  Boolean.TRUE;
                break;
            }
        }
        return validacion;
    }


    public boolean validaRuta(UploadedFile file) {
        boolean validacion;
        FacesMessage errorArchivo =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Es necesario especificar una ruta de archivo");

        if (file == null) {
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
            validacion =  Boolean.FALSE;
        } else {
            validacion =  Boolean.TRUE;
        }

        return validacion;
    }

    public boolean validaTamNombre(UploadedFile file) {
        boolean validacion =  Boolean.TRUE;
        FacesMessage errorArchivo =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del archivo es mayor a 80 caracteres");

        String nom = file.getFileName();
        String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

        if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_80) {
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
            validacion =  Boolean.FALSE;
        }

        return validacion;
    }
}
