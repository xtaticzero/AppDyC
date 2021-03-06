//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.16 at 10:56:43 AM CST 
//


package mx.gob.sat.mat.rfcampl.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rfcHistorico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rfcHistorico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rfc_anterior" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nombre_anterior" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rfc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="seccion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rfcHistorico", propOrder = {
    "rfcAnterior",
    "nombreAnterior"
})
public class RfcHistorico {

    @XmlElement(name = "rfc_anterior")
    private List<String> rfcAnterior;
    @XmlElement(name = "nombre_anterior")
    private List<String> nombreAnterior;
    @XmlAttribute(name = "rfc")
    private String rfc;
    @XmlAttribute(name = "seccion")
    private String seccion;

    /**
     * Gets the value of the rfcAnterior property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rfcAnterior property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRfcAnterior().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRfcAnterior() {
        if (rfcAnterior == null) {
            rfcAnterior = new ArrayList<String>();
        }
        return this.rfcAnterior;
    }

    /**
     * Gets the value of the nombreAnterior property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nombreAnterior property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNombreAnterior().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNombreAnterior() {
        if (nombreAnterior == null) {
            nombreAnterior = new ArrayList<String>();
        }
        return this.nombreAnterior;
    }

    /**
     * Gets the value of the rfc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Sets the value of the rfc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfc(String value) {
        this.rfc = value;
    }

    /**
     * Gets the value of the seccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * Sets the value of the seccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeccion(String value) {
        this.seccion = value;
    }

    @Override
    public String toString() {
        return "RfcHistorico{" +
                "rfcAnterior=" + rfcAnterior +
                ", nombreAnterior=" + nombreAnterior +
                ", rfc='" + rfc + '\'' +
                ", seccion='" + seccion + '\'' +
                '}';
    }
}
