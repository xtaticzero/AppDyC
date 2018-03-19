/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.documento;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_ESTADODOC
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccEstadoDocDTO implements Serializable {

    @SuppressWarnings("compatibility:6355044968741536317")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idEstadoDoc;

    public DyccEstadoDocDTO() {
    }

    public DyccEstadoDocDTO(Integer idEstadoDoc, String descripcion, Date fechaInicio, Date fechaFin) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idEstadoDoc = idEstadoDoc;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdEstadoDoc(Integer idEstadoDoc) {
        this.idEstadoDoc = idEstadoDoc;
    }

    public Integer getIdEstadoDoc() {
        return idEstadoDoc;
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

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
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
        buffer.append("idEstadoDoc=");
        buffer.append(getIdEstadoDoc());
        buffer.append(']');
        return buffer.toString();
    }
}
