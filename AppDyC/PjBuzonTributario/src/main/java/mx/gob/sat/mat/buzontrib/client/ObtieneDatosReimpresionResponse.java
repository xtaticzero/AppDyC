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
 *         &lt;element name="ObtieneDatosReimpresionResult" type="{http://www.sat.gob.mx/}DatosReimpresion" minOccurs="0"/>
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
    "obtieneDatosReimpresionResult"
})
@XmlRootElement(name = "ObtieneDatosReimpresionResponse")
public class ObtieneDatosReimpresionResponse {

    @XmlElement(name = "ObtieneDatosReimpresionResult")
    private DatosReimpresion obtieneDatosReimpresionResult;

    /**
     * Obtiene el valor de la propiedad obtieneDatosReimpresionResult.
     * 
     * @return
     *     possible object is
     *     {@link DatosReimpresion }
     *     
     */
    public DatosReimpresion getObtieneDatosReimpresionResult() {
        return obtieneDatosReimpresionResult;
    }

    /**
     * Define el valor de la propiedad obtieneDatosReimpresionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosReimpresion }
     *     
     */
    public void setObtieneDatosReimpresionResult(DatosReimpresion value) {
        this.obtieneDatosReimpresionResult = value;
    }

}
