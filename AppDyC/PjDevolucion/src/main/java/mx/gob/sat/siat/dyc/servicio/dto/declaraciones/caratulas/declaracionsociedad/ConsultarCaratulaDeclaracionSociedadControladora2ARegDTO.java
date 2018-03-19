/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad;

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
@XmlType(propOrder =
         { "vNNumeroOperacion", "vFechaCausacion", "vFperceh1", "vImpidee1", "vIapidne1", "vImpfdee1",
           "vIapfdne1", "vTdiepco1", "vC118228", "vC110001", "vC120007", "vC130004", "vI201010",
           "vC100025", "vC100009", "vC100013", "vI201011", "vC950018", "vI201012", "vI201013",
           "vC950047", "vC950048", "vC950049", "vC950052", "vC950022", "vC950019", "vC950020",
           "vI201014", "vI201015", "vC205004", "vI201016", "vI201017", "vI201018", "vI201019",
           "vC910004", "vC900000", "vC205001" })
public class ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO implements Serializable {

    @SuppressWarnings("compatibility:-736773779527119760")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private int cNPeriodo;
    private int nEjercicio;
    private int nRenglon;
    private int anio;
    
    //*****  Salidas  *****
    private BigDecimal vNNumeroOperacion;
    private String vFechaCausacion;
    private Date vFperceh1;
    private int vImpidee1;
    private int vIapidne1;
    private int vImpfdee1;
    private int vIapfdne1;
    private int vTdiepco1;
    private String vC118228;
    private BigDecimal vC110001;
    private BigDecimal vC120007;
    private BigDecimal vC130004;
    private BigDecimal vI201010;
    private BigDecimal vC100025;
    private BigDecimal vC100009;
    private BigDecimal vC100013;
    private String vI201011;
    private BigDecimal vC950018;
    private BigDecimal vI201012;
    private BigDecimal vI201013;
    private BigDecimal vC950047;
    private BigDecimal vC950048;
    private BigDecimal vC950049;
    private BigDecimal vC950052;
    private BigDecimal vC950022;
    private BigDecimal vC950019;
    private BigDecimal vC950020;
    private String vI201014;
    private BigDecimal vI201015;
    private BigDecimal vI205004;
    private BigDecimal vI201016;
    private BigDecimal vI201017;
    private String vI201018;
    private String vI201019;
    private String vC910004;
    private BigDecimal vC900000;
    private int vC205001;

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
    
    public void setNRenglon(int nRenglon) {
        this.nRenglon = nRenglon;
    }

