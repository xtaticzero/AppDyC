/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ContribuyenteDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.DeclaracionDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.InconsistenciaDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.RechazoDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ResponseConsultarExistenciaTramiteDevAut;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ResponseConsultarDetalleTramiteDevAut;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ResultadoDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.RetroalimentacionPagoDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.TramiteConsultaDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.TramiteDevolucionDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.DedInconsistenciaDTO;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.RetInconsistenciaDTO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.InconsistenciaTramiteVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.RechazoTramiteVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.TramiteExisteConsultaVO;
import mx.gob.sat.siat.dyc.util.constante.enums.AceptarPropuestaEnum;
import mx.gob.sat.siat.dyc.util.constante.enums.CreditoFiscalEnum;
import mx.gob.sat.siat.dyc.util.constante.enums.EstadoDevISREnum;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoDeclaracionEnum;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoFirmaEnum;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public final class ConvertVOHelperViejo {

    private static final Logger LOG = Logger.getLogger(ConvertVOHelperViejo.class);
    public static final String PROCESO = "En proceso de validación";
    public static final String APROBADO = "Autorizada";
    public static final String RECHAZADAS = "Rechazada";
    public static final String RECHAZADACREDITOFISCAL = "Rechazada por crédito fiscal";
    public static final String PROCESOPAGO = "En proceso de pago";
    public static final String PAGADO = "Pagada";
    public static final String AUTORIZADAINCONSCUENTACLABE = "Autorizada con inconsistencia cuenta CLABE";


    private ConvertVOHelperViejo() {

    }

    public static DatosTramiteISRVO convertTODatosTramiteISRVO(ResponseConsultarExistenciaTramiteDevAut consulExistenciaVO) {

        DatosTramiteISRVO tramitesIsRVO = new DatosTramiteISRVO();
        List<TramiteExisteConsultaVO> tramitesExisten = new ArrayList<TramiteExisteConsultaVO>();
        TramiteExisteConsultaVO tramiteExiste = null;
        LOG.error("INICIO convertTODatosTramiteISRVO");

        tramitesIsRVO.setEstadoConsulta(consulExistenciaVO.getEstatusConsulta());
        tramitesIsRVO.setMensajeRespuestaConsulta(consulExistenciaVO.getMensajeRespuestaConsulta());
        tramitesIsRVO.setRfcContribuyente(consulExistenciaVO.getRfcContribuyente());
        tramitesIsRVO.setEjercicio(consulExistenciaVO.getEjercicio());
        tramitesIsRVO.setExisteTramites(consulExistenciaVO.getExisteTramites());

        LOG.error("consulExistenciaVO.getEstatusConsulta():" + consulExistenciaVO.getEstatusConsulta());
        LOG.error("consulExistenciaVO.getMensajeRespuestaConsulta():" + consulExistenciaVO.getMensajeRespuestaConsulta());
        LOG.error("consulExistenciaVO.getRfcContribuyente():" + consulExistenciaVO.getRfcContribuyente());
        LOG.error("consulExistenciaVO.getEjercicio():" + consulExistenciaVO.getEjercicio());
        LOG.error("consulExistenciaVO.getExisteTramites():" + consulExistenciaVO.getExisteTramites());

        for (TramiteConsultaDTO tramite : consulExistenciaVO.getTramites()) {
            tramiteExiste = new TramiteExisteConsultaVO();
            tramiteExiste.setIdEstadoProceso(tramite.getIdEstadoProceso());
            tramiteExiste.setDescripcionEstado(tramite.getDescripcionEstadoProceso());
            tramiteExiste.setFolioDeclaracion(tramite.getFolioDeclaracion());

            LOG.error(">>tramite.getIdEstadoProceso() :" + tramite.getIdEstadoProceso());
            LOG.error(">>tramite.getDescripcionEstadoProceso() :" + tramite.getDescripcionEstadoProceso());
            LOG.error(">>tramite.getFolioDeclaracion() :" + tramite.getFolioDeclaracion());

            tramitesExisten.add(tramiteExiste);
        }

        tramitesIsRVO.setTramitesExisteConsulta(tramitesExisten);
        LOG.info("FIN  convertTODatosTramiteISRVO");
        return tramitesIsRVO;
    }

    public static DatosTramiteISRVO convertTODatosTramiteISRVO(mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ResponseConsultarDetalleTramiteDevAut consulExistenciaVO) {

        if (consulExistenciaVO != null) {

            return obtenerDatosTramite(consulExistenciaVO.getTramiteDevolucion());

        }

        return null;

    }

    private static DatosTramiteISRVO obtenerDatosTramite(List<mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.TramiteDevolucionDTO> lstTramites) {
        DatosTramiteISRVO datosTramite = null;
        if (lstTramites != null) {
            datosTramite = new DatosTramiteISRVO();
            datosTramite.setMensajeRespuestaConsulta("EXITO");
            datosTramite.setRfcContribuyente("");
            datosTramite.setTramitesExisteConsulta(obtenerTramiteExisteConsultaVO(lstTramites));
        } else {
            datosTramite = new DatosTramiteISRVO();
            datosTramite.setMensajeRespuestaConsulta("EXITO");
            datosTramite.setRfcContribuyente("");
        }
        return datosTramite;
    }

    private static List<TramiteExisteConsultaVO> obtenerTramiteExisteConsultaVO(List<mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.TramiteDevolucionDTO> lstTramites) {
        List<TramiteExisteConsultaVO> lstDetalleTramitesVO = new ArrayList<TramiteExisteConsultaVO>();
        for (mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.TramiteDevolucionDTO tramiteDetalle : lstTramites) {
            TramiteExisteConsultaVO tramiteExistente = new TramiteExisteConsultaVO();
            //Agregar asignaciones
            lstDetalleTramitesVO.add(tramiteExistente);
        }
        return lstDetalleTramitesVO;
    }

    public static DatosTramiteISRDetalleVO convertTODatosTramiteISRDetalleVO(ResponseConsultarDetalleTramiteDevAut consultaDetalle) {
        DatosTramiteISRDetalleVO tramiteDetalleIsRVO = new DatosTramiteISRDetalleVO();
        DeclaracionDTO declaracion = null;
        ContribuyenteDTO contribuye = null;
        TramiteDevolucionDTO tramdev = null;
        List<InconsistenciaTramiteVO> inconsistencias = new ArrayList<InconsistenciaTramiteVO>();
        List<RechazoTramiteVO> rechazos = new ArrayList<RechazoTramiteVO>();
        ResultadoDTO res = null;
        RetroalimentacionPagoDTO retro = null;
        InconsistenciaTramiteVO inconsistencia = null;
        RechazoTramiteVO rechazo;

        LOG.error("INICIO convertTODatosTramiteISRDetalleVO");

        tramiteDetalleIsRVO.setEstadoConsulta(consultaDetalle.getEstatusConsulta());
        tramiteDetalleIsRVO.setMensajeRespuestaConsulta(consultaDetalle.getMensajeRespuestaConsulta());
        tramiteDetalleIsRVO.setFolioDeclaracion(consultaDetalle.getFolioDeclaracion());

        LOG.error("consultaDetalle.getEstatusConsulta() :" + consultaDetalle.getEstatusConsulta());
        LOG.error("consultaDetalle.getMensajeRespuestaConsulta() :" + consultaDetalle.getMensajeRespuestaConsulta());
        LOG.error("consultaDetalle.getFolioDeclaracion() :" + consultaDetalle.getFolioDeclaracion());

        if (consultaDetalle.getDeclaracion() != null) {

            declaracion = consultaDetalle.getDeclaracion();
            tramiteDetalleIsRVO.setCuetaClabe(declaracion.getCuentaCLABE());
            tramiteDetalleIsRVO.setDescripcionPeriodo(declaracion.getDescripcionPeriodo());
            tramiteDetalleIsRVO.setEjercicio(declaracion.getEjercicio());
            tramiteDetalleIsRVO.setFechaPresentacion(toDate(declaracion.getFechaPresentacion()));
            tramiteDetalleIsRVO.setFechaPresentacionCadena(toDateCadena(tramiteDetalleIsRVO.getFechaPresentacion()));
            tramiteDetalleIsRVO.setMontoISRAFavor(declaracion.getMontoIsrAFavor());
            tramiteDetalleIsRVO.setNombreBanco(declaracion.getNombreInstitucionBancaria());
            tramiteDetalleIsRVO.setIdPeriodo(declaracion.getPeriodo());
            tramiteDetalleIsRVO.setTipoDeclaracion(declaracion.getTipoDeclaracion());

            LOG.error(">>declaracion.getCuentaCLABE() :" + declaracion.getCuentaCLABE());
            LOG.error(">>declaracion.getDescripcionPeriodo() :" + declaracion.getDescripcionPeriodo());
            LOG.error(">>declaracion.getEjercicio() :" + declaracion.getEjercicio());
            LOG.error(">>declaracion.getFechaPresentacion() :" + declaracion.getFechaPresentacion());
            LOG.error(">>declaracion.getMontoIsrAFavor() :" + declaracion.getMontoIsrAFavor());
            LOG.error(">>declaracion.getNombreInstitucionBancaria() :" + declaracion.getNombreInstitucionBancaria());
            LOG.error(">>declaracion.getPeriodo() :" + declaracion.getPeriodo());
            LOG.error(">>declaracion.getTipoDeclaracion() :" + declaracion.getTipoDeclaracion());

            if (declaracion.getContribuyente() != null) {
                contribuye = consultaDetalle.getDeclaracion().getContribuyente();
                tramiteDetalleIsRVO.setApellidoPaterno(contribuye.getApellidoPaterno());
                tramiteDetalleIsRVO.setApellidoMaterno(contribuye.getApellidoMaterno());
                tramiteDetalleIsRVO.setNombres(contribuye.getNombre());

                LOG.error(">>>>contribuye.getApellidoPaterno() :" + contribuye.getApellidoPaterno());
                LOG.error(">>>>contribuye.getApellidoMaterno() :" + contribuye.getApellidoMaterno());
                LOG.error(">>>>contribuye.getNombre() :" + contribuye.getNombre());
            }
        }

        if (consultaDetalle.getTramiteDevolucion() != null) {
            tramdev = consultaDetalle.getTramiteDevolucion();
            tramiteDetalleIsRVO.setConcepto(tramdev.getConcepto());
            tramiteDetalleIsRVO.setFechaHoraEstado(toDate(tramdev.getFechaHoraEstado()));
            tramiteDetalleIsRVO.setFolioMATDYC(tramdev.getFolioMatDyC());
            tramiteDetalleIsRVO.setImpuesto(tramdev.getImpuesto());
            tramiteDetalleIsRVO.setSaldoAPagar(tramdev.getSaldoPagar());
            tramiteDetalleIsRVO.setTipoTramite(tramdev.getTipoTramite());

            LOG.error(">>>>tramdev.getConcepto() :" + tramdev.getConcepto());
            LOG.error(">>>>tramdev.getFechaHoraEstado() :" + tramdev.getFechaHoraEstado());
            LOG.error(">>>>tramdev.getFolioMatDyC() :" + tramdev.getFolioMatDyC());
            LOG.error(">>>>tramdev.getImpuesto() :" + tramdev.getImpuesto());
            LOG.error(">>>>tramdev.getSaldoPagar() :" + tramdev.getSaldoPagar());
            LOG.error(">>>>tramdev.getTipoTramite() :" + tramdev.getTipoTramite());

            if (tramdev.getResultado() != null) {
                res = tramdev.getResultado();
                tramiteDetalleIsRVO.setDescripcionResultado(res.getDescripcionResultado());
                tramiteDetalleIsRVO.setIdentificadorResultado(res.getIdentificadorResultado());

                LOG.error(">>>>res.getDescripcionResultado() :" + res.getDescripcionResultado());
                LOG.error(">>>>res.getIdentificadorResultado() :" + res.getIdentificadorResultado());
            }

            if (tramdev.getRetroalimentacionPago() != null) {
                retro = tramdev.getRetroalimentacionPago();
                tramiteDetalleIsRVO.setFechaPago(toDate(retro.getFechaPago()));
                tramiteDetalleIsRVO.setMotivoRechazoPago(retro.getMotivoRechazo() == null ? "" : retro.getMotivoRechazo());

                LOG.error(">>>>retro.getFechaPago() :" + retro.getFechaPago());
                LOG.error(">>>>retro.getMotivoRechazo() :" + retro.getMotivoRechazo());
            }

            for (InconsistenciaDTO inconsis : tramdev.getInconsistencias()) {
                inconsistencia = new InconsistenciaTramiteVO();
                inconsistencia.setAccionCorrectiva(inconsis.getAccionCorrectiva());
                inconsistencia.setDescripcion(inconsis.getDescripcion());
                inconsistencia.setEjercicio(inconsis.getEjercicioAccionCorrectiva());
                inconsistencia.setGenerarDevolucionManual(inconsis.getGeneraDevolucionManual());
                inconsistencia.setNumeroInconsistencia(inconsis.getNumeroInconsistencia());

                LOG.error(">>>>inconsis.getNumeroInconsistencia() :" + inconsis.getNumeroInconsistencia());
                LOG.error(">>>>inconsis.getAccionCorrectiva() :" + inconsis.getAccionCorrectiva());
                LOG.error(">>>>inconsis.getDescripcion() :" + inconsis.getDescripcion());
                LOG.error(">>>>inconsis.getEjercicioAccionCorrectiva() :" + inconsis.getEjercicioAccionCorrectiva());
                LOG.error(">>>>inconsis.getGeneraDevolucionManual() :" + inconsis.getGeneraDevolucionManual());

                inconsistencias.add(inconsistencia);
            }

            tramiteDetalleIsRVO.setInconsistenciasTramite(inconsistencias);

            for (RechazoDTO recha : tramdev.getRechazos()) {
                rechazo = new RechazoTramiteVO();
                rechazo.setAccionCorrectiva(recha.getAccionCorrectiva());
                rechazo.setDescripcion(recha.getDescripcionRechazo());
                rechazo.setEjercicio(recha.getEjercicioAccionCorrectiva());
                rechazo.setGenerarDevolucionManual(recha.getGeneraDevolucionManual());
                rechazo.setNumeroRechazo(recha.getNumeroRechazo());
                rechazos.add(rechazo);

                LOG.error(">>>>recha.getAccionCorrectiva() :" + recha.getAccionCorrectiva());
                LOG.error(">>>>recha.getDescripcionRechazo() :" + recha.getDescripcionRechazo());
                LOG.error(">>>>recha.getEjercicioAccionCorrectiva() :" + recha.getEjercicioAccionCorrectiva());
                LOG.error(">>>>recha.getGeneraDevolucionManual() :" + recha.getGeneraDevolucionManual());
                LOG.error(">>>>recha.getNumeroRechazo() :" + recha.getNumeroRechazo());

            }
            tramiteDetalleIsRVO.setRechazosTramite(rechazos);

        }

        return tramiteDetalleIsRVO;

    }

    public static List<DatosTramiteISRDetalleVO> convertTODatosTramiteISRDetalleVO(mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ResponseConsultarDetalleTramiteDevAut consultaDetalle, List<TramiteExisteConsultaVO> lstFolios) {

        mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.RetroalimentacionPagoDTO retro = null;
        mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.DeclaracionDTO declaracion = null;
        mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ContribuyenteDTO contribuye = null;

        List<DatosTramiteISRDetalleVO> lstDetalles = new ArrayList<DatosTramiteISRDetalleVO>();
        List<InconsistenciaTramiteVO> inconsistencias;
        List<RechazoTramiteVO> rechazos;

        DatosTramiteISRDetalleVO tramiteDetalleIsRVO;
        InconsistenciaTramiteVO inconsistencia = null;
        RechazoTramiteVO rechazo;
        Map<Integer, String> enPreprocesoCatNuevo = new HashMap<Integer, String>();
        LOG.error("INICIO convertTODatosTramiteISRDetalleVO v2017");

        //En proceso de validacion
        enPreprocesoCatNuevo.put(EstadoDevISREnum.PROCESO.getId(), PROCESO);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.REPROCESO.getId(), PROCESO);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.REVISION_POR_USUARIO.getId(), PROCESO);
        
        //Autorizadas
        enPreprocesoCatNuevo.put(EstadoDevISREnum.PROCEDENTE.getId(), APROBADO);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.AUTORIZADA_POR_PROCESO.getId(), APROBADO);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.AUTORIZADA_POR_AUTORIDAD.getId(), APROBADO);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.AUTORIZADA_POR_USUARIO.getId(), APROBADO);
        
        //Rechazadas por crÃ©dito fiscal
        enPreprocesoCatNuevo.put(EstadoDevISREnum.PREAUTORIZADO.getId(), RECHAZADACREDITOFISCAL);

        //Rechazadas
        enPreprocesoCatNuevo.put(EstadoDevISREnum.RECHAZADO_POR_USUARIO.getId(), RECHAZADAS);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.RECHAZADO_POR_PROCESO.getId(), RECHAZADAS);
        enPreprocesoCatNuevo.put(EstadoDevISREnum.RECHAZADO_POR_CONTROL_SALDO.getId(), RECHAZADAS);

        //En proceso de pago
        enPreprocesoCatNuevo.put(EstadoDevISREnum.PROCESO_PAGO.getId(), PROCESOPAGO);

        //Pagadas
        enPreprocesoCatNuevo.put(EstadoDevISREnum.PAGADO.getId(), PAGADO);

        //Autorizadas con inconsistencia cuenta CLABE
        enPreprocesoCatNuevo.put(EstadoDevISREnum.NO_PAGADO.getId(), AUTORIZADAINCONSCUENTACLABE);

        
        
        if (consultaDetalle != null && consultaDetalle.getTramiteDevolucion() != null) {
            for (mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.TramiteDevolucionDTO objectDataWS : consultaDetalle.getTramiteDevolucion()) {

                tramiteDetalleIsRVO = new DatosTramiteISRDetalleVO();

                tramiteDetalleIsRVO.setFolioDeclaracion(objectDataWS.getFolioDeclaracion());
                tramiteDetalleIsRVO.setEstadoConsulta(objectDataWS.getEstatusConsulta());
                tramiteDetalleIsRVO.setMensajeRespuestaConsulta(objectDataWS.getMensajeRespuestaConsulta());
                tramiteDetalleIsRVO.setFolioMATDYC(objectDataWS.getFolioMatDyC());
                tramiteDetalleIsRVO.setTipoTramite(objectDataWS.getTipoTramite());
                tramiteDetalleIsRVO.setConcepto(objectDataWS.getConcepto());
                tramiteDetalleIsRVO.setImpuesto(objectDataWS.getImpuesto());
                tramiteDetalleIsRVO.setSaldoAPagar((new BigDecimal(objectDataWS.getSaldoPagar() == null ? 0L : objectDataWS.getSaldoPagar())).doubleValue());
                tramiteDetalleIsRVO.setSaldoCalculadoXSistema((new BigDecimal(objectDataWS.getSaldoCalculadoXSistema() == null ? 0L : objectDataWS.getSaldoCalculadoXSistema()).longValue()));
                tramiteDetalleIsRVO.setCuetaClabe(objectDataWS.getCuentaClabe());
                tramiteDetalleIsRVO.setNombreBanco(objectDataWS.getNombreBanco());
                tramiteDetalleIsRVO.setIsrAFavorDelEjercicio(objectDataWS.getIsrAFavorDelEjercicio());
                tramiteDetalleIsRVO.setIsrAcargoDelEjercicio(objectDataWS.getIsrAcargoDelEjercicio());
                tramiteDetalleIsRVO.setIdTipoResolucion(objectDataWS.getIdTipoResolucion());
                tramiteDetalleIsRVO.setIdentificadorResultado(objectDataWS.getIdentificadorResultado());
                tramiteDetalleIsRVO.setDescripcionResultado(objectDataWS.getDescripcionResultado());

                if (lstFolios != null && !lstFolios.isEmpty()) {
                    for (TramiteExisteConsultaVO tramiteExisteConsultaVO : lstFolios) {
                        if (tramiteExisteConsultaVO.getFolioDeclaracion() == tramiteDetalleIsRVO.getFolioDeclaracion()) {
                            tramiteDetalleIsRVO.setIdEstadoProceso(tramiteExisteConsultaVO.getIdEstadoProceso());
                            break;
                        }
                    }
                }

                if (objectDataWS.getContribuyente() != null) {
                    contribuye = objectDataWS.getContribuyente();

                    tramiteDetalleIsRVO.setApellidoPaterno(contribuye.getApellidoPaterno());
                    tramiteDetalleIsRVO.setApellidoMaterno(contribuye.getApellidoMaterno());
                    tramiteDetalleIsRVO.setNombres(contribuye.getNombre());

                }

                if (objectDataWS.getDeclaracion() != null) {

                    declaracion = objectDataWS.getDeclaracion();

                    tramiteDetalleIsRVO.setTipoDeclaracion(declaracion.getTipoDeclaracion());
                    tramiteDetalleIsRVO.setFechaPresentacion(toDate(declaracion.getFechaPresentacion()));
                    tramiteDetalleIsRVO.setEjercicio(declaracion.getEjercicio());
                    tramiteDetalleIsRVO.setIdPeriodo(declaracion.getPeriodo());
                    tramiteDetalleIsRVO.setDescripcionPeriodo(declaracion.getDescripcionPeriodo());
                    tramiteDetalleIsRVO.setIdFirma(declaracion.getIdFirma());
                    tramiteDetalleIsRVO.setIdTipoProceso(declaracion.getIdTipoProceso());
                    tramiteDetalleIsRVO.setAceptoPropuestaRecibida(declaracion.getAceptoPropuestaRecibida());
                    tramiteDetalleIsRVO.setCreditoFiscal(declaracion.getCreditoFiscal());
                }

                EstadoDevISREnum estadoProcesoEnum = EstadoDevISREnum.parse(tramiteDetalleIsRVO.getIdEstadoProceso());
                tramiteDetalleIsRVO.setTipoDeclaracionEnum(tramiteDetalleIsRVO.getTipoDeclaracion() == 1 ? TipoDeclaracionEnum.DECLARACION_NORMAL : tramiteDetalleIsRVO.getTipoDeclaracion() == 2 ? TipoDeclaracionEnum.DECLARACION_COMPLEMENTARIA : TipoDeclaracionEnum.DECLARACION_ERROR);
                tramiteDetalleIsRVO.setTipoFirmaEnum(tramiteDetalleIsRVO.getIdFirma() == 0 ? TipoFirmaEnum.FIRMA_PASSWORD : tramiteDetalleIsRVO.getIdFirma() == 1 ? TipoFirmaEnum.FIRMA_ELECTRONICA : TipoFirmaEnum.FIRMA_ERROR);
                tramiteDetalleIsRVO.setCreditoFiscalEnum(tramiteDetalleIsRVO.getCreditoFiscal() == 0 ? CreditoFiscalEnum.NO : CreditoFiscalEnum.SI);

                
                if(estadoProcesoEnum!=null && enPreprocesoCatNuevo.containsKey(estadoProcesoEnum.getId())){
                    tramiteDetalleIsRVO.setDescripcionResultado(enPreprocesoCatNuevo.get(estadoProcesoEnum.getId()));
                }                

                tramiteDetalleIsRVO.setDeclaracionSAT(determinacionISR(objectDataWS.getDeterminacionIsrPrecarga()));
                tramiteDetalleIsRVO.setDeclaracionContribuyente(determinacionISR(objectDataWS.getDeterminacionIsrConstribuyente()));
                tramiteDetalleIsRVO.setResultadoProceso(determinacionISR(objectDataWS.getDeterminacionIsrResultado()));

                tramiteDetalleIsRVO.setAceptarPropuestaEnum(tramiteDetalleIsRVO.getAceptoPropuestaRecibida() == 1 ? AceptarPropuestaEnum.SI : AceptarPropuestaEnum.NO);

                if (objectDataWS.getDeduccionInconsistencia() != null) {
                    tramiteDetalleIsRVO.setDeduccionInconsistencias(new ArrayList<mx.gob.sat.siat.dyc.consulta.devautisr.vo.DeduccionInconsistenciaVO>());
                    mx.gob.sat.siat.dyc.consulta.devautisr.vo.DeduccionInconsistenciaVO deduccionInconsistenciaVO;

                    for (DedInconsistenciaDTO objectDto : objectDataWS.getDeduccionInconsistencia()) {
                        deduccionInconsistenciaVO = new mx.gob.sat.siat.dyc.consulta.devautisr.vo.DeduccionInconsistenciaVO();

                        deduccionInconsistenciaVO.setTipoDeduccion(objectDto.getTipoDeduccion());
                        deduccionInconsistenciaVO.setRfcEmisorCFDI(objectDto.getRfcEmisorCFDI());
                        deduccionInconsistenciaVO.setNombreEmisorCFDI(objectDto.getNombreEmisorCFDI());
                        deduccionInconsistenciaVO.setImporteDeclaracionSAT(BigDecimal.valueOf(objectDto.getImporteDeclaracionSAT()));
                        deduccionInconsistenciaVO.setImporteDeclaracionContribuyente(BigDecimal.valueOf(objectDto.getImporteDeclaracionContribuyente()));
                        deduccionInconsistenciaVO.setNumeroInconsistencia(objectDto.getNumeroInconsistencia());
                        deduccionInconsistenciaVO.setDescripcion(objectDto.getDescripcion());
                        deduccionInconsistenciaVO.setAccionCorrectiva(objectDto.getAccionCorrectiva());
                        deduccionInconsistenciaVO.setEjercicioAccionCorrectiva(objectDto.getEjercicioAccionCorrectiva());
                        deduccionInconsistenciaVO.setGeneraDevolucionManual(objectDto.getGeneraDevolucionManual());

                        tramiteDetalleIsRVO.getDeduccionInconsistencias().add(deduccionInconsistenciaVO);
                    }
                }

                if (objectDataWS.getRetencionInconsistencia() != null) {
                    tramiteDetalleIsRVO.setRetencionInconsistencias(new ArrayList<mx.gob.sat.siat.dyc.consulta.devautisr.vo.RetencionInconsistenciaVO>());
                    mx.gob.sat.siat.dyc.consulta.devautisr.vo.RetencionInconsistenciaVO retencionInconsistenciaVO;

                    for (RetInconsistenciaDTO objectDto : objectDataWS.getRetencionInconsistencia()) {
                        retencionInconsistenciaVO = new mx.gob.sat.siat.dyc.consulta.devautisr.vo.RetencionInconsistenciaVO();

                        retencionInconsistenciaVO.setTipoIngreso(objectDto.getTipoIngreso());
                        retencionInconsistenciaVO.setRfcRetenedor(objectDto.getRfcRetenedor());
                        retencionInconsistenciaVO.setRazonSocial(objectDto.getRazonSocial());
                        retencionInconsistenciaVO.setTotalIngresoSat(BigDecimal.valueOf(objectDto.getTotalIngresoSat()));
                        retencionInconsistenciaVO.setImptoRetenidoSat(BigDecimal.valueOf(objectDto.getImptoRetenidoSat()));
                        retencionInconsistenciaVO.setTotalIngresoContribuyente(BigDecimal.valueOf(objectDto.getTotalIngresoContribuyente()));
                        retencionInconsistenciaVO.setImptoRetenidoContribuyente(BigDecimal.valueOf(objectDto.getImptoRetenidoContribuyente()));
                        retencionInconsistenciaVO.setNumeroInconsistencia(objectDto.getNumeroInconsistencia());
                        retencionInconsistenciaVO.setDescripcion(objectDto.getDescripcion());
                        retencionInconsistenciaVO.setAccionCorrectiva(objectDto.getAccionCorrectiva());
                        retencionInconsistenciaVO.setEjercicioAccionCorrectiva(objectDto.getEjercicioAccionCorrectiva());
                        retencionInconsistenciaVO.setGeneraDevolucionManual(objectDto.getGeneraDevolucionManual());

                        tramiteDetalleIsRVO.getRetencionInconsistencias().add(retencionInconsistenciaVO);
                    }
                }

                if (objectDataWS.getRetroalimentacionPago() != null) {
                    retro = objectDataWS.getRetroalimentacionPago();
                    tramiteDetalleIsRVO.setFechaPago(toDate(retro.getFechaPago()));
                    tramiteDetalleIsRVO.setMotivoRechazoPago(retro.getMotivoRechazo() == null ? "" : retro.getMotivoRechazo());
                }

                if (objectDataWS.getRechazos() != null) {                    
                    rechazos = new ArrayList<RechazoTramiteVO>();
                    for (mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.RechazoDTO recha : objectDataWS.getRechazos()) {                        
                        rechazo = new RechazoTramiteVO();
                        rechazo.setAccionCorrectiva(recha.getAccionCorrectiva());
                        rechazo.setDescripcion(recha.getDescripcionRechazo());
                        rechazo.setEjercicio(recha.getEjercicioAccionCorrectiva());
                        rechazo.setGenerarDevolucionManual(recha.getGeneraDevolucionManual());
                        rechazo.setNumeroRechazo(recha.getNumeroRechazo());

                        rechazos.add(rechazo);
                    }

                    tramiteDetalleIsRVO.setRechazosTramite(rechazos);
                }

                tramiteDetalleIsRVO.setFechaEstado(toDate(objectDataWS.getFechaEstado()));
                inconsistencias= new ArrayList<InconsistenciaTramiteVO>();
                for (mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.InconsistenciaDTO inconsis : objectDataWS.getInconsistencias()) {
                    inconsistencia = new InconsistenciaTramiteVO();
                    inconsistencia.setAccionCorrectiva(inconsis.getAccionCorrectiva());
                    inconsistencia.setDescripcion(inconsis.getDescripcion());
                    inconsistencia.setEjercicio(inconsis.getEjercicioAccionCorrectiva());
                    inconsistencia.setGenerarDevolucionManual(inconsis.getGeneraDevolucionManual());
                    inconsistencia.setNumeroInconsistencia(inconsis.getNumeroInconsistencia());
                    inconsistencia.setIdDeduccion(inconsis.getIdDeduccion());
                    inconsistencia.setIdIngreso(inconsis.getIdIngreso());

                    inconsistencias.add(inconsistencia);
                }

                tramiteDetalleIsRVO.setInconsistenciasTramite(inconsistencias);

                lstDetalles.add(tramiteDetalleIsRVO);

            }
        }

        return lstDetalles;
    }

    public static mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO determinacionISR(mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.DeterminacionISRDTO determinacionISRDTO) {
        mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO determinacionISRDTO1 = new mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO();

        if (determinacionISRDTO != null) {
            determinacionISRDTO1.setIngresosAcumulables(BigDecimal.valueOf(determinacionISRDTO.getIngresosAcumulables()));
            determinacionISRDTO1.setPerdidas(BigDecimal.valueOf(determinacionISRDTO.getPerdida()));
            determinacionISRDTO1.setTotalIngresosAcumulables(BigDecimal.valueOf(determinacionISRDTO.getTotalIngresosAcumulables()));
            determinacionISRDTO1.setDeduccionesPersonales(BigDecimal.valueOf(determinacionISRDTO.getDeduccionesPersonales()));
            determinacionISRDTO1.setBaseGravable(BigDecimal.valueOf(determinacionISRDTO.getBaseGravable()));
            determinacionISRDTO1.setIsrConformeTarifaAnual(BigDecimal.valueOf(determinacionISRDTO.getIsrConformeTarifaAnual()));
            determinacionISRDTO1.setImptoSobreIngresosAcumulables(BigDecimal.valueOf(determinacionISRDTO.getImptoSobreIngresosAcumulables()));
            determinacionISRDTO1.setImptoSobreIngresosNoAcumulables(BigDecimal.valueOf(determinacionISRDTO.getImptoSobreIngresosNoAcumulables()));
            determinacionISRDTO1.setReduccionesDeISR(BigDecimal.valueOf(determinacionISRDTO.getReduccionesDeISR()));
            determinacionISRDTO1.setImptoSobreLaRentaCausado(BigDecimal.valueOf(determinacionISRDTO.getImptoSobreLaRentaCausado()));
            determinacionISRDTO1.setPagosProvisionales(BigDecimal.valueOf(determinacionISRDTO.getPagosProvisionales()));
            determinacionISRDTO1.setImptoRetenidoAlcontribuyente(BigDecimal.valueOf(determinacionISRDTO.getImptoRetenidoAlContribuyente()));
            determinacionISRDTO1.setImptoAcreditablePagadoEnExtranjero(BigDecimal.valueOf(determinacionISRDTO.getImptoAcreditablePagadoEnExtranjero()));
            determinacionISRDTO1.setImptoSobreInteresRealPorRetirosParciales(BigDecimal.valueOf(determinacionISRDTO.getImptoSobreInteresRealXRetirosParciales()));
            determinacionISRDTO1.setIsrAFavorDelEjercicio(BigDecimal.valueOf(determinacionISRDTO.getIsrAFavorDelEjercicio()));
            determinacionISRDTO1.setSectorPrimario(BigDecimal.valueOf(determinacionISRDTO.getSectorPrimario()));
            determinacionISRDTO1.setSubsidioEmpleo(BigDecimal.valueOf(determinacionISRDTO.getSubsidioEmpleo()));

        } else {
            return null;
        }

        return determinacionISRDTO1;
    }

    private static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    private static String toDateCadena(Date fecha) {
        if (fecha == null) {
            return "";
        }

        DateFormat fechaHoraF = new SimpleDateFormat("dd/MM/yyyy");
        return fechaHoraF.format(fecha);
    }

    public static mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO clonarDeterminacion(mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO determinacionISRDTO) {

        if (determinacionISRDTO != null) {

            mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO determinacionISRDTO1 = new mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO();
            determinacionISRDTO1.setTipoDeclaracion(determinacionISRDTO.getTipoDeclaracion());
            determinacionISRDTO1.setFechaPresentacion(determinacionISRDTO.getFechaPresentacion());
            determinacionISRDTO1.setEjercicio(determinacionISRDTO.getEjercicio());
            determinacionISRDTO1.setPeriodo(determinacionISRDTO.getPeriodo());
            determinacionISRDTO1.setDescripcionPeriodo(determinacionISRDTO.getDescripcionPeriodo());
            determinacionISRDTO1.setCuentaCLABE(determinacionISRDTO.getCuentaCLABE());
            determinacionISRDTO1.setMontoIsrAFavor(determinacionISRDTO.getMontoIsrAFavor());
            determinacionISRDTO1.setNombreInstitucionBancaria(determinacionISRDTO.getNombreInstitucionBancaria());
            determinacionISRDTO1.setContribuyente(determinacionISRDTO.getContribuyente());
            determinacionISRDTO1.setFirmaDeclaracion(determinacionISRDTO.getFirmaDeclaracion());
            determinacionISRDTO1.setAceptoPropuestaRecibida(determinacionISRDTO.getAceptoPropuestaRecibida());
            determinacionISRDTO1.setIngresosAcumulables(determinacionISRDTO.getIngresosAcumulables());
            determinacionISRDTO1.setPerdidas(determinacionISRDTO.getPerdidas());
            determinacionISRDTO1.setTotalIngresosAcumulables(determinacionISRDTO.getTotalIngresosAcumulables());
            determinacionISRDTO1.setDeduccionesPersonales(determinacionISRDTO.getDeduccionesPersonales());
            determinacionISRDTO1.setBaseGravable(determinacionISRDTO.getBaseGravable());
            determinacionISRDTO1.setIsrConformeTarifaAnual(determinacionISRDTO.getIsrConformeTarifaAnual());
            determinacionISRDTO1.setSubsidioEmpleo(determinacionISRDTO.getSubsidioEmpleo());
            determinacionISRDTO1.setImptoSobreIngresosAcumulables(determinacionISRDTO.getImptoSobreIngresosAcumulables());
            determinacionISRDTO1.setImptoSobreIngresosNoAcumulables(determinacionISRDTO.getImptoSobreIngresosNoAcumulables());
            determinacionISRDTO1.setReduccionesDeISR(determinacionISRDTO.getReduccionesDeISR());
            determinacionISRDTO1.setImptoSobreLaRentaCausado(determinacionISRDTO.getImptoSobreLaRentaCausado());
            determinacionISRDTO1.setPagosProvisionales(determinacionISRDTO.getPagosProvisionales());
            determinacionISRDTO1.setImptoRetenidoAlcontribuyente(determinacionISRDTO.getImptoRetenidoAlcontribuyente());
            determinacionISRDTO1.setImptoAcreditablePagadoEnExtranjero(determinacionISRDTO.getImptoAcreditablePagadoEnExtranjero());
            determinacionISRDTO1.setSectorPrimario(determinacionISRDTO.getSectorPrimario());
            determinacionISRDTO1.setImptoSobreInteresRealPorRetirosParciales(determinacionISRDTO.getImptoSobreInteresRealPorRetirosParciales());
            determinacionISRDTO1.setIsrAFavorDelEjercicio(determinacionISRDTO.getIsrAFavorDelEjercicio());
            determinacionISRDTO1.setIsrACargoDelEjercicio(determinacionISRDTO.getIsrACargoDelEjercicio());

            return determinacionISRDTO1;
        }
        return null;
    }

}
