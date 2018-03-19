/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.devoluciones;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
@XmlType(propOrder = { "cilmapv1", "ncuomne1", "freecch1", "ismoplo1", "nruemse1", "iadmuep1" })
public class ConsultarDevolucionesDTO implements Serializable {

    @SuppressWarnings("compatibility:-8174602341177177384")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private String psfieaa1;
    private int cDecCplearv1;
    private String psffeaa1;
    private int cDecCplearv12;
    private int cStiCtslria1;

    //*****  Salidas  *****
    private String cilmapv1;
    private String ncuomne1;
    private Date freecch1;
    private BigDecimal ismoplo1;
    private String nruemse1;
    private BigDecimal iadmuep1;

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

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public int getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setPsffeaa1(String psffeaa1) {
        this.psffeaa1 = psffeaa1;
    }

    @XmlTransient
    public String getPsffeaa1() {
        return psffeaa1;
    }

    public void setCDecCplearv12(int cDecCplearv12) {
        this.cDecCplearv12 = cDecCplearv12;
    }

    @XmlTransient
    public int getCDecCplearv12() {
        return cDecCplearv12;
    }

    public void setCStiCtslria1(int cStiCtslria1) {
        this.cStiCtslria1 = cStiCtslria1;
    }

    @XmlTransient
    public int getCStiCtslria1() {
        return cStiCtslria1;
    }

    public void setCilmapv1(String cilmapv1) {
        this.cilmapv1 = cilmapv1;
    }

    public String getCilmapv1() {
        return cilmapv1;
    }

    public void setNcuomne1(String ncuomne1) {
        this.ncuomne1 = ncuomne1;
    }

    public String getNcuomne1() {
        return ncuomne1;
    }

    public void setFreecch1(Date freecch1) {
        if(freecch1!=null){
        this.freecch1 = (Date)freecch1.clone();
        }else{
                this.freecch1 = null;
            }
    }

    public Date getFreecch1() {
        if(freecch1!= null){
        return (Date)freecch1.clone();
        }else{
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

    public void setIadmuep1(BigDecimal iadmuep1) {
        this.iadmuep1 = iadmuep1;
    }

    public BigDecimal getIadmuep1() {
        return iadmuep1;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("rfceeog1:").append(this.getRfceeog1()).append(", ");
        sb.append("rfceeog2:").append(this.getRfceeog2()).append(", ");
        sb.append("rfceeog3:").append(this.getRfceeog3()).append(", ");
        sb.append("psfieaa1:").append(this.getPsfieaa1()).append(", ");
        sb.append("c_dec_cplearv1:").append(this.getCDecCplearv1()).append(", ");
        sb.append("psffeaa1:").append(this.getPsffeaa1()).append(", ");
        sb.append("c_sti_ctslria1:").append(this.getCStiCtslria1());

        return sb.toString();
    }

}
