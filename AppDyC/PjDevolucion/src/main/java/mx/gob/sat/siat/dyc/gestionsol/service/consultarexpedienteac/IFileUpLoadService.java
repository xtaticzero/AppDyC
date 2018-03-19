package mx.gob.sat.siat.dyc.gestionsol.service.consultarexpedienteac;

import org.primefaces.model.UploadedFile;

/**
 * @author J. Isaac Carbajal Bernal
 */
public interface IFileUpLoadService {
    boolean validaTamanio(Long tamanioMax, UploadedFile file);
    boolean validaTipo(String tipo1,String tipo2, UploadedFile file);
    boolean validaRuta(UploadedFile file);
}
