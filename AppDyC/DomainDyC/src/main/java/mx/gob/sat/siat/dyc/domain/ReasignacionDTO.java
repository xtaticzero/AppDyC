/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez Clase DTO para el Mapeo hacia la tabla
 * DYCA_REASIGNACION
 *
 */
public class ReasignacionDTO implements Serializable {
    private static final long serialVersionUID = 1267440654897167387L;

    private Integer idReasignacion;
    private String numcontrol;
    private Date fechaReasignacion;
    private Date fechaFin;
    private String origen;
    private Integer empleadoResponsable;
    private String nombreResponsable;
    private Integer empleadoAsignado;
    

    /**
     * @return the numcontrol
     */
    public String getNumcontrol() {
        return numcontrol;
    }

    /**
     * @param numcontrol the numcontrol to set
     */
    public void setNumcontrol(String numcontrol) {
        this.numcontrol = numcontrol;
    }

    /**
     * @return the fechaReasignacion
     */
    public Date getFechaReasignacion() {
        return (fechaReasignacion != null) ? (Date) fechaReasignacion.clone() : null;
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

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
       
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the idReasignacion
     */
    public Integer getIdReasignacion() {
        return idReasignacion;
    }

    /**
     * @param idReasignacion the idReasignacion to set
     */
    public void setIdReasignacion(Integer idReasignacion) {
        this.idReasignacion = idReasignacion;
    }

    /**
     * @return the empleadoResponsable
     */
    public Integer getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    /**
     * @param empleadoResponsable the empleadoResponsable to set
     */
    public void setEmpleadoResponsable(Integer empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }

    /**
     * @return the nombreResponsable
     */
    public String getNombreResponsable() {
        return nombreResponsable;
    }

    /**
     * @param nombreResponsable the nombreResponsable to set
     */
    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    /**
     * @return the empleadoAsignado
     */
    public Integer getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    /**
     * @param empleadoAsignado the empleadoAsignado to set
     */
    public void setEmpleadoAsignado(Integer empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

}
