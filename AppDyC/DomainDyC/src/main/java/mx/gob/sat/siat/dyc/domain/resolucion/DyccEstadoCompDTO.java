/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_ESTADOCOMP
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccEstadoCompDTO implements Serializable {

    @SuppressWarnings("compatibility:6095800860186753414")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idEstadoComp;

    public DyccEstadoCompDTO() {
    }

    public DyccEstadoCompDTO(Integer idEstadoComp, String descripcion, Date fechaInicio, Date fechaFin) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idEstadoComp = idEstadoComp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
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

    public void setIdEstadoComp(Integer idEstadoComp) {
        this.idEstadoComp = idEstadoComp;
    }

    public Integer getIdEstadoComp() {
        return idEstadoComp;
    }
}
