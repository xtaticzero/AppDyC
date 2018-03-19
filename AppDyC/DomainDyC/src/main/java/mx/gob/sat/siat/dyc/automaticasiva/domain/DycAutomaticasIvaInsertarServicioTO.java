/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.automaticasiva.domain;

/**
 * <p>Datos del contribuyente y su devolucion automatica de IVA.</p>
 * <em>TO para enviar parametros a un metodo.</em>
 * @author GAER8674
 */
public class DycAutomaticasIvaInsertarServicioTO {
    private String numeroControl;
    private Integer idTipoServicio;
    private Integer numEmpleadoDict;
    private Integer idOrigenSaldo;
    private Integer idTipoTramite;
    private String rfc;
    private Integer claveSir;
    private String boid;

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
     * @return the idTipoServicio
     */
    public Integer getIdTipoServicio() {
        return idTipoServicio;
    }

    /**
     * @param idTipoServicio the idTipoServicio to set
     */
    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    /**
     * @return the idOrigenSaldo
     */
    public Integer getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    /**
     * @param idOrigenSaldo the idOrigenSaldo to set
     */
    public void setIdOrigenSaldo(Integer idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    /**
     * @return the idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * @param idTipoTramite the idTipoTramite to set
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the claveSir
     */
    public Integer getClaveSir() {
        return claveSir;
    }

    /**
     * @param claveSir the claveSir to set
     */
    public void setClaveSir(Integer claveSir) {
        this.claveSir = claveSir;
    }

    /**
     * @return the boid
     */
    public String getBoid() {
        return boid;
    }

    /**
     * @param boid the boid to set
     */
    public void setBoid(String boid) {
        this.boid = boid;
    }

    /**
     * @return the numEmpleadoDict
     */
    public Integer getNumEmpleadoDict() {
        return numEmpleadoDict;
    }

    /**
     * @param numEmpleadoDict the numEmpleadoDict to set
     */
    public void setNumEmpleadoDict(Integer numEmpleadoDict) {
        this.numEmpleadoDict = numEmpleadoDict;
    }
}
