
package mx.gob.sat.siat.dyc.tesofe.service.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para retroalimentaPagoMATDyCIn complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="retroalimentaPagoMATDyCIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="folioMATDyC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estadoPago" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fechaPresentaArchivo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fechaPago" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numTransaccionTesofe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="descripcionMotivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retroalimentaPagoMATDyCIn", propOrder = {
    "folioMATDyC",
    "estadoPago",
    "fechaPresentaArchivo",
    "fechaPago",
    "numTransaccionTesofe",
    "motivo",
    "descripcionMotivo"
})
public class RetroalimentaPagoMATDyCIn {

    @XmlElement(required = true)
    private String folioMATDyC;
    private short estadoPago;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaPresentaArchivo;
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaPago;
    private String numTransaccionTesofe;
    private Short motivo;
    private String descripcionMotivo;

    /**
     * Obtiene el valor de la propiedad folioMATDyC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioMATDyC() {
        return folioMATDyC;
    }

    /**
     * Define el valor de la propiedad folioMATDyC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioMATDyC(String value) {
        this.folioMATDyC = value;
    }

    /**
     * Obtiene el valor de la propiedad estadoPago.
     * 
     */
    public short getEstadoPago() {
        return estadoPago;
    }

    /**
     * Define el valor de la propiedad estadoPago.
     * 
     */
    public void setEstadoPago(short value) {
        this.estadoPago = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaPresentaArchivo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPresentaArchivo() {
        return fechaPresentaArchivo;
    }

    /**
     * Define el valor de la propiedad fechaPresentaArchivo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPresentaArchivo(XMLGregorianCalendar value) {
        this.fechaPresentaArchivo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaPago.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPago() {
        return fechaPago;
    }

    /**
     * Define el valor de la propiedad fechaPago.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPago(XMLGregorianCalendar value) {
        this.fechaPago = value;
    }

    /**
     * Obtiene el valor de la propiedad numTransaccionTesofe.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTransaccionTesofe() {
        return numTransaccionTesofe;
    }

    /**
     * Define el valor de la propiedad numTransaccionTesofe.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTransaccionTesofe(String value) {
        this.numTransaccionTesofe = value;
    }

    /**
     * Obtiene el valor de la propiedad motivo.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getMotivo() {
        return motivo;
    }

    /**
     * Define el valor de la propiedad motivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setMotivo(Short value) {
        this.motivo = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionMotivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    /**
     * Define el valor de la propiedad descripcionMotivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionMotivo(String value) {
        this.descripcionMotivo = value;
    }

}
