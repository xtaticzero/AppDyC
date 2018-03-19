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

public class ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO implements Serializable {

    @SuppressWarnings("compatibility:-8073797110665452435")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String dDecRfceeog1;
    private int nDecEjercic1;
    private int cDecCplearv1;

    //*****  Salidas  *****
    private BigDecimal numeroOperacion;
    private int tdiepco1;
    private Date fDecFperceh1;
    private BigDecimal i111837;
    private BigDecimal i111838;
    private BigDecimal i111810;
    private BigDecimal i111811;
    private BigDecimal i111812;
    private BigDecimal i111813;
    private BigDecimal iDecSeumbps1;
    private BigDecimal i111848;
    private BigDecimal i111817;
    private BigDecimal i111815;
    private BigDecimal i111818;
    private BigDecimal i111860;
    private BigDecimal iDecEpidtsr1;
    private BigDecimal iDecEpipcns1;
    private BigDecimal iDecPeefafn1;
    private BigDecimal i111820;
    private BigDecimal i111819;
    private BigDecimal i111822;
    private BigDecimal iDecCfietud1;
    private BigDecimal iDecIirrpmn1;
    private BigDecimal a111888;
    private BigDecimal a111889;
    private BigDecimal i111021;
    private BigDecimal i111827;
    private BigDecimal iDecIademce1;
    private BigDecimal iDecDciafre1;
    private BigDecimal i111023;
    private BigDecimal iDecIrpeaie1;
    private BigDecimal i111024;
    private BigDecimal a111201;
    private BigDecimal a111202;
    private BigDecimal a111207;
    private BigDecimal a111206;
    private BigDecimal a111401;
    private BigDecimal a111421;
    private BigDecimal a111402;
    private BigDecimal iDecIlpioug1;
    private BigDecimal iDecEfpdama1;
    private BigDecimal a111422;
    private BigDecimal a111407;
    private BigDecimal a111404;
    private BigDecimal a111938;
    private BigDecimal a111501;
    private BigDecimal a111504;
    private BigDecimal a111523;
    private BigDecimal iDecIliebim1;
    private BigDecimal a111544;
    private BigDecimal a111535;
    private BigDecimal a111840;
    private BigDecimal a111550;
    private BigDecimal a111551;
    private BigDecimal a111552;
    private BigDecimal a111553;
    private BigDecimal a111842;
    private BigDecimal a111743;
    private BigDecimal a111730;
    private BigDecimal a111732;
    private BigDecimal a111744;
    private BigDecimal a111734;
    private BigDecimal a111728;
    private BigDecimal a111740;
    private BigDecimal a111741;
    private BigDecimal a111742;
    private BigDecimal a111724;
    private BigDecimal a111927;
    private BigDecimal a111726;
    private BigDecimal a111850;
    private BigDecimal a111851;
    private BigDecimal a111852;
    private BigDecimal a111856;
    private BigDecimal a111853;
    private BigDecimal a111880;
    private BigDecimal a111881;
    private BigDecimal a111882;
    private BigDecimal a111883;
    private BigDecimal a111884;
    private BigDecimal iDecPutpeat1;
    private BigDecimal iDecDuapspa1;
    private BigDecimal iDecDpapspa1;
    private BigDecimal a111885;
    private BigDecimal a111886;
    private BigDecimal a111142;
    private BigDecimal a111143;
    private BigDecimal a111192;
    private BigDecimal iDecIliaemo1;
    private BigDecimal iDecEfpdams1;
    private BigDecimal a111144;
    private BigDecimal i3DecUftiisl1;
    private BigDecimal i3DecPfeirsd1;
    private BigDecimal i3DecPutpeat1;
    private BigDecimal a111145;
    private BigDecimal a111146;
    private BigDecimal a111147;
    private BigDecimal a111148;
    private BigDecimal a116392;
    private BigDecimal a116393;
    private BigDecimal a116399;
    private BigDecimal iDecGridtae1;
    private BigDecimal a117918;
    private BigDecimal a118952;
    private BigDecimal a118954;
    private BigDecimal iDecPuteatr1;
    private BigDecimal i4DecDuitfie1;
    private BigDecimal i4DecDpiefre1;
    private BigDecimal a111005;
    private BigDecimal a118955;
    private BigDecimal a117001;
    private BigDecimal a117073;
    private BigDecimal a117071;
    private BigDecimal a117076;
    private BigDecimal a117285;
    private BigDecimal a117065;
    private BigDecimal a117066;
    private BigDecimal a117889;
    private BigDecimal a201356;
    private BigDecimal a111191;

