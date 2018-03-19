/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317;


import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author  Alfredo Ramirez
 * @since   06/08/2013
 *
 */
@XmlType(propOrder =
         { "numeroOperacion", "tdiepco1", "fFperceh1", "nC180218", "iC105223", "dC105736", "dC105246",
           "iC13109205", "iC13109305", "iC13109405" })
public class ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO implements Serializable {

    @SuppressWarnings("compatibility:4042200130782731780")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String cRfceeog1a;
    private String cRfceeog1b;
    private int nPeriodo1;
    private int nEjercic1;

    //*****  Salidas  *****
    private BigDecimal numeroOperacion;
    private int tdiepco1;
    private Date fFperceh1;
    private int nC180218;
    private String iC105223;
    private String dC105736;
    private BigDecimal dC105246;
    private BigDecimal iC13109205;
    private BigDecimal iC13109305;
    private BigDecimal iC13109405;

    public void setCRfceeog1a(String cRfceeog1a) {
        this.cRfceeog1a = cRfceeog1a;
    }

    @XmlTransient
    public String getCRfceeog1a() {
        return cRfceeog1a;
    }

    public void setCRfceeog1b(String cRfceeog1b) {
        this.cRfceeog1b = cRfceeog1b;
    }

    @XmlTransient
    public String getCRfceeog1b() {
        return cRfceeog1b;
    }

    public void setNPeriodo1(int nPeriodo1) {
        this.nPeriodo1 = nPeriodo1;
    }

    @XmlTransient
    public int getNPeriodo1() {
        return nPeriodo1;
    }

    public void setNEjercic1(int nEjercic1) {
        this.nEjercic1 = nEjercic1;
    }

    @XmlTransient
    public int getNEjercic1() {
        return nEjercic1;
    }

    public void setNumeroOperacion(BigDecimal numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public BigDecimal getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setTdiepco1(int tdiepco1) {
        this.tdiepco1 = tdiepco1;
    }

    public int getTdiepco1() {
        return tdiepco1;
    }

    public void setFFperceh1(Date fFperceh1) {
        this.fFperceh1 = new Date(fFperceh1.getTime());
    }

    public Date getFFperceh1() {
        return new Date(fFperceh1.getTime());
    }

    public void setNC180218(int nC180218) {
        this.nC180218 = nC180218;
    }

    public int getNC180218() {
        return nC180218;
    }

    public void setIC105223(String iC105223) {
        this.iC105223 = iC105223;
    }

    public String getIC105223() {
        return iC105223;
    }

    public void setDC105736(String dC105736) {
        this.dC105736 = dC105736;
    }

    public String getDC105736() {
        return dC105736;
    }

    public void setDC105246(BigDecimal dC105246) {
        this.dC105246 = dC105246;
    }

    public BigDecimal getDC105246() {
        return dC105246;
    }

    public void setIC13109205(BigDecimal iC13109205) {
        this.iC13109205 = iC13109205;
    }

    public BigDecimal getIC13109205() {
        return iC13109205;
    }

    public void setIC13109305(BigDecimal iC13109305) {
        this.iC13109305 = iC13109305;
    }

    public BigDecimal getIC13109305() {
        return iC13109305;
    }

    public void setIC13109405(BigDecimal iC13109405) {
        this.iC13109405 = iC13109405;
    }

    public BigDecimal getIC13109405() {
        return iC13109405;
    }

    public String getParameterReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("c_rfceeog1a:");
        sb.append(this.getCRfceeog1a());
        sb.append(", ");
        
        sb.append("c_rfceeog1b:");
        sb.append(this.getCRfceeog1b());
        sb.append(", ");
        
        sb.append("n_periodo1:");
        sb.append(this.getNPeriodo1());
        
        sb.append("n_ejercic1:");
        sb.append(this.getNEjercic1());
        
        return sb.toString();
    }

}
