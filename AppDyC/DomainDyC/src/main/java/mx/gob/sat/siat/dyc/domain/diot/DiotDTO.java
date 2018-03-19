package mx.gob.sat.siat.dyc.domain.diot;

import java.io.Serializable;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * @author Israel Chavez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "txtrfc", "txtnumoperacion", "txtfechapresen", "txtusr", "vdRfceeog1", "vdNombre", "vdFperceh1",
           "vdCtdliea1", "vdCplearv1", "vdEjercic1" })
@XmlRootElement(name = "diotDTO")
public class DiotDTO extends Marshal implements Serializable {


    @SuppressWarnings("compatibility:6196524119499445201")
    private static final long serialVersionUID = 1L;

    /**Entradas*/
    @XmlElement
    private String txtrfc;
    @XmlElement
    private String txtnumoperacion;
    @XmlElement
    private String txtfechapresen;
    @XmlElement
    private String txtusr;

    /**Salidas**/
    @XmlElement
    private String vdRfceeog1;
    @XmlElement
    private String vdNombre;
    @XmlElement
    private Date vdFperceh1;
    @XmlElement
    private int vdCtdliea1;
    @XmlElement
    private int vdCplearv1;
    @XmlElement
    private int vdEjercic1;

    public DiotDTO() {
        super();
    }

    public void setTxtrfc(String txtrfc) {
        this.txtrfc = txtrfc;
    }

    @XmlTransient
    public String getTxtrfc() {
        return txtrfc;
    }

    public void setTxtnumoperacion(String txtnumoperacion) {
        this.txtnumoperacion = txtnumoperacion;
    }

    @XmlTransient
    public String getTxtnumoperacion() {
        return txtnumoperacion;
    }

    public void setTxtfechapresen(String txtfechapresen) {
        this.txtfechapresen = txtfechapresen;
    }

    @XmlTransient
    public String getTxtfechapresen() {
        return txtfechapresen;
    }

    @XmlTransient
    public void setTxtusr(String txtusr) {
        this.txtusr = txtusr;
    }

    public String getTxtusr() {
        return txtusr;
    }

    public void setVdRfceeog1(String vdRfceeog1) {
        this.vdRfceeog1 = vdRfceeog1;
    }

    public String getVdRfceeog1() {
        return vdRfceeog1;
    }

    public void setVdNombre(String vdNombre) {
        this.vdNombre = vdNombre;
    }

    public String getVdNombre() {
        return vdNombre;
    }

    public void setVdFperceh1(Date vdFperceh1) {
        if (vdFperceh1 != null) {
            this.vdFperceh1 = (Date)vdFperceh1.clone();
        } else {
            this.vdFperceh1 = null;
        }   
    }

    public Date getVdFperceh1() {
        if (vdFperceh1 != null) {
            return (Date)vdFperceh1.clone();
        } else {
            return null;
        }   
    }

    public void setVdCtdliea1(int vdCtdliea1) {
        this.vdCtdliea1 = vdCtdliea1;
    }

    public int getVdCtdliea1() {
        return vdCtdliea1;
    }

    public void setVdCplearv1(int vdCplearv1) {
        this.vdCplearv1 = vdCplearv1;
    }

    public int getVdCplearv1() {
        return vdCplearv1;
    }

    public void setVdEjercic1(int vdEjercic1) {
        this.vdEjercic1 = vdEjercic1;
    }

    public int getVdEjercic1() {
        return vdEjercic1;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("txtrfc: ").append(this.getTxtrfc()).append(", ");
        sb.append("txtnumoperacion: ").append(this.getTxtnumoperacion()).append(", ");
        sb.append("txtfechapresen: ").append(this.getTxtfechapresen()).append(", ");
        sb.append("txtusr: ").append(this.getTxtusr());
        return sb.toString();
    }

}


