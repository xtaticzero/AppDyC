package mx.gob.sat.siat.dyc.domain.cpr;

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
         { "txtnumerocpr", "txtfeciniper", "txtfecfinper", "txtusr", "txtnumerocprRet", "vdNcopmrb1Tmp", "vdRfceeog1Tmp",
           "vdDseistc1Tmp", "vfFasesic1Tmp", "vdDseasnc1", "idTipoPeriodo", "ejercicio" })
@XmlRootElement(name = "cprDTO")
public class CPRDTO extends Marshal implements Serializable {


    @SuppressWarnings("compatibility:-6039116050566182273")
    private static final long serialVersionUID = 1L;

    /**Entradas*/
    @XmlElement
    private int txtnumerocpr;
    @XmlElement
    private Date txtfeciniper;
    @XmlElement
    private Date txtfecfinper;
    @XmlElement
    private String txtusr;

    /**Salidas**/
    @XmlElement
    private int txtnumerocprRet;
    @XmlElement
    private String vdNcopmrb1Tmp;
    @XmlElement
    private String vdRfceeog1Tmp;
    @XmlElement
    private String vdDseistc1Tmp;
    @XmlElement
    private Date vfFasesic1Tmp;
    @XmlElement
    private String vdDseasnc1;

    /**Auxiliares**/
    @XmlElement
    private int idTipoPeriodo;
    @XmlElement
    private int ejercicio;

    public CPRDTO() {
        super();
    }

    public void setTxtnumerocpr(int txtnumerocpr) {
        this.txtnumerocpr = txtnumerocpr;
    }

    @XmlTransient
    public int getTxtnumerocpr() {
        return txtnumerocpr;
    }

    public void setTxtfeciniper(Date txtfeciniper) {
        if(txtfeciniper!=null){
            this.txtfeciniper = (Date)txtfeciniper.clone();
        }else{
                this.txtfeciniper = null;
            }
    }

    @XmlTransient
    public Date getTxtfeciniper() {
        if(txtfeciniper!= null)
        {
            return (Date)txtfeciniper.clone();
        }else{
            return null;
            }
    }

    public void setTxtfecfinper(Date txtfecfinper) {
        if(txtfecfinper!=null){
            this.txtfecfinper = (Date)txtfecfinper.clone();
        }else{
                this.txtfecfinper = null;
            }
    }

    @XmlTransient
    public Date getTxtfecfinper() {
        if(txtfecfinper != null){
            return (Date)txtfecfinper.clone();
        }else{
            return null;
            }
    }

    public void setTxtusr(String txtusr) {
        this.txtusr = txtusr;
    }

    @XmlTransient
    public String getTxtusr() {
        return txtusr;
    }

    public void setTxtnumerocprRet(int txtnumerocprRet) {
        this.txtnumerocprRet = txtnumerocprRet;
    }

    public int getTxtnumerocprRet() {
        return txtnumerocprRet;
    }

    public void setVdNcopmrb1Tmp(String vdNcopmrb1Tmp) {
        this.vdNcopmrb1Tmp = vdNcopmrb1Tmp;
    }

    public String getVdNcopmrb1Tmp() {
        return vdNcopmrb1Tmp;
    }

    public void setVdRfceeog1Tmp(String vdRfceeog1Tmp) {
        this.vdRfceeog1Tmp = vdRfceeog1Tmp;
    }

    public String getVdRfceeog1Tmp() {
        return vdRfceeog1Tmp;
    }

    public void setVdDseistc1Tmp(String vdDseistc1Tmp) {
        this.vdDseistc1Tmp = vdDseistc1Tmp;
    }

    public String getVdDseistc1Tmp() {
        return vdDseistc1Tmp;
    }

    public void setVfFasesic1Tmp(Date vfFasesic1Tmp) {
        if(vfFasesic1Tmp!= null)
        {
        this.vfFasesic1Tmp = (Date)vfFasesic1Tmp.clone();
        }else{
                this.vfFasesic1Tmp = null;
            }
    }

    public Date getVfFasesic1Tmp() {
        if(vfFasesic1Tmp!=null){
        return (Date)vfFasesic1Tmp.clone();
        }else{
            return null;
            }
    }

    public void setVdDseasnc1(String vdDseasnc1) {
        this.vdDseasnc1 = vdDseasnc1;
    }

    public String getVdDseasnc1() {
        return vdDseasnc1;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("Numero cpr: ").append(this.getTxtnumerocpr()).append(", ");
        sb.append("FEcha Inicio Periodo: ").append(this.getTxtfeciniper()).append(", ");
        sb.append("Fecha fin periodo: ").append(this.getTxtfecfinper()).append(", ");
        sb.append("Usuario: ").append(this.getTxtusr());

        return sb.toString();
    }

    public void setIdTipoPeriodo(int idTipoPeriodo) {
        this.idTipoPeriodo = idTipoPeriodo;
    }

    @XmlTransient
    public int getIdTipoPeriodo() {
        return idTipoPeriodo;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    @XmlTransient
    public int getEjercicio() {
        return ejercicio;
    }
}


