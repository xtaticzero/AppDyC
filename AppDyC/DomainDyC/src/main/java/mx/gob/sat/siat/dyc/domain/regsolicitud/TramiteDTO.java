/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

/**
 * Clase que tiene los valores seleccionados de la vista AdicionarSolicitud y
 * los valores que se almacenan., para despues guardarseen DycpSolicitudDTO
 *
 * @author Paola Rivera
 */
public class TramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:8896379872591979484")
    private static final long serialVersionUID = 1L;

    /**
     * Objetos del tab Tipo tramite
     */
    private CatalogoTO origenSaldo;
    private CatalogoTO tipoTramite;
    private CatalogoTO subOrigenSaldo;
    private CatalogoTO actividadEconomica;
    private PersonaDTO retenedor;
    private String infoAdicional;
    private List<String> rfcControladora;
    private List<ArchivoVO> anexos;
    private List<ArchivoVO> documentos;
    private String numControl;
    /**
     * Objetos del tab Datos ICEP
     */
    private CatalogoTO tipoPeriodo;
    private CatalogoTO periodo;
    private CatalogoTO ejercicio;
    private CatalogoTO subTramite;
    private DyccConceptoDTO concepto;
    private DyccImpuestoDTO impuesto;

    /**
     * Institucion Financiera
     */
    private DyctCuentaBancoDTO institucionFinanciera;

    /**
     * respuestas de PadronSectorAgropecuario
     */
    private CatalogoTO secAgp;

    /**
     * incosistencias
     */
    private List<CatalogoTO> inconsistencias;
    private InformacionSaldoFavorTO saldoFavor;
    private InformacionDeclarativaDTO infoDeclarativa;
    private PersonaDTO persona;
    private RolesContribuyenteDTO rolesContribuyente;
    private DiotDTO diot;
    private SpConsultarAltexDTO altex;
    private Integer saldoIcep;

    /**
     * Solcitud Temporal
     */
    private String solTemp;
    private Integer estadoActual;

    /**
     * Resolucion del trámite
     */
    private DyctResolucionDTO resolucionDTO;

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setRfcControladora(List<String> rfcControladora) {
        this.rfcControladora = rfcControladora;
    }

    public List<String> getRfcControladora() {
        return rfcControladora;
    }

    public void setOrigenSaldo(CatalogoTO origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public CatalogoTO getOrigenSaldo() {
        return origenSaldo;
    }

    public void setTipoTramite(CatalogoTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public CatalogoTO getTipoTramite() {
        return tipoTramite;
    }

    public void setSubOrigenSaldo(CatalogoTO subOrigenSaldo) {
        this.subOrigenSaldo = subOrigenSaldo;
    }

    public CatalogoTO getSubOrigenSaldo() {
        return subOrigenSaldo;
    }

    public void setTipoPeriodo(CatalogoTO tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public CatalogoTO getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setPeriodo(CatalogoTO periodo) {
        this.periodo = periodo;
    }

    public CatalogoTO getPeriodo() {
        return periodo;
    }

    public void setEjercicio(CatalogoTO ejercicio) {
        this.ejercicio = ejercicio;
    }

    public CatalogoTO getEjercicio() {
        return ejercicio;
    }

    public void setSubTramite(CatalogoTO subTramite) {
        this.subTramite = subTramite;
    }

    public CatalogoTO getSubTramite() {
        return subTramite;
    }

    public void setImpuesto(DyccImpuestoDTO impuesto) {
        this.impuesto = impuesto;
    }

    public DyccImpuestoDTO getImpuesto() {
        return impuesto;
    }

    public void setSaldoFavor(InformacionSaldoFavorTO saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public InformacionSaldoFavorTO getSaldoFavor() {
        return saldoFavor;
    }

    public void setInfoDeclarativa(InformacionDeclarativaDTO infoDeclarativa) {
        this.infoDeclarativa = infoDeclarativa;
    }

    public InformacionDeclarativaDTO getInfoDeclarativa() {
        return infoDeclarativa;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setInstitucionFinanciera(DyctCuentaBancoDTO institucionFinanciera) {
        this.institucionFinanciera = institucionFinanciera;
    }

    public DyctCuentaBancoDTO getInstitucionFinanciera() {
        return institucionFinanciera;
    }

    public void setRolesContribuyente(RolesContribuyenteDTO rolesContribuyente) {
        this.rolesContribuyente = rolesContribuyente;
    }

    public RolesContribuyenteDTO getRolesContribuyente() {
        return rolesContribuyente;
    }

    public void setInconsistencias(List<CatalogoTO> inconsistencias) {
        this.inconsistencias = inconsistencias;
    }

    public List<CatalogoTO> getInconsistencias() {
        return inconsistencias;
    }

    public void setAnexos(List<ArchivoVO> anexos) {
        this.anexos = anexos;
    }

    public List<ArchivoVO> getAnexos() {
        return anexos;
    }

    public void setDocumentos(List<ArchivoVO> documentos) {
        this.documentos = documentos;
    }

    public List<ArchivoVO> getDocumentos() {
        return documentos;
    }

    public void setConcepto(DyccConceptoDTO concepto) {
        this.concepto = concepto;
    }

    public DyccConceptoDTO getConcepto() {
        return concepto;
    }

    public void setRetenedor(PersonaDTO retenedor) {
        this.retenedor = retenedor;
    }

    public PersonaDTO getRetenedor() {
        return retenedor;
    }

    public void setDiot(DiotDTO diot) {
        this.diot = diot;
    }

    public DiotDTO getDiot() {
        return diot;
    }

    public void setAltex(SpConsultarAltexDTO altex) {
        this.altex = altex;
    }

    public SpConsultarAltexDTO getAltex() {
        return altex;
    }

    public void setActividadEconomica(CatalogoTO actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public CatalogoTO getActividadEconomica() {
        return actividadEconomica;
    }

    public void setSecAgp(CatalogoTO secAgp) {
        this.secAgp = secAgp;
    }

    public CatalogoTO getSecAgp() {
        return secAgp;
    }

    public void setSolTemp(String solTemp) {
        this.solTemp = solTemp;
    }

    public String getSolTemp() {
        return solTemp;
    }

    public void setSaldoIcep(Integer saldoIcep) {
        this.saldoIcep = saldoIcep;
    }

    public Integer getSaldoIcep() {
        return saldoIcep;
    }

    public void setEstadoActual(Integer estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Integer getEstadoActual() {
        return estadoActual;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public DyctResolucionDTO getResolucionDTO() {
        return resolucionDTO;
    }

    public void setResolucionDTO(DyctResolucionDTO resolucionDTO) {
        this.resolucionDTO = resolucionDTO;
    }

}
