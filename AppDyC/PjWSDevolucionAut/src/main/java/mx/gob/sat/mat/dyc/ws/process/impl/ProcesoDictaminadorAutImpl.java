package mx.gob.sat.mat.dyc.ws.process.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

import mx.gob.sat.mat.dyc.ws.utils.ValidacionDatos;
import mx.gob.sat.mat.dyc.ws.process.*;
import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.DatosICEP;
import mx.gob.sat.mat.dyc.ws.domain.DatosRegistroMATDYC;
import mx.gob.sat.mat.dyc.ws.domain.DatosSolicitud;
import mx.gob.sat.mat.dyc.ws.domain.DatosTramite;
import mx.gob.sat.mat.dyc.ws.domain.DevolucionManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.utils.TipoTramiteEnum;
import mx.gob.sat.mat.dyc.ws.utils.ValidaCreditoFiscal;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
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
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import mx.gob.sat.siat.dyc.util.recurso.DycFechaUtil;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("procesoDictaminadorAut")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProcesoDictaminadorAutImpl extends ProcesoDictaminadorBaseAbstract implements ProcesoDictaminadorAut, Serializable {

    private static final long serialVersionUID = -7828566779345744135L;
    private transient DevolucionManual registroEntrada = null;
    private transient NotificacionDevManual registroSalida = new NotificacionDevManual();
    private static final String PREFIJO_TRAMITE_DEV_AUT_ISR = "SA";
    private static final Integer PROCESO_SAD = 21;
    private static final String LOG_MENSAJE = "ProcesoDictaminadorAutImpl.serialVersionUID:: ";

    public void inicializaEntradas(DevolucionManual devolucionManual) throws ServiceException {
        getLogger().info(LOG_MENSAJE + serialVersionUID + " " + ProcesoDictaminadorAutImpl.class.hashCode());
        registroEntrada = devolucionManual;
    }

    public void procesaRegistro(TipoTramiteEnum tipoTramite) throws ServiceException {
        getLogger().info(LOG_MENSAJE + serialVersionUID + " " + ProcesoDictaminadorAutImpl.class.hashCode());

        registroSalida = new NotificacionDevManual();

        if (rfcValido() && tramiteEIcepValido()) {
            IdCInterno datosContribuyente = getDatosContribuyenteRfcAmpliado();

            if (rfcAmpliadoValido(datosContribuyente, tipoTramite)) {

                DyctSaldoIcepDTO registroIcep = buscarRegistroIcep();
                List<DycpDatosSolicitudDTO> datosSolicitudes = null;
                if (registroIcep != null) {
                    Integer idSaldoIcep = registroIcep.getIdSaldoIcep();
                    consultaCompensacion(idSaldoIcep);
                    datosSolicitudes = consultaSolicitud(idSaldoIcep);
                }
                if ((registroIcep == null || tramiteValido(datosSolicitudes)) && (validaDeclaracion(consultaDeclaracion(getNumeroOperacionEntrada().toString(), getRfcEntrada()), tipoTramite))) {

                    DyctSaldoIcepDTO registroSaldoIcep = getRegistroSaldoIcep();
                    if (registroIcep == null) {
                        generarRegistroSaldoIcep(registroSaldoIcep);
                    } else {
                        registroSaldoIcep.setIdSaldoIcep(registroIcep.getIdSaldoIcep());
                        getLogger().info("DATOS_AUTOMATICA:: " + registroIcep.getIdSaldoIcep());
                    }
                    String numeroControl = generaNumeroControlDevolucionAutomatica(datosContribuyente);
                    getLogger().info("DATOS_AUTOMATICA:: " + " " + numeroControl + " " + registroEntrada + " " + datosContribuyente);
                    registrarServicio(numeroControl, datosContribuyente, registroSaldoIcep);

                    if (registroTieneIdentificadorSaldoIcep(registroSaldoIcep)) {

                        guardarDatosContribuyente(numeroControl, datosContribuyente);
                        guardarDatosTrarmiteISR(numeroControl);

                        if (registroIcep == null) {
                            generarTramiteDeclaracionControlSaldos(numeroControl, registroSaldoIcep);
                            DyctMovSaldoDTO movimientoSaldoIcep = getMovimientoSaldoIcep(getNumeroOperacionEntradaString(), registroSaldoIcep);
                            guardarMovimientoIcep(movimientoSaldoIcep);
                            registrarExpediente(numeroControl);
                            ControlSaldoImportesDTO controlSaldo = obtenerSaldoImportes(registroSaldoIcep.getIdSaldoIcep());
                            generarEstadoTramiteSalida(numeroControl, registroSaldoIcep, controlSaldo.getRemanente(), controlSaldo.getResuelto());
                        } else {
                            generarTramiteDeclaracionControlSaldos(numeroControl, registroIcep);
                            DyctMovSaldoDTO movimientoSaldoIcep = getMovimientoSaldoIcep(getNumeroOperacionEntradaString(), registroIcep);
                            guardarMovimientoIcep(movimientoSaldoIcep);
                            registrarExpediente(numeroControl);
                            ControlSaldoImportesDTO controlSaldo = obtenerSaldoImportes(registroSaldoIcep.getIdSaldoIcep());
                            generarEstadoTramiteSalida(numeroControl, registroIcep, controlSaldo.getRemanente(), controlSaldo.getResuelto());
                        }
                    }
                }

            }
        }
    }

    //Agregar SA sin credito Fiscal
    private boolean tramiteValido(List<DycpDatosSolicitudDTO> datosSolicitudes) throws ServiceException {
        boolean bandera = Boolean.TRUE;
        if (datosSolicitudes != null) {
            for (DycpDatosSolicitudDTO solicitud : datosSolicitudes) {
                String tipoSolicitud = solicitud.getNumControl().substring(0, 2);
                //Cambiar motivo Motivo = 1 <Existe devolución manual>
                if (!tipoSolicitud.equals(PREFIJO_TRAMITE_DEV_AUT_ISR)) {
                    throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_TRAMITE_ASOCIADO.getCodigo(), "EXISTE UNA SOLICITUD MANUAL (" + solicitud.getNumControl() + ")EN EL ESTADO: (" + solicitud.getIdEstadoSolicitud() + ")");
                } else if (tipoSolicitud.equals(PREFIJO_TRAMITE_DEV_AUT_ISR)) {
                    boolean sente = ValidaCreditoFiscal.validaEstadoSolicitud(solicitud);
                    if (solicitud.getNumDictaminador() != 0 && sente) {
                        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_TRAMITE_ASOCIADO.getCodigo(), "EXISTE UNA SOLICITUD AUTOMATICA  (" + solicitud.getNumControl() + ") CON DICTAMINADOR:" + solicitud.getNumDictaminador() + " Y  EN ESTATUS (" + solicitud.getIdEstadoSolicitud() + ")");
                    }
                }
            }
        }
        return bandera;
    }

    private boolean validaDeclaracion(List<DyctDeclaracionDTO> lstDeclaraciones, TipoTramiteEnum tipoTramite) throws ServiceException {
        boolean esRegistrable = true;

        if (lstDeclaraciones != null && !lstDeclaraciones.isEmpty()
                && validaTotalDeclaracionesAsociadas(lstDeclaraciones.size())) {
            //Obtener  tramite sin dictaminador
            DycpServicioDTO tramiteSinDictaminador = null;
            try {
                tramiteSinDictaminador = getDyccDevolucionAutService().consultarTramiteSinDictaminador(lstDeclaraciones.get(0).getDycpServicioDTO().getNumControl());
            } catch (SIATException e) {
                getLogger().error(e);
                throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), e.getMessage(), e);
            }
            if (tramiteSinDictaminador != null) {
                //Obtener solicitud y validar estatus
                DycpSolicitudDTO solicitudTramite = null;
                try {
                    solicitudTramite = getDyccDevolucionAutService().consultarSolicitud(tramiteSinDictaminador.getNumControl());
                } catch (SIATException e) {
                    getLogger().error(e);
                    throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), e.getMessage(), e);
                }
                if (solicitudTramite != null) {
                    if (solicitudTramite.getDyccEstadoSolDTO().getIdEstadoSolicitud().equals(PROCESO_SAD)) {
                        //Notifica a MAT-SAD
                        armaNotificacionMatSAD(tramiteSinDictaminador.getNumControl(), solicitudTramite.getDyctSaldoIcepDTO().getIdSaldoIcep());
                        esRegistrable = false;
                    } else {
                        //Valida estatus
                        if (ValidaCreditoFiscal.validaEstatusSAD(solicitudTramite.getDyccEstadoSolDTO().getIdEstadoSolicitud())) {

                            if (tipoTramite != null && tipoTramite.equals(TipoTramiteEnum.REPCION_GEST_TRAM_DEV_AUT)) {
                                throw new ServiceException(CodigosDeError.MOTIVO_EXISTE_TRAMITE_CON_NUMERO_DECLARACION.getCodigo(), CodigosDeError.MOTIVO_EXISTE_TRAMITE_CON_NUMERO_DECLARACION.getCodigo(), CodigosDeError.MOTIVO_EXISTE_TRAMITE_CON_NUMERO_DECLARACION.getDescripcion() + " CON NUMERO DE CONTROL (" + solicitudTramite.getDycpServicioDTO().getNumControl() + ") CON ESTATUS "
                                        + " ( " + solicitudTramite.getDyccEstadoSolDTO().getIdEstadoSolicitud() + " )");
                            }

                            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "EXISTE UNA SOLICITUD AUTOMATICA CON NUMERO DE CONTROL (" + solicitudTramite.getDycpServicioDTO().getNumControl() + ") CON ESTATUS "
                                    + " ( " + solicitudTramite.getDyccEstadoSolDTO().getIdEstadoSolicitud() + " )");
                        }
                    }
                }
            } else {
                throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO SE ENCONTRO NINGUN TRAMITE ASOCIADO AL NUMERO DE CONTROL (" + lstDeclaraciones.get(0).getDycpServicioDTO().getNumControl() + ")");
            }
        }

        return esRegistrable;
    }

    private void armaNotificacionMatSAD(String numControl, Integer idSaldoIcep) throws ServiceException {
        DyctSaldoIcepDTO icep = new DyctSaldoIcepDTO();
        icep.setIdSaldoIcep(idSaldoIcep);

        ControlSaldoImportesDTO controlSaldo = obtenerSaldoImportes(idSaldoIcep);
        generarEstadoTramiteSalida(numControl, icep, controlSaldo.getRemanente(),
                controlSaldo.getResuelto());

    }

    private boolean validaTotalDeclaracionesAsociadas(Integer totalDeclaraciones) throws ServiceException {

        boolean bandera = true;

        if (totalDeclaraciones > 1) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "EXISTE MAS DE UNA DECLARACION ASOCIADA AL NUMERO DE OPERACION (" + getNumeroOperacionEntrada() + ") Y RFC " + getRfcEntrada()
                    + " TOTAL DE DECLARACIONES ASOCIADAS (" + totalDeclaraciones + " )");
        }

        return bandera;
    }

    private NotificacionDevManual generaSalida() {

        return registroSalida;
    }

    public boolean rfcValido() throws ServiceException {
        String rfc = getRfcEntrada();

        if (ValidacionDatos.rfcFormatoValido(rfc)) {
            return Boolean.TRUE;
        }

        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ES UN RFC NO VALIDO, NO CUMPLE CON EL PATRON");

    }

    public String getRfcEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosContribuyente() == null) {

            return null;
        }

        return registroEntrada.getDatosContribuyente().getRFC();
    }

    public boolean tramiteEIcepValido() throws ServiceException {
        DatosTramite datosTramite = getDatosTramiteEntrada();
        DatosICEP icep = getIcepEntrada();

        if (ValidacionDatos.datosTramiteValidos(datosTramite)
                && ValidacionDatos.icepValido(icep)) {

            return Boolean.TRUE;
        }
        throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "EXISTE ERROR EN LOS DATOS DE TRAMITE " + datosTramite + " O EN EL ICEP " + icep);
    }

    public DatosICEP getIcepEntrada() {
        return registroEntrada.getDatosICEP();
    }

    public DatosTramite getDatosTramiteEntrada() {
        return registroEntrada.getDatosTramite();
    }

    public IdCInterno getDatosContribuyenteRfcAmpliado() throws ServiceException {
        String rfc = getRfcEntrada();
        IdCInterno datosContribuyente = getRfcAmpliado(rfc);
        if (datosContribuyente == null) {
            throw new ServiceException(CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(),"RFC_AMPLIADO NO ESTA REGRESANDO RESPUESTA");
        }
        return datosContribuyente;
    }

    private IdCInterno getRfcAmpliado(String rfc) throws ServiceException {
        try {
            return getRfcAmplService().consultarXRfc(rfc);
        } catch (RfcAmplExcepcion error) {
            getLogger().error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ERROR AL CONSUMIR RFC AMPLIADO " + error.getMessage(), error);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ERROR AL CONSUMIR RFC AMPLIADO " + e.getMessage(), e);
        }
    }

    public boolean rfcAmpliadoValido(IdCInterno datosContribuyente, TipoTramiteEnum tipoTramite) throws ServiceException {
        if (ValidacionDatos.informacionRfcAmpliadoNoValida(datosContribuyente)) {

            if (tipoTramite != null && tipoTramite.equals(TipoTramiteEnum.REPCION_GEST_TRAM_DEV_AUT)) {
                throw new ServiceException(CodigosDeError.MOTIVO_ERROR_PROBLEMA_REGISTO_O_DYC.getCodigo(), CodigosDeError.MOTIVO_ERROR_PROBLEMA_REGISTO_O_DYC.getDescripcion(), CodigosDeError.MOTIVO_ERROR_PROBLEMA_REGISTO_O_DYC.getDescripcion() + datosContribuyente);
            }

            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "LOS DATOS DE RFC_AMPLIDADO TIENEN INCONSISTENCIA " + datosContribuyente);
        }

        return Boolean.TRUE;
    }

    public DyctSaldoIcepDTO buscarRegistroIcep() {
        DyctSaldoIcepDTO registroIcep = encontrarRegistroIcep();

        if (registroIcep == null
                || registroIcepSinResultados(registroIcep)) {

            return null;
        }

        return registroIcep;
    }

    private DyctSaldoIcepDTO encontrarRegistroIcep() {
        try {

            String rfc = getRfcEntrada();
            DyccConceptoDTO concepto = getConceptoEntradaDTO();
            DyccEjercicioDTO ejercicio = getEjercicioEntradaDTO();
            DyccPeriodoDTO periodo = getPeriodoEntradaDTO();
            Integer origenSaldo = getOrigenSaldoEntradaInteger();

            if (validaDatosRegistroICEP(rfc, concepto, ejercicio, periodo) && origenSaldo != null) {

                return getDyccDevolucionAutService().encontrarRegistroIcepContribuyente(rfc, concepto, ejercicio, periodo, origenSaldo);
            }
        } catch (DycDaoExcepcion error) {
            getLogger().error(error);
        }

        return null;
    }

    private boolean validaDatosRegistroICEP(String rfc, DyccConceptoDTO concepto,
            DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo) {

        if (rfc != null && concepto != null && ejercicio != null && periodo != null) {
            return Boolean.TRUE;
        }
        return false;
    }

    private boolean registroIcepSinResultados(DyctSaldoIcepDTO registroIcep) {
        return registroIcep == null || validaRegistroICEP(registroIcep);
    }

    private boolean validaRegistroICEP(DyctSaldoIcepDTO registroIcep) {
        return (registroIcep.getRfc() == null && registroIcep.getBoId() == null)
                || (registroIcep.getBoId() != null && registroIcep.getBoId().equals(ConstantesProceso.ICEP_NO_ENCONTRADO));
    }

    public NotificacionDevManual getResultado() {
        getLogger().info(LOG_MENSAJE + serialVersionUID + " " + ProcesoDictaminadorAutImpl.class.hashCode());
        return generaSalida();
    }

    public DyccConceptoDTO getConceptoEntradaDTO() {
        BigInteger valorEntrada = getConceptoEntrada();

        if (valorEntrada == null) {
            return null;
        }

        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();

        dyccConceptoDTO.setIdConcepto(valorEntrada.intValue());

        return dyccConceptoDTO;
    }

    public BigInteger getConceptoEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getConcepto();
    }

    public DyccEjercicioDTO getEjercicioEntradaDTO() {
        BigInteger valorEntrada = getEjercicioEntrada();

        if (valorEntrada == null) {
            return null;
        }

        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();

        dyccEjercicioDTO.setIdEjercicio(valorEntrada.intValue());

        return dyccEjercicioDTO;
    }

    public BigInteger getEjercicioEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getEjercicio();
    }

    public String getEjercicioEntradaString() {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getEjercicio().toString();
    }

    public DyccPeriodoDTO getPeriodoEntradaDTO() {
        String valorEntrada = getPeriodoEntrada();

        if (valorEntrada == null) {
            return null;
        }

        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();

        dyccPeriodoDTO.setIdPeriodo(Integer.parseInt(valorEntrada));

        return dyccPeriodoDTO;
    }

    public String getPeriodoEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosICEP() == null) {

            return null;
        }

        return registroEntrada.getDatosICEP().getPeriodo();
    }

    public Integer getOrigenSaldoEntradaInteger() {
        BigInteger valorEntrada = getOrigenSaldoEntrada();

        if (valorEntrada == null) {
            return null;
        }

        Integer valor = valorEntrada.intValue();

        return valor;
    }

    public BigInteger getOrigenSaldoEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosTramite() == null) {

            return null;
        }

        return registroEntrada.getDatosTramite().getOrigenSaldo();
    }

    public String generaNumeroControlDevolucionAutomatica(IdCInterno datosContribuyente) throws ServiceException {

        String rfc = getRfcEntrada();
        String anioActual = getAnioActual();

        String claveSir = getClaveSir(rfc, datosContribuyente);
        String codigoADR = claveSir;

        String consecutivo = getConsecutivoDevolucionAutomatica(claveSir);

        return creaNumeroControlDevolucionAutomatica(codigoADR, anioActual, consecutivo);
    }

    public String creaNumeroControlDevolucionAutomatica(String codigoADR, String anio, String consecutivo) {
        StringBuilder numeroCotrolDevolucionAutomatica = new StringBuilder();

        numeroCotrolDevolucionAutomatica
                .append(ConstantesProceso.PREFIJO_DEVOLUCION_AUTOMATICA)
                .append(codigoADR)
                .append(anio)
                .append(consecutivo);

        return numeroCotrolDevolucionAutomatica.toString();
    }

    public String getClaveSir(String rfc, IdCInterno datosContribuyente) {

        if (datosContribuyente != null) {
            return getDyccDevolucionAutService().obtenerRfcAmpliadoClaveSir(datosContribuyente);
        } else {
            return "";
        }
    }

    public String getAnioActual() {
        return DycFechaUtil.dateToString(new Date(), ConstantesProceso.FORMATO_ANIOS_NUMERO_CONTROL);
    }

    public String getConsecutivoDevolucionAutomatica(String claveSir) throws ServiceException {
        String consecutivoDevolucionAutomatica = "";

        try {
            consecutivoDevolucionAutomatica = getDyccDevolucionAutService().getNumeroConsecutivoDevolucionAutomatica(claveSir);
        } catch (Exception error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GENERO EL CONSECUTIVO " + error.getMessage(), error);
        }

        return consecutivoDevolucionAutomatica;
    }

    private void registrarServicio(String numeroControl, IdCInterno datosContribuyente,
            DyctSaldoIcepDTO registroSaldoIcep) throws ServiceException {

        DycpSolicitudDTO solicitud = getSolicitudServicioTramite(numeroControl, datosContribuyente, registroSaldoIcep);
        Integer numEmpleado = null;

        try {
            getDyccDevolucionAutService().insertaServicioSol(solicitud, numEmpleado);
        } catch (Exception error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GENERO EL REGISTRO SERVICIO SOLICITUD " + error.getMessage(), error);
        }

    }

    private DycpSolicitudDTO getSolicitudServicioTramite(String numeroControl, IdCInterno datosContribuyente, DyctSaldoIcepDTO registroSaldoIcep) {

        DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        DycaServOrigenDTO dycaServOrigenDTO = new DycaServOrigenDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        DyccEstadoSolDTO dyccEstadoSolDTO = new DyccEstadoSolDTO();
        DyccActividadDTO dyccActividadDTO = new DyccActividadDTO();
        DyccSubTramiteDTO dyccSubtramiteDTO = new DyccSubTramiteDTO();
        DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO = new DyccSubOrigSaldoDTO();

        Integer idOrigenSaldo = getOrigenSaldoEntradaInteger();
        Integer tipoTramite = ConstantesProceso.ID_TIPO_TRAMITE;
        String rfcContribuyente = getRfcEntrada();
        Integer claveAdm = Integer.parseInt(getClaveSir(rfcContribuyente, datosContribuyente));
        String boid = datosContribuyente.getBoid();
        Date fechaInicioTramite = new Date();
        BigDecimal importeSolicitado = getImporteSaldoAFavorEntrada();

        dyccOrigenSaldoDTO.setIdOrigenSaldo(idOrigenSaldo);
        dyccTipoTramiteDTO.setIdTipoTramite(tipoTramite);

        dycpServicioDTO.setRfcContribuyente(rfcContribuyente);
        dycpServicioDTO.setClaveAdm(claveAdm);
        dycpServicioDTO.setBoid(boid);

        dyctSaldoIcepDTO.setIdSaldoIcep(registroSaldoIcep.getIdSaldoIcep());

        dyccEstadoSolDTO.setIdEstadoSolicitud(ConstantesProceso.EN_PROCESO_SAD);

        dyccSubtramiteDTO.setIdSubTipoTramite(ConstantesProceso.ID_SUB_TIPO_TRAMITE);

        dyccSubOrigSaldoDTO.setIdSuborigenSaldo(ConstantesProceso.ID_SUB_ORIGEN_SALDO);

        dyccActividadDTO.setDyccSubOrigSaldoDTO(dyccSubOrigSaldoDTO);

        solicitud.getCadenaOriginal();
        solicitud.setEsCertificado(Boolean.FALSE);
        solicitud.setImporteSolicitado(importeSolicitado);
        solicitud.setFechaInicioTramite(fechaInicioTramite);
        solicitud.setFechaPresentacion(fechaInicioTramite);
        solicitud.setNumControl(numeroControl);

        dycaServOrigenDTO.setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);

        dycaOrigenTramiteDTO.setDycaServOrigenDTO(dycaServOrigenDTO);
        dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);

        dycpServicioDTO.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);

        solicitud.setDyccSubtramiteDTO(dyccSubtramiteDTO);
        solicitud.setDyccActividadDTO(dyccActividadDTO);
        solicitud.setDyccEstadoSolDTO(dyccEstadoSolDTO);
        solicitud.setDycpServicioDTO(dycpServicioDTO);
        solicitud.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);

        return solicitud;
    }

    public DyctSaldoIcepDTO getRegistroSaldoIcep() {
        DyctSaldoIcepDTO registroSaldoIcep = new DyctSaldoIcepDTO();

        String rfc = getRfcEntrada();
        DyccPeriodoDTO periodo = getPeriodoEntradaDTO();
        DyccConceptoDTO concepto = getConceptoEntradaDTO();
        DyccEjercicioDTO ejercicio = getEjercicioEntradaDTO();
        DyccOrigenSaldoDTO origenSaldo = getOrigenSaldoEntradaDTO();

        registroSaldoIcep.setRfc(rfc);
        registroSaldoIcep.setDyccConceptoDTO(concepto);
        registroSaldoIcep.setDyccEjercicioDTO(ejercicio);
        registroSaldoIcep.setDyccPeriodoDTO(periodo);
        registroSaldoIcep.setDyccOrigenSaldoDTO(origenSaldo);

        return registroSaldoIcep;
    }

    private DyccOrigenSaldoDTO getOrigenSaldoEntradaDTO() {
        Integer valorEntrada = getOrigenSaldoEntradaInteger();

        if (valorEntrada == null) {
            return null;
        }

        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();

        dyccOrigenSaldoDTO.setIdOrigenSaldo(valorEntrada);

        return dyccOrigenSaldoDTO;
    }

    public void generarRegistroSaldoIcep(DyctSaldoIcepDTO registroSaldoIcep) throws ServiceException {
        try {
            getDyccDevolucionAutService().guardarRegistroSaldoIcep(registroSaldoIcep);
        } catch (Exception error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ERROR AL GUARDAR EL REGISTRO SALDO ICEP " + error.getMessage(), error);
        }
    }

    public boolean registroTieneIdentificadorSaldoIcep(DyctSaldoIcepDTO registroSaldoIcep) {
        return registroSaldoIcep.getIdSaldoIcep() != null;
    }

    public void guardarDatosContribuyente(String numeroControl, IdCInterno datosContribuyente) throws ServiceException {

        Date fechaConsulta = new Date();
        try {
            getCrearCasoAutomaticasIvaService().insertarContribuyente(numeroControl, fechaConsulta, datosContribuyente);
        } catch (DycServiceExcepcion error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GENERO EL contribuyente " + error.getMessage(), error);
        }
    }

    private XMLGregorianCalendar getFechaPresentacionEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null) {

            return null;
        }

        return registroEntrada.getDatosDeclaracion().getFechaPresentacion();
    }

    private Date getFechaPresentacionEntradaDate() {
        XMLGregorianCalendar fechaPresentacionEntrada = getFechaPresentacionEntrada();
        Date fechaPresentacion = null;

        if (fechaPresentacionEntrada != null) {
            fechaPresentacion = fechaPresentacionEntrada.toGregorianCalendar().getTime();
        }

        return fechaPresentacion;
    }

    private BigDecimal getImporteSaldoAFavorEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null) {

            return null;
        }

        return registroEntrada.getDatosDeclaracion().getImporteSaldoAFavor();
    }

    private void guardarDatosTrarmiteISR(String numeroControl) throws ServiceException {
        String cuentaClabe = getCuentaClabeEntrada();
        Date fechaPresentacion = getFechaPresentacionEntradaDate();
        try {
            Integer idArchivo = getDyccDevolucionAutService().insertarCuentaBancoArchivoDevAut(numeroControl, fechaPresentacion);

            getCrearCasoAutomaticasIvaService().insertarCuentaBanco(
                    cuentaClabe,
                    numeroControl,
                    fechaPresentacion,
                    idArchivo
            );

        } catch (Exception error) {
            getLogger().error(error);
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GENERO LA CUENTA BANCO " + error.getMessage(), error);
        }
    }

    private String getCuentaClabeEntrada() {
        if (registroEntrada == null
                || registroEntrada.getInfoBanco() == null) {

            return null;
        }

        return registroEntrada.getInfoBanco().getCuentaCLABE();
    }

    private void generarTramiteDeclaracionControlSaldos(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep) throws ServiceException {
        DyctDeclaracionDTO tramiteControlSaldos = getTramiteControlSaldos(numeroControl);
        DyctDeclaraIcepDTO declaracionIcep = getDeclaracionIcep(registroSaldoIcep);

        guardarTramiteControlSaldos(tramiteControlSaldos);
        guardarTramiteDeclaracionIcep(declaracionIcep);
    }

    private DyctDeclaracionDTO getTramiteControlSaldos(String numeroControl) {
        DyctDeclaracionDTO tramiteControlSaldos = new DyctDeclaracionDTO();
        DyccUsoDecDTO dyccUsoDecDTO = new DyccUsoDecDTO();
        DyccTipoDeclaraDTO dyccTipoDeclaraDTO = new DyccTipoDeclaraDTO();

        BigDecimal importe = getImporteSaldoAFavorEntrada();
        String numeroOperacion = getNumeroOperacionEntradaString();
        Date fechaPresentacion = getFechaPresentacionEntradaDate();
        Calendar calHoy = Calendar.getInstance();
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.setTime(fechaPresentacion);
        cal.set(Calendar.HOUR, calHoy.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, calHoy.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calHoy.get(Calendar.SECOND));
        fechaPresentacion = cal.getTime();

        dyccUsoDecDTO.setIdUsoDec(ConstantesProceso.ID_USO_DEC_DEV_AUTOMATICAS);
        dyccTipoDeclaraDTO.setIdTipoDeclaracion(registroEntrada.getDatosDeclaracion().getTipoDeclaracion().intValue());

        tramiteControlSaldos.setImporte(importe);
        tramiteControlSaldos.setNumControl(numeroControl);
        tramiteControlSaldos.setSaldoAfavor(importe);
        tramiteControlSaldos.setNumOperacion(numeroOperacion);
        tramiteControlSaldos.setDyccUsoDecDTO(dyccUsoDecDTO);
        tramiteControlSaldos.setFechaPresentacion(fechaPresentacion);
        tramiteControlSaldos.setDyccTipoDeclaraDTO(dyccTipoDeclaraDTO);

        return tramiteControlSaldos;
    }

    private DyctDeclaraIcepDTO getDeclaracionIcep(DyctSaldoIcepDTO registroSaldoIcep) {
        DyctDeclaraIcepDTO declaracionIcep = new DyctDeclaraIcepDTO();

        DyccTipoDeclaraDTO tipoDeclaraDTO = new DyccTipoDeclaraDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO = registroSaldoIcep;

        Date fechaPresentacion = getFechaPresentacionEntradaDate();

        Calendar calHoy = Calendar.getInstance();
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.setTime(fechaPresentacion);
        cal.set(Calendar.HOUR, calHoy.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, calHoy.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calHoy.get(Calendar.SECOND));
        fechaPresentacion = cal.getTime();

        Date fechaValidacionDWH = new Date();
        BigInteger numeroOperacion = getNumeroOperacionEntrada();
        BigDecimal importeSaldoAFavor = getImporteSaldoAFavorEntrada();

        tipoDeclaraDTO.setIdTipoDeclaracion(registroEntrada.getDatosDeclaracion().getTipoDeclaracion().intValue());

        declaracionIcep.setDyccTipoDeclaraDTO(tipoDeclaraDTO);
        declaracionIcep.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);

        declaracionIcep.setNumOperacion(numeroOperacion.longValue());
        declaracionIcep.setFechaPresentacion(fechaPresentacion);
        declaracionIcep.setSaldoAFavor(importeSaldoAFavor);
        declaracionIcep.setValidacionDWH(fechaValidacionDWH);
        declaracionIcep.setOrigenDeclara(Constantes.OrigenesDeclaracion.DATAWAREHOUSE);
        declaracionIcep.setFechaRegistro(fechaValidacionDWH);

        return declaracionIcep;
    }

    private DyctMovSaldoDTO getMovimientoSaldoIcep(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep) {
        DyctMovSaldoDTO movimientoSaldoIcep = new DyctMovSaldoDTO();

        BigDecimal importe = getImporteSaldoAFavorEntrada();
        String idOrigen = numeroControl;
        Date fechaOrigen = getFechaPresentacionEntradaDate();
        Date fechaRegistro = new Date();
        DyccMovIcepDTO dyccMovIcepDTO = Constantes.MovsIcep.ABONO_SAFDWH;
        DyctSaldoIcepDTO dyctSaldoIcepDTO = registroSaldoIcep;

        movimientoSaldoIcep.setImporte(importe);
        movimientoSaldoIcep.setIdOrigen(idOrigen);
        movimientoSaldoIcep.setFechaOrigen(fechaOrigen);
        movimientoSaldoIcep.setFechaRegistro(fechaRegistro);
        movimientoSaldoIcep.setDyccMovIcepDTO(dyccMovIcepDTO);
        movimientoSaldoIcep.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);

        return movimientoSaldoIcep;
    }

    private BigInteger getNumeroOperacionEntrada() {
        if (registroEntrada == null
                || registroEntrada.getDatosDeclaracion() == null) {

            return null;
        }

        return registroEntrada.getDatosDeclaracion().getNumOperacion();
    }

    private String getNumeroOperacionEntradaString() {
        BigInteger valor = getNumeroOperacionEntrada();

        if (valor != null) {

            return valor.toString();
        }

        return null;
    }

    private void generarEstadoTramiteSalida(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep, BigDecimal remanente, BigDecimal importeResuelto) {
        DatosSolicitud datosSolicitud = getSolicitudRegistroExitoso();
        DatosRegistroMATDYC datosRegistroMATDYC = getDatosRegistroMATDYC(numeroControl, registroSaldoIcep, remanente, importeResuelto);

        registroSalida.setDatosSolicitud(datosSolicitud);
        registroSalida.setDatosRegistroMATDYC(datosRegistroMATDYC);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public NotificacionDevManual execute(DevolucionManual devolucionManual, TipoTramiteEnum tipoTramite) throws ServiceException {

        inicializaEntradas(devolucionManual);
        //Reg. Aut.
        procesaRegistro(tipoTramite);

        return getResultado();
    }

}
