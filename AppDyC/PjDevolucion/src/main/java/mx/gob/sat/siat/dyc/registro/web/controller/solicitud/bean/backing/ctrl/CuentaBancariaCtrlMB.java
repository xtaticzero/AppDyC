package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.ctrl;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInstCreditoService;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import static mx.gob.sat.siat.dyc.generico.util.Utils.muestraMensaje;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.EjecutaFielMB;
import mx.gob.sat.siat.dyc.registro.dto.InfoCuentaClabeFieldDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidadorRNRegistro;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.CuantaBancariaMB;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.ReemplazaCuentaBancariaMB;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.service.DyctCuentaBancoService;
import mx.gob.sat.siat.dyc.service.DyctExpedienteService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoArchivo;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author JEFG Actualizo Luis Alberto Dominguez Palomino [LADP]
 * @since 14/03/14
 */
@ManagedBean(name = "cuentaBancariaCtrl")
@SessionScoped
public class CuentaBancariaCtrlMB {

    private static final Logger LOG = Logger.getLogger(CuentaBancariaCtrlMB.class.getName());
    private static final String ACTUALIZAR_CUANTA_BANCARIA_PAGE = "informacionCuentaClabe";
    @ManagedProperty(value = "#{dyctCuentaBancoService}")
    private DyctCuentaBancoService dyctCuentaBancoService;

    /**
     * Managed Bean para la consulta de la actualizacion de cuenta bancaria clabe
     */
    @ManagedProperty(value = "#{cuentaBancariaMB}")
    private CuantaBancariaMB cuantaBancariaPage;

    /**
     * Manages Bena para la consulta de las cuentas por TESOFE
     */
    @ManagedProperty(value = "#{reemplazaCuentaBancariaMB}")
    private ReemplazaCuentaBancariaMB reemplazaCuentaBancariaMB;

    /**
     * Componenete que nos permite realiza el adgoritmo para cuenta clabe sea correcta
     */
    @ManagedProperty("#{validadorRNRegistro}")
    private ValidadorRNRegistro crnPF;

    /**
     * Servicio y objetos que nos ayuda consultar los mensajes pertienentes para
     * este componenete
     */
    @ManagedProperty(value = "#{dyccMensajeUsrService}")
    private DyccMensajeUsrService dyccMensajeUsrService;
    private Mensaje mensaje;
    private DyccMensajeUsrDTO mensajeUsr = new DyccMensajeUsrDTO();

    /**
     * Servicio que nos consulta las instituciones bancarias existentes
     */
    @ManagedProperty(value = "#{dyccInstCreditoService}")
    private DyccInstCreditoService dyccInstCreditoService;

    /**
     * Servicio que afecta a expediente por campos manifiestaedocta
     */
    @ManagedProperty(value = "#{dyctExpedienteService}")
    private DyctExpedienteService dyctExpedienteService;

    @ManagedProperty(value = "#{ejecutaFIELMB}")
    private EjecutaFielMB ejecutaFielMB;

    /**
     * Objeto que ayuda a recuperar la informacion elegida
     */
    private ReqCuentaClabeVO selectedPrueba = new ReqCuentaClabeVO();

    /**
     * Objeto que ayuda a mostrar la informacion a remplazar que son las
     * aceptadas por tesofe
     */
    private RemplazaCuentaClabeVO remplazaCuentaClabeVO;

    /**
     * Objeto que ayuda a recuperar informacion para realizar actualizacion, consultas
     */
    private DyctCuentaBancoDTO dyctCuentaBancoDTO = new DyctCuentaBancoDTO();

    /**
     * objeto que es para recuperar la informacion de instituciones bancarias
     */
    private DyccInstCreditoDTO dyccInstCreditoDTO = new DyccInstCreditoDTO();

    /**
     * Muestra la lista de las instituciones disponibles private
     * List<DyccInstCreditoDTO> listaInstitucion = new ArrayList();
     */
    /**
     * Fechas del dialog principal
     */
    private String fechaInicial;
    private String fechaActualizacion;

