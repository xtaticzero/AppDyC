/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.automaticasiva.util.constante;

/**
 *
 * @author GAER8674
 */
public enum EDycAutomaticasIvaEstadoCasoDevolucion {

    AUTORIZADA_TOTAL(12, 1, 2, 1, "AUTORIZADA TOTAL"),
    PARCIAL_REM_DISP(10, 3, 2, 0, "AUTORIZADA PARCIAL CON REMANENTE DISPONIBLE"),
    DESISTIDA(5, 5, 2, 0, "DESISTIDA"),
    NEGADA_SAD(23, 4, 2, 0, "NEGADA"),
    AUTORIZADA_PARCIAL(22, 14, 2, 3, "AUTORIZADA PARCIAL"),;

    private final Integer idEstadoSolicitud;
    private final Integer idTipoResol;
    private final Integer idEstResol;
    private final Integer idTipoResolWS;
    private final String descripcion;

    private EDycAutomaticasIvaEstadoCasoDevolucion(int idEstadoSolicitud, Integer idTipoResol, Integer idEstResol, Integer idTipoResolWS, String descripcion) {
        this.idEstadoSolicitud = idEstadoSolicitud;
        this.idTipoResol = idTipoResol;
        this.idEstResol = idEstResol;
        this.idTipoResolWS = idTipoResolWS;
        this.descripcion = descripcion;
    }

    /**
     * @return the idEstadoSolicitud
     */
    public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    /**
     * @return the idTipoResol
     */
    public Integer getIdTipoResol() {
        return idTipoResol;
    }

    /**
     * @return the idEstResol
     */
    public Integer getIdEstResol() {
        return idEstResol;
    }

    /**
     * 
     * @return the idTipoResolWS
     */
    public Integer getIdTipoResolWS() {
        return idTipoResolWS;
    }

    /**
     * 
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

}
