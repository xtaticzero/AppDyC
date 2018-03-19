package mx.gob.sat.siat.dyc.gestionsol.vo.revision;

import java.math.BigDecimal;

public class ResumenRevisionVO {
    public ResumenRevisionVO() {
        super();
    }
    
    private String rfcContribuyente;
    private String nombreORazonSocial;
    private String numControl;
    private String numControlDoc;
    private String tipoTramite;
    private String tipoResolucion;
    
    private BigDecimal importeAutorizado;
    private BigDecimal importeNegado;
    private BigDecimal importeNeto;
    private BigDecimal importeSolicitado;

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setNombreORazonSocial(String nombreORazonSocial) {
        this.nombreORazonSocial = nombreORazonSocial;
    }

    public String getNombreORazonSocial() {
        return nombreORazonSocial;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoResolucion(String tipoResolucion) {
        this.tipoResolucion = tipoResolucion;
    }

    public String getTipoResolucion() {
        return tipoResolucion;
    }

    public void setImporteAutorizado(BigDecimal importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public BigDecimal getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteNegado(BigDecimal importeNegado) {
        this.importeNegado = importeNegado;
    }

    public BigDecimal getImporteNegado() {
        return importeNegado;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }
}
