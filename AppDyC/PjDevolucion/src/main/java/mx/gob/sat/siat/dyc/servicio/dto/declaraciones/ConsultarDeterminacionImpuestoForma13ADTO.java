/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder =
         { "cNPeriodo", "nEjercicio", "tdiepco1", "fperceh1", "nNumeroOperacion", "forma", "i111021", "i111024" })
public class ConsultarDeterminacionImpuestoForma13ADTO implements Serializable {

    @SuppressWarnings("compatibility:8283513416877531924")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;

    //*****  I/O  *****
    private int cNPeriodo;
    private int nEjercicio;

    //*****  Salidas  *****
    private String tdiepco1;
    private Date fperceh1;
    private BigDecimal nNumeroOperacion;
    private String forma;
    private BigDecimal i111021;
    private BigDecimal i111024;

    public void setRfceeog1(String rfceeog1) {
        this.rfceeog1 = rfceeog1;
    }

    @XmlTransient
    public String getRfceeog1() {
        return rfceeog1;
    }

    public void setRfceeog2(String rfceeog2) {
        this.rfceeog2 = rfceeog2;
    }

    @XmlTransient
    public String getRfceeog2() {
        return rfceeog2;
    }

    public void setRfceeog3(String rfceeog3) {
        this.rfceeog3 = rfceeog3;
    }

    @XmlTransient
    public String getRfceeog3() {
        return rfceeog3;
    }

    public void setCNPeriodo(int cNPeriodo) {
        this.cNPeriodo = cNPeriodo;
    }

    public int getCNPeriodo() {
        return cNPeriodo;
    }

    public void setNEjercicio(int nEjercicio) {
        this.nEjercicio = nEjercicio;
    }

    public int getNEjercicio() {
        return nEjercicio;
    }

    public void setTdiepco1(String tdiepco1) {
        this.tdiepco1 = tdiepco1;
    }

    public String getTdiepco1() {
        return tdiepco1;
    }

    public void setFperceh1(Date fperceh1) {
        if (fperceh1 != null) {
            this.fperceh1 = (Date)fperceh1.clone();
        } else {
            this.fperceh1 = null;
        }   
    }

    public Date getFperceh1() {
        if (fperceh1 != null) {
            return (Date)fperceh1.clone();
        } else {
            return null;
        }   
    }

    public void setNNumeroOperacion(BigDecimal nNumeroOperacion) {
        this.nNumeroOperacion = nNumeroOperacion;
    }

    public BigDecimal getNNumeroOperacion() {
        return nNumeroOperacion;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getForma() {
        return forma;
    }

    public void setI111021(BigDecimal i111021) {
        this.i111021 = i111021;
    }

    public BigDecimal getI111021() {
        return i111021;
    }

    public void setI111024(BigDecimal i111024) {
        this.i111024 = i111024;
    }

    public BigDecimal getI111024() {
        return i111024;
    }

    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("rfceeog1:").append(this.getRfceeog1()).append(", ");
        sb.append("rfceeog2:").append(this.getRfceeog2()).append(", ");
        sb.append("rfceeog3:").append(this.getRfceeog3()).append(", ");
        sb.append("cNPeriodo:").append(this.getCNPeriodo()).append(", ");
        sb.append("nEjercicio:").append(this.getNEjercicio());
             
        return sb.toString();
    }
}
