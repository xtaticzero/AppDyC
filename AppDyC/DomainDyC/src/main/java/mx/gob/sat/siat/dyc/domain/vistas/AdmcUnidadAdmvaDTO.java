package mx.gob.sat.siat.dyc.domain.vistas;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla ADMC_UNIDADADMVA
 * @author  Luis Alberto Dominguez Palomino [LADP]
 * @since   20/10/2014
 */
public class AdmcUnidadAdmvaDTO implements Serializable
{
    @SuppressWarnings("compatibility:-8466216237712909343")
    private static final long serialVersionUID = 1L;
    
    public static final String NOMBRE_TABLA = "DYCC_UNIDADADMVA";
    
    private Integer idUnidadAdmva;
    private Integer idUnidAdmvaTipo;
    private Integer idOrganismo;
    private String nombre;
    private String nomAbreviado;

    private Integer cargoTitular;
    private Integer idUnidadmpadre;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer claveSir;
    private Integer ordenSec;
    private String nomTitular;
    private Integer idUAdminAmbito;
    private String constanteJava;
    private String claveAgrs;
    private String abreviaturaTitular;
    private AdmcUnidadAdmDomDTO admcUnidadAdmDomDTO;

    private EnumTipoUnidadAdmvaDyC tipoUnidadAdmva;

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setIdUnidadAdmva(Integer idUnidadAdmva) {
        this.idUnidadAdmva = idUnidadAdmva;
    }

    public Integer getIdUnidadAdmva() {
        return idUnidadAdmva;
    }

    public void setIdUnidAdmvaTipo(Integer idUnidAdmvaTipo) {
        this.idUnidAdmvaTipo = idUnidAdmvaTipo;
    }

    public Integer getIdUnidAdmvaTipo() {
        return idUnidAdmvaTipo;
    }

    public void setIdOrganismo(Integer idOrganismo) {
        this.idOrganismo = idOrganismo;
    }

    public Integer getIdOrganismo() {
        return idOrganismo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNomAbreviado(String nomAbreviado) {
        this.nomAbreviado = nomAbreviado;
    }

    public String getNomAbreviado() {
        return nomAbreviado;
    }

    public void setCargoTitular(Integer cargoTitular) {
        this.cargoTitular = cargoTitular;
    }

    public Integer getCargoTitular() {
        return cargoTitular;
    }

    public void setIdUnidadmpadre(Integer idUnidadmpadre) {
        this.idUnidadmpadre = idUnidadmpadre;
    }

    public Integer getIdUnidadmpadre() {
        return idUnidadmpadre;
    }

    public void setClaveSir(Integer claveSir) {
        this.claveSir = claveSir;
    }

    public Integer getClaveSir() {
        return claveSir;
    }

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }

    public void setNomTitular(String nomTitular) {
        this.nomTitular = nomTitular;
    }

    public String getNomTitular() {
        return nomTitular;
    }

    public void setIdUAdminAmbito(Integer idUAdminAmbito) {
        this.idUAdminAmbito = idUAdminAmbito;
    }

    public Integer getIdUAdminAmbito() {
        return idUAdminAmbito;
    }

    public void setConstanteJava(String constanteJava) {
        this.constanteJava = constanteJava;
    }

    public String getConstanteJava() {
        return constanteJava;
    }

    public void setClaveAgrs(String claveAgrs) {
        this.claveAgrs = claveAgrs;
    }

    public String getClaveAgrs() {
        return claveAgrs;
    }

    public void setAbreviaturaTitular(String abreviaturaTitular) {
        this.abreviaturaTitular = abreviaturaTitular;
    }

    public String getAbreviaturaTitular() {
        return abreviaturaTitular;
    }

    public EnumTipoUnidadAdmvaDyC getTipoUnidadAdmva() {
        return tipoUnidadAdmva;
    }

    public void setTipoUnidadAdmva(EnumTipoUnidadAdmvaDyC tipoUnidadAdmva) {
        this.tipoUnidadAdmva = tipoUnidadAdmva;
    }

    public AdmcUnidadAdmDomDTO getAdmcUnidadAdmDomDTO() {
        return admcUnidadAdmDomDTO;
    }

    public void setAdmcUnidadAdmDomDTO(AdmcUnidadAdmDomDTO admcUnidadAdmDomDTO) {
        this.admcUnidadAdmDomDTO = admcUnidadAdmDomDTO;
    }
}