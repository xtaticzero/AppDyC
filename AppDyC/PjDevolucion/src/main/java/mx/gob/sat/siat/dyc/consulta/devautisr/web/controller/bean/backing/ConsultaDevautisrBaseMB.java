/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.web.controller.bean.backing;

import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import mx.gob.sat.mat.dyc.integrarexpediente.service.IcepService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.periodovac.PeriodoVacService;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.catalogo.service.DyccConceptoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccImpuestoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInstCreditoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.consulta.devautisr.service.ConsultaWSDevAutISRService;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.service.consultardevolucionescontribuyente.ConsultarDevolucionContribuyenteService;
import mx.gob.sat.siat.dyc.gestionsol.service.emitirresolucion.EmitirResolucionService;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.AdjuntarDocumentoMB;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.EjecutaFielMB;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.SolventarRequerimientoMB;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.impl.ContribuyenteServiceImpl;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.impl.ConsultaPadronSolComponent;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidadorRNRegistro;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.AdicionarSolicitudMB;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.service.DyctCuentaBancoService;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public abstract class ConsultaDevautisrBaseMB implements Serializable {

    private static final long serialVersionUID = 6884981071797745795L;

    /**
     * @see org.apache.log4j.Logger
     */
    private final Logger logger;

    @ManagedProperty("#{consultarDevolucionContribuyenteService}")
    private ConsultarDevolucionContribuyenteService consultarDevolucionContribuyenteService;

    @ManagedProperty(value = "#{dyctCuentaBancoService}")
    private DyctCuentaBancoService dyctCuentaBancoService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{dyctArchivoService}")
    private DyctArchivoService dyctArchivoService;

    @ManagedProperty("#{solventarRequerimientoMB}")
    private SolventarRequerimientoMB solventarRequerimientoMB;

    @ManagedProperty(value = "#{catalogoTipoTramiteService}")
    private CatalogoTipoTramiteService catalogoTipoTramiteService;

    @ManagedProperty("#{adjuntarDocumentoMB}")
    private AdjuntarDocumentoMB adjuntarDocumentoMB;

    @ManagedProperty(value = "#{dyccConceptoService}")
    private DyccConceptoService dyccConceptoService;

    @ManagedProperty(value = "#{dyccPeriodoService}")
    private DyccPeriodoService dyccPeriodoService;

    @ManagedProperty("#{validadorRNRegistro}")
    private ValidadorRNRegistro validadorRNRegistro;

    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService consultarDyccMensajeUsrService;

    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB solDevPage;

    @ManagedProperty(value = "#{dyccImpuestoService}")
    private DyccImpuestoService dyccImpuestoService;

    @ManagedProperty(value = "#{consultaWSDevAutISRService}")
    private ConsultaWSDevAutISRService consultaWSDevAutISRService;

    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;

    @ManagedProperty(value = "#{insertaContribuyenteServiceImpl}")
    private ContribuyenteServiceImpl serviceContte;

    @ManagedProperty(value = "#{ejecutaFIELMB}")
    private EjecutaFielMB ejecutaFielMB;

    @ManagedProperty("#{acuseReciboService}")
    private AcuseReciboService acuseReciboService;

    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService serviceIDC;

    @ManagedProperty("#{icepService}")
    private IcepService icepService;

    @ManagedProperty("#{validadorRNRegistro}")
    private ValidadorRNRegistro validadorRN;

    @ManagedProperty(value = "#{registraSolDevService}")
    private RegistraSolDevService registraSolDevService;

    /**
     * Servicio que nos consulta las instituciones bancarias existentes
     */
    @ManagedProperty(value = "#{dyccInstCreditoService}")
    private DyccInstCreditoService dyccInstCreditoService;

    @ManagedProperty("#{consultaPadronSolComponent}")
    private ConsultaPadronSolComponent consultaPadronesSol;

    @ManagedProperty(value = "#{emitirResolucionService}")
    private EmitirResolucionService emitirResolucionService;

    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB mbAdicionarSolicitud;

    @ManagedProperty("#{periodoVacService}")
    private PeriodoVacService periodoVacService;

    public ConsultaDevautisrBaseMB() {
        logger = Logger.getLogger(getClass());
    }

    public AdicionarSolicitudMB getMbAdicionarSolicitud() {
        return mbAdicionarSolicitud;
    }

    public void setMbAdicionarSolicitud(AdicionarSolicitudMB mbAdicionarSolicitud) {
        this.mbAdicionarSolicitud = mbAdicionarSolicitud;
    }

    public PeriodoVacService getPeriodoVacService() {
        return periodoVacService;
    }

    public void setPeriodoVacService(PeriodoVacService periodoVacService) {
        this.periodoVacService = periodoVacService;
    }

    public ConsultarDevolucionContribuyenteService getConsultarDevolucionContribuyenteService() {
        return consultarDevolucionContribuyenteService;
    }

    public void setConsultarDevolucionContribuyenteService(ConsultarDevolucionContribuyenteService consultarDevolucionContribuyenteService) {
        this.consultarDevolucionContribuyenteService = consultarDevolucionContribuyenteService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public DyctArchivoService getDyctArchivoService() {
        return dyctArchivoService;
    }

    public void setDyctArchivoService(DyctArchivoService dyctArchivoService) {
        this.dyctArchivoService = dyctArchivoService;
    }

    public SolventarRequerimientoMB getSolventarRequerimientoMB() {
        return solventarRequerimientoMB;
    }

    public void setSolventarRequerimientoMB(SolventarRequerimientoMB solventarRequerimientoMB) {
        this.solventarRequerimientoMB = solventarRequerimientoMB;
    }

    public ConsultaWSDevAutISRService getConsultaWSDevAutISRService() {
        return consultaWSDevAutISRService;
    }

    public void setConsultaWSDevAutISRService(ConsultaWSDevAutISRService consultaWSDevAutISRService) {
        this.consultaWSDevAutISRService = consultaWSDevAutISRService;
    }

    public CatalogoTipoTramiteService getCatalogoTipoTramiteService() {
        return catalogoTipoTramiteService;
    }

    public void setCatalogoTipoTramiteService(CatalogoTipoTramiteService catalogoTipoTramiteService) {
        this.catalogoTipoTramiteService = catalogoTipoTramiteService;
    }

    public DyctCuentaBancoService getDyctCuentaBancoService() {
        return dyctCuentaBancoService;
    }

    public void setDyctCuentaBancoService(DyctCuentaBancoService dyctCuentaBancoService) {
        this.dyctCuentaBancoService = dyctCuentaBancoService;
    }

    public ValidadorRNRegistro getValidadorRNRegistro() {
        return validadorRNRegistro;
    }

    public void setValidadorRNRegistro(ValidadorRNRegistro validadorRNRegistro) {
        this.validadorRNRegistro = validadorRNRegistro;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public void setConsultarDyccMensajeUsrService(DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public AdicionarSolicitudMB getSolDevPage() {
        return solDevPage;
    }

    public void setSolDevPage(AdicionarSolicitudMB solDevPage) {
        this.solDevPage = solDevPage;
    }

    public AdjuntarDocumentoMB getAdjuntarDocumentoMB() {
        return adjuntarDocumentoMB;
    }

    public void setAdjuntarDocumentoMB(AdjuntarDocumentoMB adjuntarDocumentoMB) {
        this.adjuntarDocumentoMB = adjuntarDocumentoMB;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public PersonaIDCService getServiceIDC() {
        return serviceIDC;
    }

    public void setServiceIDC(PersonaIDCService serviceIDC) {
        this.serviceIDC = serviceIDC;
    }

    public DyccConceptoService getDyccConceptoService() {
        return dyccConceptoService;
    }

    public void setDyccConceptoService(DyccConceptoService dyccConceptoService) {
        this.dyccConceptoService = dyccConceptoService;
    }

    public ContribuyenteServiceImpl getServiceContte() {
        return serviceContte;
    }

    public void setServiceContte(ContribuyenteServiceImpl serviceContte) {
        this.serviceContte = serviceContte;
    }

    public EjecutaFielMB getEjecutaFielMB() {
        return ejecutaFielMB;
    }

    public void setEjecutaFielMB(EjecutaFielMB ejecutaFielMB) {
        this.ejecutaFielMB = ejecutaFielMB;
    }

    public AcuseReciboService getAcuseReciboService() {
        return acuseReciboService;
    }

    public void setAcuseReciboService(AcuseReciboService acuseReciboService) {
        this.acuseReciboService = acuseReciboService;
    }

    public DyccInstCreditoService getDyccInstCreditoService() {
        return dyccInstCreditoService;
    }

    public void setDyccInstCreditoService(DyccInstCreditoService dyccInstCreditoService) {
        this.dyccInstCreditoService = dyccInstCreditoService;
    }

    public DyccPeriodoService getDyccPeriodoService() {
        return dyccPeriodoService;
    }

    public void setDyccPeriodoService(DyccPeriodoService dyccPeriodoService) {
        this.dyccPeriodoService = dyccPeriodoService;
    }

    public DyccImpuestoService getDyccImpuestoService() {
        return dyccImpuestoService;
    }

    public void setDyccImpuestoService(DyccImpuestoService dyccImpuestoService) {
        this.dyccImpuestoService = dyccImpuestoService;
    }

    public IcepService getIcepService() {
        return icepService;
    }

    public void setIcepService(IcepService icepService) {
        this.icepService = icepService;
    }

    public ValidadorRNRegistro getValidadorRN() {
        return validadorRN;
    }

    public void setValidadorRN(ValidadorRNRegistro validadorRN) {
        this.validadorRN = validadorRN;
    }

    public ConsultaPadronSolComponent getConsultaPadronesSol() {
        return consultaPadronesSol;
    }

    public void setConsultaPadronesSol(ConsultaPadronSolComponent consultaPadronesSol) {
        this.consultaPadronesSol = consultaPadronesSol;
    }

    public RegistraSolDevService getRegistraSolDevService() {
        return registraSolDevService;
    }

    public void setRegistraSolDevService(RegistraSolDevService registraSolDevService) {
        this.registraSolDevService = registraSolDevService;
    }

    public EmitirResolucionService getEmitirResolucionService() {
        return emitirResolucionService;
    }

    public void setEmitirResolucionService(EmitirResolucionService emitirResolucionService) {
        this.emitirResolucionService = emitirResolucionService;
    }

    public Logger getLogger() {
        return logger;
    }

}
