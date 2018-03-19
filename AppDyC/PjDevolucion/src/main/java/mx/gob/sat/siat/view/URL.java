package mx.gob.sat.siat.view;

import java.io.Serializable;


/**
 * Clase del objeto URL.
 * @author aoma878v.
 */
public class URL implements Serializable{
    
    @SuppressWarnings("compatibility:3229993428594335669")
    private static final long serialVersionUID = 1L;
    private String url;
    private String target;
    private String tipoLogeo;
    private String hostServer;
    private String idSession;


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTipoLogeo(String tipoLogeo) {
        this.tipoLogeo = tipoLogeo;
    }

    public String getTipoLogeo() {
        return tipoLogeo;
    }

    public void setHostServer(String hostServer) {
        this.hostServer = hostServer;
    }

    public String getHostServer() {
        return hostServer;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getIdSession() {
        return idSession;
    }
}
