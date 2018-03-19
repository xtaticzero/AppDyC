/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.icep.ObtieneIcepDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.AdicionarSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.InformacionDeclarativaDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevTempVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.tramites.TramitesValidacionDTO;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidadorRNRegistro;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteAvisoCompensacion;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteCompetenciaAGAFF;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionPeriodo;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoArchivo;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.util.constante2.ConstatesMsgDictaminadores;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author Paola Rivera
 *
 */
@ManagedBean(name = "adicionarSolicitudMB")
@SessionScoped
public class AdicionarSolicitudMB extends AbstractPage {

    private static final Logger LOG = Logger.getLogger(AdicionarSolicitudMB.class);
    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;
    @ManagedProperty("#{validadorRNRegistro}")
    private ValidadorRNRegistro validadorRN;

    private ObtieneIcepDTO icep;
    private TramiteDTO tramite;
    private AdicionarSolicitudDTO flagsSolicitud;
    private StreamedContent file;
    private UploadedFile fileEdoCta;
    private UploadedFile fileDoc;
    private UploadedFile fileAnexo;
    private ArchivoVO anexo;
    private ArchivoVO documento;
    private ArchivoVO docEdoCta;
    private StringBuilder carpetaTemp;
    private List<CatalogoTO> catOrigenSaldo;
    private List<CatalogoTO> catTipoTramite;
    private List<CatalogoTO> catSubOrigenSaldo;
    private List<CatalogoTO> catActividades;
    private List<CatalogoTO> catTipoPeriodo;
    private List<CatalogoTO> catPeriodo;
    private List<CatalogoTO> catEjercicio;
    private List<CatalogoTO> subtramite;
    private List<DyccInconsistDTO> inconsistenciasDTO;
    private List<ArchivoVO> documentosAdjuntos;
    private List<String> messageArchivosSolTemp;
    private List<DyctCuentaBancoDTO> listInstCredito;
    private List<ArchivoVO> lisAnexo;
    private TramitesValidacionDTO tramitesValidacion;
    
    private static final int CERO = 0;
    private static final int OCHOCIENTOS = 800;
    private int maxLength = OCHOCIENTOS;

    /** private DatosDevolucionUtilsDTO datosUtils; */


    public AdicionarSolicitudMB() {
        flagsSolicitud = new AdicionarSolicitudDTO();
    }

    public void inicializaVariables() {
        LOG.debug("INICIO inicializaVariables");
        setInconsistenciasDTO(new ArrayList<DyccInconsistDTO>());
        flagsSolicitud.setShowEdoCta(Boolean.TRUE);
        setCatTipoPeriodo(new ArrayList<CatalogoTO>());
        setCatOrigenSaldo(new ArrayList<CatalogoTO>());
        flagsSolicitud.setCompetencia(ConstantesTipoRol.AGAFF);
        tramite.setOrigenSaldo(new CatalogoTO());
        tramite.setTipoTramite(new CatalogoTO());
        tramite.setSubOrigenSaldo(new CatalogoTO());
        tramite.setActividadEconomica(new CatalogoTO());
        tramite.setTipoPeriodo(new CatalogoTO());
        tramite.setPeriodo(new CatalogoTO());
        tramite.setEjercicio(new CatalogoTO());
        tramite.setRfcControladora(new ArrayList<String>());
        tramite.setInfoDeclarativa(new InformacionDeclarativaDTO());
        tramite.setInconsistencias(new ArrayList<CatalogoTO>());
        listInstCredito = new ArrayList<DyctCuentaBancoDTO>();
        setDataModel(new SIATDataModel<Serializable>());
        setDataModelAnexo(new SIATDataModel<Serializable>());
        setDataModelDocumento(new SIATDataModel<Serializable>());
        catTipoTramite = new ArrayList<CatalogoTO>();
        catSubOrigenSaldo = new ArrayList<CatalogoTO>();
        catActividades = new ArrayList<CatalogoTO>();
        catPeriodo = new ArrayList<CatalogoTO>();
        catEjercicio = new ArrayList<CatalogoTO>();
        tramite.setAnexos(new ArrayList<ArchivoVO>());
        tramite.setDocumentos(new ArrayList<ArchivoVO>());
        getDataModelDocumento().setWrappedData(getTramite().getDocumentos());
        setDocumentosAdjuntos(new ArrayList<ArchivoVO>());
        /**RNFDC214 */
        if (getTramite().getRolesContribuyente().isGranContribuyente() ||
            getTramite().getRolesContribuyente().isEstadoExtranjero()) {
            getFlagsSolicitud().setCompetencia(ConstantesTipoRol.AGGC);
        }
        LOG.info("COMPETENCIA  AGGC(13)|AGAFF(16) => " + getFlagsSolicitud().getCompetencia() + " " +
                 tramite.getRolesContribuyente().getEsContribuyente());
    }


