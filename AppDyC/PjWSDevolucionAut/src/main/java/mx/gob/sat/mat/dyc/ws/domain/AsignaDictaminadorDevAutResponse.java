//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.19 at 04:36:50 PM CST 
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
 *         &lt;element name="NotificacionDevManual" type="{http://ws.dyc.mat.sat.gob.mx/}NotificacionDevManual"/>
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
    "notificacionDevManual"
})
@XmlRootElement(name = "asignaDictaminadorDevAutResponse")
public class AsignaDictaminadorDevAutResponse {

    @XmlElement(name = "NotificacionDevManual", required = true)
    private NotificacionDevManual notificacionDevManual;

    /**
     * Gets the value of the notificacionDevManual property.
     * 
     * @return
     *     possible object is
     *     {@link NotificacionDevManual }
     *     
     */
    public NotificacionDevManual getNotificacionDevManual() {
        return notificacionDevManual;
    }

    /**
     * Sets the value of the notificacionDevManual property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificacionDevManual }
     *     
     */
    public void setNotificacionDevManual(NotificacionDevManual value) {
        this.notificacionDevManual = value;
    }

}
