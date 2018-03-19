/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.domain.devolucionaut;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

/**
 *
 * @author Mauricio Lira López
 **/
public class DetalleTramGenerarRepoDTO implements Serializable{
    
    @SuppressWarnings("compatibility:279465436847886487")
    private static final long serialVersionUID = 1L;
    
    private Integer numEmpleado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numControl;
    private String rfc;
    private String tramite;
    private String tipoTramite;
    private String estadoTramite;
    private Date fechaInicioTramite;
    private BigDecimal importeSaldoF;
    
    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(String estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    public Date getFechaInicioTramite() {
        if (null != fechaInicioTramite){
            return (Date) fechaInicioTramite.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicioTramite(Date fechaInicioTramite) {
        if (null != fechaInicioTramite){
            this.fechaInicioTramite = (Date) fechaInicioTramite.clone();
        } else {
            this.fechaInicioTramite = null;
        }
    }

    public BigDecimal getImporteSaldoF() {
        return importeSaldoF;
    }

    public void setImporteSaldoF(BigDecimal importeSaldoF) {
        this.importeSaldoF = importeSaldoF;
    }
}
