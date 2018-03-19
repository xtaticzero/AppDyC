package mx.gob.sat.siat.dyc.domain.periodo;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;


/**
 * DTO para la tabla DYCC_ESTADOPAGO
 * @author Alfredo Ramirez
 * @since 01/04/2014
 */
public class DyccEstadoPagoDTO implements Serializable {

    @SuppressWarnings("compatibility:2065151149581738039")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idEstadoPago;
    private List<DycpSolPagoDTO> dycpSolPagoList;

    public DyccEstadoPagoDTO() {
    }

    public DyccEstadoPagoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idEstadoPago) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idEstadoPago = idEstadoPago;
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

    public void setIdEstadoPago(Integer idEstadoPago) {
        this.idEstadoPago = idEstadoPago;
    }

    public Integer getIdEstadoPago() {
        return idEstadoPago;
    }

    public void setDycpSolPagoList(List<DycpSolPagoDTO> dycpSolPagoList) {
        this.dycpSolPagoList = dycpSolPagoList;
    }

    public List<DycpSolPagoDTO> getDycpSolPagoList() {
        return dycpSolPagoList;
    }

    public String getParameterReport() {
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
        buffer.append("idEstadoPago=");
        buffer.append(getIdEstadoPago());
        buffer.append(']');
        return buffer.toString();
    }
}
