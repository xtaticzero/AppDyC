/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.contribuyente;

import java.io.Serializable;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Clase que sirve para obtener la direccion del contribuyente
 * @author Paola Rivera
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "claveAdmonLocal", "admonLocal", "calle", "numeroExt", "numeroInt", "claveColonia", "colonia", "claveLocalidad",
           "localidad", "entreCalle1", "entreCalle2", "refAdicionales", "tipoInmueble", "descInmueble", "tipoVialidad",
           "descVialidad", "codigoPostal", "claveMunicipio", "municipio", "claveEntFed", "entFed", "telefono1",
           "tipoTelefono1", "telefono2", "tipoTelefono2", "email", "fechaAltaDomicilio", "pais", "caracDomicilio",
           "centroRegional", "descCentroRegional" })
@XmlRootElement(name = "Ubicacion")
public class PersonaUbicacionDTO implements Serializable {

    @SuppressWarnings("compatibility:5281747807113315156")
    private static final long serialVersionUID = 1L;

    @XmlElement
    private int claveAdmonLocal;
    @XmlElement
    private String admonLocal;
    @XmlElement
    private String calle;
    @XmlElement
    private String numeroExt;
    @XmlElement
    private String numeroInt;
    @XmlElement
    private String claveColonia;
    @XmlElement
    private String colonia;
    @XmlElement
    private String claveLocalidad;
    @XmlElement
    private String localidad;
    @XmlElement
    private String entreCalle1;
    @XmlElement
    private String entreCalle2;
    @XmlElement
    private String refAdicionales;
    @XmlElement
    private String tipoInmueble;
    @XmlElement
    private String descInmueble;
    @XmlElement
    private String tipoVialidad;
    @XmlElement
    private String descVialidad;
    @XmlElement
    private String codigoPostal;
    @XmlElement
    private String claveMunicipio;
    @XmlElement
    private String municipio;
    @XmlElement
    private String claveEntFed;
    @XmlElement
    private String entFed;
    @XmlElement
    private String telefono1;
    @XmlElement
    private String tipoTelefono1;
    @XmlElement
    private String telefono2;
    @XmlElement
    private String tipoTelefono2;
    @XmlElement
    private String email;
    @XmlElement
    private Date fechaAltaDomicilio;
    @XmlElement
    private String pais;
    @XmlElement
    private String caracDomicilio;
    @XmlElement
    private String centroRegional;
    @XmlElement
    private String descCentroRegional;


    public PersonaUbicacionDTO() {
        super();
    }


    public void setAdmonLocal(String admonLocal) {
        this.admonLocal = admonLocal;
    }

    public String getAdmonLocal() {
        return admonLocal;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCalle() {
        return calle;
    }

    public void setNumeroExt(String numeroExt) {
        this.numeroExt = numeroExt;
    }

    public String getNumeroExt() {
        return numeroExt;
    }

    public void setNumeroInt(String numeroInt) {
        this.numeroInt = numeroInt;
    }

    public String getNumeroInt() {
        return numeroInt;
    }


    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getColonia() {
        return colonia;
    }

    public void setClaveLocalidad(String claveLocalidad) {
        this.claveLocalidad = claveLocalidad;
    }

    public String getClaveLocalidad() {
        return claveLocalidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setEntreCalle1(String entreCalle1) {
        this.entreCalle1 = entreCalle1;
    }

    public String getEntreCalle1() {
        return entreCalle1;
    }

    public void setEntreCalle2(String entreCalle2) {
        this.entreCalle2 = entreCalle2;
    }

    public String getEntreCalle2() {
        return entreCalle2;
    }

    public void setRefAdicionales(String refAdicionales) {
        this.refAdicionales = refAdicionales;
    }

    public String getRefAdicionales() {
        return refAdicionales;
    }

    public void setTipoInmueble(String tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getTipoInmueble() {
        return tipoInmueble;
    }

    public void setDescInmueble(String descInmueble) {
        this.descInmueble = descInmueble;
    }

    public String getDescInmueble() {
        return descInmueble;
    }

    public void setTipoVialidad(String tipoVialidad) {
        this.tipoVialidad = tipoVialidad;
    }

    public String getTipoVialidad() {
        return tipoVialidad;
    }

    public void setDescVialidad(String descVialidad) {
        this.descVialidad = descVialidad;
    }

    public String getDescVialidad() {
        return descVialidad;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setClaveMunicipio(String claveMunicipio) {
        this.claveMunicipio = claveMunicipio;
    }

    public String getClaveMunicipio() {
        return claveMunicipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setClaveEntFed(String claveEntFed) {
        this.claveEntFed = claveEntFed;
    }

    public String getClaveEntFed() {
        return claveEntFed;
    }

    public void setEntFed(String entFed) {
        this.entFed = entFed;
    }

    public String getEntFed() {
        return entFed;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTipoTelefono1(String tipoTelefono1) {
        this.tipoTelefono1 = tipoTelefono1;
    }

    public String getTipoTelefono1() {
        return tipoTelefono1;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTipoTelefono2(String tipoTelefono2) {
        this.tipoTelefono2 = tipoTelefono2;
    }

    public String getTipoTelefono2() {
        return tipoTelefono2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFechaAltaDomicilio(Date fechaAltaDomicilio) {
        if(null != fechaAltaDomicilio) {
            this.fechaAltaDomicilio = (Date)fechaAltaDomicilio.clone();
        } else {
            this.fechaAltaDomicilio = null;
        }
    }

    public Date getFechaAltaDomicilio() {
        if(null != fechaAltaDomicilio) {
            return (Date)fechaAltaDomicilio.clone();
        } else {
            return null;
        }
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPais() {
        return pais;
    }

    public void setCaracDomicilio(String caracDomicilio) {
        this.caracDomicilio = caracDomicilio;
    }

    public String getCaracDomicilio() {
        return caracDomicilio;
    }

    public void setCentroRegional(String centroRegional) {
        this.centroRegional = centroRegional;
    }

    public String getCentroRegional() {
        return centroRegional;
    }

    public void setDescCentroRegional(String descCentroRegional) {
        this.descCentroRegional = descCentroRegional;
    }

    public String getDescCentroRegional() {
        return descCentroRegional;
    }

    public void setClaveColonia(String claveColonia) {
        this.claveColonia = claveColonia;
    }

    public String getClaveColonia() {
        return claveColonia;
    }

    public void setClaveAdmonLocal(int claveAdmonLocal) {
        this.claveAdmonLocal = claveAdmonLocal;
    }

    public int getClaveAdmonLocal() {
        return claveAdmonLocal;
    }
}