    public void getUrlLocalidad() throws SIATException
    {
        carpetaTemp = new StringBuilder();
        Map<String, Object> respValidador = validadorRN.identificarAdministracion(getTramite().getOrigenSaldo().getIdNum(),
                                             getTramite().getRolesContribuyente(),
                                             getTramite().getPersona().getDomicilio().getClaveAdmonLocal(), getTramite().getTipoTramite().getIdNum());

        getTramite().getRolesContribuyente().setClaveLocalidad((String)respValidador.get("claveSirNumControl"));
        getTramite().getRolesContribuyente().setClaveAdmon((Integer)respValidador.get("claveAdministracion"));

        carpetaTemp.append(ConstantesDyCURL.URL_DOCUMENTOS);
        carpetaTemp.append("/");
        carpetaTemp.append((String)respValidador.get("claveSirNumControl"));
        carpetaTemp.append("/");
        carpetaTemp.append(getTramite().getPersona().getRfcVigente());
        carpetaTemp.append("/");
        carpetaTemp.append(ValidaDatosSolicitud.CARPETATEMP + new SimpleDateFormat("ddMMyyHHmmss").format(new Date()));
    }

    public void initFlags() {
        flagsSolicitud.setVerRFC(Boolean.FALSE);
        flagsSolicitud.setVerRFCControlador(Boolean.FALSE);
        flagsSolicitud.setBloqDoctos(Boolean.FALSE);
        flagsSolicitud.setFlagSecAgp(Boolean.FALSE);
        flagsSolicitud.setFlagTemp(Boolean.FALSE);
        flagsSolicitud.setRowActividad(Boolean.FALSE);
        getFlagsSolicitud().setFlagOperaciones(Boolean.FALSE);
        tramite.setRetenedor(null);
        tramite.getAnexos().clear();
        tramite.getDocumentos().clear();
        tramite.getRfcControladora().clear();
        flagsSolicitud.setRfcTerceros(null);
        tramite.setSubOrigenSaldo(null);
        tramite.setActividadEconomica(null);
    }


    /**
     * Metodo para mostrar el tab de Info de Saldo valida primero los datos del contribuyente paso 40
     */
    public void validaInfoSaldo() throws SIATException {
        initFlagSaldo();
        Integer tramiteSaldo = tramite.getTipoTramite().getIdNum();
        rNFDC19(tramiteSaldo);
        flagsSolicitud.setBloqInfoDIOT(tramitesValidacion.isTramitesDIOT());
        if (tramitesValidacion.isTramUltimaDelclaracion()) {
            tramite.getSaldoFavor().setTipoDeclaracion("3");
            flagsSolicitud.setRowFechaCaucion(Boolean.TRUE);
            flagsSolicitud.setRowNumeroOperacion(Boolean.TRUE);
        } else if (tramitesValidacion.isTramConsultaSaldoICEPSP()) {
            flagsSolicitud.setRowFechaPresentacion(Boolean.TRUE);
            flagsSolicitud.setRowTipoDeclaracion(Boolean.TRUE);
            flagsSolicitud.setRowNumeroOperacion(Boolean.TRUE);
            /** Test de prueba consultaICEP(getTramite().getPersona().getRfcVigente()); */
            if (icep.getEstatus().equals(ConstantesAsignarAuDic.VALOR_1_CADENA)) {
                tramite.getSaldoFavor().setSaldoICEP(Boolean.TRUE);
                tramite.getSaldoFavor().setDatosCorrectos("Si");
                datosICEP();
                /** res = importeSaldosDyc(ConstantesDyCNumerico.VALOR_30); */
            } else {
                tramite.getSaldoFavor().setDatosCorrectos("Si");
                RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.SHOW_DIALOGO);
            }
        } else {
            flagsSolicitud.setRowNumeroDocumento(tramitesValidacion.isTramitesNumDocumento());
        }

