package mx.gob.sat.mat.dyc.ws.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import mx.gob.sat.mat.dyc.ws.DevolucionDelegate;
import mx.gob.sat.mat.dyc.ws.domain.*;
import mx.gob.sat.mat.dyc.ws.process.ProcesoConfirmarAutorizacionPago;
import mx.gob.sat.mat.dyc.ws.process.ProcesoConsultarExistenciaSolDev;
import mx.gob.sat.mat.dyc.ws.process.ProcesoDictaminadorAut;
import mx.gob.sat.mat.dyc.ws.process.ProcesoVerificaDevManual;

import mx.gob.sat.mat.dyc.ws.utils.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import mx.gob.sat.mat.dyc.devolucionaut.service.WsAutomaticaService;
import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.DatosRegistroMATDYC;
import mx.gob.sat.mat.dyc.ws.domain.DatosSolicitud;
import mx.gob.sat.mat.dyc.ws.domain.EstadoActualizacion;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.process.ProcesoRepcionYGestTramDevAutISR;
import mx.gob.sat.mat.dyc.ws.utils.DateLoggerUtil;
import mx.gob.sat.mat.dyc.ws.utils.TipoTramiteEnum;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctWsAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Mauricio Lira Lopez
 */
@Component("devolucionDelegate")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DevolucionDelegateImpl implements DevolucionDelegate, Serializable {

    private static final Logger LOGGER = Logger.getLogger(DevolucionDelegateImpl.class);
    private static final long serialVersionUID = -2529869315111812859L;
    private static final String LOG_MENSAJE = "DevolucionDelegateImpl.serialVersionUID:: ";

    @Autowired
    private ProcesoVerificaDevManual procesoVerificaDevManual;

    @Autowired
    private ProcesoDictaminadorAut procesoDictaminadorAut;

    @Autowired
    private ProcesoConfirmarAutorizacionPago procesoConfirmarAutorizacionPago;

    @Autowired
    private ProcesoConsultarExistenciaSolDev procesoConsultarExistenciaSolDev;

    @Autowired
    private transient WsAutomaticaService wsAutomaticaService;

    @Autowired
    @Qualifier("procesoRepcionYGestTramDevAutISR")
    private ProcesoRepcionYGestTramDevAutISR procesoRepcionYGestTramDevAutISR;

    @Override
    public NotificacionDevManual procesarDictaminadorAut(DevolucionManual devolucionManual, WebServiceContext wsContext) {
        LOGGER.info(LOG_MENSAJE + serialVersionUID + " " + toString());

        NotificacionDevManual registroSalida;
        try {
            //Reg. Aut.
            registroSalida = procesoDictaminadorAut.execute(devolucionManual, TipoTramiteEnum.PROCESO_DICTAMINADOR_AUT);
        } catch (ServiceException e) {
            registroSalida = new NotificacionDevManual();
            registroSalida.setDatosRegistroMATDYC(new DatosRegistroMATDYC());

            DatosSolicitud informacionError = new DatosSolicitud();
            informacionError.setEstadoRegistro(BigInteger.valueOf(Long.parseLong(e.getFaultInfo().getFaultCode())));
            informacionError.setMotivo(BigInteger.valueOf(Long.parseLong(e.getFaultInfo().getMotivoCode())));
            registroSalida.setDatosSolicitud(informacionError);
            guardarBitacora(wsContext, registroSalida, e.getFaultInfo().getFaultString(), ConstantesProceso.FA1);
            LOGGER.error(e.getFaultInfo().getFaultString());
        } catch (Exception ex) {
            registroSalida = new NotificacionDevManual();
            registroSalida.setDatosRegistroMATDYC(new DatosRegistroMATDYC());

            DatosSolicitud informacionError = new DatosSolicitud();
            informacionError.setEstadoRegistro(BigInteger.valueOf(ConstantesProceso.NO_EXITOSO));
            informacionError.setMotivo(BigInteger.valueOf(ConstantesProceso.NO_EXITOSO));
            registroSalida.setDatosSolicitud(informacionError);
            guardarBitacora(wsContext, registroSalida, ex.getMessage(), ConstantesProceso.FA1);
            LOGGER.error(ex.getMessage());
        }
        return registroSalida;

    }

    @Override
    public NotificacionConfirAutoPago procesarAutorizacionPago(PreparacionPagoDevAut preparacionPagoDevAut, WebServiceContext wsContext) {
        LOGGER.info(LOG_MENSAJE + serialVersionUID + " " + toString());
        NotificacionConfirAutoPago registroSalida;
        try {
            //Confirmacion de Pago esta es base
            registroSalida = procesoConfirmarAutorizacionPago.execute(preparacionPagoDevAut, wsContext);
        } catch (Exception ex) {
            EstadoActualizacion estadoActualizacion = new EstadoActualizacion();
            estadoActualizacion.setEstadoActualizacion(BigInteger.valueOf(ConstantesProceso.NO_EXITOSO));
            registroSalida = new NotificacionConfirAutoPago();
            registroSalida.setEstadoActualizacion(estadoActualizacion);
            guardarBitacora(wsContext, registroSalida, ex.getMessage(), ConstantesProceso.FA2);
            LOGGER.error(ex.getMessage());
        }
        return registroSalida;

    }

    @Override
    public NotificacionVeriDevManual procesarVerificaDevManual(RegistroDevolucionAut registroDevolucionAut, WebServiceContext wsContext) {
        LOGGER.info(LOG_MENSAJE + serialVersionUID + " " + toString());
        NotificacionVeriDevManual registroSalida;
        try {
            registroSalida = procesoVerificaDevManual.execute(registroDevolucionAut, wsContext);
        } catch (ServiceException e) {
            int estado = Integer.parseInt(e.getFaultInfo().getFaultCode());
            int motivo = Integer.parseInt(e.getFaultInfo().getMotivoCode());
            registroSalida = new NotificacionVeriDevManual();
            registroSalida.setDatosRegistroMATDYC(new DatosRegistroMATDYC());
            DatosSolicitud datosSolicitud = new DatosSolicitud();
            datosSolicitud.setEstadoRegistro(BigInteger.valueOf(estado));
            datosSolicitud.setMotivo(BigInteger.valueOf(motivo));
            registroSalida.setDatosSolicitud(datosSolicitud);
            guardarBitacora(wsContext, registroSalida, e.getFaultInfo().getFaultString(), ConstantesProceso.FA3);
            LOGGER.error(e.getFaultInfo().getFaultString());
        } catch (Exception e) {
            registroSalida = new NotificacionVeriDevManual();
            registroSalida.setDatosRegistroMATDYC(new DatosRegistroMATDYC());
            DatosSolicitud datosSolicitud = new DatosSolicitud();
            datosSolicitud.setEstadoRegistro(BigInteger.valueOf(ConstantesProceso.NO_EXITOSO));
            datosSolicitud.setMotivo(BigInteger.valueOf(ConstantesProceso.NO_EXITOSO));
            registroSalida.setDatosSolicitud(datosSolicitud);
            guardarBitacora(wsContext, registroSalida, e.getMessage(), ConstantesProceso.FA3);
            LOGGER.error(e.getMessage());
        }

        return registroSalida;
    }

    @Override
    public NotificacionConsulSolIRS procesarSolCompIRSDevAut(BusquedaTramitesManuales busquedaTramitesManuales, WebServiceContext wsContext) {
        LOGGER.info(LOG_MENSAJE + serialVersionUID + " " + toString());
        NotificacionConsulSolIRS registroSalida;
        try {
            registroSalida = procesoConsultarExistenciaSolDev.procesarSolCompIRSDevAut(busquedaTramitesManuales);
        } catch (Exception e) {
            registroSalida = new NotificacionConsulSolIRS();
            DatosContribuyente datosContribuyente = new DatosContribuyente();
            datosContribuyente.setRFC("");
            registroSalida.setDatosContribuyente(datosContribuyente);
            DatosSolicitud datosSolicitud = new DatosSolicitud();
            datosSolicitud.setEstadoRegistro(BigInteger.ZERO);
            registroSalida.setDatosSolicitud(datosSolicitud);
            guardarBitacora(wsContext, registroSalida, e.getMessage(), ConstantesProceso.FA4);
            LOGGER.error(e.getMessage());
        }
        return registroSalida;
    }

    private void guardarBitacora(WebServiceContext wsContext, Object registroSalida, String mensaje, Integer idOperacion) {
        try {
            HttpSession httpSession = ((HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST)).getSession();
            String request = (String) httpSession.getAttribute("xmlRequestString");
            String response = Utilerias.dtoToXMLString(registroSalida);

            DyctWsAutomaticaDTO wsAutomatica = new DyctWsAutomaticaDTO();
            wsAutomatica.setFechaRegistro(new Date());
            wsAutomatica.setIdOperacion(idOperacion);
            String mensajeCopia;
            if (mensaje != null) {
                int maxCampoMsg = ConstantesDyCNumerico.VALOR_250;
                mensajeCopia = mensaje.length() > maxCampoMsg ? mensaje.substring(ConstantesDyCNumerico.VALOR_0, maxCampoMsg) : mensaje;
            } else {
                mensajeCopia = "ERROR EN LOS DATOS DEL REQUEST";
            }
            wsAutomatica.setMensaje(mensajeCopia);
            wsAutomatica.setXmlRequest(request);
            wsAutomatica.setXmlResponse(response);
            wsAutomaticaService.insertar(wsAutomatica);

        } catch (Exception e) {
            LOGGER.error("ERROR AL GUARDAR TABLA DE SEGUIMIENTO:: " + e.getMessage(), e);
        }
    }

    @Override
    public NotificacionRegistroYGestion procesarRepcionYGestTramDevAutISR(RegistroDevolucionAut registroDevolucionAut, WebServiceContext wsContext) {
        LOGGER.info(LOG_MENSAJE + serialVersionUID + " " + toString());
        NotificacionRegistroYGestion registroSalida = new NotificacionRegistroYGestion();
        try {
            LOGGER.info(DateLoggerUtil.getTimeToExcecution());
            registroSalida = procesoRepcionYGestTramDevAutISR.execute(registroDevolucionAut, registroSalida, wsContext);
            LOGGER.info(DateLoggerUtil.getTimeToExcecution());
        } catch (ServiceException se) {
            registroSalida.setEdoDeLaOperacion(new BigInteger(String.valueOf(ConstantesProceso.NO_EXITOSO)));
            if (registroSalida.getMotivo() == null && se.getFaultInfo().getMotivoCode() != null) {
                try {
                    registroSalida.setMotivo(new BigInteger(se.getFaultInfo().getMotivoCode()));
                } catch (Exception e) {
                    LOGGER.info(e);
                    registroSalida.setMotivo(new BigInteger(se.getFaultInfo().getFaultCode()));
                }                
            }
            limpiarFolios(registroSalida);
            guardarBitacora(wsContext, registroSalida, se.getFaultInfo().getFaultString(), ConstantesProceso.FA5);
            LOGGER.error(se.getFaultInfo().getFaultCode() + (":") + se.getFaultInfo().getFaultString(), se);
            LOGGER.info(DateLoggerUtil.getTimeToExcecution());

        } catch (Exception e) {
            registroSalida = new NotificacionRegistroYGestion();
            registroSalida.setEdoDeLaOperacion(new BigInteger(String.valueOf(ConstantesProceso.NO_EXITOSO)));
            registroSalida.setMotivo(new BigInteger(CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo()));
            guardarBitacora(wsContext, registroSalida, CodigosDeError.MOTIVO_ERROR_TRAMITE.getDescripcion(), ConstantesProceso.FA5);
            LOGGER.error(e.getMessage(), e);
            LOGGER.info(DateLoggerUtil.getTimeToExcecution());
        }

        return registroSalida;
    }

    private void limpiarFolios(NotificacionRegistroYGestion registroSalida) {
        if (registroSalida.getEdoDeLaOperacion().intValue() == ConstantesProceso.NO_EXITOSO) {
            registroSalida.setFolioMATDYC(null);
            registroSalida.setIdICEP(null);
        }
    }
}
