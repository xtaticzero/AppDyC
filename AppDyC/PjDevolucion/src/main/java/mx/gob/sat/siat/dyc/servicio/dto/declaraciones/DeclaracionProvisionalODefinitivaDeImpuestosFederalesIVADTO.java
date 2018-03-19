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
 * @since 10/07/2013
 *
 */
@XmlType(propOrder =
         { "vNDecNoupmee1", "vFDecFcieamc1", "vIDecTaapt1i1", "vIDecTaapt1i2", "vIDecTaapibs1",
           "vIDecTaapibs2", "vIDecTdaapt01", "vIDecTaapqsp1", "vIDecTiaapt11", "vIDecTiaapt12",
           "vIDecTiaapib1", "vIDecTiaapib2", "vIDecTitacep1", "vIDecItabdia1", "vIDecIpiabdi1",
           "vIDecItaider1", "vIDecIpiider1", "vIDecTicaago1", "vIDecItpiabd1", "vIDecItpiide1",
           "vIDecPucaa5l1", "vIDecPucaa5l2", "vIDecIabuira1", "vIDecIavcare1", "vIDecMaaidao1",
           "vIDecTiapovc1", "vIDecVaagt1a1", "vIDecVaagt1a2", "vIDecVaagt0e1", "vIDecVaagt0o1",
           "vIDecSaagucc1", "vIDecVaaqsdp1", "vIDecIcmapuu1", "vIDecCardaac1", "vIDecIracvel1",
           "vIDecTiaovct1", "vIDecOccctaa1", "vIDecOcfctaa1", "vIDecCcaanrt1", "vIDecSfaalvd1",
           "vIDecDioenbv1", "vIDecSfpaael1", "vIDecAsfpaE1", "vIDecIaaadmi1", "vIDecIcmapru1",
           "vIDecRsfiaaa1" })
public class DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO implements Serializable {

    @SuppressWarnings("compatibility:-2670379089015733043")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String cIdeRfceeog1;
    private String cIdeRfceeog2;
    private String cIdeRfceeog3;
    private int nDecEjercic1;
    private int cDecCplearv1;

    //*****  Salidas  *****
    private BigDecimal vNDecNoupmee1;
    private Date vFDecFcieamc1;
    private BigDecimal vIDecTaapt1i1;
    private BigDecimal vIDecTaapt1i2;
    private BigDecimal vIDecTaapibs1;
    private BigDecimal vIDecTaapibs2;
    private BigDecimal vIDecTdaapt01;
    private BigDecimal vIDecTaapqsp1;
    private BigDecimal vIDecTiaapt11;
    private BigDecimal vIDecTiaapt12;
    private BigDecimal vIDecTiaapib1;
    private BigDecimal vIDecTiaapib2;
    private BigDecimal vIDecTitacep1;
    private BigDecimal vIDecItabdia1;
    private BigDecimal vIDecIpiabdi1;
    private BigDecimal vIDecItaider1;
    private BigDecimal vIDecIpiider1;
    private BigDecimal vIDecTicaago1;
    private BigDecimal vIDecItpiabd1;
    private BigDecimal vIDecItpiide1;
    private BigDecimal vIDecPucaa5l1;
    private BigDecimal vIDecPucaa5l2;
    private BigDecimal vIDecIabuira1;
    private BigDecimal vIDecIavcare1;
    private BigDecimal vIDecMaaidao1;
    private BigDecimal vIDecTiapovc1;
    private BigDecimal vIDecVaagt1a1;
    private BigDecimal vIDecVaagt1a2;
    private BigDecimal vIDecVaagt0e1;
    private BigDecimal vIDecVaagt0o1;
    private BigDecimal vIDecSaagucc1;
    private BigDecimal vIDecVaaqsdp1;
    private BigDecimal vIDecIcmapuu1;
    private BigDecimal vIDecCardaac1;
    private BigDecimal vIDecIracvel1;
    private BigDecimal vIDecTiaovct1;
    private BigDecimal vIDecOccctaa1;
    private BigDecimal vIDecOcfctaa1;
    private BigDecimal vIDecCcaanrt1;
    private BigDecimal vIDecSfaalvd1;
    private BigDecimal vIDecDioenbv1;
    private BigDecimal vIDecSfpaael1;
    private BigDecimal vIDecAsfpaE1;
    private BigDecimal vIDecIaaadmi1;
    private BigDecimal vIDecIcmapru1;
    private BigDecimal vIDecRsfiaaa1;

