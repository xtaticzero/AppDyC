package mx.gob.sat.siat.dyc.gestionsol.vo.revision;

import java.math.BigDecimal;

import java.util.Date;


/**
 * @author Jesús Alfredo Hernandez Orozco
 */
public class RevisionCentralVO {
    public RevisionCentralVO() {
        super();
    }
    
    private String dictaminado;
    private String dictaminador;
    private String nombreDocumento;
    private String numControl;
    private String numControlDoc;
    private String rfcContribuyente;
    private String tipoTramite;
    private Date fechaRegistro;
    private Date fechaVencimiento;
    private BigDecimal importeSolicitado;

    public void setDictaminado(String dictaminado) {
        this.dictaminado = dictaminado;
    }

    public String getDictaminado() {
        return dictaminado;
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = (fechaRegistro != null) ? (Date) fechaRegistro.clone() : null;
    }

    public Date getFechaRegistro() {
        return (fechaRegistro != null) ? (Date) fechaRegistro.clone() : null;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = (fechaVencimiento != null) ? (Date) fechaVencimiento.clone() : null;
    }

    public Date getFechaVencimiento() {
        return (fechaVencimiento != null) ? (Date) fechaVencimiento.clone() : null;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }
}
