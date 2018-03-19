/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;


/**
 * DTO de la tabla DYCT_DOCUMENTO
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctDocumentoDTO implements Serializable {

    @SuppressWarnings("compatibility:1928623589332520421")
    private static final long serialVersionUID = 1L;

    private Date fechaFinPlazoLegal;
    private Date fechaIniPlazoLegal;
    private Date fechaRegistro;
    private DyccEstadoReqDTO dyccEstadoReqDTO;
    private DyccEstadoDocDTO dyccEstadoDocDTO;
    private String nombreArchivo;
    private String numControlDoc;
    private String url;
    private DycpServicioDTO dycpServicioDTO;
    private DyccTipoDocumentoDTO dyccTipoDocumentoDTO;
    private DyccAprobadorDTO dyccAprobadorDTO;
    private DyccTipoFirmaDTO dyccTipoFirmaDTO;
    private DyccMatDocumentosDTO dyccMatDocumentosDTO;
    private String folioNyv;
    private String cadenaOriginal;
    private String selloDigital;
    private String firmaElectronia;

    public DyctDocumentoDTO() {
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

    public void setDyccTipoDocumentoDTO(DyccTipoDocumentoDTO dyccTipoDocumentoDTO) {
        this.dyccTipoDocumentoDTO = dyccTipoDocumentoDTO;
    }

    public DyccTipoDocumentoDTO getDyccTipoDocumentoDTO() {
        return dyccTipoDocumentoDTO;
    }

    public void setDyccAprobadorDTO(DyccAprobadorDTO dyccAprobadorDTO) {
        this.dyccAprobadorDTO = dyccAprobadorDTO;
    }

    public DyccAprobadorDTO getDyccAprobadorDTO() {
        return dyccAprobadorDTO;
    }

    public void setDyccTipoFirmaDTO(DyccTipoFirmaDTO dyccTipoFirmaDTO) {
        this.dyccTipoFirmaDTO = dyccTipoFirmaDTO;
    }

    public DyccTipoFirmaDTO getDyccTipoFirmaDTO() {
        return dyccTipoFirmaDTO;
    }

    public void setDyccMatDocumentosDTO(DyccMatDocumentosDTO dyccMatDocumentosDTO) {
        this.dyccMatDocumentosDTO = dyccMatDocumentosDTO;
    }

    public DyccMatDocumentosDTO getDyccMatDocumentosDTO() {
        return dyccMatDocumentosDTO;
    }

    public void setDyccEstadoReqDTO(DyccEstadoReqDTO dyccEstadoReqDTO) {
        this.dyccEstadoReqDTO = dyccEstadoReqDTO;
    }

    public DyccEstadoReqDTO getDyccEstadoReqDTO() {
        return dyccEstadoReqDTO;
    }

    public void setDyccEstadoDocDTO(DyccEstadoDocDTO dyccEstadoDocDTO) {
        this.dyccEstadoDocDTO = dyccEstadoDocDTO;
    }

    public DyccEstadoDocDTO getDyccEstadoDocDTO() {
        return dyccEstadoDocDTO;
    }

    public void setFolioNyv(String folioNyv) {
        this.folioNyv = folioNyv;
    }

    public String getFolioNyv() {
        return folioNyv;
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

    public void setFirmaElectronia(String firmaElectronia) {
        this.firmaElectronia = firmaElectronia;
    }

    public String getFirmaElectronia() {
        return firmaElectronia;
    }
}
