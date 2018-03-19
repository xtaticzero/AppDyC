/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.vo;

/**
 *
 * @author GAER8674
 */
public class DycAutomaticasIvaProcesadoDTO {
    private String rfc;
    private String numeroOperacion;
    private Integer idSaldoIcep;
    private String numeroControl;
    private boolean desistida;

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
     * @return the numeroOperacion
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion the numeroOperacion to set
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * @return the desistida
     */
    public boolean isDesistida() {
        return desistida;
    }

    /**
     * @param desistida the desistida to set
     */
    public void setDesistida(boolean desistida) {
        this.desistida = desistida;
    }
}
