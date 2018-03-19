package mx.gob.sat.siat.dyc.domain.devolucionaut;

import java.io.Serializable;

import java.util.Date;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class DyccMarcResultado implements Serializable {

    private static final long serialVersionUID = 2898677263364463689L;

    private String idMarcResultado;
    private String descripcion;
    private Date fechaFin;

    public DyccMarcResultado() {
    }

    public DyccMarcResultado(String idMarcResultado, String descripcion, Date fechaFin) {
        this.idMarcResultado = idMarcResultado;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    /**
     * @return the idMarcResultado
     */
    public String getIdMarcResultado() {
        return idMarcResultado;
    }

    /**
     * @param idMarcResultado the idMarcResultado to set
     */
    public void setIdMarcResultado(String idMarcResultado) {
        this.idMarcResultado = idMarcResultado;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    @Override
    public String toString() {
        return "{" + "idMarcResultado=" + idMarcResultado + ", descripcion=" + descripcion + ", fechaFin=" + fechaFin + '}';
    }

}
