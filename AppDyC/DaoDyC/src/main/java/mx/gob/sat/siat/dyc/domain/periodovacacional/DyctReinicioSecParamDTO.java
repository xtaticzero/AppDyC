/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.domain.periodovacacional;

import java.util.Date;

/**
 *
 * @author root
 */
public class DyctReinicioSecParamDTO {

    private Integer idProgreInicio;
    private Date fechaProgramacion;
    private boolean estado;
    private Date fechaFin;

    public DyctReinicioSecParamDTO() {
    }

    public Integer getIdProgreInicio() {
        return idProgreInicio;
    }

    public void setIdProgreInicio(Integer idProgreInicio) {
        this.idProgreInicio = idProgreInicio;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion != null ? (Date) fechaProgramacion.clone() : null;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion != null ? (Date) fechaProgramacion.clone() : null;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaFin() {
        return fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    @Override
    public String toString() {
        return "DyctReinicioSecParamDTO{" + "idProgreInicio=" + idProgreInicio + ", fechaProgramacion=" + fechaProgramacion + ", estado=" + estado + ", fechaFin=" + fechaFin + '}';
    }

}
