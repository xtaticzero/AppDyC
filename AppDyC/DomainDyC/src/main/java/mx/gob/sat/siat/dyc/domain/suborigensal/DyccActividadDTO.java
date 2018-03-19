/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.suborigensal;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el tabla DYCC_ACTIVIDAD
 * @author  Alfredo Ramirez
 * @since   24/04/2014
 */
public class DyccActividadDTO implements Serializable {

    @SuppressWarnings("compatibility:-2057238630348274316")
    private static final long serialVersionUID = 1L;

    private Integer idActividad;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO;

    public DyccActividadDTO() {
    }

    public DyccActividadDTO(Integer idActividad, String descripcion, Date fechaInicio, Date fechaFin) {
        this.idActividad = idActividad;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getIdActividad() {
        return idActividad;
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

    public void setDyccSubOrigSaldoDTO(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO) {
        this.dyccSubOrigSaldoDTO = dyccSubOrigSaldoDTO;
    }

    public DyccSubOrigSaldoDTO getDyccSubOrigSaldoDTO() {
        return dyccSubOrigSaldoDTO;
    }
}
