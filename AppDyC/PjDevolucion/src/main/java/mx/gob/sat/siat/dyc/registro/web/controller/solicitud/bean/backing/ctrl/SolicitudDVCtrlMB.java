/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.ctrl;


import java.io.FileInputStream;
import java.io.IOException;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import mx.gob.sat.mat.dyc.integrarexpediente.service.IcepService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInstCreditoService;
import mx.gob.sat.siat.dyc.domain.icep.ObtieneIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;

import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.EjecutaFielMB;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.impl.ConsultaPadronSolComponent;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.AdicionarSolicitudMB;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.DatosContribuyenteMB;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.AsignarException;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCompetenciaAgaff;
import mx.gob.sat.siat.dyc.util.constante.ConstantesConsultaDBLink;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIVAProductos;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsValidacionesTramite;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajesMatrizDictaminadores;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPIMMEX;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaPeriodo;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaRNFDC16;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaRNFDC20;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;


/**
 * @author JEFG
 * */
@ManagedBean(name = "solicitudDVCtrlMB")
@RequestScoped
public class SolicitudDVCtrlMB {

    private static final Logger LOGGER = Logger.getLogger(SolicitudDVCtrlMB.class);
    private Integer showTab = null;
    private Integer tramite = null;
    private String folio;
    private String mensajeDialogo;
    private boolean bDictaminador = Boolean.TRUE;

    public boolean isbDictaminador() {
        return bDictaminador;
    }

    public void setbDictaminador(boolean bDictaminador) {
        this.bDictaminador = bDictaminador;
    }

    private static final String ERROR = "DC-E";
    private static final long LIMITE_FOLIO = 999999999;
    private static final String ERROR_VALIDACION = "error en validacion";
  
    /** Services  */
    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;
    @ManagedProperty("#{consultaPadronSolComponent}")
    private ConsultaPadronSolComponent consultaPadronesSol;
    @ManagedProperty("#{acuseReciboService}")
    private AcuseReciboService acuseReciboService;
    @ManagedProperty("#{icepService}")
    private IcepService icepService;
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{dyccInstCreditoService}")
    private DyccInstCreditoService dyccInstCreditoService;
    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;

    @ManagedProperty("#{validaTramitesService}")
    private ValidaTramitesService serviceValidarTramite;

    /** PageMB  */
    @ManagedProperty(value = "#{ejecutaFIELMB}")
    private EjecutaFielMB appletFiel;

    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB mbAdicionarSolicitud;

    @ManagedProperty(value = "#{datosContribuyenteMB}")
    private DatosContribuyenteMB datosContribuyentePage;

    public void createSolicitud() {
        String solTemp =
            null != datosContribuyentePage.getSolDevRegistro() ? datosContribuyentePage.getSolDevRegistro().getIdDev() :
            null;
        mbAdicionarSolicitud.getTramite().setSolTemp(solTemp);
        try {
            if (validaProperties()) {
                LOGGER.info("Init Firmado FIEL!... ");
                appletFiel.setDatosSolicitud(ConstantesDyCNumerico.VALOR_2, mbAdicionarSolicitud.getTramite(),
                                             (ValidaDatosSolicitud.generaCadenaOriginal(mbAdicionarSolicitud.getTramite())),
                                             getServiceObtenerSesion().execute());
                mbAdicionarSolicitud.getFlagsSolicitud().setStep("T8");
                RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.LOADINGBAR_HIDE);
                JSFUtils.getExternalContext().redirect(JSFUtils.getExternalContext().getRequestContextPath() +
                                                       ValidaDatosSolicitud.FIEL);
            } else if (!validaRn6()) {
                if (importeSolicitadoValido()) {
                    generarAcuseRecibo();
                }
            } else {
                JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ") +
                                       mbAdicionarSolicitud.getValidadorRN().getEstadoRN6().toLowerCase(),
                                       FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception e) {
            ManejadorLogException.getTraceError(e);
            mbAdicionarSolicitud.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute("dlgSolError.show();");
        }
    }


