package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.util.Date;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 02/05/2014
 * @version 0.1
 * VO para la interfaz ConsultarDeclaracionProvixionalODefinitivaDelImpuestosFederales[DWH_DFSD-36]
 */
public class DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO{
    //*****  Salidas  *****
    /**f_dec_fcieamc1*/
    private Date fDecFcieamc1;
    /**f_dec_fperceh1 */
    private Date fDecFperceh1;
    /**c_dec_ctdliea1*/
    private String cDecCtdliea1;
    /**c_obl_ccloanv1*/
    private String cOblCcloanv1;
    /**d_dec_drceeos1*/
    private String dDecDrceeos1;
    /**c_dec_ceflsia1 */
    private String cDecCeflsia1;
    /**i_dec_meosntt1*/
    private Long iDecMeosntt1;
    
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

    public void setCDecCeflsia1(String cDecCeflsia1) {
        this.cDecCeflsia1 = cDecCeflsia1;
    }

    public String getCDecCeflsia1() {
        return cDecCeflsia1;
    }

    public void setIDecMeosntt1(Long iDecMeosntt1) {
        this.iDecMeosntt1 = iDecMeosntt1;
    }

    public Long getIDecMeosntt1() {
        return iDecMeosntt1;
    }
}
