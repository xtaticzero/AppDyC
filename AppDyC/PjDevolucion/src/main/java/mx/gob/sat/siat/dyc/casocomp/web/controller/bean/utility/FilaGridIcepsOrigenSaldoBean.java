package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility;

import java.math.BigDecimal;

import java.util.Date;


public class FilaGridIcepsOrigenSaldoBean {
    private Integer idSaldoIcep;
    private String origenSaldo;
    private String periodicidad;
    private String periodo;
    private String ejercicio;
    private String concepto;
    private BigDecimal saldoAplicar;
    private BigDecimal montoSaldoFavor;
    private Date fechaPresentoDeclaracion;
    private BigDecimal remanenteHistorico;
    private Date fechaCausacion;

    public String getOrigenSaldo() {
        return origenSaldo;
    }

    public void setOrigenSaldo(String origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setFechaPresentoDeclaracion(Date fechaPresentoDeclaracion) {
        if (null != fechaPresentoDeclaracion) {
            this.fechaPresentoDeclaracion = (Date)fechaPresentoDeclaracion.clone();
        } else {
            this.fechaPresentoDeclaracion = null;
        }
    }

    public Date getFechaPresentoDeclaracion() {
        if (null != this.fechaPresentoDeclaracion) {
            return (Date)this.fechaPresentoDeclaracion.clone();
        } else {
            return null;
        }
    }

    public void setFechaCausacion(Date fechaCausacion) {
        if (null != fechaCausacion) {
            this.fechaCausacion = (Date)fechaCausacion.clone();
        } else {
            this.fechaCausacion = null;
        }
    }

    public Date getFechaCausacion() {
        if (null != fechaCausacion) {
            return (Date)fechaCausacion.clone();
        } else {
            return null;
        }
    }

    public void setSaldoAplicar(BigDecimal saldoAplicar) {
        this.saldoAplicar = saldoAplicar;
    }

    public BigDecimal getSaldoAplicar() {
        return saldoAplicar;
    }

    public void setMontoSaldoFavor(BigDecimal montoSaldoFavor) {
        this.montoSaldoFavor = montoSaldoFavor;
    }

    public BigDecimal getMontoSaldoFavor() {
        return montoSaldoFavor;
    }

    public void setRemanenteHistorico(BigDecimal remanenteHistorico) {
        this.remanenteHistorico = remanenteHistorico;
    }

    public BigDecimal getRemanenteHistorico() {
        return remanenteHistorico;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }
}
