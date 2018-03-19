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


/**
 *
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
@XmlType(propOrder = { "cNPeriodo", "tdiepco1", "fperceh1", "nNumeroOperacion", "forma", "i111021", "i111024" })
public class ConsultarDeterminacionImpuestoCdiDpdifAnioDTO implements Serializable {

    @SuppressWarnings("compatibility:-9033846436010926293")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private int cStiCcloanv1;
    private int nEjercicio;

    //*****  I/O  *****
    private int cNPeriodo;

    //*****  Salidas  *****
    private int tdiepco1;
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

    public void setCStiCcloanv1(int cStiCcloanv1) {
        this.cStiCcloanv1 = cStiCcloanv1;
    }

    @XmlTransient
    public int getCStiCcloanv1() {
        return cStiCcloanv1;
    }

    public void setNEjercicio(int nEjercicio) {
        this.nEjercicio = nEjercicio;
    }

    @XmlTransient
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
        sb.append(this.getRfceeog1());
        sb.append(", ");
        
        sb.append("rfceeog2:");
        sb.append(this.getRfceeog2());
        sb.append(", ");
        
        sb.append("rfceeog3:");
        sb.append(this.getRfceeog3());
        sb.append(", ");
        
        sb.append("cNPeriodo:");
        sb.append(this.getCStiCcloanv1());
        sb.append(", ");
        
        sb.append("nEjercicio:");
        sb.append(this.getNEjercicio());
                    
        return sb.toString();
    }
}