    /**
     * Variable para estado de cuentas validas en la lista
     */
    private String estadoCuentaList = ConstantesDyC3.ESTADOCUENTAVALIDAS;
    private String estadoCuentaA;

    /**
     * Constantes para actualizar el componenete de mensajes unicos y principal
     */
    private String idMensaje = "msg";
    private String idMensajeT = "msgT";
    private String adjuntaRem = "adjuntaRempla";
    private String formularioMensaje = "formulario:msg";

    /**
     * Constantes para el mensaje pertinente para este componente
     */
    private Integer idCaso = ConstantesDyCNumerico.VALOR_60;
    private Integer tipoMensaje = ConstantesIds.MSG_1;

    /**
     * Objetos y servicios para poder subir el archivo y actualizar su ruta,
     * descripcion y nombre
     */
    @ManagedProperty(value = "#{dyctArchivoService}")
    private DyctArchivoService dyctArchivoService;
    private UploadedFile file;
    private DyctArchivoDTO dyctArchivo = new DyctArchivoDTO();
    @ManagedProperty(value = "#{registraSolDevService}")
    private RegistraSolDevService registraSolDevService;

    /**
     * Para revisar si estpa amparado
     */
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{solventarRequerimientoService}")
    private SolventarRequerimientoService solventarRequerimientoService;

    /**
     * Booleanos para habilitar opciones en los componentes del jsf
     */
    private Boolean opcionEstadoCta;
    private Boolean opcionAdjuntaZip;
    private Boolean opcionConformidad;
    private Boolean visibleFile;
    private Boolean botonInfoaActualizar;
    private Boolean botonActuaInfo;
    private Boolean botonRemplazarCuenta;
    private String checBoxConformidad;
    private String textoMSG10;
    private String textoMSG8;
    private String numControl;
    private String nomCorrecto;

    private Integer idArchivoNumCtrlSelccion;

    public CuentaBancariaCtrlMB() {
        mensaje = new Mensaje();
    }

    public void pruebaButtonR() {
        this.getReqCuantasClabe();
    }

    @PostConstruct
    public void init() {
        this.rfc();
        getReqCuantasClabe();
        List<RemplazaCuentaClabeVO> cuentasTESOFE = dyctCuentaBancoService.consultaCuentaClabeXPagoTesofe(rfc());
        botonRemplazarCuenta = cuentasTESOFE.size() == ConstantesDyCNumerico.VALOR_0 ? Boolean.TRUE : false;
    }

