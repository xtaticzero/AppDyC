package mx.gob.sat.siat.dyc.domain.contribuyente;

import java.io.Serializable;

import java.util.List;


public class RolesContribuyenteDTO implements Serializable {

    @SuppressWarnings("compatibility:-745349993357192702")
    private static final long serialVersionUID = 1L;

    private transient List<PersonaRolDTO> roles;
    private String esContribuyente;
    private String claveLocalidad;
    private Integer claveAdmon;
    private boolean granContribuyente;
    private boolean personaFisica;
    private boolean personaMoral;
    private boolean sociedadControladora;
    private boolean dictaminado;
    private boolean estadoExtranjero;
    private boolean amparado;
    private boolean integradoraPM;

    public void setRoles(List<PersonaRolDTO> roles) {
        this.roles = roles;
    }

    public List<PersonaRolDTO> getRoles() {
        return roles;
    }

    public void setEsContribuyente(String esContribuyente) {
        this.esContribuyente = esContribuyente;
    }

    public String getEsContribuyente() {
        return esContribuyente;
    }

    public void setClaveLocalidad(String claveLocalidad) {
        this.claveLocalidad = claveLocalidad;
    }

    public String getClaveLocalidad() {
        return claveLocalidad;
    }

    public void setGranContribuyente(boolean granContribuyente) {
        this.granContribuyente = granContribuyente;
    }

    public boolean isGranContribuyente() {
        return granContribuyente;
    }

    public void setPersonaFisica(boolean peronaFisica) {
        this.personaFisica = peronaFisica;
    }

    public boolean isPersonaFisica() {
        return personaFisica;
    }

    public void setPersonaMoral(boolean personaMoral) {
        this.personaMoral = personaMoral;
    }

    public boolean isPersonaMoral() {
        return personaMoral;
    }

    public void setSociedadControladora(boolean sociedadControladora) {
        this.sociedadControladora = sociedadControladora;
    }

    public boolean isSociedadControladora() {
        return sociedadControladora;
    }

    public void setDictaminado(boolean dictaminado) {
        this.dictaminado = dictaminado;
    }

    public boolean isDictaminado() {
        return dictaminado;
    }

    public void setEstadoExtranjero(boolean estadoExtranjero) {
        this.estadoExtranjero = estadoExtranjero;
    }

    public boolean isEstadoExtranjero() {
        return estadoExtranjero;
    }

    public void setAmparado(boolean amparado) {
        this.amparado = amparado;
    }

    public boolean isAmparado() {
        return amparado;
    }

    public void setIntegradoraPM(boolean integradoraPM) {
        this.integradoraPM = integradoraPM;
    }

    public boolean isIntegradoraPM() {
        return integradoraPM;
    }

    public void setClaveAdmon(Integer claveAdmon) {
        this.claveAdmon = claveAdmon;
    }

    public Integer getClaveAdmon() {
        return claveAdmon;
    }
}
