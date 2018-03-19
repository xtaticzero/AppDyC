package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.math.BigDecimal;

import java.util.Date;


public class FilaGridConversionesBean
{
    private String tipoConversion;
    private BigDecimal importeConvertido;
    private Date fechaDestino;
    private Float factorActualizacion;
    private Float inpcOrigen;
    private Date fechaPubInpcOrigen;
    private Float inpcDestino;
    private Date fechaPubInpcDestino;

    public String getTipoConversion() {
        return tipoConversion;
    }

    public void setTipoConversion(String tipoConversion) {
        this.tipoConversion = tipoConversion;
    }

    public BigDecimal getImporteConvertido() {
        return importeConvertido;
    }

    public void setImporteConvertido(BigDecimal importeConvertido) {
        this.importeConvertido = importeConvertido;
    }

    public Date getFechaDestino() {
        return (fechaDestino != null) ? (Date) fechaDestino.clone() : null;
    }

    public void setFechaDestino(Date fechaDestino) {
        this.fechaDestino = (fechaDestino != null) ? (Date) fechaDestino.clone() : null;
    }

    public Float getFactorActualizacion() {
        return factorActualizacion;
    }

    public void setFactorActualizacion(Float factorActualizacion) {
        this.factorActualizacion = factorActualizacion;
    }

    public Float getInpcOrigen() {
        return inpcOrigen;
    }

    public void setInpcOrigen(Float inpcOrigen) {
        this.inpcOrigen = inpcOrigen;
    }

    public Date getFechaPubInpcOrigen() {
        return (fechaPubInpcOrigen != null) ? (Date) fechaPubInpcOrigen.clone() : null;
    }

    public void setFechaPubInpcOrigen(Date fechaPubInpcOrigen) {
        this.fechaPubInpcOrigen = (fechaPubInpcOrigen != null) ? (Date) fechaPubInpcOrigen.clone() : null;
    }

    public Float getInpcDestino() {
        return inpcDestino;
    }

    public void setInpcDestino(Float inpcDestino) {
        this.inpcDestino = inpcDestino;
    }

    public Date getFechaPubInpcDestino() {
        return (fechaPubInpcDestino != null) ? (Date) fechaPubInpcDestino.clone() : null;
    }

    public void setFechaPubInpcDestino(Date fechaPubInpcDestino) {
        this.fechaPubInpcDestino = (fechaPubInpcDestino != null) ? (Date) fechaPubInpcDestino.clone() : null;
    }
}
