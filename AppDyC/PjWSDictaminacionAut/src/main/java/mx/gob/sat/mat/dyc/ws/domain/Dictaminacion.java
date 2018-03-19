//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.21 at 01:45:57 PM CDT 
//
package mx.gob.sat.mat.dyc.ws.domain;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumControl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NumLote" type="{http://www.w3.org/2001/XMLSchema}integer" nillable = "true"/>
 *         &lt;element name="RFC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Impuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Concepto" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NumOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ImporteSaldoF" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="FechaProcModelo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Modelo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MarcResultado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MotRiesgo" type="{http://www.w3.org/2001/XMLSchema}integer" nillable = "true"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dictaminacion", propOrder = {
    "numControl",
    "numLote",
    "rfc",
    "impuesto",
    "concepto",
    "numOperacion",
    "importeSaldoF",
    "fechaProcModelo",
    "modelo",
    "marcResultado",
    "motRiesgo"
})
@XmlRootElement(name = "Dictaminacion")
public class Dictaminacion {

    @XmlElement(name = "NumControl", required = true)
    private String numControl;
    @XmlElement(name = "NumLote", required = true, nillable = true)
    private Integer numLote;
    @XmlElement(name = "RFC", required = true)
    private String rfc;
    @XmlElement(name = "Impuesto", required = true)
    private String impuesto;
    @XmlElement(name = "Concepto", required = true)
    private Integer concepto;
    @XmlElement(name = "NumOperacion", required = true)
    private String numOperacion;
    @XmlElement(name = "ImporteSaldoF", required = true)
    private BigDecimal importeSaldoF;
    @XmlElement(name = "FechaProcModelo", required = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar fechaProcModelo;
    @XmlElement(name = "Modelo", required = true)
    private String modelo;
    @XmlElement(name = "MarcResultado", required = true)
    private String marcResultado;
    @XmlElement(name = "MotRiesgo", required = true, nillable = true)
    private Integer motRiesgo;

    /**
     * @return the numControl
     */
    public String getNumControl() {
        return numControl;
    }

    /**
     * @param numControl the numControl to set
     */
    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    /**
     * @return the numLote
     */
    public Integer getNumLote() {
        return numLote;
    }

    /**
     * @param numLote the numLote to set
     */
    public void setNumLote(Integer numLote) {
        this.numLote = numLote;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the impuesto
     */
    public String getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    /**
     * @return the concepto
     */
    public Integer getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the numOperacion
     */
    public String getNumOperacion() {
        return numOperacion;
    }

    /**
     * @param numOperacion the numOperacion to set
     */
    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    /**
     * @return the importeSaldoF
     */
    public BigDecimal getImporteSaldoF() {
        return importeSaldoF;
    }

    /**
     * @param importeSaldoF the importeSaldoF to set
     */
    public void setImporteSaldoF(BigDecimal importeSaldoF) {
        this.importeSaldoF = importeSaldoF;
    }

    /**
     * @return the fechaProcModelo
     */
    public XMLGregorianCalendar getFechaProcModelo() {
        return fechaProcModelo;
    }

    /**
     * @param fechaProcModelo the fechaProcModelo to set
     */
    public void setFechaProcModelo(XMLGregorianCalendar fechaProcModelo) {
        this.fechaProcModelo = fechaProcModelo;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the marcResultado
     */
    public String getMarcResultado() {
        return marcResultado;
    }

    /**
     * @param marcResultado the marcResultado to set
     */
    public void setMarcResultado(String marcResultado) {
        this.marcResultado = marcResultado;
    }

    /**
     * @return the motRiesgo
     */
    public Integer getMotRiesgo() {
        return motRiesgo;
    }

    /**
     * @param motRiesgo the motRiesgo to set
     */
    public void setMotRiesgo(Integer motRiesgo) {
        this.motRiesgo = motRiesgo;
    }

}
