/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.tipotramite;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * DTO para el catalogo DYCC_VALIDACION
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccValidacionDTO implements Serializable {

    @SuppressWarnings("compatibility:-8664900103827268523")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idValidacion;
    private List<DycaValidaTramiteDTO> dycaValidaTramiteList;

    public DyccValidacionDTO() {
    }

    public DyccValidacionDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idValidacion) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idValidacion = idValidacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getIdValidacion() {
        return idValidacion;
    }

    public void setIdValidacion(Integer idValidacion) {
        this.idValidacion = idValidacion;
    }

    public List<DycaValidaTramiteDTO> getDycaValidaTramiteList() {
        return dycaValidaTramiteList;
    }

    public void setDycaValidaTramiteList(List<DycaValidaTramiteDTO> dycaValidaTramiteList) {
        this.dycaValidaTramiteList = dycaValidaTramiteList;
    }

    public String getParamterReport() {
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
        buffer.append("idValidacion=");
        buffer.append(getIdValidacion());
        buffer.append(']');
        return buffer.toString();
    }
}
