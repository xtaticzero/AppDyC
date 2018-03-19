package mx.gob.sat.siat.dyc.avisocomp.vo;

import java.io.Serializable;

public class DatosOrigenAcuseVO implements Serializable{

    @SuppressWarnings("compatibility:3282934687142499801")
    private static final long serialVersionUID = 1L;
    
    private String descOrigenSaldo;
    private String descConceptoOrigen;
    private String descTipoPeriodo;
    private String descEjercicio;
    private String numControl;
    private String descPeriodo;
    private String numOperacion;
    private String descTipoDeclaracion;
    private String fechaPresentOrigen;
    private String cantidadCompensa;
    private String saldoAFavor;
    
    public DatosOrigenAcuseVO() {
        super();
    }


    public void setDescOrigenSaldo(String descOrigenSaldo) {
        this.descOrigenSaldo = descOrigenSaldo;
    }

    public String getDescOrigenSaldo() {
        return descOrigenSaldo;
    }

    public void setDescConceptoOrigen(String descConceptoOrigen) {
        this.descConceptoOrigen = descConceptoOrigen;
    }

    public String getDescConceptoOrigen() {
        return descConceptoOrigen;
    }

    public void setDescTipoPeriodo(String descTipoPeriodo) {
        this.descTipoPeriodo = descTipoPeriodo;
    }

    public String getDescTipoPeriodo() {
        return descTipoPeriodo;
    }

    public void setDescEjercicio(String descEjercicio) {
        this.descEjercicio = descEjercicio;
    }

    public String getDescEjercicio() {
        return descEjercicio;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setDescPeriodo(String descPeriodo) {
        this.descPeriodo = descPeriodo;
    }

    public String getDescPeriodo() {
        return descPeriodo;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setDescTipoDeclaracion(String descTipoDeclaracion) {
        this.descTipoDeclaracion = descTipoDeclaracion;
    }

    public String getDescTipoDeclaracion() {
        return descTipoDeclaracion;
    }


    public void setFechaPresentOrigen(String fechaPresentOrigen) {
        this.fechaPresentOrigen = fechaPresentOrigen;
    }

    public String getFechaPresentOrigen() {
        return fechaPresentOrigen;
    }

    public void setSaldoAFavor(String saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public String getSaldoAFavor() {
        return saldoAFavor;
    }

    public void setCantidadCompensa(String cantidadCompensa) {
        this.cantidadCompensa = cantidadCompensa;
    }

    public String getCantidadCompensa() {
        return cantidadCompensa;
    }
}
