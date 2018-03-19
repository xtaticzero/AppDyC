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
public class ConsultarDeterminacionImpuestoForma2a2DTO implements Serializable {

    @SuppressWarnings("compatibility:-1269064697817718167")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private int cDecCplearv1;
    private int iapidne1;
    private int iapfdne1;
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;

    //*****  Salidas  *****
    private int nEjercicio;
    private int cNPeriodo;
    private String tdiepco1;
    private Date fperceh1;
    private String nNumeroOperacion;
    private String forma;
    private BigDecimal i111021;
    private BigDecimal i111024;

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public int getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setIapidne1(int iapidne1) {
        this.iapidne1 = iapidne1;
    }

    @XmlTransient
    public int getIapidne1() {
        return iapidne1;
    }

    public void setIapfdne1(int iapfdne1) {
        this.iapfdne1 = iapfdne1;
    }

    @XmlTransient
    public int getIapfdne1() {
        return iapfdne1;
    }

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

    public void setNNumeroOperacion(String nNumeroOperacion) {
        this.nNumeroOperacion = nNumeroOperacion;
    }

    public String getNNumeroOperacion() {
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
        
        sb.append("rfc:").append(this.getRfceeog1()).append(", ");
        sb.append("rfc1:").append(this.getRfceeog2()).append(", ");
        sb.append("rfc2:").append(this.getRfceeog3()).append(", ");
        sb.append("iapidne1:").append(this.getIapidne1()).append(", ");
        sb.append("iapfdne1:").append(this.getIapfdne1()).append(", ");
        sb.append("cDecCplearv1:").append(this.getCDecCplearv1());
        
        return sb.toString();
    }

}
