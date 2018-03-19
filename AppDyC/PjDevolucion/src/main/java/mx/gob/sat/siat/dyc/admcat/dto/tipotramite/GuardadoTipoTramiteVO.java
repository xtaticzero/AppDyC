package mx.gob.sat.siat.dyc.admcat.dto.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;


public class GuardadoTipoTramiteVO {    
    
    private DycaOrigenTramiteDTO origenTramite;
    private List<DycaOrigenTramiteDTO> listaOrigenTramites;
    private DyccTipoTramiteDTO   tipoTramite;
    
    private List<DyccAnexoTramiteDTO>  anexoTramite;
    private List<DycaTipoPeriodoTtDTO> tipoPeriodoTt;
    private List<DyccInfoTramiteDTO>   infoTramite;
    
    private List<DyccSubSaldoTramDTO>  subSaldoTram;
    private List<DyccTramiteRolDTO>    tramiteRol;
    private List<DyccTtSubtramiteDTO>  ttSubtramite;
    private List<DyccUnidadTramiteDTO> unidadTramite;

    public void setTipoTramite(DyccTipoTramiteDTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public DyccTipoTramiteDTO getTipoTramite() {
        return tipoTramite;
    }

    public void setUnidadTramite(List<DyccUnidadTramiteDTO> unidadTramite) {
        this.unidadTramite = unidadTramite;
    }

    public List<DyccUnidadTramiteDTO> getUnidadTramite() {
        return unidadTramite;
    }

    public void setTramiteRol(List<DyccTramiteRolDTO> tramiteRol) {
        this.tramiteRol = tramiteRol;
    }

    public List<DyccTramiteRolDTO> getTramiteRol() {
        return tramiteRol;
    }

    public void setAnexoTramite(List<DyccAnexoTramiteDTO> anexoTramite) {
        this.anexoTramite = anexoTramite;
    }

    public List<DyccAnexoTramiteDTO> getAnexoTramite() {
        return anexoTramite;
    }

    public void setInfoTramite(List<DyccInfoTramiteDTO> infoTramite) {
        this.infoTramite = infoTramite;
    }

    public List<DyccInfoTramiteDTO> getInfoTramite() {
        return infoTramite;
    }

    public void setTtSubtramite(List<DyccTtSubtramiteDTO> ttSubtramite) {
        this.ttSubtramite = ttSubtramite;
    }

    public List<DyccTtSubtramiteDTO> getTtSubtramite() {
        return ttSubtramite;
    }

    public void setSubSaldoTram(List<DyccSubSaldoTramDTO> subSaldoTram) {
        this.subSaldoTram = subSaldoTram;
    }

    public List<DyccSubSaldoTramDTO> getSubSaldoTram() {
        return subSaldoTram;
    }

    public void setOrigenTramite(DycaOrigenTramiteDTO origenTramite) {
        this.origenTramite = origenTramite;
    }

    public DycaOrigenTramiteDTO getOrigenTramite() {
        return origenTramite;
    }

    public void setTipoPeriodoTt(List<DycaTipoPeriodoTtDTO> tipoPeriodoTt) {
        this.tipoPeriodoTt = tipoPeriodoTt;
    }

    public List<DycaTipoPeriodoTtDTO> getTipoPeriodoTt() {
        return tipoPeriodoTt;
    }

    public void setListaOrigenTramites(List<DycaOrigenTramiteDTO> listaOrigenTramites) {
        this.listaOrigenTramites = listaOrigenTramites;
    }

    public List<DycaOrigenTramiteDTO> getListaOrigenTramites() {
        return listaOrigenTramites;
    }
}
