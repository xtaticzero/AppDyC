package mx.gob.sat.siat.dyc.domain.resolucion;

import java.util.Date;

public class FilaGridTramitesBean {

    private String administracion;
    private String dictaminador;
    private String numControl;
    private String rfc;
    private String tramite;
    private String tipoTramite;
    private String estado;

    private Integer dias;
    private Integer tipoDia;

    private Double importe;

    private Date fechaPresentacion;

    private Integer claveAdm;

    private String periodo;
    private String ejercicio;
    private Date fechaResolucion;
    private Double importePagado;
    private Date fechaPago;
    private String impuestos;
    private String conceptoImpuestos;
    private Double importeAutorizado;

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date) fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date) fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getDias() {
        return dias;
    }

    public void setTipoDia(Integer tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Integer getTipoDia() {
        return tipoDia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Date getFechaResolucion() {
        if (fechaResolucion != null) {
            return (Date) fechaResolucion.clone();
        } else {
            return null;
        }
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (fechaResolucion != null) {
            this.fechaResolucion = (Date) fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
        }
    }

    public Double getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(Double importePagado) {
        this.importePagado = importePagado;
    }

    public Date getFechaPago() {
        if (fechaPago != null) {
            return (Date) fechaPago.clone();
        } else {
            return null;
        }
    }

    public void setFechaPago(Date fechaPago) {
        if (fechaPago != null) {
            this.fechaPago = (Date) fechaPago.clone();
        } else {
            this.fechaPago = null;
        }
    }

    public String getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(String impuestos) {
        this.impuestos = impuestos;
    }

    public String getConceptoImpuestos() {
        return conceptoImpuestos;
    }

    public void setConceptoImpuestos(String conceptoImpuestos) {
        this.conceptoImpuestos = conceptoImpuestos;
    }

    public Double getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteAutorizado(Double importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

}
