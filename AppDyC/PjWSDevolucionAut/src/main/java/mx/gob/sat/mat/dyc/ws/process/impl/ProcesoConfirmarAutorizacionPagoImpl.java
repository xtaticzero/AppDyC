package mx.gob.sat.mat.dyc.ws.process.impl;

import mx.gob.sat.mat.dyc.ws.process.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import mx.gob.sat.mat.dyc.automaticasiva.service.CrearCasoAutomaticasIvaService;
import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.mat.dyc.devolucionaut.service.DyccDevolucionAutService;
import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.EstadoActualizacion;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConfirAutoPago;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.PreparacionPagoDevAut;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.utils.DeterminadorEstadoDevAutHelper;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteInfoAdicional;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mauricio Lira Lopez
 */
@Component("procesoConfirmarAutorizacionPago")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProcesoConfirmarAutorizacionPagoImpl implements ProcesoConfirmarAutorizacionPago, Serializable {

    private final transient Logger logger;
    private static final long serialVersionUID = -1640285915010893810L;
    private static final String LOG_MENSAJE = "ProcesoConfirmarAutorizacionPagoImpl.serialVersionUID:: ";

    public ProcesoConfirmarAutorizacionPagoImpl() {
        logger = Logger.getLogger(getClass());
    }

    @Autowired
    private transient DyccDevolucionAutService dyccDevolucionAutService;

    @Autowired
    private transient RegistroPistasAuditoria registroPistasAuditoria;

    @Autowired
    private transient AfectarSaldosXDevolucionesService afectarSaldosXDevolucionesService;

    @Autowired
    private transient CrearCasoAutomaticasIvaService crearCasoAutomaticasIvaService;

    @Autowired
    private transient DyccMensajeUsrService dyccMensajeUsrService;

    private NotificacionRegistroYGestion procesaRegistroAut(RegistroDevolucionAut registroDevolucionAut,
            NotificacionRegistroYGestion notificacionRegYPago,
            NotificacionDevManual notificacionRegistro,
            WebServiceContext wsContext)
            throws ServiceException {

        logger.info(LOG_MENSAJE + serialVersionUID + " " + ProcesoConfirmarAutorizacionPagoImpl.class.hashCode());
        logger.info(wsContext);

        //importeResuelto es Remanente
        DeterminadorEstadoDevAutHelper.determinarTipo(registroDevolucionAut, notificacionRegYPago, notificacionRegistro);

        confirmarAutorizacionPagoAut(DeterminadorEstadoDevAutHelper.tipoResFromIdWS(notificacionRegYPago.getTipoDeResolucion()),
                notificacionRegistro.getDatosRegistroMATDYC().getFolioMATDYC(),
                registroDevolucionAut.getDatosDevolucionAutomatica().getImporteSolicitado(),
                notificacionRegYPago.getImporteAutorizado(),
                notificacionRegYPago.getImporteNetoADevolver());

        verificarImportes(notificacionRegYPago, notificacionRegistro);

        //Pendiente completar pistas de auditoria:
        //guardarPistaAuditoria
        //(wsContext, idMensajePagoDev, ConstantesProceso.ID_GRUPO_OPERACION, idMovTipo, registroEntrada)
        return notificacionRegYPago;
    }

    private void verificarImportes(NotificacionRegistroYGestion notificacionRegYPago, NotificacionDevManual notificacionRegistro) throws ServiceException {
        try {
            ControlSaldoImportesDTO controlSaldo = dyccDevolucionAutService.obtenerSaldoImportes(notificacionRegistro.getDatosRegistroMATDYC().getIDICEP().intValue());

            notificacionRegYPago.setRemanenteICEPDespuesDeCargo(controlSaldo.getRemanente());
            notificacionRegYPago.setImporteResueltoDespuesDeCargo(controlSaldo.getResuelto());

        } catch (Exception err) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO RECUPERO EL REMANENTE" + err.getMessage(), err);
        }
    }

    private void confirmarAutorizacionPagoAut(EDycAutomaticasIvaEstadoCasoDevolucion tipoResolucion,
            String numeroControl, BigDecimal importeSolicitado, BigDecimal importeAutorizado, BigDecimal importeNetoADevolver) throws ServiceException {

        try {

            if (tipoResolucion == null) {
                throw new ServiceException(CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(),
                        CodigosDeError.MOTIVO_ERROR_TRAMITE.getDescripcion());
            }

            registrarResolucionAut(tipoResolucion,
                    numeroControl,
                    importeSolicitado,
                    importeAutorizado,
                    importeNetoADevolver);
            registrarMovimientoPagoAut(numeroControl);
            actualizacionStatus(ConstantesProceso.AUTORIZADA_SAD, numeroControl);
        } catch (SIATException e) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_PROBLEMA_APLICAR_CARGO.getCodigo(),
                    CodigosDeError.MOTIVO_ERROR_PROBLEMA_APLICAR_CARGO.getDescripcion(), e);
        }
    }

    public NotificacionConfirAutoPago procesaRegistro(PreparacionPagoDevAut registroEntrada, WebServiceContext wsContext) throws SIATException {
        logger.info(LOG_MENSAJE + serialVersionUID + " " + ProcesoConfirmarAutorizacionPagoImpl.class.hashCode());

        String numeroControl = getFolioMATDYCEntrada(registroEntrada);
        String rfc = getRfcEntrada(registroEntrada);
        Integer icep = getIdSaldoIcep(registroEntrada);

        DycpSolicitudDTO solicitudDTO = dyccDevolucionAutService.existeRegistroSolicitud(numeroControl, rfc, icep);
        boolean isDesistida = getAccion(registroEntrada).equals(ConstantesDyCNumerico.VALOR_1);

        EDycAutomaticasIvaEstadoCasoDevolucion estadoDevolucion = getEstadoDevolucion(isDesistida, registroEntrada.getIdResolucion());

        BigDecimal montoAplicar = isDesistida ? BigDecimal.ZERO : getMontoAplicarEntrada(registroEntrada);
        registrarResolucion(estadoDevolucion, numeroControl, solicitudDTO.getImporteSolicitado(), montoAplicar);
        //no hay desistidas para nuevo metodo
        if (!isDesistida) {
            registrarMovimientoPago(numeroControl);
        }

        //estatus con el que se queda el tramite ConstantesProceso.AUTORIZADA_SAD
        Integer idEstatus = isDesistida ? ConstantesProceso.NEGADA_SAD : ConstantesProceso.AUTORIZADA_SAD;
        actualizacionStatus(idEstatus, numeroControl);

        //Pistas de Auditoria
        int idMensajePagoDev = ConstantesProceso.ID_MENSAJE_PAGO_DEV;
        int idMovTipo = ConstantesProceso.MOV_AUTORIZADA_SAD;
        if (isDesistida) {
            idMensajePagoDev = ConstantesProceso.ID_MENSAJE_RECHAZO_PAGO_DEV;
            idMovTipo = ConstantesProceso.MOV_NEGADA_SAD;
        }

        guardarPistaAuditoria(wsContext, idMensajePagoDev, ConstantesProceso.ID_GRUPO_OPERACION, idMovTipo, registroEntrada);
        EstadoActualizacion estadoActualizacion = new EstadoActualizacion();
        estadoActualizacion.setEstadoActualizacion(BigInteger.valueOf(ConstantesProceso.EXITOSO));

        return preparadaSalida(estadoActualizacion);

    }

    private EDycAutomaticasIvaEstadoCasoDevolucion getEstadoDevolucion(boolean isRechazar, Integer idResolucion) throws SIATException {
        if (isRechazar) {

            return EDycAutomaticasIvaEstadoCasoDevolucion.DESISTIDA;
        } else {

            for (EDycAutomaticasIvaEstadoCasoDevolucion edoDevolucion : EDycAutomaticasIvaEstadoCasoDevolucion.values()) {
                if (edoDevolucion.getIdTipoResol().equals(idResolucion)) {
                    return edoDevolucion;
                }
            }
            throw new SIATException("TIPO DE RESOLUCION INVALIDA " + idResolucion);
        }
    }

    private Integer getAccion(PreparacionPagoDevAut registroEntrada) throws SIATException {
        if (registroEntrada == null
                || registroEntrada.getDatosDevolucionAut() == null
                || registroEntrada.getDatosDevolucionAut().getAccion() == null) {
            throw new SIATException("Accion nula");
        }

        BigInteger accion = registroEntrada.getDatosDevolucionAut().getAccion();
        if (!(accion.equals(BigInteger.ZERO) || accion.equals(BigInteger.ONE))) {
            throw new SIATException("Accion no valida " + accion);
        }
        return accion.intValue();
    }

    private String getFolioMATDYCEntrada(PreparacionPagoDevAut registroEntrada) {
        if (registroEntrada == null
                || registroEntrada.getDatosRegistroMATDYC() == null) {
            return null;
        }

        return registroEntrada.getDatosRegistroMATDYC().getFolioMATDYC();
    }

    private void actualizacionStatus(Integer estatus, String numeroControl) throws SIATException {
        dyccDevolucionAutService.actualizaEstatus(estatus, numeroControl);
    }

    private void guardarPistaAuditoria(WebServiceContext wsContext, int idMensaje, int idGrupoMensaje, int idMovimiento, PreparacionPagoDevAut registroEntrada) {

        try {

            HttpSession httpSession = getSession(wsContext);
            String mensaje = getMensajePistaAuditoria(idMensaje, idGrupoMensaje, registroEntrada);

            SegbMovimientoDTO movimientoDTO = getMovimientoPistaAuditoria(httpSession, mensaje, idMovimiento, registroEntrada);

            registroPistasAuditoria.registrarPistaAuditoria(movimientoDTO);

        } catch (AccesoDenegadoException error) {
            logger.error(error.getMessage());
        } catch (SIATException error) {
            logger.error(error.getMessage());
        }

    }

    private HttpSession getSession(WebServiceContext wsContext) {
        HttpSession httpSession;

        httpSession = ((HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST))
                .getSession();

        AccesoUsr accesoUsr = llenaSessionDummy();

        httpSession.setAttribute("accesoEF", accesoUsr);
        httpSession.setAttribute(ConstanteInfoAdicional.USUARIO, accesoUsr.getUsuario());
        httpSession.setAttribute("roles", accesoUsr.getRoles());
        httpSession.setAttribute("portal", "accesoEF");

        return httpSession;
    }

    private String getMensajePistaAuditoria(int idMensaje, int idGrupoMensaje, PreparacionPagoDevAut registroEntrada) throws SIATException {
        DyccMensajeUsrDTO mensajeUsr = dyccMensajeUsrService.obtieneMensaje(idMensaje, idGrupoMensaje, ConstantesProceso.BITACORA);

        String mensaje = mensajeUsr.getDescripcion();
        String rfc = registroEntrada.getDatosContribuyente().getRFC();
        String numeroControl = registroEntrada.getDatosRegistroMATDYC().getFolioMATDYC();
        BigDecimal importeSaldoFavor = registroEntrada.getDatosDevolucionAut().getMontoAplicar();

        mensaje = mensaje.replace("<Número de Control>", numeroControl)
                .replace("<Importe saldo a favor>", importeSaldoFavor.toString())
                .replace("<RFC del contribuyente>", rfc);

        return mensaje;
    }

    private SegbMovimientoDTO getMovimientoPistaAuditoria(HttpSession httpSession, String mensaje, int idMovimiento, PreparacionPagoDevAut registroEntrada) throws AccesoDenegadoException {

        String rfc = getRfcEntrada(registroEntrada);

        SegbMovimientoDTO movimientoDTO = MovimientoUtil.addMovimiento(
                httpSession,
                ConstantesDyC1.CLAVE_SYS,
                Procesos.DYC00106,
                new Date(),
                new Date(),
                idMovimiento,
                mensaje
        );

        movimientoDTO.setIdentificador(rfc);

        return movimientoDTO;
    }

    private AccesoUsr llenaSessionDummy() {
        AccesoUsr accesoUsr = new AccesoUsr();
        Random ran = new Random();

        accesoUsr.setClaveAdminCentral("9000000000");
        accesoUsr.setLocalidad("5620010602");
        accesoUsr.setUsuario("RAHM580403ME3");
        accesoUsr.setMenu("1");
        accesoUsr.setTipoAuth("4");
        accesoUsr.setTipoAuth("2");

        accesoUsr.setClaveSir("13");
        accesoUsr.setRfcCorto("GOFC786O");
        accesoUsr.setNombreCompleto("WS SIVAD-MORSA DICTAMINACION AUTOMATICA ");
        accesoUsr.setClaveAdminGral("9000000000");

        accesoUsr.setSessionID("1404100" + ran.nextInt(ConstantesDyCNumerico.VALOR_9000000 + 1));
        accesoUsr.setSessionIDNovell("2B9238F207F94C272A325A40EA7952FF");
        accesoUsr.setNumeroEmp("53408");
        accesoUsr.setIdTipoPersona("00");
        accesoUsr.setIp("[lo (Software Loopback Interface 1)=/127.0.0.1][lo (Software Loopback Interface 1)=/0:0:0:0:0:0:0:1][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/99.91.8.68][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/fe80:0:0:0:8cb7:9e8f:c0ce:c501%11][net5 (Adaptador 6to4 de Microsoft)=/2002:635b:844:0:0:0:635b:844][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/10.57.232.16][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/fe80:0:0:0:799c:770b:4c10:6569%18][eth10 (VirtualBox Host-Only Ethernet Adapter)=/192.168.56.1][eth10 (VirtualBox Host-Only Ethernet Adapter)=/fe80:0:0:0:a8e5:c230:89e1:9d8b%22] ");
        accesoUsr.setLocalidadOp("900E040301");
        accesoUsr.setLocalidadCRM("G-96");
        accesoUsr.setNombres("WS SIVAD-MORSA");
        accesoUsr.setPrimerApellido("DICTAMINACION");
        accesoUsr.setRoles("SAT_NYV_ADM_GRL_AC,SAT_DYC_DICT,SAT_DYC_CONS_PISTA_COBR");
        accesoUsr.setSegundoApellido("AUTOMATICA");
        accesoUsr.setUsuarioOficina("ADMON GRAL GRANDES CONTRIB");

        accesoUsr.setCentroCosto("562");
        accesoUsr.setCentroCostoOp("562");
        accesoUsr.setCentroDatos("1");
        accesoUsr.setMac("[lo (Software Loopback Interface 1)=][eth3 (Intel(R) 82579LM Gigabit Network Connection)=90-B1-1C-7C-C0-E0][net5 (Adaptador 6to4 de Microsoft)=00-00-00-00-00-00-00-E0][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=00-05-9A-3C-78-00][eth10 (VirtualBox Host-Only Ethernet Adapter)=08-00-27-00-44-9E] ");

        Map<Integer, String> proceso = new HashMap<Integer, String>();
        proceso.put(ConstantesDyCNumerico.VALOR_136, Procesos.DYC00002);
        proceso.put(ConstantesDyCNumerico.VALOR_137, Procesos.DYC00003);
        proceso.put(ConstantesDyCNumerico.VALOR_130, Procesos.DYC00005);
        proceso.put(ConstantesDyCNumerico.VALOR_132, Procesos.DYC00006);
        proceso.put(ConstantesDyCNumerico.VALOR_722, Procesos.DYC00011);
        proceso.put(ConstantesDyCNumerico.VALOR_116, Procesos.DYC00106);

        accesoUsr.setProcesos(proceso);

        return accesoUsr;
    }

    private Integer getIdSaldoIcep(PreparacionPagoDevAut registroEntrada) {
        if (registroEntrada == null
                || registroEntrada.getDatosRegistroMATDYC() == null
                || registroEntrada.getDatosRegistroMATDYC().getIDICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosRegistroMATDYC().getIDICEP().intValue();
    }

    private String getRfcEntrada(PreparacionPagoDevAut registroEntrada) {
        if (registroEntrada == null
                || registroEntrada.getDatosContribuyente() == null) {

            return null;
        }

        return registroEntrada.getDatosContribuyente().getRFC();
    }

    private BigDecimal getMontoAplicarEntrada(PreparacionPagoDevAut registroEntrada) throws SIATException {
        if (registroEntrada == null
                || registroEntrada.getDatosDevolucionAut() == null
                || registroEntrada.getDatosDevolucionAut().getMontoAplicar() == null) {
            throw new SIATException("El monto aplicar no puede ser nulo");
        }

        return registroEntrada.getDatosDevolucionAut().getMontoAplicar();
    }

    private void registrarResolucion(EDycAutomaticasIvaEstadoCasoDevolucion estadoDevolucion, String numeroControl, BigDecimal importeSolicitado, BigDecimal montoAplicar) throws SIATException {
        try {

            crearCasoAutomaticasIvaService.insertarResolucion(
                    numeroControl,
                    new Date(),
                    new Date(),
                    importeSolicitado,
                    estadoDevolucion,
                    montoAplicar
            );

        } catch (DycServiceExcepcion ex) {
            logger.error(ex.getMessage(), ex);
            throw new SIATException("ERROR AL INSERTAR RESOLUCION", ex);
        }

    }

    private void registrarResolucionAut(EDycAutomaticasIvaEstadoCasoDevolucion estadoDevolucion, String numeroControl, BigDecimal importeSolicitado, BigDecimal montoAutorizado, BigDecimal montoNeto) throws SIATException {
        try {

            crearCasoAutomaticasIvaService.insertarResolucionAut(
                    numeroControl,
                    new Date(),
                    new Date(),
                    importeSolicitado,
                    estadoDevolucion,
                    montoAutorizado,
                    montoNeto
            );

        } catch (DycServiceExcepcion ex) {
            logger.error(ex.getMessage(), ex);
            throw new SIATException("ERROR AL INSERTAR RESOLUCION", ex);
        }

    }

    private void registrarMovimientoPago(String numeroControl) throws SIATException {
        try {
            afectarSaldosXDevolucionesService.afectarXResolucionDevAutomaticasIva(numeroControl);
        } catch (SIATException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SIATException("ERROR  EL MOVIMIENTO PAGO", ex);
        }
    }

    private void registrarMovimientoPagoAut(String numeroControl) throws SIATException {
        try {
            afectarSaldosXDevolucionesService.afectarXResolucionDevAutomaticasWS(numeroControl);
        } catch (SIATException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SIATException("ERROR  EL MOVIMIENTO PAGO", ex);
        }
    }

    private NotificacionConfirAutoPago preparadaSalida(EstadoActualizacion estadoActualizacion) {
        NotificacionConfirAutoPago registroSalida = new NotificacionConfirAutoPago();
        registroSalida.setEstadoActualizacion(estadoActualizacion);
        return registroSalida;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public NotificacionConfirAutoPago execute(PreparacionPagoDevAut preparacionPagoDevAut, WebServiceContext wsContext) throws SIATException {
        logger.info("ProcesoConfirmarAutorizacionPagoImpl" + toString());
        return procesaRegistro(preparacionPagoDevAut, wsContext);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public NotificacionRegistroYGestion execute(RegistroDevolucionAut registroDevolucionAut, NotificacionRegistroYGestion notificacionRegistroYPago, NotificacionDevManual notificacionRegistro, WebServiceContext wsContext) throws ServiceException {
        return procesaRegistroAut(registroDevolucionAut, notificacionRegistroYPago, notificacionRegistro, wsContext);
    }

}
