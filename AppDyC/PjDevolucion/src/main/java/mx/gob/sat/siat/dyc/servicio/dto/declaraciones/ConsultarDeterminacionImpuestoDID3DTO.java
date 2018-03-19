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
         { "nEjercicio", "cNPeriodo", "tdiepco1", "fperceh1", "nNumeroOperacion", "forma", "i111021", "i111024" })
public class ConsultarDeterminacionImpuestoDID3DTO implements Serializable {

    @SuppressWarnings("compatibility:-1228305817599665543")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String dDecRfceeog1;
    private String dDecRfceeog2;
    private String dDecRfceeog3;
    private int cDecCplearv1;
    private int nDecEjercic1;
        
    //*****  Salidas  *****
    private int nEjercicio;
    private int cNPeriodo;
    private int tdiepco1;
    private Date fperceh1;
    private BigDecimal nNumeroOperacion;
    private String forma;
    private BigDecimal i111021;
    private BigDecimal i111024;

    public void setDDecRfceeog1(String dDecRfceeog1) {
        this.dDecRfceeog1 = dDecRfceeog1;
    }

    @XmlTransient
    public String getDDecRfceeog1() {
        return dDecRfceeog1;
    }

    public void setDDecRfceeog2(String dDecRfceeog2) {
        this.dDecRfceeog2 = dDecRfceeog2;
    }

    @XmlTransient
    public String getDDecRfceeog2() {
        return dDecRfceeog2;
    }

    public void setDDecRfceeog3(String dDecRfceeog3) {
        this.dDecRfceeog3 = dDecRfceeog3;
    }

    @XmlTransient
    public String getDDecRfceeog3() {
        return dDecRfceeog3;
    }

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public int getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setNDecEjercic1(int nDecEjercic1) {
        this.nDecEjercic1 = nDecEjercic1;
    }

    @XmlTransient
    public int getNDecEjercic1() {
        return nDecEjercic1;
    }

    public void setNEjercicio(int nEjercicio) {
        this.nEjercicio = nEjercicio;
    }

    public int getNEjercicio() {
        return nEjercicio;
    }

    public void setCNPeriodo(int cNPeriodo) {
        this.cNPeriodo = cNPeriodo;
    }

    public int getCNPeriodo() {
        return cNPeriodo;
    }

    public void setTdiepco1(int tdiepco1) {
        this.tdiepco1 = tdiepco1;
    }

    public int getTdiepco1() {
        return tdiepco1;
    }

    public void setFperceh1(Date fperceh1) {
        this.fperceh1 = new Date(fperceh1.getTime());
    }

    public Date getFperceh1() {
        return new Date(fperceh1.getTime());
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
        
        sb.append("rfceeog1:");
        sb.append(this.getDDecRfceeog1());
        sb.append(", ");
        
        sb.append("rfceeog2:");
        sb.append(this.getDDecRfceeog2());
        sb.append(", ");
        
        sb.append("rfceeog3:");
        sb.append(this.getDDecRfceeog3());
        sb.append(", ");
        
        sb.append("c_n_periodo:");
        sb.append(this.getCDecCplearv1());
        sb.append(", ");
        
        sb.append("n_ejercicio:");
        sb.append(this.getNDecEjercic1());
                
        return sb.toString();
    }

}
