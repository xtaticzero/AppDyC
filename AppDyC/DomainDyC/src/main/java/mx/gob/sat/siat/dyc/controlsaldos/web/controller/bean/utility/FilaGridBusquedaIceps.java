package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.io.Serializable;

public class FilaGridBusquedaIceps implements Serializable
{
    private static final long serialVersionUID = 2349330628396674412L;
    
    private Integer id;
    private Integer idSaldoIcep;
    private String rfc;
    private String impuesto;
    private String concepto;
    private String periodo;
    private Integer ejercicio;
    private String tipoSaldo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }    
    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }
}
