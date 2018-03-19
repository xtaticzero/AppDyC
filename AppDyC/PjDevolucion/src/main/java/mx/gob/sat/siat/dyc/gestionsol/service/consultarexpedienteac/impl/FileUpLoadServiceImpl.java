package mx.gob.sat.siat.dyc.gestionsol.service.consultarexpedienteac.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.gestionsol.service.consultarexpedienteac.IFileUpLoadService;

import org.primefaces.model.UploadedFile;

import org.springframework.stereotype.Service;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Service(value = "fileUpLoad")
public class FileUpLoadServiceImpl implements IFileUpLoadService {
    @Override
    public boolean validaTamanio(Long tamanioMax, UploadedFile file) {
        boolean tam;
        
        FacesMessage errorTamanio = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","El archivo"+ file.getFileName() +"sobrepasa el peso permitido "+tamanioMax.toString());
        if(file.getSize() > tamanioMax){
            FacesContext.getCurrentInstance().addMessage(null, errorTamanio);
            tam=Boolean.FALSE;
        }
        else{
            tam=Boolean.TRUE;
            }
        return  tam;
    }

    @Override
    public boolean validaTipo(String tipo1,String tipo2, UploadedFile file) {
        boolean tip;
        FacesMessage errorTipo = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","El archivo"+ file.getFileName() +"sobrepasa el peso permitido "+ "no es de tipo zip");
        if(!file.getContentType().equals(tipo1)&& !file.getContentType().equals(tipo2)){
            FacesContext.getCurrentInstance().addMessage(null, errorTipo);
            tip=Boolean.FALSE;
        }
        else{
            tip=Boolean.TRUE;
            }
        
        return tip;
    }

    @Override
    public boolean validaRuta(UploadedFile file) {
        boolean rut;
        FacesMessage errorRuta = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","Es necesario especificar una ruta de archivo");
        if(file == null){
            FacesContext.getCurrentInstance().addMessage(null, errorRuta);
            rut=Boolean.FALSE;
        }
        else{
            rut=Boolean.TRUE;
            }
        
        return rut;
    }
}
