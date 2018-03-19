package cte.dyc.dto;

import java.io.Serializable;

import java.util.Date;


public class DyctDocumentoDTO implements Serializable {

    @SuppressWarnings("compatibility:-7388423240963927660")
    private static final long serialVersionUID = 1L;

    private Date fechaFinPlazoLegal;
    private Date fechaIniPlazoLegal;
    private Date fechaRegistro;
    private Integer dyccEstadoReqDTO;
    private Integer dyccEstadoDocDTO;
    private String nombreArchivo;
    private String numControlDoc;
    private String url;
    private DycpServicioDTO dycpServicioDTO;
    private Integer dyccTipoDocumentoDTO;
    private EmpleadoDTO dyccAprobadorDTO;
    private Integer dyccTipoFirmaDTO; 
    private Integer dyccMatDocumentosDTO;
    private String folioNyv;


    public DyctDocumentoDTO() {
        super();
    }

    public void setFechaFinPlazoLegal(Date fechaFinPlazoLegal) {
        if (null != fechaFinPlazoLegal) {
            this.fechaFinPlazoLegal = (Date)fechaFinPlazoLegal.clone();
        } else {
            this.fechaFinPlazoLegal = null;
        }
    }

    public Date getFechaFinPlazoLegal() {
        if (null != fechaFinPlazoLegal) {
            return (Date)fechaFinPlazoLegal.clone();
        } else {
            return null;
        }
    }

    public void setFechaIniPlazoLegal(Date fechaIniPlazoLegal) {
        if (null != fechaIniPlazoLegal) {
            this.fechaIniPlazoLegal = (Date)fechaIniPlazoLegal.clone();
        } else {
            this.fechaIniPlazoLegal = null;
        }
    }

    public Date getFechaIniPlazoLegal() {
        if (null != fechaIniPlazoLegal) {
            return (Date)fechaIniPlazoLegal.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setDyccEstadoReqDTO(Integer dyccEstadoReqDTO) {
        this.dyccEstadoReqDTO = dyccEstadoReqDTO;
    }

    public Integer getDyccEstadoReqDTO() {
        return dyccEstadoReqDTO;
    }

    public void setDyccEstadoDocDTO(Integer dyccEstadoDocDTO) {
        this.dyccEstadoDocDTO = dyccEstadoDocDTO;
    }

    public Integer getDyccEstadoDocDTO() {
        return dyccEstadoDocDTO;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setDyccTipoDocumentoDTO(Integer dyccTipoDocumentoDTO) {
        this.dyccTipoDocumentoDTO = dyccTipoDocumentoDTO;
    }

    public Integer getDyccTipoDocumentoDTO() {
        return dyccTipoDocumentoDTO;
    }

    public void setDyccAprobadorDTO(EmpleadoDTO dyccAprobadorDTO) {
        this.dyccAprobadorDTO = dyccAprobadorDTO;
    }

    public EmpleadoDTO getDyccAprobadorDTO() {
        return dyccAprobadorDTO;
    }

    public void setDyccTipoFirmaDTO(Integer dyccTipoFirmaDTO) {
        this.dyccTipoFirmaDTO = dyccTipoFirmaDTO;
    }

    public Integer getDyccTipoFirmaDTO() {
        return dyccTipoFirmaDTO;
    }

    public void setDyccMatDocumentosDTO(Integer dyccMatDocumentosDTO) {
        this.dyccMatDocumentosDTO = dyccMatDocumentosDTO;
    }

    public Integer getDyccMatDocumentosDTO() {
        return dyccMatDocumentosDTO;
    }

    public void setFolioNyv(String folioNyv) {
        this.folioNyv = folioNyv;
    }

    public String getFolioNyv() {
        return folioNyv;
    }
}
