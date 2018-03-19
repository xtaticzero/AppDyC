package mx.gob.sat.siat.dyc.admcat.dto.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;


public class ConsultaTipoTramiteVO {
    public ConsultaTipoTramiteVO() {
        super();
    }
    
    private DatosTipoTramiteVO    datosTipoTramiteVO;
    private GuardadoTipoTramiteVO guardadoTipoTramiteVO;
    
    private DyccOrigenSaldoDTO   origenSaldo;
    private DyccImpuestoDTO      impuesto;
    private DyccConceptoDTO      concepto;
    private DyccTipoPlazoDTO     tipoPlgazo;
    private DyccTipoServicioDTO  tipoServicio;
    private DyccTipoPlazoDTO     listaTipoPlazog;
    
    private List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmvaTipo;
    
    private String requiereCCI;
    private String resolucionAutomatica;

    public void setDatosTipoTramiteVO(DatosTipoTramiteVO datosTipoTramiteVO) {
        this.datosTipoTramiteVO = datosTipoTramiteVO;
    }

    public DatosTipoTramiteVO getDatosTipoTramiteVO() {
        return datosTipoTramiteVO;
    }

    public void setGuardadoTipoTramiteVO(GuardadoTipoTramiteVO guardadoTipoTramiteVO) {
        this.guardadoTipoTramiteVO = guardadoTipoTramiteVO;
    }

    public GuardadoTipoTramiteVO getGuardadoTipoTramiteVO() {
        return guardadoTipoTramiteVO;
    }

    public void setTipoPlgazo(DyccTipoPlazoDTO tipoPlgazo) {
        this.tipoPlgazo = tipoPlgazo;
    }

    public DyccTipoPlazoDTO getTipoPlgazo() {
        return tipoPlgazo;
    }

    public void setOrigenSaldo(DyccOrigenSaldoDTO origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public DyccOrigenSaldoDTO getOrigenSaldo() {
        return origenSaldo;
    }

    public void setImpuesto(DyccImpuestoDTO impuesto) {
        this.impuesto = impuesto;
    }

    public DyccImpuestoDTO getImpuesto() {
        return impuesto;
    }

    public void setConcepto(DyccConceptoDTO concepto) {
        this.concepto = concepto;
    }

    public DyccConceptoDTO getConcepto() {
        return concepto;
    }

    public void setTipoServicio(DyccTipoServicioDTO tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public DyccTipoServicioDTO getTipoServicio() {
        return tipoServicio;
    }

    public void setRequiereCCI(String requiereCCI) {
        this.requiereCCI = requiereCCI;
    }

    public String getRequiereCCI() {
        return requiereCCI;
    }

    public void setResolucionAutomatica(String resolucionAutomatica) {
        this.resolucionAutomatica = resolucionAutomatica;
    }

    public String getResolucionAutomatica() {
        return resolucionAutomatica;
    }

    public void setListaUnidadAdmvaTipo(List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmvaTipo) {
        this.listaUnidadAdmvaTipo = listaUnidadAdmvaTipo;
    }

    public List<AdmcUnidAdmvaTipoDTO> getListaUnidadAdmvaTipo() {
        return listaUnidadAdmvaTipo;
    }

    public void setListaTipoPlazog(DyccTipoPlazoDTO listaTipoPlazog) {
        this.listaTipoPlazog = listaTipoPlazog;
    }

    public DyccTipoPlazoDTO getListaTipoPlazog() {
        return listaTipoPlazog;
    }
}
