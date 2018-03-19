//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.03.17 a las 09:43:24 PM CST 
//


package mx.gob.sat.mat.buzonnotif.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para MedioComunicacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="MedioComunicacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="medio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo_medio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="desc_medio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prioridad_correo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="amparado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MedioComunicacion", namespace = "http://www.sat.gob.mx/", propOrder = {
    "medio",
    "tipoMedio",
    "descMedio",
    "prioridadCorreo",
    "amparado"
})
public class MedioComunicacion {

    private String medio;
    @XmlElement(name = "tipo_medio")
    private int tipoMedio;
    @XmlElement(name = "desc_medio")
    private String descMedio;
    @XmlElement(name = "prioridad_correo")
    private int prioridadCorreo;
    private int amparado;

    /**
     * Obtiene el valor de la propiedad medio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedio() {
        return medio;
    }

    /**
     * Define el valor de la propiedad medio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedio(String value) {
        this.medio = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoMedio.
     * 
     */
    public int getTipoMedio() {
        return tipoMedio;
    }

    /**
     * Define el valor de la propiedad tipoMedio.
     * 
     */
    public void setTipoMedio(int value) {
        this.tipoMedio = value;
    }

    /**
     * Obtiene el valor de la propiedad descMedio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescMedio() {
        return descMedio;
    }

    /**
     * Define el valor de la propiedad descMedio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescMedio(String value) {
        this.descMedio = value;
    }

    /**
     * Obtiene el valor de la propiedad prioridadCorreo.
     * 
     */
    public int getPrioridadCorreo() {
        return prioridadCorreo;
    }

    /**
     * Define el valor de la propiedad prioridadCorreo.
     * 
     */
    public void setPrioridadCorreo(int value) {
        this.prioridadCorreo = value;
    }

    /**
     * Obtiene el valor de la propiedad amparado.
     * 
     */
    public int getAmparado() {
        return amparado;
    }

    /**
     * Define el valor de la propiedad amparado.
     * 
     */
    public void setAmparado(int value) {
        this.amparado = value;
    }

}
