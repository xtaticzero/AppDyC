/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.domain;

/**
 * <p>Para envio de valores al validador del Request (Solo los datos que valida 
 * internamente el Web Service los demas datos se pasan sin validar).</p>
 * <em>TO para enviar parametros a un metodo.</em>
 * @author GAER8674
 */
public class BuzonTribValidarDatosTO {
    private int idComunicado;
    private String rfc;
    private String vigenciaIni;
    private String vigenciaFin;

    public BuzonTribValidarDatosTO() {}

    public BuzonTribValidarDatosTO(int idComunicado, String rfc, String vigenciaIni, String vigenciaFin) {
        this.idComunicado = idComunicado;
        this.rfc = rfc;
        this.vigenciaIni = vigenciaIni;
        this.vigenciaFin = vigenciaFin;
    }

    
    /**
     * @return the idComunicado
     */
    public int getIdComunicado() {
        return idComunicado;
    }

    /**
     * @param idComunicado the idComunicado to set
     */
    public void setIdComunicado(int idComunicado) {
        this.idComunicado = idComunicado;
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
     * @return the vigenciaIni
     */
    public String getVigenciaIni() {
        return vigenciaIni;
    }

    /**
     * @param vigenciaIni the vigenciaIni to set
     */
    public void setVigenciaIni(String vigenciaIni) {
        this.vigenciaIni = vigenciaIni;
    }

    /**
     * @return the vigenciaFin
     */
    public String getVigenciaFin() {
        return vigenciaFin;
    }

    /**
     * @param vigenciaFin the vigenciaFin to set
     */
    public void setVigenciaFin(String vigenciaFin) {
        this.vigenciaFin = vigenciaFin;
    }
}
