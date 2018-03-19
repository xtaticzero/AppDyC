//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.24 a las 09:25:44 AM CST 
//


package mx.gob.sat.mat.buzontrib.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DatosReimpresion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DatosReimpresion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaHora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCelular" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cadenaOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosReimpresion", propOrder = {
    "fechaHora",
    "email",
    "numeroCelular",
    "cadenaOriginal",
    "firma"
})
public class DatosReimpresion {

    private String fechaHora;
    private String email;
    private String numeroCelular;
    private String cadenaOriginal;
    private String firma;

    /**
     * Obtiene el valor de la propiedad fechaHora.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaHora() {
        return fechaHora;
    }

    /**
     * Define el valor de la propiedad fechaHora.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaHora(String value) {
        this.fechaHora = value;
    }

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroCelular.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCelular() {
        return numeroCelular;
    }

    /**
     * Define el valor de la propiedad numeroCelular.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCelular(String value) {
        this.numeroCelular = value;
    }

    /**
     * Obtiene el valor de la propiedad cadenaOriginal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * Define el valor de la propiedad cadenaOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadenaOriginal(String value) {
        this.cadenaOriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad firma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirma() {
        return firma;
    }

    /**
     * Define el valor de la propiedad firma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirma(String value) {
        this.firma = value;
    }

}
