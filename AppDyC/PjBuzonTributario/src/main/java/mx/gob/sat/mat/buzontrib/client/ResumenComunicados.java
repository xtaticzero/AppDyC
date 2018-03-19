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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ResumenComunicados complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ResumenComunicados">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Encabezado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Detalle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f_VigenciaIni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f_VigenciaFin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_Comunicado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="c_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f_acceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResumenComunicados", namespace = "http://tempuri.org/", propOrder = {
    "encabezado",
    "detalle",
    "fVigenciaIni",
    "fVigenciaFin",
    "idComunicado",
    "cId",
    "fAcceso"
})
public class ResumenComunicados {

    @XmlElement(name = "Encabezado")
    private String encabezado;
    @XmlElement(name = "Detalle")
    private String detalle;
    @XmlElement(name = "f_VigenciaIni")
    private String fVigenciaIni;
    @XmlElement(name = "f_VigenciaFin")
    private String fVigenciaFin;
    @XmlElement(name = "id_Comunicado")
    private String idComunicado;
    @XmlElement(name = "c_id")
    private String cId;
    @XmlElement(name = "f_acceso")
    private String fAcceso;

    /**
     * Obtiene el valor de la propiedad encabezado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncabezado() {
        return encabezado;
    }

    /**
     * Define el valor de la propiedad encabezado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncabezado(String value) {
        this.encabezado = value;
    }

    /**
     * Obtiene el valor de la propiedad detalle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Define el valor de la propiedad detalle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalle(String value) {
        this.detalle = value;
    }

    /**
     * Obtiene el valor de la propiedad fVigenciaIni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFVigenciaIni() {
        return fVigenciaIni;
    }

    /**
     * Define el valor de la propiedad fVigenciaIni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFVigenciaIni(String value) {
        this.fVigenciaIni = value;
    }

    /**
     * Obtiene el valor de la propiedad fVigenciaFin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFVigenciaFin() {
        return fVigenciaFin;
    }

    /**
     * Define el valor de la propiedad fVigenciaFin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFVigenciaFin(String value) {
        this.fVigenciaFin = value;
    }

    /**
     * Obtiene el valor de la propiedad idComunicado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComunicado() {
        return idComunicado;
    }

    /**
     * Define el valor de la propiedad idComunicado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComunicado(String value) {
        this.idComunicado = value;
    }

    /**
     * Obtiene el valor de la propiedad cId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCId() {
        return cId;
    }

    /**
     * Define el valor de la propiedad cId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCId(String value) {
        this.cId = value;
    }

    /**
     * Obtiene el valor de la propiedad fAcceso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAcceso() {
        return fAcceso;
    }

    /**
     * Define el valor de la propiedad fAcceso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAcceso(String value) {
        this.fAcceso = value;
    }

}
