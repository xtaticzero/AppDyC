/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.adminproceso;

import java.util.Date;

/**
 * DTO para el catalogo DYCC_STATUSPROCESO
 * @author  Alfredo Ramirez
 * @since   25/04/2014
 */
public class DyccStatusProcesoDTO {

    private Integer idStatusProceso;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;

    public DyccStatusProcesoDTO() {
    }

    public DyccStatusProcesoDTO(Integer idStatusProceso, String nombre, Date fechaInicio, Date fechaFin) {
        this.idStatusProceso = idStatusProceso;
        this.nombre = nombre;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
    }

    public void setIdStatusProceso(Integer idStatusProceso) {
        this.idStatusProceso = idStatusProceso;
    }

    public Integer getIdStatusProceso() {
        return idStatusProceso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
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
}
