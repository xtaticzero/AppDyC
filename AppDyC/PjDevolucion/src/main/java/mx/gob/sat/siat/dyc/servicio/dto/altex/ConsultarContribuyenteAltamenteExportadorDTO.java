/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.altex;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
@XmlType(propOrder =
         { "naltex", "axoaltex", "apepatRsint", "delegacion", "cp", "calle", "tel", "fax", "colonia", "estado",
           "noInterior", "noExterior" })
public class ConsultarContribuyenteAltamenteExportadorDTO implements Serializable {

    @SuppressWarnings("compatibility:-4425222901531718293")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfc;
    private String rfc1;
    private String rfc2;

    //*****  Salidas  *****
    private int naltex;
    private int axoaltex;
    private String apepatRsint;
    private String delegacion;
    private int cp;
    private String calle;
    private String tel;
    private String fax;
    private String colonia;
    private String estado;
    private String noInterior;
    private String noExterior;

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @XmlTransient
    public String getRfc() {
        return rfc;
    }

    public void setRfc1(String rfc1) {
        this.rfc1 = rfc1;
    }

    @XmlTransient
    public String getRfc1() {
        return rfc1;
    }

    public void setRfc2(String rfc2) {
        this.rfc2 = rfc2;
    }

    @XmlTransient
    public String getRfc2() {
        return rfc2;
    }

    public void setNaltex(int naltex) {
        this.naltex = naltex;
    }

    public int getNaltex() {
        return naltex;
    }

    public void setAxoaltex(int axoaltex) {
        this.axoaltex = axoaltex;
    }

    public int getAxoaltex() {
        return axoaltex;
    }

    public void setApepatRsint(String apepatRsint) {
        this.apepatRsint = apepatRsint;
    }

    public String getApepatRsint() {
        return apepatRsint;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getCp() {
        return cp;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCalle() {
        return calle;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getColonia() {
        return colonia;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoExterior() {
        return noExterior;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("rfc:" + this.getRfc() + ", ");
        sb.append("rfc1:" + this.getRfc1() + ", ");
        sb.append("rfc2:" + this.getRfc2() + ", ");

        return sb.toString();
    }

}
