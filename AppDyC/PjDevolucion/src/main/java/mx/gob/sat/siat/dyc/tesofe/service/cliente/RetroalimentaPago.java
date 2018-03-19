
package mx.gob.sat.siat.dyc.tesofe.service.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para retroalimentaPago complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="retroalimentaPago">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retroalimentaPagoIn" type="{http://impl.services.matdyc.retropago.pf.isr.sad.dyc.sat.gob.mx/}retroalimentaPagoMATDyCIn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retroalimentaPago", propOrder = {
    "retroalimentaPagoIn"
})
public class RetroalimentaPago {

    private RetroalimentaPagoMATDyCIn retroalimentaPagoIn;

    /**
     * Obtiene el valor de la propiedad retroalimentaPagoIn.
     * 
     * @return
     *     possible object is
     *     {@link RetroalimentaPagoMATDyCIn }
     *     
     */
    public RetroalimentaPagoMATDyCIn getRetroalimentaPagoIn() {
        return retroalimentaPagoIn;
    }

    /**
     * Define el valor de la propiedad retroalimentaPagoIn.
     * 
     * @param value
     *     allowed object is
     *     {@link RetroalimentaPagoMATDyCIn }
     *     
     */
    public void setRetroalimentaPagoIn(RetroalimentaPagoMATDyCIn value) {
        this.retroalimentaPagoIn = value;
    }

}
