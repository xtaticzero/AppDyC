package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


public class ConsultarDeclaracionISRMoralDTO implements Serializable{
    
    @SuppressWarnings("compatibility:1178640709407092388")
    private static final long serialVersionUID = 1L;

    public ConsultarDeclaracionISRMoralDTO() {
        super();
    }
    /**Entradas**/
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private int iapidne1;
    private int iapfdne1;
    private int cDecCplearv1;
    
    /**Salidas**/
    private BigDecimal numeroOperacion;
    private int vIapidne1;
    private int vCDecCpl;
    private String vTdiepco1;
    private Date vFperceh1;
    private BigDecimal i111923;
    private BigDecimal i121019;
    private BigDecimal vI800601;
    private String vI800701;
    private String vP6a118216;
    private BigDecimal vI111000;
    private BigDecimal vI111001;
    private BigDecimal vI3110201;
    private BigDecimal vI3110301;
    private BigDecimal vI111003;
    private BigDecimal vI111004;
    private BigDecimal vI111005;
    private BigDecimal vI111006;
    private BigDecimal vI111007;
    private String vI3110601;
    private String vI3110701;
    private BigDecimal vI111010;
    private BigDecimal vI111009;
    private String vI111008;
    private BigDecimal vI3105001;
    private String vI3110801;
    private BigDecimal vI3110501;
    private String vI3110901;
    private BigDecimal vI3105301;
    private String vI111013;
    private String vI111014;
    private BigDecimal vI111016;
    private BigDecimal vI111015;
    private BigDecimal vI111017;
    private String vI3111101;
    private BigDecimal vI121011;
    private String vI111011;
    private String vI111018;
    private String vI111019;
    private BigDecimal vI3106501;
    private BigDecimal vI3106601;
    private String vI3111201;
    private BigDecimal vI3106701;
    private BigDecimal vI111021;
    private BigDecimal vI111023;
    private String v3111801;
    private String vI111024;
    private BigDecimal vI3107201;
    private BigDecimal vI3106801;
    private BigDecimal vI111058;
    private String vI111020;
    private String vI111904;
    
    public void setRfceeog1(String rfceeog1) {
        this.rfceeog1 = rfceeog1;
    }

    public String getRfceeog1() {
        return rfceeog1;
    }

    public void setIapidne1(int iapidne1) {
        this.iapidne1 = iapidne1;
    }

    public int getIapidne1() {
        return iapidne1;
    }

    public void setIapfdne1(int iapfdne1) {
        this.iapfdne1 = iapfdne1;
    }

