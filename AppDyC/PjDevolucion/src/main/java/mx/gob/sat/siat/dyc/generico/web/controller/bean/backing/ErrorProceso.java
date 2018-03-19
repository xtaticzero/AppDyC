package mx.gob.sat.siat.dyc.generico.web.controller.bean.backing;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;


@ViewScoped
@ManagedBean(name = "mensajeErrorMB")
public class ErrorProceso implements Serializable{
    @SuppressWarnings("compatibility:-4768818693386235826")
    private static final long serialVersionUID = 1L;
    private String error;
    private boolean isImgVisible; 
    private String icon;
    private String messages;  
    /**
     * PostConstruct que obtiene la exception de session e imprime el error.
     */
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        String tipo=session.getAttribute("tipo")+"";
        error=session.getAttribute("error")+"";
        messages = "ui-messages-error";
        icon="ui-messages-warn-icon";
        isImgVisible=Boolean.TRUE;
        if("1".equals(tipo)){
            icon="ui-messages-i-sat-icon";
            messages = "ui-messages-info";
            isImgVisible = Boolean.FALSE;
        }else if("2".equals(tipo)){
            icon="ui-messages-fatal-icon";
            messages = "ui-messages-fatal";
            isImgVisible = false;
        }
    }

    /**
     * De tipo String.
     * 
     * @param error Tipo de dato String.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * De tipo String.
     * 
     * @return error.
     */
    public String getError() {
        return error;
    }

    public boolean isIsImgVisible() {
        return isImgVisible;
    }

    public void setIsImgVisible(boolean isImgVisible) {
        this.isImgVisible = isImgVisible;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