    private boolean validaProperties() throws SIATException {
        boolean prop = false;
        Properties properties = new Properties();
        FileInputStream archivo = null;
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            properties.load(archivo);
            if (properties.getProperty("presentaFIEL").equals("true") && presentaFiel()) {
                prop = Boolean.TRUE;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new SIATException("No existe el archivo o directorio (/siat/dyc/configuraciondyc/parametrosdyc.properties)",
                                    e);
        } finally {
            if (null != archivo) {
                try {
                    archivo.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return prop;
    }

    /** valida regla Presenta FIEL Contribuyente, por default siempre sera PF o PM  */
    private boolean presentaFiel() {
        boolean val = false;
        if (datosContribuyentePage.getTipoFirmado().equals(ValidaDatosSolicitud.FIRMAR_FIEL)) {
            val = Boolean.TRUE;
        }
        return val;
    }

    private void generarAcuseRecibo() throws SIATException, AsignarException {
        Map<String, Object> datos = null;

        try {
            setbDictaminador(Boolean.TRUE);
            datos = ValidaDatosSolicitud.generaCadenaySello(mbAdicionarSolicitud.getTramite());

            dycpSolicitudService.insertaSolicitud(mbAdicionarSolicitud.getTramite(), datos.get("sello").toString(),
                                                  datos.get("cadena").toString()).getOutput();
        } catch (IOException e) {
            folio = ERROR + (int)(Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar la cadena original y/o el sello digital.";
            LOGGER.error("folio-->" + folio + " Mensaje Error: " + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            mbAdicionarSolicitud.getFlagsSolicitud().setShowMessageErrorAsig(true);
            RequestContext.getCurrentInstance().execute("dlgAsigError.show();");
        } catch (AsignarException ae) {

            mensajeDialogo = "No existen dictaminadores disponibles para asignar en esta administración.";

            ManejadorLogException.getTraceError(ae);
            mbAdicionarSolicitud.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.FALSE);
            mbAdicionarSolicitud.getFlagsSolicitud().setShowMessageErrorAsigFolio(Boolean.TRUE);
            setbDictaminador(Boolean.FALSE);
            RequestContext.getCurrentInstance().execute("dlgAsigError.show();");
        } catch (SIATException e) {
            folio = ERROR + (int)(Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar el acuse de su solicitud.";
            LOGGER.error("folio-->" + folio + " Mensaje Error: " + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            mbAdicionarSolicitud.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute("dlgAsigError.show();");
        }
    }

    public String onFlowProcess(FlowEvent event) throws SIATException {
        LOGGER.debug("INICIO onFlowProcess");
        tramite = mbAdicionarSolicitud.getTramite().getTipoTramite().getIdNum();
        if (isAnteriorTab(Integer.parseInt(event.getOldStep().substring(1)),
                          Integer.parseInt(event.getNewStep().substring(1)))) {
            return "T".concat(getShowTab().toString());
        }
        try {
            switch (Integer.valueOf(event.getOldStep().substring(1))) {
            case ValidaDatosSolicitud.TABTRAMITE:
                mbAdicionarSolicitud.getFlagsSolicitud().setBloqDoctos(Boolean.FALSE);
                validaRFCTerceros();
                break;
            case ValidaDatosSolicitud.TABICEP:
                setShowTab(ValidaDatosSolicitud.TABSALDO);
                if (validaEmpresas()) {
                    setShowTab(ValidaDatosSolicitud.TABICEP);
                } else if (!mbAdicionarSolicitud.getFlagsSolicitud().getBloqInfoSaldoFavor()) {
                    seRequiereProtestaVerdad();
                    presentaDeclaracion();
                    consultaAnexosElectronicos();
                    mbAdicionarSolicitud.validaInfoSaldo();
                    mbAdicionarSolicitud.getFlagsSolicitud().setBloqInfoSaldoFavor(Boolean.TRUE);
                }
                break;
            case ValidaDatosSolicitud.TABSALDO:
                if (detenerRegistroSolicitud()) {
                    setShowTab(ValidaDatosSolicitud.TABICEP);
                } else if (informacionDeclarativa()) {
                    setShowTab(ValidaDatosSolicitud.TABDIOT);
                } else {
                    cuentaBanco();
                    setShowTab(ValidaDatosSolicitud.TABBANCO);
                }
                break;
            case ValidaDatosSolicitud.TABDIOT:
                cuentaBanco();
                setShowTab(ValidaDatosSolicitud.TABBANCO);
                break;
            case ValidaDatosSolicitud.TABBANCO:
                if (validaEdoCta()) {
                    setShowTab(ValidaDatosSolicitud.TABBANCO);
                } else if (presentaAnexosElectronicos()) {
                    setShowTab(ValidaDatosSolicitud.TABANEXOS);
                } else {
                    presentaDocumentoF3241();
                    setShowTab(ValidaDatosSolicitud.TABDOCUMENTO);
                }
                break;
            case ValidaDatosSolicitud.TABANEXOS:
                if (validaAnexosAdjuntos()) {
                    JSFUtils.messageGlobal(ConstantesArchivo.ARCHIVO_VALIDO, FacesMessage.SEVERITY_INFO);
                    setShowTab(ValidaDatosSolicitud.TABANEXOS);
                } else {
                    presentaDocumentoF3241();
                    setShowTab(ValidaDatosSolicitud.TABDOCUMENTO);
                }
                break;
            case ValidaDatosSolicitud.TABDOCUMENTO:
                if (validaDocAdjuntos()) {
                    JSFUtils.messageGlobal(ConstantesArchivo.ARCHIVO_VALIDO, FacesMessage.SEVERITY_INFO);
                    setShowTab(ValidaDatosSolicitud.TABDOCUMENTO);
                } else {
                    segirAInsistencia();
                    setShowTab(ValidaDatosSolicitud.TABDATOS);
                }
                break;
            default:
                setShowTab(ValidaDatosSolicitud.TABTRAMITE);
            }
        } catch (SIATException e) {
            LOGGER.error("Error en flujo principal del Registro de la soicitud - Tab:" + getShowTab());
            ManejadorLogException.getTraceError(e);
            throw e;
        } catch (Exception e) {
            throw new SIATException("Error en flujo principal del Registro de la soicitud", e);
        }
        RequestContext.getCurrentInstance().update(ValidaDatosSolicitud.UPDATEBAR);
        return "T".concat(getShowTab().toString());
    }

    private boolean isAnteriorTab(int anteriorTab, int siguienteTab) {
        boolean isAnteriorTab = Boolean.TRUE;
        if (!mbAdicionarSolicitud.getFlagsSolicitud().getBloqInfoDIOT() &&
            ValidaDatosSolicitud.validaTabDIOT(anteriorTab, siguienteTab)) {
            setShowTab(ValidaDatosSolicitud.TABSALDO);
        } else if (!mbAdicionarSolicitud.getFlagsSolicitud().getBloqAnexos() &&
                   ValidaDatosSolicitud.validaTabAnexos(anteriorTab, siguienteTab)) {
            setShowTab(ValidaDatosSolicitud.TABBANCO);
        } else if (siguienteTab < anteriorTab) {
            setShowTab(siguienteTab);
        } else {
            isAnteriorTab = false;
        }
        return isAnteriorTab;
    }


    private void validaRFCTerceros() throws SIATException {
        mbAdicionarSolicitud.getFlagsSolicitud().setBloqDatosIcep(Boolean.TRUE);
        setShowTab(ValidaDatosSolicitud.TABTRAMITE);
        if (mbAdicionarSolicitud.getFlagsSolicitud().isVerRFC()) {
            try {
                mbAdicionarSolicitud.getTramite().setRetenedor(consultaPadronesSol.consultaRetenedor(mbAdicionarSolicitud.getFlagsSolicitud().getRfcTerceros()));
            } catch (SIATException e) {
                JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
                mbAdicionarSolicitud.getTramite().getRetenedor().setRfc(ConstantesDyC.NULL);

            }
        }
        if (mbAdicionarSolicitud.getFlagsSolicitud().isVerRFCControlador() &&
            mbAdicionarSolicitud.getTramite().getRfcControladora().isEmpty()) {
            JSFUtils.messageByIDComponent("rfcControladora", FacesMessage.SEVERITY_INFO, "Ingrese un rfc valido");
        } else {
            mbAdicionarSolicitud.getFlagsSolicitud().setBloqDatosIcep(Boolean.TRUE);
            setShowTab(ValidaDatosSolicitud.TABICEP);
        }
    }

    private boolean validaEmpresas() throws SIATException {
        if (ValidaDatosSolicitud.isTramiteIMMEX(tramite) &&
            consultaPadronesSol.consultaIMMEX(mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente(), tramite,
                                              mbAdicionarSolicitud.getTramite().getPeriodo().getIdString(),
                                              mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum())) {
            finalizarSolicitud(ConstantesDyCNumerico.VALOR_27);
            return Boolean.TRUE;
        }
        if (isEmpresaCertificada(tramite)) {
            return Boolean.TRUE;
        }
        return false;
    }

    private void presentaDeclaracion() throws SIATException, IOException {
        LOGGER.debug("INICIO presentaDeclaracion");
        if (!(mbAdicionarSolicitud.getTramitesValidacion().isTramUltimaDelclaracion() &&
              mbAdicionarSolicitud.getTramite().getTipoPeriodo().getIdString().equals(ConstantesValidaPeriodo.SIN_PERIODO) &&
              mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum() ==
              Constantes.OrigenesSaldo.PAGO_INDEBIDO.getIdOrigenSaldo().intValue())) {
            mbAdicionarSolicitud.getTramitesValidacion().setTramUltimaDelclaracion(false);
            if (mbAdicionarSolicitud.getTramitesValidacion().isTramConsultaSaldoICEPSP()) {
                consultaICEP(mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente());
            }
        }
    }

    private boolean seRequiereProtestaVerdad() throws SIATException {
        List<Integer> tramBajoProtesta =
            getServiceValidarTramite().getTramitesXValidacion(IdsValidacionesTramite.PROTESTA_DECIR_VERDAD).getOutputs();
        if (tramBajoProtesta.contains(tramite) && !mbAdicionarSolicitud.getFlagsSolicitud().isFlagOperaciones()) {
            mbAdicionarSolicitud.getFlagsSolicitud().setMessageProtestaOperaciones(getMessage(ConstantesMensajesMatrizDictaminadores.MSG_MD_NOT_COMIC));
            mbAdicionarSolicitud.getFlagsSolicitud().setFlagOperaciones(Boolean.TRUE);
            mbAdicionarSolicitud.getTramite().getTipoTramite().setDescripcion("1");
            mbAdicionarSolicitud.getFlagsSolicitud().setProtesta("Si");
            return Boolean.TRUE;
        }
        return false;
    }

    private boolean validaRn6() {       
        Map<String, Object> estadoSolAutomatica =mbAdicionarSolicitud.getValidadorRN().esTramiteISRConEstadoYTipoResolucionRn6Rn7(mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente(),
                                                       mbAdicionarSolicitud.getTramite().getConcepto().getIdConcepto(),
                                                       mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum(),
                                                       mbAdicionarSolicitud.getTramite().getPeriodo().getIdNum(),
                                                       mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum(),                        
                                                       mbAdicionarSolicitud.getTramite().getTipoTramite().getIdNum());        
        
        Map<String, Object> estadoTipoResolGeneral=mbAdicionarSolicitud.getValidadorRN().rn6(mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente(),
                                                       mbAdicionarSolicitud.getTramite().getConcepto().getIdConcepto(),
                                                       mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum(),
                                                       mbAdicionarSolicitud.getTramite().getPeriodo().getIdNum(),
                                                       mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum());
        Map<String, Object> estadoISRPFGeneral=mbAdicionarSolicitud.getValidadorRN().rn6Isr(mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente(),
                                                       mbAdicionarSolicitud.getTramite().getConcepto().getIdConcepto(),
                                                       mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum(),
                                                       mbAdicionarSolicitud.getTramite().getPeriodo().getIdNum(),
                                                       mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum());
        
        if((Boolean)estadoSolAutomatica.get("ISR_PF_AUT")){
            DyccEstadoSolDTO estadoAUT=(DyccEstadoSolDTO)estadoSolAutomatica.get("EDO_ISR_AUT");
            if(!(Boolean)estadoSolAutomatica.get("EDO_PERMITIDO")){
                JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ")+
                    ((estadoAUT!=null)?estadoAUT.getDescripcion():ERROR_VALIDACION).toLowerCase()
                    ,FacesMessage.SEVERITY_ERROR);
                LOGGER.error("RN7, Se encontro una solicitud automatica ISRPF con estado, no se permite el registro, estado: "+((estadoAUT!=null)?estadoAUT.getDescripcion():ERROR_VALIDACION));
                return Boolean.TRUE;
            } else {
                //buscare si el icep de la automatica tiene asociado un solicutd manual con estado no permitido
                if ((Boolean)estadoISRPFGeneral.get("EXISTE_EDO")) {
                    DyccEstadoSolDTO estado=(DyccEstadoSolDTO)estadoTipoResolGeneral.get("EDO_SOL");
                    JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ") +((estado!=null)?estado.getDescripcion():ERROR_VALIDACION).toLowerCase(),
                    FacesMessage.SEVERITY_ERROR);
                    LOGGER.error("RN6_ISR_PF, Se encontro una solicitud manual con estado, no se permite el registro, estado: "+((estado!=null)?estado.getDescripcion():ERROR_VALIDACION));
                    return Boolean.TRUE;
                }
                //Si se permite nuevo porque no se encontro uno estado de con devolucion simultanea para el icep
                LOGGER.error("RN7, No se encontro una solicitud automatica ISR PF AUT con con estado  con/sin tipó resolucion  por lo cual se permite el registro, estado: "+((estadoAUT!=null)?estadoAUT.getDescripcion():"No encontrado"));
                return false;
            }
        } else if ((Boolean)estadoTipoResolGeneral.get("EXISTE_EDO")) {
            DyccEstadoSolDTO estado=(DyccEstadoSolDTO)estadoTipoResolGeneral.get("EDO_SOL");
                JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ") +((estado!=null)?estado.getDescripcion():ERROR_VALIDACION).toLowerCase(),
            FacesMessage.SEVERITY_ERROR);
            LOGGER.error("RN6, Se encontro una solicitud devolucion con estado, no se permite el registro, estado: "+((estado!=null)?estado.getDescripcion():ERROR_VALIDACION));
            return Boolean.TRUE;
        } else {
            return false;
        }
    }
   
    private boolean detenerRegistroSolicitud() throws SIATException, IOException {
        if (validaRn6()) {
            return Boolean.TRUE;
        }
        if (!importeSolicitadoValido()) {
            return Boolean.TRUE;
        }
        if (mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum() ==
            ConstantesValidaRNFDC16.EXTRANJEROS_SIN_ESTABLECIMIENTO && tramite == ConstantesDyC.TIPO_TRAMITE_NO334 &&
            declaracionRetenedorNacional()) {
            return Boolean.TRUE;
        }
        return false;
    }

    private boolean informacionDeclarativa() {
        boolean isInfo = Boolean.FALSE;
  
        if (mbAdicionarSolicitud.getFlagsSolicitud().getBloqInfoDIOT()) {
            if (ValidaDatosSolicitud.isPeriodoBimestral(mbAdicionarSolicitud.getTramite().getTipoPeriodo().getIdString(),
                                                        mbAdicionarSolicitud.getTramite().getTipoTramite().getIdNum())) {
                mbAdicionarSolicitud.getFlagsSolicitud().setBloqInfoDIOT(Boolean.FALSE);
            } else {
             
                isNuevasInversiones(tramite);
                if (ValidaDatosSolicitud.datosALTEX(tramite)) {
                    mbAdicionarSolicitud.getFlagsSolicitud().setRowALTEX(Boolean.TRUE);
                     isInfo = Boolean.TRUE;
                }else{
                     mbAdicionarSolicitud.getFlagsSolicitud().setBloqInfoDIOT(Boolean.FALSE);
                }
               
            }
        }
        return isInfo;
    }

    private void isNuevasInversiones(int ivaNuevasInv) {
        if (ValidaDatosSolicitud.isPeriodoAnterior2015(ivaNuevasInv,
                                                       mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum())) {
            finalizarSolicitud(ConstantesDyCNumerico.VALOR_64);
        } else if (ivaNuevasInv == ConstantesIVAProductos.IVA_NUEVAS_INVERCIONES &&
                   ValidaDatosSolicitud.isImporteNuevasInversiones(mbAdicionarSolicitud.getTramite().getSaldoFavor().getImporteSaldoFavor().compareTo(new BigDecimal(ConstantesConsultaDBLink.IMPORTE_NUEVAS_INVERSIONES)),
                                                                   mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum())) {
            finalizarSolicitud(ConstantesMensajesMatrizDictaminadores.MSG_MD_13);
        }
    }


    private boolean validaDocAdjuntos() {
        int size = 0;
        if (null != mbAdicionarSolicitud.getFlagsSolicitud().getNumDocAdjuntos()) {
            size = mbAdicionarSolicitud.getTramite().getDocumentos().size();
            if (size < mbAdicionarSolicitud.getFlagsSolicitud().getNumDocAdjuntos()) {
                return Boolean.TRUE;
            }
        }
        return false;
    }

    private boolean validaEdoCta() {
        if (null == mbAdicionarSolicitud.getTramite().getInstitucionFinanciera()) {
            JSFUtils.messageGlobal(ValidaDatosSolicitud.SELECTCLABE, FacesMessage.SEVERITY_INFO);
        } else if (null == mbAdicionarSolicitud.getTramite().getInstitucionFinanciera().getDyctArchivoDTO()) {
            JSFUtils.messageGlobal(ValidaDatosSolicitud.SELECTCEDOCTA +
                                   mbAdicionarSolicitud.getTramite().getInstitucionFinanciera().getDyccInstCreditoDTO().getDescripcion(),
                                   FacesMessage.SEVERITY_INFO);
        } else {
            return false;
        }
        return Boolean.TRUE;
    }

    private void cuentaBanco() throws SIATException {
        if (mbAdicionarSolicitud.getListInstCredito().isEmpty()) {
            getCuentaBancosPorFRC();
            mbAdicionarSolicitud.getUrlLocalidad();
        }
    }


    public void getCuentaBancosPorFRC() throws SIATException {
        String rfc = mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente();
        if (null != rfc) {
            mbAdicionarSolicitud.setListInstCredito(dyccInstCreditoService.getCunetaBancosPorRFC(rfc).getOutputs());
            mbAdicionarSolicitud.getDataModel().setWrappedData(mbAdicionarSolicitud.getListInstCredito());
        }
    }

    private void segirAInsistencia() throws SIATException {
        if (!mbAdicionarSolicitud.getTramite().getInconsistencias().isEmpty()) {
            mbAdicionarSolicitud.getFlagsSolicitud().setMessageSol(null);
            mbAdicionarSolicitud.getFlagsSolicitud().setMessageSol(getMessage(ConstantesDyCNumerico.VALOR_4));
            RequestContext.getCurrentInstance().update(ValidaDatosSolicitud.UPDATE_MENSAJE_A_INSISTENCIA);
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.DIALOGO_A_INSISTENCIA);
        }
        mbAdicionarSolicitud.setFile(null);
        mbAdicionarSolicitud.confirmarSaldoAFavor();
    }

    private boolean importeSolicitadoValido() throws SIATException {
        DyctSaldoIcepDTO dyctSaldoIcepDTO = null;
        boolean restriccionRNFDC16 = Boolean.TRUE;
        Integer origenDev = null;
        BigDecimal saldoSolicitado =
            null != mbAdicionarSolicitud.getTramite().getSaldoFavor().getImporteSolicitadoDevolucion() ?
            mbAdicionarSolicitud.getTramite().getSaldoFavor().getImporteSolicitadoDevolucion() :
            ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO;
        /**Valida que el saldo solicitado no sea cero  */
        if (mbAdicionarSolicitud.getValidadorRN().validaImporteSolEnRNFD16(saldoSolicitado.intValue())) {
            showMessages(ConstantesDyCNumerico.VALOR_30, FacesMessage.SEVERITY_ERROR);
            return false;
        }
        /**Ò consulta saldo disponible en saldos DyC.., si existe mapea idSaldoIcep y valida -Mayor al ultimo saldo disponible registrado en Saldos DYC, de otro modo si no existe se crea(flag)*/
        origenDev = mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum();
        dyctSaldoIcepDTO = obtenerSaldoIcep();
        if (null == dyctSaldoIcepDTO) {
            mbAdicionarSolicitud.getTramite().setSaldoIcep(null);
            mbAdicionarSolicitud.getFlagsSolicitud().setImporteSaldosDyC(Boolean.FALSE);
        } else {
            mbAdicionarSolicitud.getFlagsSolicitud().setImporteSaldosDyC(Boolean.TRUE);
            mbAdicionarSolicitud.getTramite().setSaldoIcep(dyctSaldoIcepDTO.getIdSaldoIcep());
        }
        /**Ò si existe cantidad a favor registrado en la ultima declaracion registrada en Datawarehouse valida RNFD16 */
        if (validaICEP() &&
            !mbAdicionarSolicitud.getValidadorRN().rn16(saldoSolicitado.intValue(), new Double(mbAdicionarSolicitud.getIcep().getSaldoFavor()).intValue(),
                                                        tramite, origenDev,
                                                        mbAdicionarSolicitud.getTramite().getPeriodo().getIdNum())) {
            restriccionRNFDC16 = false;
        }
        return restriccionRNFDC16;
    }

    private boolean validaICEP() {
        if (null != mbAdicionarSolicitud.getIcep() && null != mbAdicionarSolicitud.getIcep().getTipoDeclaracion()) {
            return Boolean.TRUE;
        }
        return false;
    }

    private DyctSaldoIcepDTO obtenerSaldoIcep() throws SIATException {
        Map<String, Object> datos = new HashMap<String, Object>();
        datos.put("origen", mbAdicionarSolicitud.getTramite().getOrigenSaldo().getIdNum());
        datos.put("concepto", mbAdicionarSolicitud.getTramite().getConcepto().getIdConcepto());
        datos.put("impuesto", mbAdicionarSolicitud.getTramite().getImpuesto().getIdImpuesto());
        datos.put("ejercicio", mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum());
        datos.put("periodo", mbAdicionarSolicitud.getTramite().getPeriodo().getIdNum());
        datos.put("rfc", mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente());
        return consultaPadronesSol.consultaDyctSaldoIcep(datos);
    }

    private void consultaICEP(String rfc) throws SIATException, IOException {
        LOGGER.debug("INICIO consultaICEP");
        mbAdicionarSolicitud.setIcep(new ObtieneIcepDTO());
        mbAdicionarSolicitud.getIcep().setRfc(rfc);
        mbAdicionarSolicitud.getIcep().setEjercicio(String.valueOf(mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum()));
        mbAdicionarSolicitud.getIcep().setPeriodo(String.valueOf(mbAdicionarSolicitud.getTramite().getPeriodo().getIdNum()));
        mbAdicionarSolicitud.getIcep().setTipoTramite(String.valueOf(mbAdicionarSolicitud.getTramite().getTipoTramite().getIdNum()));
        mbAdicionarSolicitud.getIcep().setConcepto(String.valueOf(mbAdicionarSolicitud.getTramite().getConcepto().getIdConcepto()));
        mbAdicionarSolicitud.getIcep().setEstatus(String.valueOf(ConstantesDyCNumerico.VALOR_0));
        mbAdicionarSolicitud.getIcep().setUsuario(ConstantesSPIMMEX.USR_STORED_PROCEDURES);
        mbAdicionarSolicitud.getIcep().setBoId(String.valueOf(mbAdicionarSolicitud.getTramite().getPersona().getBoId()));
        try {
            mbAdicionarSolicitud.setIcep(icepService.obtenerIcep(mbAdicionarSolicitud.getIcep()));
        } catch (SQLException e) {
            throw new SIATException("ERROR EN STORED", e);
        }
    }

    /**RNDC12 omitida
     * private boolean importeCantidadFavor() {
        Double devolucion = solDevPage.getTramite().getSaldoFavor().getImporteSolicitadoDevolucion();
        Double permitido = ConstantesDyC.IMPORTE_MAXIMO;
        if (devolucion.intValue() > permitido.intValue()) {
            solicitudUtils.message(ConstantesDyCNumerico.VALOR_9, FacesMessage.SEVERITY_ERROR);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    } */

    private boolean declaracionRetenedorNacional() throws SIATException, IOException {
        consultaICEP(mbAdicionarSolicitud.getTramite().getRetenedor().getRfcVigente());
        if (null == mbAdicionarSolicitud.getIcep().getTipoDeclaracion()) {
            showMessages(ConstantesMensajes.MSG_6, FacesMessage.SEVERITY_ERROR);
            mbAdicionarSolicitud.getTramite().setPeriodo(null);
            mbAdicionarSolicitud.getTramite().setTipoPeriodo(null);
            mbAdicionarSolicitud.getTramite().setEjercicio(null);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**validaciòn de anexos electronicos RNDC55*/
    private void consultaAnexosElectronicos() throws SIATException {
        mbAdicionarSolicitud.getFlagsSolicitud().setBloqAnexos(Boolean.FALSE);
        mbAdicionarSolicitud.getTramite().getAnexos().clear();
        if ((!mbAdicionarSolicitud.getTramite().getRolesContribuyente().isGranContribuyente() &&
             !mbAdicionarSolicitud.getTramite().getRolesContribuyente().isDictaminado())) {
            mbAdicionarSolicitud.getTramite().setAnexos(mbAdicionarSolicitud.getValidadorRN().rn55(tramite));
            if (!mbAdicionarSolicitud.getTramite().getAnexos().isEmpty()) {
                mbAdicionarSolicitud.getFlagsSolicitud().setBloqAnexos(Boolean.TRUE);
            }
        }
        mbAdicionarSolicitud.getDataModelAnexo().setWrappedData(mbAdicionarSolicitud.getTramite().getAnexos());
    }

    /**Se presenta TAB con los anexos a requerir segun matriz de anexos y/o si el tramite es 130(sectorAgro)
     * se pedira ficha secAgro
     * */
    private boolean presentaAnexosElectronicos() throws SIATException {
        if (getTramite().equals(ConstantesDyCNumerico.VALOR_130)) {
            int i =
                mbAdicionarSolicitud.getTramite().getSaldoFavor().getImporteSolicitadoDevolucion().compareTo(new BigDecimal(ConstantesValidaRNFDC20.IMPORTE_AGROPECUARIO));
            if (ValidaDatosSolicitud.isImportePeriodo(i,
                                                      mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum())) {
                validaPadronAgropecuario();
            } else if (mbAdicionarSolicitud.getTramite().getAnexos().size() == 1 ||
                       mbAdicionarSolicitud.getTramite().getAnexos().isEmpty()) {
                mbAdicionarSolicitud.getFlagsSolicitud().setBloqAnexos(Boolean.FALSE);
            }
        }
        return mbAdicionarSolicitud.getFlagsSolicitud().getBloqAnexos();
    }

    private void validaPadronAgropecuario() throws SIATException {
        mbAdicionarSolicitud.elimarAnexoSecAgp();
        mbAdicionarSolicitud.getTramite().setSecAgp(new CatalogoTO());
        mbAdicionarSolicitud.getFlagsSolicitud().setFlagSecAgp(Boolean.TRUE);
        mbAdicionarSolicitud.getFlagsSolicitud().setBloqAnexos(Boolean.TRUE);
        int status =
            consultaPadronesSol.consultaSectorAgro(mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente());
        if (status == ConstantesDyCNumerico.VALOR_1) {
            mbAdicionarSolicitud.getFlagsSolicitud().setMessageSecAgp(getMessage(ConstantesDyCNumerico.VALOR_55));
            mbAdicionarSolicitud.getTramite().getSecAgp().setIdNum(status);
            RequestContext.getCurrentInstance().update("form:infoAgro");
            RequestContext.getCurrentInstance().execute("infoAgropecuario.show();");
        } else {
            mbAdicionarSolicitud.getFlagsSolicitud().setResSecAgp(Boolean.TRUE);
            mbAdicionarSolicitud.getTramite().getSecAgp().setIdNum(status);
            mbAdicionarSolicitud.getTramite().getSecAgp().setIdString("1");
            mbAdicionarSolicitud.getFlagsSolicitud().setMessageSecAgp(getMessage(ConstantesDyCNumerico.VALOR_54));
            RequestContext.getCurrentInstance().update("form:infoAplicaFacilida");
            RequestContext.getCurrentInstance().execute("aplicaFacilidad.show();");
        }
        mbAdicionarSolicitud.getTramite().getAnexos().add(mbAdicionarSolicitud.getValidadorRN().getFichaSecAgro());
    }

    private boolean isEmpresaCertificada(int tramite) throws SIATException {
        String res = null;
        if (ValidaDatosSolicitud.isTramitePadronCert(tramite)) {
            if (ValidaDatosSolicitud.isPeriodo2014(mbAdicionarSolicitud.getTramite().getEjercicio().getIdNum())) {
                res =
 consultaPadronesSol.consultaEmpresaCertificada(tramite, mbAdicionarSolicitud.getTramite().getPersona().getRfcVigente());
                if (res.equals(ConstantesDyC.NO_REGISTRADA)) {
                    finalizarSolicitud(ConstantesMensajesMatrizDictaminadores.MSG_MD_DEL_DICT);
                    return Boolean.TRUE;
                } else if (res.equals(ConstantesDyC.DIFERENTE_REGISTRO)) {
                    finalizarSolicitud(ConstantesMensajesMatrizDictaminadores.MSG_MD_ADI_ADD);
                    return Boolean.TRUE;
                }
            } else {
                finalizarSolicitud(ConstantesMensajesMatrizDictaminadores.MSG_MD_CFR_ELIM);
                return Boolean.TRUE;
            }
        }
        return false;
    }

    private void finalizarSolicitud(int idMessage) {
        mbAdicionarSolicitud.getFlagsSolicitud().setMessageGlobal(getMessage(idMessage));
        mbAdicionarSolicitud.getFlagsSolicitud().setEndSolicitud(Boolean.TRUE);
        RequestContext.getCurrentInstance().update("form:messageEndSolicitud");
        RequestContext.getCurrentInstance().execute("endSolicitud.show();");
    }

    private void presentaDocumentoF3241() throws SIATException {
        if (!mbAdicionarSolicitud.getFlagsSolicitud().getBloqDoctos()) {
            mbAdicionarSolicitud.getFlagsSolicitud().setNumDocAdjuntos(null);
            int competencia = mbAdicionarSolicitud.getFlagsSolicitud().getCompetencia();
            if (ValidaDatosSolicitud.isCompetenciaAGGC(competencia,
                                                       getServiceValidarTramite().validaTramite(ConstantesDyCNumerico.VALOR_30,
                                                                                                tramite)) ||
                ValidaDatosSolicitud.isCompetenciaAGAFF(competencia,
                                                        getServiceValidarTramite().validaTramite(ConstantesDyCNumerico.VALOR_31,
                                                                                                 tramite),
                                                        mbAdicionarSolicitud.getTramite().getRolesContribuyente().isDictaminado())) {
                mbAdicionarSolicitud.getFlagsSolicitud().setPresentaDoc(Boolean.TRUE);
                mbAdicionarSolicitud.getFlagsSolicitud().setNumDocAdjuntos(ConstantesDyCNumerico.VALOR_1);
                showMessages(ConstantesDyCNumerico.VALOR_34, FacesMessage.SEVERITY_INFO);
            }
            documentosComplementarios(competencia);
            mbAdicionarSolicitud.getFlagsSolicitud().setBloqDoctos(Boolean.TRUE);
        }
    }


    public void completarSolicitud() {
        if (validaDocAdjuntos() || validaAnexosAdjuntos()) {
            JSFUtils.getFacesContext().addMessage(null,
                                                  new FacesMessage(FacesMessage.SEVERITY_WARN, ConstantesArchivo.ARCHIVO_VALIDO,
                                                                   null));
            RequestContext.getCurrentInstance().update("growl");
        } else {
            createSolicitud();
        }
    }

    private boolean validaAnexosAdjuntos() {
        boolean isRequerdio = false;
        if (!mbAdicionarSolicitud.getTramite().getAnexos().isEmpty()) {
            for (ArchivoVO objeto : mbAdicionarSolicitud.getTramite().getAnexos()) {
                if (objeto.getEstado().equals(ConstantesArchivo.EDO_ANEXO)) {
                    isRequerdio = Boolean.TRUE;
                    break;
                }
            }
        }
        return isRequerdio;
    }

    private void documentosComplementarios(int competencia) {
        if (tramite == ConstantesIVAProductos.IVA_NUEVAS_INVERCIONES) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_60);
        } else if (tramite == ConstantesIVAProductos.IVA_MEDICINA) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_62);
        } else if (tramite == ConstantesIVAProductos.IVA_PRODUCTOS_ALIMENTACION) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_63);
        } else if ((tramite == ConstantesIVAProductos.ISR_PERSONAS_FISICAS && competencia == ConstantesDyC.AGAFF) ||
                   tramite == ConstantesIVAProductos.ISR_HIDROCARBUROS) {
            presentaDocumento(ConstantesDyCNumerico.VALOR_7);
        }
    }

    private void presentaDocumento(int idMessage) {
        int numDoc =
            null != mbAdicionarSolicitud.getFlagsSolicitud().getNumDocAdjuntos() ? ConstantesDyCNumerico.VALOR_2 :
            ConstantesDyCNumerico.VALOR_1;
        mbAdicionarSolicitud.getFlagsSolicitud().setPresentaDoc(Boolean.TRUE);
        mbAdicionarSolicitud.getFlagsSolicitud().setNumDocAdjuntos(numDoc);
        showMessages(idMessage, FacesMessage.SEVERITY_INFO);
    }


    private String getMessage(int idMensaje) {
        return messageSolicitud.getMessage(idMensaje, ConstantesMensajes.CU_REGISTRO_SOLDEVLINEA);
    }

    private void showMessages(int idMensaje, Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }


    /** SET and GET  */
    public void setTramite(Integer tramite) {
        this.tramite = tramite;
    }

    public Integer getTramite() {
        return tramite;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setConsultaPadronesSol(ConsultaPadronSolComponent consultaPadronesSol) {
        this.consultaPadronesSol = consultaPadronesSol;
    }

    public ConsultaPadronSolComponent getConsultaPadronesSol() {
        return consultaPadronesSol;
    }

    public void setAcuseReciboService(AcuseReciboService acuseReciboService) {
        this.acuseReciboService = acuseReciboService;
    }

    public AcuseReciboService getAcuseReciboService() {
        return acuseReciboService;
    }

    public void setIcepService(IcepService icepService) {
        this.icepService = icepService;
    }

    public IcepService getIcepService() {
        return icepService;
    }

    public void setDyccInstCreditoService(DyccInstCreditoService dyccInstCreditoService) {
        this.dyccInstCreditoService = dyccInstCreditoService;
    }

    public DyccInstCreditoService getDyccInstCreditoService() {
        return dyccInstCreditoService;
    }

    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setAppletFiel(EjecutaFielMB appletFiel) {
        this.appletFiel = appletFiel;
    }

    public EjecutaFielMB getAppletFiel() {
        return appletFiel;
    }

    public void setDatosContribuyentePage(DatosContribuyenteMB datosContribuyentePage) {
        this.datosContribuyentePage = datosContribuyentePage;
    }

    public DatosContribuyenteMB getDatosContribuyentePage() {
        return datosContribuyentePage;
    }

    public void setShowTab(Integer showTab) {
        this.showTab = showTab;
    }

    public Integer getShowTab() {
        return showTab;
    }


    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public AdicionarSolicitudMB getMbAdicionarSolicitud() {
        return mbAdicionarSolicitud;
    }

    public void setMbAdicionarSolicitud(AdicionarSolicitudMB mbAdicionarSolicitud) {
        this.mbAdicionarSolicitud = mbAdicionarSolicitud;
    }

    public ValidaTramitesService getServiceValidarTramite() {
        return serviceValidarTramite;
    }

    public void setServiceValidarTramite(ValidaTramitesService serviceValidarTramite) {
        this.serviceValidarTramite = serviceValidarTramite;
    }

    public void setMensajeDialogo(String mensajeDialogo) {
        this.mensajeDialogo = mensajeDialogo;
    }

    public String getMensajeDialogo() {
        return mensajeDialogo;
    }
}