    public int getIapfdne1() {
        return iapfdne1;
    }

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    public int getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setNumeroOperacion(BigDecimal numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public BigDecimal getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setVIapidne1(int vIapidne1) {
        this.vIapidne1 = vIapidne1;
    }

    public int getVIapidne1() {
        return vIapidne1;
    }

    public void setVCDecCpl(int vCDecCpl) {
        this.vCDecCpl = vCDecCpl;
    }

    public int getVCDecCpl() {
        return vCDecCpl;
    }

    public void setVTdiepco1(String vTdiepco1) {
        this.vTdiepco1 = vTdiepco1;
    }

    public String getVTdiepco1() {
        return vTdiepco1;
    }

    public void setVFperceh1(Date vFperceh1) {
        this.vFperceh1 = new Date(vFperceh1.getTime());
    }

    public Date getVFperceh1() {
        return new Date(vFperceh1.getTime());
    }

    public void setI111923(BigDecimal i111923) {
        this.i111923 = i111923;
    }

    public BigDecimal getI111923() {
        return i111923;
    }

    public void setI121019(BigDecimal i121019) {
        this.i121019 = i121019;
    }

    public BigDecimal getI121019() {
        return i121019;
    }

    public void setVI800601(BigDecimal vI800601) {
        this.vI800601 = vI800601;
    }

    public BigDecimal getVI800601() {
        return vI800601;
    }

    public void setVI800701(String vI800701) {
        this.vI800701 = vI800701;
    }

    public String getVI800701() {
        return vI800701;
    }

    public void setVP6a118216(String vP6a118216) {
        this.vP6a118216 = vP6a118216;
    }

    public String getVP6a118216() {
        return vP6a118216;
    }

    public void setVI111000(BigDecimal vI111000) {
        this.vI111000 = vI111000;
    }

    public BigDecimal getVI111000() {
        return vI111000;
    }

    public void setVI111001(BigDecimal vI111001) {
        this.vI111001 = vI111001;
    }

    public BigDecimal getVI111001() {
        return vI111001;
    }

    public void setVI3110201(BigDecimal vI3110201) {
        this.vI3110201 = vI3110201;
    }

    public BigDecimal getVI3110201() {
        return vI3110201;
    }

    public void setVI3110301(BigDecimal vI3110301) {
        this.vI3110301 = vI3110301;
    }

    public BigDecimal getVI3110301() {
        return vI3110301;
    }

    public void setVI111003(BigDecimal vI111003) {
        this.vI111003 = vI111003;
    }

    public BigDecimal getVI111003() {
        return vI111003;
    }

    public void setVI111004(BigDecimal vI111004) {
        this.vI111004 = vI111004;
    }

    public BigDecimal getVI111004() {
        return vI111004;
    }

    public void setVI111005(BigDecimal vI111005) {
        this.vI111005 = vI111005;
    }

    public BigDecimal getVI111005() {
        return vI111005;
    }

    public void setVI111006(BigDecimal vI111006) {
        this.vI111006 = vI111006;
    }

    public BigDecimal getVI111006() {
        return vI111006;
    }

    public void setVI111007(BigDecimal vI111007) {
        this.vI111007 = vI111007;
    }

    public BigDecimal getVI111007() {
        return vI111007;
    }

    public void setVI3110601(String vI3110601) {
        this.vI3110601 = vI3110601;
    }

    public String getVI3110601() {
        return vI3110601;
    }

    public void setVI3110701(String vI3110701) {
        this.vI3110701 = vI3110701;
    }

    public String getVI3110701() {
        return vI3110701;
    }

    public void setVI111010(BigDecimal vI111010) {
        this.vI111010 = vI111010;
    }

    public BigDecimal getVI111010() {
        return vI111010;
    }

    public void setVI111009(BigDecimal vI111009) {
        this.vI111009 = vI111009;
    }

    public BigDecimal getVI111009() {
        return vI111009;
    }

    public void setVI111008(String vI111008) {
        this.vI111008 = vI111008;
    }

    public String getVI111008() {
        return vI111008;
    }

    public void setVI3105001(BigDecimal vI3105001) {
        this.vI3105001 = vI3105001;
    }

    public BigDecimal getVI3105001() {
        return vI3105001;
    }

    public void setVI3110801(String vI3110801) {
        this.vI3110801 = vI3110801;
    }

    public String getVI3110801() {
        return vI3110801;
    }

    public void setVI3110501(BigDecimal vI3110501) {
        this.vI3110501 = vI3110501;
    }

    public BigDecimal getVI3110501() {
        return vI3110501;
    }

    public void setVI3110901(String vI3110901) {
        this.vI3110901 = vI3110901;
    }

    public String getVI3110901() {
        return vI3110901;
    }

    public void setVI3105301(BigDecimal vI3105301) {
        this.vI3105301 = vI3105301;
    }

    public BigDecimal getVI3105301() {
        return vI3105301;
    }

    public void setVI111013(String vI111013) {
        this.vI111013 = vI111013;
    }

    public String getVI111013() {
        return vI111013;
    }

    public void setVI111014(String vI111014) {
        this.vI111014 = vI111014;
    }

    public String getVI111014() {
        return vI111014;
    }

    public void setVI111016(BigDecimal vI111016) {
        this.vI111016 = vI111016;
    }

    public BigDecimal getVI111016() {
        return vI111016;
    }

    public void setVI111015(BigDecimal vI111015) {
        this.vI111015 = vI111015;
    }

    public BigDecimal getVI111015() {
        return vI111015;
    }

    public void setVI111017(BigDecimal vI111017) {
        this.vI111017 = vI111017;
    }

    public BigDecimal getVI111017() {
        return vI111017;
    }

    public void setVI3111101(String vI3111101) {
        this.vI3111101 = vI3111101;
    }

    public String getVI3111101() {
        return vI3111101;
    }

    public void setVI121011(BigDecimal vI121011) {
        this.vI121011 = vI121011;
    }

    public BigDecimal getVI121011() {
        return vI121011;
    }

    public void setVI111011(String vI111011) {
        this.vI111011 = vI111011;
    }

    public String getVI111011() {
        return vI111011;
    }

    public void setVI111018(String vI111018) {
        this.vI111018 = vI111018;
    }

    public String getVI111018() {
        return vI111018;
    }

    public void setVI111019(String vI111019) {
        this.vI111019 = vI111019;
    }

    public String getVI111019() {
        return vI111019;
    }

    public void setVI3106501(BigDecimal vI3106501) {
        this.vI3106501 = vI3106501;
    }

    public BigDecimal getVI3106501() {
        return vI3106501;
    }

    public void setVI3106601(BigDecimal vI3106601) {
        this.vI3106601 = vI3106601;
    }

    public BigDecimal getVI3106601() {
        return vI3106601;
    }

    public void setVI3111201(String vI3111201) {
        this.vI3111201 = vI3111201;
    }

    public String getVI3111201() {
        return vI3111201;
    }

    public void setVI3106701(BigDecimal vI3106701) {
        this.vI3106701 = vI3106701;
    }

    public BigDecimal getVI3106701() {
        return vI3106701;
    }

    public void setVI111021(BigDecimal vI111021) {
        this.vI111021 = vI111021;
    }

    public BigDecimal getVI111021() {
        return vI111021;
    }

    public void setVI111023(BigDecimal vI111023) {
        this.vI111023 = vI111023;
    }

    public BigDecimal getVI111023() {
        return vI111023;
    }

    public void setV3111801(String vI3111801) {
        this.v3111801 = vI3111801;
    }

    public String getV3111801() {
        return v3111801;
    }

    public void setVI111024(String vI111024) {
        this.vI111024 = vI111024;
    }

    public String getVI111024() {
        return vI111024;
    }

    public void setVI3107201(BigDecimal vI3107201) {
        this.vI3107201 = vI3107201;
    }

    public BigDecimal getVI3107201() {
        return vI3107201;
    }

    public void setVI3106801(BigDecimal vI3106801) {
        this.vI3106801 = vI3106801;
    }

    public BigDecimal getVI3106801() {
        return vI3106801;
    }

    public void setVI111058(BigDecimal vI111058) {
        this.vI111058 = vI111058;
    }

    public BigDecimal getVI111058() {
        return vI111058;
    }

    public void setVI111020(String vI111020) {
        this.vI111020 = vI111020;
    }

    public String getVI111020() {
        return vI111020;
    }

    public void setVI111904(String vI111904) {
        this.vI111904 = vI111904;
    }

    public String getVI111904() {
        return vI111904;
    }

    public void setRfceeog2(String rfceeog2) {
        this.rfceeog2 = rfceeog2;
    }

    public String getRfceeog2() {
        return rfceeog2;
    }

    public void setRfceeog3(String rfceeog3) {
        this.rfceeog3 = rfceeog3;
    }

    public String getRfceeog3() {
        return rfceeog3;
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
        
        sb.append("cDecCplearv1:"); 
        sb.append(this.getCDecCplearv1());
        
        return sb.toString();
    }
}
