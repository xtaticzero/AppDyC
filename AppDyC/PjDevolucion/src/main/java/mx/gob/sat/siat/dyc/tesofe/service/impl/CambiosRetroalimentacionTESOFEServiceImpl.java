package mx.gob.sat.siat.dyc.tesofe.service.impl;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import mx.gob.sat.siat.dyc.tesofe.service.cliente.MATSADRetroalimentaPagoService;
import mx.gob.sat.siat.dyc.tesofe.service.cliente.RetroalimentaPagoMATDyCIn;
import mx.gob.sat.siat.dyc.tesofe.service.cliente.RetroalimentaPagoMATDyCServiceImpl;

import mx.gob.sat.siat.dyc.dao.banco.DyctCuentaBancoDAO;
import mx.gob.sat.siat.dyc.dao.motivo.DyccMotivoRechazoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.dao.util.DycpSolPagoDAO;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoRechazoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEstadoPagoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccFormaPagoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;
import mx.gob.sat.siat.dyc.tesofe.service.CambiosRetroalimentacionTESOFEService;
import mx.gob.sat.siat.dyc.tesofe.service.EmitirComunicadosAutService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "cambiosRetroalimentacionTESOFEService")
public class CambiosRetroalimentacionTESOFEServiceImpl implements CambiosRetroalimentacionTESOFEService {

    private static final int ABONO_EFECTUADO = 0;
    private static final int CAMPOFECHAPRESENT = 17;
    private static final int CAMPOFECHA = 18;
    private static final int CAMPOSTATUS = 25;
    private static final int CAMPODESCRECHAZO = 26;
    private static final int CAMPOTRANSACCION = 14;
    private static final int CLAVE_RASTREO = 11;
    private static final int IDABONONOEFECTUADO = 2;
    private static final int IDABONOEFECTUADO = 1;
    private static final int IDESTADOPAGO = 1;
    private static final int IDFORMAPAGO = 1;
    private static final int IDPAGADA = 13;
    private static final int RESULTADOAUTORIZADA = 2;
    private static final int TRAMITE_ISR_AUTOMAT = 132;
    private static final Logger LOGGER = Logger.getLogger(RetroalimentarTESOFEServiceImpl.class);

    @Autowired
    private DyctCuentaBancoDAO dgyctCuentaBancoDAO;

    @Autowired
    private DyccMotivoRechazoDAO dyccMotivoRechazoDAO;

    @Autowired(required = true)
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private DycpSolPagoDAO dycpSolPagoDAO;

    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    @Autowired(required = true)
    private EmitirComunicadosAutService emitirComunicadosAutService;

