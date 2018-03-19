/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.domain.controlsaldos;

/**
 *
 * @author GAER8674
 */
public class DycControlSaldosObtenerIcepDTO {
    private String rfc;
    private Integer idConcepto;
    private Integer idEjercicio;
    private Integer idPeriodo;
    private Integer idOrigenSaldo;

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
     * @return the idConcepto
     */
    public Integer getIdConcepto() {
        return idConcepto;
    }

    /**
     * @param idConcepto the idConcepto to set
     */
    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    /**
     * @return the idEjercicio
     */
    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    /**
     * @param idEjercicio the idEjercicio to set
     */
    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    /**
     * @return the idPeriodo
     */
    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    /**
     * @param idPeriodo the idPeriodo to set
     */
    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
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
}
