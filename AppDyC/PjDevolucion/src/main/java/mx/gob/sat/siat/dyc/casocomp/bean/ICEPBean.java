package mx.gob.sat.siat.dyc.casocomp.bean;

import java.math.BigDecimal;


/**
 * @author Huetzin Cruz Lozano
 */
public class ICEPBean
{
    private String impuesto;
    private String concepto;
    private Integer ejercicio;
    private String periodo;
    private String fechaPresDeclara;
    private BigDecimal importeCompensado;

    private BigDecimal impCompImprocedente;
    private BigDecimal saldoFavorImp;

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

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getFechaPresDeclara() {
        return fechaPresDeclara;
    }

    public void setFechaPresDeclara(String fechaPresDeclara) {
        this.fechaPresDeclara = fechaPresDeclara;
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public void setImpCompImprocedente(BigDecimal impCompImprocedente) {
        this.impCompImprocedente = impCompImprocedente;
    }

    public BigDecimal getImpCompImprocedente() {
        return impCompImprocedente;
    }

    public void setSaldoFavorImp(BigDecimal saldoFavorImp) {
        this.saldoFavorImp = saldoFavorImp;
    }

    public BigDecimal getSaldoFavorImp() {
        return saldoFavorImp;
    }
}
