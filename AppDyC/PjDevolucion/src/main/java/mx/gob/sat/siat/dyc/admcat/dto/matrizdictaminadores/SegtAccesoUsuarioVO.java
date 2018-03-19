package mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores;

import java.sql.Date;

public class SegtAccesoUsuarioVO {

    private String rfc;
    private Integer idMotivo;
    private String observaciones;
    private Date fechaInicio;
    private Date fechaFin;

    public SegtAccesoUsuarioVO() {
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setFechaInicio(Date fechaInicio) {
        if(fechaInicio != null){
            this.fechaInicio = (Date)fechaInicio.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if(fechaInicio != null){
            return (Date)fechaInicio.clone();
        }else{
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if(fechaFin != null){
            this.fechaFin = (Date)fechaFin.clone();
        }else{
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if(fechaFin != null){
            return (Date)fechaFin.clone();
        }else{
            return null;
        }
    }
}
