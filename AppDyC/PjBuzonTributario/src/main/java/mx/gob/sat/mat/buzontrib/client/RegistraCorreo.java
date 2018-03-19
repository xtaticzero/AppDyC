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
 *         &lt;element name="idCorreo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RFC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaCorreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="celular" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parametros" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "idCorreo",
    "rfc",
    "cuentaCorreo",
    "celular",
    "parametros"
})
@XmlRootElement(name = "RegistraCorreo")
public class RegistraCorreo {

    private int idCorreo;
    @XmlElement(name = "RFC")
    private String rfc;
    private String cuentaCorreo;
    private String celular;
    private String parametros;

    /**
     * Obtiene el valor de la propiedad idCorreo.
     * 
     */
    public int getIdCorreo() {
        return idCorreo;
    }

    /**
     * Define el valor de la propiedad idCorreo.
     * 
     */
    public void setIdCorreo(int value) {
        this.idCorreo = value;
    }

    /**
     * Obtiene el valor de la propiedad rfc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRFC() {
        return rfc;
    }

    /**
     * Define el valor de la propiedad rfc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRFC(String value) {
        this.rfc = value;
    }

    /**
     * Obtiene el valor de la propiedad cuentaCorreo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaCorreo() {
        return cuentaCorreo;
    }

    /**
     * Define el valor de la propiedad cuentaCorreo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaCorreo(String value) {
        this.cuentaCorreo = value;
    }

    /**
     * Obtiene el valor de la propiedad celular.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Define el valor de la propiedad celular.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCelular(String value) {
        this.celular = value;
    }

    /**
     * Obtiene el valor de la propiedad parametros.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParametros() {
        return parametros;
    }

    /**
     * Define el valor de la propiedad parametros.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParametros(String value) {
        this.parametros = value;
    }

}
