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
 * @since   13/08/2013
 *
 */
@XmlType(propOrder =
         { "numeroOperacion", "tdiepco1", "fDecFperceh1", "i111837", "i111838", "i111810", "i111811", "i111812",
           "i111813", "iDecSeumbps1", "i111848", "i111817", "i111815", "i111818", "i111860", "iDecEpidtsr1",
           "iDecEpipcns1", "iDecPeefafn1", "i111820", "i111819", "i111822", "iDecCfietud1", "iDecIirrpmn1", "a111888",
           "a111889", "i111021", "i111827", "iDecIademce1", "iDecDciafre1", "i111023", "iDecIrpeaie1", "i111024",
           "a111201", "a111202", "a111207", "a111206", "a111401", "a111421", "a111402", "iDecIlpioug1", "iDecEfpdama1",
           "a111422", "a111407", "a111404", "a111938", "a111501", "a111504", "a111523", "iDecIliebim1", "a111544",
           "a111535", "a111840", "a111550", "a111551", "a111552", "a111553", "a111842", "a111743", "a111730",
           "a111732", "a111744", "a111734", "a111728", "a111740", "a111741", "a111742", "a111724", "a111927",
           "a111726", "a111850", "a111851", "a111852", "a111856", "a111853", "a111880", "a111881", "a111882",
           "a111883", "a111884", "iDecPutpeat1", "iDecDuapspa1", "iDecDpapspa1", "a111885", "a111886", "a111142",
           "a111143", "a111192", "iDecIliaemo1", "iDecEfpdams1", "a111144", "i3DecUftiisl1", "i3DecPfeirsd1",
           "i3DecPutpeat1", "a111145", "a111146", "a111147", "a111148", "a116392", "a116393", "a116399",
           "iDecGridtae1", "a117918", "a118952", "a118954", "iDecPuteatr1", "i4DecDuitfie1", "i4DecDpiefre1",
           "a111005", "a118955", "a117001", "a117073", "a117071", "a117076", "a117285", "a117065", "a117066",
           "a117889", "a201356", "a111191" })
public class ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO implements Serializable {

    @SuppressWarnings("compatibility:-2986880713789170104")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private int nEjercicio;
    private int cNPeriodo;

    //*****  Salidas  *****
    private BigDecimal numeroOperacion;
    private int tdiepco1;
    private Date fDecFperceh1;
    private String i111837;
    private String i111838;
    private String i111810;
    private BigDecimal i111811;
    private BigDecimal i111812;
    private BigDecimal i111813;
    private String iDecSeumbps1;
    private BigDecimal i111848;
    private BigDecimal i111817;
    private String i111815;
    private BigDecimal i111818;
    private String i111860;
    private String iDecEpidtsr1;
    private String iDecEpipcns1;
    private String iDecPeefafn1;
    private BigDecimal i111820;
    private BigDecimal i111819;
    private BigDecimal i111822;
    private String iDecCfietud1;
    private String iDecIirrpmn1;
    private BigDecimal a111888;
    private BigDecimal a111889;
    private BigDecimal i111021;
    private String i111827;
    private String iDecIademce1;
    private String iDecDciafre1;
    private String i111023;
    private String iDecIrpeaie1;
    private BigDecimal i111024;
    private BigDecimal a111201;
    private BigDecimal a111202;
    private BigDecimal a111207;
    private BigDecimal a111206;
    private String a111401;
    private String a111421;
    private String a111402;
    private String iDecIlpioug1;
    private String iDecEfpdama1;
    private String a111422;
    private String a111407;
    private String a111404;
    private String a111938;
    private String a111501;
    private String a111504;
    private String a111523;
    private String iDecIliebim1;
    private String a111544;
    private String a111535;
    private String a111840;
    private String a111550;
    private String a111551;
    private String a111552;
    private String a111553;
    private String a111842;
    private String a111743;
    private String a111730;
    private String a111732;
    private String a111744;
    private String a111734;
    private String a111728;
    private String a111740;
    private String a111741;
    private String a111742;
    private String a111724;
    private String a111927;
    private String a111726;
    private String a111850;
    private String a111851;
    private String a111852;
    private String a111856;
    private String a111853;
    private String a111880;
    private String a111881;
    private String a111882;
    private String a111883;
    private String a111884;
    private String iDecPutpeat1;
    private String iDecDuapspa1;
    private String iDecDpapspa1;
    private String a111885;
    private String a111886;
    private String a111142;
    private String a111143;
    private String a111192;
    private String iDecIliaemo1;
    private String iDecEfpdams1;
    private String a111144;
    private String i3DecUftiisl1;
    private String i3DecPfeirsd1;
    private String i3DecPutpeat1;
    private String a111145;
    private String a111146;
    private String a111147;
    private String a111148;
    private String a116392;
    private String a116393;
    private String a116399;
    private String iDecGridtae1;
    private String a117918;
    private String a118952;
    private String a118954;
    private String iDecPuteatr1;
    private String i4DecDuitfie1;
    private String i4DecDpiefre1;
    private String a111005;
    private String a118955;
    private String a117001;
    private String a117073;
    private String a117071;
    private String a117076;
    private String a117285;
    private String a117065;
    private String a117066;
    private String a117889;
    private String a201356;
    private String a111191;

