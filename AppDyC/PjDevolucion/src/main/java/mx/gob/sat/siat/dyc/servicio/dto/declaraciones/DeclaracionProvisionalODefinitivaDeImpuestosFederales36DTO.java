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


/**
 * Parametros de salida para Interfaz
 * ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales
 * [DWH_DFSD-36]
 * @author J. Isaac Carbajal Bernal
 */
public class DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO implements Serializable {

    @SuppressWarnings("compatibility:-5223160195042357467")
    private static final long serialVersionUID = 1L;

    /**n_dec_noupmee1*/
    private Integer nDecNoupmee1; 
    /**f_dec_fperceh1*/
    private Date fDecFperceh1;
    /**f_dec_fcieamc1*/
    private Date fDecFcieamc1;
    /**c_dec_ctdliea1*/
    private String cDecCtdliea1;
    /**c_obl_ccloanv1*/
    private String cOblCcloanv1;
    /**d_dec_drceeos1*/
    private String dDecDrceeos1; 
    /**i_pag_ifmapvu1*/
    private BigDecimal iPagIfmapvu1;
    /**i_pag_icmapru1*/
    private BigDecimal iPagIcmapru1; 
    /**i_pag_capaada1*/
    private Integer iPagCapaada1; 
    /**i_pag_carader1*/
    private Integer iPagCarader1; 
    /**i_pag_camcadu1*/
    private Integer iPagCamcadu1;
    /**i_pag_tcootna1*/
    private Integer iPagTcootna1;
    /**i_pag_acasprl1*/
    private Integer iPagAcasprl1; 
    /**i_pag_seumbps1*/
    private Integer iPagSeumbps1; 
    /**i_pag_acpolmi1*/
    private BigDecimal iPagAcpolmi1; 
    /**i_pag_acidspm1*/
    private Integer iPagAcidspm1; 
    /**i_pag_adatpiu1*/
    private Integer iPagAdatpiu1; 
    /**i_pag_auiccps1*/
    private Integer iPagAuiccps1; 
    /**i_pag_apaprgl1*/
    private Integer iPagApaprgl1; 
    /**i_pag_aoeptsl1*/
    private Integer iPagAoeptsl1; 
    /**i_pag_actpeel1*/
    private Integer iPagActpeel1; 
    /**i_pag_admpial1*/
    private Integer iPagAdmpial1;
    /**i_pag_taoptla1*/
    private Integer iPagTaoptla1;
    /**f_pag_fpraeae1*/
    private Date fPagFpraeae1; 
    /**i_pag_mpaoann1*/
    private Integer iPagMpaoann1; 
    /**i_pag_mpatoan1*/
    private Integer iPagMpatoan1; 
    /**i_pag_ccaanrt1*/
    private Integer iPagCcaanrt1;
    /**d_pag_appalri1*/
    private String dPagAppalri1; 
    /**i_pag_pi1pamr1*/
    private Integer iPagPi1pamr1; 
    /**i_pag_pi1pamr2*/
    private Integer iPagPi1pamr2; 
    /**i_pag_cfaanvt1*/
    private Integer iPagCfaanvt1;
    /**i_pag_cpaangt1*/
    private Integer iPagCpaangt1;
    /**c_ubi_ceflnea1*/
    private String cUbiCeflnea1; 

    public void setNDecNoupmee1(Integer nDecNoupmee1) {
        this.nDecNoupmee1 = nDecNoupmee1;
    }

    public Integer getNDecNoupmee1() {
        return nDecNoupmee1;
    }

    public void setFDecFperceh1(Date fDecFperceh1) {
        this.fDecFperceh1 = new Date(fDecFperceh1.getTime());
    }

    public Date getFDecFperceh1() {
        return new Date(fDecFperceh1.getTime());
    }

    public void setFDecFcieamc1(Date fDecFcieamc1) {
        this.fDecFcieamc1 = new Date(fDecFcieamc1.getTime());
    }

    public Date getFDecFcieamc1() {
        return new Date(fDecFcieamc1.getTime());
    }

    public void setCDecCtdliea1(String cDecCtdliea1) {
        this.cDecCtdliea1 = cDecCtdliea1;
    }

    public String getCDecCtdliea1() {
        return cDecCtdliea1;
    }

    public void setCOblCcloanv1(String cOblCcloanv1) {
        this.cOblCcloanv1 = cOblCcloanv1;
    }

    public String getCOblCcloanv1() {
        return cOblCcloanv1;
    }

    public void setDDecDrceeos1(String dDecDrceeos1) {
        this.dDecDrceeos1 = dDecDrceeos1;
    }

    public String getDDecDrceeos1() {
        return dDecDrceeos1;
    }

    public void setIPagIfmapvu1(BigDecimal iPagIfmapvu1) {
        this.iPagIfmapvu1 = iPagIfmapvu1;
    }

    public BigDecimal getIPagIfmapvu1() {
        return iPagIfmapvu1;
    }

    public void setIPagCapaada1(Integer iPagCapaada1) {
        this.iPagCapaada1 = iPagCapaada1;
    }

    public Integer getIPagCapaada1() {
        return iPagCapaada1;
    }

    public void setIPagCarader1(Integer iPagCarader1) {
        this.iPagCarader1 = iPagCarader1;
    }

    public Integer getIPagCarader1() {
        return iPagCarader1;
    }

    public void setIPagCamcadu1(Integer iPagCamcadu1) {
        this.iPagCamcadu1 = iPagCamcadu1;
    }

