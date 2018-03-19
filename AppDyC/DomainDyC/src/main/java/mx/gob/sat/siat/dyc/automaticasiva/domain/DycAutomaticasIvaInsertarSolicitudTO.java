/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.automaticasiva.domain;

import java.math.BigDecimal;

import java.util.Date;

/**
 * <p>Datos de la solicitud de devolucion automatica de IVA.</p>
 * <em>TO para enviar parametros a un metodo.</em>
 * @author GAER8674
 */
public class DycAutomaticasIvaInsertarSolicitudTO {
    private String numeroControl;
    private Date fechaInicioTramite;
    private BigDecimal importeSolicitado;
    private String infoAdicional;
    private String diotNumOperacion;
    private Date diotFechaPresenta;
    private String retenedorRfc;
    private String altexConstancia;
    private Boolean esCertificado;
    private Integer idEstadoSolicitud;
    private Integer idSuborigenSaldo;
    private Integer idSubTipoTramite;
    private Integer idActividad;
    private Integer resolAutomatica;
    private Integer idSaldoIcep;
    private String cadenaOriginal;
    private String selloDigital;
    private Date fechaPresentacion;

    /**
     * @return the numeroControl
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     * @param numeroControl the numeroControl to set
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     * @return the importeSolicitado
     */
    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    /**
     * @param importeSolicitado the importeSolicitado to set
     */
    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    /**
     * @return the infoAdicional
     */
    public String getInfoAdicional() {
        return infoAdicional;
    }

    /**
     * @param infoAdicional the infoAdicional to set
     */
    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    /**
     * @return the diotNumOperacion
     */
    public String getDiotNumOperacion() {
        return diotNumOperacion;
    }

    /**
     * @param diotNumOperacion the diotNumOperacion to set
     */
    public void setDiotNumOperacion(String diotNumOperacion) {
        this.diotNumOperacion = diotNumOperacion;
    }

    /**
     * @return the diotFechaPresenta
     */
    public Date getDiotFechaPresenta() {
        if(null!=diotFechaPresenta){
            return (Date)diotFechaPresenta.clone();
        }
        else{
            return null;
        }
    }

    /**
     * @param diotFechaPresenta the diotFechaPresenta to set
     */
    public void setDiotFechaPresenta(Date diotFechaPresenta) {
        if(null!=diotFechaPresenta){
            this.diotFechaPresenta = (Date)diotFechaPresenta.clone();
        }
        else{
            this.diotFechaPresenta = null;
        }
    }

    /**
     * @return the retenedorRfc
     */
    public String getRetenedorRfc() {
        return retenedorRfc;
    }

    /**
     * @param retenedorRfc the retenedorRfc to set
     */
    public void setRetenedorRfc(String retenedorRfc) {
        this.retenedorRfc = retenedorRfc;
    }

    /**
     * @return the altexConstancia
     */
    public String getAltexConstancia() {
        return altexConstancia;
    }

    /**
     * @param altexConstancia the altexConstancia to set
     */
    public void setAltexConstancia(String altexConstancia) {
        this.altexConstancia = altexConstancia;
    }

    /**
     * @return the esCertificado
     */
    public Boolean getEsCertificado() {
        return esCertificado;
    }

    /**
     * @param esCertificado the esCertificado to set
     */
    public void setEsCertificado(Boolean esCertificado) {
        this.esCertificado = esCertificado;
    }

    /**
     * @return the idEstadoSolicitud
     */
    public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    /**
     * @param idEstadoSolicitud the idEstadoSolicitud to set
     */
    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    /**
     * @return the idSuborigenSaldo
     */
    public Integer getIdSuborigenSaldo() {
        return idSuborigenSaldo;
    }

    /**
     * @param idSuborigenSaldo the idSuborigenSaldo to set
     */
    public void setIdSuborigenSaldo(Integer idSuborigenSaldo) {
        this.idSuborigenSaldo = idSuborigenSaldo;
    }

    /**
     * @return the idSubTipoTramite
     */
    public Integer getIdSubTipoTramite() {
        return idSubTipoTramite;
    }

    /**
     * @param idSubTipoTramite the idSubTipoTramite to set
     */
    public void setIdSubTipoTramite(Integer idSubTipoTramite) {
        this.idSubTipoTramite = idSubTipoTramite;
    }

    /**
     * @return the idActividad
     */
    public Integer getIdActividad() {
        return idActividad;
    }

    /**
     * @param idActividad the idActividad to set
     */
    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    /**
     * @return the idSaldoIcep
     */
    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    /**
     * @param idSaldoIcep the idSaldoIcep to set
     */
    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    /**
     * @return the cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * @param cadenaOriginal the cadenaOriginal to set
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    /**
     * @return the selloDigital
     */
    public String getSelloDigital() {
        return selloDigital;
    }

    /**
     * @param selloDigital the selloDigital to set
     */
    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    /**
     * @return the fechaInicioTramite
     */
    public Date getFechaInicioTramite() {
        if(null!=fechaInicioTramite){
            return (Date)fechaInicioTramite.clone();
        }
        else{
            return null;
        }
    }

    /**
     * @param fechaInicioTramite the fechaInicioTramite to set
     */
    public void setFechaInicioTramite(Date fechaInicioTramite) {
        if(null!=fechaInicioTramite){
            this.fechaInicioTramite = (Date)fechaInicioTramite.clone();
        }
        else{
            this.fechaInicioTramite = null;
        }
    }

    /**
     * @return the fechaPresentacion
     */
    public Date getFechaPresentacion() {
        if(null!=fechaPresentacion){
            return (Date)fechaPresentacion.clone();
        }
        else{
            return null;
        }
    }

    /**
     * @param fechaPresentacion the fechaPresentacion to set
     */
    public void setFechaPresentacion(Date fechaPresentacion) {
        if(null!=fechaPresentacion){
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        }
        else{
            this.fechaPresentacion = null;
        }
    }

    /**
     * @return the resolAutomatica
     */
    public Integer getResolAutomatica() {
        return resolAutomatica;
    }

    /**
     * @param resolAutomatica the resolAutomatica to set
     */
    public void setResolAutomatica(Integer resolAutomatica) {
        this.resolAutomatica = resolAutomatica;
    }
}
