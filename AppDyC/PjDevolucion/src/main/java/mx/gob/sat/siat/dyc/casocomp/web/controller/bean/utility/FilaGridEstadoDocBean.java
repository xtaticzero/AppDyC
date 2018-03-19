package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility;

import java.util.Date;

public class FilaGridEstadoDocBean
{
    private String tipoDocumento;
    private String accionSeguimiento;
    private String responsable;
    private String fecha;
    private String comentarios;
    private Date fechaReasignacion;
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getAccionSeguimiento() {
        return accionSeguimiento;
    }

    public void setAccionSeguimiento(String accionSeguimiento) {
        this.accionSeguimiento = accionSeguimiento;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the fechaReasignacion
     */
    public Date getFechaReasignacion() {
        return (fechaReasignacion != null ? (Date) fechaReasignacion.clone() : null);
    }

    /**
     * @param fechaReasignacion the fechaReasignacion to set
     */
    public void setFechaReasignacion(Date fechaReasignacion) {
        if (null != fechaReasignacion) {
            this.fechaReasignacion = (Date)fechaReasignacion.clone();
        } else {
            this.fechaReasignacion = null;
        }
    }

    
}