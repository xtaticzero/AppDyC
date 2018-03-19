package mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores;

import java.sql.Date;


public class SegtCambioAdscripcionVO {

    private String rfc;
    private Integer ccosto;
    private String depid;
    private Integer ccostoop;
    private String depidop;
    private String observaciones;
    private Date fechaInicio;
    private Date fechaFin;

    public SegtCambioAdscripcionVO() {
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setCcosto(Integer ccosto) {
        this.ccosto = ccosto;
    }

    public Integer getCcosto() {
        return ccosto;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getDepid() {
        return depid;
    }

    public void setCcostoop(Integer ccostoop) {
        this.ccostoop = ccostoop;
    }

    public Integer getCcostoop() {
        return ccostoop;
    }

    public void setDepidop(String depidop) {
        this.depidop = depidop;
    }

    public String getDepidop() {
        return depidop;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if(fechaFin!= null){
            return (Date)fechaFin.clone();
        }else{
            return null;
        }
    }
}
