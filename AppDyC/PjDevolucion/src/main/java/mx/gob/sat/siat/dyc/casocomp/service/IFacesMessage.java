package mx.gob.sat.siat.dyc.casocomp.service;

import javax.faces.application.FacesMessage;

/**
 * @author J. Isaac Carbajal Bernal
 */
public interface IFacesMessage {
    void facesMensaje(String mensaje, FacesMessage.Severity severity);

    void facesMensajeDB(Integer idMensaje, Integer idGrupoOperacion, FacesMessage.Severity severity);

    String getMessage(Integer idMensaje, Integer idGrupoOperacion);
}
