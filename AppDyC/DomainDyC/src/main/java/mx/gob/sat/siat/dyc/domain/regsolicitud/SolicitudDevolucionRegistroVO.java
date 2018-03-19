package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;


public class SolicitudDevolucionRegistroVO implements Serializable {
    @SuppressWarnings("compatibility:586324902541302490")
    private static final long serialVersionUID = 1L;

    public SolicitudDevolucionRegistroVO() {
        super();
    }

    private String idDev;
    private String fecha;
    private String origenDevolucion;
    private String tipoTramite;
    private String subOrigenSaldo;
    private String impuesto;
    private String consepto;
    private String ejercicio;
    private String periodo;
    private Double importeSolicitado;


    /**
     * @param idDev
     */


    public void setOrigenDevolucion(String origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }

    public String getOrigenDevolucion() {
        return origenDevolucion;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setSubOrigenSaldo(String subOrigenSaldo) {
        this.subOrigenSaldo = subOrigenSaldo;
    }

    public String getSubOrigenSaldo() {
        return subOrigenSaldo;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setConsepto(String consepto) {
        this.consepto = consepto;
    }

    public String getConsepto() {
        return consepto;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setIdDev(String idDev) {
        this.idDev = idDev;
    }

    public String getIdDev() {
        return idDev;
    }

    public void setImporteSolicitado(Double importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public Double getImporteSolicitado() {
        return importeSolicitado;
    }
}

