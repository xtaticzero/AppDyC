/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.domain.altex;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


/**
 *
 * @author Israel Chavez
 * @since 24/09/2013
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "txtnumalt", "txtrfc", "txtusr", "vdNumalt", "vdNombre", "vdRfceeog1", "vdVigencia", "vdAnioreg", "vdDomreg" })
@XmlRootElement(name= "spConsultarAltexDTO")
public class SpConsultarAltexDTO extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:6755814413271607710")
    private static final long serialVersionUID = 1L;

    /**Entradas**/
    @XmlElement
    private int txtnumalt;
    @XmlElement
    private String txtrfc;
    @XmlElement
    private String txtusr;

    /**Salidas**/
    @XmlElement
    private int vdNumalt;
    @XmlElement
    private String vdNombre;
    @XmlElement
    private String vdRfceeog1;
    @XmlElement
    private String vdVigencia;
    @XmlElement
    private int vdAnioreg;
    @XmlElement
    private String vdDomreg;



    public void setTxtnumalt(int txtnumalt) {
        this.txtnumalt = txtnumalt;
    }
    @XmlTransient
    public int getTxtnumalt() {
        return txtnumalt;
    }

    public void setTxtrfc(String txtrfc) {
        this.txtrfc = txtrfc;
    }

    @XmlTransient
    public String getTxtrfc() {
        return txtrfc;
    }

    public void setTxtusr(String txtusr) {
        this.txtusr = txtusr;
    }

    @XmlTransient
    public String getTxtusr() {
        return txtusr;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("txtnumalt:" + this.getTxtnumalt() + ", ");
        sb.append("txtrfc:" + this.getTxtrfc() + ", ");
        sb.append("txtusr:" + this.getTxtusr() + ", ");

        return sb.toString();
    }

    public void setVdNumalt(int vdNumalt) {
        this.vdNumalt = vdNumalt;
    }

    public int getVdNumalt() {
        return vdNumalt;
    }

    public void setVdNombre(String vdNombre) {
        this.vdNombre = vdNombre;
    }

    public String getVdNombre() {
        return vdNombre;
    }

    public void setVdRfceeog1(String vdRfceeog1) {
        this.vdRfceeog1 = vdRfceeog1;
    }

    public String getVdRfceeog1() {
        return vdRfceeog1;
    }

    public void setVdVigencia(String vdVigencia) {
        this.vdVigencia = vdVigencia;
    }

    public String getVdVigencia() {
        return vdVigencia;
    }

    public void setVdAnioreg(int vdAnioreg) {
        this.vdAnioreg = vdAnioreg;
    }

    public int getVdAnioreg() {
        return vdAnioreg;
    }

    public void setVdDomreg(String vdDomreg) {
        this.vdDomreg = vdDomreg;
    }

    public String getVdDomreg() {
        return vdDomreg;
    }
}