    public void prerenderView() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            AccesoUsr au = serviceObtenerSesion.execute();
            if (registraSolDevService.desdeTramitesYNoEstaAmparado(au.getUsuario())) {
                muestraMensaje("Sustituci\u00F3n de cuenta CLABE por devoluci\u00F3n no pagada");
            }
        }
    }

    /**
     * --Metodo que consulta los registro del contribuyente y con id de
     * plantilla 22,61--
     */
    public String getReqCuantasClabe() {
        cuantaBancariaPage.setDataModel(new SIATDataModel<Serializable>());
        List<ReqCuentaClabeVO> reqCuentasClabe = new ArrayList<ReqCuentaClabeVO>();
        try {
            reqCuentasClabe = dyctCuentaBancoService.getReqCuentasCLABE(rfc()).getOutputs();
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            JSFUtils.messageGlobal("ERROR EN CAT\u00c1LOGO ", FacesMessage.SEVERITY_FATAL);
        }
        cuantaBancariaPage.getDataModel().setWrappedData(reqCuentasClabe);
        return ACTUALIZAR_CUANTA_BANCARIA_PAGE;
    }

    /**
     * --Metodo que obtiene la cuenta cable del contribuyente para mostrar en
     * panel principal--
     */
    public String reemplazarCuenta() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String paginaR = " ";

        try {
            if (selectedPrueba != null) {
                RemplazaCuentaClabeVO cuentaClabeVO
                        = dyctCuentaBancoService.consultaNumCtrlTramite(selectedPrueba.getNumControl());

                DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
                DyctArchivoDTO archivoDTO = new DyctArchivoDTO();
                solicitud.setNumControl(selectedPrueba.getNumControl());
                dyctCuentaBancoDTO.setDycpSolicitudDTO(solicitud);
                dyctCuentaBancoDTO.setClabe(cuentaClabeVO.getCuenta());
                dyccInstCreditoDTO.setDescripcion(cuentaClabeVO.getBanco());
                dyctCuentaBancoDTO.setDyccInstCreditoDTO(dyccInstCreditoDTO);
                dyctCuentaBancoDTO.setFechaRegistro(cuentaClabeVO.getFechaRegistro());
                dyctCuentaBancoDTO.setFechaUltimaMod(cuentaClabeVO.getFechaUltima());
                archivoDTO.setIdArchivo(cuentaClabeVO.getIdArchivo());
                dyctCuentaBancoDTO.setDyctArchivoDTO(archivoDTO);
                dyctCuentaBancoDTO.setCuentaValida(cuentaClabeVO.getCuentaValida());
                if (dyctCuentaBancoDTO.getCuentaValida() == ConstantesDyCNumerico.VALOR_0) {
                    estadoCuentaA = ConstantesDyC3.ESTADOCUENTAVALIDAN;
                }

                reemplazaCuentaBancariaMB.setDataModel(new SIATDataModel<Serializable>());
                List<RemplazaCuentaClabeVO> listaCuentasTESOFE = new ArrayList();
                try {
                    listaCuentasTESOFE = dyctCuentaBancoService.consultaCuentaClabeXPagoTesofe(rfc());
                } catch (Exception siatE) {
                    LOG.error(siatE.getMessage());
                    JSFUtils.messageGlobal("ERROR EN CATALOGO " + siatE.getCause(), FacesMessage.SEVERITY_FATAL);
                }
                reemplazaCuentaBancariaMB.getDataModel().setWrappedData(listaCuentasTESOFE);
                requestContext.update("formRemplaza:panelInfo");
                botonInfoaActualizar = Boolean.TRUE;
                botonActuaInfo = Boolean.TRUE;

                paginaR = "remplazarCuentaBancaria.jsf";
            } else {
                mensajeUsr.setDescripcion("Debes seleccionar una opción");
                mensaje.addError(idMensaje, mensajeUsr.getDescripcion());
                requestContext.update(formularioMensaje);
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        return paginaR;
    }

    /**
     * Metodo que muestra el panel dialog correspondiente ala opcion actualizar
     * cuenta bancaria mostrando datos predefinidos de la eleccion del numero de control--
     */
    public void botonOpcionInsertarActualizar() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        DycpSolicitudDTO dycpSolicitudDTO = new DycpSolicitudDTO();
        try {
            if (selectedPrueba == null) {
                mensajeUsr.setDescripcion("Debes seleccionar una opción");
                mensaje.addError(idMensaje, mensajeUsr.getDescripcion());
                requestContext.update(formularioMensaje);
            } else {
                requestContext.update(formularioMensaje);
                requestContext.update("formActual:dlgActua");
                /**
                 * requestContext.execute("wgdActua.show()");
                 */
                visibleFile = Boolean.TRUE;
                dyctCuentaBancoDTO.setClabe(selectedPrueba.getCuentaClabe());
                opcionEstadoCta = false;
                opcionAdjuntaZip = false;
                checBoxConformidad = "";
                dycpSolicitudDTO.setNumControl(selectedPrueba.getNumControl());
                dyctCuentaBancoDTO.setDycpSolicitudDTO(dycpSolicitudDTO);
                dyccInstCreditoDTO.setIdInstCredito(selectedPrueba.getIdInstitucion());
                DyctCuentaBancoDTO consulCuenta;

                consulCuenta = dyctCuentaBancoService.consultaXNumCtrl(dyctCuentaBancoDTO);
                ParamOutputTO<DyccInstCreditoDTO> institucion
                        = dyccInstCreditoService.getInstitucion(consulCuenta.getDyccInstCreditoDTO().getIdInstCredito());
                dyccInstCreditoDTO.setDescripcion(institucion.getOutput().getDescripcion());

                fechaInicial = new SimpleDateFormat("dd/MM/yyy").format(consulCuenta.getFechaRegistro());
                fechaActualizacion = new SimpleDateFormat("dd/MM/yyy").format(consulCuenta.getFechaUltimaMod());

            }
        } catch (SIATException e) {
            LOG.info(e.getMessage());
        }
    }

    /**
     * --Metodo del proceso de actualizacion de la cuenta clabe sobre ese numero
     * de control--
     */
    public void accionesCuentaBanco() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String opcionBot = params.get("opcionBoton");

        if (opcionBot.equals("actualizarInfo") || opcionBot.equals("actualizarRempla")) {
            try {
                if (opcionBot.equals("actualizarRempla")) {
                    dyccInstCreditoDTO
                            = dyccInstCreditoService.institucionXDescripcion(dyctCuentaBancoDTO.getDyccInstCreditoDTO().getDescripcion());
                }

                LOG.info("Banco id ---> " + dyccInstCreditoDTO.getIdInstCredito());
                dyctCuentaBancoDTO.setDyccInstCreditoDTO(dyccInstCreditoDTO);
                LOG.info("Banco clabe --> " + dyctCuentaBancoDTO.getClabe());

                if (crnPF.rn470(dyctCuentaBancoDTO.getClabe())
                        && crnPF.getIdBancario() == dyctCuentaBancoDTO.getDyccInstCreditoDTO().getIdInstCredito()) {
                    boolean valida
                            = crnPF.validaClabeRFC(dyctCuentaBancoDTO.getClabe(), cuantaBancariaPage.getUsuario().getUsuario());
                    continuaAccionBanco(valida, opcionBot);
                } else {
                    mensajeUsr = dyccMensajeUsrService.obtieneMensaje(ConstantesIds.MSG_2, idCaso, tipoMensaje);
                    mensaje.addError(idMensajeT, mensajeUsr.getDescripcion());
                }
            } catch (SIATException e) {
                LOG.info(e.getMessage());
            }
        } else {
            requestContext.execute("wgdActua.hide();");
            file = null;
            dyctArchivo.setNombreArchivo("");
            visibleFile = false;
        }
    }

    private void continuaAccionBanco(Boolean valida, String opcionBot) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (!valida) {
                if (opcionBot.equals("actualizarInfo")) {
                    mensajeUsr = dyccMensajeUsrService.obtieneMensaje(ConstantesIds.MSG_5, idCaso, tipoMensaje);
                    mensaje.addWarn(idMensajeT, mensajeUsr.getDescripcion());
                    dyccInstCreditoDTO.setIdInstCredito(0);
                    dyctCuentaBancoDTO.setClabe("");
                } else {
                    mensajeUsr = dyccMensajeUsrService.obtieneMensaje(ConstantesIds.MSG_5, idCaso, tipoMensaje);
                    mensaje.addWarn(idMensaje, mensajeUsr.getDescripcion());
                    requestContext.update("formRemplaza:" + idMensaje);
                }
            } else if (opcionBot.equals("actualizarInfo")) {
                dyctCuentaBancoDTO.setCuentaValida(ConstantesDyCNumerico.VALOR_1);
                visibleFile = Boolean.TRUE;
                file = null;
                dyctArchivo.setNombreArchivo("");
                textoMSG10 = "Debe adjuntar el estado de cuenta para la cuenta CLABE";
                dyctArchivo.setDescripcion("");
                opcionEstadoCta = Boolean.TRUE;
                opcionAdjuntaZip = false;
            } else {
                dyctCuentaBancoService.remplazaCuenta(dyctCuentaBancoDTO);
                mensajeUsr = dyccMensajeUsrService.obtieneMensaje(ConstantesIds.MSG_4, idCaso, tipoMensaje);
                mensaje.addInfo(idMensaje, mensajeUsr.getDescripcion());
                textoMSG10 = "Debe adjuntar el estado de cuenta para la cuenta CLABE";
                dyctArchivo.setDescripcion("");
                opcionEstadoCta = Boolean.TRUE;
                opcionAdjuntaZip = false;
                getReqCuantasClabe();
            }
        } catch (SIATException e) {
            LOG.info(e.getMessage());
        }
    }

    /**
     * --Metodo que muestra la informacion seleccionada para rempazar la cuenta
     * clabe de ese numero de control--
     */
    public void actualizaInfoSeleccionada() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (remplazaCuentaClabeVO == null) {
            mensajeUsr.setDescripcion("Debes seleccionar una opción");
            mensaje.addError(idMensaje, mensajeUsr.getDescripcion());
            requestContext.update("formRemplaza:" + idMensaje);
        } else {
            dyctCuentaBancoDTO.setClabe(remplazaCuentaClabeVO.getCuenta());
            dyccInstCreditoDTO.setDescripcion(remplazaCuentaClabeVO.getBanco());
            dyctCuentaBancoDTO.setDyccInstCreditoDTO(dyccInstCreditoDTO);
            dyctCuentaBancoDTO.setFechaRegistro(remplazaCuentaClabeVO.getFechaRegistro());
            dyctCuentaBancoDTO.setFechaUltimaMod(remplazaCuentaClabeVO.getFechaUltima());
            idArchivoNumCtrlSelccion = remplazaCuentaClabeVO.getIdArchivo();
            dyctCuentaBancoDTO.setCuentaValida(ConstantesDyCNumerico.VALOR_1);
            estadoCuentaA = ConstantesDyC3.ESTADOCUENTAVALIDAS;
            botonActuaInfo = false;
            botonInfoaActualizar = Boolean.TRUE;
            requestContext.update("formRemplaza:panelInfo");
        }
    }

    public String subirArchivo() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String opcionBot = params.get("remplaAdjunta");
        opcionAdjuntaZip = false;

        HttpServletRequest request
                = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        String cabeceraVirus = request.getHeader("X-Content-Scanning");
        LOG.info("cabeceraVirus ->" + cabeceraVirus + "<-");

        if (cabeceraVirus != null) {
            LOG.info("Se detecto virus");
            if (opcionBot.equals(adjuntaRem)) {
                mensaje.addError(idMensaje, "Se detecto virus en el archivo que intenta subir!");
            } else {
                mensaje.addError(idMensajeT, "Se detecto virus en el archivo que intenta subir!");
            }
            return "";
        }

        if (file != null) {
            nomCorrecto = file.getFileName();
            nomCorrecto
                    = nomCorrecto.substring(nomCorrecto.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, nomCorrecto.length());
            String extencion = FilenameUtils.getExtension(nomCorrecto);
            if (!extencion.equals("zip")) {
                file = null;
                if (opcionBot.equals(adjuntaRem)) {
                    mensaje.addError(idMensaje, "El archivo seleccionado debe tener una extención ZIP");
                } else {
                    mensaje.addError(idMensajeT, "El archivo seleccionado debe tener una extención ZIP");
                }
            } else if (file.getSize() > ConstantesDyCNumerico.VALOR_4194304) {
                if (opcionBot.equals(adjuntaRem)) {
                    mensaje.addError(idMensaje,
                            "El archivo" + nomCorrecto + ConstantesTipoArchivo.MENSAJE_DOCUMENTO);
                } else {
                    mensaje.addError(idMensajeT,
                            "El archivo" + nomCorrecto + ConstantesTipoArchivo.MENSAJE_DOCUMENTO);
                }
            } else {
                DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
                numControl = selectedPrueba.getNumControl();
                solicitud.setNumControl(numControl);
                dyctCuentaBancoDTO.setDycpSolicitudDTO(solicitud);

                continuidadSubirArchivo();
            }
        } else {
            mensaje.addError(idMensajeT,
                    "El archivo no existe o no está disponible. Verifique el archivo o intente de nuevo.");
            opcionConformidad = false;
            opcionAdjuntaZip = false;
        }
        return " ";
    }

    private void continuidadSubirArchivo() {
        textoMSG8
                = "Manifiesto bajo protesta de decir verdad, que soy titular de la cuenta CLABE indicada, " + "por lo que autorizo al Servicio de Administración Tributaria para que la verifique ante la institución de "
                + "crédito correspondiente, a fin de que esa autoridad pueda efectuar el depósito de la devolución en caso de  ser autorizada.";
        opcionAdjuntaZip = false;
        opcionConformidad = Boolean.TRUE;
    }

    public void llamadoApplet() throws SIATException {
        try {
            Map<String, String> params
                    = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String opcionBot = params.get("conformiSI");

            SolventacionRequerimientoVO objetoSolventar = null;

            String adminD = numControl.substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_4);
            String rfc = this.rfc();

            InfoCuentaClabeFieldDTO infoCuenta = new InfoCuentaClabeFieldDTO();
            infoCuenta.setAdminD(adminD);
            infoCuenta.setRfc(rfc);
            infoCuenta.setDyctCuentaBanco(dyctCuentaBancoDTO);
            infoCuenta.setNomCorrecto(nomCorrecto);
            infoCuenta.setFile(file);
            infoCuenta.setOpcionMani(opcionBot);

            //Llenar objeto solventación
            objetoSolventar = new SolventacionRequerimientoVO();
            objetoSolventar.setNumControl(numControl);
            objetoSolventar.setNumControlDoc(solventarRequerimientoService.consultaDocCuentaClabeXSolventar(numControl).getNumControlDoc());
            objetoSolventar.setIdEstadoReq(ConstantesDyCNumerico.VALOR_5);
            ejecutaFielMB.setDatosSolventarRequerimiento(cuantaBancariaPage.getUsuario(), objetoSolventar,
                    objetoSolventar.getNumControlDoc());

            ejecutaFielMB.setDatosCuentaClabe(ConstantesDyCNumerico.VALOR_5, infoCuenta,
                    cuantaBancariaPage.getUsuario());
            cancelarConformidad();
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.LOADINGBAR_HIDE);
            FacesContext.getCurrentInstance().getExternalContext().redirect("../gestionsol/firmaFIEL.jsf");

        } catch (Exception e) {
            throw new SIATException("Hubo un erro al actualizar la cuenta clabe, favor de intentarlo mas tarde.", e);
            /**
             * mensaje.addInfo(idMensaje, "Hubo un erro al actualizar la cuenta
             * clabe, favor de intentarlo mas tarde.");
             */
        }
    }

    public void cancelarConformidad() {
        opcionConformidad = false;
        opcionAdjuntaZip = false;
        opcionEstadoCta = false;
        visibleFile = false;
    }

    /**
     * --Muestra dialog para adjuntar estado de cuenta--
     */
    public void dialogAdjuntaZip() {
        opcionEstadoCta = false;
        opcionAdjuntaZip = Boolean.TRUE;
    }

    public void buscaBanco() {
        crnPF.rn470(dyctCuentaBancoDTO.getClabe());
        dyccInstCreditoDTO.setIdInstCredito(crnPF.getIdBancario());
        dyccInstCreditoDTO.setDescripcion(crnPF.getDescripcion());
    }

    public void pruebaBoton(SelectEvent event) {
        botonInfoaActualizar = false;
        botonActuaInfo = Boolean.TRUE;
    }

    private String rfc() {
        return cuantaBancariaPage.getUsuario().getUsuario();
    }

    public void setDyctCuentaBancoService(DyctCuentaBancoService dyctCuentaBancoService) {
        this.dyctCuentaBancoService = dyctCuentaBancoService;
    }

    public DyctCuentaBancoService getDyctCuentaBancoService() {
        return dyctCuentaBancoService;
    }

    public void setCuantaBancariaPage(CuantaBancariaMB cuantaBancariaPage) {
        this.cuantaBancariaPage = cuantaBancariaPage;
    }

    public CuantaBancariaMB getCuantaBancariaPage() {
        return cuantaBancariaPage;
    }

    public void setCrnPF(ValidadorRNRegistro crnPF) {
        this.crnPF = crnPF;
    }

    public ValidadorRNRegistro getCrnPF() {
        return crnPF;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setDyccInstCreditoService(DyccInstCreditoService dyccInstCreditoService) {
        this.dyccInstCreditoService = dyccInstCreditoService;
    }

    public DyccInstCreditoService getDyccInstCreditoService() {
        return dyccInstCreditoService;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setDyctCuentaBancoDTO(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        this.dyctCuentaBancoDTO = dyctCuentaBancoDTO;
    }

    public DyctCuentaBancoDTO getDyctCuentaBancoDTO() {
        return dyctCuentaBancoDTO;
    }

    public void setSelectedPrueba(ReqCuentaClabeVO selectedPrueba) {
        this.selectedPrueba = selectedPrueba;
    }

    public ReqCuentaClabeVO getSelectedPrueba() {
        return selectedPrueba;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setDyccInstCreditoDTO(DyccInstCreditoDTO dyccInstCreditoDTO) {
        this.dyccInstCreditoDTO = dyccInstCreditoDTO;
    }

    public DyccInstCreditoDTO getDyccInstCreditoDTO() {
        return dyccInstCreditoDTO;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setMensajeUsr(DyccMensajeUsrDTO mensajeUsr) {
        this.mensajeUsr = mensajeUsr;
    }

    public DyccMensajeUsrDTO getMensajeUsr() {
        return mensajeUsr;
    }

    public void setIdCaso(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public Integer getIdCaso() {
        return idCaso;
    }

    public void setTipoMensaje(Integer tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public Integer getTipoMensaje() {
        return tipoMensaje;
    }

    public void setRemplazaCuentaClabeVO(RemplazaCuentaClabeVO remplazaCuentaClabeVO) {
        this.remplazaCuentaClabeVO = remplazaCuentaClabeVO;
    }

    public RemplazaCuentaClabeVO getRemplazaCuentaClabeVO() {
        return remplazaCuentaClabeVO;
    }

    public void setReemplazaCuentaBancariaMB(ReemplazaCuentaBancariaMB reemplazaCuentaBancariaMB) {
        this.reemplazaCuentaBancariaMB = reemplazaCuentaBancariaMB;
    }

    public ReemplazaCuentaBancariaMB getReemplazaCuentaBancariaMB() {
        return reemplazaCuentaBancariaMB;
    }

    public void setEstadoCuentaList(String estadoCuentaList) {
        this.estadoCuentaList = estadoCuentaList;
    }

    public String getEstadoCuentaList() {
        return estadoCuentaList;
    }

    public void setEstadoCuentaA(String estadoCuentaA) {
        this.estadoCuentaA = estadoCuentaA;
    }

    public String getEstadoCuentaA() {
        return estadoCuentaA;
    }

    /**
     * public void setListaInstitucion(List<DyccInstCreditoDTO>
     * listaInstitucion) { this.listaInstitucion = listaInstitucion; }
     *
     * public List<DyccInstCreditoDTO> getListaInstitucion() { return
     * listaInstitucion;
    }
     */
    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setFormularioMensaje(String formularioMensaje) {
        this.formularioMensaje = formularioMensaje;
    }

    public String getFormularioMensaje() {
        return formularioMensaje;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setVisibleFile(Boolean visibleFile) {
        this.visibleFile = visibleFile;
    }

    public Boolean getVisibleFile() {
        return visibleFile;
    }

    public void setDyctArchivo(DyctArchivoDTO dyctArchivo) {
        this.dyctArchivo = dyctArchivo;
    }

    public DyctArchivoDTO getDyctArchivo() {
        return dyctArchivo;
    }

    public void setOpcionEstadoCta(Boolean opcionEstadoCta) {
        this.opcionEstadoCta = opcionEstadoCta;
    }

    public Boolean getOpcionEstadoCta() {
        return opcionEstadoCta;
    }

    public void setBotonInfoaActualizar(Boolean botonInfoaActualizar) {
        this.botonInfoaActualizar = botonInfoaActualizar;
    }

    public Boolean getBotonInfoaActualizar() {
        return botonInfoaActualizar;
    }

    public void setBotonActuaInfo(Boolean botonActuaInfo) {
        this.botonActuaInfo = botonActuaInfo;
    }

    public Boolean getBotonActuaInfo() {
        return botonActuaInfo;
    }

    public void setDyctArchivoService(DyctArchivoService dyctArchivoService) {
        this.dyctArchivoService = dyctArchivoService;
    }

    public DyctArchivoService getDyctArchivoService() {
        return dyctArchivoService;
    }

    public void setIdArchivoNumCtrlSelccion(Integer idArchivoNumCtrlSelccion) {
        this.idArchivoNumCtrlSelccion = idArchivoNumCtrlSelccion;
    }

    public Integer getIdArchivoNumCtrlSelccion() {
        return idArchivoNumCtrlSelccion;
    }

    public void setTextoMSG10(String textoMSG10) {
        this.textoMSG10 = textoMSG10;
    }

    public String getTextoMSG10() {
        return textoMSG10;
    }

    public void setOpcionAdjuntaZip(Boolean opcionAdjuntaZip) {
        this.opcionAdjuntaZip = opcionAdjuntaZip;
    }

    public Boolean getOpcionAdjuntaZip() {
        return opcionAdjuntaZip;
    }

    public void setOpcionConformidad(Boolean opcionConformidad) {
        this.opcionConformidad = opcionConformidad;
    }

    public Boolean getOpcionConformidad() {
        return opcionConformidad;
    }

    public void setTextoMSG8(String textoMSG8) {
        this.textoMSG8 = textoMSG8;
    }

    public String getTextoMSG8() {
        return textoMSG8;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setChecBoxConformidad(String checBoxConformidad) {
        this.checBoxConformidad = checBoxConformidad;
    }

    public String getChecBoxConformidad() {
        return checBoxConformidad;
    }

    public void setDyctExpedienteService(DyctExpedienteService dyctExpedienteService) {
        this.dyctExpedienteService = dyctExpedienteService;
    }

    public DyctExpedienteService getDyctExpedienteService() {
        return dyctExpedienteService;
    }

    public void setIdMensajeT(String idMensajeT) {
        this.idMensajeT = idMensajeT;
    }

    public String getIdMensajeT() {
        return idMensajeT;
    }

    public void setBotonRemplazarCuenta(Boolean botonRemplazarCuenta) {
        this.botonRemplazarCuenta = botonRemplazarCuenta;
    }

    public Boolean getBotonRemplazarCuenta() {
        return botonRemplazarCuenta;
    }

    public void setEjecutaFielMB(EjecutaFielMB ejecutaFielMB) {
        this.ejecutaFielMB = ejecutaFielMB;
    }

    public EjecutaFielMB getEjecutaFielMB() {
        return ejecutaFielMB;
    }

    public void setNomCorrecto(String nomCorrecto) {
        this.nomCorrecto = nomCorrecto;
    }

    public String getNomCorrecto() {
        return nomCorrecto;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public String getAdjuntaRem() {
        return adjuntaRem;
    }

    public void setAdjuntaRem(String adjuntaRem) {
        this.adjuntaRem = adjuntaRem;
    }

    public RegistraSolDevService getRegistraSolDevService() {
        return registraSolDevService;
    }

    public void setRegistraSolDevService(RegistraSolDevService registraSolDevService) {
        this.registraSolDevService = registraSolDevService;
    }

    public SolventarRequerimientoService getSolventarRequerimientoService() {
        return solventarRequerimientoService;
    }

    public void setSolventarRequerimientoService(SolventarRequerimientoService solventarRequerimientoService) {
        this.solventarRequerimientoService = solventarRequerimientoService;
    }

}
