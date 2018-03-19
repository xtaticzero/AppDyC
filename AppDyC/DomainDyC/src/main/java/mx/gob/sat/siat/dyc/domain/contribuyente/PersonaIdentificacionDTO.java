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
 * Clase que sirve para obtener los datos de identificacion de una persona moral o fisica
 * @author Paola Rivera
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "tipoPersona", "curp", "claveSegmento", "descripcionSegmento", "nit", "nombre", "apPaterno", "apMaterno",
           "nombreComercial", "fNacimiento", "razonSocial", "tipoSociedad", "fConstitucion", "nacionalidad",
           "fInicioOperacion", "claveSitContribuyente", "claveDetSitCont", "descSitContribuyente",
           "fechaSitContribuyente", "claveSitDomicilio", "descSitDomicilio", "claveSitContDom", "descSitContDom",
           "paisOrigen", "dfTipo", "dfFolio", "dfFInicio", "dfFFin", "email", "idPersona", "denominacion" })
@XmlRootElement(name = "Identificacion")
public class PersonaIdentificacionDTO implements Serializable {

    @SuppressWarnings("compatibility:-2764394077125184744")
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String tipoPersona;
    @XmlElement
    private String curp;
    @XmlElement
    private String claveSegmento;
    @XmlElement
    private String descripcionSegmento;
    @XmlElement
    private String nit;
    @XmlElement
    private String nombre;
    @XmlElement
    private String apPaterno;
    @XmlElement
    private String apMaterno;
    @XmlElement
    private String nombreComercial;
    @XmlElement
    private Date fNacimiento;
    @XmlElement
    private String razonSocial;
    @XmlElement
    private String tipoSociedad;
    @XmlElement
    private Date fConstitucion;
    @XmlElement
    private String nacionalidad;
    @XmlElement
    private Date fInicioOperacion;
    @XmlElement
    private String claveSitContribuyente;
    @XmlElement
    private String claveDetSitCont;
    @XmlElement
    private String descSitContribuyente;
    @XmlElement
    private Date fechaSitContribuyente;
    @XmlElement
    private String claveSitDomicilio;
    @XmlElement
    private String descSitDomicilio;
    @XmlElement
    private String claveSitContDom;
    @XmlElement
    private String descSitContDom;
    @XmlElement
    private String paisOrigen;
    @XmlElement
    private String dfTipo;
    @XmlElement
    private String dfFolio;
    @XmlElement
    private Date dfFInicio;
    @XmlElement
    private Date dfFFin;
    @XmlElement
    private String email;
    @XmlElement
    private String idPersona;
    @XmlElement
    private String denominacion;

    public PersonaIdentificacionDTO() {
        super();
    }


    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCurp() {
        return curp;
    }

    public void setClaveSegmento(String claveSegmento) {
        this.claveSegmento = claveSegmento;
    }

    public String getClaveSegmento() {
        return claveSegmento;
    }

    public void setDescripcionSegmento(String descripcionSegmento) {
        this.descripcionSegmento = descripcionSegmento;
    }

    public String getDescripcionSegmento() {
        return descripcionSegmento;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setFNacimiento(Date fNacimiento) {
        if(null != fNacimiento) {
            this.fNacimiento = (Date)fNacimiento.clone();
        } else {
            this.fNacimiento = null;
        }
    }

    public Date getFNacimiento() {
        if(null != fNacimiento) {
            return (Date)fNacimiento.clone();
        } else {
            return null;
        }
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }

    public String getTipoSociedad() {
        return tipoSociedad;
    }

    public void setFConstitucion(Date fConstitucion) {
        if(null != fConstitucion) {
            this.fConstitucion = (Date)fConstitucion.clone();
        } else {
            this.fConstitucion = null;
        }
    }

    public Date getFConstitucion() {
        if(null != fConstitucion) {
            return (Date)fConstitucion.clone();
        } else {
            return null;
        }
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setFInicioOperacion(Date fInicioOperacion) {
        if(null != fInicioOperacion) {
            this.fInicioOperacion = (Date)fInicioOperacion.clone();
        } else {
            this.fInicioOperacion = null;
        }
    }

    public Date getFInicioOperacion() {
        if(null != fInicioOperacion) {
            return (Date)fInicioOperacion.clone();
        } else {
            return null;
        }
    }

    public void setClaveSitContribuyente(String claveSitContribuyente) {
        this.claveSitContribuyente = claveSitContribuyente;
    }

    public String getClaveSitContribuyente() {
        return claveSitContribuyente;
    }

    public void setClaveDetSitCont(String claveDetSitCont) {
        this.claveDetSitCont = claveDetSitCont;
    }

    public String getClaveDetSitCont() {
        return claveDetSitCont;
    }

    public void setDescSitContribuyente(String descSitContribuyente) {
        this.descSitContribuyente = descSitContribuyente;
    }

    public String getDescSitContribuyente() {
        return descSitContribuyente;
    }

    public void setFechaSitContribuyente(Date fechaSitContribuyente) {
        if(null != fechaSitContribuyente) {
            this.fechaSitContribuyente = (Date)fechaSitContribuyente.clone();
        } else {
            this.fechaSitContribuyente = null;
        }
    }

    public Date getFechaSitContribuyente() {
        if(null != fechaSitContribuyente) {
            return (Date)fechaSitContribuyente.clone();
        } else {
            return null;
        }
    }

    public void setClaveSitDomicilio(String claveSitDomicilio) {
        this.claveSitDomicilio = claveSitDomicilio;
    }

    public String getClaveSitDomicilio() {
        return claveSitDomicilio;
    }

    public void setDescSitDomicilio(String descSitDomicilio) {
        this.descSitDomicilio = descSitDomicilio;
    }

    public String getDescSitDomicilio() {
        return descSitDomicilio;
    }

    public void setClaveSitContDom(String claveSitContDom) {
        this.claveSitContDom = claveSitContDom;
    }

    public String getClaveSitContDom() {
        return claveSitContDom;
    }

    public void setDescSitContDom(String descSitContDom) {
        this.descSitContDom = descSitContDom;
    }

    public String getDescSitContDom() {
        return descSitContDom;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setDfTipo(String dfTipo) {
        this.dfTipo = dfTipo;
    }

    public String getDfTipo() {
        return dfTipo;
    }

    public void setDfFolio(String dfFolio) {
        this.dfFolio = dfFolio;
    }

    public String getDfFolio() {
        return dfFolio;
    }

    public void setDfFInicio(Date dfFInicio) {
        if(null != dfFInicio) {
            this.dfFInicio = (Date)dfFInicio.clone();
        } else {
            this.dfFInicio = null;
        }
    }

    public Date getDfFInicio() {
        if(null != dfFInicio) {
            return (Date)dfFInicio.clone();
        } else {
            return null;
        }
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNit() {
        return nit;
    }

    public void setDfFFin(Date dfFFin) {
        if(null != dfFFin) {
            this.dfFFin = (Date)dfFFin.clone();
        } else {
            this.dfFFin = null;
        } 
    }

    public Date getDfFFin() {
        if(null != dfFFin) {
            return (Date)dfFFin.clone();
        } else {
            return null;
        }
    }
}
