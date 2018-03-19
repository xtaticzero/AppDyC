package mx.gob.sat.siat.dyc.gestionsol.vo.solventacion;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;


public class SolventacionRequerimientoVO {
    public SolventacionRequerimientoVO() {
        super();
    }
    
    private String numControl;
    private String numControlDoc;
    private String urlArchivo;
    
    private Integer estadoSol;
    private Integer idEstadoReq;
    private Integer idEstadoSol;
    private Integer idTipoTramite; 
    
    private NotaExpedienteDTO notaExpedienteDTO;
    private SeguimientoDTO    seguimientoDTO;

    private List<AnexoRequeridoDTO> lstAnexosReqDTO;
    private List<DocRequeridoDTO>   lstDocumentosReqDTO;

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setEstadoSol(Integer estadoSol) {
        this.estadoSol = estadoSol;
    }

    public Integer getEstadoSol() {
        return estadoSol;
    }

    public void setIdEstadoReq(Integer idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public Integer getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setNotaExpedienteDTO(NotaExpedienteDTO notaExpedienteDTO) {
        this.notaExpedienteDTO = notaExpedienteDTO;
    }

    public NotaExpedienteDTO getNotaExpedienteDTO() {
        return notaExpedienteDTO;
    }

    public void setSeguimientoDTO(SeguimientoDTO seguimientoDTO) {
        this.seguimientoDTO = seguimientoDTO;
    }

    public SeguimientoDTO getSeguimientoDTO() {
        return seguimientoDTO;
    }

    public void setLstDocumentosReqDTO(List<DocRequeridoDTO> lstDocumentosReqDTO) {
        this.lstDocumentosReqDTO = lstDocumentosReqDTO;
    }

    public List<DocRequeridoDTO> getLstDocumentosReqDTO() {
        return lstDocumentosReqDTO;
    }

    public void setIdEstadoSol(Integer idEstadoSol) {
        this.idEstadoSol = idEstadoSol;
    }

    public Integer getIdEstadoSol() {
        return idEstadoSol;
    }

    public void setLstAnexosReqDTO(List<AnexoRequeridoDTO> lstAnexosReqDTO) {
        this.lstAnexosReqDTO = lstAnexosReqDTO;
    }

    public List<AnexoRequeridoDTO> getLstAnexosReqDTO() {
        return lstAnexosReqDTO;
    }
}
