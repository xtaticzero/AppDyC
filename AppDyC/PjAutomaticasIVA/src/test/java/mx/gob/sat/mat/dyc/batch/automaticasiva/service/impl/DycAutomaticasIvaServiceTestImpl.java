/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.batch.automaticasiva.service.impl;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;
import mx.gob.sat.mat.buzonnotif.client.MedioComunicacion;
import mx.gob.sat.mat.buzonnotif.service.IBuzonNotifService;
import mx.gob.sat.mat.buzonnotif.util.excepcion.BuzonNotifExcepcion;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicadoResponse;
import mx.gob.sat.mat.buzontrib.service.IBuzonTribService;
import mx.gob.sat.mat.buzontrib.util.excepcion.BuzonTribExcepcion;
import mx.gob.sat.mat.dyc.automaticasiva.service.CrearCasoAutomaticasIvaService;
import mx.gob.sat.mat.dyc.batch.automaticasiva.service.DycAutomaticasIvaService;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.constante.EDycAutomaticasIvaCodigoError;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion.DycAutomaticasIvaExcepcion;
import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ProcesarDeclaracionCompBussinesDel;
import mx.gob.sat.mat.dyc.integrarexpediente.service.IntegrarExpedienteService;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.service.IRfcAmplService;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;
import mx.gob.sat.siat.dyc.dao.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.DyctDevIvaDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.domain.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.domain.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.automaticasiva.domain.DycAutomaticasIvaInsertarSolicitudTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.DycControlSaldosObtenerIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.DycConstantesAutomaticasIva;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.util.constante.EDycServiceCodigoError;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import mx.gob.sat.siat.dyc.util.recurso.DycFechaUtil;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaProcesadoDTO;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

/**
 *
 * @author GAER8674
 */
@Service
public class DycAutomaticasIvaServiceTestImpl implements DycAutomaticasIvaService {
    private final Logger log = Logger.getLogger(DycAutomaticasIvaServiceTestImpl.class);
    
    @Value(DycConstantesAutomaticasIva.PROPERTIES_KEY_MORSA_MARCARESULTADO_POSITIVA)
    private String morsaMarcaPositiva;

    @Autowired
    @Qualifier("rfcAmplService")
    IRfcAmplService rfcAmplService;
    
    @Autowired
    @Qualifier("dyctDevIvaDAO")
    DyctDevIvaDAO dyctDevIvaDAO;
    
    @Autowired
    ProcesarDeclaracionCompBussinesDel procesarDeclaracionCompBussinesDel;

    @Autowired
    @Qualifier("solNumConsecutivoDAO")
    SolNumConsecutivoDAO solNumConsecutivoDAO;
    
    @Autowired
    @Qualifier("buzonNotifService")
    private IBuzonNotifService buzonNotifService;
    
    @Autowired
    @Qualifier("buzonTribService")
    IBuzonTribService buzonTribService;
    
    @Autowired
    @Qualifier("dycpSolicitudDAO")
    private DycpSolicitudDAO daoSolicitud;
    
    @Autowired
    @Qualifier("afectarSaldosXDevolucionesService")
    AfectarSaldosXDevolucionesService afectarSaldosXDevolucionesService;
    
    @Autowired
    @Qualifier("crearCasoAutomaticasIvaService")
    CrearCasoAutomaticasIvaService crearCasoAutomaticasIvaService;
    
    @Autowired
    @Qualifier("integrarExpedienteService")
    IntegrarExpedienteService integrarExpedienteService;
    
