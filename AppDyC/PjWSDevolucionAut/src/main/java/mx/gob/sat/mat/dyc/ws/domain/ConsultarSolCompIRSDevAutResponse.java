//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.19 at 04:38:32 PM CST 
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
 *         &lt;element name="NotificacionConsulSolIRS" type="{http://ws.dyc.mat.sat.gob.mx/}NotificacionConsulSolIRS"/>
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
    "notificacionConsulSolIRS"
})
@XmlRootElement(name = "consultarSolCompIRSDevAutResponse")
public class ConsultarSolCompIRSDevAutResponse {

    @XmlElement(name = "NotificacionConsulSolIRS", required = true)
    private NotificacionConsulSolIRS notificacionConsulSolIRS;

    /**
     * Gets the value of the notificacionConsulSolIRS property.
     * 
     * @return
     *     possible object is
     *     {@link NotificacionConsulSolIRS }
     *     
     */
    public NotificacionConsulSolIRS getNotificacionConsulSolIRS() {
        return notificacionConsulSolIRS;
    }

    /**
     * Sets the value of the notificacionConsulSolIRS property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificacionConsulSolIRS }
     *     
     */
    public void setNotificacionConsulSolIRS(NotificacionConsulSolIRS value) {
        this.notificacionConsulSolIRS = value;
    }

}