    public Integer getIPagCamcadu1() {
        return iPagCamcadu1;
    }

    public void setIPagTcootna1(Integer iPagTcootna1) {
        this.iPagTcootna1 = iPagTcootna1;
    }

    public Integer getIPagTcootna1() {
        return iPagTcootna1;
    }

    public void setIPagAcasprl1(Integer iPagAcasprl1) {
        this.iPagAcasprl1 = iPagAcasprl1;
    }

    public Integer getIPagAcasprl1() {
        return iPagAcasprl1;
    }

    public void setIPagSeumbps1(Integer iPagSeumbps1) {
        this.iPagSeumbps1 = iPagSeumbps1;
    }

    public Integer getIPagSeumbps1() {
        return iPagSeumbps1;
    }

    public void setIPagAcpolmi1(BigDecimal iPagAcpolmi1) {
        this.iPagAcpolmi1 = iPagAcpolmi1;
    }

    public BigDecimal getIPagAcpolmi1() {
        return iPagAcpolmi1;
    }

    public void setIPagAcidspm1(Integer iPagAcidspm1) {
        this.iPagAcidspm1 = iPagAcidspm1;
    }

    public Integer getIPagAcidspm1() {
        return iPagAcidspm1;
    }

    public void setIPagAdatpiu1(Integer iPagAdatpiu1) {
        this.iPagAdatpiu1 = iPagAdatpiu1;
    }

    public Integer getIPagAdatpiu1() {
        return iPagAdatpiu1;
    }

    public void setIPagAuiccps1(Integer iPagAuiccps1) {
        this.iPagAuiccps1 = iPagAuiccps1;
    }

    public Integer getIPagAuiccps1() {
        return iPagAuiccps1;
    }

    public void setIPagApaprgl1(Integer iPagApaprgl1) {
        this.iPagApaprgl1 = iPagApaprgl1;
    }

    public Integer getIPagApaprgl1() {
        return iPagApaprgl1;
    }

    public void setIPagAoeptsl1(Integer iPagAoeptsl1) {
        this.iPagAoeptsl1 = iPagAoeptsl1;
    }

    public Integer getIPagAoeptsl1() {
        return iPagAoeptsl1;
    }

    public void setIPagActpeel1(Integer iPagActpeel1) {
        this.iPagActpeel1 = iPagActpeel1;
    }

    public Integer getIPagActpeel1() {
        return iPagActpeel1;
    }

    public void setIPagAdmpial1(Integer iPagAdmpial1) {
        this.iPagAdmpial1 = iPagAdmpial1;
    }

    public Integer getIPagAdmpial1() {
        return iPagAdmpial1;
    }

    public void setIPagTaoptla1(Integer iPagTaoptla1) {
        this.iPagTaoptla1 = iPagTaoptla1;
    }

    public Integer getIPagTaoptla1() {
        return iPagTaoptla1;
    }

    public void setFPagFpraeae1(Date fPagFpraeae1) {
        this.fPagFpraeae1 = new Date(fPagFpraeae1.getTime());
    }

    public Date getFPagFpraeae1() {
        return new Date(fPagFpraeae1.getTime());
    }

    public void setIPagMpaoann1(Integer iPagMpaoann1) {
        this.iPagMpaoann1 = iPagMpaoann1;
    }

    public Integer getIPagMpaoann1() {
        return iPagMpaoann1;
    }

    public void setIPagMpatoan1(Integer iPagMpatoan1) {
        this.iPagMpatoan1 = iPagMpatoan1;
    }

    public Integer getIPagMpatoan1() {
        return iPagMpatoan1;
    }

    public void setIPagCcaanrt1(Integer iPagCcaanrt1) {
        this.iPagCcaanrt1 = iPagCcaanrt1;
    }

    public Integer getIPagCcaanrt1() {
        return iPagCcaanrt1;
    }

    public void setDPagAppalri1(String dPagAppalri1) {
        this.dPagAppalri1 = dPagAppalri1;
    }

    public String getDPagAppalri1() {
        return dPagAppalri1;
    }

    public void setIPagPi1pamr1(Integer iPagPi1pamr1) {
        this.iPagPi1pamr1 = iPagPi1pamr1;
    }

    public Integer getIPagPi1pamr1() {
        return iPagPi1pamr1;
    }

    public void setIPagPi1pamr2(Integer iPagPi1pamr2) {
        this.iPagPi1pamr2 = iPagPi1pamr2;
    }

    public Integer getIPagPi1pamr2() {
        return iPagPi1pamr2;
    }

    public void setIPagCfaanvt1(Integer iPagCfaanvt1) {
        this.iPagCfaanvt1 = iPagCfaanvt1;
    }

    public Integer getIPagCfaanvt1() {
        return iPagCfaanvt1;
    }

    public void setIPagCpaangt1(Integer iPagCpaangt1) {
        this.iPagCpaangt1 = iPagCpaangt1;
    }

    public Integer getIPagCpaangt1() {
        return iPagCpaangt1;
    }

    public void setCUbiCeflnea1(String cUbiCeflnea1) {
        this.cUbiCeflnea1 = cUbiCeflnea1;
    }

    public String getCUbiCeflnea1() {
        return cUbiCeflnea1;
    }

    public void setIPagIcmapru1(BigDecimal iPagIcmapru1) {
        this.iPagIcmapru1 = iPagIcmapru1;
    }

    public BigDecimal getIPagIcmapru1() {
        return iPagIcmapru1;
    }
}


