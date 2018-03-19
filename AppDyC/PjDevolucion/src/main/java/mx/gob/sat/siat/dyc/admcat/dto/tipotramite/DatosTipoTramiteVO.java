package mx.gob.sat.siat.dyc.admcat.dto.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;


public class DatosTipoTramiteVO {
    public DatosTipoTramiteVO() {
        super();
    }
    private DyccImpuestoDTO            impuesto;
    private DyccTipoTramiteDTO         tipoTramite;
    private List<DyccConceptoDTO>      listaConceptos;
    private List<DyccImpuestoDTO>      listaImpuestos;
    private List<DyccInfoARequerirDTO> listaInfoARequerir;
    private List<DyccMatrizAnexosDTO>  listaMatrizAnexos;
    private List<DyccOrigenSaldoDTO>   lisdtaOrigenSaldo;
    private List<DycaOrigenTramiteDTO> listaOrigenTramite;
    private List<DyccRolDTO>           listaRoles;
    private List<DyccSubOrigSaldoDTO>  listaSubOrigenesSaldo;
    private List<DyccSubTramiteDTO>    listaSubtramites;
    private List<DyccTipoPeriodoDTO>   listaTipoPeriodo;
    private List<DyccTipoPlazoDTO>     listaTipoPlazo;
    private List<DyccTipoServicioDTO>  listaTipoServicio;
    private List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtvaTipo;
   
    public void setListaTipoServicio(List<DyccTipoServicioDTO> listaTipoServicio) {
        this.listaTipoServicio = listaTipoServicio;
    }

    public List<DyccTipoServicioDTO> getListaTipoServicio() {
        return listaTipoServicio;
    }

    public void setLisdtaOrigenSaldo(List<DyccOrigenSaldoDTO> lisdtaOrigenSaldo) {
        this.lisdtaOrigenSaldo = lisdtaOrigenSaldo;
    }

    public List<DyccOrigenSaldoDTO> getLisdtaOrigenSaldo() {
        return lisdtaOrigenSaldo;
    }

    public void setListaTipoPlazo(List<DyccTipoPlazoDTO> listaTipoPlazo) {
        this.listaTipoPlazo = listaTipoPlazo;
    }

    public List<DyccTipoPlazoDTO> getListaTipoPlazo() {
        return listaTipoPlazo;
    }

    public void setListaImpuestos(List<DyccImpuestoDTO> listaImpuestos) {
        this.listaImpuestos = listaImpuestos;
    }

    public List<DyccImpuestoDTO> getListaImpuestos() {
        return listaImpuestos;
    }

    public void setListaConceptos(List<DyccConceptoDTO> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public List<DyccConceptoDTO> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaRoles(List<DyccRolDTO> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<DyccRolDTO> getListaRoles() {
        return listaRoles;
    }

    public void setListaMatrizAnexos(List<DyccMatrizAnexosDTO> listaMatrizAnexos) {
        this.listaMatrizAnexos = listaMatrizAnexos;
    }

    public List<DyccMatrizAnexosDTO> getListaMatrizAnexos() {
        return listaMatrizAnexos;
    }

    public void setListaInfoARequerir(List<DyccInfoARequerirDTO> listaInfoARequerir) {
        this.listaInfoARequerir = listaInfoARequerir;
    }

    public List<DyccInfoARequerirDTO> getListaInfoARequerir() {
        return listaInfoARequerir;
    }

    public void setListaSubtramites(List<DyccSubTramiteDTO> listaSubtramites) {
        this.listaSubtramites = listaSubtramites;
    }

    public List<DyccSubTramiteDTO> getListaSubtramites() {
        return listaSubtramites;
    }

    public void setListaSubOrigenesSaldo(List<DyccSubOrigSaldoDTO> listaSubOrigenesSaldo) {
        this.listaSubOrigenesSaldo = listaSubOrigenesSaldo;
    }

    public List<DyccSubOrigSaldoDTO> getListaSubOrigenesSaldo() {
        return listaSubOrigenesSaldo;
    }

    public void setListaTipoPeriodo(List<DyccTipoPeriodoDTO> listaTipoPeriodo) {
        this.listaTipoPeriodo = listaTipoPeriodo;
    }

    public List<DyccTipoPeriodoDTO> getListaTipoPeriodo() {
        return listaTipoPeriodo;
    }

    public void setListaUnidadAdmtvaTipo(List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtvaTipo) {
        this.listaUnidadAdmtvaTipo = listaUnidadAdmtvaTipo;
    }

    public List<AdmcUnidAdmvaTipoDTO> getListaUnidadAdmtvaTipo() {
        return listaUnidadAdmtvaTipo;
    }

    public void setTipoTramite(DyccTipoTramiteDTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public DyccTipoTramiteDTO getTipoTramite() {
        return tipoTramite;
    }

    public void setListaOrigenTramite(List<DycaOrigenTramiteDTO> listaOrigenTramite) {
        this.listaOrigenTramite = listaOrigenTramite;
    }

    public List<DycaOrigenTramiteDTO> getListaOrigenTramite() {
        return listaOrigenTramite;
    }

    public void setImpuesto(DyccImpuestoDTO impuesto) {
        this.impuesto = impuesto;
    }

    public DyccImpuestoDTO getImpuesto() {
        return impuesto;
    }
}
