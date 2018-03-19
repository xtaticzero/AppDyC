package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.util.Date;

public class FilaGridDevolucionesBean
{
    private String tipoDevolucion;
    private String numControl;
    private String importeSolDevStr;
    private String tipoResolucion;
    private String fechaResolucionStr;
    private String importeAutorizadoStr;
    private String importeNegadoStr;
    private String actualizacionStr;
    private String interesesStr;
    private String retInteresesStr;
    private String impCompensadoStr;    
    private String importeNetoDevStr;
    
    private Date fechaResolucion;
    private Double importeSolDev;
    private Double importeAutorizado;
    private Double importeNegado;
    private Double actualizacion;
    private Double intereses;
    private Double retIntereses;
    private Double impCompensado;
    private Double importeNetoDev;

    private Integer idEstatusSolicitud;
    private Integer idMovDevolucion;

    public String getTipoDevolucion() {
        return tipoDevolucion;
    }

    public void setTipoDevolucion(String tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getTipoResolucion() {
        return tipoResolucion;
    }

    public void setTipoResolucion(String tipoResolucion) {
        this.tipoResolucion = tipoResolucion;
    }

    public Date getFechaResolucion() {
        if (fechaResolucion != null) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
        }
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (fechaResolucion != null) {
            this.fechaResolucion = (Date)fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
        }
    }

    public Double getImporteSolDev() {
        return importeSolDev;
    }

    public void setImporteSolDev(Double importeSolDev) {
        this.importeSolDev = importeSolDev;
    }

    public Double getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteAutorizado(Double importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public Double getImporteNegado() {
        return importeNegado;
    }

    public void setImporteNegado(Double importeNegado) {
        this.importeNegado = importeNegado;
    }

    public Double getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Double actualizacion) {
        this.actualizacion = actualizacion;
    }

    public Double getIntereses() {
        return intereses;
    }

    public void setIntereses(Double intereses) {
        this.intereses = intereses;
    }

    public Double getRetIntereses() {
        return retIntereses;
    }

    public void setRetIntereses(Double retIntereses) {
        this.retIntereses = retIntereses;
    }

    public Double getImpCompensado() {
        return impCompensado;
    }

    public void setImpCompensado(Double impCompensado) {
        this.impCompensado = impCompensado;
    }

    public Double getImporteNetoDev() {
        return importeNetoDev;
    }

    public void setImporteNetoDev(Double importeNetoDev) {
        this.importeNetoDev = importeNetoDev;
    }

    public String getFechaResolucionStr() {
        return fechaResolucionStr;
    }

    public void setFechaResolucionStr(String fechaResolucionStr) {
        this.fechaResolucionStr = fechaResolucionStr;
    }

    public String getImporteSolDevStr() {
        return importeSolDevStr;
    }

    public void setImporteSolDevStr(String importeSolDevStr) {
        this.importeSolDevStr = importeSolDevStr;
    }

    public String getImporteAutorizadoStr() {
        return importeAutorizadoStr;
    }

    public void setImporteAutorizadoStr(String importeAutorizadoStr) {
        this.importeAutorizadoStr = importeAutorizadoStr;
    }

    public String getImporteNegadoStr() {
        return importeNegadoStr;
    }

    public void setImporteNegadoStr(String importeNegadoStr) {
        this.importeNegadoStr = importeNegadoStr;
    }

    public String getActualizacionStr() {
        return actualizacionStr;
    }

    public void setActualizacionStr(String actualizacionStr) {
        this.actualizacionStr = actualizacionStr;
    }

    public String getInteresesStr() {
        return interesesStr;
    }

    public void setInteresesStr(String interesesStr) {
        this.interesesStr = interesesStr;
    }

    public String getRetInteresesStr() {
        return retInteresesStr;
    }

    public void setRetInteresesStr(String retInteresesStr) {
        this.retInteresesStr = retInteresesStr;
    }

    public String getImpCompensadoStr() {
        return impCompensadoStr;
    }

    public void setImpCompensadoStr(String impCompensadoStr) {
        this.impCompensadoStr = impCompensadoStr;
    }

    public String getImporteNetoDevStr() {
        return importeNetoDevStr;
    }

    public void setImporteNetoDevStr(String importeNetoDevStr) {
        this.importeNetoDevStr = importeNetoDevStr;
    }

    public Integer getIdEstatusSolicitud() {
        return idEstatusSolicitud;
    }

    public void setIdEstatusSolicitud(Integer idEstatusSolicitud) {
        this.idEstatusSolicitud = idEstatusSolicitud;
    }

    public Integer getIdMovDevolucion() {
        return idMovDevolucion;
    }

    public void setIdMovDevolucion(Integer idMovDevolucion) {
        this.idMovDevolucion = idMovDevolucion;
    }
}
