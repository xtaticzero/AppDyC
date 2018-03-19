/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.domain.declaracion;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public class DeclaracionDTO implements Serializable {

    private static final long serialVersionUID = 7521384539906792658L;

    private Integer tipoDeclaracion;
    private Date fechaPresentacion;
    private Integer ejercicio;
    private Integer periodo;
    private String descripcionPeriodo;
    private Integer idFirma;
    private Integer idTipoProceso;
    private Integer aceptoPropuestaRecibida;
    private Integer creditoFiscal;

    public Integer getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setTipoDeclaracion(Integer tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Date getFechaPresentacion() {
        return (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getDescripcionPeriodo() {
        return descripcionPeriodo;
    }

    public void setDescripcionPeriodo(String descripcionPeriodo) {
        this.descripcionPeriodo = descripcionPeriodo;
    }

    public Integer getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(Integer idFirma) {
        this.idFirma = idFirma;
    }

    public Integer getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public Integer getAceptoPropuestaRecibida() {
        return aceptoPropuestaRecibida;
    }

    public void setAceptoPropuestaRecibida(Integer aceptoPropuestaRecibida) {
        this.aceptoPropuestaRecibida = aceptoPropuestaRecibida;
    }

    public Integer getCreditoFiscal() {
        return creditoFiscal;
    }

    public void setCreditoFiscal(Integer creditoFiscal) {
        this.creditoFiscal = creditoFiscal;
    }

}