    public void setCIdeRfceeog1(String cIdeRfceeog1) {
        this.cIdeRfceeog1 = cIdeRfceeog1;
    }

    @XmlTransient
    public String getCIdeRfceeog1() {
        return cIdeRfceeog1;
    }

    public void setCIdeRfceeog2(String cIdeRfceeog2) {
        this.cIdeRfceeog2 = cIdeRfceeog2;
    }

    @XmlTransient
    public String getCIdeRfceeog2() {
        return cIdeRfceeog2;
    }

    public void setCIdeRfceeog3(String cIdeRfceeog3) {
        this.cIdeRfceeog3 = cIdeRfceeog3;
    }

    @XmlTransient
    public String getCIdeRfceeog3() {
        return cIdeRfceeog3;
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

    public void setVNDecNoupmee1(BigDecimal vNDecNoupmee1) {
        this.vNDecNoupmee1 = vNDecNoupmee1;
    }

    public BigDecimal getVNDecNoupmee1() {
        return vNDecNoupmee1;
    }

    public void setVFDecFcieamc1(Date vFDecFcieamc1) {
        this.vFDecFcieamc1 = new Date(vFDecFcieamc1.getTime());
    }

    public Date getVFDecFcieamc1() {
        return new Date(vFDecFcieamc1.getTime());
    }

    public void setVIDecTaapt1i1(BigDecimal vIDecTaapt1i1) {
        this.vIDecTaapt1i1 = vIDecTaapt1i1;
    }

    public BigDecimal getVIDecTaapt1i1() {
        return vIDecTaapt1i1;
    }

    public void setVIDecTaapt1i2(BigDecimal vIDecTaapt1i2) {
        this.vIDecTaapt1i2 = vIDecTaapt1i2;
    }

    public BigDecimal getVIDecTaapt1i2() {
        return vIDecTaapt1i2;
    }

    public void setVIDecTaapibs1(BigDecimal vIDecTaapibs1) {
        this.vIDecTaapibs1 = vIDecTaapibs1;
    }

    public BigDecimal getVIDecTaapibs1() {
        return vIDecTaapibs1;
    }

    public void setVIDecTaapibs2(BigDecimal vIDecTaapibs2) {
        this.vIDecTaapibs2 = vIDecTaapibs2;
    }

    public BigDecimal getVIDecTaapibs2() {
        return vIDecTaapibs2;
    }

    public void setVIDecTdaapt01(BigDecimal vIDecTdaapt01) {
        this.vIDecTdaapt01 = vIDecTdaapt01;
    }

    public BigDecimal getVIDecTdaapt01() {
        return vIDecTdaapt01;
    }

    public void setVIDecTaapqsp1(BigDecimal vIDecTaapqsp1) {
        this.vIDecTaapqsp1 = vIDecTaapqsp1;
    }

    public BigDecimal getVIDecTaapqsp1() {
        return vIDecTaapqsp1;
    }

    public void setVIDecTiaapt11(BigDecimal vIDecTiaapt11) {
        this.vIDecTiaapt11 = vIDecTiaapt11;
    }

    public BigDecimal getVIDecTiaapt11() {
        return vIDecTiaapt11;
    }

    public void setVIDecTiaapt12(BigDecimal vIDecTiaapt12) {
        this.vIDecTiaapt12 = vIDecTiaapt12;
    }

    public BigDecimal getVIDecTiaapt12() {
        return vIDecTiaapt12;
    }

    public void setVIDecTiaapib1(BigDecimal vIDecTiaapib1) {
        this.vIDecTiaapib1 = vIDecTiaapib1;
    }

    public BigDecimal getVIDecTiaapib1() {
        return vIDecTiaapib1;
    }

    public void setVIDecTiaapib2(BigDecimal vIDecTiaapib2) {
        this.vIDecTiaapib2 = vIDecTiaapib2;
    }

    public BigDecimal getVIDecTiaapib2() {
        return vIDecTiaapib2;
    }

    public void setVIDecTitacep1(BigDecimal vIDecTitacep1) {
        this.vIDecTitacep1 = vIDecTitacep1;
    }

    public BigDecimal getVIDecTitacep1() {
        return vIDecTitacep1;
    }

    public void setVIDecItabdia1(BigDecimal vIDecItabdia1) {
        this.vIDecItabdia1 = vIDecItabdia1;
    }

    public BigDecimal getVIDecItabdia1() {
        return vIDecItabdia1;
    }

    public void setVIDecIpiabdi1(BigDecimal vIDecIpiabdi1) {
        this.vIDecIpiabdi1 = vIDecIpiabdi1;
    }

    public BigDecimal getVIDecIpiabdi1() {
        return vIDecIpiabdi1;
    }

    public void setVIDecItaider1(BigDecimal vIDecItaider1) {
        this.vIDecItaider1 = vIDecItaider1;
    }

    public BigDecimal getVIDecItaider1() {
        return vIDecItaider1;
    }

    public void setVIDecIpiider1(BigDecimal vIDecIpiider1) {
        this.vIDecIpiider1 = vIDecIpiider1;
    }

    public BigDecimal getVIDecIpiider1() {
        return vIDecIpiider1;
    }

    public void setVIDecTicaago1(BigDecimal vIDecTicaago1) {
        this.vIDecTicaago1 = vIDecTicaago1;
    }

    public BigDecimal getVIDecTicaago1() {
        return vIDecTicaago1;
    }

    public void setVIDecItpiabd1(BigDecimal vIDecItpiabd1) {
        this.vIDecItpiabd1 = vIDecItpiabd1;
    }

    public BigDecimal getVIDecItpiabd1() {
        return vIDecItpiabd1;
    }

    public void setVIDecItpiide1(BigDecimal vIDecItpiide1) {
        this.vIDecItpiide1 = vIDecItpiide1;
    }

    public BigDecimal getVIDecItpiide1() {
        return vIDecItpiide1;
    }

    public void setVIDecPucaa5l1(BigDecimal vIDecPucaa5l1) {
        this.vIDecPucaa5l1 = vIDecPucaa5l1;
    }

    public BigDecimal getVIDecPucaa5l1() {
        return vIDecPucaa5l1;
    }

    public void setVIDecPucaa5l2(BigDecimal vIDecPucaa5l2) {
        this.vIDecPucaa5l2 = vIDecPucaa5l2;
    }

    public BigDecimal getVIDecPucaa5l2() {
        return vIDecPucaa5l2;
    }

    public void setVIDecIabuira1(BigDecimal vIDecIabuira1) {
        this.vIDecIabuira1 = vIDecIabuira1;
    }

    public BigDecimal getVIDecIabuira1() {
        return vIDecIabuira1;
    }

    public void setVIDecIavcare1(BigDecimal vIDecIavcare1) {
        this.vIDecIavcare1 = vIDecIavcare1;
    }

    public BigDecimal getVIDecIavcare1() {
        return vIDecIavcare1;
    }

    public void setVIDecMaaidao1(BigDecimal vIDecMaaidao1) {
        this.vIDecMaaidao1 = vIDecMaaidao1;
    }

    public BigDecimal getVIDecMaaidao1() {
        return vIDecMaaidao1;
    }

    public void setVIDecTiapovc1(BigDecimal vIDecTiapovc1) {
        this.vIDecTiapovc1 = vIDecTiapovc1;
    }

    public BigDecimal getVIDecTiapovc1() {
        return vIDecTiapovc1;
    }

    public void setVIDecVaagt1a1(BigDecimal vIDecVaagt1a1) {
        this.vIDecVaagt1a1 = vIDecVaagt1a1;
    }

    public BigDecimal getVIDecVaagt1a1() {
        return vIDecVaagt1a1;
    }

    public void setVIDecVaagt1a2(BigDecimal vIDecVaagt1a2) {
        this.vIDecVaagt1a2 = vIDecVaagt1a2;
    }

    public BigDecimal getVIDecVaagt1a2() {
        return vIDecVaagt1a2;
    }

    public void setVIDecVaagt0e1(BigDecimal vIDecVaagt0e1) {
        this.vIDecVaagt0e1 = vIDecVaagt0e1;
    }

    public BigDecimal getVIDecVaagt0e1() {
        return vIDecVaagt0e1;
    }

    public void setVIDecVaagt0o1(BigDecimal vIDecVaagt0o1) {
        this.vIDecVaagt0o1 = vIDecVaagt0o1;
    }

    public BigDecimal getVIDecVaagt0o1() {
        return vIDecVaagt0o1;
    }

    public void setVIDecSaagucc1(BigDecimal vIDecSaagucc1) {
        this.vIDecSaagucc1 = vIDecSaagucc1;
    }

    public BigDecimal getVIDecSaagucc1() {
        return vIDecSaagucc1;
    }

    public void setVIDecVaaqsdp1(BigDecimal vIDecVaaqsdp1) {
        this.vIDecVaaqsdp1 = vIDecVaaqsdp1;
    }

    public BigDecimal getVIDecVaaqsdp1() {
        return vIDecVaaqsdp1;
    }

    public void setVIDecIcmapuu1(BigDecimal vIDecIcmapuu1) {
        this.vIDecIcmapuu1 = vIDecIcmapuu1;
    }

    public BigDecimal getVIDecIcmapuu1() {
        return vIDecIcmapuu1;
    }

    public void setVIDecCardaac1(BigDecimal vIDecCardaac1) {
        this.vIDecCardaac1 = vIDecCardaac1;
    }

    public BigDecimal getVIDecCardaac1() {
        return vIDecCardaac1;
    }

    public void setVIDecIracvel1(BigDecimal vIDecIracvel1) {
        this.vIDecIracvel1 = vIDecIracvel1;
    }

    public BigDecimal getVIDecIracvel1() {
        return vIDecIracvel1;
    }

    public void setVIDecTiaovct1(BigDecimal vIDecTiaovct1) {
        this.vIDecTiaovct1 = vIDecTiaovct1;
    }

    public BigDecimal getVIDecTiaovct1() {
        return vIDecTiaovct1;
    }

    public void setVIDecOccctaa1(BigDecimal vIDecOccctaa1) {
        this.vIDecOccctaa1 = vIDecOccctaa1;
    }

    public BigDecimal getVIDecOccctaa1() {
        return vIDecOccctaa1;
    }

    public void setVIDecOcfctaa1(BigDecimal vIDecOcfctaa1) {
        this.vIDecOcfctaa1 = vIDecOcfctaa1;
    }

    public BigDecimal getVIDecOcfctaa1() {
        return vIDecOcfctaa1;
    }

    public void setVIDecCcaanrt1(BigDecimal vIDecCcaanrt1) {
        this.vIDecCcaanrt1 = vIDecCcaanrt1;
    }

    public BigDecimal getVIDecCcaanrt1() {
        return vIDecCcaanrt1;
    }

    public void setVIDecSfaalvd1(BigDecimal vIDecSfaalvd1) {
        this.vIDecSfaalvd1 = vIDecSfaalvd1;
    }

    public BigDecimal getVIDecSfaalvd1() {
        return vIDecSfaalvd1;
    }

    public void setVIDecDioenbv1(BigDecimal vIDecDioenbv1) {
        this.vIDecDioenbv1 = vIDecDioenbv1;
    }

    public BigDecimal getVIDecDioenbv1() {
        return vIDecDioenbv1;
    }

    public void setVIDecSfpaael1(BigDecimal vIDecSfpaael1) {
        this.vIDecSfpaael1 = vIDecSfpaael1;
    }

    public BigDecimal getVIDecSfpaael1() {
        return vIDecSfpaael1;
    }

    public void setVIDecAsfpaE1(BigDecimal vIDecAsfpaE1) {
        this.vIDecAsfpaE1 = vIDecAsfpaE1;
    }

    public BigDecimal getVIDecAsfpaE1() {
        return vIDecAsfpaE1;
    }

    public void setVIDecIaaadmi1(BigDecimal vIDecIaaadmi1) {
        this.vIDecIaaadmi1 = vIDecIaaadmi1;
    }

    public BigDecimal getVIDecIaaadmi1() {
        return vIDecIaaadmi1;
    }

    public void setVIDecIcmapru1(BigDecimal vIDecIcmapru1) {
        this.vIDecIcmapru1 = vIDecIcmapru1;
    }

    public BigDecimal getVIDecIcmapru1() {
        return vIDecIcmapru1;
    }

    public void setVIDecRsfiaaa1(BigDecimal vIDecRsfiaaa1) {
        this.vIDecRsfiaaa1 = vIDecRsfiaaa1;
    }

    public BigDecimal getVIDecRsfiaaa1() {
        return vIDecRsfiaaa1;
    }

    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("cIdeRfceeog1:");
        sb.append(this.getCIdeRfceeog1());
        sb.append(", ");
        
        sb.append("cIdeRfceeog2:");
        sb.append(this.getCIdeRfceeog2());
        sb.append(", ");
        
        sb.append("cIdeRfceeog3:"); 
        sb.append(this.getCIdeRfceeog3());
        sb.append(", ");
        
        sb.append("nDecEjercic1:"); 
        sb.append(this.getNDecEjercic1());
        sb.append(", ");
        
        sb.append("cDecCplearv1:");
        sb.append(this.getCDecCplearv1());
                
        return sb.toString();
    }
}

