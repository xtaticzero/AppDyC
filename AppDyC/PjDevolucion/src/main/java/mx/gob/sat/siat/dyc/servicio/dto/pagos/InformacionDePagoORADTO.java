/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.pagos;

import java.io.Serializable;

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
         { "f_dec_fcieamc1", "f_dec_fperceh1", "i_pag_icmapru1", "i_pag_ifmapvu1", "i_pag_acpolmi1", "c_dec_ctdliea1" })
public class InformacionDePagoORADTO implements Serializable {

    @SuppressWarnings("compatibility:-8726262898935579750")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String cIdeRfceeog1;
    private String cIdeRfceeog2;
    private String cIdeRfceeog3;
    private int cDecCrleanv1;
    private String cDecCplearv1;
    private int nDecEjercic1;

    //*****  Salidas  *****
    private Date fDecFcieamc1;
    private Date fDecFperceh1;
    private int iPagIcmapru1;
    private int iPagIfmapvu1;
    private int iPagAcpolmi1;
    private int cDecCtdliea1;

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

    public void setCDecCrleanv1(int cDecCrleanv1) {
        this.cDecCrleanv1 = cDecCrleanv1;
    }

    @XmlTransient
    public int getCDecCrleanv1() {
        return cDecCrleanv1;
    }

    public void setCDecCplearv1(String cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public String getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setNDecEjercic1(int nDecEjercic1) {
        this.nDecEjercic1 = nDecEjercic1;
    }

    @XmlTransient
    public int getNDecEjercic1() {
        return nDecEjercic1;
    }

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

    public void setIPagIcmapru1(int iPagIcmapru1) {
        this.iPagIcmapru1 = iPagIcmapru1;
    }

    public int getIPagIcmapru1() {
        return iPagIcmapru1;
    }

    public void setIPagIfmapvu1(int iPagIfmapvu1) {
        this.iPagIfmapvu1 = iPagIfmapvu1;
    }

    public int getIPagIfmapvu1() {
        return iPagIfmapvu1;
    }

    public void setIPagAcpolmi1(int iPagAcpolmi1) {
        this.iPagAcpolmi1 = iPagAcpolmi1;
    }

    public int getIPagAcpolmi1() {
        return iPagAcpolmi1;
    }

    public void setCDecCtdliea1(int cDecCtdliea1) {
        this.cDecCtdliea1 = cDecCtdliea1;
    }

    public int getCDecCtdliea1() {
        return cDecCtdliea1;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("c_ide_rfceeog1:").append(this.getCIdeRfceeog1()).append(", ");
        sb.append("c_ide_rfceeog2:").append(this.getCIdeRfceeog2()).append(", ");
        sb.append("c_ide_rfceeog3:").append(this.getCIdeRfceeog3()).append(", ");
        sb.append("c_n_periodo:").append(this.getCDecCplearv1()).append(", ");
        sb.append("n_ejercicio:").append(this.getNDecEjercic1());

        return sb.toString();
    }
}
