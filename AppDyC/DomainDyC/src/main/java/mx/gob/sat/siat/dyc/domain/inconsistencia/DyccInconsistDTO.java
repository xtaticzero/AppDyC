/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.inconsistencia;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla [DYCC_INCONSIST].
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Agosto 19, 2015
 *
 */
public class DyccInconsistDTO implements Serializable {

    @SuppressWarnings("compatibility:1728694190607319527")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idInconsistencia;

    public DyccInconsistDTO() {
    }

    public DyccInconsistDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idInconsistencia) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idInconsistencia = idInconsistencia;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (java.sql.Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(java.sql.Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (java.sql.Date)fechaFin.clone();
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdInconsistencia(Integer idInconsistencia) {
        this.idInconsistencia = idInconsistencia;
    }

    public Integer getIdInconsistencia() {
        return idInconsistencia;
    }

    public String getoParameterReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("descripcion=");
        buffer.append(getDescripcion());
        buffer.append(',');
        buffer.append("fechaFin=");
        buffer.append(getFechaFin());
        buffer.append(',');
        buffer.append("fechaInicio=");
        buffer.append(getFechaInicio());
        buffer.append(',');
        buffer.append("idInconsistencia=");
        buffer.append(getIdInconsistencia());
        buffer.append(']');
        return buffer.toString();
    }
}
