/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_DIAINHABIL
 * @author  Alfredo Ramirez
 * @since   30/08/2013
 */
public class DyccDiaInhabilDTO implements Serializable {

    @SuppressWarnings("compatibility:-6744419580628434086")
    private static final long serialVersionUID = 1L;

    private Integer ejercicio;
    private Integer esInhabil;
    private Date fecha;
    private Date fechaMovimiento;

    public DyccDiaInhabilDTO() {
    }

    public DyccDiaInhabilDTO(Integer ejercicio, Integer esInhabil, Date fecha, Date fechaMovimiento) {
        this.ejercicio = ejercicio;
        this.esInhabil = esInhabil;
        this.fecha = fecha != null ? (Date)fecha.clone() : null;
        this.fechaMovimiento = fechaMovimiento != null ? (Date)fechaMovimiento.clone() : null;
    }

    public void setFecha(Date fecha) {
        if (null != fecha) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public Date getFecha() {
        if (null != fecha) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        if (null != fechaMovimiento) {
            this.fechaMovimiento = (Date)fechaMovimiento.clone();
        } else {
            this.fechaMovimiento = null;
        }
    }

    public Date getFechaMovimiento() {
        if (null != fechaMovimiento) {
            return (Date)fechaMovimiento.clone();
        } else {
            return null;
        }
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEsInhabil(Integer esInhabil) {
        this.esInhabil = esInhabil;
    }

    public Integer getEsInhabil() {
        return esInhabil;
    }

    public String getParameterReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("ejercicio=");
        buffer.append(getEjercicio());
        buffer.append(',');
        buffer.append("esInhabil=");
        buffer.append(getEsInhabil());
        buffer.append(',');
        buffer.append("fecha=");
        buffer.append(getFecha());
        buffer.append(',');
        buffer.append("fechaMovimiento=");
        buffer.append(getFechaMovimiento());
        buffer.append(']');
        return buffer.toString();
    }
}
