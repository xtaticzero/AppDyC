/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.generico.util;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Clase de mensajes generica
 * @author  Alfredo Ramirez
 * @since   05/12/2013
 */
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 8827772692600867120L;

    public void addInfo(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
    }

    public void addWarn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

    public void addError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }

    public void addFatal(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensaje, ""));
    }

    public void addInfo(String nombre, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(nombre,
                                                     new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
    }

    public void addWarn(String nombre, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(nombre,
                                                     new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

    public void addError(String nombre, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(nombre,
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }

    public void addFatal(String nombre, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(nombre,
                                                     new FacesMessage(FacesMessage.SEVERITY_FATAL, mensaje, ""));
    }
}
