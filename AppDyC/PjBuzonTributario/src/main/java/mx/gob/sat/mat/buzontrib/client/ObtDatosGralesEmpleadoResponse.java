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
 *         &lt;element name="ObtDatosGralesEmpleadoResult" type="{http://www.sat.gob.mx/}DatosEmpleado" minOccurs="0"/>
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
    "obtDatosGralesEmpleadoResult"
})
@XmlRootElement(name = "ObtDatosGralesEmpleadoResponse")
public class ObtDatosGralesEmpleadoResponse {

    @XmlElement(name = "ObtDatosGralesEmpleadoResult")
    private DatosEmpleado obtDatosGralesEmpleadoResult;

    /**
     * Obtiene el valor de la propiedad obtDatosGralesEmpleadoResult.
     * 
     * @return
     *     possible object is
     *     {@link DatosEmpleado }
     *     
     */
    public DatosEmpleado getObtDatosGralesEmpleadoResult() {
        return obtDatosGralesEmpleadoResult;
    }

    /**
     * Define el valor de la propiedad obtDatosGralesEmpleadoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosEmpleado }
     *     
     */
    public void setObtDatosGralesEmpleadoResult(DatosEmpleado value) {
        this.obtDatosGralesEmpleadoResult = value;
    }

}
