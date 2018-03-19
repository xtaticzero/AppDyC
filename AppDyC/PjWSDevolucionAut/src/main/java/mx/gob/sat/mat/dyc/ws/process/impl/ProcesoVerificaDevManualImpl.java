package mx.gob.sat.mat.dyc.ws.process.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.WebServiceContext;

import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.DatosICEP;
import mx.gob.sat.mat.dyc.ws.domain.DatosRegistroMATDYC;
import mx.gob.sat.mat.dyc.ws.domain.DatosSolicitud;
import mx.gob.sat.mat.dyc.ws.domain.DatosTramite;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionVeriDevManual;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.process.*;
import mx.gob.sat.mat.dyc.ws.utils.ValidaCreditoFiscal;
import mx.gob.sat.mat.dyc.ws.utils.ValidacionDatos;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.DycControlSaldosObtenerIcepDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteInfoAdicional;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("procesoVerificaDevManual")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProcesoVerificaDevManualImpl extends ProcesoVerificaDevBaseAbstract implements ProcesoVerificaDevManual, Serializable {

    private static final long serialVersionUID = -3472061544913634145L;
    private static final String PREFIJO_TRAMITE_DEV_AUT_ISR = "SA";

    private NotificacionVeriDevManual procesaRegistro(RegistroDevolucionAut registroEntrada, WebServiceContext wsContext)
            throws ServiceException {
        NotificacionVeriDevManual registroSalida = new NotificacionVeriDevManual();
        boolean bloquearIcep = false;
        try {
            if (rfcValido(registroEntrada) && tramiteEIcepValido(registroEntrada)) {

                IdCInterno datosContribuyente = getDatosContribuyenteRfcAmpliado(registroEntrada);

                if (rfcAmpliadoValido(datosContribuyente)) {

                    DyctSaldoIcepDTO registroIcep = buscarRegistroIcep(registroEntrada);
                    if (registroIcep != null) {
                        bloquearIcep = Boolean.TRUE;
                        Integer idSaldoIcep = registroIcep.getIdSaldoIcep();
                        consultaCompensacion(idSaldoIcep);
                        List<DycpDatosSolicitudDTO> datosSolicitudes = consultaSolicitud(idSaldoIcep);
                        tramiteValido(datosSolicitudes);
                    }

                    DyctSaldoIcepDTO registroSaldoIcep = getRegistroSaldoIcep(registroEntrada);
                    if (registroIcep == null) {
                        generarRegistroSaldoIcep(registroSaldoIcep);
                    } else {
                        registroSaldoIcep.setIdSaldoIcep(registroIcep.getIdSaldoIcep());
                    }

                    String numeroControl = generaNumeroControlDevolucionAutomatica(datosContribuyente);
                    registrarServicio(numeroControl, datosContribuyente, registroSaldoIcep, registroEntrada);

                    if (registroTieneIdentificadorSaldoIcep(registroSaldoIcep)) {

                        guardarDatosContribuyente(numeroControl, datosContribuyente);
                        guardarDatosTrarmiteISR(numeroControl, registroEntrada);
                        generarTramiteDeclaracionControlSaldos(numeroControl, registroSaldoIcep, registroEntrada);
                        DyctMovSaldoDTO movimientoSaldoIcep
                                = getMovimientoSaldoIcep(getNumeroOperacionEntradaString(registroEntrada), registroSaldoIcep,
                                        registroEntrada);
                        guardarMovimientoIcep(movimientoSaldoIcep);
                        relacionarInconsistenciaCreditoFiscal(numeroControl);

                        registrarExpediente(numeroControl);

                        if (bloquearIcep) {
                            bloquearRegistroIcep(registroSaldoIcep);
                        }

                        generarPistasAuditoria(numeroControl, registroEntrada, wsContext);

                        registroSalida = generarSalidaProcesamientoTramite(numeroControl, registroSaldoIcep);

                    }

                }

            }
        } catch (SIATException e) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    e.getMessage(), e);
        }
        return registroSalida;
    }

    private DyctMovSaldoDTO getMovimientoSaldoIcep(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep,
            RegistroDevolucionAut registroEntrada) throws ServiceException {
        DyctMovSaldoDTO movimientoSaldoIcep = new DyctMovSaldoDTO();

        BigDecimal importe = getImporteSaldoAFavorEntrada(registroEntrada);
        Date fechaOrigen = getFechaPresentacionEntradaDate(registroEntrada);
        Date fechaRegistro = getFechaActual();
        DyccMovIcepDTO dyccMovIcepDTO = Constantes.MovsIcep.ABONO_SAFDWH;

        movimientoSaldoIcep.setImporte(importe);
        movimientoSaldoIcep.setIdOrigen(numeroControl);
        movimientoSaldoIcep.setFechaOrigen(fechaOrigen);
        movimientoSaldoIcep.setFechaRegistro(fechaRegistro);
        movimientoSaldoIcep.setDyccMovIcepDTO(dyccMovIcepDTO);
        movimientoSaldoIcep.setDyctSaldoIcepDTO(registroSaldoIcep);

        return movimientoSaldoIcep;
    }

    private boolean rfcValido(RegistroDevolucionAut registroEntrada) throws ServiceException {
        String rfc = getRfcEntrada(registroEntrada);

        if (ValidacionDatos.rfcFormatoValido(rfc)) {
            return Boolean.TRUE;
        }
        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                "ERROR EL RFC " + rfc + " NO CUMPLE CON EL PATRON");
    }

    private boolean tramiteEIcepValido(RegistroDevolucionAut registroEntrada) throws ServiceException {
        DatosTramite datosTramite = getDatosTramiteEntrada(registroEntrada);
        DatosICEP icep = getIcepEntrada(registroEntrada);

        if (ValidacionDatos.datosTramiteValidos(datosTramite)
                && ValidacionDatos.icepValido(icep)) {

            return Boolean.TRUE;
        }
        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                "ERROR EN LOS DATOS DE TRAMITE O DEL ICEP");
    }

    private DatosICEP getIcepEntrada(RegistroDevolucionAut registroEntrada) {
        return registroEntrada.getDatosICEP();
    }

    private DatosTramite getDatosTramiteEntrada(RegistroDevolucionAut registroEntrada) {
        return registroEntrada.getDatosTramite();
    }

    private IdCInterno getDatosContribuyenteRfcAmpliado(RegistroDevolucionAut registroEntrada) throws ServiceException {
        String rfc = getRfcEntrada(registroEntrada);

        return getRfcAmpliado(rfc);
    }

    private boolean tramiteValido(List<DycpDatosSolicitudDTO> datosSolicitudes) throws ServiceException {
        boolean bandera = Boolean.TRUE;
        if (datosSolicitudes != null) {
            for (DycpDatosSolicitudDTO solicitud : datosSolicitudes) {
                String tipoSolicitud = solicitud.getNumControl().substring(0, 2);

                if (!tipoSolicitud.equals(PREFIJO_TRAMITE_DEV_AUT_ISR)) {
                    throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO,
                            CodigosDeError.MOTIVO_TRAMITE_ASOCIADO.getCodigo(), "EXISTE UNA SOLICITUD MANUAL ("
                            + solicitud.getNumControl() + ")EN EL ESTADO: (" + solicitud.getIdEstadoSolicitud() + ")");
                } else if (tipoSolicitud.equals(PREFIJO_TRAMITE_DEV_AUT_ISR)) {
                /* En caso de existir solicitudes automaticas preautorizadas, continua con el flujo. */
                boolean sente = ValidaCreditoFiscal.validaEstadoSolicitud(solicitud);
                    if (solicitud.getNumDictaminador() != 0 && sente) {
                        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO,
                            CodigosDeError.MOTIVO_TRAMITE_ASOCIADO.getCodigo(), "EXISTE UNA SOLICITUD AUTOMATICA ("
                                + solicitud.getNumControl() + ") CON DICTAMINADOR: " + solicitud.getNumDictaminador()
                                + " Y EN ESTATUS (" + solicitud.getIdEstadoSolicitud() + ")");
                    }
                }
            }
        }
        return bandera;
    }

    private IdCInterno getRfcAmpliado(String rfc) throws ServiceException {
        try {
            return getRfcAmplService().consultarXRfc(rfc);
        } catch (RfcAmplExcepcion error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL CONSUMIR RFC AMPLIADO " + error.getMessage(), error);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL CONSUMIR RFC AMPLIADO " + e.getMessage(), e);
        }
    }

    private boolean rfcAmpliadoValido(IdCInterno datosContribuyente) throws ServiceException {
        if (ValidacionDatos.informacionRfcAmpliadoNoValida(datosContribuyente)) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "LOS DATOS DE RFC_AMPLIDADO TIENEN INCONSISTENCIA ");
        }

        return Boolean.TRUE;
    }

    private DyctSaldoIcepDTO buscarRegistroIcep(RegistroDevolucionAut registroEntrada) throws ServiceException {
        DyctSaldoIcepDTO registroIcep = encontrarRegistroIcep(registroEntrada);

        if (registroIcep == null
                || registroIcepSinResultados(registroIcep)) {

            return null;
        }

        return registroIcep;
    }

    private boolean registroIcepSinResultados(DyctSaldoIcepDTO registroIcep) {
        return registroIcep == null || validaRegistroICEP(registroIcep);

    }

    private boolean validaRegistroICEP(DyctSaldoIcepDTO registroIcep) {
        return (registroIcep.getRfc() == null && registroIcep.getBoId() == null)
                || (registroIcep.getBoId() != null && registroIcep.getBoId().equals(ConstantesProceso.ICEP_NO_ENCONTRADO));
    }

    private DyctSaldoIcepDTO encontrarRegistroIcep(RegistroDevolucionAut registroEntrada) throws ServiceException {
        try {
            DycControlSaldosObtenerIcepDTO registroBusquedaIcep = getRegistroBusquedaIcep(registroEntrada);
            if (registroBusquedaIcep != null) {

                return getProcesarDeclaracionCompBussinesDel().obtenerIcep(registroBusquedaIcep);
            }

        } catch (DycServiceExcepcion error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL CONSULTAR EL OBTENER EL SALDO ICEP " + error.getMessage(), error);
        }

        return null;
    }

    private DycControlSaldosObtenerIcepDTO getRegistroBusquedaIcep(RegistroDevolucionAut registroEntrada)
            throws ServiceException {
        DycControlSaldosObtenerIcepDTO registroBusquedaIcep = null;

        String rfc = getRfcEntrada(registroEntrada);
        Integer concepto = getConceptoEntradaInteger(registroEntrada);
        Integer ejercicio = getEjercicioEntradaInteger(registroEntrada);
        Integer periodo = getPeriodoEntradaInteger(registroEntrada);
        Integer origenSaldo = getOrigenSaldoEntrada(registroEntrada);

        if (validaDatosRegistroICEP(rfc, concepto, ejercicio, periodo) && origenSaldo != null) {

            registroBusquedaIcep = new DycControlSaldosObtenerIcepDTO();

            registroBusquedaIcep.setRfc(rfc);
            registroBusquedaIcep.setIdConcepto(concepto);
            registroBusquedaIcep.setIdEjercicio(ejercicio);
            registroBusquedaIcep.setIdPeriodo(periodo);
            registroBusquedaIcep.setIdOrigenSaldo(origenSaldo);

        }

        return registroBusquedaIcep;
    }

    private boolean validaDatosRegistroICEP(String rfc, Integer concepto,
            Integer ejercicio, Integer periodo) throws ServiceException {

        if (rfc != null && concepto != null && ejercicio != null && periodo != null) {
            return Boolean.TRUE;
        }
        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                "ERROR EN LOS DATOS DEL ICEP " + rfc + " " + concepto + " " + ejercicio + " " + periodo);

    }

    private Integer getConceptoEntradaInteger(RegistroDevolucionAut registroEntrada) {
        BigInteger valor = getConceptoEntrada(registroEntrada);

        if (valor != null) {

            return valor.intValue();
        }

        return null;
    }

    private BigInteger getConceptoEntrada(RegistroDevolucionAut registroEntrada) {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getConcepto();
    }

    private Integer getEjercicioEntradaInteger(RegistroDevolucionAut registroEntrada) {
        BigInteger valor = getEjercicioEntrada(registroEntrada);

        if (valor != null) {

            return valor.intValue();
        }

        return null;
    }

    private BigInteger getEjercicioEntrada(RegistroDevolucionAut registroEntrada) {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getEjercicio();
    }

    private Integer getPeriodoEntradaInteger(RegistroDevolucionAut registroEntrada) {
        String valor = getPeriodoEntrada(registroEntrada);

        if (valor != null) {

            return Integer.parseInt(valor);
        }

        return null;
    }

    private String getPeriodoEntrada(RegistroDevolucionAut registroEntrada) {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getPeriodo();
    }

    private String generaNumeroControlDevolucionAutomatica(IdCInterno datosContribuyente) throws ServiceException {

        String anioActual = getAnioActual();

        String claveSir = getClaveSir(datosContribuyente);

        String consecutivo = getConsecutivoDevolucionAutomatica(claveSir);
        if (consecutivo != null && consecutivo.isEmpty()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL OBTENER LA SECUENCIA PARA LA ADMINISTRACION " + claveSir);
        }

        return creaNumeroControlDevolucionAutomatica(claveSir, anioActual, consecutivo);
    }

    private DyctSaldoIcepDTO getRegistroSaldoIcep(RegistroDevolucionAut registroEntrada) throws ServiceException {
        DyctSaldoIcepDTO registroSaldoIcep = new DyctSaldoIcepDTO();

        String rfc = getRfcEntrada(registroEntrada);
        DyccPeriodoDTO periodo = getPeriodoEntradaDTO(registroEntrada);
        DyccConceptoDTO concepto = getConceptoEntradaDTO(registroEntrada);
        DyccEjercicioDTO ejercicio = getEjercicioEntradaDTO(registroEntrada);
        DyccOrigenSaldoDTO origenSaldo = getOrigenSaldoEntradaDTO(registroEntrada);

        registroSaldoIcep.setRfc(rfc);
        registroSaldoIcep.setDyccConceptoDTO(concepto);
        registroSaldoIcep.setDyccEjercicioDTO(ejercicio);
        registroSaldoIcep.setDyccPeriodoDTO(periodo);
        registroSaldoIcep.setDyccOrigenSaldoDTO(origenSaldo);

        return registroSaldoIcep;
    }

    private DyccPeriodoDTO getPeriodoEntradaDTO(RegistroDevolucionAut registroEntrada) {
        String valorEntrada = getPeriodoEntrada(registroEntrada);

        if (valorEntrada == null) {
            return null;
        }

        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();

        dyccPeriodoDTO.setIdPeriodo(Integer.parseInt(valorEntrada));

        return dyccPeriodoDTO;
    }

    private DyccConceptoDTO getConceptoEntradaDTO(RegistroDevolucionAut registroEntrada) {
        BigInteger valorEntrada = getConceptoEntrada(registroEntrada);

        if (valorEntrada == null) {
            return null;
        }

        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();

        dyccConceptoDTO.setIdConcepto(valorEntrada.intValue());

        return dyccConceptoDTO;
    }

    private DyccEjercicioDTO getEjercicioEntradaDTO(RegistroDevolucionAut registroEntrada) {
        BigInteger valorEntrada = getEjercicioEntrada(registroEntrada);

        if (valorEntrada == null) {
            return null;
        }

        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();

        dyccEjercicioDTO.setIdEjercicio(valorEntrada.intValue());

        return dyccEjercicioDTO;
    }

    private void generarRegistroSaldoIcep(DyctSaldoIcepDTO registroSaldoIcep) throws ServiceException {
        try {
            getDyccDevolucionAutService().guardarRegistroSaldoIcep(registroSaldoIcep);
        } catch (SIATException error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL GUARDAR EL SALDO ICEP " + error.getMessage(), error);
        }
    }

    private boolean registroTieneIdentificadorSaldoIcep(DyctSaldoIcepDTO registroSaldoIcep) throws ServiceException {
        if (registroSaldoIcep.getIdSaldoIcep() == null) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR NO SE REGISTRO EL ICEP");
        }
        return registroSaldoIcep.getIdSaldoIcep() != null;
    }

    private void guardarDatosContribuyente(String numeroControl, IdCInterno datosContribuyente) throws ServiceException {

        Date fechaConsulta = getFechaActual();
        try {
            getCrearCasoAutomaticasIvaService().insertarContribuyente(numeroControl, fechaConsulta, datosContribuyente);
        } catch (DycServiceExcepcion error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR NO SE GUARDARON LOS DATOS DEL CONTRIBUYENTE " + error.getMessage(), error);
        }
    }

    private void guardarDatosTrarmiteISR(String numeroControl, RegistroDevolucionAut registroEntrada)
            throws ServiceException {
        String cuentaClabe = getCuentaClabeEntrada(registroEntrada);
        Date fechaPresentacion = getFechaPresentacionEntradaDate(registroEntrada);

        Integer idArchivo = getDyccDevolucionAutService().insertarCuentaBancoArchivoDevAut(numeroControl, fechaPresentacion);

        try {

            getCrearCasoAutomaticasIvaService().insertarCuentaBanco(
                    cuentaClabe,
                    numeroControl,
                    fechaPresentacion,
                    idArchivo
            );

        } catch (DycServiceExcepcion error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR NO SE GUARDARON LOS DATOS DE LA CUENTA " + error.getMessage(), error);
        }
    }

    private String getCuentaClabeEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getInfoBanco() == null
                || null == registroEntrada.getInfoBanco().getCuentaCLABE()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR INFO BANCO (CUENTA CLABE)  ES REQUERIDO");
        }

        return registroEntrada.getInfoBanco().getCuentaCLABE();
    }

    private BigInteger getTipoDeclaracionEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null
                || null == registroEntrada.getDatosDeclaracion().getTipoDeclaracion()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR DATOS DE DECLARACION (TIPO DECLARACION)  ES REQUERIDO");
        }

        return registroEntrada.getDatosDeclaracion().getTipoDeclaracion();
    }

    private Date getFechaPresentacionEntradaDate(RegistroDevolucionAut registroEntrada) throws ServiceException {
        XMLGregorianCalendar fechaPresentacionEntrada = getFechaPresentacionEntrada(registroEntrada);
        Date fechaPresentacion = null;

        if (fechaPresentacionEntrada != null) {
            fechaPresentacion = fechaPresentacionEntrada.toGregorianCalendar().getTime();
        }

        return fechaPresentacion;
    }

    private XMLGregorianCalendar getFechaPresentacionEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null
                || null == registroEntrada.getDatosDeclaracion().getFechaPresentacion()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR DATOS DE LA DECLARACION (FECHA DE PRESENTACION)  ES REQUERIDO");
        }

        return registroEntrada.getDatosDeclaracion().getFechaPresentacion();
    }

    private BigDecimal getImporteSaldoAFavorEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null
                || null == registroEntrada.getDatosDeclaracion().getImporteSaldoAFavor()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR DATOS DE LA DECLARACION (IMPORTE SALDO A FAVOR)  ES REQUERIDO");
        }

        return registroEntrada.getDatosDeclaracion().getImporteSaldoAFavor();
    }

    private void generarTramiteDeclaracionControlSaldos(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep,
            RegistroDevolucionAut registroEntrada) throws ServiceException {
        DyctDeclaracionDTO tramiteControlSaldos = getTramiteControlSaldos(numeroControl, registroEntrada);
        DyctDeclaraIcepDTO declaracionIcep = getDeclaracionIcep(registroSaldoIcep, registroEntrada);

        guardarTramiteControlSaldos(tramiteControlSaldos);
        guardarTramiteDeclaracionIcep(declaracionIcep);
    }

    private DyctDeclaracionDTO getTramiteControlSaldos(String numeroControl, RegistroDevolucionAut registroEntrada)
            throws ServiceException {
        DyctDeclaracionDTO tramiteControlSaldos = new DyctDeclaracionDTO();

        DyccUsoDecDTO dyccUsoDecDTO = new DyccUsoDecDTO();
        DyccTipoDeclaraDTO dyccTipoDeclaraDTO = new DyccTipoDeclaraDTO();

        BigDecimal importe = getImporteSaldoAFavorEntrada(registroEntrada);
        String numeroOperacion = getNumeroOperacionEntradaString(registroEntrada);
        Date fechaPresentacion = asignaHora(getFechaPresentacionEntradaDate(registroEntrada));

        dyccUsoDecDTO.setIdUsoDec(ConstantesProceso.ID_USO_DEC_DEV_AUTOMATICAS);
        dyccTipoDeclaraDTO.setIdTipoDeclaracion(getTipoDeclaracionEntrada(registroEntrada).intValue());

        tramiteControlSaldos.setImporte(importe);
        tramiteControlSaldos.setNumControl(numeroControl);
        tramiteControlSaldos.setSaldoAfavor(importe);
        tramiteControlSaldos.setNumOperacion(numeroOperacion);
        tramiteControlSaldos.setDyccUsoDecDTO(dyccUsoDecDTO);
        tramiteControlSaldos.setFechaPresentacion(fechaPresentacion);
        tramiteControlSaldos.setDyccTipoDeclaraDTO(dyccTipoDeclaraDTO);

        return tramiteControlSaldos;
    }

    private DyctDeclaraIcepDTO getDeclaracionIcep(DyctSaldoIcepDTO registroSaldoIcep,
            RegistroDevolucionAut registroEntrada) throws ServiceException {
        DyctDeclaraIcepDTO declaracionIcep = new DyctDeclaraIcepDTO();

        DyccTipoDeclaraDTO tipoDeclaraDTO = new DyccTipoDeclaraDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO = registroSaldoIcep;

        Date fechaPresentacion = asignaHora(getFechaPresentacionEntradaDate(registroEntrada));

        Date fechaValidacionDWH = getFechaActual();
        BigInteger numeroOperacion = getNumeroOperacionEntrada(registroEntrada);
        BigDecimal importeSaldoAFavor = getImporteSaldoAFavorEntrada(registroEntrada);

        tipoDeclaraDTO.setIdTipoDeclaracion(getTipoDeclaracionEntrada(registroEntrada).intValue());

        declaracionIcep.setDyccTipoDeclaraDTO(tipoDeclaraDTO);
        declaracionIcep.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);

        declaracionIcep.setNumOperacion(numeroOperacion.longValue());
        declaracionIcep.setFechaPresentacion(fechaPresentacion);
        declaracionIcep.setSaldoAFavor(importeSaldoAFavor);
        declaracionIcep.setValidacionDWH(fechaValidacionDWH);
        declaracionIcep.setOrigenDeclara(Constantes.OrigenesDeclaracion.CONTRIBUYENTE);
        declaracionIcep.setFechaRegistro(fechaValidacionDWH);

        return declaracionIcep;
    }

    private Date asignaHora(Date fechaPresentacion) {
        Calendar calHoy = Calendar.getInstance();
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.setTime(fechaPresentacion);
        cal.set(Calendar.HOUR, calHoy.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, calHoy.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calHoy.get(Calendar.SECOND));

        return cal.getTime();
    }

    private void guardarTramiteControlSaldos(DyctDeclaracionDTO tramiteControlSaldos) throws ServiceException {
        try {
            getDyccDevolucionAutService().guardarDeclaracion(tramiteControlSaldos);
        } catch (DycDaoExcepcion error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL GUARDAR LOS DATOS DE LA DECLARACION " + error.getMessage(), error);
        }
    }

    private void guardarTramiteDeclaracionIcep(DyctDeclaraIcepDTO declaracionIcep) throws ServiceException {
        try {
            getDyccDevolucionAutService().guardarDeclaracionIcep(declaracionIcep);
        } catch (SIATException error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL GUARDAR EN DECLARAICEP " + error.getMessage(), error);
        }
    }

    private String getNumeroOperacionEntradaString(RegistroDevolucionAut registroEntrada) throws ServiceException {
        BigInteger valor = getNumeroOperacionEntrada(registroEntrada);

        if (valor != null) {

            return valor.toString();
        }

        return null;
    }

    private BigInteger getNumeroOperacionEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null
                || null == registroEntrada.getDatosDeclaracion().getNumOperacion()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR DATOS DE LA DECLARACION (NUM OPERACION)  ES REQUERIDO");
        }

        return registroEntrada.getDatosDeclaracion().getNumOperacion();
    }

    private void relacionarInconsistenciaCreditoFiscal(String numeroControl) throws ServiceException {
        DycaSolInconsistDTO registroSolicitudInconsistencia = getRegistroSolicitudInconsistencia(numeroControl);
        try {
            getDyccDevolucionAutService().insertarInconsistencia(registroSolicitudInconsistencia);
        } catch (SIATException e) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL REGISTRAR INCONSISTENCIA CREDITO FISCAL", e);
        }
    }

    private DycaSolInconsistDTO getRegistroSolicitudInconsistencia(String numeroControl) {
        DycaSolInconsistDTO solicitudInconsistencia = new DycaSolInconsistDTO();
        DyccInconsistDTO incosistencia = new DyccInconsistDTO();
        DycpSolicitudDTO solicitud = new DycpSolicitudDTO();

        incosistencia.setIdInconsistencia(ConstantesProceso.ID_INCONSISTENCIA);

        solicitud.setNumControl(numeroControl);

        solicitudInconsistencia.setDyccInconsistDTO(incosistencia);
        solicitudInconsistencia.setDycpSolicitudDTO(solicitud);
        solicitudInconsistencia.setDescripcion(ConstantesProceso.DESCRIPCION_INCONSISTENCIA);

        return solicitudInconsistencia;
    }

    private void registrarExpediente(String numeroControl) throws ServiceException {
        DycpSolicitudDTO solicitud = getSolicitudTramite(numeroControl);
        try {
            getIntegrarExpedienteService().crearExpediente(solicitud, null, null, null);
        } catch (Exception error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL REGISTRAR EL EXPEDIENTE " + error.getMessage(), error);
        }
    }

    private DycpSolicitudDTO getSolicitudTramite(String numeroControl) {

        DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();

        dyccTipoTramiteDTO.setResolAutomatica(ConstantesProceso.ESTADO_RESOLUCION_AUTOMATICA);
        dyccTipoTramiteDTO.setIdTipoTramite(ConstantesProceso.ID_TIPO_TRAMITE);

        dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        dycpServicioDTO.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);

        solicitud.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
        solicitud.setDycpServicioDTO(dycpServicioDTO);
        solicitud.setNumControl(numeroControl);

        return solicitud;
    }

    private void generarPistasAuditoria(String numeroControl, RegistroDevolucionAut registroEntrada,
            WebServiceContext wsContext) throws SIATException {
        guardarPistaAuditoria(wsContext, ConstantesProceso.ID_MENSAJE_ICONSISTENCIA,
                ConstantesProceso.ID_GRUPO_OPERACION, ConstantesProceso.MOV_INCONSISTENCIA_NUM_CONTROL, numeroControl,
                registroEntrada);
    }

    private void guardarPistaAuditoria(WebServiceContext wsContext, int idMensaje, int idGrupoMensaje, int idMovimiento, String numeroControl, RegistroDevolucionAut registroEntrada) throws SIATException {

        try {

            HttpSession httpSession = getSession(wsContext);
            String mensaje = getMensajePistaAuditoria(idMensaje, idGrupoMensaje, numeroControl, registroEntrada);

            SegbMovimientoDTO movimientoDTO = getMovimientoPistaAuditoria(httpSession, mensaje, idMovimiento, registroEntrada);

            getRegistroPistasAuditoria().registrarPistaAuditoria(movimientoDTO);

        } catch (Exception error) {
            getLogger().error(error.getMessage());
            throw new SIATException(error);
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

    private String getMensajePistaAuditoria(int idMensaje, int idGrupoMensaje, String numeroControl, RegistroDevolucionAut registroEntrada) throws SIATException {
        DyccMensajeUsrDTO mensajeUsr = getDyccMensajeUsrService().obtieneMensaje(idMensaje, idGrupoMensaje, ConstantesProceso.BITACORA);

        String mensaje = mensajeUsr.getDescripcion();
        String rfc = registroEntrada.getDatosContribuyente().getRFC();
        BigDecimal importeSaldoFavor = registroEntrada.getDatosDevolucionAutomatica().getImporteSolicitado();

        mensaje = mensaje.replace("<Numero de Control>", numeroControl)
                .replace("<Importe saldo a favor>", importeSaldoFavor.toString())
                .replace("<RFC del contribuyente>", rfc);

        return mensaje;
    }

    private SegbMovimientoDTO getMovimientoPistaAuditoria(HttpSession httpSession, String mensaje, int idMovimiento,
            RegistroDevolucionAut registroEntrada) throws AccesoDenegadoException, ServiceException {

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

    private NotificacionVeriDevManual generarSalidaProcesamientoTramite(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep) {
        DatosSolicitud datosSolicitud = getSolicitudRegistroExitoso();
        DatosRegistroMATDYC datosRegistroMATDYC = getDatosRegistroMATDYC(numeroControl, registroSaldoIcep);

        NotificacionVeriDevManual registroSalida = new NotificacionVeriDevManual();
        registroSalida.setDatosSolicitud(datosSolicitud);
        registroSalida.setDatosRegistroMATDYC(datosRegistroMATDYC);
        return registroSalida;
    }

    private DatosSolicitud getSolicitudRegistroExitoso() {
        DatosSolicitud datosSolicitud = new DatosSolicitud();

        datosSolicitud.setEstadoRegistro(BigInteger.valueOf(ConstantesProceso.ESTADO_REGISTRO_EXITOSO));

        return datosSolicitud;
    }

    private DatosRegistroMATDYC getDatosRegistroMATDYC(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep) {
        DatosRegistroMATDYC datosRegistroMATDYC = new DatosRegistroMATDYC();

        datosRegistroMATDYC.setFolioMATDYC(numeroControl);
        datosRegistroMATDYC.setIDICEP(BigInteger.valueOf(registroSaldoIcep.getIdSaldoIcep()));

        return datosRegistroMATDYC;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public NotificacionVeriDevManual execute(RegistroDevolucionAut registroDevolucionAut, WebServiceContext wsContext)
            throws ServiceException {

        return procesaRegistro(registroDevolucionAut, wsContext);

    }

}
