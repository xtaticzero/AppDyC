package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para la tabla DYCC_EDOPADRONAGRO
 * @author  luis Alberto Dominguez Palomino
 *
 */
public class DyccEdoPadronAgroDTO implements Serializable {

    @SuppressWarnings("compatibility:-5641865609098912989")
    private static final long serialVersionUID = 1L;
    
    private Integer estadoPadron;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;

    public DyccEdoPadronAgroDTO() {
    }
    
    public DyccEdoPadronAgroDTO(Integer estadoPadron, String descripcion, Date fechaInicio, Date fechaFin) {
        this.estadoPadron = estadoPadron;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
    }
    
    public void setEstadoPadron(Integer estadoPadron) {
        this.estadoPadron = estadoPadron;
    }

    public Integer getEstadoPadron() {
        return estadoPadron;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
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
}
