//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.19 at 04:37:55 PM CST 
//


package mx.gob.sat.mat.dyc.ws.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PreparacionPagoDevAut" type="{http://ws.dyc.mat.sat.gob.mx/}PreparacionPagoDevAut"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "preparacionPagoDevAut"
})
@XmlRootElement(name = "confirmarAutorizacionPago")
public class ConfirmarAutorizacionPago {

    @XmlElement(name = "PreparacionPagoDevAut", required = true)
    private PreparacionPagoDevAut preparacionPagoDevAut;

    /**
     * Gets the value of the preparacionPagoDevAut property.
     * 
     * @return
     *     possible object is
     *     {@link PreparacionPagoDevAut }
     *     
     */
    public PreparacionPagoDevAut getPreparacionPagoDevAut() {
        return preparacionPagoDevAut;
    }

    /**
     * Sets the value of the preparacionPagoDevAut property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreparacionPagoDevAut }
     *     
     */
    public void setPreparacionPagoDevAut(PreparacionPagoDevAut value) {
        this.preparacionPagoDevAut = value;
    }

}
