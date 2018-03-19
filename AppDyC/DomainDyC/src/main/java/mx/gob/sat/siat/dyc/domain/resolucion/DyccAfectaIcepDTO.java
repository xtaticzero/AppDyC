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
 * DTO para el tabla DYCC_AFECTAICEP
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccAfectaIcepDTO implements Serializable {

    @SuppressWarnings("compatibility:5488818824995433162")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idAfectacion;

    public DyccAfectaIcepDTO() {
    }

    public DyccAfectaIcepDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idAfectacion) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idAfectacion = idAfectacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setIdAfectacion(Integer idAfectacion) {
        this.idAfectacion = idAfectacion;
    }

    public Integer getIdAfectacion() {
        return idAfectacion;
    }
}
