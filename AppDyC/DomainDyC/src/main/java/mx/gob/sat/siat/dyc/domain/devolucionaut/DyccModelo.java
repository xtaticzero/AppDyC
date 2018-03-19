package mx.gob.sat.siat.dyc.domain.devolucionaut;

import java.io.Serializable;

import java.util.Date;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class DyccModelo implements Serializable {

    private static final long serialVersionUID = -1158208632243842144L;

    private Integer idModelo;
    private String descripcion;
    private Date fechaFin;

    public DyccModelo() {
    }

    public DyccModelo(Integer idModelo, String descripcion, Date fechaFin) {
        this.idModelo = idModelo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    /**
     * @return the idModelo
     */
    public Integer getIdModelo() {
        return idModelo;
    }

    /**
     * @param idModelo the idModelo to set
     */
    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
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
        return "{" + "idModelo=" + idModelo + ", descripcion=" + descripcion + ", fechaFin=" + fechaFin + '}';
    }

}
