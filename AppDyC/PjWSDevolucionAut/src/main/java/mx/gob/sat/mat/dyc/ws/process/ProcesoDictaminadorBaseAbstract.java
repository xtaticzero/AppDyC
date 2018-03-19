/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import mx.gob.sat.mat.dyc.automaticasiva.service.CrearCasoAutomaticasIvaService;
import mx.gob.sat.mat.dyc.devolucionaut.service.DyccDevolucionAutService;
import mx.gob.sat.mat.dyc.integrarexpediente.service.IntegrarExpedienteService;
import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.DatosRegistroMATDYC;
import mx.gob.sat.mat.dyc.ws.domain.DatosSolicitud;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.rfcampl.service.IRfcAmplService;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public abstract class ProcesoDictaminadorBaseAbstract implements Serializable {

    private static final long serialVersionUID = 408731925962889849L;

    private final transient Logger logger;

    @Autowired(required = true)
    private transient IRfcAmplService rfcAmplService;

    @Autowired
    private transient DyccDevolucionAutService dyccDevolucionAutService;

    @Autowired
    private transient CrearCasoAutomaticasIvaService crearCasoAutomaticasIvaService;

    @Autowired
    private transient IntegrarExpedienteService integrarExpedienteService;

    public ProcesoDictaminadorBaseAbstract() {
        logger = Logger.getLogger(getClass());
    }

    protected DycpSolicitudDTO getSolicitudTramite(String numeroControl) {

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

    protected DatosSolicitud getSolicitudRegistroExitoso() {
        DatosSolicitud datosSolicitud = new DatosSolicitud();
        datosSolicitud.setEstadoRegistro(BigInteger.valueOf(ConstantesProceso.ESTADO_REGISTRO_EXITOSO));
        return datosSolicitud;
    }

    protected DatosRegistroMATDYC getDatosRegistroMATDYC(String numeroControl, DyctSaldoIcepDTO registroSaldoIcep, BigDecimal remanente, BigDecimal importeResuelto) {
        DatosRegistroMATDYC datosRegistroMATDYC = new DatosRegistroMATDYC();

        datosRegistroMATDYC.setFolioMATDYC(numeroControl);
        datosRegistroMATDYC.setIDICEP(BigInteger.valueOf(registroSaldoIcep.getIdSaldoIcep()));
        datosRegistroMATDYC.setSaldoICEP(remanente);
        datosRegistroMATDYC.setImporteResuelto(importeResuelto);

        return datosRegistroMATDYC;
    }

    protected ControlSaldoImportesDTO obtenerSaldoImportes(Integer idSaldoIcep) throws ServiceException {
        try {
            return dyccDevolucionAutService.obtenerSaldoImportes(idSaldoIcep);
        } catch (Exception e) {
            throw new ServiceException("NO RECUPERO EL REMANENTE", e);
        }
    }

    protected List<DycpDatosSolicitudDTO> consultaSolicitud(Integer idSaldoIcep) throws ServiceException {
        try {

            return dyccDevolucionAutService.obtenerDatosSolicitud(idSaldoIcep);
        } catch (Exception error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "ERROR AL  OBTENER  LA SOLICITUD", error);
        }

    }

    protected List<DyctDeclaracionDTO> consultaDeclaracion(String numeroOperacion, String rfc) throws ServiceException {
        try {
            return dyccDevolucionAutService.obtenerDeclaracion(numeroOperacion, rfc);
        } catch (Exception e) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), " ERROR AL OBTENER LA DECLARACION", e);
        }
    }

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

    protected void guardarTramiteControlSaldos(DyctDeclaracionDTO tramiteControlSaldos) throws ServiceException {
        try {
            dyccDevolucionAutService.guardarDeclaracion(tramiteControlSaldos);
        } catch (DycDaoExcepcion error) {
            getLogger().error(error);
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO  GUARDO LA DECLARACION " + error.getMessage(), error);
        }
    }

    protected void guardarTramiteDeclaracionIcep(DyctDeclaraIcepDTO declaracionIcep) throws ServiceException {
        try {
            dyccDevolucionAutService.guardarDeclaracionIcep(declaracionIcep);
        } catch (SIATException error) {
            getLogger().error(error);
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GUARDO LA DECLARACION ICEP " + error.getMessage(), error);
        }
    }

    protected void guardarMovimientoIcep(DyctMovSaldoDTO movimientoSaldoIcep) throws ServiceException {
        try {
            dyccDevolucionAutService.guardarMovientoSaldoIcep(movimientoSaldoIcep);
        } catch (SIATException error) {
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GUARDO EL MOVIMIENTO ICEP " + error.getMessage(), error);
        }
    }

    protected void registrarExpediente(String numeroControl) throws ServiceException {
        DycpSolicitudDTO solicitud = getSolicitudTramite(numeroControl);
        try {
            integrarExpedienteService.crearExpediente(solicitud, null, null, null);
        } catch (Exception error) {
            getLogger().error(error);
            throw new ServiceException(ConstantesProceso.REGISTRO_NO_EXITOSO, CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), "NO GUARDO EL EXPEDIENTE " + error.getMessage(), error);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public IRfcAmplService getRfcAmplService() {
        return rfcAmplService;
    }

    public void setRfcAmplService(IRfcAmplService rfcAmplService) {
        this.rfcAmplService = rfcAmplService;
    }

    public DyccDevolucionAutService getDyccDevolucionAutService() {
        return dyccDevolucionAutService;
    }

    public void setDyccDevolucionAutService(DyccDevolucionAutService dyccDevolucionAutService) {
        this.dyccDevolucionAutService = dyccDevolucionAutService;
    }

    public CrearCasoAutomaticasIvaService getCrearCasoAutomaticasIvaService() {
        return crearCasoAutomaticasIvaService;
    }

    public void setCrearCasoAutomaticasIvaService(CrearCasoAutomaticasIvaService crearCasoAutomaticasIvaService) {
        this.crearCasoAutomaticasIvaService = crearCasoAutomaticasIvaService;
    }

    public IntegrarExpedienteService getIntegrarExpedienteService() {
        return integrarExpedienteService;
    }

    public void setIntegrarExpedienteService(IntegrarExpedienteService integrarExpedienteService) {
        this.integrarExpedienteService = integrarExpedienteService;
    }

}
