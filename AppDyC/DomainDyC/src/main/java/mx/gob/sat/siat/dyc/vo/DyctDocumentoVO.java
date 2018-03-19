package mx.gob.sat.siat.dyc.vo;

import java.util.Date;

public class DyctDocumentoVO {
    public DyctDocumentoVO() {
        super();
    }
    private String numControlDoc;
    private int idTipoDocumento;
    private String numControl;
    private String url;
    private Date fechaRegistro;
    private String nombreArchivo;
    private int idEstadoDoc;
    private int idEstadoReq;
    private int idPlantilla;
    private Date fechaIniPlazoLegal;
    private Date fechaFinPlazoLegal;
    private int idTipoFirma;
    private int numEmpleadoAprob;
    private String folioNYV;
    private String cadenaOriginal;
    private String selloDigital;
    private String firmaElectronica;

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setIdEstadoDoc(int idEstadoDoc) {
        this.idEstadoDoc = idEstadoDoc;
    }

    public int getIdEstadoDoc() {
        return idEstadoDoc;
    }

    public void setIdEstadoReq(int idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public int getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setFechaIniPlazoLegal(Date fechaIniPlazoLegal) {
        if (null != fechaIniPlazoLegal) {
            this.fechaIniPlazoLegal = (Date)fechaIniPlazoLegal.clone();
        } else {
            this.fechaIniPlazoLegal = null;
        }
    }

    public Date getFechaIniPlazoLegal() {
        if (fechaIniPlazoLegal != null) {
            return (Date)fechaIniPlazoLegal.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinPlazoLegal(Date fechaFinPlazoLegal) {
        if (fechaFinPlazoLegal != null) {
            this.fechaFinPlazoLegal = (Date)fechaFinPlazoLegal.clone();
        } else {
            this.fechaFinPlazoLegal = null;
        }
    }

    public Date getFechaFinPlazoLegal() {
        if (fechaFinPlazoLegal != null) {
            return (Date)fechaFinPlazoLegal.clone();
        } else {
            return null;
        }
    }

    public void setIdTipoFirma(int idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    public int getIdTipoFirma() {
        return idTipoFirma;
    }

    public void setNumEmpleadoAprob(int numEmpleadoAprob) {
        this.numEmpleadoAprob = numEmpleadoAprob;
    }

    public int getNumEmpleadoAprob() {
        return numEmpleadoAprob;
    }

    public void setFolioNYV(String folioNYV) {
        this.folioNYV = folioNYV;
    }

    public String getFolioNYV() {
        return folioNYV;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setFirmaElectronica(String firmaElectronica) {
        this.firmaElectronica = firmaElectronica;
    }

    public String getFirmaElectronica() {
        return firmaElectronica;
    }
}