    public void setRfceeog1(String rfceeog1) {
        this.rfceeog1 = rfceeog1;
    }

    @XmlTransient
    public String getRfceeog1() {
        return rfceeog1;
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

    @XmlTransient
    public int getCNPeriodo() {
        return cNPeriodo;
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

    public void setFDecFperceh1(Date fDecFperceh1) {
        this.fDecFperceh1 = new Date(fDecFperceh1.getTime());
    }

    public Date getFDecFperceh1() {
        return new Date(fDecFperceh1.getTime());
    }

    public void setI111837(String i111837) {
        this.i111837 = i111837;
    }

    public String getI111837() {
        return i111837;
    }

    public void setI111838(String i111838) {
        this.i111838 = i111838;
    }

    public String getI111838() {
        return i111838;
    }

    public void setI111810(String i111810) {
        this.i111810 = i111810;
    }

    public String getI111810() {
        return i111810;
    }

    public void setI111811(BigDecimal i111811) {
        this.i111811 = i111811;
    }

    public BigDecimal getI111811() {
        return i111811;
    }

    public void setI111812(BigDecimal i111812) {
        this.i111812 = i111812;
    }

    public BigDecimal getI111812() {
        return i111812;
    }

    public void setI111813(BigDecimal i111813) {
        this.i111813 = i111813;
    }

    public BigDecimal getI111813() {
        return i111813;
    }

    public void setIDecSeumbps1(String iDecSeumbps1) {
        this.iDecSeumbps1 = iDecSeumbps1;
    }

    public String getIDecSeumbps1() {
        return iDecSeumbps1;
    }

    public void setI111848(BigDecimal i111848) {
        this.i111848 = i111848;
    }

    public BigDecimal getI111848() {
        return i111848;
    }

    public void setI111817(BigDecimal i111817) {
        this.i111817 = i111817;
    }

    public BigDecimal getI111817() {
        return i111817;
    }

    public void setI111815(String i111815) {
        this.i111815 = i111815;
    }

    public String getI111815() {
        return i111815;
    }

    public void setI111818(BigDecimal i111818) {
        this.i111818 = i111818;
    }

    public BigDecimal getI111818() {
        return i111818;
    }

    public void setI111860(String i111860) {
        this.i111860 = i111860;
    }

    public String getI111860() {
        return i111860;
    }

    public void setIDecEpidtsr1(String iDecEpidtsr1) {
        this.iDecEpidtsr1 = iDecEpidtsr1;
    }

    public String getIDecEpidtsr1() {
        return iDecEpidtsr1;
    }

    public void setIDecEpipcns1(String iDecEpipcns1) {
        this.iDecEpipcns1 = iDecEpipcns1;
    }

    public String getIDecEpipcns1() {
        return iDecEpipcns1;
    }

    public void setIDecPeefafn1(String iDecPeefafn1) {
        this.iDecPeefafn1 = iDecPeefafn1;
    }

    public String getIDecPeefafn1() {
        return iDecPeefafn1;
    }

    public void setI111820(BigDecimal i111820) {
        this.i111820 = i111820;
    }

    public BigDecimal getI111820() {
        return i111820;
    }

    public void setI111819(BigDecimal i111819) {
        this.i111819 = i111819;
    }

    public BigDecimal getI111819() {
        return i111819;
    }

    public void setI111822(BigDecimal i111822) {
        this.i111822 = i111822;
    }

    public BigDecimal getI111822() {
        return i111822;
    }

    public void setIDecCfietud1(String iDecCfietud1) {
        this.iDecCfietud1 = iDecCfietud1;
    }

    public String getIDecCfietud1() {
        return iDecCfietud1;
    }

    public void setIDecIirrpmn1(String iDecIirrpmn1) {
        this.iDecIirrpmn1 = iDecIirrpmn1;
    }

    public String getIDecIirrpmn1() {
        return iDecIirrpmn1;
    }

    public void setA111888(BigDecimal a111888) {
        this.a111888 = a111888;
    }

    public BigDecimal getA111888() {
        return a111888;
    }

    public void setA111889(BigDecimal a111889) {
        this.a111889 = a111889;
    }

    public BigDecimal getA111889() {
        return a111889;
    }

    public void setI111021(BigDecimal i111021) {
        this.i111021 = i111021;
    }

    public BigDecimal getI111021() {
        return i111021;
    }

    public void setI111827(String i111827) {
        this.i111827 = i111827;
    }

    public String getI111827() {
        return i111827;
    }

    public void setIDecIademce1(String iDecIademce1) {
        this.iDecIademce1 = iDecIademce1;
    }

    public String getIDecIademce1() {
        return iDecIademce1;
    }

    public void setIDecDciafre1(String iDecDciafre1) {
        this.iDecDciafre1 = iDecDciafre1;
    }

    public String getIDecDciafre1() {
        return iDecDciafre1;
    }

    public void setI111023(String i111023) {
        this.i111023 = i111023;
    }

    public String getI111023() {
        return i111023;
    }

    public void setIDecIrpeaie1(String iDecIrpeaie1) {
        this.iDecIrpeaie1 = iDecIrpeaie1;
    }

    public String getIDecIrpeaie1() {
        return iDecIrpeaie1;
    }

    public void setI111024(BigDecimal i111024) {
        this.i111024 = i111024;
    }

    public BigDecimal getI111024() {
        return i111024;
    }

    public void setA111201(BigDecimal a111201) {
        this.a111201 = a111201;
    }

    public BigDecimal getA111201() {
        return a111201;
    }

    public void setA111202(BigDecimal a111202) {
        this.a111202 = a111202;
    }

    public BigDecimal getA111202() {
        return a111202;
    }

    public void setA111207(BigDecimal a111207) {
        this.a111207 = a111207;
    }

    public BigDecimal getA111207() {
        return a111207;
    }

    public void setA111206(BigDecimal a111206) {
        this.a111206 = a111206;
    }

    public BigDecimal getA111206() {
        return a111206;
    }

    public void setA111401(String a111401) {
        this.a111401 = a111401;
    }

    public String getA111401() {
        return a111401;
    }

    public void setA111421(String a111421) {
        this.a111421 = a111421;
    }

    public String getA111421() {
        return a111421;
    }

    public void setA111402(String a111402) {
        this.a111402 = a111402;
    }

    public String getA111402() {
        return a111402;
    }

    public void setIDecIlpioug1(String iDecIlpioug1) {
        this.iDecIlpioug1 = iDecIlpioug1;
    }

    public String getIDecIlpioug1() {
        return iDecIlpioug1;
    }

    public void setIDecEfpdama1(String iDecEfpdama1) {
        this.iDecEfpdama1 = iDecEfpdama1;
    }

    public String getIDecEfpdama1() {
        return iDecEfpdama1;
    }

    public void setA111422(String a111422) {
        this.a111422 = a111422;
    }

    public String getA111422() {
        return a111422;
    }

    public void setA111407(String a111407) {
        this.a111407 = a111407;
    }

    public String getA111407() {
        return a111407;
    }

    public void setA111404(String a111404) {
        this.a111404 = a111404;
    }

    public String getA111404() {
        return a111404;
    }

    public void setA111938(String a111938) {
        this.a111938 = a111938;
    }

    public String getA111938() {
        return a111938;
    }

    public void setA111501(String a111501) {
        this.a111501 = a111501;
    }

    public String getA111501() {
        return a111501;
    }

    public void setA111504(String a111504) {
        this.a111504 = a111504;
    }

    public String getA111504() {
        return a111504;
    }

    public void setA111523(String a111523) {
        this.a111523 = a111523;
    }

    public String getA111523() {
        return a111523;
    }

    public void setIDecIliebim1(String iDecIliebim1) {
        this.iDecIliebim1 = iDecIliebim1;
    }

    public String getIDecIliebim1() {
        return iDecIliebim1;
    }

    public void setA111544(String a111544) {
        this.a111544 = a111544;
    }

    public String getA111544() {
        return a111544;
    }

    public void setA111535(String a111535) {
        this.a111535 = a111535;
    }

    public String getA111535() {
        return a111535;
    }

    public void setA111840(String a111840) {
        this.a111840 = a111840;
    }

    public String getA111840() {
        return a111840;
    }

    public void setA111550(String a111550) {
        this.a111550 = a111550;
    }

    public String getA111550() {
        return a111550;
    }

    public void setA111551(String a111551) {
        this.a111551 = a111551;
    }

    public String getA111551() {
        return a111551;
    }

    public void setA111552(String a111552) {
        this.a111552 = a111552;
    }

    public String getA111552() {
        return a111552;
    }

    public void setA111553(String a111553) {
        this.a111553 = a111553;
    }

    public String getA111553() {
        return a111553;
    }

    public void setA111842(String a111842) {
        this.a111842 = a111842;
    }

    public String getA111842() {
        return a111842;
    }

    public void setA111743(String a111743) {
        this.a111743 = a111743;
    }

    public String getA111743() {
        return a111743;
    }

    public void setA111730(String a111730) {
        this.a111730 = a111730;
    }

    public String getA111730() {
        return a111730;
    }

    public void setA111732(String a111732) {
        this.a111732 = a111732;
    }

    public String getA111732() {
        return a111732;
    }

    public void setA111744(String a111744) {
        this.a111744 = a111744;
    }

    public String getA111744() {
        return a111744;
    }

    public void setA111734(String a111734) {
        this.a111734 = a111734;
    }

    public String getA111734() {
        return a111734;
    }

    public void setA111728(String a111728) {
        this.a111728 = a111728;
    }

    public String getA111728() {
        return a111728;
    }

    public void setA111740(String a111740) {
        this.a111740 = a111740;
    }

    public String getA111740() {
        return a111740;
    }

    public void setA111741(String a111741) {
        this.a111741 = a111741;
    }

    public String getA111741() {
        return a111741;
    }

    public void setA111742(String a111742) {
        this.a111742 = a111742;
    }

    public String getA111742() {
        return a111742;
    }

    public void setA111724(String a111724) {
        this.a111724 = a111724;
    }

    public String getA111724() {
        return a111724;
    }

    public void setA111927(String a111927) {
        this.a111927 = a111927;
    }

    public String getA111927() {
        return a111927;
    }

    public void setA111726(String a111726) {
        this.a111726 = a111726;
    }

    public String getA111726() {
        return a111726;
    }

    public void setA111850(String a111850) {
        this.a111850 = a111850;
    }

    public String getA111850() {
        return a111850;
    }

    public void setA111851(String a111851) {
        this.a111851 = a111851;
    }

    public String getA111851() {
        return a111851;
    }

    public void setA111852(String a111852) {
        this.a111852 = a111852;
    }

    public String getA111852() {
        return a111852;
    }

    public void setA111856(String a111856) {
        this.a111856 = a111856;
    }

    public String getA111856() {
        return a111856;
    }

    public void setA111853(String a111853) {
        this.a111853 = a111853;
    }

    public String getA111853() {
        return a111853;
    }

    public void setA111880(String a111880) {
        this.a111880 = a111880;
    }

    public String getA111880() {
        return a111880;
    }

    public void setA111881(String a111881) {
        this.a111881 = a111881;
    }

    public String getA111881() {
        return a111881;
    }

    public void setA111882(String a111882) {
        this.a111882 = a111882;
    }

    public String getA111882() {
        return a111882;
    }

    public void setA111883(String a111883) {
        this.a111883 = a111883;
    }

    public String getA111883() {
        return a111883;
    }

    public void setA111884(String a111884) {
        this.a111884 = a111884;
    }

    public String getA111884() {
        return a111884;
    }

    public void setIDecPutpeat1(String iDecPutpeat1) {
        this.iDecPutpeat1 = iDecPutpeat1;
    }

    public String getIDecPutpeat1() {
        return iDecPutpeat1;
    }

    public void setIDecDuapspa1(String iDecDuapspa1) {
        this.iDecDuapspa1 = iDecDuapspa1;
    }

    public String getIDecDuapspa1() {
        return iDecDuapspa1;
    }

    public void setIDecDpapspa1(String iDecDpapspa1) {
        this.iDecDpapspa1 = iDecDpapspa1;
    }

    public String getIDecDpapspa1() {
        return iDecDpapspa1;
    }

    public void setA111885(String a111885) {
        this.a111885 = a111885;
    }

    public String getA111885() {
        return a111885;
    }

    public void setA111886(String a111886) {
        this.a111886 = a111886;
    }

    public String getA111886() {
        return a111886;
    }

    public void setA111142(String a111142) {
        this.a111142 = a111142;
    }

    public String getA111142() {
        return a111142;
    }

    public void setA111143(String a111143) {
        this.a111143 = a111143;
    }

    public String getA111143() {
        return a111143;
    }

    public void setA111192(String a111192) {
        this.a111192 = a111192;
    }

    public String getA111192() {
        return a111192;
    }

    public void setIDecIliaemo1(String iDecIliaemo1) {
        this.iDecIliaemo1 = iDecIliaemo1;
    }

    public String getIDecIliaemo1() {
        return iDecIliaemo1;
    }

    public void setIDecEfpdams1(String iDecEfpdams1) {
        this.iDecEfpdams1 = iDecEfpdams1;
    }

    public String getIDecEfpdams1() {
        return iDecEfpdams1;
    }

    public void setA111144(String a111144) {
        this.a111144 = a111144;
    }

    public String getA111144() {
        return a111144;
    }

    public void setI3DecUftiisl1(String i3DecUftiisl1) {
        this.i3DecUftiisl1 = i3DecUftiisl1;
    }

    public String getI3DecUftiisl1() {
        return i3DecUftiisl1;
    }

    public void setI3DecPfeirsd1(String i3DecPfeirsd1) {
        this.i3DecPfeirsd1 = i3DecPfeirsd1;
    }

    public String getI3DecPfeirsd1() {
        return i3DecPfeirsd1;
    }

    public void setI3DecPutpeat1(String i3DecPutpeat1) {
        this.i3DecPutpeat1 = i3DecPutpeat1;
    }

    public String getI3DecPutpeat1() {
        return i3DecPutpeat1;
    }

    public void setA111145(String a111145) {
        this.a111145 = a111145;
    }

    public String getA111145() {
        return a111145;
    }

    public void setA111146(String a111146) {
        this.a111146 = a111146;
    }

    public String getA111146() {
        return a111146;
    }

    public void setA111147(String a111147) {
        this.a111147 = a111147;
    }

    public String getA111147() {
        return a111147;
    }

    public void setA111148(String a111148) {
        this.a111148 = a111148;
    }

    public String getA111148() {
        return a111148;
    }

    public void setA116392(String a116392) {
        this.a116392 = a116392;
    }

    public String getA116392() {
        return a116392;
    }

    public void setA116393(String a116393) {
        this.a116393 = a116393;
    }

    public String getA116393() {
        return a116393;
    }

    public void setA116399(String a116399) {
        this.a116399 = a116399;
    }

    public String getA116399() {
        return a116399;
    }

    public void setIDecGridtae1(String iDecGridtae1) {
        this.iDecGridtae1 = iDecGridtae1;
    }

    public String getIDecGridtae1() {
        return iDecGridtae1;
    }

    public void setA117918(String a117918) {
        this.a117918 = a117918;
    }

    public String getA117918() {
        return a117918;
    }

    public void setA118952(String a118952) {
        this.a118952 = a118952;
    }

    public String getA118952() {
        return a118952;
    }

    public void setA118954(String a118954) {
        this.a118954 = a118954;
    }

    public String getA118954() {
        return a118954;
    }

    public void setIDecPuteatr1(String iDecPuteatr1) {
        this.iDecPuteatr1 = iDecPuteatr1;
    }

    public String getIDecPuteatr1() {
        return iDecPuteatr1;
    }

    public void setI4DecDuitfie1(String i4DecDuitfie1) {
        this.i4DecDuitfie1 = i4DecDuitfie1;
    }

    public String getI4DecDuitfie1() {
        return i4DecDuitfie1;
    }

    public void setI4DecDpiefre1(String i4DecDpiefre1) {
        this.i4DecDpiefre1 = i4DecDpiefre1;
    }

    public String getI4DecDpiefre1() {
        return i4DecDpiefre1;
    }

    public void setA111005(String a111005) {
        this.a111005 = a111005;
    }

    public String getA111005() {
        return a111005;
    }

    public void setA118955(String a118955) {
        this.a118955 = a118955;
    }

    public String getA118955() {
        return a118955;
    }

    public void setA117001(String a117001) {
        this.a117001 = a117001;
    }

    public String getA117001() {
        return a117001;
    }

    public void setA117073(String a117073) {
        this.a117073 = a117073;
    }

    public String getA117073() {
        return a117073;
    }

    public void setA117071(String a117071) {
        this.a117071 = a117071;
    }

    public String getA117071() {
        return a117071;
    }

    public void setA117076(String a117076) {
        this.a117076 = a117076;
    }

    public String getA117076() {
        return a117076;
    }

    public void setA117285(String a117285) {
        this.a117285 = a117285;
    }

    public String getA117285() {
        return a117285;
    }

    public void setA117065(String a117065) {
        this.a117065 = a117065;
    }

    public String getA117065() {
        return a117065;
    }

    public void setA117066(String a117066) {
        this.a117066 = a117066;
    }

    public String getA117066() {
        return a117066;
    }

    public void setA117889(String a117889) {
        this.a117889 = a117889;
    }

    public String getA117889() {
        return a117889;
    }

    public void setA201356(String a201356) {
        this.a201356 = a201356;
    }

    public String getA201356() {
        return a201356;
    }

    public void setA111191(String a111191) {
        this.a111191 = a111191;
    }

    public String getA111191() {
        return a111191;
    }

    public String getParameterReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("rfceeog1:");
        sb.append(this.getRfceeog1());
        sb.append(", ");
        sb.append("nEjercicio:");
        sb.append(this.getNEjercicio());
        sb.append(", ");
        sb.append("cNPeriodo:");
        sb.append(this.getCNPeriodo());
        return sb.toString();
    }

}
