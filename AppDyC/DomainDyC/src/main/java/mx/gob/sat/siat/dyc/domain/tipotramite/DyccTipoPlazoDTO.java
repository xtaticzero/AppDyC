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
 * DTO para el catalogo DYCC_TIPOPLAZO
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccTipoPlazoDTO implements Serializable {

    @SuppressWarnings("compatibility:7830914686774645967")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idTipoPlazo;
    private List<DyccTipoTramiteDTO> dyccTipoTramiteList;

    public DyccTipoPlazoDTO() {
    }

    public DyccTipoPlazoDTO(Integer idTipoPlazo, String descripcion, Date fechaInicio, Date fechaFin) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoPlazo = idTipoPlazo;
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

    public void setIdTipoPlazo(Integer idTipoPlazo) {
        this.idTipoPlazo = idTipoPlazo;
    }

    public Integer getIdTipoPlazo() {
        return idTipoPlazo;
    }

    public void setDyccTipoTramiteList(List<DyccTipoTramiteDTO> dyccTipoTramiteList) {
        this.dyccTipoTramiteList = dyccTipoTramiteList;
    }

    public List<DyccTipoTramiteDTO> getDyccTipoTramiteList() {
        return dyccTipoTramiteList;
    }

    public String getParametroReport() {
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
        buffer.append("idTipoPlazo=");
        buffer.append(getIdTipoPlazo());
        buffer.append(']');
        return buffer.toString();
    }
}
