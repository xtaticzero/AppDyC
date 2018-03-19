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

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
@XmlType(propOrder =
         { "nEejercicio", "cNPeriodo", "tdiepco1", "fperceh1", "nNumeroOperacion", "formulario", "i111021", "i111024" })
public class ConsultarDeterminacionDeImpuestosCdiDpdifDTO implements Serializable {

    @SuppressWarnings("compatibility:8697642701854700808")
    private static final long serialVersionUID = 1L;

    private String cIdeRfceeog1;
    private String cIdeRfceeog2;
    private String cIdeRfceeog3;
    private String cDecCplearv1;
    private int nDecEjercic1;
    private String cOblCcloanv1;

    private int nEejercicio;
    private String cNPeriodo;
    private String tdiepco1;
    private Date fperceh1;
    private int nNumeroOperacion;
    private String formulario;
    private int i111021;
    private int i111024;

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

    public void setCOblCcloanv1(String cOblCcloanv1) {
        this.cOblCcloanv1 = cOblCcloanv1;
    }

    @XmlTransient
    public String getCOblCcloanv1() {
        return cOblCcloanv1;
    }

    public void setNEejercicio(int nEejercicio) {
        this.nEejercicio = nEejercicio;
    }

    public int getNEejercicio() {
        return nEejercicio;
    }

    public void setCNPeriodo(String cNPeriodo) {
        this.cNPeriodo = cNPeriodo;
    }

    public String getCNPeriodo() {
        return cNPeriodo;
    }

    public void setTdiepco1(String tdiepco1) {
        this.tdiepco1 = tdiepco1;
    }

    public String getTdiepco1() {
        return tdiepco1;
    }

    public void setFperceh1(Date fperceh1) {
        this.fperceh1 = new Date(fperceh1.getTime());
    }

    public Date getFperceh1() {
        return new Date(fperceh1.getTime());
    }

    public void setNNumeroOperacion(int nNumeroOperacion) {
        this.nNumeroOperacion = nNumeroOperacion;
    }

    public int getNNumeroOperacion() {
        return nNumeroOperacion;
    }

    public void setFORMULARIO(String formulario) {
        this.formulario = formulario;
    }

    public String getFORMULARIO() {
        return formulario;
    }

    public void setI111021(int i111021) {
        this.i111021 = i111021;
    }

    public int getI111021() {
        return i111021;
    }

    public void setI111024(int i111024) {
        this.i111024 = i111024;
    }

    public int getI111024() {
        return i111024;
    }

    public String getParameterReport() {

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
        sb.append("cDecCplearv1:");
        sb.append(this.getCDecCplearv1());
        sb.append(", ");
        sb.append("nDecEjercic1:");
        sb.append(this.getNDecEjercic1());
        sb.append(", ");
        sb.append("cOblCcloanv1:");
        sb.append(this.getCOblCcloanv1());
        return sb.toString();
    }
}