    public void setDDecRfceeog1(String dDecRfceeog1) {
        this.dDecRfceeog1 = dDecRfceeog1;
    }

    @XmlTransient
    public String getDDecRfceeog1() {
        return dDecRfceeog1;
    }

    public void setNDecEjercic1(int nDecEjercic1) {
        this.nDecEjercic1 = nDecEjercic1;
    }

    @XmlTransient
    public int getNDecEjercic1() {
        return nDecEjercic1;
    }

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public int getCDecCplearv1() {
        return cDecCplearv1;
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

    public void setI111837(BigDecimal i111837) {
        this.i111837 = i111837;
    }

    public BigDecimal getI111837() {
        return i111837;
    }

    public void setI111838(BigDecimal i111838) {
        this.i111838 = i111838;
    }

    public BigDecimal getI111838() {
        return i111838;
    }

    public void setI111810(BigDecimal i111810) {
        this.i111810 = i111810;
    }

    public BigDecimal getI111810() {
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

    public void setIDecSeumbps1(BigDecimal idecseumbps1) {
        this.iDecSeumbps1 = idecseumbps1;
    }

    public BigDecimal getIDecSeumbps1() {
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

    public void setI111815(BigDecimal i111815) {
        this.i111815 = i111815;
    }

    public BigDecimal getI111815() {
        return i111815;
    }

    public void setI111818(BigDecimal i111818) {
        this.i111818 = i111818;
    }

    public BigDecimal getI111818() {
        return i111818;
    }

    public void setI111860(BigDecimal i111860) {
        this.i111860 = i111860;
    }

    public BigDecimal getI111860() {
        return i111860;
    }

    public void setIDecEpidtsr1(BigDecimal idecepidtsr1) {
        this.iDecEpidtsr1 = idecepidtsr1;
    }

    public BigDecimal getIDecEpidtsr1() {
        return iDecEpidtsr1;
    }

    public void setIDecEpipcns1(BigDecimal idecepipcns1) {
        this.iDecEpipcns1 = idecepipcns1;
    }

    public BigDecimal getIDecEpipcns1() {
        return iDecEpipcns1;
    }

    public void setIDecPeefafn1(BigDecimal idecpeefafn1) {
        this.iDecPeefafn1 = idecpeefafn1;
    }

    public BigDecimal getIDecPeefafn1() {
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

    public void setIDecCfietud1(BigDecimal ideccfietud1) {
        this.iDecCfietud1 = ideccfietud1;
    }

    public BigDecimal getIDecCfietud1() {
        return iDecCfietud1;
    }

    public void setIDecIirrpmn1(BigDecimal ideciirrpmn1) {
        this.iDecIirrpmn1 = ideciirrpmn1;
    }

    public BigDecimal getIDecIirrpmn1() {
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

    public void setI111827(BigDecimal i111827) {
        this.i111827 = i111827;
    }

    public BigDecimal getI111827() {
        return i111827;
    }

    public void setIDecIademce1(BigDecimal ideciademce1) {
        this.iDecIademce1 = ideciademce1;
    }

    public BigDecimal getIDecIademce1() {
        return iDecIademce1;
    }

    public void setIDecDciafre1(BigDecimal idecdciafre1) {
        this.iDecDciafre1 = idecdciafre1;
    }

    public BigDecimal getIDecDciafre1() {
        return iDecDciafre1;
    }

    public void setI111023(BigDecimal i111023) {
        this.i111023 = i111023;
    }

    public BigDecimal getI111023() {
        return i111023;
    }

    public void setIDecIrpeaie1(BigDecimal idecirpeaie1) {
        this.iDecIrpeaie1 = idecirpeaie1;
    }

    public BigDecimal getIDecIrpeaie1() {
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

    public void setA111401(BigDecimal a111401) {
        this.a111401 = a111401;
    }

    public BigDecimal getA111401() {
        return a111401;
    }

    public void setA111421(BigDecimal a111421) {
        this.a111421 = a111421;
    }

    public BigDecimal getA111421() {
        return a111421;
    }

    public void setA111402(BigDecimal a111402) {
        this.a111402 = a111402;
    }

    public BigDecimal getA111402() {
        return a111402;
    }

    public void setIDecIlpioug1(BigDecimal idecilpioug1) {
        this.iDecIlpioug1 = idecilpioug1;
    }

    public BigDecimal getIDecIlpioug1() {
        return iDecIlpioug1;
    }

    public void setIDecEfpdama1(BigDecimal idecefpdama1) {
        this.iDecEfpdama1 = idecefpdama1;
    }

    public BigDecimal getIDecEfpdama1() {
        return iDecEfpdama1;
    }

    public void setA111422(BigDecimal a111422) {
        this.a111422 = a111422;
    }

    public BigDecimal getA111422() {
        return a111422;
    }

    public void setA111407(BigDecimal a111407) {
        this.a111407 = a111407;
    }

    public BigDecimal getA111407() {
        return a111407;
    }

    public void setA111404(BigDecimal a111404) {
        this.a111404 = a111404;
    }

    public BigDecimal getA111404() {
        return a111404;
    }

    public void setA111938(BigDecimal a111938) {
        this.a111938 = a111938;
    }

    public BigDecimal getA111938() {
        return a111938;
    }

    public void setA111501(BigDecimal a111501) {
        this.a111501 = a111501;
    }

    public BigDecimal getA111501() {
        return a111501;
    }

    public void setA111504(BigDecimal a111504) {
        this.a111504 = a111504;
    }

    public BigDecimal getA111504() {
        return a111504;
    }

    public void setA111523(BigDecimal a111523) {
        this.a111523 = a111523;
    }

    public BigDecimal getA111523() {
        return a111523;
    }

    public void setIDecIliebim1(BigDecimal ideciliebim1) {
        this.iDecIliebim1 = ideciliebim1;
    }

    public BigDecimal getIDecIliebim1() {
        return iDecIliebim1;
    }

    public void setA111544(BigDecimal a111544) {
        this.a111544 = a111544;
    }

    public BigDecimal getA111544() {
        return a111544;
    }

    public void setA111535(BigDecimal a111535) {
        this.a111535 = a111535;
    }

    public BigDecimal getA111535() {
        return a111535;
    }

    public void setA111840(BigDecimal a111840) {
        this.a111840 = a111840;
    }

    public BigDecimal getA111840() {
        return a111840;
    }

    public void setA111550(BigDecimal a111550) {
        this.a111550 = a111550;
    }

    public BigDecimal getA111550() {
        return a111550;
    }

    public void setA111551(BigDecimal a111551) {
        this.a111551 = a111551;
    }

    public BigDecimal getA111551() {
        return a111551;
    }

    public void setA111552(BigDecimal a111552) {
        this.a111552 = a111552;
    }

    public BigDecimal getA111552() {
        return a111552;
    }

    public void setA111553(BigDecimal a111553) {
        this.a111553 = a111553;
    }

    public BigDecimal getA111553() {
        return a111553;
    }

    public void setA111842(BigDecimal a111842) {
        this.a111842 = a111842;
    }

    public BigDecimal getA111842() {
        return a111842;
    }

    public void setA111743(BigDecimal a111743) {
        this.a111743 = a111743;
    }

    public BigDecimal getA111743() {
        return a111743;
    }

    public void setA111730(BigDecimal a111730) {
        this.a111730 = a111730;
    }

    public BigDecimal getA111730() {
        return a111730;
    }

    public void setA111732(BigDecimal a111732) {
        this.a111732 = a111732;
    }

    public BigDecimal getA111732() {
        return a111732;
    }

    public void setA111744(BigDecimal a111744) {
        this.a111744 = a111744;
    }

    public BigDecimal getA111744() {
        return a111744;
    }

    public void setA111734(BigDecimal a111734) {
        this.a111734 = a111734;
    }

    public BigDecimal getA111734() {
        return a111734;
    }

    public void setA111728(BigDecimal a111728) {
        this.a111728 = a111728;
    }

    public BigDecimal getA111728() {
        return a111728;
    }

    public void setA111740(BigDecimal a111740) {
        this.a111740 = a111740;
    }

    public BigDecimal getA111740() {
        return a111740;
    }

    public void setA111741(BigDecimal a111741) {
        this.a111741 = a111741;
    }

    public BigDecimal getA111741() {
        return a111741;
    }

    public void setA111742(BigDecimal a111742) {
        this.a111742 = a111742;
    }

    public BigDecimal getA111742() {
        return a111742;
    }

    public void setA111724(BigDecimal a111724) {
        this.a111724 = a111724;
    }

    public BigDecimal getA111724() {
        return a111724;
    }

    public void setA111927(BigDecimal a111927) {
        this.a111927 = a111927;
    }

    public BigDecimal getA111927() {
        return a111927;
    }

    public void setA111726(BigDecimal a111726) {
        this.a111726 = a111726;
    }

    public BigDecimal getA111726() {
        return a111726;
    }

    public void setA111850(BigDecimal a111850) {
        this.a111850 = a111850;
    }

    public BigDecimal getA111850() {
        return a111850;
    }

    public void setA111851(BigDecimal a111851) {
        this.a111851 = a111851;
    }

    public BigDecimal getA111851() {
        return a111851;
    }

    public void setA111852(BigDecimal a111852) {
        this.a111852 = a111852;
    }

    public BigDecimal getA111852() {
        return a111852;
    }

    public void setA111856(BigDecimal a111856) {
        this.a111856 = a111856;
    }

    public BigDecimal getA111856() {
        return a111856;
    }

    public void setA111853(BigDecimal a111853) {
        this.a111853 = a111853;
    }

    public BigDecimal getA111853() {
        return a111853;
    }

    public void setA111880(BigDecimal a111880) {
        this.a111880 = a111880;
    }

    public BigDecimal getA111880() {
        return a111880;
    }

    public void setA111881(BigDecimal a111881) {
        this.a111881 = a111881;
    }

    public BigDecimal getA111881() {
        return a111881;
    }

    public void setA111882(BigDecimal a111882) {
        this.a111882 = a111882;
    }

    public BigDecimal getA111882() {
        return a111882;
    }

    public void setA111883(BigDecimal a111883) {
        this.a111883 = a111883;
    }

    public BigDecimal getA111883() {
        return a111883;
    }

    public void setA111884(BigDecimal a111884) {
        this.a111884 = a111884;
    }

    public BigDecimal getA111884() {
        return a111884;
    }

    public void setIDecPutpeat1(BigDecimal idecputpeat1) {
        this.iDecPutpeat1 = idecputpeat1;
    }

    public BigDecimal getIDecPutpeat1() {
        return iDecPutpeat1;
    }

    public void setIDecDuapspa1(BigDecimal idecduapspa1) {
        this.iDecDuapspa1 = idecduapspa1;
    }

    public BigDecimal getIDecDuapspa1() {
        return iDecDuapspa1;
    }

    public void setIDecDpapspa1(BigDecimal idecdpapspa1) {
        this.iDecDpapspa1 = idecdpapspa1;
    }

    public BigDecimal getIDecDpapspa1() {
        return iDecDpapspa1;
    }

    public void setA111885(BigDecimal a111885) {
        this.a111885 = a111885;
    }

    public BigDecimal getA111885() {
        return a111885;
    }

    public void setA111886(BigDecimal a111886) {
        this.a111886 = a111886;
    }

    public BigDecimal getA111886() {
        return a111886;
    }

    public void setA111142(BigDecimal a111142) {
        this.a111142 = a111142;
    }

    public BigDecimal getA111142() {
        return a111142;
    }

    public void setA111143(BigDecimal a111143) {
        this.a111143 = a111143;
    }

    public BigDecimal getA111143() {
        return a111143;
    }

    public void setA111192(BigDecimal a111192) {
        this.a111192 = a111192;
    }

    public BigDecimal getA111192() {
        return a111192;
    }

    public void setIDecIliaemo1(BigDecimal ideciliaemo1) {
        this.iDecIliaemo1 = ideciliaemo1;
    }

    public BigDecimal getIDecIliaemo1() {
        return iDecIliaemo1;
    }

    public void setIDecEfpdams1(BigDecimal idecefpdams1) {
        this.iDecEfpdams1 = idecefpdams1;
    }

    public BigDecimal getIDecEfpdams1() {
        return iDecEfpdams1;
    }

    public void setA111144(BigDecimal a111144) {
        this.a111144 = a111144;
    }

    public BigDecimal getA111144() {
        return a111144;
    }

    public void setI3DecUftiisl1(BigDecimal i3decuftiisl1) {
        this.i3DecUftiisl1 = i3decuftiisl1;
    }

    public BigDecimal getI3DecUftiisl1() {
        return i3DecUftiisl1;
    }

    public void setI3DecPfeirsd1(BigDecimal i3decpfeirsd1) {
        this.i3DecPfeirsd1 = i3decpfeirsd1;
    }

    public BigDecimal getI3DecPfeirsd1() {
        return i3DecPfeirsd1;
    }

    public void setI3DecPutpeat1(BigDecimal i3decputpeat1) {
        this.i3DecPutpeat1 = i3decputpeat1;
    }

    public BigDecimal getI3DecPutpeat1() {
        return i3DecPutpeat1;
    }

    public void setA111145(BigDecimal a111145) {
        this.a111145 = a111145;
    }

    public BigDecimal getA111145() {
        return a111145;
    }

    public void setA111146(BigDecimal a111146) {
        this.a111146 = a111146;
    }

    public BigDecimal getA111146() {
        return a111146;
    }

    public void setA111147(BigDecimal a111147) {
        this.a111147 = a111147;
    }

    public BigDecimal getA111147() {
        return a111147;
    }

    public void setA111148(BigDecimal a111148) {
        this.a111148 = a111148;
    }

    public BigDecimal getA111148() {
        return a111148;
    }

    public void setA116392(BigDecimal a116392) {
        this.a116392 = a116392;
    }

    public BigDecimal getA116392() {
        return a116392;
    }

    public void setA116393(BigDecimal a116393) {
        this.a116393 = a116393;
    }

    public BigDecimal getA116393() {
        return a116393;
    }

    public void setA116399(BigDecimal a116399) {
        this.a116399 = a116399;
    }

    public BigDecimal getA116399() {
        return a116399;
    }

    public void setIDecGridtae1(BigDecimal idecgridtae1) {
        this.iDecGridtae1 = idecgridtae1;
    }

    public BigDecimal getIDecGridtae1() {
        return iDecGridtae1;
    }

    public void setA117918(BigDecimal a117918) {
        this.a117918 = a117918;
    }

    public BigDecimal getA117918() {
        return a117918;
    }

    public void setA118952(BigDecimal a118952) {
        this.a118952 = a118952;
    }

    public BigDecimal getA118952() {
        return a118952;
    }

    public void setA118954(BigDecimal a118954) {
        this.a118954 = a118954;
    }

    public BigDecimal getA118954() {
        return a118954;
    }

    public void setIDecPuteatr1(BigDecimal idecputeatr1) {
        this.iDecPuteatr1 = idecputeatr1;
    }

    public BigDecimal getIDecPuteatr1() {
        return iDecPuteatr1;
    }

    public void setI4DecDuitfie1(BigDecimal i4decduitfie1) {
        this.i4DecDuitfie1 = i4decduitfie1;
    }

    public BigDecimal getI4DecDuitfie1() {
        return i4DecDuitfie1;
    }

    public void setI4DecDpiefre1(BigDecimal i4decdpiefre1) {
        this.i4DecDpiefre1 = i4decdpiefre1;
    }

    public BigDecimal getI4DecDpiefre1() {
        return i4DecDpiefre1;
    }

    public void setA111005(BigDecimal a111005) {
        this.a111005 = a111005;
    }

    public BigDecimal getA111005() {
        return a111005;
    }

    public void setA118955(BigDecimal a118955) {
        this.a118955 = a118955;
    }

    public BigDecimal getA118955() {
        return a118955;
    }

    public void setA117001(BigDecimal a117001) {
        this.a117001 = a117001;
    }

    public BigDecimal getA117001() {
        return a117001;
    }

    public void setA117073(BigDecimal a117073) {
        this.a117073 = a117073;
    }

    public BigDecimal getA117073() {
        return a117073;
    }

    public void setA117071(BigDecimal a117071) {
        this.a117071 = a117071;
    }

    public BigDecimal getA117071() {
        return a117071;
    }

    public void setA117076(BigDecimal a117076) {
        this.a117076 = a117076;
    }

    public BigDecimal getA117076() {
        return a117076;
    }

    public void setA117285(BigDecimal a117285) {
        this.a117285 = a117285;
    }

    public BigDecimal getA117285() {
        return a117285;
    }

    public void setA117065(BigDecimal a117065) {
        this.a117065 = a117065;
    }

    public BigDecimal getA117065() {
        return a117065;
    }

    public void setA117066(BigDecimal a117066) {
        this.a117066 = a117066;
    }

    public BigDecimal getA117066() {
        return a117066;
    }

    public void setA117889(BigDecimal a117889) {
        this.a117889 = a117889;
    }

    public BigDecimal getA117889() {
        return a117889;
    }

    public void setA201356(BigDecimal a201356) {
        this.a201356 = a201356;
    }

    public BigDecimal getA201356() {
        return a201356;
    }

    public void setA111191(BigDecimal a111191) {
        this.a111191 = a111191;
    }

    public BigDecimal getA111191() {
        return a111191;
    }

    public String getParameterReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("dDecRfceeog1:");
        sb.append(this.getDDecRfceeog1());
        sb.append(", ");
        sb.append("nDecEjercic1:");
        sb.append(this.getNDecEjercic1());
        sb.append(", ");
        sb.append("cDecCplearv1:");
        sb.append(this.getCDecCplearv1());
        return sb.toString();
    }

}
