package mx.gob.sat.siat.dyc.domain.catalogo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Gregorio.Serapio
 */
public class DyccMontoDTO implements Serializable {

    private static final long serialVersionUID = 2230147518481172L;
    private Integer idMonto;
    private BigDecimal montoMax;
    private Date fechaInicio;
    private Date fechaFin;

    public Integer getIdMonto() {
        return idMonto;
    }

    public void setIdMonto(Integer idMonto) {
        this.idMonto = idMonto;
    }

    public BigDecimal getMontoMax() {
        return montoMax;
    }

    public void setMontoMax(BigDecimal montoMax) {
        this.montoMax = montoMax;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date) fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date) fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    @Override
    public String toString() {
        return "DyccMonto{" + "idMonto=" + idMonto + ", montoMax=" + montoMax + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + '}';
    }

}
