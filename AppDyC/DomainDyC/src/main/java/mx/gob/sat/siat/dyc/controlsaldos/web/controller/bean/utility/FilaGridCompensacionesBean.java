package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.math.BigDecimal;

import java.util.Date;


public class FilaGridCompensacionesBean
{
    private Integer idMovCompensacion;
    private String tipoCompensacion;
    private String numControl;
    private BigDecimal importeCompensado;
    private BigDecimal importeComphist;
    private Date fechaDeclaracionDestino;
    private String conceptoDestino;
    private String ejercicioDestino;
    private String periodoDestino;
    private String tipoResolucion;
    private Date fechaResolucion;
    
    //se ocupan para enviar el valor formateado al reporte
    private String fechaDeclaracionDestinoStr;
    private String importeCompensadoStr;
    private String fechaResolucionStr;
    private String importeCompHistStr;
    private BigDecimal importeCompRegistrado;
    private Date fechaFin;

    private String notas;

    public String getTipoCompensacion() {
        return tipoCompensacion;
    }

    public void setTipoCompensacion(String tipoCompensacion) {
        this.tipoCompensacion = tipoCompensacion;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Date getFechaDeclaracionDestino() {
        if (fechaDeclaracionDestino != null) {
           return (Date)fechaDeclaracionDestino.clone();
        } else {
           return null;
        }
    }

    public void setFechaDeclaracionDestino(Date fechaDeclaracionDestino) {
        if (fechaDeclaracionDestino != null) {
            this.fechaDeclaracionDestino = (Date)fechaDeclaracionDestino.clone();
        } else {
            this.fechaDeclaracionDestino = null;
        }
    }

    public String getPeriodoDestino() {
        return periodoDestino;
    }

    public void setPeriodoDestino(String periodoImporte) {
        this.periodoDestino = periodoImporte;
    }

    public String getEjercicioDestino() {
        return ejercicioDestino;
    }

    public void setEjercicioDestino(String ejercicioDestino) {
        this.ejercicioDestino = ejercicioDestino;
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

    public Integer getIdMovCompensacion() {
        return idMovCompensacion;
    }

    public void setIdMovCompensacion(Integer idMovCompensacion) {
        this.idMovCompensacion = idMovCompensacion;
    }

    public String getFechaDeclaracionDestinoStr() {
        return fechaDeclaracionDestinoStr;
    }

    public void setFechaDeclaracionDestinoStr(String fechaDeclaracionDestinoStr) {
        this.fechaDeclaracionDestinoStr = fechaDeclaracionDestinoStr;
    }

    public String getImporteCompensadoStr() {
        return importeCompensadoStr;
    }

    public void setImporteCompensadoStr(String importeCompensadoStr) {
        this.importeCompensadoStr = importeCompensadoStr;
    }

    public String getFechaResolucionStr() {
        return fechaResolucionStr;
    }

    public void setFechaResolucionStr(String fechaResolucionStr) {
        this.fechaResolucionStr = fechaResolucionStr;
    }

    public String getConceptoDestino() {
        return conceptoDestino;
    }

    public void setConceptoDestino(String conceptoDestino) {
        this.conceptoDestino = conceptoDestino;
    }

    public String getImporteCompHistStr() {
        return importeCompHistStr;
    }

    public void setImporteCompHistStr(String importeCompHistStr) {
        this.importeCompHistStr = importeCompHistStr;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public BigDecimal getImporteComphist() {
        return importeComphist;
    }

    public void setImporteComphist(BigDecimal importeComphist) {
        this.importeComphist = importeComphist;
    }

    public BigDecimal getImporteCompRegistrado() {
        return importeCompRegistrado;
    }

    public void setImporteCompRegistrado(BigDecimal importeCompRegistrado) {
        this.importeCompRegistrado = importeCompRegistrado;
    }
    
    public Date getFechaFin() {
        if (fechaFin != null) {
           return (Date)fechaFin.clone();
        } else {
           return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }
    
}
