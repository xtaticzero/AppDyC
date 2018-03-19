package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

public class DyctSaldoIcepAuxVO implements Serializable {

    @SuppressWarnings("compatibility:6539281770191403795")
    private static final long serialVersionUID = 1L;
    
    private String rfc;
    private String impuesto;
    private String concepto;
    private String tipoPeriodo;
    private String periodo;
    private int ejercicio;
    private String tipoSaldo;
    private Long idSaldoIcep;
    private String numControl;
    
    private String razonSocial;
    private String nombre;
    private Double remanente;
    private Integer bloqueado;

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setIdSaldoIcep(Long idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public Long getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRemanente(Double remanente) {
        this.remanente = remanente;
    }

    public Double getRemanente() {
        return remanente;
    }

    public void setBloqueado(Integer bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Integer getBloqueado() {
        return bloqueado;
    }
}

