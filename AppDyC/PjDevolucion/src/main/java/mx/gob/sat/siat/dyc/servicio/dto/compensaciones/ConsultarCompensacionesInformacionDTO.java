/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.compensaciones;

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
@XmlType(propOrder = { "ncuomne1", "fperceh1", "ismoplo1", "nruemse1", "freecsh1" })
public class ConsultarCompensacionesInformacionDTO implements Serializable {

    @SuppressWarnings("compatibility:-4858802756054472181")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private String psfieaa1;
    private String psffeaa1;
    private int cStiCcloanv1;

    //*****  Salidas  *****
    private String ncuomne1;
    private Date fperceh1;
    private BigDecimal ismoplo1;
    private String nruemse1;
    private Date freecsh1;


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

    public void setPsfieaa1(String psfieaa1) {
        this.psfieaa1 = psfieaa1;
    }

    @XmlTransient
    public String getPsfieaa1() {
        return psfieaa1;
    }

    public void setPsffeaa1(String psffeaa1) {
        this.psffeaa1 = psffeaa1;
    }

    @XmlTransient
    public String getPsffeaa1() {
        return psffeaa1;
    }

    public void setNcuomne1(String ncuomne1) {
        this.ncuomne1 = ncuomne1;
    }

    public String getNcuomne1() {
        return ncuomne1;
    }

    public void setFperceh1(Date fperceh1) {
        if (fperceh1 != null) {
            this.fperceh1 = (Date)fperceh1.clone();
        } else {
            this.fperceh1 = null;
        }   
    }

    public Date getFperceh1() {
        if (fperceh1 != null) {
            return (Date)fperceh1.clone();
        } else {
            return null;
        }   
    }

    public void setIsmoplo1(BigDecimal ismoplo1) {
        this.ismoplo1 = ismoplo1;
    }

    public BigDecimal getIsmoplo1() {
        return ismoplo1;
    }

    public void setNruemse1(String nruemse1) {
        this.nruemse1 = nruemse1;
    }

    public String getNruemse1() {
        return nruemse1;
    }

    public void setFreecsh1(Date freecsh1) {
        if (freecsh1 != null) {
            this.freecsh1 = (Date)freecsh1.clone();
        } else {
            this.freecsh1 = null;
        }   
    }

    public Date getFreecsh1() {
        if (freecsh1 != null) {
            return (Date)freecsh1.clone();
        } else {
            return null;
        }   
    }

    public void setCStiCcloanv1(int cStiCcloanv1) {
        this.cStiCcloanv1 = cStiCcloanv1;
    }

    public int getCStiCcloanv1() {
        return cStiCcloanv1;
    }

    public Date getFperceh11() {
        if (fperceh1 != null) {
            return (Date)fperceh1.clone();
        } else {
            return null;
        }   
    }

    public Date getFreecsh11() {
        if (freecsh1 != null) {
            return (Date)freecsh1.clone();
        } else {
            return null;
        }   
    }
    
    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("rfceeog1:").append(this.getRfceeog1()).append(", ");
        sb.append("rfceeog2:").append(this.getRfceeog2()).append(", ");
        sb.append("rfceeog3:").append(this.getRfceeog3()).append(", ");
        sb.append("psfieaa1:").append(this.getPsfieaa1()).append(", ");
        sb.append("psffeaa1:").append(this.getPsffeaa1()).append(", ");
        sb.append("c_sti_ccloanv1:").append(this.getCStiCcloanv1());

        return sb.toString();
    }
}
