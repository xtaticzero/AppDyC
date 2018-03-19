/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.saldoicep;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_EJERCICIO
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccEjercicioDTO implements Serializable {

    @SuppressWarnings("compatibility:-7754524901683519678")
    private static final long serialVersionUID = 1L;

    private Date fechaInicio;
    private Date fechaFin;
    private Integer idEjercicio;
    private Integer permiteSeleccion;

    public DyccEjercicioDTO() {
    }

    public DyccEjercicioDTO(Date fechaFin, Date fechaInicio, Integer idEjercicio, Integer permiteSeleccion) {
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idEjercicio = idEjercicio;
        this.permiteSeleccion = permiteSeleccion;
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

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setPermiteSeleccion(Integer permiteSeleccion) {
        this.permiteSeleccion = permiteSeleccion;
    }

    public Integer getPermiteSeleccion() {
        return permiteSeleccion;
    }
}
