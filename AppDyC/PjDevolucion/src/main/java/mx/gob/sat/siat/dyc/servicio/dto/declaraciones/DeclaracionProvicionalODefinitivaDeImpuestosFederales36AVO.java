package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.math.BigDecimal;

import java.util.Date;


/**
 * Parametros de salida para Interfaz
 * ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesA
 * [DWH_DFSD-36]
 * @author J. Isaac Carbajal Bernal
 */
public class DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO {
    
    /**f_dec_fcieamc1*/
    private Date fDecFcieamc1;
    /**f_dec_fperceh1*/
    private Date fDecFperceh1;
    /**c_dec_ctdliea1*/
    private String cDecCtdliea1;
    /**c_obl_ccloanv1*/
    private String cOblCcloanv1;
    /**d_dec_drceeos1*/
    private String dDecDrceeos1;
    /**d_obl_dpeesrc1*/
    private String dOblDpeesrc1;
    /**c_obl_ccloanv2*/
    private String cOblCcloanv2;
    /**i_dec_isqamau1*/
    private BigDecimal iDecIsqamau1;
    /**i_dec_iosfmra1*/
    private Long iDecIosfmra1;
    /**f_dec_fddmsfe1*/
    private Date fDecFddmsfe1;
    /**i_dec_rvhaaea1*/
    private Long iDecRvhaaea1;
    /**i_dec_rvaaaea1*/
    private Long iDecRvaaaea1;
    
    public void setFDecFcieamc1(Date fDecFcieamc1) {
        if (fDecFcieamc1 != null) {
            this.fDecFcieamc1 = (Date)fDecFcieamc1.clone();
        } else {
            this.fDecFcieamc1 = null;
        }
    }

    public Date getFDecFcieamc1() {
        if (fDecFcieamc1 != null) {
            return (Date)fDecFcieamc1.clone();
        } else {
            return null;
        }
    }

    public void setFDecFperceh1(Date fDecFperceh1) {
        if (fDecFperceh1 != null) {
            this.fDecFperceh1 = (Date)fDecFperceh1.clone();
        } else {
            this.fDecFperceh1 = null;
        }
    }

    public Date getFDecFperceh1() {
        if (fDecFperceh1 != null) {
            return (Date)fDecFperceh1.clone();
        } else {
            return null;
        }
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

    public void setDOblDpeesrc1(String dOblDpeesrc1) {
        this.dOblDpeesrc1 = dOblDpeesrc1;
    }

    public String getDOblDpeesrc1() {
        return dOblDpeesrc1;
    }

    public void setIDecIosfmra1(Long iDecIosfmra1) {
        this.iDecIosfmra1 = iDecIosfmra1;
    }

    public Long getIDecIosfmra1() {
        return iDecIosfmra1;
    }

    public void setFDecFddmsfe1(Date fDecFddmsfe1) {
        if (fDecFddmsfe1 != null) {
            this.fDecFddmsfe1 = (Date)fDecFddmsfe1.clone();
        } else {
            this.fDecFddmsfe1 = null;
        }
    }

    public Date getFDecFddmsfe1() {
        if (fDecFddmsfe1 != null) {
            return (Date)fDecFddmsfe1.clone();
        } else {
            return null;
        }
    }

    public void setIDecRvhaaea1(Long iDecRvhaaea1) {
        this.iDecRvhaaea1 = iDecRvhaaea1;
    }

    public Long getIDecRvhaaea1() {
        return iDecRvhaaea1;
    }

    public void setIDecRvaaaea1(Long iDecRvaaaea1) {
        this.iDecRvaaaea1 = iDecRvaaaea1;
    }

    public Long getIDecRvaaaea1() {
        return iDecRvaaaea1;
    }

    public void setCOblCcloanv2(String cOblCcloanv2) {
        this.cOblCcloanv2 = cOblCcloanv2;
    }

    public String getCOblCcloanv2() {
        return cOblCcloanv2;
    }

    public void setIDecIsqamau1(BigDecimal iDecIsqamau1) {
        this.iDecIsqamau1 = iDecIsqamau1;
    }

    public BigDecimal getIDecIsqamau1() {
        return iDecIsqamau1;
    }
}
