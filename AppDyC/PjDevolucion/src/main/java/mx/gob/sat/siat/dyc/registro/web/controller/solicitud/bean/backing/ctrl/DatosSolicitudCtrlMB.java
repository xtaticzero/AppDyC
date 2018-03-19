/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.ctrl;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.catalogo.service.DyccConceptoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccImpuestoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccSubOrigSaldoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoPeriodoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoTramiteService;
import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.AdicionarSolicitudMB;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.DatosContribuyenteMB;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.service.altex.ConsultarContribuyenteAltamenteExportadorService;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoArchivo;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesValContribuyente;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;

import org.springframework.dao.DataAccessException;


/**
 * @author JEFG
 * */
@ManagedBean(name = "datosSolicitudCtrlMB")
@RequestScoped
public class DatosSolicitudCtrlMB {
    private static final Logger LOGGER = Logger.getLogger(DatosSolicitudCtrlMB.class);

    /** SERVICES DyC */
    @ManagedProperty("#{consultarContribuyenteAltamenteExportadorService}")
    private ConsultarContribuyenteAltamenteExportadorService datosAltexService;
    @ManagedProperty(value = "#{registraSolDevService}")
    private RegistraSolDevService registraSolDevService;
    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService dyccMensajeUsrService;
    @ManagedProperty(value = "#{dyccTipoTramiteService}")
    private DyccTipoTramiteService dyccTipoTramiteService;
    @ManagedProperty(value = "#{dyccSubOrigSaldoService}")
    private DyccSubOrigSaldoService dyccSubOrigSaldoService;
    @ManagedProperty(value = "#{dyccConceptoService}")
    private DyccConceptoService dyccConceptoService;
    @ManagedProperty(value = "#{dyccImpuestoService}")
    private DyccImpuestoService dyccImpuestoService;
    @ManagedProperty("#{dyccTipoPeriodoService}")
    private DyccTipoPeriodoService dyccTipoPeriodoService;
    @ManagedProperty("#{dyccPeriodoService}")
    private DyccPeriodoService dyccPeriodoService;
    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;
    @ManagedProperty("#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;
    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService personaIDCService;
    @ManagedProperty(value = "#{dyccOrigenSaldoService}")
    private DyccOrigenSaldoService dyccOrigenSaldoService;
    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;

    @ManagedProperty(value = "#{validaTramitesService}")
    private ValidaTramitesService validacionTramites;

    /** PAGE MB DyC */
    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB solDevPage;
    @ManagedProperty(value = "#{datosContribuyenteMB}")
    private DatosContribuyenteMB datosContribuyentePage;

    public void addCuentaBanco() {
        try {
            if (findCuentaBanco()) {
                findCunetaCLABE();
            }
        } catch (SIATException e) {
            LOGGER.error(e.getMessage());
            solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
        }
    }

    public boolean findCuentaBanco() throws SIATException {
        String clabe = solDevPage.getFlagsSolicitud().getCuentaClabe();
        if (null == clabe) {
            JSFUtils.messageGlobal("INGRESE UNA CUENTA CLABE", FacesMessage.SEVERITY_ERROR);
        } else if (solDevPage.getValidadorRN().rn470(clabe)) {
            validaCtaEnLista(clabe);
            return Boolean.TRUE;
        } else {
            message(ConstantesIds.MSG_37, FacesMessage.SEVERITY_ERROR);
            solDevPage.getFlagsSolicitud().setCuentaClabe(ConstantesDyC2.EMPTY_STRING);
        }
        return false;
    }

    private void validaCtaEnLista(String clabe) {
        for (int i = 0; i < solDevPage.getListInstCredito().size(); i++) {
            if (solDevPage.getListInstCredito().get(i).getClabe().equals(clabe)) {
                solDevPage.getFlagsSolicitud().setCuentaClabe(ConstantesDyC2.EMPTY_STRING);
                solDevPage.getTramite().setInstitucionFinanciera(solDevPage.getListInstCredito().get(i));
                return;
            }
        }
        DyctCuentaBancoDTO addCuentaBanco = new DyctCuentaBancoDTO();
        DyccInstCreditoDTO instCredito = new DyccInstCreditoDTO();
        addCuentaBanco.setClabe(clabe);
        instCredito.setIdInstCredito(solDevPage.getValidadorRN().getIdBancario());
        instCredito.setDescripcion(solDevPage.getValidadorRN().getDescripcion());
        instCredito.setDyctCuentabancoList(Collections.singletonList(addCuentaBanco));
        addCuentaBanco.setDyccInstCreditoDTO(instCredito);
        solDevPage.getListInstCredito().add(addCuentaBanco);
        solDevPage.getFlagsSolicitud().setCuentaClabe(ConstantesDyC2.EMPTY_STRING);
        solDevPage.getTramite().setInstitucionFinanciera(addCuentaBanco);

    }

    private void message(int idMensaje, Severity severity) throws SIATException {
        DyccMensajeUsrDTO message = null;
        message = dyccMensajeUsrService.obtieneMensaje(idMensaje, ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
        JSFUtils.messageGlobal(message.getDescripcion(), severity);
    }

    public void findCunetaCLABE() {
        String cuentaClabe = solDevPage.getTramite().getInstitucionFinanciera().getClabe();
        String rfc = solDevPage.getTramite().getPersona().getRfcVigente();
        CatalogoTO inconsistencia7 = new CatalogoTO();
        inconsistencia7.setDescripcion(ConstantesValContribuyente.MESSAGE_INCONSISTENCIA_CLABE);
        inconsistencia7.setIdNum(ConstantesDyCNumerico.VALOR_7);

        CatalogoTO inconsistencia4 = new CatalogoTO();
        inconsistencia4.setDescripcion(ConstantesValContribuyente.EDO_CONTRIBUYENTE_INACTIVO);
        inconsistencia4.setIdNum(ConstantesDyCNumerico.VALOR_4);

        if (!solDevPage.getValidadorRN().validaClabeRFC(cuentaClabe, rfc)) {
            addInconsistencias(inconsistencia7);
            addInconsistencias(inconsistencia4);
            solDevPage.getFlagsSolicitud().setMessageSol(getMessage(ConstantesIds.MSG_38));
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.SHOWINSISTENCIACLABE);
        } else {
            solDevPage.getFlagsSolicitud().setMessageGlobal(getMessage(ConstantesIds.MSG_48));
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.MESSAGEEDOCTA);
            solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.FALSE);
            removeInconsistencias(inconsistencia7);
        }
        solDevPage.getDataModel().setWrappedData(solDevPage.getListInstCredito());
        solDevPage.getTramite().getInstitucionFinanciera().setDyctArchivoDTO(null);
    }

    private void addInconsistencias(CatalogoTO inconsistenca) {
        for (CatalogoTO add : solDevPage.getTramite().getInconsistencias()) {
            if (add.getIdNum() == inconsistenca.getIdNum()) {
                return;
            }
        }
        solDevPage.getTramite().getInconsistencias().add(inconsistenca);
    }

    private void removeInconsistencias(CatalogoTO inconsistenca) {
        for (CatalogoTO add : solDevPage.getTramite().getInconsistencias()) {
            if (add.getIdNum() == inconsistenca.getIdNum()) {
                solDevPage.getTramite().getInconsistencias().remove(add);
                return;
            }
        }
    }


    public void consultaDatosAltex() throws SIATException {
        if (solDevPage.getTramite().getInfoDeclarativa().getAltexNumConstancia() != null) {
            int numConstancia = solDevPage.getTramite().getInfoDeclarativa().getAltexNumConstancia();
            String[] fechaVigente = null;
            Date fechaCapturada = new Date();
            Date fechaConsulta = new Date();
            DateFormat fechacapturada = new SimpleDateFormat(ConstantesTipoArchivo.YYYYMM);
            if (0 != numConstancia) {
                String rfc = solDevPage.getTramite().getPersona().getRfcVigente(), val = null;
                SpConsultarAltexDTO constancia = new SpConsultarAltexDTO();
                constancia.setTxtnumalt(numConstancia);
                constancia.setTxtrfc(rfc);
                constancia.setTxtusr(ConstantesDyC.USR_STORED_PROCEDURES);
                constancia = datosAltexService.obtieneDatosAltexSP(constancia);
                if (null == constancia.getVdVigencia()) {
                    solDevPage.getTramite().getInfoDeclarativa().setAltexNumConstancia(null);
                    showMessages(ConstantesIds.MSG_16, FacesMessage.SEVERITY_ERROR);
                } else {
                    fechaVigente = constancia.getVdVigencia().split("-");
                    val = fechaVigente[1].trim();
                    if (!ConstantesDyC2.EMPTY_STRING.equals(val)) {
                        try {
                            fechaCapturada = fechacapturada.parse(val);
                        } catch (ParseException e) {
                            LOGGER.error(e.getMessage());
                        }
                        if (fechaCapturada.getTime() > fechaConsulta.getTime()) {
                            solDevPage.getTramite().getInfoDeclarativa().setAltexNumConstancia(null);
                            showMessages(ConstantesIds.MSG_16, FacesMessage.SEVERITY_ERROR);
                            return;
                        }
                    }
                }
                solDevPage.getTramite().setAltex(constancia);
            }
        }
    }

    private boolean tramitesRNFDC3(int origenSaldo) throws SIATException {
        boolean rnfdc3 = Boolean.FALSE;

        LOGGER.info("COMPETENCIA " + solDevPage.getFlagsSolicitud().getCompetencia());

        if (origenSaldo == 2 &&
            solDevPage.getTramite().getRolesContribuyente().getEsContribuyente().equals(ConstanteValidacionRNFDC10.ALSC)) {
            solDevPage.setCatTipoTramite(dyccTipoTramiteService.tramitesPagoDeLoIndebido().getOutputs());
            rnfdc3 = Boolean.TRUE;
        }

        return rnfdc3;
    }

    /**
     * Metodo que obtiene el tipo de Tramite de acuerdo al origen de saldo seleccionado
     */
    public void buscaTipoTramite() {
        LOGGER.info("######### buscaTipoTramite " + solDevPage.getTramite().getOrigenSaldo().getIdNum());
        solDevPage.getCatTipoTramite().clear();
        solDevPage.getCatSubOrigenSaldo().clear();
        solDevPage.getCatActividades().clear();
        solDevPage.getFlagsSolicitud().setFlagOperaciones(Boolean.FALSE);
        int idOrigenSaldo = solDevPage.getTramite().getOrigenSaldo().getIdNum();
        solDevPage.getFlagsSolicitud().setRowSubOrigenSaldo(Boolean.TRUE);
        solDevPage.getFlagsSolicitud().setPresentaDoc(Boolean.FALSE);
        boolean isAgace =
            solDevPage.getTramite().getRolesContribuyente().getEsContribuyente().trim().equals(ConstanteValidacionRNFDC10.AF_AGACE);

        try {
            if (!tramitesRNFDC3(idOrigenSaldo)) {

                solDevPage.setCatTipoTramite(dyccTipoTramiteService.obtieneTipoTramite(idOrigenSaldo,
                                                                                       solDevPage.getFlagsSolicitud().getCompetencia(),
                                                                                       getRoles(),
                                                                                       isAgace).getOutputs());
            }
           
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
        }

    }


    private List<Integer> getRoles() {
        List<Integer> roles = new ArrayList<Integer>();
        if (solDevPage.getTramite().getRolesContribuyente().isPersonaFisica()) {
            roles.add(0, (int)ConstantesTipoRol.PERSONA_FISICA);
        } else if (solDevPage.getTramite().getRolesContribuyente().isPersonaMoral()) {
            roles.add(0, (int)ConstantesTipoRol.PERSONA_MORAL);
        }

        if (solDevPage.getTramite().getRolesContribuyente().isGranContribuyente() &&
            solDevPage.getTramite().getRolesContribuyente().isSociedadControladora()) {
            roles.add(1, ConstantesIds.SOC_MERCAN_CONTROL);
        } else if (solDevPage.getTramite().getRolesContribuyente().isGranContribuyente() &&
                   solDevPage.getTramite().getRolesContribuyente().isIntegradoraPM()) {
            roles.add(1, ConstantesIds.INTEGRADORA_PM);
        } else {
            roles.add(1, ConstantesDyC1.CERO);
        }
        return roles;
    }

    /**
     * Metodo que se encarga de llenar el campo suborigen del saldo de acuerdo al tipo de Tramite
     */
    public void buscaSubOrigenSaldo() throws SIATException {
        LOGGER.info("### buscaSubOrigenSaldo");
        CatalogoTO catalogo = null;
        List<DyccSubOrigSaldoDTO> subOrigenSaldo = null;
        Integer idTipoTramite = solDevPage.getTramite().getTipoTramite().getIdNum();
        if (!ValidaDatosSolicitud.validaPeriodoIEPSDisel(idTipoTramite)) {
            finalizarSolicitud(ConstantesDyCNumerico.VALOR_70);
            solDevPage.getTramite().setTipoTramite(null);
            return;
        }
        solDevPage.initFlags();
        getTramitesVal(idTipoTramite);
        solDevPage.getCatSubOrigenSaldo().clear();
        solDevPage.getCatTipoPeriodo().clear();
        solDevPage.getCatPeriodo().clear();
        try {
            subOrigenSaldo = dyccSubOrigSaldoService.obtieneSubOrigSaldo(idTipoTramite);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (subOrigenSaldo.isEmpty()) {
            solDevPage.getFlagsSolicitud().setRowSubOrigenSaldo(Boolean.FALSE);
            solDevPage.getTramite().setSubOrigenSaldo(null);
        } else if (subOrigenSaldo.size() == ConstantesDyCNumerico.VALOR_1) {
            catalogo = new CatalogoTO();
            catalogo.setIdNum(subOrigenSaldo.get(ConstantesDyC1.CERO).getIdSuborigenSaldo());
            catalogo.setDescripcion(subOrigenSaldo.get(ConstantesDyC1.CERO).getDescripcion());
            solDevPage.getTramite().setSubOrigenSaldo(catalogo);
            solDevPage.getCatSubOrigenSaldo().add(catalogo);
        } else {
            getCatalogoSubOrigen(subOrigenSaldo);
            solDevPage.getFlagsSolicitud().setRowSubOrigenSaldo(Boolean.TRUE);
        }
        presentaRetenedor(idTipoTramite);
        solDevPage.getTramite().getTipoTramite().setDescripcion(null);
        buscarConceptoEImpuesto();
    }

    private void presentaRetenedor(int idTipoTramite) {
        /** RFCs: retenedor, controladora.*/
        switch (idTipoTramite) {
        case ConstantesIds.TIPO_TRAMITE_NO334:
            solDevPage.getTramite().setRetenedor(new PersonaDTO());
            solDevPage.getFlagsSolicitud().setVerRFC(Boolean.TRUE);
            break;
        case ConstantesIds.TIPO_TRAMITE_NO382:
        case ConstantesIds.TIPO_TRAMITE_NO489:
            solDevPage.getFlagsSolicitud().setVerRFCControlador(Boolean.TRUE);
            break;
        default:
            solDevPage.getFlagsSolicitud().setVerRFC(Boolean.FALSE);
            solDevPage.getFlagsSolicitud().setVerRFCControlador(Boolean.FALSE);
            break;
        }

    }


    /**
     * Metodo que se encarga de llenar los combos de
     * concepto, impuesto y tipoPeriodo de acuerdo al tipo de tramite seleccionado
     */
    public void buscarConceptoEImpuesto() throws SIATException {
        LOGGER.info("### buscarConceptoEImpuesto");
        List<DyccTipoPeriodoDTO> tipoPeriodo = null;
        CatalogoTO catalogo = null;
        int idTipoTramite = ConstantesDyC1.CERO;
        int i = ConstantesDyC1.CERO;
        if (null != solDevPage.getTramite().getTipoTramite()) {
            idTipoTramite = solDevPage.getTramite().getTipoTramite().getIdNum();
            try {
                solDevPage.getTramite().setConcepto(dyccConceptoService.obtieneConceptoPorIdTipoTramite(idTipoTramite));
                solDevPage.getTramite().setImpuesto(dyccImpuestoService.obtieneImpuestoPorIdTramite(solDevPage.getTramite().getConcepto().getDyccImpuestoDTO().getIdImpuesto()));
                tipoPeriodo = dyccTipoPeriodoService.tipoPeriodo(idTipoTramite).getOutputs();
            } catch (DataAccessException e) {
                throw new SIATException(e);
            }
            LOGGER.info("####CONCEPTO" + solDevPage.getTramite().getConcepto().getIdConcepto());
            LOGGER.info("####IMPUESTO" + solDevPage.getTramite().getImpuesto().getIdImpuesto());
            for (DyccTipoPeriodoDTO item : tipoPeriodo) {
                catalogo = new CatalogoTO();
                catalogo.setIdNum(i++);
                catalogo.setIdString(item.getIdTipoPeriodo());
                catalogo.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() :
                                        ConstantesDyC2.EMPTY_STRING);
                solDevPage.getCatTipoPeriodo().add(catalogo);
            }
        }
    }

    public void agragarRFCControlador() {
        String res = solDevPage.getFlagsSolicitud().getRfcTerceros();
        LOGGER.info("agragarRFCControlador " + res);
        if (null == res || res.equals("")) {
            JSFUtils.messageByIDComponent("rfcControladora", FacesMessage.SEVERITY_INFO, "Ingrese un rfc valido");
            RequestContext.getCurrentInstance().update("rfcControladora");
            return;
        }
        try {
            res = registraSolDevService.validaRFCControlador(solDevPage.getFlagsSolicitud().getRfcTerceros());
        } catch (SIATException e) {
            JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            solDevPage.getFlagsSolicitud().setRfcTerceros(null);
            return;
        }
        if (res.equals("existe") &&
            !solDevPage.getTramite().getRfcControladora().contains(solDevPage.getFlagsSolicitud().getRfcTerceros())) {
            solDevPage.getTramite().getRfcControladora().add(solDevPage.getFlagsSolicitud().getRfcTerceros());
            solDevPage.getFlagsSolicitud().setRfcTerceros(null);
        }
    }


    public void getCatalogoActividades() throws SIATException {
        if (null != solDevPage.getTramite().getSubOrigenSaldo()) {
            int id = solDevPage.getTramite().getSubOrigenSaldo().getIdNum();
            solDevPage.setCatActividades(dyccSubOrigSaldoService.getCatActividades(id).getOutputs());
            if (solDevPage.getCatActividades().isEmpty()) {
                solDevPage.getFlagsSolicitud().setRowActividad(Boolean.FALSE);
            } else {
                solDevPage.getFlagsSolicitud().setRowActividad(Boolean.TRUE);
            }
        }
        /**   buscarConceptoEImpuesto(); */
    }

    private void getCatalogoSubOrigen(List<DyccSubOrigSaldoDTO> subOrigenSaldo) {
        CatalogoTO catalogo = null;
        CatalogoTO otroSubOrigen = null;
        for (DyccSubOrigSaldoDTO item : subOrigenSaldo) {
            if (item.getIdSuborigenSaldo().equals(ConstantesIds.MSG_15)) {
                otroSubOrigen = new CatalogoTO();
                otroSubOrigen.setIdNum(item.getIdSuborigenSaldo());
                otroSubOrigen.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() :
                                             ConstantesDyC2.EMPTY_STRING);
            } else {
                catalogo = new CatalogoTO();
                catalogo.setIdNum(item.getIdSuborigenSaldo());
                catalogo.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() :
                                        ConstantesDyC2.EMPTY_STRING);
                solDevPage.getCatSubOrigenSaldo().add(catalogo);
            }
        }
        if (null != otroSubOrigen) {
            solDevPage.getCatSubOrigenSaldo().add(otroSubOrigen);
        }
    }

 /**   public void declaracionDIOT() {
        String flag = solDevPage.getFlagsSolicitud().getFlagDeclaracion();
        if (flag.equals(ValidaDatosSolicitud.DATOSINCORRECTOS)) {
            solDevPage.getFlagsSolicitud().setMessageGlobal(getMessage(ConstantesDyCNumerico.VALOR_12));
            solDevPage.getFlagsSolicitud().setEndSolicitud(Boolean.TRUE);
            RequestContext.getCurrentInstance().update("form:messageEndSolicitud");
            RequestContext.getCurrentInstance().execute("endSolicitud.show();");
        }
    }
*/

    private void getTramitesVal(int tramite) throws SIATException {
        solDevPage.setTramitesValidacion(validacionTramites.getTramitesValidacion(tramite).getOutput());
    }

    public String recuperarSolicitudTemp() {
        LOGGER.info("INIT TEMP " + datosContribuyentePage.getSolDevRegistro());
        if (null == datosContribuyentePage.getSolDevRegistro()) {
            JSFUtils.messageGlobal("Seleccione un registro", FacesMessage.SEVERITY_INFO);
            return null;
        }
        try {
            solDevPage.recuperarSolTemp(dycpSolicitudService.completarSolicitudTemp(datosContribuyentePage.getSolDevRegistro()).getOutput());
            getTramitesVal(solDevPage.getTramite().getTipoTramite().getIdNum());
            presentaDocAdjuntos();
            /**FlagTemp se utiliza para la navegacion sin afectar flujo!..   */
            solDevPage.getTramite().setOrigenSaldo(getDyccOrigenSaldoService().getOrigenSaldo(solDevPage.getTramite().getOrigenSaldo().getIdNum()).getOutput());
            getInformacion();
            datosContribuyentePage.setContribuyente(solDevPage.getTramite().getPersona());
            solDevPage.recuperaInformacionSaldo();
            datosContribuyentePage.setSonDatos("1");
            viewMessage();
        } catch (Exception e) {
            LOGGER.error(e.getCause());
            solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute("dlgSolError.show();");
        }
        /**TEMPORAL MENSAJE  adicionarSolicitudPage.setSolTempConfirm(Boolean.TRUE); */
        /**  RequestContext.getCurrentInstance().execute("confirmation.show();"); */
        return ValidaDatosSolicitud.PAGE_SOLICITUD_TEMP;
    }

    private void viewMessage() {
        int num = solDevPage.getMessageArchivosSolTemp().size();
        if (num > 0) {
            JSFUtils.getFacesContext().addMessage(null,
                                                  new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Verifica que los siguientes documentos estén adjuntos a la Solicitud de Devolución!..",
                                                                   null));
            for (int i = 0; i < num; i++) {
                JSFUtils.getFacesContext().addMessage(null,
                                                      new FacesMessage(FacesMessage.SEVERITY_INFO, " * " + solDevPage.getMessageArchivosSolTemp().get(i),
                                                                       null));
            }
            RequestContext.getCurrentInstance().update("form:growl");
        }
    }

    private void presentaDocAdjuntos() throws SIATException {
        int tramite = solDevPage.getTramite().getTipoTramite().getIdNum();
        int competencia = solDevPage.getFlagsSolicitud().getCompetencia();
        if (ValidaDatosSolicitud.isCompetenciaAGGC(competencia,
                                                   validacionTramites.validaTramite(ConstantesDyCNumerico.VALOR_30,
                                                                                    tramite)) ||
            ValidaDatosSolicitud.isCompetenciaAGAFF(competencia,
                                                    validacionTramites.validaTramite(ConstantesDyCNumerico.VALOR_31,
                                                                                     tramite),
                                                    solDevPage.getTramite().getRolesContribuyente().isDictaminado())) {
            solDevPage.getFlagsSolicitud().setPresentaDoc(Boolean.TRUE);
            addMessageReq(getMessage(ConstantesDyCNumerico.VALOR_34));
        }
        documentosComplementarios(competencia, tramite);
    }

    private void documentosComplementarios(int competencia, int tramite) {
        if (tramite == ConstantesIds.IVA_NUEVAS_INVERCIONES) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_60);
        } else if (tramite == ConstantesIds.IVA_MEDICINA) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_62);
        } else if (tramite == ConstantesIds.IVA_PRODUCTOS_ALIMENTACION) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_63);
        } else if (tramite == ConstantesIds.IVA_EXPORTACION) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_69);
        } else if (tramite == ConstantesIds.ISR_PERSONAS_FISICAS && competencia == ConstantesTipoRol.AGAFF) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_7);
        }
    }

    private void presentaDocumento(int idMessage) {
        int numDoc =
            null != solDevPage.getFlagsSolicitud().getNumDocAdjuntos() ? ConstantesDyCNumerico.VALOR_2 : ConstantesDyCNumerico.VALOR_1;
        solDevPage.getFlagsSolicitud().setPresentaDoc(Boolean.TRUE);
        solDevPage.getFlagsSolicitud().setNumDocAdjuntos(numDoc);
        addMessageReq(getMessage(idMessage));
    }


    /** Revisar
     * private void presentaDocAdjuntos() throws SIATException {
        int tramite = solDevPage.getTramite().getTipoTramite().getIdNum();
        int competencia =
            ValidaDatosSolicitud.presentarDocumentos(solDevPage.getTramite().getRolesContribuyente().isDictaminado(),
                                                     solDevPage.getFlagsSolicitud().getCompetencia(), tramite);
        if ((competencia == ConstantesDyCNumerico.VALOR_31) &&
            getValidacionTramites().validaTramite(competencia, tramite)) {
            solDevPage.getFlagsSolicitud().setNumDocAdjuntos(1);
            addMessageReq(messageSolicitud.getMessage(ConstantesDyCNumerico.VALOR_7, 1));
        } else if ((competencia == ConstantesDyCNumerico.VALOR_30) &&
                   getValidacionTramites().validaTramite(competencia, tramite)) {
            solDevPage.getFlagsSolicitud().setNumDocAdjuntos(1);
            addMessageReq(messageSolicitud.getMessage(ConstantesDyCNumerico.VALOR_34, 1));
            if (tramite == ConstantesDyCNumerico.VALOR_133) {
                solDevPage.getFlagsSolicitud().setNumDocAdjuntos(2);
                addMessageReq(messageSolicitud.getMessage(ConstantesDyCNumerico.VALOR_60, 1));
            }
        }
    } */

    private void addMessageReq(String messageReq) {
        if (null != messageReq) {
            solDevPage.getMessageArchivosSolTemp().add(messageReq);
        }
    }


    private void getInformacion() throws SIATException {
        DyccConceptoDTO concepto = null;
        concepto = getDyccConceptoService().obtieneIdConcepto(solDevPage.getTramite().getConcepto().getIdConcepto());
        solDevPage.getTramite().setConcepto(concepto);
        solDevPage.getTramite().setImpuesto(dyccImpuestoService.impuestoXConcepto(concepto));
        getTramite();
        recuperaTipoPeriodo();
    }

    private void getTramite() throws SIATException {
        Integer idTipoTramite = solDevPage.getTramite().getTipoTramite().getIdNum();
        DyccTipoTramiteDTO tramite = dyccTipoTramiteService.getTramite(idTipoTramite).getOutput();
        solDevPage.getTramite().setTipoTramite(new CatalogoTO(tramite.getIdTipoTramite(),
                                                              solDevPage.getFlagsSolicitud().getProtesta(),
                                                              tramite.getDescripcion()));
    }

    private void recuperaTipoPeriodo() throws SIATException {
        DyccPeriodoDTO periodo = new DyccPeriodoDTO();
        Integer id = solDevPage.getTramite().getTipoPeriodo().getIdNum();
        periodo.setIdPeriodo(id);
        CatalogoTO catTipoPeriodo = null;
        CatalogoTO catPeriodo = new CatalogoTO();
        periodo = dyccPeriodoService.obtienePeriodoPorIdPeriodo(periodo);
        catTipoPeriodo = getDyccTipoPeriodoService().findTipoPeriodo(id).getOutput();
        catPeriodo.setIdNum(periodo.getIdPeriodo());
        catPeriodo.setDescripcion(periodo.getDescripcion());
        catPeriodo.setIdString(periodo.getPeriodoInicioFin());
        solDevPage.getTramite().setTipoPeriodo(catTipoPeriodo);
        solDevPage.getTramite().setPeriodo(catPeriodo);
    }


    public void complementaIVASecAgp() {
        solDevPage.getFlagsSolicitud().setResSecAgp(Boolean.TRUE);
        solDevPage.getTramite().getSecAgp().setDescripcion("1");
    }

    public void noComplementaIVASecAgp() {
        solDevPage.getTramite().setSecAgp(null);
        solDevPage.getFlagsSolicitud().setFlagSecAgp(Boolean.FALSE);
        solDevPage.getFlagsSolicitud().setResSecAgp(false);
        solDevPage.elimarAnexoSecAgp();
    }

    public void viewInfoSecAgp() {
        solDevPage.getFlagsSolicitud().setMessageSecAgp(getMessage(ConstantesDyCNumerico.VALOR_55));
        RequestContext.getCurrentInstance().update("form:infoAgro");
        RequestContext.getCurrentInstance().execute("infoAgropecuario.show();");
    }

    public void negarEdoCta() {
        solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.TRUE);
        solDevPage.getFlagsSolicitud().setFlgEdoCta(false);
        solDevPage.getTramite().getInstitucionFinanciera().setCuentaValida(ConstantesDyCNumerico.VALOR_0);
        solDevPage.getFlagsSolicitud().setMessageSol(null);
    }

    public void confirmaEdoCta() {
        solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.TRUE);
        solDevPage.getFlagsSolicitud().setFlgEdoCta(false);
        solDevPage.getTramite().getInstitucionFinanciera().setCuentaValida(ConstantesDyCNumerico.VALOR_1);
        solDevPage.getFlagsSolicitud().setMessageSol(null);
    }

    public void confirmarNoProtesta() {
        /** adicionarSolicitudPage.getTramite().getTipoTramite().setDescripcion("0");
        solicitudUtils.setMessageGlobal(solicitudUtils.getMessage(52));
        RequestContext.getCurrentInstance().execute(DIALOGPROTESTA); */
        solDevPage.getFlagsSolicitud().setProtesta(null);
    }


    public void validaResProtesta() {
        if (solDevPage.getFlagsSolicitud().getProtesta().equals("Si")) {
            solDevPage.getTramite().getTipoTramite().setDescripcion("1");
        } else {
            solDevPage.getTramite().getTipoTramite().setDescripcion(null);
            solDevPage.getTramite().getTipoTramite().setDescripcion("0");
            solDevPage.getFlagsSolicitud().setMessageGlobal(getMessage(ConstantesDyCNumerico.VALOR_52));
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.DIALOGPROTESTA);
        }
    }

    public void continuarBajoProtesta() {
        solDevPage.getTramite().getTipoTramite().setDescripcion("0");
    }

    public void confirmarSolicitud() {
        if (datosContribuyentePage.getSonDatos().equals(String.valueOf(ConstantesDyCNumerico.VALOR_2))) {
            solDevPage.getFlagsSolicitud().setMessageSol(getMessage(ConstantesDyCNumerico.VALOR_21));
            RequestContext.getCurrentInstance().execute("dlgInst.show();");
        }
    }

    public void modificarSolicitud() {
        solDevPage.getFlagsSolicitud().setMessageSol(null);
        datosContribuyentePage.setSonDatos("1");
        solDevPage.getFlagsSolicitud().setStep("T1");
        /**  adicionarSolicitudPage.getTramite().setInstitucionFinanciera(null);
        adicionarSolicitudPage.getTramite().getInconsistencias().clear(); */
        /**segirAInsistencia();*/
    }

    private String getMessage(int idMensaje) {
        return messageSolicitud.getMessage(idMensaje, ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
    }

    private void showMessages(int idMensaje, Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }

    public String adicionarSolictud() {
        datosContribuyentePage.setSolDevRegistro(null);
        datosContribuyentePage.setSonDatos("1");
        try {
            datosContribuyentePage.getRolesContribuyente().setRoles(personaIDCService.buscaRolesPorBoId(datosContribuyentePage.getContribuyente()));
            datosContribuyentePage.init();
        } catch (SIATException e) {
            LOGGER.error(e.getMessage());
            solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
        }
        return ValidaDatosSolicitud.PAGE_CONTRIBUYENTE;
    }

    public void showAdjuntarEdoCta() {
        LOGGER.info("Show EdoCta");
        solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.FALSE);
    }

    public String cancelarSolicitud() {
        return datosContribuyentePage.isIsSolTem() ? ValidaDatosSolicitud.PAGE_SOLICITUDES_DEVOLUCION :
               ValidaDatosSolicitud.PAGE_CONTRIBUYENTE;
    }
    
    public String cancelarSolicitudConsulta() {
        
        
        return "regresarConsultarDevCont";
    }
    
    
    public void createSolicitudTemporal() {
        try {
            dycpSolicitudService.insertaSolicitudTemporal(solDevPage.getTramite());
        } catch (SIATException e) {
            LOGGER.error("El registro de la solictud temporal no se completo: " + e.getMessage());
            solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
            return;
        }
        datosContribuyentePage.setIsSolTem(Boolean.TRUE);
        solDevPage.getFlagsSolicitud().setMessageSol(getMessage(ConstantesDyCNumerico.VALOR_19));
        RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.EXIT);
    }

    private void finalizarSolicitud(int idMessage) {
        solDevPage.getFlagsSolicitud().setMessageGlobal(getMessage(idMessage));
        solDevPage.getFlagsSolicitud().setEndSolicitud(Boolean.TRUE);
        RequestContext.getCurrentInstance().update("form:messageEndSolicitud");
        RequestContext.getCurrentInstance().execute("endSolicitud.show();");
    }

    public void limpiarSolicitud() {
        solDevPage.getFlagsSolicitud().setMessageSol(getMessage(ConstantesDyCNumerico.VALOR_20));
        RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.EXIT);
    }

    public void setSolDevPage(AdicionarSolicitudMB adicionarSolicitudPage) {
        this.solDevPage = adicionarSolicitudPage;
    }

    public AdicionarSolicitudMB getSolDevPage() {
        return solDevPage;
    }

    public void setDatosAltexService(ConsultarContribuyenteAltamenteExportadorService datosAltexService) {
        this.datosAltexService = datosAltexService;
    }

    public ConsultarContribuyenteAltamenteExportadorService getDatosAltexService() {
        return datosAltexService;
    }

    public void setRegistraSolDevService(RegistraSolDevService registraSolDevService) {
        this.registraSolDevService = registraSolDevService;
    }

    public RegistraSolDevService getRegistraSolDevService() {
        return registraSolDevService;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setDyccTipoTramiteService(DyccTipoTramiteService dyccTipoTramiteService) {
        this.dyccTipoTramiteService = dyccTipoTramiteService;
    }

    public DyccTipoTramiteService getDyccTipoTramiteService() {
        return dyccTipoTramiteService;
    }

    public void setDyccSubOrigSaldoService(DyccSubOrigSaldoService dyccSubOrigSaldoService) {
        this.dyccSubOrigSaldoService = dyccSubOrigSaldoService;
    }

    public DyccSubOrigSaldoService getDyccSubOrigSaldoService() {
        return dyccSubOrigSaldoService;
    }

    public void setDyccConceptoService(DyccConceptoService dyccConceptoService) {
        this.dyccConceptoService = dyccConceptoService;
    }

    public DyccConceptoService getDyccConceptoService() {
        return dyccConceptoService;
    }

    public void setDyccImpuestoService(DyccImpuestoService dyccImpuestoService) {
        this.dyccImpuestoService = dyccImpuestoService;
    }

    public DyccImpuestoService getDyccImpuestoService() {
        return dyccImpuestoService;
    }

    public void setDyccTipoPeriodoService(DyccTipoPeriodoService dyccTipoPeriodoService) {
        this.dyccTipoPeriodoService = dyccTipoPeriodoService;
    }

    public DyccTipoPeriodoService getDyccTipoPeriodoService() {
        return dyccTipoPeriodoService;
    }

    public void setDyccPeriodoService(DyccPeriodoService dyccPeriodoService) {
        this.dyccPeriodoService = dyccPeriodoService;
    }

    public DyccPeriodoService getDyccPeriodoService() {
        return dyccPeriodoService;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setDatosContribuyentePage(DatosContribuyenteMB datosContribuyentePage) {
        this.datosContribuyentePage = datosContribuyentePage;
    }

    public DatosContribuyenteMB getDatosContribuyentePage() {
        return datosContribuyentePage;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
    }

    public PersonaIDCService getPersonaIDCService() {
        return personaIDCService;
    }

    public void setDyccOrigenSaldoService(DyccOrigenSaldoService dyccOrigenSaldoService) {
        this.dyccOrigenSaldoService = dyccOrigenSaldoService;
    }

    public DyccOrigenSaldoService getDyccOrigenSaldoService() {
        return dyccOrigenSaldoService;
    }

    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setValidacionTramites(ValidaTramitesService validacionTramites) {
        this.validacionTramites = validacionTramites;
    }

    public ValidaTramitesService getValidacionTramites() {
        return validacionTramites;
    }
}
