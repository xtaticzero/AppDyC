/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 10/07/2013
 *
 */
 @XmlType(propOrder = { "vNNumeroOperacion", "vFechaCausacion",  "vFperceh1", "vImpidee1", "vIapidne1", "vImpfdee1",
                        "vIapfdne1", "vTdiepco1", "vC118228", "vC110001", "vC120007", "vC130004", "vI201010",
                        "vC100025", "vC100009", "vC100013", "vI201011", "vC950018", "vI201012", "vI201013",
                        "vC950047", "vC950048", "vC950049", "vC950052", "vC950022", "vC950019", "vC950020",
                        "vI201014", "vI201015", "vI205004", "vI201016", "vI201017", "vI201018", "vI201019",
                        "vC910004", "vC900000", "vC205001"})
public class ConsultarCaratulaDeclaracionMoral2Forma2ADTO implements Serializable {
    
    @SuppressWarnings("compatibility:-2738345913632860052")
    private static final long serialVersionUID = 1L;
    
    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private int iapidne1;
    private int iapfdne1;
    private int cDecCplearv1;
    
    //*****  Salidas  *****
    private String vNNumeroOperacion;
    private String vFechaCausacion;
    private Date vFperceh1;
    private int vImpidee1;
    private int vIapidne1;
    private int vImpfdee1;
    private int vIapfdne1;
    private String vTdiepco1;
    private String vC118228;
    private String vC110001;
    private int vC120007;
    private int vC130004;
    private int vI201010;
    private int vC100025;
    private int vC100009;
    private int vC100013;
    private BigDecimal vI201011;
    private int vC950018;
    private BigDecimal vI201012;
    private int vI201013;
    private int vC950047;
    private int vC950048;
    private int vC950049;
    private BigDecimal vC950052;
    private int vC950022;
    private int vC950019;
    private int vC950020;
    private BigDecimal vI201014;
    private BigDecimal vI201015;
    private BigDecimal vI205004;
    private BigDecimal vI201016;
    private BigDecimal vI201017;
    private String vI201018;
    private int vI201019;
    private BigDecimal vC910004;
    private int vC900000;
    private String vC205001;

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

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public int getCDecCplearv1() {
        return cDecCplearv1;
    }
    
    public void setVNNumeroOperacion(String vNNumeroOperacion) {
        this.vNNumeroOperacion = vNNumeroOperacion;
    }

    public String getVNNumeroOperacion() {
        return vNNumeroOperacion;
    }

    public void setVFechaCausacion(String vFechaCausacion) {
        this.vFechaCausacion = vFechaCausacion;
    }

    public String getVFechaCausacion() {
        return vFechaCausacion;
    }

    public void setVFperceh1(Date vFperceh1) {
        this.vFperceh1 = new Date(vFperceh1.getTime());
    }

    public Date getVFperceh1() {
        return new Date(vFperceh1.getTime());
    }

    public void setVImpidee1(int vImpidee1) {
        this.vImpidee1 = vImpidee1;
    }

    public int getVImpidee1() {
        return vImpidee1;
    }

    public void setVIapidne1(int vIapidne1) {
        this.vIapidne1 = vIapidne1;
    }

    public int getVIapidne1() {
        return vIapidne1;
    }

    public void setVImpfdee1(int vImpfdee1) {
        this.vImpfdee1 = vImpfdee1;
    }

    public int getVImpfdee1() {
        return vImpfdee1;
    }

    public void setVIapfdne1(int vIapfdne1) {
        this.vIapfdne1 = vIapfdne1;
    }

    public int getVIapfdne1() {
        return vIapfdne1;
    }

    public void setVTdiepco1(String vTdiepco1) {
        this.vTdiepco1 = vTdiepco1;
    }

    public String getVTdiepco1() {
        return vTdiepco1;
    }

    public void setVC118228(String vC118228) {
        this.vC118228 = vC118228;
    }

    public String getVC118228() {
        return vC118228;
    }

    public void setVC110001(String vC110001) {
        this.vC110001 = vC110001;
    }

    public String getVC110001() {
        return vC110001;
    }

    public void setVC120007(int vC120007) {
        this.vC120007 = vC120007;
    }

    public int getVC120007() {
        return vC120007;
    }

    public void setVC130004(int vC130004) {
        this.vC130004 = vC130004;
    }

