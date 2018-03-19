/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.dictamenes;

import java.io.Serializable;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
@XmlType(propOrder =
         { "ejercicio_fiscal", "id_sector", "tipo_dictamen", "fecha_recepcion", "obligado", "no_cpr", "rfc_cpr",
           "nombre_cpr" })
public class ConsultarDictamenesDTO implements Serializable {

    @SuppressWarnings("compatibility:-1391045680755734963")
    private static final long serialVersionUID = 1L;
    
    //*****  Entradas  *****
    private String rfcContribuyente;
    private String rfcContribuyente2;
    private String rfcContribuyente3;

    //***** I/O
    private int ejercicioFiscal;

    //*****  Salidas  *****
    private String idSector;
    private String tipoDictamen;
    private Date fechaRecepcion;
    private String obligado;
    private String noCpr;
    private String rfcCpr;
    private String nombreCpr;

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    @XmlTransient
    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente2(String rfcContribuyente2) {
        this.rfcContribuyente2 = rfcContribuyente2;
    }

    @XmlTransient
    public String getRfcContribuyente2() {
        return rfcContribuyente2;
    }

    public void setRfcContribuyente3(String rfcContribuyente3) {
        this.rfcContribuyente3 = rfcContribuyente3;
    }

    @XmlTransient
    public String getRfcContribuyente3() {
        return rfcContribuyente3;
    }

    public void setEjercicioFiscal(int ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    public int getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    public void setIdSector(String idSector) {
        this.idSector = idSector;
    }

    public String getIdSector() {
        return idSector;
    }

    public void setTipoDictamen(String tipoDictamen) {
        this.tipoDictamen = tipoDictamen;
    }

    public String getTipoDictamen() {
        return tipoDictamen;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        if(null != fechaRecepcion) {
            this.fechaRecepcion = (Date)fechaRecepcion.clone();
        } else {
            this.fechaRecepcion = null;
        }
    }

    public Date getFechaRecepcion() {
        if(null != fechaRecepcion) {
            return (Date)fechaRecepcion.clone();
        } else {
            return null;
        }
    }

    public void setObligado(String obligado) {
        this.obligado = obligado;
    }

    public String getObligado() {
        return obligado;
    }

    public void setNoCpr(String noCpr) {
        this.noCpr = noCpr;
    }

    public String getNoCpr() {
        return noCpr;
    }

    public void setRfcCpr(String rfcCpr) {
        this.rfcCpr = rfcCpr;
    }

    public String getRfcCpr() {
        return rfcCpr;
    }

    public void setNombreCpr(String nombreCpr) {
        this.nombreCpr = nombreCpr;
    }

    public String getNombreCpr() {
        return nombreCpr;
    }

    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("rfc_contribuyente:" + this.getRfcContribuyente() + ", ");
        sb.append("rfc_contribuyente2:" + this.getRfcContribuyente2() + ", ");
        sb.append("rfc_contribuyente3:" + this.getRfcContribuyente3() + ", ");
        sb.append("ejercicio_fiscal:" + this.getEjercicioFiscal() + ", ");
                
        return sb.toString();
    }

}
