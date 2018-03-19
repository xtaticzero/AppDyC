package mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac;

import java.util.Date;

/**
 * @author J. Isaac Carbajal Bernal
 */
public class CuadroBean {
    private Integer idDetalleAviso;
    private String origenComp;
    private String tipoTramite;
    private Integer presentoDIOT;
    private Long numOperacionDIOT;
    private Date fechaPresentacionDIOT;
    private String concepto;
    private String impuesto;
    private Integer ejercicio;
    private String periodo;
    private String tipoDePeriodo;
    private Integer esRemanente; 
    private String numeroControl;
    private Double impActualizado;
    private Double cantImpCompensa;
    private Double remImpCompensa;

    public void setOrigenComp(String origenComp) {
        this.origenComp = origenComp;
    }

    public String getOrigenComp() {
        return origenComp;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setNumOperacionDIOT(Long numOperacionDIOT) {
        this.numOperacionDIOT = numOperacionDIOT;
    }

    public Long getNumOperacionDIOT() {
        return numOperacionDIOT;
    }

    public void setFechaPresentacionDIOT(Date fechaPresentacionDIOT) {
        if (fechaPresentacionDIOT != null) {
            this.fechaPresentacionDIOT = (Date)fechaPresentacionDIOT.clone();
        } else {
            this.fechaPresentacionDIOT = null;
        }

    }

    public Date getFechaPresentacionDIOT() {
        if (fechaPresentacionDIOT != null) {
            return (Date)fechaPresentacionDIOT.clone();
        } else {
            return null;
        }
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setEsRemanente(Integer esRemanente) {
        this.esRemanente = esRemanente;
    }

    public Integer getEsRemanente() {
        return esRemanente;
    }

    public void setImpActualizado(Double impActualizado) {
        this.impActualizado = impActualizado;
    }

    public Double getImpActualizado() {
        return impActualizado;
    }

    public void setCantImpCompensa(Double cantImpCompensa) {
        this.cantImpCompensa = cantImpCompensa;
    }

    public Double getCantImpCompensa() {
        return cantImpCompensa;
    }

    public void setRemImpCompensa(Double remImpCompensa) {
        this.remImpCompensa = remImpCompensa;
    }

    public Double getRemImpCompensa() {
        return remImpCompensa;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setPresentoDIOT(Integer presentoDIOT) {
        this.presentoDIOT = presentoDIOT;
    }

    public Integer getPresentoDIOT() {
        return presentoDIOT;
    }

    public void setTipoDePeriodo(String tipoDePeriodo) {
        this.tipoDePeriodo = tipoDePeriodo;
    }

    public String getTipoDePeriodo() {
        return tipoDePeriodo;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setIdDetalleAviso(Integer idDetalleAviso) {
        this.idDetalleAviso = idDetalleAviso;
    }

    public Integer getIdDetalleAviso() {
        return idDetalleAviso;
    }
}
