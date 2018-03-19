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
 * @since   06/08/2013
 *
 */
@XmlType(propOrder =
         { "numeroOperacion", "tdiepco1", "fperceh1", "tipoIngreso", "rfcRetExp", "importe1", "importe4" })
public class ConsultarDeclaracionISRPersonaFisica1317aDecDTO implements Serializable {

    @SuppressWarnings("compatibility:7699801733652408430")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String dDecRfceeog1;
    private int nDecEjercic1;
    private int cDecCplearv1;

    //*****  Salidas  *****
    private BigDecimal numeroOperacion;
    private int tdiepco1;
    private Date fperceh1;
    private String tipoIngreso;
    private String rfcRetExp;
    private BigDecimal importe1;
    private BigDecimal importe4;

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

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setRfcRetExp(String rfcRetExp) {
        this.rfcRetExp = rfcRetExp;
    }

    public String getRfcRetExp() {
        return rfcRetExp;
    }

    public void setImporte1(BigDecimal importe1) {
        this.importe1 = importe1;
    }

    public BigDecimal getImporte1() {
        return importe1;
    }

    public void setImporte4(BigDecimal importe4) {
        this.importe4 = importe4;
    }

    public BigDecimal getImporte4() {
        return importe4;
    }

    public String getParameterReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("dDecRfceeog1:").append(this.getDDecRfceeog1()).append(", ");
        sb.append("nDecEjercic1:").append(this.getNDecEjercic1()).append(", ");
        sb.append("cDecCplearv1:").append(this.getCDecCplearv1());
        return sb.toString();
    }

}
