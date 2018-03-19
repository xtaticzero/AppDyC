package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.util.Date;

/**
 *
 * @author Softtek
 */
public class FilaGridMovsSaldoBean
{
    private Integer idMovSaldo;
    private Date fechaRegistro;
    private Date fechaOrigen;
    private String tipoMovimiento;
    private String origen;
    private Double monto;
    private String descrOrigen;
    private String infoAdicional;

    private String estatus;

    private Integer idAfectacion;
    private String justificacion;
    private Integer idMovimiento;

    public Integer getIdMovSaldo() {
        return idMovSaldo;
    }

    public void setIdMovSaldo(Integer idMovSaldo) {
        this.idMovSaldo = idMovSaldo;
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaOrigen() {
        if (fechaOrigen != null) {
            return (Date)fechaOrigen.clone();
        } else {
            return null;
        }
    }

    public void setFechaOrigen(Date fechaOrigen) {
        if (fechaOrigen != null) {
            this.fechaOrigen = (Date)fechaOrigen.clone();
        } else {
            this.fechaOrigen = null;
        }
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescrOrigen() {
        return descrOrigen;
    }

    public void setDescrOrigen(String descrOrigen) {
        this.descrOrigen = descrOrigen;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Integer getIdAfectacion() {
        return idAfectacion;
    }

    public void setIdAfectacion(Integer idAfectacion) {
        this.idAfectacion = idAfectacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }
}
