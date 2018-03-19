/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.dyc.automaticasiva.service.CrearCasoAutomaticasIvaService;
import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ProcesarDeclaracionCompBussinesDel;
import mx.gob.sat.mat.dyc.devolucionaut.service.DyccDevolucionAutService;
import mx.gob.sat.mat.dyc.integrarexpediente.service.IntegrarExpedienteService;
import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.service.IRfcAmplService;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.recurso.DycFechaUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public abstract class ProcesoVerificaDevBaseAbstract implements Serializable {

    private static final long serialVersionUID = -3925756785718363736L;

    private final transient Logger logger;

    @Autowired
    private transient IRfcAmplService rfcAmplService;

    @Autowired
    private transient ProcesarDeclaracionCompBussinesDel procesarDeclaracionCompBussinesDel;

    @Autowired
    private transient DyccDevolucionAutService dyccDevolucionAutService;

    @Autowired
    private transient CrearCasoAutomaticasIvaService crearCasoAutomaticasIvaService;

    @Autowired
    private transient IntegrarExpedienteService integrarExpedienteService;

    @Autowired
    private transient RegistroPistasAuditoria registroPistasAuditoria;

    @Autowired
    private transient DyccMensajeUsrService dyccMensajeUsrService;

    public ProcesoVerificaDevBaseAbstract() {
        logger = Logger.getLogger(getClass());
    }

    protected void guardarMovimientoIcep(DyctMovSaldoDTO movimientoSaldoIcep) throws ServiceException {
        try {
            dyccDevolucionAutService.guardarMovientoSaldoIcep(movimientoSaldoIcep);
        } catch (SIATException error) {
            logger.error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL GUARDAR EL MOVIMIENTO " + error.getMessage(), error);
        }
    }

    protected String getAnioActual() {
        return DycFechaUtil.dateToString(getFechaActual(), ConstantesProceso.FORMATO_ANIOS_NUMERO_CONTROL);
    }

    protected Date getFechaActual() {
        return new Date();
    }

    protected String getClaveSir(IdCInterno datosContribuyente) {

        if (datosContribuyente != null) {
            return dyccDevolucionAutService.obtenerRfcAmpliadoClaveSir(datosContribuyente);
        } else {
            return "";
        }
    }

    protected String getConsecutivoDevolucionAutomatica(String claveSir) throws ServiceException {
        String consecutivoDevolucionAutomatica = "";

        try {
            consecutivoDevolucionAutomatica = dyccDevolucionAutService.getNumeroConsecutivoDevolucionAutomatica(claveSir);
        } catch (Exception error) {
            logger.error(error.getMessage());
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL OBTENER LA SECUENCIA DE LA ADMINISTRACION " + claveSir + " " + error.getMessage(), error);
        }

        return consecutivoDevolucionAutomatica;
    }

    protected String creaNumeroControlDevolucionAutomatica(String codigoADR, String anio, String consecutivo) {
        StringBuilder numeroCotrolDevolucionAutomatica = new StringBuilder();

        numeroCotrolDevolucionAutomatica
                .append(ConstantesProceso.PREFIJO_DEVOLUCION_AUTOMATICA)
                .append(codigoADR)
                .append(anio)
                .append(consecutivo);

        return numeroCotrolDevolucionAutomatica.toString();
    }

    protected void registrarServicio(String numeroControl, IdCInterno datosContribuyente,
            DyctSaldoIcepDTO registroSaldoIcep, RegistroDevolucionAut registroEntrada) throws ServiceException {

        DycpSolicitudDTO solicitud = getSolicitudServicioTramite(numeroControl, datosContribuyente, registroSaldoIcep,
                registroEntrada);
        Integer numEmpleado;
        try {
            numEmpleado = dyccDevolucionAutService.asignarDictaminador(solicitud);
        } catch (SQLException e) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR AL CONSULTAR EL DICTAMINADOR ASIGNAR " + e.getMessage(), e);
        }

        if (numEmpleado != ConstantesDyCNumerico.VALOR_0) {
            try {
                dyccDevolucionAutService.generarServicioSolicitud(solicitud, numEmpleado);
            } catch (SIATException e) {
                throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                        "ERROR REGISTRAR SOLICITUD DE SERVICIO", e);
            }
        } else {
            String msg = "No se encontro dictaminador para la admon. --> " + solicitud.getClaveAdm() + " para el RFC " + solicitud.getRfcContribuyente();
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    msg);
        }
    }

    protected DycpSolicitudDTO getSolicitudServicioTramite(String numeroControl, IdCInterno datosContribuyente,
            DyctSaldoIcepDTO registroSaldoIcep, RegistroDevolucionAut registroEntrada) throws ServiceException {

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

        Integer idOrigenSaldo = getOrigenSaldoEntrada(registroEntrada);
        Integer tipoTramite = ConstantesProceso.ID_TIPO_TRAMITE;
        String rfcContribuyente = getRfcEntrada(registroEntrada);
        Integer claveAdm = Integer.parseInt(getClaveSir(datosContribuyente));
        String boid = datosContribuyente.getBoid();
        Date fechaInicioTramite = getFechaActual();
        BigDecimal importeSolicitado = getImporteSolicitadoEntrada(registroEntrada);

        dyccOrigenSaldoDTO.setIdOrigenSaldo(idOrigenSaldo);
        dyccTipoTramiteDTO.setIdTipoTramite(tipoTramite);

        dycpServicioDTO.setRfcContribuyente(rfcContribuyente);
        dycpServicioDTO.setClaveAdm(claveAdm);
        dycpServicioDTO.setBoid(boid);

        dyctSaldoIcepDTO.setIdSaldoIcep(registroSaldoIcep.getIdSaldoIcep());

        dyccEstadoSolDTO.setIdEstadoSolicitud(Constantes.EstadosSolicitud.PREAUTORIZADA.getIdEstadoSolicitud());

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
    
    private BigDecimal getImporteSolicitadoEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosDevolucionAutomatica() == null
                || registroEntrada.getDatosDevolucionAutomatica().getImporteSolicitado() == null) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR EL IMPORTE SOLICITADO ES REQUERIDO");
        }

        return registroEntrada.getDatosDevolucionAutomatica().getImporteSolicitado();
    }
    
    protected String getRfcEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosContribuyente() == null) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR EN LOS DATOS DE ENTRADA DEL CONTRIBUYENTE " + registroEntrada + " "
                    + (registroEntrada != null ? registroEntrada.getDatosContribuyente() : ""));
        }

        return registroEntrada.getDatosContribuyente().getRFC();
    }

    protected DyccOrigenSaldoDTO getOrigenSaldoEntradaDTO(RegistroDevolucionAut registroEntrada) throws ServiceException {
        Integer valorEntrada = getOrigenSaldoEntrada(registroEntrada);

        if (valorEntrada == null) {
            return null;
        }

        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();

        dyccOrigenSaldoDTO.setIdOrigenSaldo(valorEntrada);

        return dyccOrigenSaldoDTO;
    }

    protected Integer getOrigenSaldoEntrada(RegistroDevolucionAut registroEntrada) throws ServiceException {
        if (registroEntrada == null
                || registroEntrada.getDatosTramite() == null
                || null == registroEntrada.getDatosTramite().getOrigenSaldo()) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, ConstantesProceso.REGISTRO_NO_EXITOSO,
                    "ERROR ORIGEN SALDO ES REQUERIDO");
        }

        return registroEntrada.getDatosTramite().getOrigenSaldo().intValue();
    }
    
    /**
     * Realiza la busqueda de compensaciones relacionadas al ICEP.
     *
     * @param idSaldoIcep
     * @throws ServiceException
     */
    protected void consultaCompensacion(Integer idSaldoIcep) throws ServiceException {
        String comp = null;
        try {
            comp = dyccDevolucionAutService.getCompensacionPorIcep(idSaldoIcep);
        } catch (Exception error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ERROR AL OBTENER LA COMPENSACION" + error);
        }
        if (comp != null) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_TRAMITE_ASOCIADO.getCodigo(), "YA EXISTE UNA COMPENSACION CON ESE ICEP " + idSaldoIcep + " NUMCONTROL DE LA COMP: " + comp);
        }
    }
    
    protected List<DycpDatosSolicitudDTO> consultaSolicitud(Integer idSaldoIcep) throws ServiceException {
        try {

            return dyccDevolucionAutService.obtenerDatosSolicitud(idSaldoIcep);
        } catch (Exception error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO,
                    CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ERROR AL  OBTENER  LA SOLICITUD", error);
        }
    }
    
    protected void bloquearRegistroIcep(DyctSaldoIcepDTO registroSaldoIcep) {
        dyccDevolucionAutService.bloqueaRegistroSaldoIcep(registroSaldoIcep);
    }

    public Logger getLogger() {
        return logger;
    }

    public IRfcAmplService getRfcAmplService() {
        return rfcAmplService;
    }

    public ProcesarDeclaracionCompBussinesDel getProcesarDeclaracionCompBussinesDel() {
        return procesarDeclaracionCompBussinesDel;
    }

    public DyccDevolucionAutService getDyccDevolucionAutService() {
        return dyccDevolucionAutService;
    }

    public CrearCasoAutomaticasIvaService getCrearCasoAutomaticasIvaService() {
        return crearCasoAutomaticasIvaService;
    }

    public IntegrarExpedienteService getIntegrarExpedienteService() {
        return integrarExpedienteService;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

}