    public int getVC130004() {
        return vC130004;
    }

    public void setVI201010(int vI201010) {
        this.vI201010 = vI201010;
    }

    public int getVI201010() {
        return vI201010;
    }

    public void setVC100025(int vC100025) {
        this.vC100025 = vC100025;
    }

    public int getVC100025() {
        return vC100025;
    }

    public void setVC100009(int vC100009) {
        this.vC100009 = vC100009;
    }

    public int getVC100009() {
        return vC100009;
    }

    public void setVC100013(int vC100013) {
        this.vC100013 = vC100013;
    }

    public int getVC100013() {
        return vC100013;
    }

    public void setVI201011(BigDecimal vI201011) {
        this.vI201011 = vI201011;
    }

    public BigDecimal getVI201011() {
        return vI201011;
    }

    public void setVC950018(int vC950018) {
        this.vC950018 = vC950018;
    }

    public int getVC950018() {
        return vC950018;
    }

    public void setVI201012(BigDecimal vI201012) {
        this.vI201012 = vI201012;
    }

    public BigDecimal getVI201012() {
        return vI201012;
    }

    public void setVI201013(int vI201013) {
        this.vI201013 = vI201013;
    }

    public int getVI201013() {
        return vI201013;
    }

    public void setVC950047(int vC950047) {
        this.vC950047 = vC950047;
    }

    public int getVC950047() {
        return vC950047;
    }

    public void setVC950048(int vC950048) {
        this.vC950048 = vC950048;
    }

    public int getVC950048() {
        return vC950048;
    }

    public void setVC950049(int vC950049) {
        this.vC950049 = vC950049;
    }

    public int getVC950049() {
        return vC950049;
    }

    public void setVC950052(BigDecimal vC950052) {
        this.vC950052 = vC950052;
    }

    public BigDecimal getVC950052() {
        return vC950052;
    }

    public void setVC950022(int vC950022) {
        this.vC950022 = vC950022;
    }

    public int getVC950022() {
        return vC950022;
    }

    public void setVC950019(int vC950019) {
        this.vC950019 = vC950019;
    }

    public int getVC950019() {
        return vC950019;
    }

    public void setVC950020(int vC950020) {
        this.vC950020 = vC950020;
    }

    public int getVC950020() {
        return vC950020;
    }

    public void setVI201014(BigDecimal vI201014) {
        this.vI201014 = vI201014;
    }

    public BigDecimal getVI201014() {
        return vI201014;
    }

    public void setVI201015(BigDecimal vI201015) {
        this.vI201015 = vI201015;
    }

    public BigDecimal getVI201015() {
        return vI201015;
    }

    public void setVI205004(BigDecimal vI205004) {
        this.vI205004 = vI205004;
    }

    public BigDecimal getVI205004() {
        return vI205004;
    }

    public void setVI201016(BigDecimal vI201016) {
        this.vI201016 = vI201016;
    }

    public BigDecimal getVI201016() {
        return vI201016;
    }

    public void setVI201017(BigDecimal vI201017) {
        this.vI201017 = vI201017;
    }

    public BigDecimal getVI201017() {
        return vI201017;
    }

    public void setVI201018(String vI201018) {
        this.vI201018 = vI201018;
    }

    public String getVI201018() {
        return vI201018;
    }

    public void setVI201019(int vI201019) {
        this.vI201019 = vI201019;
    }

    public int getVI201019() {
        return vI201019;
    }

    public void setVC910004(BigDecimal vC910004) {
        this.vC910004 = vC910004;
    }

    public BigDecimal getVC910004() {
        return vC910004;
    }

    public void setVC900000(int vC900000) {
        this.vC900000 = vC900000;
    }

    public int getVC900000() {
        return vC900000;
    }

    public void setVC205001(String vC205001) {
        this.vC205001 = vC205001;
    }

    public String getVC205001() {
        return vC205001;
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
        
        sb.append("iapidne1:");
        sb.append(this.getIapidne1());
        sb.append(", ");
        
        sb.append("iapfdne1:");
        sb.append(this.getIapfdne1());
        sb.append(", ");
        
        sb.append("c_dec_cplearv1:");
        sb.append(this.getCDecCplearv1());
        
        return sb.toString();
    }
}
