package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

public class NotificacionVO implements Serializable {

    private Integer idFalloDetalle;
    private String origen;
    private String mensaje;
    private Integer total;

    public NotificacionVO() {
        super();
    }

    public NotificacionVO(Integer idFalloDetalle, String origen, String mensaje, Integer total) {
        super();
        this.idFalloDetalle = idFalloDetalle;
        this.origen = origen;
        this.mensaje = mensaje;
        this.total = total;
    }

    public Integer getIdFalloDetalle() {
        return idFalloDetalle;
    }

    public void setIdFalloDetalle(Integer idFalloDetalle) {
        this.idFalloDetalle = idFalloDetalle;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
