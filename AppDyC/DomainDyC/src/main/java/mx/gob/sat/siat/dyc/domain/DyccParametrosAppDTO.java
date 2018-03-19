/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO de la tabla DYCC_PARAMETROSAPP
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccParametrosAppDTO implements Serializable {

    @SuppressWarnings("compatibility:2128752743144627307")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idParametro;
    private BigDecimal valor;
    private String valorStr;

    public DyccParametrosAppDTO() {
    }

    public DyccParametrosAppDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idParametro,
                                BigDecimal valor) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idParametro = idParametro;
        this.valor = valor;
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

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }


    public void setValorStr(String valorStr) {
        this.valorStr = valorStr;
    }

    public String getValorStr() {
        return valorStr;
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
        buffer.append("idParametro=");
        buffer.append(getIdParametro());
        buffer.append(',');
        buffer.append("valor=");
        buffer.append(getValor());
        buffer.append(']');
        return buffer.toString();
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