    private String numeroControl = null;

   
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, 
            rollbackFor = { Throwable.class })
    public DycAutomaticasIvaProcesadoDTO procesarDevolucionAutomaticaIva(DyctDevAutoIvaDTO registroMorsa) throws DycAutomaticasIvaExcepcion{
        DycAutomaticasIvaProcesadoDTO procesadoDTO = new DycAutomaticasIvaProcesadoDTO();

        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroLote", ""+registroMorsa.getNumeroLote()) );
        estadoVariables.add( new DycLogEstadoVariable("numeroOperacion", ""+registroMorsa.getNumeroOperacion()) );
        estadoVariables.add( new DycLogEstadoVariable("rfc", registroMorsa.getRfc()) );
                
        try{
            procesadoDTO.setRfc(registroMorsa.getRfc());
            Date hoy = new Date();
            Integer idSaldoIcep = null;
            Integer motivoRechazo = Integer.valueOf(registroMorsa.getMotivoRechazo());
        
            if(esMorsaMarcaResultadoPositiva(registroMorsa)){
                Date fechaConsultaARFCAmpliado = new Date();
                IdCInterno rfcAmpliado = consultarRfcAmpliado(registroMorsa.getRfc());
                String claveSir = obtenerRfcAmpliadoClaveSir(registroMorsa.getRfc(), rfcAmpliado);
                if(null==numeroControl){ // ONLY FOR DEVELOPMENT
                    numeroControl = generarNumeroControl(claveSir, new Date());
                }
                procesadoDTO.setNumeroControl(numeroControl);
                
                DyctSaldoIcepDTO saldoIcep = obtenerSaldoIcep(registroMorsa);
                if(!existeIcepCargadoEnSaldosDyc(saldoIcep)){
                    idSaldoIcep = guardarCasoDevolucionDatosIcep(numeroControl, registroMorsa);
                    procesadoDTO.setIdSaldoIcep(idSaldoIcep);
                    guardarCasoDevolucionSolicitud(numeroControl, registroMorsa, 
                            claveSir, rfcAmpliado, idSaldoIcep, fechaConsultaARFCAmpliado,
                            EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_TOTAL); 
                    integrarExpediente(numeroControl, registroMorsa.getRfc());
                    afectacionControlSaldos(numeroControl);
                    
                    cambiarDeclaracionMorsaEstado(registroMorsa, DycConstantesAutomaticasIva.DYCCESTADO_IDESTADO_PROCESADAACEPTADA);
                }
                else{
                    procesadoDTO.setIdSaldoIcep(saldoIcep.getIdSaldoIcep());
                    guardarCasoDevolucionSolicitud(numeroControl, registroMorsa, 
                            claveSir, rfcAmpliado, saldoIcep.getIdSaldoIcep(), fechaConsultaARFCAmpliado,
                            EDycAutomaticasIvaEstadoCasoDevolucion.DESISTIDA_X_SISTEMA); 
                    
                    Date vigenciaFin = obtenerNotificacionVigenciaFin(hoy);
                    if(enviarNotificacion(motivoRechazo, registroMorsa.getNombreContribuyente(), registroMorsa.getRfc(), hoy, vigenciaFin)){
                        cambiarDeclaracionMorsaEstado(registroMorsa, DycConstantesAutomaticasIva.DYCCESTADO_IDESTADO_PROCESADANEGADACONNOTIFICACION);
                    }
                    else{
                        cambiarDeclaracionMorsaEstado(registroMorsa, DycConstantesAutomaticasIva.DYCCESTADO_IDESTADO_PROCESADANEGADASINNOTIFICACION);
                    }
                }
            }
            else {
                Date vigenciaFin = obtenerNotificacionVigenciaFin(hoy);
                if(enviarNotificacion(motivoRechazo, registroMorsa.getNombreContribuyente(), registroMorsa.getRfc(), hoy, vigenciaFin)){
                    cambiarDeclaracionMorsaEstado(registroMorsa, DycConstantesAutomaticasIva.DYCCESTADO_IDESTADO_PROCESADANEGADACONNOTIFICACION);
                }
                else{
                    cambiarDeclaracionMorsaEstado(registroMorsa, DycConstantesAutomaticasIva.DYCCESTADO_IDESTADO_PROCESADANEGADASINNOTIFICACION);
                }
            }
        } catch (DycAutomaticasIvaExcepcion ex) {
            DycAutomaticasIvaExcepcion newEx = new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_DEVOLUCION_INDIVIDUAL, estadoVariables, ex);
            newEx.setDevolucionNoProcesada(procesadoDTO);
            throw newEx;
        } catch (RuntimeException ex) {
            DycAutomaticasIvaExcepcion newEx = new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_DEVOLUCION_INDIVIDUAL, estadoVariables, ex);
            newEx.setDevolucionNoProcesada(procesadoDTO);
            throw newEx;
        }
        
        return procesadoDTO;
    }
    
    /**
     * 
     * @param numeroLote
     * @return
     * @throws DycAutomaticasIvaExcepcion
     * @deprecated Solo es para pruebas.
     */
    @Deprecated
    public List<DyctDevAutoIvaDTO> consultarUnaDevolucionAutomaticaIVA(Long numeroLote) throws DycAutomaticasIvaExcepcion{
        List<DyctDevAutoIvaDTO> lote = consultarLoteDevolucionesAutomaticasIVA(numeroLote);
        List<DyctDevAutoIvaDTO> lotePrueba = new ArrayList<DyctDevAutoIvaDTO>();
        
        if(lote.size()>0){
            lotePrueba.add(lote.get(0));
        }
        
        return lotePrueba;
    }
    
    @Override
    public List<DyctDevAutoIvaDTO> consultarDevolucionesAutomaticasIVANuevas() throws DycAutomaticasIvaExcepcion{
        List<DyctDevAutoIvaDTO> loteDevoluciones = null;
        
        try {
            loteDevoluciones = dyctDevIvaDAO.selectNuevasDevoluciones();
        } catch (RuntimeException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.BD_MORSA_GENERAL, null, ex);
        }
        
        return loteDevoluciones;
    }
    
    @Deprecated
    public List<DyctDevAutoIvaDTO> consultarLoteDevolucionesAutomaticasIVA(Long numeroLote) throws DycAutomaticasIvaExcepcion{
        List<DyctDevAutoIvaDTO> loteDevoluciones = null;
        
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroLote", numeroLote.toString()) );
        
        try {
            loteDevoluciones = dyctDevIvaDAO.selectXNumeroLote(numeroLote);
        } catch (RuntimeException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.BD_MORSA_GENERAL, estadoVariables, ex);
        }
        
        return loteDevoluciones;
    }
    
    public boolean esMorsaMarcaResultadoPositiva(DyctDevAutoIvaDTO registroMora) {
        log.info("esMorsaMarcaResultadoPositiva - Inicio || marcaResultado=" + registroMora.getMarcaResultado());
        boolean esMorsaMarcaResultadoPositiva = false;
        
        if(registroMora.getMarcaResultado()!=null){
            esMorsaMarcaResultadoPositiva =
                registroMora.getMarcaResultado().equalsIgnoreCase(morsaMarcaPositiva);
        }

        log.info("esMorsaMarcaResultadoPositiva - Inicio");
        return esMorsaMarcaResultadoPositiva;
    }
    
    public IdCInterno consultarRfcAmpliado(String rfc) throws DycAutomaticasIvaExcepcion{
        IdCInterno rfcAmpliado = null;

        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("rfc", rfc) );
        
        try {
            rfcAmpliado = rfcAmplService.consultarXRfc(rfc);
            if(rfcAmpliado==null){
                throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.RFCAMPLIADO_RESPUESTA_NORECIBIDA, estadoVariables);
            }
        } catch (RfcAmplExcepcion ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.RFCAMPLIADO_GENERAL, estadoVariables, ex);
        } catch (RuntimeException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.RFCAMPLIADO_GENERAL, estadoVariables, ex);
        }
        
        return rfcAmpliado;
    }
    
    public String obtenerRfcAmpliadoClaveSir(String rfc, IdCInterno rfcAmpliado) throws DycAutomaticasIvaExcepcion{
        String claveSirEnmascarada = null;
        
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("rfc", rfc) );
        
        if(rfcAmpliado.getUbicacion()!=null){
            String claveSir = rfcAmpliado.getUbicacion().getCALR();
            claveSirEnmascarada = ConstantesDyC.MASCARA_CEROS_CLAVESIR.substring(claveSir.length());
            claveSirEnmascarada = claveSirEnmascarada + claveSir;
            
            if(claveSir == null){
                throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.RFCAMPLIADO_RESPUESTA_UBICACION_CLAVESIR_NORECIBIDA, estadoVariables);
            }
        }
        else{
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.RFCAMPLIADO_RESPUESTA_UBICACION_NORECIBIDA, estadoVariables);
        }
        
        return claveSirEnmascarada;
    }

    public String generarNumeroControl(String claveSir, Date fecha) throws DycAutomaticasIvaExcepcion {
        log.info("generarNumeroControl - Inicio || claveSir=" + claveSir + " || fecha=" + fecha);
        
        String numeroControl = null;
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("claveSir", claveSir) );
        estadoVariables.add( new DycLogEstadoVariable("fecha", fecha.toString()) );

        try {
            final String FECHAJAVA_FORMATO = "yy";
            String anioActual = DycFechaUtil.dateToString(fecha, FECHAJAVA_FORMATO);
            String consecutivo = solNumConsecutivoDAO.getNumConsecutivoDevIva(claveSir);
            
            numeroControl = DycConstantesAutomaticasIva.CASODEVOLUCION_NUMEROCONTROL_PREFIJO + claveSir + anioActual + consecutivo;
        } catch (DycDaoExcepcion ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_NUMEROCONTROL_GENERAL, estadoVariables, ex);
        }
        
        log.info("generarNumeroControl - Fin || numeroControl=" + numeroControl);
        return numeroControl;
    }
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, 
            rollbackFor = { Throwable.class })
    public Integer guardarCasoDevolucionDatosIcep(String numeroControl, DyctDevAutoIvaDTO registroMorsa) throws DycAutomaticasIvaExcepcion{
        log.info("guardarCasoDevolucionDatosIcep - Inicio || numeroControl="+numeroControl + " || concepto="+registroMorsa.getConcepto() 
                + " || ejercicio="+registroMorsa.getEjercicio() + " || periodo="+registroMorsa.getPeriodo() + " || rfc="+registroMorsa.getRfc());
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroControl", numeroControl) );
        
        Integer idSaldoIcep = null;
        
        DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO(null, null, null, registroMorsa.getConcepto(), null);
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO(null, null, registroMorsa.getEjercicio(), null);
        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO(null, null, null, registroMorsa.getPeriodo(), null, null);
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO(null, null, null, DycConstantesAutomaticasIva.DYCPSERVICIO_ORIGENSALDO_SALDOAFAVOR, null);
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO(null, dyccConceptoDTO, dyccEjercicioDTO, dyccPeriodoDTO, null, null, registroMorsa.getRfc());
        dyctSaldoIcepDTO.setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);

        solicitud.setNumControl(numeroControl);
        solicitud.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
        solicitud.setDycpServicioDTO(new DycpServicioDTO(numeroControl));
        
        solicitud.getDycpServicioDTO().setDyctDeclaracionList(new ArrayList<DyctDeclaracionDTO>());
        DyctDeclaracionDTO declaracionIcep = new DyctDeclaracionDTO();
        declaracionIcep.setNumOperacion(registroMorsa.getNumeroOperacion().toString());
        declaracionIcep.setFechaPresentacion(registroMorsa.getFechaHoraPresentacion());
        declaracionIcep.setDyccTipoDeclaraDTO(new DyccTipoDeclaraDTO(DycConstantesAutomaticasIva.DYCTDECLARACION_IDTIPODECLARACION_NORMAL));
        declaracionIcep.setSaldoAfavor(new BigDecimal(registroMorsa.getImporteSaldoFavor()));
        declaracionIcep.setNumDocumento(registroMorsa.getNumeroOperacion().toString()); /** De acuerdo al layout_solicitud-resolucion 1 3*/
        solicitud.getDycpServicioDTO().getDyctDeclaracionList().add(declaracionIcep);
        
        solicitud.getDycpServicioDTO().setRfcContribuyente(registroMorsa.getRfc());
        solicitud.getDycpServicioDTO().setDycaOrigenTramiteDTO(new DycaOrigenTramiteDTO(new DycaServOrigenDTO(),new DyccTipoTramiteDTO()));
        
        DyccConceptoDTO dyccConceptoDTO2 = new DyccConceptoDTO("Descripcion", new Date(), new Date(), registroMorsa.getConcepto(), new DyccImpuestoDTO());
        solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().setDyccConceptoDTO(dyccConceptoDTO2);
        
        try {
            // Para generar el abono en la partida doble de control de saldos.
            Map<String, Object> retorno = afectarSaldosXDevolucionesService.afectarXRegistro(solicitud);
            idSaldoIcep = (Integer) retorno.get("idSaldoIcep");
        } catch (SIATException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.BD_DYC_SALDOICEP_INSERT_ICEP, estadoVariables, ex);
        }
        
        log.info("guardarCasoDevolucionDatosIcep - Fin || idSaldoIcep=" + idSaldoIcep);
        return idSaldoIcep;
    }
    
    /**
     * <ul>
     *  <li>Una solicitud nace con estado RECIBIDO.</li>
     *  <li>Una solicitud nace con el tipo tramite 139 IVA DEVOLUCION AUTOMATICA.</li>
     * </ul>
     * @param registroMora 
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, 
            rollbackFor = { Throwable.class })
    public void guardarCasoDevolucionSolicitud(String numeroControl, DyctDevAutoIvaDTO registroMora, 
            String claveSir, IdCInterno rfcAmpliado, Integer idSaldoIcep, 
            Date fechaConsultaARFCAmpliado, EDycAutomaticasIvaEstadoCasoDevolucion estadoCasoDevolucion) throws DycAutomaticasIvaExcepcion{
        log.info("guardarCasoDevolucionSolicitud - Inicio || numeroControl="+numeroControl + " || rfc="+registroMora.getRfc() 
                + " || fechaPresentacion="+registroMora.getFechaHoraPresentacion() + " || saldoAFavor="+registroMora.getImporteSaldoFavor() 
                + " || claveSir="+claveSir + " || idSaldoIcep="+idSaldoIcep + " || fechaConsultaARFCAmpliado="+fechaConsultaARFCAmpliado 
                + " || estadoCasoDevolucion="+estadoCasoDevolucion.toString());
                
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable(DycConstantesAutomaticasIva.LOG_CASODEVOLUCION_NUMEROCONTROL, numeroControl) );
        estadoVariables.add( new DycLogEstadoVariable("claveSir", claveSir) );
        estadoVariables.add( new DycLogEstadoVariable("idSaldoIcep", idSaldoIcep.toString()) );
        estadoVariables.add( new DycLogEstadoVariable("fechaConsultaARFCAmpliado", fechaConsultaARFCAmpliado.toString()) );
        estadoVariables.add( new DycLogEstadoVariable("estadoCasoDevolucion", estadoCasoDevolucion.toString()) );
        
        try {
            int claveSiri = Integer.parseInt(claveSir);
            BigDecimal importeSaldoF = new BigDecimal(registroMora.getImporteSaldoFavor());
            crearCasoAutomaticasIvaService.insertarServicioAutomaticasIva(numeroControl, registroMora.getRfc(), claveSiri, rfcAmpliado.getBoid());
            
            DycAutomaticasIvaInsertarSolicitudTO solicitudTO = new DycAutomaticasIvaInsertarSolicitudTO();
            solicitudTO.setNumeroControl(numeroControl);
            solicitudTO.setFechaInicioTramite(registroMora.getFechaHoraPresentacion());
            solicitudTO.setImporteSolicitado(importeSaldoF);
            solicitudTO.setIdEstadoSolicitud(estadoCasoDevolucion.getIdEstadoSolicitud());
            solicitudTO.setResolAutomatica(DycConstantesAutomaticasIva.DYCPSOLICITUD_RESOLAUTOMATICA_ENABLE);
            solicitudTO.setIdSaldoIcep(idSaldoIcep);
            solicitudTO.setFechaPresentacion(registroMora.getFechaHoraPresentacion());
            crearCasoAutomaticasIvaService.insertarSolicitud(solicitudTO);
            
            crearCasoAutomaticasIvaService.insertarContribuyente(numeroControl, fechaConsultaARFCAmpliado, rfcAmpliado);
            
            crearCasoAutomaticasIvaService.insertarDeclaracion(registroMora.getFechaHoraPresentacion(), 
                    importeSaldoF, numeroControl, registroMora.getNumeroOperacion().toString());
            
            Integer idArchivo = crearCasoAutomaticasIvaService.insertarCuentaBancoArchivo(numeroControl);
            crearCasoAutomaticasIvaService.insertarCuentaBanco(registroMora.getNumeroCuentaClabe(), 
                    numeroControl, registroMora.getFechaHoraPresentacion(), idArchivo);
            
            crearCasoAutomaticasIvaService.insertarResolucion(numeroControl, registroMora.getFechaHoraPresentacion(), 
                    registroMora.getFechaHoraProcesamiento(), importeSaldoF, estadoCasoDevolucion);
        } catch (DycServiceExcepcion ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_CASODEVOLUCION_GENERAR_GENERAL, estadoVariables, ex);
        }
        
        log.info("guardarCasoDevolucionSolicitud - Fin");
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, 
            rollbackFor = { Throwable.class })
    public void afectacionControlSaldos(String numeroControl) throws DycAutomaticasIvaExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroControl", numeroControl) );
        
        try {
            // Para generar el cargo en la partida doble de control de saldos.
            afectarSaldosXDevolucionesService.afectarXResolucionDevAutomaticasIva(numeroControl);
        } catch (SIATException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.BD_DYC_CONTROLSALDOS_INSERT_MOVSALDO, estadoVariables, ex);
        }
    }    
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, 
            rollbackFor = { Throwable.class })
    public void cambiarDeclaracionMorsaEstado(DyctDevAutoIvaDTO registroMorsa, Integer estado){
        dyctDevIvaDAO.actualizaEstado(registroMorsa, estado);
    }
    
    public DyctSaldoIcepDTO obtenerSaldoIcep(DyctDevAutoIvaDTO registroMora) throws DycAutomaticasIvaExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("rfc", registroMora.getRfc()) );
        estadoVariables.add( new DycLogEstadoVariable("concepto", registroMora.getConcepto().toString()) );
        estadoVariables.add( new DycLogEstadoVariable("ejercicio", registroMora.getEjercicio().toString()) );
        estadoVariables.add( new DycLogEstadoVariable("periodo", registroMora.getPeriodo().toString()) );
        estadoVariables.add( new DycLogEstadoVariable("origenSaldo", ""+ConstantesDyC.SALDO_A_FAVOR) );
        
        DyctSaldoIcepDTO dyctSaldoIcepDTO = null;
        try {
            DycControlSaldosObtenerIcepDTO saldoIcep = new DycControlSaldosObtenerIcepDTO();
            saldoIcep.setRfc(registroMora.getRfc());
            saldoIcep.setIdConcepto(registroMora.getConcepto());
            saldoIcep.setIdEjercicio(registroMora.getEjercicio());
            saldoIcep.setIdPeriodo(registroMora.getPeriodo());
            saldoIcep.setIdOrigenSaldo(ConstantesDyC.SALDO_A_FAVOR);

            dyctSaldoIcepDTO = procesarDeclaracionCompBussinesDel.obtenerIcep(saldoIcep);
        } catch (DycServiceExcepcion ex) {
            if(ex.getCodigoError().equals(EDycServiceCodigoError.BD_DYC_SALDOICEP_NOENCONTRADO)){
                dyctSaldoIcepDTO = null;
            }
            else{
                throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.BD_DYC_SALDOICEP_QUERY_ICEP, estadoVariables);
            }
        }
        
        return dyctSaldoIcepDTO;
    }
    
    private boolean existeIcepCargadoEnSaldosDyc(DyctSaldoIcepDTO saldoIcep){
        return saldoIcep!=null;
    }
    
    private Date obtenerNotificacionVigenciaFin(Date vigenciaInicio){
        return DycFechaUtil.sumarDias(vigenciaInicio, DycConstantesAutomaticasIva.NOTIFICACION_CASODEVOLUCION_VIGENCIA);
    }
    
    public boolean enviarNotificacion(int idComunicado, String rfc, String razonSocial, Date vigenciaInicio, Date vigenciaFin) throws DycAutomaticasIvaExcepcion {
        log.info("enviarNotificacion - Inicio || idComunicado="+idComunicado + " || rfc="+rfc 
                + " || vigenciaInicio="+vigenciaInicio + " || vigenciaFin="+vigenciaFin);
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable(DycConstantesAutomaticasIva.LOG_GENERAL_RFC, rfc) );
        boolean fueEnviadoMensajeInteres = Boolean.TRUE;
        
        try {
            String medio = obtenerBuzonNotifMedio(rfc);
            //ONLY DEV
            medio = null;
            
            if(medio!=null && !medio.isEmpty()){
                String vigenciaInicioStr = DycFechaUtil.dateToString(vigenciaInicio, DycConstantesAutomaticasIva.NOTIFICACION_FECHAJAVA_FORMATOWS);
                String vigenciaFinStr = DycFechaUtil.dateToString(vigenciaFin, DycConstantesAutomaticasIva.NOTIFICACION_FECHAJAVA_FORMATOWS);
                
                estadoVariables.add( new DycLogEstadoVariable("idComunicado", String.valueOf(idComunicado)) );
                estadoVariables.add( new DycLogEstadoVariable("vigenciaInicio", vigenciaInicioStr) );
                estadoVariables.add( new DycLogEstadoVariable("vigenciaFin", vigenciaFinStr) );

                RegistraComunicado request = new RegistraComunicado();
                request.setIdComunicado(idComunicado);
                request.setRFC(rfc);
                request.setRazonSocial(razonSocial);
                request.setVigenciaIni(vigenciaInicioStr);
                request.setVigenciaFin(vigenciaFinStr);

                RegistraComunicadoResponse response = buzonTribService.enviarNotificacion(request);
                if(response!=null){ 
                    if(response.getRegistraComunicadoResult()!=null 
                            && response.getRegistraComunicadoResult().getCveError()>0){
                        estadoVariables.add( new DycLogEstadoVariable("cveError", String.valueOf(response.getRegistraComunicadoResult().getCveError())) );
                        estadoVariables.add( new DycLogEstadoVariable("descError", response.getRegistraComunicadoResult().getDescError()) );
                        log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.BUZONTRIB_RESPUESTA_NORECIBIDA, estadoVariables));
                        fueEnviadoMensajeInteres = false;
                    }
                }
                else{
                    log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.BUZONTRIB_RESPUESTA_NORECIBIDA, estadoVariables));
                    fueEnviadoMensajeInteres = false;
                }
            }
            else{
                log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.BUZONNOTIF_MEDIO_NOTIENE, estadoVariables));
                fueEnviadoMensajeInteres = false;
            }
        } catch (BuzonNotifExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.BUZONNOTIF_GENERAL, estadoVariables), ex);
            fueEnviadoMensajeInteres = false;
        } catch (BuzonTribExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.BUZONTRIB_GENERAL, estadoVariables), ex);
            fueEnviadoMensajeInteres = false;
        } catch (RuntimeException ex) {
            log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.BUZONTRIB_GENERAL, estadoVariables), ex);
            fueEnviadoMensajeInteres = false;
        }
        
        log.info("enviarNotificacion - Fin");
        return fueEnviadoMensajeInteres;
    }
    
    private String obtenerBuzonNotifMedio(String rfc) throws BuzonNotifExcepcion{
        List<MedioComunicacion> mediosComunicacion = buzonNotifService.obtieneMediosComunicacion(rfc);
        String ultimoMedio = null;
        
        for(MedioComunicacion medio : mediosComunicacion){
            ultimoMedio = medio.getMedio();
        }
        
        return ultimoMedio;
    }
    
    /**
     * 
     * @param subRol
     * @deprecated Eliminado del CU en la version 1.0.4 (2016-02-26).
     */
    @Deprecated
    private void relacionarSolicitudDevolucionConCompetencia(String subRol){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, 
            rollbackFor = { Throwable.class })
    public void integrarExpediente(String numeroControl, String rfc) throws DycAutomaticasIvaExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroControl", numeroControl) );
        estadoVariables.add( new DycLogEstadoVariable("rfc", rfc) );
        
        try {
            
            DycpSolicitudDTO solicitud = daoSolicitud.encontrar(numeroControl);
            solicitud.setNumControl(numeroControl);
            integrarExpedienteService.crearExpediente(solicitud, null, null, null);
        } catch (FileNotFoundException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_INTEGRAREXPEDIENTE_GENERAR_GENERAL, estadoVariables, ex);
        } catch (ClassNotFoundException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_INTEGRAREXPEDIENTE_GENERAR_GENERAL, estadoVariables, ex);
        } catch (SQLException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_INTEGRAREXPEDIENTE_GENERAR_GENERAL, estadoVariables, ex);
        } catch (JAXBException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_INTEGRAREXPEDIENTE_GENERAR_GENERAL, estadoVariables, ex);
        } catch (SAXException ex) {
            throw new DycAutomaticasIvaExcepcion(EDycAutomaticasIvaCodigoError.AUTOMATICAS_INTEGRAREXPEDIENTE_GENERAR_GENERAL, estadoVariables, ex);
        }
    }
}