        tramite.getSaldoFavor().setEtiquetaSaldo(tramitesValidacion.getPresentacionSaldo());
        presentacionSaldoAFavor();
    }


    public void recuperaInformacionSaldo() throws SIATException {
        Integer tramiteSaldo = tramite.getTipoTramite().getIdNum();
        Integer origenSaldo = tramite.getOrigenSaldo().getIdNum();
        String tipoPeriodo = tramite.getTipoPeriodo().getIdString();
        flagsSolicitud.setRowFechaCaucion(Boolean.FALSE);
        flagsSolicitud.setRowTipoDeclaracion(Boolean.FALSE);
        flagsSolicitud.setRowFechaPresentacion(Boolean.FALSE);
        flagsSolicitud.setRowNumeroOperacion(Boolean.FALSE);
        flagsSolicitud.setRowNumeroDocumento(Boolean.FALSE);
        rNFDC19(tramiteSaldo);
        if ((tramitesValidacion.isTramUltimaDelclaracion() && tipoPeriodo.equals(ConstanteValidacionPeriodo.SIN_PERIODO) &&
             origenSaldo.equals(ConstantesDyCNumerico.VALOR_2))) {
            /** tramite.getSaldoFavor().setTipoDeclaracion("1"); */
            flagsSolicitud.setRowFechaCaucion(Boolean.TRUE);
            flagsSolicitud.setRowNumeroOperacion(Boolean.TRUE);
        } else if (tramitesValidacion.isTramConsultaSaldoICEPSP()) {
            flagsSolicitud.setRowFechaPresentacion(Boolean.TRUE);
            flagsSolicitud.setRowTipoDeclaracion(Boolean.TRUE);
            flagsSolicitud.setRowNumeroOperacion(Boolean.TRUE);
        } else {
            flagsSolicitud.setRowNumeroDocumento(tramitesValidacion.isTramitesNumDocumento());
            flagsSolicitud.setImporteSaldosDyC(Boolean.TRUE);
            /**  tramite.getSaldoFavor().setEtiquetaSaldo(ValidaTipoTramite.prsentacionSaldoDeclaracion(tramiteSaldo,
                                                                                                        ConstantesDyC.SIN_DELARACION)); */
        }
        presentacionSaldoAFavor();
    }

    private void datosICEP() throws SIATException {
        Integer tipoDeclaracion = Integer.valueOf(icep.getTipoDeclaracion());
        if (tipoDeclaracion.equals(ConstantesDyCNumerico.VALOR_1) ||
            tipoDeclaracion.equals(ConstantesDyCNumerico.VALOR_41)) {
            tipoDeclaracion = ConstantesDyCNumerico.VALOR_1;
        } else {
            tipoDeclaracion = ConstantesDyCNumerico.VALOR_2;
        }

        String fechaPres = icep.getFechPresentacion().replace(ConstanteValidacionRNFDC10.UTC, ConstantesDyC2.CADENA_VACIA);
        DateFormat dateFormat = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT);
        tramite.getSaldoFavor().setTipoDeclaracion(String.valueOf(ValidaDatosSolicitud.tipoDeclaracion(tipoDeclaracion)));
        LOG.info("FECHA " + fechaPres);
        try {
            tramite.getSaldoFavor().setFechaPresentacion(dateFormat.parse(fechaPres));
            tramite.getSaldoFavor().setNumeroOperacion(icep.getNumOper());
            tramite.getSaldoFavor().setImporteSaldoFavor(new BigDecimal(icep.getSaldoFavor()));
            tramite.getSaldoFavor().setImporteSolicitadoDevolucion(tramite.getSaldoFavor().getImporteSaldoFavor());
        } catch (ParseException e) {
            throw new SIATException(e);
        } catch (NumberFormatException e) {
            throw new SIATException(e);
        }
        flagsSolicitud.setConfirmarICEP(Boolean.TRUE);
        setIcep(null);

    }


    private void presentacionSaldoAFavor() {
        if (tramite.getSaldoFavor().getEtiquetaSaldo().equals(ValidaDatosSolicitud.NO_PRESENTA_SALDO)) {
            flagsSolicitud.setRowSaldoFavor(Boolean.FALSE);
        } else {
            flagsSolicitud.setRowSaldoFavor(Boolean.TRUE);
        }
        if (!flagsSolicitud.isRowSaldoFavor() && !flagsSolicitud.isRowImporteDevComEfectuadas() &&
            !flagsSolicitud.isRowImporteAcreditamiento()) {
            flagsSolicitud.setActivo(Boolean.FALSE);
        }
    }

    public void rNFDC19(int tramite) {
        /**   String importe = ValidaTipoTramite.importeDevCompensaciones(tramite); */
        if (tramitesValidacion.isTramitesRNFDC19()) {
            flagsSolicitud.setRowImporteDevComEfectuadas(Boolean.TRUE);
        } else if ((tramite >= ConstantesDyCNumerico.VALOR_119 && tramite <= ConstantesDyCNumerico.VALOR_122) ||
                   (tramite == ConstantesDyCNumerico.VALOR_344 || tramite == ConstantesDyCNumerico.VALOR_347)) {
            flagsSolicitud.setRowImporteDevComEfectuadas(Boolean.TRUE);
            flagsSolicitud.setRowImporteAcreditamiento(Boolean.TRUE);
        } else {
            flagsSolicitud.setRowImporteDevComEfectuadas(Boolean.FALSE);
            flagsSolicitud.setRowImporteAcreditamiento(Boolean.FALSE);
        }
    }


    public void validaImporteSolDev() {
        /**     getAdicionarSolicitud().setFlagTemp(Boolean.FALSE); */
        BigDecimal importefectuadas = null;
        BigDecimal importeEfectuado = null;
        BigDecimal saldoFavor = null;
        try {
            importefectuadas =
                    null != getTramite().getSaldoFavor().getImporteEfectuados() ? getTramite().getSaldoFavor().getImporteEfectuados() :
                    ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO;
            importeEfectuado =
                    null != getTramite().getSaldoFavor().getImporteAcreditramientoEfectuado() ? getTramite().getSaldoFavor().getImporteAcreditramientoEfectuado() :
                    ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO;
            saldoFavor =
                    null != getTramite().getSaldoFavor().getImporteSaldoFavor() ? getTramite().getSaldoFavor().getImporteSaldoFavor() :
                    ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            JSFUtils.messageGlobal(ConstantesTipoArchivo.MENSAJE_IMPORTES, FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (importeEfectuado.add(importefectuadas).compareTo(saldoFavor) == 1) {
            JSFUtils.messageGlobal(getTramite().getSaldoFavor().getEtiquetaSaldo().concat(". ").concat(ConstanteValidacionRNFDC10.SALDO_FAVOR),
                                   FacesMessage.SEVERITY_ERROR);
            getTramite().getSaldoFavor().setImporteEfectuados(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
            getTramite().getSaldoFavor().setImporteAcreditramientoEfectuado(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
            if (!tramite.getSaldoFavor().isSaldoICEP()) {
                getTramite().getSaldoFavor().setImporteSaldoFavor(null);
                getTramite().getSaldoFavor().setImporteSolicitadoDevolucion(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
            }
        } else {
            getTramite().getSaldoFavor().setImporteSolicitadoDevolucion(getValidadorRN().rn20(importeEfectuado.add(importefectuadas),
                                                                                        saldoFavor));
        }
        
        if(ValidaDatosSolicitud.isSectorAgropecuario(getTramite().getTipoTramite().getIdNum())){
            BigDecimal saldoLimite = new BigDecimal(ConstanteAvisoCompensacion.IMPORTE_NUEVAS_INVERSIONES);
            if (importeEfectuado.add(saldoFavor).compareTo(saldoLimite) == 1) {
                JSFUtils.messageGlobal(getTramite().getSaldoFavor().getEtiquetaSaldo().concat(". ").concat(ConstanteValidacionRNFDC10.LIMITE_SALDO_AGROPECUARIO),
                                       FacesMessage.SEVERITY_ERROR);
                getTramite().getSaldoFavor().setImporteEfectuados(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
                getTramite().getSaldoFavor().setImporteAcreditramientoEfectuado(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
                if (!tramite.getSaldoFavor().isSaldoICEP()) {
                    getTramite().getSaldoFavor().setImporteSaldoFavor(null);
                    getTramite().getSaldoFavor().setImporteSolicitadoDevolucion(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
                }
            } 
        }
    }

    public void showUploadFile() {
        LOG.debug("INICIO metodo showUploadFile");
        flagsSolicitud.setShowUploadFile(Boolean.FALSE);
    }

    public void validaFechaCausacion() throws SIATException {
        Date fechCausacion = tramite.getSaldoFavor().getFechaCaucion();
        int ejercicio = tramite.getEjercicio().getIdNum();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        if (Integer.parseInt(dateFormat.format(fechCausacion)) > ejercicio ||
            Integer.parseInt(dateFormat.format(fechCausacion)) < ejercicio) {
            showMessage(ConstantesIds.MSG_40, FacesMessage.SEVERITY_ERROR);
            tramite.getSaldoFavor().setFechaCaucion(ValidaDatosSolicitud.FECHANULL);
        }
    }

    public void validaFechaPeresentacion() throws SIATException {
        Date fechacapturada = tramite.getSaldoFavor().getFechaPresentacion();
        SimpleDateFormat formateador = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT);
        String fechaSistema = formateador.format(fechacapturada);
        String periodo =
            tramite.getPeriodo().getIdString().substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_2);
        String periodoCap = String.valueOf(tramite.getEjercicio().getIdNum() + "-");
        periodoCap = periodoCap + (periodo.equals("NA") ? "01" : periodo);
        Date fechaDate1;
        try {
            fechaDate1 = formateador.parse(fechaSistema);
            Date fechaDate2 = formateador.parse(periodoCap + "-01");
            if (fechaDate1.before(fechaDate2)) {
                LOG.info("La fecha presentacion debe ser posterior al período por el que se solicita devolución:");
                LOG.info("Fecha presentacion " + fechaSistema + " | " + "Periodo " + periodoCap);

                showMessage(ConstantesIds.MSG_46, FacesMessage.SEVERITY_ERROR);
                tramite.getSaldoFavor().setFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
            }
        } catch (ParseException e) {
            LOG.error(e);
        }
        tramite.getSaldoFavor().setNormalFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
    }

    public void validaFechaPeresentacionNormal()throws SIATException {
        Date fechPresentacion = tramite.getSaldoFavor().getFechaPresentacion();
        Date fechNormalPresentacion = tramite.getSaldoFavor().getNormalFechaPresentacion();
        
        Date fechacapturada = tramite.getSaldoFavor().getNormalFechaPresentacion();
        SimpleDateFormat formateador = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT);
        String fechaSistema = formateador.format(fechacapturada);
        if (null != fechPresentacion) {
            if ((fechNormalPresentacion.compareTo(fechPresentacion) > 0)) {
                tramite.getSaldoFavor().setNormalFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
                showMessage(ConstantesIds.MSG_42, FacesMessage.SEVERITY_ERROR);
                tramite.getSaldoFavor().setFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
            }
            
            String periodo =
                tramite.getPeriodo().getIdString().substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_2);
            String periodoCap = String.valueOf(tramite.getEjercicio().getIdNum() + "-");
            periodoCap = periodoCap + (periodo.equals("NA") ? "01" : periodo);
            Date fechaDate1;
            try {
                fechaDate1 = formateador.parse(fechaSistema);
                Date fechaDate2 = formateador.parse(periodoCap + "-01");
                if (fechaDate1.before(fechaDate2)) {
                    LOG.info("La fecha presentacion debe ser posterior al período por el que se solicita devolución:");
                    LOG.info("Fecha presentacion " + fechaSistema + " | " + "Periodo " + periodoCap);

                    showMessage(ConstantesIds.MSG_46, FacesMessage.SEVERITY_ERROR);
                    tramite.getSaldoFavor().setFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
                }
            } catch (ParseException e) {
                LOG.error(e);
            }
        }
    }
    
    
    public void validaFechaPeresentacionNormal2() throws SIATException {
        Date fechacapturada = tramite.getSaldoFavor().getNormalFechaPresentacion();
        SimpleDateFormat formateador = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT);
        String fechaSistema = formateador.format(fechacapturada);
        String periodo =
            tramite.getPeriodo().getIdString().substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_2);
        String periodoCap = String.valueOf(tramite.getEjercicio().getIdNum() + "-");
        periodoCap = periodoCap + (periodo.equals("NA") ? "01" : periodo);
        Date fechaDate1;
        try {
            fechaDate1 = formateador.parse(fechaSistema);
            Date fechaDate2 = formateador.parse(periodoCap + "-01");
            if (fechaDate1.before(fechaDate2)) {
                LOG.info("La fecha presentacion debe ser posterior al período por el que se solicita devolución:");
                LOG.info("Fecha presentacion " + fechaSistema + " | " + "Periodo " + periodoCap);

                showMessage(ConstantesIds.MSG_46, FacesMessage.SEVERITY_ERROR);
                tramite.getSaldoFavor().setFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
            }
        } catch (ParseException e) {
            LOG.error(e);
        }
        tramite.getSaldoFavor().setNormalFechaPresentacion(ValidaDatosSolicitud.FECHANULL);
    }

    public void datosCorrectos() {
        if (tramite.getSaldoFavor().getDatosCorrectos().equals("No")) {
            tramite.getSaldoFavor().setFechaPresentacion(null);
            tramite.getSaldoFavor().setImporteAcreditramientoEfectuado(null);
            tramite.getSaldoFavor().setImporteEfectuados(null);
            tramite.getSaldoFavor().setImporteSaldoFavor(null);
            tramite.getSaldoFavor().setImporteSolicitadoDevolucion(null);
            tramite.getSaldoFavor().setTipoDeclaracion(null);
            tramite.getSaldoFavor().setNumeroOperacion(null);
            flagsSolicitud.setConfirmarICEP(Boolean.FALSE);
            tramite.getSaldoFavor().setSaldoICEP(Boolean.FALSE);
            setIcep(null);
            /** rNFDC19(tramite.getTipoTramite().getIdNum()); */
        }
    }

    private void initFlagSaldo() {
        tramite.setSaldoFavor(new InformacionSaldoFavorTO());
        flagsSolicitud.setBloqDeclaracionNormal(Boolean.FALSE);
        flagsSolicitud.setRowFechaCaucion(Boolean.FALSE);
        flagsSolicitud.setRowTipoDeclaracion(Boolean.FALSE);
        flagsSolicitud.setRowFechaPresentacion(Boolean.FALSE);
        flagsSolicitud.setRowNumeroOperacion(Boolean.FALSE);
        tramite.getSaldoFavor().setSaldoICEP(Boolean.FALSE);
        flagsSolicitud.setConfirmarICEP(Boolean.FALSE);
        flagsSolicitud.setRowNumeroDocumento(Boolean.FALSE);
        flagsSolicitud.setActivo(Boolean.TRUE);
    }

    public void confirmarSaldoAFavor() {
        if (getTramite().getSaldoFavor().getEtiquetaSaldo().equals("SF")) {
            /**  adicionarSolicitudPage.getAdicionarSolicitud().setEtiquetaSaldoFavor(null); */
            getTramite().getSaldoFavor().setImporteSaldoFavor(null);
        }
        if (!getFlagsSolicitud().isRowImporteDevComEfectuadas()) {
            getTramite().getSaldoFavor().setImporteAcreditramientoEfectuado(null);
        } else if (null == getTramite().getSaldoFavor().getImporteAcreditramientoEfectuado()) {
            getTramite().getSaldoFavor().setImporteAcreditramientoEfectuado(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
        }
        if (!getFlagsSolicitud().isRowImporteAcreditamiento()) {
            getTramite().getSaldoFavor().setImporteEfectuados(null);
        } else if (null == getTramite().getSaldoFavor().getImporteEfectuados()) {
            getTramite().getSaldoFavor().setImporteEfectuados(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
        }
    }

    public boolean isContribuyente() {
        /**3.1. Si el contribuyente no tiene el rol \u201cGran contribuyente\u201d o  es competencia de AGAFF con rol \u201cDictaminado\u201d  */
        if (!getTramite().getRolesContribuyente().isGranContribuyente() ||
            (getTramite().getRolesContribuyente().isGranContribuyente() &&
             getTramite().getRolesContribuyente().isDictaminado())) {
            return Boolean.TRUE;
        }
        return false;
    }

    public void elimarAnexoSecAgp() {
        for (ArchivoVO anexoSecAgp : getTramite().getAnexos()) {
            if (anexoSecAgp.getId().equals(ConstantesDyCNumerico.VALOR_1)) {
                getTramite().getAnexos().remove(anexoSecAgp);
                return;
            }
        }
    }

    public void recuperarSolTemp(SolicitudDevTempVO solTemp) throws SIATException {
        setDataModel(new SIATDataModel<Serializable>());
        setDataModelAnexo(new SIATDataModel<Serializable>());
        setDataModelDocumento(new SIATDataModel<Serializable>());
        setFlagsSolicitud(new AdicionarSolicitudDTO());
        getFlagsSolicitud().setCompetencia(ConstantesTipoRol.AGAFF);
        DyccConceptoDTO concepto = new DyccConceptoDTO();
        TramiteDTO recuperaTramite = new TramiteDTO();
        recuperaTramite.setAnexos(new ArrayList<ArchivoVO>());
        recuperaTramite.setDocumentos(new ArrayList<ArchivoVO>());
        setDocumentosAdjuntos(new ArrayList<ArchivoVO>());
        setInconsistenciasDTO(new ArrayList<DyccInconsistDTO>());
        recuperaTramite.setInconsistencias(solTemp.getInconsistTemp());
        concepto.setIdConcepto(solTemp.getSolicitudTemp().getIdConcepto());
        recuperaTramite.setConcepto(concepto);
        recuperaTramite.setInfoAdicional(solTemp.getSolicitudTemp().getInfoAdicional());
        recuperaTramite.setSaldoFavor(solTemp.getSolDeclaracionTemp());
        recuperaInforDelSaldo(solTemp.getSolDeclaracionTemp().getNormalNumOperacion());
        recuperaTramite.setPersona((PersonaDTO)ValidaDatosSolicitud.recuperaPersona(solTemp.getSolContribTemp().getDatosContribuyente()));
        recuperaTramite.setRolesContribuyente(ValidaDatosSolicitud.recuperaRolesContribuyente(solTemp.getSolContribTemp()));
        recuperaTramite.getRolesContribuyente().setRoles(new ArrayList<PersonaRolDTO>());
        recuperaTramite.getRolesContribuyente().setClaveLocalidad(solTemp.getSolicitudTemp().getClaveAdm().toString());
        recuperaTramite.getRolesContribuyente().setClaveAdmon(solTemp.getSolicitudTemp().getClaveAdm());
        recuperaTramite.setOrigenSaldo(new CatalogoTO(solTemp.getSolicitudTemp().getIdOrigensaldo()));
        recuperaTramite.setTipoTramite(new CatalogoTO(solTemp.getSolicitudTemp().getIdTipotramite()));
        recuperaTramite.setSubOrigenSaldo(ValidaDatosSolicitud.recuperaObjeto(solTemp.getSolicitudTemp().getIdSuborigensaldo()));
        recuperaTramite.setActividadEconomica(ValidaDatosSolicitud.recuperaObjeto(solTemp.getSolicitudTemp().getIdActividad()));
        recuperaTramite.setTipoPeriodo(new CatalogoTO(solTemp.getSolicitudTemp().getIdPeriodo()));
        recuperaTramite.setPeriodo(new CatalogoTO(solTemp.getSolicitudTemp().getIdPeriodo()));
        recuperaTramite.setEjercicio(new CatalogoTO(solTemp.getSolicitudTemp().getIdEjercicio()));
        recuperaTramite.setSubTramite(ValidaDatosSolicitud.recuperaObjeto(solTemp.getSolicitudTemp().getIdSubtipotramite()));
        recuperaTramite.setInstitucionFinanciera(recuperaCuentaCLABE(solTemp.getSolicitudTemp().getClabeBancaria(),
                                                                     solTemp.getArchivos().get(0)));
        recuperaTramite.setSecAgp(presentaInfoAgro(solTemp.getSolicitudTemp().getInfoAgropecuario(),
                                                   solTemp.getSolicitudTemp().getAplicaFacilidad(),
                                                   solTemp.getSolicitudTemp().getEstadoPatron()));
        recuperaTramite.getInstitucionFinanciera().setCuentaValida(solTemp.getSolicitudTemp().getManifiestaEdocta());

        getFlagsSolicitud().setMessageProtestaEdoCta(messageSolicitud.getMessage(ConstantesDyCNumerico.VALOR_53, 1));
        recuperaTramite.setSaldoIcep(solTemp.getSolicitudTemp().getSaldoIcep());
        if (null != solTemp.getSolicitudTemp().getDatosCorrectos()) {
            recuperaTramite.getSaldoFavor().setDatosCorrectos(solTemp.getSolicitudTemp().getDatosCorrectos());
            recuperaTramite.getSaldoFavor().setSaldoICEP(solTemp.getSolicitudTemp().getDatosCorrectos().equals("1"));
        }
        if (null != solTemp.getSolicitudTemp().getProtesta()) {
            getFlagsSolicitud().setProtesta(solTemp.getSolicitudTemp().getProtesta().toString());
        }
        recuperaTramite.setInfoDeclarativa(new InformacionDeclarativaDTO());
        setTramite(recuperaTramite);
        presentaProtesta(solTemp.getSolicitudTemp().getProtesta());
        getArchivosTemp(solTemp.getArchivos());
        presentaAnexos();
        informacionDiotAltex(solTemp.getSolicitudTemp().getDiotNumOperacion(),
                             solTemp.getSolicitudTemp().getAltexConstancia(),
                             solTemp.getSolicitudTemp().getDiotFechaPresenta());
    }

    private void informacionDiotAltex(String numDiot, String numAltex, Date fechaDiot) {
        if (null != numDiot) {
            getFlagsSolicitud().setFlagDeclaracion("Si");
            getFlagsSolicitud().setRowDIOT(Boolean.TRUE);
            getFlagsSolicitud().setBloqInfoDIOT(Boolean.TRUE);
            getTramite().getInfoDeclarativa().setDiotNumOperacion(numDiot);
            getTramite().getInfoDeclarativa().setDiotFechPrecentacion(fechaDiot);

        }
        if (null != numAltex) {
            getFlagsSolicitud().setRowALTEX(Boolean.TRUE);
            getTramite().getInfoDeclarativa().setAltexNumConstancia(Integer.valueOf(numAltex));
        }
    }

    private void presentaAnexos() {
        if (!getTramite().getAnexos().isEmpty()) {
            getFlagsSolicitud().setShowUploadFile(Boolean.TRUE);
            getFlagsSolicitud().setBloqAnexos(Boolean.TRUE);
        }
    }


    private void getArchivosTemp(List<ArchivoVO> anexos) {
        if (!anexos.isEmpty()) {
            anexos.remove(0);
            setMessageArchivosSolTemp(new ArrayList<String>());
            for (ArchivoVO item : anexos) {
                if (item.getId().equals(0)) {
                    getTramite().getDocumentos().add(item);
                    getDocumentosAdjuntos().add(item);
                } else {
                    item.setEstado(null != item.getNombreArchivo() ? ConstantesTipoArchivo.EDO_ANEXO_ADJUNTADO :
                                   ConstantesTipoArchivo.EDO_ANEXO);
                    addMessageReq(null != item.getNombreArchivo() ? null : item.getDescripcion());
                    getTramite().getAnexos().add(item);
                }
            }
            getDataModelAnexo().setWrappedData(getTramite().getAnexos());
        }
    }

    private void addMessageReq(String messageReq) {
        if (null != messageReq) {
            getMessageArchivosSolTemp().add(messageReq);
        }
    }

    private void presentaProtesta(Integer protesta) {
        if (null != protesta) {
            getFlagsSolicitud().setMessageProtestaOperaciones(messageSolicitud.getMessage(ConstatesMsgDictaminadores.MSG_MD_NOT_COMIC,
                                                                                          1));
            getFlagsSolicitud().setFlagOperaciones(Boolean.TRUE);
            getFlagsSolicitud().setProtesta(protesta.toString());
        }
    }

    private DyctCuentaBancoDTO recuperaCuentaCLABE(String cuntaClabe, ArchivoVO itemArchivo) {
        DyctCuentaBancoDTO ctaBancaria = null;
        DyctArchivoDTO edoCta = null;
        if (getValidadorRN().rn470(cuntaClabe)) {
            ctaBancaria = new DyctCuentaBancoDTO();
            DyccInstCreditoDTO instCredit = new DyccInstCreditoDTO();
            instCredit.setIdInstCredito(getValidadorRN().getIdBancario());
            instCredit.setDescripcion(getValidadorRN().getDescripcion());
            ctaBancaria.setClabe(cuntaClabe);
            ctaBancaria.setDyccInstCreditoDTO(instCredit);
            ctaBancaria.setCuentaValida(1);
            edoCta = new DyctArchivoDTO();
            edoCta.setIdArchivo(1);
            edoCta.setUrl(itemArchivo.getUrl().substring(0, itemArchivo.getUrl().lastIndexOf("/archivos")));
            edoCta.setNombreArchivo(itemArchivo.getNombreArchivo());
            edoCta.setDescripcion("CUENTA CLABE");
            itemArchivo.setDescripcion("CUENTA CLABE");
            setDocEdoCta(itemArchivo);
            getDocumentosAdjuntos().add(itemArchivo);
            ctaBancaria.setDyctArchivoDTO(edoCta);
            setCarpetaTemp(new StringBuilder(edoCta.getUrl()));
        }
        return ctaBancaria;
    }

    private CatalogoTO presentaInfoAgro(Integer infoAgro, Integer aplica, Integer edoPadron) {
        CatalogoTO secAgro = null;
        if (null != infoAgro) {
            secAgro = new CatalogoTO(edoPadron);
            secAgro.setDescripcion(infoAgro.toString());
            getFlagsSolicitud().setMessageSecAgp(messageSolicitud.getMessage(ConstantesIds.MSG_55, 1));
            getFlagsSolicitud().setResSecAgp(Boolean.TRUE);
            getFlagsSolicitud().setFlagSecAgp(Boolean.TRUE);
        } else if (null != aplica) {
            secAgro = new CatalogoTO(edoPadron);
            secAgro.setIdString(aplica.toString());
            getFlagsSolicitud().setMessageSecAgp(messageSolicitud.getMessage(ConstantesIds.MSG_54, 1));
            getFlagsSolicitud().setResSecAgp(Boolean.TRUE);
            getFlagsSolicitud().setFlagSecAgp(Boolean.TRUE);
        }
        return secAgro;
    }

    private void recuperaInforDelSaldo(String informacion) {
        /** Recupera el objeto de los datos de la declaracion  */
        if (null != informacion) {
            getFlagsSolicitud().setBloqDeclaracionNormal(Boolean.TRUE);
        }
    }

    private void showMessage(int idMessage, FacesMessage.Severity severity) {
        JSFUtils.messageGlobal(messageSolicitud.getMessage(idMessage, 1), severity);
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    public TramiteDTO getTramite() {
        return tramite;
    }

    public void setFlagsSolicitud(AdicionarSolicitudDTO adicionarSolicitud) {
        this.flagsSolicitud = adicionarSolicitud;
    }

    public AdicionarSolicitudDTO getFlagsSolicitud() {
        return flagsSolicitud;
    }


    public void setInconsistenciasDTO(List<DyccInconsistDTO> inconsistenciasDTO) {
        this.inconsistenciasDTO = inconsistenciasDTO;
    }

    public List<DyccInconsistDTO> getInconsistenciasDTO() {
        return inconsistenciasDTO;
    }


    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setCatOrigenSaldo(List<CatalogoTO> catOrigenSaldo) {
        this.catOrigenSaldo = catOrigenSaldo;
    }

    public List<CatalogoTO> getCatOrigenSaldo() {
        return catOrigenSaldo;
    }

    public void setCatTipoTramite(List<CatalogoTO> catTipoTramite) {
        this.catTipoTramite = catTipoTramite;
    }

    public List<CatalogoTO> getCatTipoTramite() {
        return catTipoTramite;
    }

    public void setCatSubOrigenSaldo(List<CatalogoTO> catSubOrigenSaldo) {
        this.catSubOrigenSaldo = catSubOrigenSaldo;
    }

    public List<CatalogoTO> getCatSubOrigenSaldo() {
        return catSubOrigenSaldo;
    }


    public void setCatTipoPeriodo(List<CatalogoTO> catTipoPeriodo) {
        this.catTipoPeriodo = catTipoPeriodo;
    }

    public List<CatalogoTO> getCatTipoPeriodo() {
        return catTipoPeriodo;
    }

    public void setCatPeriodo(List<CatalogoTO> catPeriodo) {
        this.catPeriodo = catPeriodo;
    }

    public List<CatalogoTO> getCatPeriodo() {
        return catPeriodo;
    }

    public void setCatEjercicio(List<CatalogoTO> catEjercicio) {
        this.catEjercicio = catEjercicio;
    }

    public List<CatalogoTO> getCatEjercicio() {
        return catEjercicio;
    }

    public void setAnexo(ArchivoVO anexo) {
        this.anexo = anexo;
    }

    public ArchivoVO getAnexo() {
        return anexo;
    }

    public void setLisAnexo(List<ArchivoVO> lisAnexo) {
        this.lisAnexo = lisAnexo;
    }

    public List<ArchivoVO> getLisAnexo() {
        return lisAnexo;
    }

    public void setDocumento(ArchivoVO documento) {
        this.documento = documento;
    }

    public ArchivoVO getDocumento() {
        return documento;
    }

    public void setSubtramite(List<CatalogoTO> subtramite) {
        this.subtramite = subtramite;
    }

    public List<CatalogoTO> getSubtramite() {
        return subtramite;
    }

    public void setValidadorRN(ValidadorRNRegistro crnPF) {
        this.validadorRN = crnPF;
    }

    public ValidadorRNRegistro getValidadorRN() {
        return validadorRN;
    }

    public void setListInstCredito(List<DyctCuentaBancoDTO> listInstCredito) {
        this.listInstCredito = listInstCredito;
    }

    public List<DyctCuentaBancoDTO> getListInstCredito() {
        return listInstCredito;
    }

    public void setIcep(ObtieneIcepDTO icep) {
        this.icep = icep;
    }

    public ObtieneIcepDTO getIcep() {
        return icep;
    }

    public void setTramitesValidacion(TramitesValidacionDTO tramitesValidacion) {
        this.tramitesValidacion = tramitesValidacion;
    }

    public TramitesValidacionDTO getTramitesValidacion() {
        return tramitesValidacion;
    }


    public void setCatActividades(List<CatalogoTO> catActividades) {
        this.catActividades = catActividades;
    }

    public List<CatalogoTO> getCatActividades() {
        return catActividades;
    }

    public void setCarpetaTemp(StringBuilder carpetaTemp) {
        this.carpetaTemp = carpetaTemp;
    }

    public StringBuilder getCarpetaTemp() {
        return carpetaTemp;
    }

    public void setFileEdoCta(UploadedFile fileEdoCta) {
        this.fileEdoCta = fileEdoCta;
    }

    public UploadedFile getFileEdoCta() {
        return fileEdoCta;
    }

    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setFileDoc(UploadedFile fileDoc) {
        this.fileDoc = fileDoc;
    }

    public UploadedFile getFileDoc() {
        return fileDoc;
    }

    public void setFileAnexo(UploadedFile fileAnexo) {
        this.fileAnexo = fileAnexo;
    }

    public UploadedFile getFileAnexo() {
        return fileAnexo;
    }

    public void setDocumentosAdjuntos(List<ArchivoVO> documentosAdjuntos) {
        this.documentosAdjuntos = documentosAdjuntos;
    }

    public List<ArchivoVO> getDocumentosAdjuntos() {
        return documentosAdjuntos;
    }

    public void setMessageArchivosSolTemp(List<String> messageArchivosSolTemp) {
        this.messageArchivosSolTemp = messageArchivosSolTemp;
    }

    public List<String> getMessageArchivosSolTemp() {
        return messageArchivosSolTemp;
    }

    public void setDocEdoCta(ArchivoVO docEdoCta) {
        this.docEdoCta = docEdoCta;
    }

    public ArchivoVO getDocEdoCta() {
        return docEdoCta;
    }
    
    public void validarString(){
        String temp = tramite.getInfoAdicional().replace("\n","");
        LOG.info("Cadena original: " + tramite.getInfoAdicional().length());
        LOG.info("Cadena recortada: " + temp.length());
        if (tramite.getInfoAdicional().length() >= OCHOCIENTOS) {
            tramite.setInfoAdicional(tramite.getInfoAdicional().substring(CERO, OCHOCIENTOS));
            maxLength = temp.length();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "Avíso", "Ha sobrepasado el límite de caracteres"));
            RequestContext.getCurrentInstance().update("form:messages");
        }else{
            LOG.info("La cadena es menor, se actualiza el length a " + OCHOCIENTOS);
            maxLength = OCHOCIENTOS;
        }
        RequestContext.getCurrentInstance().update("form:infoAdicional");
    }

    public int getMaxLength() {
        return maxLength;
    }
    
}
