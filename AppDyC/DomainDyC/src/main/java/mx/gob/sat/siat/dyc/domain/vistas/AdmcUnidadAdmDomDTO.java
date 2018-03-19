package mx.gob.sat.siat.dyc.domain.vistas;

import java.io.Serializable;

public class AdmcUnidadAdmDomDTO implements Serializable
{
    @SuppressWarnings("compatibility:-3330113057835130572")
    private static final long serialVersionUID = 1L;
    
    private String calle;
    private String numExterior;
    private String numInterior;
    private String colonia;
    private Integer codigoPostal;
    private String municipio;
    private String entidadFed;
    private String referencias;
    private Integer idUnidadAdmDom;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getNumInterior() {
        return numInterior;
    }

    public void setNumInterior(String numInterior) {
        this.numInterior = numInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEntidadFed() {
        return entidadFed;
    }

    public void setEntidadFed(String entidadFed) {
        this.entidadFed = entidadFed;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public Integer getIdUnidadAdmDom() {
        return idUnidadAdmDom;
    }

    public void setIdUnidadAdmDom(Integer idUnidadAdmDom) {
        this.idUnidadAdmDom = idUnidadAdmDom;
    }
}

