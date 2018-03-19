package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;


public class DeclaracionIcepParamVO implements Serializable{


    @SuppressWarnings ("compatibility:3419471355064217713")
    private static final long serialVersionUID = -7092918770008676189L;
    
    private String rfc;
    private Integer idPeriodo;
    private Integer idEjercicio;
    private Integer idConcepto;
    private Integer idTipoTramite;
    private String usuario;


    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
