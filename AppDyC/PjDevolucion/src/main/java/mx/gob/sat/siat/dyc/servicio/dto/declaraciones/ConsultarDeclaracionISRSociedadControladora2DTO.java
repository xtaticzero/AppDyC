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
         { "vNNumeroOperacion", "vIapidne1", "vCplearv1", "vTdiepco1", "vFperceh1", "vI111025", "vI111026", "vI111027",
           "vI111028", "vI111029", "vI3100302", "vI3100402", "vI3110202", "vI3110602", "vI3110302", "vI111030",
           "vI3101702", "vI111031", "vI111032", "vI111033", "vI111034", "vI111035", "vI111036", "vI111037",
           "vI3102402", "vI3102502", "vI3102702", "vI3102802", "vI111038", "vI111039", "vI111040", "vI111041",
           "vI111042", "vI111043", "vI111044", "vI111045", "vI3103702", "vI111003", "vI111004", "vI111046", "vI111005",
           "vI111006", "vI111007", "vI3107302", "vI3107402", "vI3104602", "vI111008", "vI111009", "vI111047",
           "vI111048", "vI3105002", "vI3107502", "vI3110502", "vI3107602", "vI111049", "vI111050", "vI111013",
           "vI111051", "vI111052", "vI111014", "vI111053", "vI111054", "vI111055", "vI111056", "vI111017", "vI111057",
           "vI3110702", "vI3110802", "vI3110902", "vI3106502", "vI3106602", "vI3107702", "vI3111002", "vI111058",
           "vI3108002", "vI3110402", "vI111018", "vI111019", "vI3111202", "vI3107202", "vI111020", "vI111904",
           "vI111021", "vI111022", "vI11109", "vI3111102", "vI111024" })

public class ConsultarDeclaracionISRSociedadControladora2DTO implements Serializable {

    @SuppressWarnings("compatibility:-6972162148519827307")
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
    private int vIapidne1;
    private int vCplearv1;
    private String vTdiepco1;
    private Date vFperceh1;
    private BigDecimal vI111025;
    private String vI111026;
    private BigDecimal vI111027;
    private BigDecimal vI111028;
    private BigDecimal vI111029;
    private String vI3100302;
    private String vI3100402;
    private String vI3110202;
    private String vI3110602;
    private String vI3110302;
    private BigDecimal vI111030;
    private String vI3101702;
    private BigDecimal vI111031;
    private BigDecimal vI111032;
    private BigDecimal vI111033;
    private BigDecimal vI111034;
    private BigDecimal vI111035;
    private BigDecimal vI111036;
    private BigDecimal vI111037;
    private String vI3102402;
    private String vI3102502;
    private String vI3102702;
    private String vI3102802;
    private BigDecimal vI111038;
    private int vI111039;
    private BigDecimal vI111040;
    private BigDecimal vI111041;
    private BigDecimal vI111042;
    private BigDecimal vI111043;
    private BigDecimal vI111044;
    private BigDecimal vI111045;
    private String vI3103702;
    private BigDecimal vI111003;
    private BigDecimal vI111004;
    private BigDecimal vI111046;
    private BigDecimal vI111005;
    private BigDecimal vI111006;
    private BigDecimal vI111007;
    private String vI3107302;
    private String vI3107402;
    private String vI3104602;
    private String vI111008;
    private String vI111009;
    private BigDecimal vI111047;
    private BigDecimal vI111048;
    private String vI3105002;
    private String vI3107502;
    private String vI3110502;
    private String vI3107602;
    private BigDecimal vI111049;
    private BigDecimal vI111050;
    private BigDecimal vI111013;
    private String vI111051;
    private String vI111052;
    private String vI111014;
    private BigDecimal vI111053;
    private BigDecimal vI111054;
    private BigDecimal vI111055;
    private BigDecimal vI111056;
    private int vI111017;
    private BigDecimal vI111057;
    private String vI3110702;
    private String vI3110802;
    private String vI3110902;
    private String vI3106502;
    private String vI3106602;
    private String vI3107702;
    private String vI3111002;
    private String vI111058;
    private String vI3108002;
    private String vI3110402;
    private String vI111018;
    private String vI111019;
    private String vI3111202;
    private String vI3107202;
    private String vI111020;
    private String vI111904;
    private String vI111021;
    private String vI111022;
    private String vI11109;
    private String vI3111102;
    private BigDecimal vI111024;

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

