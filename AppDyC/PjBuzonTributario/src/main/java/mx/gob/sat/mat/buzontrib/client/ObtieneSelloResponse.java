//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.24 a las 09:25:44 AM CST 
//


package mx.gob.sat.mat.buzontrib.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObtieneSelloResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "obtieneSelloResult"
})
@XmlRootElement(name = "ObtieneSelloResponse")
public class ObtieneSelloResponse {

    @XmlElement(name = "ObtieneSelloResult")
    private String obtieneSelloResult;

    /**
     * Obtiene el valor de la propiedad obtieneSelloResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObtieneSelloResult() {
        return obtieneSelloResult;
    }

    /**
     * Define el valor de la propiedad obtieneSelloResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObtieneSelloResult(String value) {
        this.obtieneSelloResult = value;
    }

}
