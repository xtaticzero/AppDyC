package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.math.BigDecimal;

import java.util.Date;


public class CargoCuadroSAFBean
{
    private BigDecimal cargoOrigen;
    private Float inpc;
    private Float factorAct;
    private Date fechaOrigen;
    private String origen;
    private BigDecimal cargoReal;
    private String descrInpc;
    private String descrOrigen;

    public BigDecimal getCargoOrigen() {
        return cargoOrigen;
    }

    public void setCargoOrigen(BigDecimal cargoOrigen) {
        this.cargoOrigen = cargoOrigen;
    }

    public Float getInpc() {
        return inpc;
    }

    public void setInpc(Float inpc) {
        this.inpc = inpc;
    }

    public Float getFactorAct() {
        return factorAct;
    }

    public void setFactorAct(Float factorAct) {
        this.factorAct = factorAct;
    }

    public Date getFechaOrigen() {
        return (fechaOrigen != null) ? (Date) fechaOrigen.clone() : null;
    }

    public void setFechaOrigen(Date fechaOrigen) {
        this.fechaOrigen = (fechaOrigen != null) ? (Date) fechaOrigen.clone() : null;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public BigDecimal getCargoReal() {
        return cargoReal;
    }

    public void setCargoReal(BigDecimal cargoReal) {
        this.cargoReal = cargoReal;
    }

    public String getDescrInpc() {
        return descrInpc;
    }

    public void setDescrInpc(String descrInpc) {
        this.descrInpc = descrInpc;
    }

    public String getDescrOrigen() {
        return descrOrigen;
    }

    public void setDescrOrigen(String descrOrigen) {
        this.descrOrigen = descrOrigen;
    }
}
