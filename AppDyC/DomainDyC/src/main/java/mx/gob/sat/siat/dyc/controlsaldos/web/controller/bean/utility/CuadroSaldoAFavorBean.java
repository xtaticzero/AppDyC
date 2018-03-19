package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;


public class CuadroSaldoAFavorBean
{
    private BigDecimal saldoAFavor;
    private String origen;
    private Date fecha;
    private String fechaStr;
    private Float inpc;
    private BigDecimal remanente;
    private BigDecimal remanenteTotal;
    private String descrInpc;
    private String saldoAFavorStr;
    private List<CargoCuadroSAFBean> cargos;
    private Date validacionDWH;
    
    public BigDecimal getSaldoAFavor() {
        return saldoAFavor;
    }

    public void setSaldoAFavor(BigDecimal saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Date getFecha() {
        return (fecha != null) ? (Date) fecha.clone() : null;
    }

    public void setFecha(Date fecha) {
        this.fecha = (fecha != null) ? (Date) fecha.clone() : null;
    }

    public Float getInpc() {
        return inpc;
    }

    public void setInpc(Float inpc) {
        this.inpc = inpc;
    }

    public BigDecimal getRemanente() {
        return remanente;
    }

    public void setRemanente(BigDecimal remanente) {
        this.remanente = remanente;
    }

    public BigDecimal getRemanenteTotal() {
        return remanenteTotal;
    }

    public void setRemanenteTotal(BigDecimal remanenteTotal) {
        this.remanenteTotal = remanenteTotal;
    }

    public List<CargoCuadroSAFBean> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoCuadroSAFBean> cargos) {
        this.cargos = cargos;
    }

    public String getDescrInpc() {
        return descrInpc;
    }

    public void setDescrInpc(String descrInpc) {
        this.descrInpc = descrInpc;
    }

    public String getSaldoAFavorStr() {
        return saldoAFavorStr;
    }

    public void setSaldoAFavorStr(String saldoAFavorStr) {
        this.saldoAFavorStr = saldoAFavorStr;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }
    
    public Date getValidacionDWH() {
        return validacionDWH != null ? (Date) validacionDWH.clone() : null;
    }

    public void setValidacionDWH(Date validacionDWH) {
        this.validacionDWH = validacionDWH != null ? (Date) validacionDWH.clone() : null;
    }
}
