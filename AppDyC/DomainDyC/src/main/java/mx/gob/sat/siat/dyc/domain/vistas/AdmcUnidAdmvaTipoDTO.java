package mx.gob.sat.siat.dyc.domain.vistas;

import java.io.Serializable;

import java.util.Date;


public class AdmcUnidAdmvaTipoDTO implements Serializable {
    public AdmcUnidAdmvaTipoDTO() {
        super();
    }
    
    private Integer idUnidadAdmvaTipo;
    private String nombre;
    private String descripcion;
    private Integer ordenSec;
    private Date fechaInicio;
    private Date fechaFin;
    private String constanteJava;

    public void setIdUnidadAdmvaTipo(Integer idUnidadAdmvaTipo) {
        this.idUnidadAdmvaTipo = idUnidadAdmvaTipo;
    }

    public Integer getIdUnidadAdmvaTipo() {
        return idUnidadAdmvaTipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (java.util.Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (java.util.Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (java.util.Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (java.util.Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setConstanteJava(String constanteJava) {
        this.constanteJava = constanteJava;
    }

    public String getConstanteJava() {
        return constanteJava;
    }
}
