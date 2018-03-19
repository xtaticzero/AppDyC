//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.19 at 04:36:50 PM CST 
//


package mx.gob.sat.mat.dyc.ws.domain;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for DevolucionManual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DevolucionManual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatosContribuyente" type="{http://ws.dyc.mat.sat.gob.mx/}DatosContribuyente"/>
 *         &lt;element name="DatosTramite" type="{http://ws.dyc.mat.sat.gob.mx/}DatosTramite"/>
 *         &lt;element name="DatosICEP" type="{http://ws.dyc.mat.sat.gob.mx/}DatosICEP"/>
 *         &lt;element name="DatosDeclaracion" type="{http://ws.dyc.mat.sat.gob.mx/}DatosDeclaracion"/>
 *         &lt;element name="InfoBanco" type="{http://ws.dyc.mat.sat.gob.mx/}InfoBanco"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DevolucionManual", propOrder = {
    "datosContribuyente",
    "datosTramite",
    "datosICEP",
    "datosDeclaracion",
    "infoBanco"
})
@XmlRootElement(name = "DevolucionManual")
public class DevolucionManual {

    @XmlElement(name = "DatosContribuyente", required = true)
    private DatosContribuyente datosContribuyente;
    @XmlElement(name = "DatosTramite", required = true)
    private DatosTramite datosTramite;
    @XmlElement(name = "DatosICEP", required = true)
    private DatosICEP datosICEP;
    @XmlElement(name = "DatosDeclaracion", required = true)
    private DatosDeclaracion datosDeclaracion;
    @XmlElement(name = "InfoBanco", required = true)
    private InfoBanco infoBanco;

    /**
     * Gets the value of the datosContribuyente property.
     * 
     * @return
     *     possible object is
     *     {@link DatosContribuyente }
     *     
     */
    public DatosContribuyente getDatosContribuyente() {
        return datosContribuyente;
    }

    /**
     * Sets the value of the datosContribuyente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosContribuyente }
     *     
     */
    public void setDatosContribuyente(DatosContribuyente value) {
        this.datosContribuyente = value;
    }

    /**
     * Gets the value of the datosTramite property.
     * 
     * @return
     *     possible object is
     *     {@link DatosTramite }
     *     
     */
    public DatosTramite getDatosTramite() {
        return datosTramite;
    }

    /**
     * Sets the value of the datosTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosTramite }
     *     
     */
    public void setDatosTramite(DatosTramite value) {
        this.datosTramite = value;
    }

    /**
     * Gets the value of the datosICEP property.
     * 
     * @return
     *     possible object is
     *     {@link DatosICEP }
     *     
     */
    public DatosICEP getDatosICEP() {
        return datosICEP;
    }

    /**
     * Sets the value of the datosICEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosICEP }
     *     
     */
    public void setDatosICEP(DatosICEP value) {
        this.datosICEP = value;
    }

    /**
     * Gets the value of the datosDeclaracion property.
     * 
     * @return
     *     possible object is
     *     {@link DatosDeclaracion }
     *     
     */
    public DatosDeclaracion getDatosDeclaracion() {
        return datosDeclaracion;
    }

    /**
     * Sets the value of the datosDeclaracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosDeclaracion }
     *     
     */
    public void setDatosDeclaracion(DatosDeclaracion value) {
        this.datosDeclaracion = value;
    }

    /**
     * Gets the value of the infoBanco property.
     * 
     * @return
     *     possible object is
     *     {@link InfoBanco }
     *     
     */
    public InfoBanco getInfoBanco() {
        return infoBanco;
    }

    /**
     * Sets the value of the infoBanco property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoBanco }
     *     
     */
    public void setInfoBanco(InfoBanco value) {
        this.infoBanco = value;
    }

    @Override
    public String toString() {
        return "DevolucionManual{"
                + datosContribuyente
                + datosTramite
                + datosICEP
                + datosDeclaracion
                + infoBanco +
                '}';
    }
}
