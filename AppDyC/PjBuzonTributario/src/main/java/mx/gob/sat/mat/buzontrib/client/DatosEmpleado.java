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
 * <p>Clase Java para DatosEmpleado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DatosEmpleado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExtensionData" type="{http://www.sat.gob.mx/}ExtensionDataObject" minOccurs="0"/>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indicadorDepartamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://www.sat.gob.mx/}ArrayOfRoles" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosEmpleado", propOrder = {
    "extensionData",
    "error",
    "indicadorDepartamento",
    "roles"
})
public class DatosEmpleado {

    @XmlElement(name = "ExtensionData")
    private ExtensionDataObject extensionData;
    private String error;
    private String indicadorDepartamento;
    private ArrayOfRoles roles;

    /**
     * Obtiene el valor de la propiedad extensionData.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionDataObject }
     *     
     */
    public ExtensionDataObject getExtensionData() {
        return extensionData;
    }

    /**
     * Define el valor de la propiedad extensionData.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionDataObject }
     *     
     */
    public void setExtensionData(ExtensionDataObject value) {
        this.extensionData = value;
    }

    /**
     * Obtiene el valor de la propiedad error.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Define el valor de la propiedad error.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

    /**
     * Obtiene el valor de la propiedad indicadorDepartamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicadorDepartamento() {
        return indicadorDepartamento;
    }

    /**
     * Define el valor de la propiedad indicadorDepartamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorDepartamento(String value) {
        this.indicadorDepartamento = value;
    }

    /**
     * Obtiene el valor de la propiedad roles.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRoles }
     *     
     */
    public ArrayOfRoles getRoles() {
        return roles;
    }

    /**
     * Define el valor de la propiedad roles.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRoles }
     *     
     */
    public void setRoles(ArrayOfRoles value) {
        this.roles = value;
    }

}
