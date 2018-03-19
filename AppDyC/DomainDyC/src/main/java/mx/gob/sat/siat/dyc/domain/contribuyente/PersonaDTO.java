/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.contribuyente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "rfc", "rfcVigente", "rfcSolicitado", "boId", "idPersona", "personaIdentificacion", "domicilio" })
@XmlRootElement(name = "PersonaDTO")
public class PersonaDTO extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:7642454794731118059")
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String rfc;

    @XmlElement
    private String rfcVigente;

    @XmlElement
    private String rfcSolicitado;

    @XmlElement
    private String boId;

    @XmlElement
    private String idPersona;

    @XmlElement(name = "PersonaIdentificacionDTO", type = PersonaIdentificacionDTO.class)
    private PersonaIdentificacionDTO personaIdentificacion;

    @XmlElement(name = "PersonaUbicacionDTO", type = PersonaUbicacionDTO.class)
    private PersonaUbicacionDTO domicilio;

    public PersonaDTO() {
        super();
    }
    /** Minimal Constructor*/
    public PersonaDTO(String rfc) {
        super();
        this.rfc = rfc;
    }


    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfcVigente(String rfcVigente) {
        this.rfcVigente = rfcVigente;
    }

    public String getRfcVigente() {
        return rfcVigente;
    }

    public void setRfcSolicitado(String rfcSolicitado) {
        this.rfcSolicitado = rfcSolicitado;
    }

    public String getRfcSolicitado() {
        return rfcSolicitado;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getBoId() {
        return boId;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setDomicilio(PersonaUbicacionDTO domicilio) {
        this.domicilio = domicilio;
    }

    public PersonaUbicacionDTO getDomicilio() {
        return domicilio;
    }

    public void setPersonaIdentificacion(PersonaIdentificacionDTO personaIdentificacion) {
        this.personaIdentificacion = personaIdentificacion;
    }

    public PersonaIdentificacionDTO getPersonaIdentificacion() {
        return personaIdentificacion;
    }

}
