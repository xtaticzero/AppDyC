package mx.gob.sat.siat.dyc.casocomp.service.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Service(value = "facesMessage")
public class FacesMessageImpl implements IFacesMessage {

    @Autowired
    private DyccMensajeUsrService dyccMensajeUsrService;

    @Override
    public void facesMensaje(String mensaje, FacesMessage.Severity severity) {
        FacesMessage ms = new FacesMessage(severity, null, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, ms);
    }

    @Override
    public void facesMensajeDB(Integer idMensaje, Integer idGrupoOperacion, FacesMessage.Severity severity) {
        DyccMensajeUsrDTO mensajeUsr = dyccMensajeUsrService.encontrar(idMensaje, idGrupoOperacion);
        FacesMessage ms = new FacesMessage(severity, null, mensajeUsr.getDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, ms);
    }

    @Override
    public String getMessage(Integer idMensaje, Integer idGrupoOperacion) {
        try {
            return dyccMensajeUsrService.encontrar(idMensaje, idGrupoOperacion).getDescripcion();
        } catch (Exception e) {
           return null;
        }
    }
}
