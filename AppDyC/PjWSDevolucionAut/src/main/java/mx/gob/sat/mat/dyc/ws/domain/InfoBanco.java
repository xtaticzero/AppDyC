//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.19 at 04:38:18 PM CST 
//


package mx.gob.sat.mat.dyc.ws.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoBanco complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoBanco">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cuentaCLABE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoBanco", propOrder = {
    "cuentaCLABE"
})
@XmlRootElement(name = "InfoBanco")
public class InfoBanco {

    @XmlElement(required = true)
    private String cuentaCLABE;

    /**
     * Gets the value of the cuentaCLABE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaCLABE() {
        return cuentaCLABE;
    }

    /**
     * Sets the value of the cuentaCLABE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaCLABE(String value) {
        this.cuentaCLABE = value;
    }

    @Override
    public String toString() {
        return "InfoBanco{" +
                "cuentaCLABE='" + cuentaCLABE + '\'' +
                '}';
    }
}