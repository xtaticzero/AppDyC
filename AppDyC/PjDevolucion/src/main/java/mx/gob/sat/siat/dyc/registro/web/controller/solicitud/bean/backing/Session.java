package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="auth")
@SessionScoped
public class Session implements Serializable {
    @SuppressWarnings("compatibility:1124523532984677206")
    private static final long serialVersionUID = -3367531425112372643L;

    public Session() {
        super();
    }
    
    
    public void keepSessionAlive() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
    }
}