    /**
     *
     * @param datosRenglon
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {SIATException.class})
    public void buscarFlujoAlterno(String[] datosRenglon) throws SIATException {
        String numeroControl = null;
        DyctResolucionDTO resolucion = null;
        try {
            resolucion = dyctResolucionDAO.consultarDatosResolucionXCveRastreo(datosRenglon[CLAVE_RASTREO].trim());
            numeroControl = resolucion.getDycpSolicitudDTO().getNumControl();
        } catch (SIATException siate) {
            LOGGER.error("Clave de rastreo no registrada en sistema: " + datosRenglon[CLAVE_RASTREO]);
        }

        if (numeroControl != null) {

            LOGGER.info("******************* Numero de control: " + numeroControl
                    + ". Clave de rastreo: " + datosRenglon[CLAVE_RASTREO]
                    + ". Abono efectuado: " + datosRenglon[CAMPOSTATUS]);
            try {
                if (Integer.valueOf(datosRenglon[CAMPOSTATUS]) == ABONO_EFECTUADO) {
                    flujoAlterno01(datosRenglon, numeroControl, resolucion);

                } else {
                    flujoAlterno02(datosRenglon, numeroControl, resolucion);
                }
            } catch (Exception siate) {
                LOGGER.error("buscarFlujoAlterno(): ", siate);
                throw new SIATException(siate);
            }
        }

    }

    /**
     * Implementa el flujo alterno 1 (FA01) del caso de uso
     * EnviarRetroalimentacionDeLaTESOFE
     *
     * @param datosRenglon - datos que se incluyen en el archivo de texto
     * @param numControl - nmero de control correspondiente a datosRenglon
     * @param resolucion - resolucin que incluye el tipo de trmite de
     * datosRenglon
     * @throws SIATException
     */
    private void flujoAlterno01(String[] datosRenglon, String numControl,
            DyctResolucionDTO resolucion) throws SIATException {
        DycpSolPagoDTO objetoSolPago = null;
        try {
            Date fechaSQL = null;
            objetoSolPago = new DycpSolPagoDTO();
            fechaSQL = crearFechaDeString(datosRenglon, CAMPOFECHA);

            objetoSolPago.setDyccEstadoPagoDTO(new DyccEstadoPagoDTO());
            objetoSolPago.getDyccEstadoPagoDTO().setIdEstadoPago(IDESTADOPAGO);
            objetoSolPago.setDyccFormaPagoDTO(new DyccFormaPagoDTO());
            objetoSolPago.getDyccFormaPagoDTO().setIdFormaPago(IDFORMAPAGO);
            objetoSolPago.setDyccMotivoRechazoDTO(new DyccMotivoRechazoDTO());
            objetoSolPago.setDycpSolicitudDTO(new DycpSolicitudDTO());
            objetoSolPago.setFechaAbono(fechaSQL);
            objetoSolPago.setNumeroTransaccion(Integer.parseInt(String.valueOf(datosRenglon[CAMPOTRANSACCION])));
            objetoSolPago.setNumControl(numControl);

            actualizarOInsertar(objetoSolPago, numControl);

            dycpSolicitudDAO.actualizarFechaFinTramite(crearFechaDeString(datosRenglon, CAMPOFECHA), numControl);
            dycpSolicitudDAO.actualizarStatus(IDPAGADA, numControl);

            if (resolucion != null && resolucion.getDyccTipoTramite().getIdTipoTramite() == TRAMITE_ISR_AUTOMAT) {
                LOGGER.info("Es trmite 132, pago efectuado");
                responderServicio(datosRenglon, numControl, Boolean.TRUE);

            } else {
                emitirComunicadosAutService.emitirComunicadosAutomaticamente(numControl, RESULTADOAUTORIZADA,
                        datosRenglon);
            }

        } catch (Exception siate) {
            LOGGER.error("Error al registrar un abono no efectuado. \n numControl: "
                    + numControl + ". Datos de pago: " + Arrays.toString(datosRenglon) + "\n"
                    + ExtractorUtil.ejecutar(objetoSolPago));
            LOGGER.error("La excepcion dice: ", siate);
            throw new SIATException(siate);
        }
        objetoSolPago = null;
    }

    /**
     * Implementa el flujo alterno 2
     *
     * @param datosRenglon - datos que se incluyen en el archivo de texto
     * @param numControl - nmero de control correspondiente a datosRenglon
     * @param resolucion - resolucin que incluye el tipo de trmite de
     * datosRenglon
     * @throws SIATException
     */
    private void flujoAlterno02(String[] datosRenglon, String numControl,
            DyctResolucionDTO resolucion) throws SIATException {
        DycpSolPagoDTO objetoSolPago = null;
        try {
            Date fechaSQL = null;
            objetoSolPago = new DycpSolPagoDTO();
            fechaSQL = crearFechaDeString(datosRenglon, CAMPOFECHA);
            objetoSolPago.setDyccEstadoPagoDTO(new DyccEstadoPagoDTO());
            objetoSolPago.setDyccFormaPagoDTO(new DyccFormaPagoDTO());
            objetoSolPago.setDyccMotivoRechazoDTO(new DyccMotivoRechazoDTO());
            objetoSolPago.setDycpSolicitudDTO(new DycpSolicitudDTO());
            objetoSolPago.getDyccEstadoPagoDTO().setIdEstadoPago(IDABONONOEFECTUADO);
            objetoSolPago.setFechaAbono(fechaSQL);
            objetoSolPago.getDyccMotivoRechazoDTO().setIdMotivoRechazo(Integer.valueOf(datosRenglon[CAMPOSTATUS]));
            objetoSolPago.setNumeroTransaccion(Integer.parseInt(String.valueOf(datosRenglon[CAMPOTRANSACCION])));
            objetoSolPago.setNumControl(numControl);

            actualizarOInsertar(objetoSolPago, numControl);
            dgyctCuentaBancoDAO.actualizarCuentaValida(0, numControl);

            if (resolucion != null && resolucion.getDyccTipoTramite().getIdTipoTramite() == TRAMITE_ISR_AUTOMAT) {
                LOGGER.info("Es trmite 132, pago NO efectuado");
                responderServicio(datosRenglon, numControl, false);

            }

            // TODO: revisar: truena si se procesa 2 veces el mismo nmero de control
            // para un pago NO efectuado
            if (dyccMotivoRechazoDAO.validarIDMotivoRechazo(Integer.valueOf(datosRenglon[CAMPOSTATUS])) > 0) {
                emitirComunicadosAutService.emitirComunicadosAutomaticamente(numControl, 1, datosRenglon);
            }

            objetoSolPago = null;

        } catch (Exception siate) {
            LOGGER.error("Error al registrar un abono efectuado. \n numControl: "
                    + numControl + ". Datos de pago: " + Arrays.toString(datosRenglon) + "\n"
                    + ExtractorUtil.ejecutar(objetoSolPago));
            LOGGER.error("La excepcion dice: ", siate);
            throw new SIATException(siate);
        }
        objetoSolPago = null;
    }

