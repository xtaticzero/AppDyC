package mx.gob.sat.siat.dyc.controlsaldos.vo;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


public class MontoParcialVO implements Serializable {
    private int  idImpuesto;
    private int idCocepto;
    private int idEjercicio;
    private int idPeriodo;
    
    private Integer idSaldoIcep;
    private Integer idDeclaraIcep;
    private Double actualizacion;
    private BigDecimal montoUsado;
    private Double montoCargo;
    private Double saldoAfavorAns;
    private Double saldoAfavorDes;
    private boolean declaracionUsada;
    private Date fechaPresentacion;


    public int getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(int idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public int getIdCocepto() {
        return idCocepto;
    }

    public void setIdCocepto(int idCocepto) {
        this.idCocepto = idCocepto;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Double getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Double actualizacion) {
        this.actualizacion = actualizacion;
    }

    public void setSaldoAfavorAns(Double saldoAfavorAns) {
        this.saldoAfavorAns = saldoAfavorAns;
    }

    public Double getSaldoAfavorAns() {
        return saldoAfavorAns;
    }

    public void setSaldoAfavorDes(Double saldoAfavorDes) {
        this.saldoAfavorDes = saldoAfavorDes;
    }

    public Double getSaldoAfavorDes() {
        return saldoAfavorDes;
    }

    public void setDeclaracionUsada(boolean declaracionUsada) {
        this.declaracionUsada = declaracionUsada;
    }

    public boolean isDeclaracionUsada() {
        return declaracionUsada;
    }

    public void setMontoCargo(Double montoCargo) {
        this.montoCargo = montoCargo;
    }

    public Double getMontoCargo() {
        return montoCargo;
    }


    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdDeclaraIcep(Integer idDeclaraIcep) {
        this.idDeclaraIcep = idDeclaraIcep;
    }

    public Integer getIdDeclaraIcep() {
        return idDeclaraIcep;
    }


    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = (Date)fechaPresentacion.clone();
    }

    public Date getFechaPresentacion() {
        
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }


    public void setMontoUsado(BigDecimal montoUsado) {
        this.montoUsado = montoUsado;
    }

    public BigDecimal getMontoUsado() {
        return montoUsado;
    }
}