    public void setVIapidne1(int vIapidne1) {
        this.vIapidne1 = vIapidne1;
    }

    public int getVIapidne1() {
        return vIapidne1;
    }

    public void setVCplearv1(int vCplearv1) {
        this.vCplearv1 = vCplearv1;
    }

    public int getVCplearv1() {
        return vCplearv1;
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

    public void setVI111025(BigDecimal vI111025) {
        this.vI111025 = vI111025;
    }

    public BigDecimal getVI111025() {
        return vI111025;
    }

    public void setVI111026(String vI111026) {
        this.vI111026 = vI111026;
    }

    public String getVI111026() {
        return vI111026;
    }

    public void setVI111027(BigDecimal vI111027) {
        this.vI111027 = vI111027;
    }

    public BigDecimal getVI111027() {
        return vI111027;
    }

    public void setVI111028(BigDecimal vI111028) {
        this.vI111028 = vI111028;
    }

    public BigDecimal getVI111028() {
        return vI111028;
    }

    public void setVI111029(BigDecimal vI111029) {
        this.vI111029 = vI111029;
    }

    public BigDecimal getVI111029() {
        return vI111029;
    }

    public void setVI3100302(String vI3100302) {
        this.vI3100302 = vI3100302;
    }

    public String getVI3100302() {
        return vI3100302;
    }

    public void setVI3100402(String vI3100402) {
        this.vI3100402 = vI3100402;
    }

    public String getVI3100402() {
        return vI3100402;
    }

    public void setVI3110202(String vI3110202) {
        this.vI3110202 = vI3110202;
    }

    public String getVI3110202() {
        return vI3110202;
    }

    public void setVI3110602(String vI3110602) {
        this.vI3110602 = vI3110602;
    }

    public String getVI3110602() {
        return vI3110602;
    }

    public void setVI3110302(String vI3110302) {
        this.vI3110302 = vI3110302;
    }

    public String getVI3110302() {
        return vI3110302;
    }

    public void setVI111030(BigDecimal vI111030) {
        this.vI111030 = vI111030;
    }

    public BigDecimal getVI111030() {
        return vI111030;
    }

    public void setVI3101702(String vI3101702) {
        this.vI3101702 = vI3101702;
    }

    public String getVI3101702() {
        return vI3101702;
    }

    public void setVI111031(BigDecimal vI111031) {
        this.vI111031 = vI111031;
    }

    public BigDecimal getVI111031() {
        return vI111031;
    }

    public void setVI111032(BigDecimal vI111032) {
        this.vI111032 = vI111032;
    }

    public BigDecimal getVI111032() {
        return vI111032;
    }

    public void setVI111033(BigDecimal vI111033) {
        this.vI111033 = vI111033;
    }

    public BigDecimal getVI111033() {
        return vI111033;
    }

    public void setVI111034(BigDecimal vI111034) {
        this.vI111034 = vI111034;
    }

    public BigDecimal getVI111034() {
        return vI111034;
    }

    public void setVI111035(BigDecimal vI111035) {
        this.vI111035 = vI111035;
    }

    public BigDecimal getVI111035() {
        return vI111035;
    }

    public void setVI111036(BigDecimal vI111036) {
        this.vI111036 = vI111036;
    }

    public BigDecimal getVI111036() {
        return vI111036;
    }

    public void setVI111037(BigDecimal vI111037) {
        this.vI111037 = vI111037;
    }

    public BigDecimal getVI111037() {
        return vI111037;
    }

    public void setVI3102402(String vI3102402) {
        this.vI3102402 = vI3102402;
    }

    public String getVI3102402() {
        return vI3102402;
    }

    public void setVI3102502(String vI3102502) {
        this.vI3102502 = vI3102502;
    }

    public String getVI3102502() {
        return vI3102502;
    }

    public void setVI3102702(String vI3102702) {
        this.vI3102702 = vI3102702;
    }

    public String getVI3102702() {
        return vI3102702;
    }

    public void setVI3102802(String vI3102802) {
        this.vI3102802 = vI3102802;
    }

    public String getVI3102802() {
        return vI3102802;
    }

    public void setVI111038(BigDecimal vI111038) {
        this.vI111038 = vI111038;
    }

    public BigDecimal getVI111038() {
        return vI111038;
    }

    public void setVI111039(int vI111039) {
        this.vI111039 = vI111039;
    }

    public int getVI111039() {
        return vI111039;
    }

    public void setVI111040(BigDecimal vI111040) {
        this.vI111040 = vI111040;
    }

    public BigDecimal getVI111040() {
        return vI111040;
    }

    public void setVI111041(BigDecimal vI111041) {
        this.vI111041 = vI111041;
    }

    public BigDecimal getVI111041() {
        return vI111041;
    }

    public void setVI111042(BigDecimal vI111042) {
        this.vI111042 = vI111042;
    }

    public BigDecimal getVI111042() {
        return vI111042;
    }

    public void setVI111043(BigDecimal vI111043) {
        this.vI111043 = vI111043;
    }

    public BigDecimal getVI111043() {
        return vI111043;
    }

    public void setVI111044(BigDecimal vI111044) {
        this.vI111044 = vI111044;
    }

    public BigDecimal getVI111044() {
        return vI111044;
    }

    public void setVI111045(BigDecimal vI111045) {
        this.vI111045 = vI111045;
    }

    public BigDecimal getVI111045() {
        return vI111045;
    }

    public void setVI3103702(String vI3103702) {
        this.vI3103702 = vI3103702;
    }

    public String getVI3103702() {
        return vI3103702;
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

    public void setVI111046(BigDecimal vI111046) {
        this.vI111046 = vI111046;
    }

    public BigDecimal getVI111046() {
        return vI111046;
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

    public void setVI3107302(String vI3107302) {
        this.vI3107302 = vI3107302;
    }

    public String getVI3107302() {
        return vI3107302;
    }

    public void setVI3107402(String vI3107402) {
        this.vI3107402 = vI3107402;
    }

    public String getVI3107402() {
        return vI3107402;
    }

    public void setVI3104602(String vI3104602) {
        this.vI3104602 = vI3104602;
    }

    public String getVI3104602() {
        return vI3104602;
    }

    public void setVI111008(String vI111008) {
        this.vI111008 = vI111008;
    }

    public String getVI111008() {
        return vI111008;
    }

    public void setVI111009(String vI111009) {
        this.vI111009 = vI111009;
    }

    public String getVI111009() {
        return vI111009;
    }

    public void setVI111047(BigDecimal vI111047) {
        this.vI111047 = vI111047;
    }

    public BigDecimal getVI111047() {
        return vI111047;
    }

    public void setVI111048(BigDecimal vI111048) {
        this.vI111048 = vI111048;
    }

    public BigDecimal getVI111048() {
        return vI111048;
    }

    public void setVI3105002(String vI3105002) {
        this.vI3105002 = vI3105002;
    }

    public String getVI3105002() {
        return vI3105002;
    }

    public void setVI3107502(String vI3107502) {
        this.vI3107502 = vI3107502;
    }

    public String getVI3107502() {
        return vI3107502;
    }

    public void setVI3110502(String vI3110502) {
        this.vI3110502 = vI3110502;
    }

    public String getVI3110502() {
        return vI3110502;
    }

    public void setVI3107602(String vI3107602) {
        this.vI3107602 = vI3107602;
    }

    public String getVI3107602() {
        return vI3107602;
    }

    public void setVI111049(BigDecimal vI111049) {
        this.vI111049 = vI111049;
    }

    public BigDecimal getVI111049() {
        return vI111049;
    }

    public void setVI111050(BigDecimal vI111050) {
        this.vI111050 = vI111050;
    }

    public BigDecimal getVI111050() {
        return vI111050;
    }

    public void setVI111013(BigDecimal vI111013) {
        this.vI111013 = vI111013;
    }

    public BigDecimal getVI111013() {
        return vI111013;
    }

    public void setVI111051(String vI111051) {
        this.vI111051 = vI111051;
    }

    public String getVI111051() {
        return vI111051;
    }

    public void setVI111052(String vI111052) {
        this.vI111052 = vI111052;
    }

    public String getVI111052() {
        return vI111052;
    }

    public void setVI111014(String vI111014) {
        this.vI111014 = vI111014;
    }

    public String getVI111014() {
        return vI111014;
    }

    public void setVI111053(BigDecimal vI111053) {
        this.vI111053 = vI111053;
    }

    public BigDecimal getVI111053() {
        return vI111053;
    }

    public void setVI111054(BigDecimal vI111054) {
        this.vI111054 = vI111054;
    }

    public BigDecimal getVI111054() {
        return vI111054;
    }

    public void setVI111055(BigDecimal vI111055) {
        this.vI111055 = vI111055;
    }

    public BigDecimal getVI111055() {
        return vI111055;
    }

    public void setVI111056(BigDecimal vI111056) {
        this.vI111056 = vI111056;
    }

    public BigDecimal getVI111056() {
        return vI111056;
    }

    public void setVI111017(int vI111017) {
        this.vI111017 = vI111017;
    }

    public int getVI111017() {
        return vI111017;
    }

    public void setVI111057(BigDecimal vI111057) {
        this.vI111057 = vI111057;
    }

    public BigDecimal getVI111057() {
        return vI111057;
    }

    public void setVI3110702(String vI3110702) {
        this.vI3110702 = vI3110702;
    }

    public String getVI3110702() {
        return vI3110702;
    }

    public void setVI3110802(String vI3110802) {
        this.vI3110802 = vI3110802;
    }

    public String getVI3110802() {
        return vI3110802;
    }

    public void setVI3110902(String vI3110902) {
        this.vI3110902 = vI3110902;
    }

    public String getVI3110902() {
        return vI3110902;
    }

    public void setVI3106502(String vI3106502) {
        this.vI3106502 = vI3106502;
    }

    public String getVI3106502() {
        return vI3106502;
    }

    public void setVI3106602(String vI3106602) {
        this.vI3106602 = vI3106602;
    }

    public String getVI3106602() {
        return vI3106602;
    }

    public void setVI3107702(String vI3107702) {
        this.vI3107702 = vI3107702;
    }

    public String getVI3107702() {
        return vI3107702;
    }

    public void setVI3111002(String vI3111002) {
        this.vI3111002 = vI3111002;
    }

    public String getVI3111002() {
        return vI3111002;
    }

    public void setVI111058(String vI111058) {
        this.vI111058 = vI111058;
    }

    public String getVI111058() {
        return vI111058;
    }

    public void setVI3108002(String vI3108002) {
        this.vI3108002 = vI3108002;
    }

    public String getVI3108002() {
        return vI3108002;
    }

    public void setVI3110402(String vI3110402) {
        this.vI3110402 = vI3110402;
    }

    public String getVI3110402() {
        return vI3110402;
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

    public void setVI3111202(String vI3111202) {
        this.vI3111202 = vI3111202;
    }

    public String getVI3111202() {
        return vI3111202;
    }

    public void setVI3107202(String vI3107202) {
        this.vI3107202 = vI3107202;
    }

    public String getVI3107202() {
        return vI3107202;
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

    public void setVI111021(String vI111021) {
        this.vI111021 = vI111021;
    }

    public String getVI111021() {
        return vI111021;
    }

    public void setVI111022(String vI111022) {
        this.vI111022 = vI111022;
    }

    public String getVI111022() {
        return vI111022;
    }

    public void setVI11109(String vI11109) {
        this.vI11109 = vI11109;
    }

    public String getVI11109() {
        return vI11109;
    }

    public void setVI3111102(String vI3111102) {
        this.vI3111102 = vI3111102;
    }

    public String getVI3111102() {
        return vI3111102;
    }

    public void setVI111024(BigDecimal vI111024) {
        this.vI111024 = vI111024;
    }

    public BigDecimal getVI111024() {
        return vI111024;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("rfc:");
        sb.append(this.getRfceeog1());
        sb.append(", ");
        sb.append("rfc1:");
        sb.append(this.getRfceeog2());
        sb.append(", ");
        sb.append("rfc2:");
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