    /**
     * Llena objeto y enva respuesta a MAT-SAD
     *
     * @param datosRenglon - datos que se incluyen en el archivo de texto
     * @param numControl - nmero de control correspondiente a datosRenglon
     * @param afonoEfectuado - indica si el abono fue efectuado
     */
    private void responderServicio(String[] datosRenglon, String numControl,
            boolean afonoEfectuado) throws SIATException {
        try {
            MATSADRetroalimentaPagoService cliente = new MATSADRetroalimentaPagoService();
            RetroalimentaPagoMATDyCServiceImpl servicio = cliente.getRetroalimentaPagoMATDyCServiceImplPort();

            RetroalimentaPagoMATDyCIn respuesta = new RetroalimentaPagoMATDyCIn();
            respuesta.setFolioMATDyC(numControl);
            if (afonoEfectuado) {
                respuesta.setEstadoPago((short) IDABONOEFECTUADO);
                respuesta.setMotivo(null);
                respuesta.setDescripcionMotivo(null);
            } else {
                respuesta.setEstadoPago((short) IDABONONOEFECTUADO);
                respuesta.setMotivo(Short.parseShort(datosRenglon[CAMPOSTATUS]));
                respuesta.setDescripcionMotivo(datosRenglon[CAMPODESCRECHAZO]);
            }

            Date fechaPresentacion = crearFechaDeString(datosRenglon, CAMPOFECHAPRESENT);
            GregorianCalendar calendarPres = new GregorianCalendar();
            calendarPres.setTime(fechaPresentacion);
            XMLGregorianCalendar dateXMLPresentacion = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarPres);
            respuesta.setFechaPresentaArchivo(dateXMLPresentacion);

            Date fechaPago = crearFechaDeString(datosRenglon, CAMPOFECHA);
            GregorianCalendar calendarPago = new GregorianCalendar();
            calendarPago.setTime(fechaPago);
            XMLGregorianCalendar dateXMLPago = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarPago);
            respuesta.setFechaPago(dateXMLPago);

            respuesta.setNumTransaccionTesofe(String.valueOf(datosRenglon[CLAVE_RASTREO]));

            /*
             Datos del registro en MAT DyC
             Folio MAT DyC               -- Numero de control MAT DyC
             Datos retroalimentacin TESOFE
             Estado del Pago             -- 1 efectuado / 2 no efectuado
             Fecha de presentacin       -- fecha de presentacion del archivo vinculado
             Fecha de pago               -- fecha de pago
             Nmero transaccin TESOFE   -- id asignado por la TESOFE
             Motivo                      -- solo para no efectuados, id de rechazo
             Descripcin del motivo      -- solo para no efectuados, descripcin del rechazo
             */
            servicio.retroalimentaPago(respuesta);

        } catch (Exception ex) {
            LOGGER.error("Error al responder a MAT-SAD: numero de Control: "
                    + numControl + ". Datos de pago: " + Arrays.toString(datosRenglon) + "\n",
                    ex);
        }
    }

    /**
     * Lo que hace es verificar si existe ya un registro en base de datos de un
     * numero de control y en caso de existir acualiza el registro existente, en
     * caso contrario agrega uno nuevo en base de datos.
     *
     * @param objetoSolPago
     * @param numControl
     * @throws SIATException
     */
    private void actualizarOInsertar(DycpSolPagoDTO objetoSolPago, String numControl) throws SIATException {
        if (dycpSolPagoDAO.buscarRegistroExistente(numControl) > 0) {
            dycpSolPagoDAO.actualizar(objetoSolPago);
        } else {
            dycpSolPagoDAO.insertar(objetoSolPago);
        }
    }

    /**
     * Lo que hace es verificar si existe ya un registro en base de datos de un
     * numero de control y en caso de existir acualiza el registro existente, en
     * caso contrario agrega uno nuevo en base de datos.
     *
     * @param objetoSolPago
     * @param numControl
     * @throws SIATException
     */
    private Date crearFechaDeString(String[] datosRenglon, int numColumna) throws SIATException {
        Date fechaSQL = null;
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fechaConFormato = formatoFecha.parse(datosRenglon[numColumna]);
            fechaSQL = new Date(fechaConFormato.getTime());

        } catch (ParseException pe) {
            LOGGER.error("crearFechaDeString(): " + pe);
            throw new SIATException(pe);
        }
        return fechaSQL;
    }
}
