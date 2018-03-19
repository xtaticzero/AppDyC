//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.16 at 10:56:43 AM CST 
//


package mx.gob.sat.mat.rfcampl.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actividad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actividad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="c_Actividad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Actividad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Orden" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Porcentaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Alta_Act" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Baja_Act" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actividad", propOrder = {
    "cActividad",
    "dActividad",
    "orden",
    "porcentaje",
    "fAltaAct",
    "fBajaAct"
})
public class Actividad {

    @XmlElement(name = "c_Actividad", required = true)
    private String cActividad;
    @XmlElement(name = "d_Actividad", required = true)
    private String dActividad;
    @XmlElement(name = "Orden", required = true)
    private String orden;
    @XmlElement(name = "Porcentaje", required = true)
    private String porcentaje;
    @XmlElement(name = "f_Alta_Act", required = true)
    private String fAltaAct;
    @XmlElement(name = "f_Baja_Act", required = true)
    private String fBajaAct;

    /**
     * Gets the value of the cActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCActividad() {
        return cActividad;
    }

    /**
     * Sets the value of the cActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCActividad(String value) {
        this.cActividad = value;
    }

    /**
     * Gets the value of the dActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDActividad() {
        return dActividad;
    }

    /**
     * Sets the value of the dActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDActividad(String value) {
        this.dActividad = value;
    }

    /**
     * Gets the value of the orden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrden() {
        return orden;
    }

    /**
     * Sets the value of the orden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrden(String value) {
        this.orden = value;
    }

    /**
     * Gets the value of the porcentaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPorcentaje() {
        return porcentaje;
    }

    /**
     * Sets the value of the porcentaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPorcentaje(String value) {
        this.porcentaje = value;
    }

    /**
     * Gets the value of the fAltaAct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaAct() {
        return fAltaAct;
    }

    /**
     * Sets the value of the fAltaAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaAct(String value) {
        this.fAltaAct = value;
    }

    /**
     * Gets the value of the fBajaAct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBajaAct() {
        return fBajaAct;
    }

    /**
     * Sets the value of the fBajaAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBajaAct(String value) {
        this.fBajaAct = value;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "cActividad='" + cActividad + '\'' +
                ", dActividad='" + dActividad + '\'' +
                ", orden='" + orden + '\'' +
                ", porcentaje='" + porcentaje + '\'' +
                ", fAltaAct='" + fAltaAct + '\'' +
                ", fBajaAct='" + fBajaAct + '\'' +
                '}';
    }
}
