/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.motivo;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para la tabla DYCC_MOTIVORECHAZO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccMotivoRechazoDTO implements Serializable {

    @SuppressWarnings("compatibility:-4229778223185164747")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idMotivoRechazo;

    public DyccMotivoRechazoDTO() {
    }

    public DyccMotivoRechazoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idMotivoRechazo) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idMotivoRechazo = idMotivoRechazo;
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

    public void setIdMotivoRechazo(Integer idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    public Integer getIdMotivoRechazo() {
        return idMotivoRechazo;
    }
}