    @XmlTransient
    public int getNRenglon() {
        return nRenglon;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @XmlTransient
    public int getAnio() {
        return anio;
    }

    public void setVNNumeroOperacion(BigDecimal vNNumeroOperacion) {
        this.vNNumeroOperacion = vNNumeroOperacion;
    }

    public BigDecimal getVNNumeroOperacion() {
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

    public void setVTdiepco1(int vTdiepco1) {
        this.vTdiepco1 = vTdiepco1;
    }

    public int getVTdiepco1() {
        return vTdiepco1;
    }

    public void setVC118228(String vC118228) {
        this.vC118228 = vC118228;
    }

    public String getVC118228() {
        return vC118228;
    }

    public void setVC110001(BigDecimal vC110001) {
        this.vC110001 = vC110001;
    }

    public BigDecimal getVC110001() {
        return vC110001;
    }

    public void setVC120007(BigDecimal vC120007) {
        this.vC120007 = vC120007;
    }

    public BigDecimal getVC120007() {
        return vC120007;
    }

    public void setVC130004(BigDecimal vC130004) {
        this.vC130004 = vC130004;
    }

    public BigDecimal getVC130004() {
        return vC130004;
    }

    public void setVI201010(BigDecimal vI201010) {
        this.vI201010 = vI201010;
    }

    public BigDecimal getVI201010() {
        return vI201010;
    }

    public void setVC100025(BigDecimal vC100025) {
        this.vC100025 = vC100025;
    }

    public BigDecimal getVC100025() {
        return vC100025;
    }

    public void setVC100009(BigDecimal vC100009) {
        this.vC100009 = vC100009;
    }

    public BigDecimal getVC100009() {
        return vC100009;
    }

    public void setVC100013(BigDecimal vC100013) {
        this.vC100013 = vC100013;
    }

    public BigDecimal getVC100013() {
        return vC100013;
    }

    public void setVI201011(String vI201011) {
        this.vI201011 = vI201011;
    }

    public String getVI201011() {
        return vI201011;
    }

    public void setVC950018(BigDecimal vC950018) {
        this.vC950018 = vC950018;
    }

    public BigDecimal getVC950018() {
        return vC950018;
    }

    public void setVI201012(BigDecimal vI201012) {
        this.vI201012 = vI201012;
    }

    public BigDecimal getVI201012() {
        return vI201012;
    }

    public void setVI201013(BigDecimal vI201013) {
        this.vI201013 = vI201013;
    }

    public BigDecimal getVI201013() {
        return vI201013;
    }

    public void setVC950047(BigDecimal vC950047) {
        this.vC950047 = vC950047;
    }

    public BigDecimal getVC950047() {
        return vC950047;
    }

    public void setVC950048(BigDecimal vC950048) {
        this.vC950048 = vC950048;
    }

    public BigDecimal getVC950048() {
        return vC950048;
    }

    public void setVC950049(BigDecimal vC950049) {
        this.vC950049 = vC950049;
    }

    public BigDecimal getVC950049() {
        return vC950049;
    }

    public void setVC950052(BigDecimal vC950052) {
        this.vC950052 = vC950052;
    }

    public BigDecimal getVC950052() {
        return vC950052;
    }

    public void setVC950022(BigDecimal vC950022) {
        this.vC950022 = vC950022;
    }

    public BigDecimal getVC950022() {
        return vC950022;
    }

    public void setVC950019(BigDecimal vC950019) {
        this.vC950019 = vC950019;
    }

    public BigDecimal getVC950019() {
        return vC950019;
    }

    public void setVC950020(BigDecimal vC950020) {
        this.vC950020 = vC950020;
    }

    public BigDecimal getVC950020() {
        return vC950020;
    }

    public void setVI201014(String vI201014) {
        this.vI201014 = vI201014;
    }

    public String getVI201014() {
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

    public void setVI201019(String vI201019) {
        this.vI201019 = vI201019;
    }

    public String getVI201019() {
        return vI201019;
    }

    public void setVC910004(String vC910004) {
        this.vC910004 = vC910004;
    }

    public String getVC910004() {
        return vC910004;
    }

    public void setVC900000(BigDecimal vC900000) {
        this.vC900000 = vC900000;
    }

    public BigDecimal getVC900000() {
        return vC900000;
    }

    public void setVC205001(int vC205001) {
        this.vC205001 = vC205001;
    }

    public int getVC205001() {
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
        
        sb.append("cNPeriodo:");
        sb.append(this.getCNPeriodo());
        sb.append(", ");
        
        sb.append("nEjercicio:"); 
        sb.append(this.getNEjercicio());
        sb.append(", ");
        
        sb.append("nRenglon:");
        sb.append(this.getNRenglon()); 
        sb.append(", ");
        
        sb.append("anio:"); 
        sb.append(this.getAnio());
        
        return sb.toString();
    }

    public void setCNPeriodo(int cNPeriodo) {
        this.cNPeriodo = cNPeriodo;
    }
    @XmlTransient
    public int getCNPeriodo() {
        return cNPeriodo;
    }

    public void setNEjercicio(int nEjercicio) {
        this.nEjercicio = nEjercicio;
    }
    
    @XmlTransient
    public int getNEjercicio() {
        return nEjercicio;
    }
}
