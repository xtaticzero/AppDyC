package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.mat.dyc.controlsaldos.service.RegistrarDeclaracionesService;
import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("serviceAfectarSaldosXDevoluciones")
public class AfectarSaldosXDevolucionesServiceImpl implements AfectarSaldosXDevolucionesService {

    private static final Logger LOG = Logger.getLogger(AfectarSaldosXDevolucionesServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private RegistrarDeclaracionesService serviceRegistrarDecls;

    @Autowired
    private ValidaTramitesService serviceValidaTramite;

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private DyctResolucionDAO daoResolucion;

    @Autowired
    private DycpSolicitudDAO daoSolicitud;

    @Autowired
    private ObtenerFechaHistoricaServiceImpl serviceObtenerFechaHistorica;

    private static final Integer SINPERIODO = 99;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Map<String, Object> afectarXRegistro(DycpSolicitudDTO solicitud) throws SIATException {
        return afectarXRegistro(solicitud, false);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Map<String, Object> afectarXRegistroISR(DycpSolicitudDTO solicitud) throws SIATException {
        return afectarXRegistroISR(solicitud, false);
    }

    public DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo) {
        return daoSaldoIcep.encontrar(rfc, concepto, ejercicio, periodo);
    }

    public DyctSaldoIcepDTO encontrarISR(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo, DyccOrigenSaldoDTO saldo) {

        return daoSaldoIcep.encontrar(rfc, concepto, ejercicio, periodo, saldo);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Map<String, Object> afectarXRegistroDevAutomaticasIva(DycpSolicitudDTO solicitud) throws SIATException {
        return afectarXRegistro(solicitud, Boolean.TRUE);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public Map<String, Object> afectarXResolucionDevAutomaticasWS(String numControl) throws SIATException {
        return afectarXResolucionAut(numControl);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Map<String, Object> afectarXRegistro(DycpSolicitudDTO solicitud, boolean esDevolucionAutomatica) throws SIATException {
        LOG.debug("INICIO afectarXRegistro");
        try {
            DyctSaldoIcepDTO saldoIcep = solicitud.getDyctSaldoIcepDTO();
            saldoIcep.setDyccConceptoDTO(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccConceptoDTO());
            saldoIcep.setRfc(solicitud.getDycpServicioDTO().getRfcContribuyente());

            daoSaldoIcep.insert(saldoIcep);

            Date fechaHistorica = obtenerFechaHistSinPersistencia(solicitud);
            LOG.debug("idSaldoIcep ->" + saldoIcep.getIdSaldoIcep() + "<-; fecha Historica ->" + fechaHistorica + "<-");

            List<DyctDeclaracionDTO> infoSaldoAFavorList = solicitud.getDycpServicioDTO().getDyctDeclaracionList();

            generarMovimientosExternos(infoSaldoAFavorList, saldoIcep, fechaHistorica);

            Boolean encontroDeclsDWH = Boolean.FALSE;

            if (implicaDeclaracion(solicitud)) {
                if (esDevolucionAutomatica) {
                    encontroDeclsDWH = serviceRegistrarDecls.executeDevAutomaticasIva(solicitud);
                } else {
                    encontroDeclsDWH = serviceRegistrarDecls.execute(solicitud.getDycpServicioDTO());
                }
            } else {
                DyctDeclaracionDTO infoSaldoAFavor = infoSaldoAFavorList.get(0);
                DyctMovSaldoDTO abonoPagoIndebido = new DyctMovSaldoDTO();
                abonoPagoIndebido.setDyctSaldoIcepDTO(saldoIcep);
                BigDecimal importeAbono = determinarImporteAbono(infoSaldoAFavor);
                importeAbono = validarImporteAbono(importeAbono, solicitud.getImporteSolicitado());

                abonoPagoIndebido.setImporte(importeAbono);
                abonoPagoIndebido.setFechaRegistro(new Date());
                abonoPagoIndebido.setFechaOrigen(infoSaldoAFavor.getFechaCausacion());
                abonoPagoIndebido.setDyccMovIcepDTO(Constantes.MovsIcep.ABONO_PAGOINDEBIDO);
                String origen = infoSaldoAFavor.getNumDocumento() != null ? infoSaldoAFavor.getNumDocumento() : null;
                if (origen == null) {
                    origen = infoSaldoAFavor.getNumOperacion() != null ? infoSaldoAFavor.getNumOperacion() : null;
                }
                abonoPagoIndebido.setIdOrigen(origen);
                daoMovSaldo.insertar(abonoPagoIndebido);
            }

            LOG.info("Se registro el ICEP con id ->" + saldoIcep.getIdSaldoIcep()
                    + "<- con el metodo saveIcepSinDeclaracion");

            Map<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("idSaldoIcep", saldoIcep.getIdSaldoIcep());
            respuesta.put("encontroDeclaDWH", encontroDeclsDWH);
            //TODO: validar si esta bandera (arriba) es necesaria
            return respuesta;
        } catch (SIATException siatE) {
            LOG.error(siatE.getMessage());
            throw new SIATException(siatE);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Map<String, Object> afectarXRegistroISR(DycpSolicitudDTO solicitud, boolean esDevolucionAutomatica) throws SIATException {
        LOG.debug("INICIO afectarXRegistro");
        try {
            DyctSaldoIcepDTO saldoIcep = solicitud.getDyctSaldoIcepDTO();
            saldoIcep.setDyccConceptoDTO(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccConceptoDTO());
            saldoIcep.setRfc(solicitud.getDycpServicioDTO().getRfcContribuyente());

            Date fechaHistorica = obtenerFechaHistSinPersistencia(solicitud);
            LOG.debug("idSaldoIcep ->" + saldoIcep.getIdSaldoIcep() + "<-; fecha Historica ->" + fechaHistorica + "<-");

            List<DyctDeclaracionDTO> infoSaldoAFavorList = solicitud.getDycpServicioDTO().getDyctDeclaracionList();

            generarMovimientosExternos(infoSaldoAFavorList, saldoIcep, fechaHistorica);

            Boolean encontroDeclsDWH = Boolean.FALSE;

            if (implicaDeclaracion(solicitud)) {
                if (esDevolucionAutomatica) {
                    encontroDeclsDWH = serviceRegistrarDecls.executeDevAutomaticasIva(solicitud);
                } else {
                    encontroDeclsDWH = serviceRegistrarDecls.execute(solicitud.getDycpServicioDTO());
                }
            } else {
                DyctDeclaracionDTO infoSaldoAFavor = infoSaldoAFavorList.get(0);
                DyctMovSaldoDTO abonoPagoIndebido = new DyctMovSaldoDTO();
                abonoPagoIndebido.setDyctSaldoIcepDTO(saldoIcep);
                BigDecimal importeAbono = determinarImporteAbono(infoSaldoAFavor);
                importeAbono = validarImporteAbono(importeAbono, solicitud.getImporteSolicitado());

                abonoPagoIndebido.setImporte(importeAbono);
                abonoPagoIndebido.setFechaRegistro(new Date());
                abonoPagoIndebido.setFechaOrigen(infoSaldoAFavor.getFechaCausacion());
                abonoPagoIndebido.setDyccMovIcepDTO(Constantes.MovsIcep.ABONO_PAGOINDEBIDO);
                String origen = infoSaldoAFavor.getNumDocumento() != null ? infoSaldoAFavor.getNumDocumento() : null;
                if (origen == null) {
                    origen = infoSaldoAFavor.getNumOperacion() != null ? infoSaldoAFavor.getNumOperacion() : null;
                }
                abonoPagoIndebido.setIdOrigen(origen);
                daoMovSaldo.insertar(abonoPagoIndebido);
            }

            LOG.info("Se registro el ICEP con id ->" + saldoIcep.getIdSaldoIcep()
                    + "<- con el metodo saveIcepSinDeclaracion");

            Map<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("idSaldoIcep", saldoIcep.getIdSaldoIcep());
            respuesta.put("encontroDeclaDWH", encontroDeclsDWH);
            //TODO: validar si esta bandera (arriba) es necesaria
            return respuesta;
        } catch (SIATException siatE) {
            LOG.error(siatE.getMessage());
            throw new SIATException(siatE);
        }
    }

    private BigDecimal determinarImporteAbono(DyctDeclaracionDTO infoSaldoAFavor) {
        return infoSaldoAFavor.getSaldoAfavor() != null ? infoSaldoAFavor.getSaldoAfavor() : (infoSaldoAFavor.getImporte()
                != null)
                        ? infoSaldoAFavor.getImporte()
                        : BigDecimal.ZERO;
    }

    private BigDecimal validarImporteAbono(BigDecimal importeAbono, BigDecimal importeSolicitado) {

        return importeAbono.equals(BigDecimal.ZERO) ? (importeSolicitado != null ? importeSolicitado : BigDecimal.ZERO) : importeAbono;
    }

    private void generarMovimientosExternos(List<DyctDeclaracionDTO> infoSaldoAFavorList, DyctSaldoIcepDTO saldoIcep, Date fechaHistorica) throws SIATException {
        for (DyctDeclaracionDTO infoSaldoAFavor : infoSaldoAFavorList) {
            String origen = infoSaldoAFavor.getNumDocumento() != null ? infoSaldoAFavor.getNumDocumento() : null;
            if (origen == null) {
                origen = infoSaldoAFavor.getNumOperacion() != null ? infoSaldoAFavor.getNumOperacion() : null;
            }

            // Importe del Acreditamiento  efectuado
            if (infoSaldoAFavor.getAcreditamiento() != null
                    && infoSaldoAFavor.getAcreditamiento().doubleValue() > 0) {
                DyctMovSaldoDTO cargoAcreditamiento = new DyctMovSaldoDTO();
                cargoAcreditamiento.setDyctSaldoIcepDTO(saldoIcep);
                cargoAcreditamiento.setImporte(infoSaldoAFavor.getAcreditamiento());
                cargoAcreditamiento.setFechaRegistro(new Date());
                cargoAcreditamiento.setFechaOrigen(fechaHistorica);
                cargoAcreditamiento.setDyccMovIcepDTO(Constantes.MovsIcep.CARGO_ACREDITAMIENTO);
                cargoAcreditamiento.setIdOrigen(origen);
                daoMovSaldo.insertar(cargoAcreditamiento);
            }
        }
    }

    private Date obtenerFechaHistSinPersistencia(DycpSolicitudDTO solicitud) {
        Date fechaHistorica = null;

        for (DyctDeclaracionDTO infoSaldoAFavor : solicitud.getDycpServicioDTO().getDyctDeclaracionList()) {
            if ((infoSaldoAFavor.getFechaPresentacion() != null)
                    && (fechaHistorica == null || infoSaldoAFavor.getFechaPresentacion().after(fechaHistorica))) {
                fechaHistorica = infoSaldoAFavor.getFechaPresentacion();
            }
            if ((infoSaldoAFavor.getFechaCausacion() != null)
                    && (fechaHistorica == null || infoSaldoAFavor.getFechaCausacion().after(fechaHistorica))) {
                fechaHistorica = infoSaldoAFavor.getFechaCausacion();
            }
        }
        return fechaHistorica != null ? fechaHistorica : new Date();
    }

    @Override
    public boolean implicaDeclaracion(DycpSolicitudDTO solicitud) throws SIATException {
        LOG.info("INICIO implicaDeclaracion");
        Integer idTipoTramite
                = solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite();
        // sin periodo no aplica declaracion
        if ((solicitud.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo().compareTo(SINPERIODO)) == ConstantesDyCNumerico.VALOR_0) {
            return false;
        }

        boolean impDecl = serviceValidaTramite.validaTramite(ConstantesDyCNumerico.VALOR_4, idTipoTramite);
        LOG.info("El tramite " + idTipoTramite + " implica declaracion ->" + impDecl);
        return impDecl;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public Map<String, Object> afectarXResolucion(String numControlDoc) throws SIATException {
        return afectarXResolucion(numControlDoc, Boolean.TRUE);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public Map<String, Object> afectarXResolucionDevAutomaticasIva(String numControl) throws SIATException {
        return afectarXResolucion(numControl, false);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public Map<String, Object> afectarXResolucionDevAutomaticasIva(DycpSolicitudDTO solicitud, DyctResolucionDTO resolucion) throws SIATException {
        return afectarXResolucion(solicitud, resolucion);
    }

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    private Map<String, Object> afectarXResolucion(String numControlDoc, boolean tieneDocumento) throws SIATException {
        DyctDocumentoDTO documento;
        DycpSolicitudDTO solicitud;
        if (tieneDocumento) {
            documento = daoDocumento.encontrar(numControlDoc);
            solicitud = daoSolicitud.encontrar(documento.getDycpServicioDTO().getNumControl());
        } else {
            // En el caso de DevAutomaticasIva no existe documento 
            // (numeroControlDoc representa el numeroControl para el caso de devolucion automatica).
            solicitud = daoSolicitud.encontrar(numControlDoc);
        }
        DyctResolucionDTO resolucion = daoResolucion.encontrar(solicitud);

        return afectarXResolucion(solicitud, resolucion);
    }

    private Map<String, Object> afectarXResolucionAut(String numControlDoc) throws SIATException {

        DycpSolicitudDTO solicitud;

        // En el caso de DevAutomaticasIva no existe documento 
        // (numeroControlDoc representa el numeroControl para el caso de devolucion automatica).
        solicitud = daoSolicitud.encontrar(numControlDoc);

        DyctResolucionDTO resolucion = daoResolucion.encontrar(solicitud);
        DyctSaldoIcepDTO saldoIcep = solicitud.getDyctSaldoIcepDTO();

        Date ahora = new Date();
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(AfectarSaldosXDevolucionesService.SALDO_ICEP, saldoIcep.getIdSaldoIcep());
        respuesta.put(AfectarSaldosXDevolucionesService.SE_AFECTO_SALDO, Boolean.TRUE);
        LOG.debug("idTipoResolucion ->" + resolucion.getDyccTipoResolDTO().getIdTipoResol());

        Date fechaHistorica = serviceObtenerFechaHistorica.execute(saldoIcep);
        LOG.debug("fechaHistorica ->" + fechaHistorica + "<-");

        if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.AUTORIZADA_TOTAL) {
            DyctMovSaldoDTO cargoDevolTotal = new DyctMovSaldoDTO();
            cargoDevolTotal.setDyctSaldoIcepDTO(saldoIcep);
            cargoDevolTotal.setImporte(resolucion.getImporteNeto());

            cargoDevolTotal.setFechaRegistro(ahora);
            cargoDevolTotal.setFechaOrigen(fechaHistorica);
            cargoDevolTotal.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_AUTORIZADO);
            cargoDevolTotal.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoDevolTotal);
        } else if ((resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.AUTORIZADA_PARCIAL)) {
            DyctMovSaldoDTO cargoDevolAutParcial = new DyctMovSaldoDTO();
            cargoDevolAutParcial.setDyctSaldoIcepDTO(saldoIcep);
            //No es lo mismo porque en el importe neto se resta la compensaciÃ³n de oficio (Creditos fiscales) y esto afecta el 
            //calculo de remanente.
            cargoDevolAutParcial.setImporte(resolucion.getImporteNeto());
            cargoDevolAutParcial.setFechaOrigen(fechaHistorica);

            cargoDevolAutParcial.setFechaRegistro(ahora);
            cargoDevolAutParcial.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_AUTORIZADO);
            cargoDevolAutParcial.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoDevolAutParcial);
        } else {
            LOG.error("El tipo de la resolucion NO es vÃ¡lido, verificar; numControlDoc ->" + numControlDoc + " idTipoResol ->" + resolucion.getDyccTipoResolDTO());
            respuesta.put(AfectarSaldosXDevolucionesService.SE_AFECTO_SALDO, Boolean.FALSE);
        }

        return respuesta;
    }

    private Map<String, Object> afectarXResolucion(DycpSolicitudDTO solicitud, DyctResolucionDTO resolucion) throws SIATException {

        DyctSaldoIcepDTO saldoIcep = solicitud.getDyctSaldoIcepDTO();
        Date ahora = new Date();
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("idSaldoIcep", saldoIcep.getIdSaldoIcep());
        respuesta.put("seAfectoSaldos", Boolean.TRUE);
        LOG.debug("idTipoResolucion ->" + resolucion.getDyccTipoResolDTO().getIdTipoResol());

        Date fechaHistorica = serviceObtenerFechaHistorica.execute(saldoIcep);
        LOG.debug("fechaHistorica ->" + fechaHistorica + "<-");

        if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.AUTORIZADA_TOTAL) {
            DyctMovSaldoDTO cargoDevolTotal = new DyctMovSaldoDTO();
            cargoDevolTotal.setDyctSaldoIcepDTO(saldoIcep);
            cargoDevolTotal.setImporte(resolucion.getImpAutorizado());

            cargoDevolTotal.setFechaRegistro(ahora);
            cargoDevolTotal.setFechaOrigen(fechaHistorica);
            cargoDevolTotal.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_AUTORIZADO);
            cargoDevolTotal.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoDevolTotal);
        } else if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.AUTORIZADA_TOTAL_DEV_AUTO) {
            DyctMovSaldoDTO cargoDevolTotal = new DyctMovSaldoDTO();
            cargoDevolTotal.setDyctSaldoIcepDTO(saldoIcep);
            cargoDevolTotal.setImporte(resolucion.getImpAutorizado());

            cargoDevolTotal.setFechaRegistro(ahora);
            cargoDevolTotal.setFechaOrigen(fechaHistorica);
            cargoDevolTotal.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_AUTORIZADO);
            cargoDevolTotal.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoDevolTotal);
        } else if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_NEGADO) {
            DyctMovSaldoDTO cargoDevolAutParcial = new DyctMovSaldoDTO();
            cargoDevolAutParcial.setDyctSaldoIcepDTO(saldoIcep);
            cargoDevolAutParcial.setImporte(resolucion.getImpAutorizado());
            cargoDevolAutParcial.setFechaRegistro(ahora);
            cargoDevolAutParcial.setFechaOrigen(fechaHistorica);
            cargoDevolAutParcial.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_AUTORIZADO);
            cargoDevolAutParcial.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoDevolAutParcial);

            DyctMovSaldoDTO cargoNegado = new DyctMovSaldoDTO();
            cargoNegado.setDyctSaldoIcepDTO(saldoIcep);
            cargoNegado.setImporte(resolucion.getImpNegado());
            cargoNegado.setFechaRegistro(ahora);
            cargoNegado.setFechaOrigen(fechaHistorica);
            cargoNegado.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_NEGADO);
            cargoNegado.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoNegado);
        } else if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_DISP) {
            DyctMovSaldoDTO cargoDevolAutParcial = new DyctMovSaldoDTO();
            cargoDevolAutParcial.setDyctSaldoIcepDTO(saldoIcep);
            //No es lo mismo porque en el importe neto se resta la compensación de oficio (Creditos fiscales) y esto afecta el 
            //calculo de remanente.
            cargoDevolAutParcial.setImporte(resolucion.getImpAutorizado());
            cargoDevolAutParcial.setFechaOrigen(fechaHistorica);

            cargoDevolAutParcial.setFechaRegistro(ahora);
            cargoDevolAutParcial.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_AUTORIZADO);
            cargoDevolAutParcial.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoDevolAutParcial);
        } else if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.NEGADA) {
            DyctMovSaldoDTO cargoNegado = new DyctMovSaldoDTO();
            cargoNegado.setDyctSaldoIcepDTO(saldoIcep);
            LOG.debug("importeNegado ->" + resolucion.getImpNegado());
            cargoNegado.setImporte(resolucion.getImpNegado());
            cargoNegado.setFechaRegistro(ahora);
            cargoNegado.setFechaOrigen(fechaHistorica);
            cargoNegado.setDyccMovIcepDTO(Constantes.MovsIcep.IMPORTE_NEGADO);
            cargoNegado.setIdOrigen(resolucion.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl());
            daoMovSaldo.insertar(cargoNegado);
        } else if (resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.DESISTIDA) {
            LOG.info("La resolucion de tipo Desistida para la devolucion " + solicitud.getDycpServicioDTO().getNumControl() + " NO implica afectar Saldos, NO se realizará ninguna accion");
            respuesta.put("seAfectoSaldos", Boolean.FALSE);
        } else {
            respuesta.put("seAfectoSaldos", Boolean.FALSE);
        }

        return respuesta;
    }

    /*
     * agregará los abonos para equilibrar el saldo (en caso de existir) los cargos que se generarón al registrar una solicitud de devolución
     * -  Importe de las devoluciones y/o compensaciones anteriores sin incluir actualización
     * -  Importe del Acreditamiento  efectuado
     * */
    @Override
    public void afectarXDesistimiento(String numControlDoc) throws SIATException {
        DyctDocumentoDTO documento = daoDocumento.encontrar(numControlDoc);
        DycpSolicitudDTO solicitud = daoSolicitud.encontrar(documento.getDycpServicioDTO().getNumControl());
        DyctSaldoIcepDTO saldoIcep = solicitud.getDyctSaldoIcepDTO();

        List<DyctMovSaldoDTO> movsADesistir = daoMovSaldo.selecOrderXSaldoicepMovsicep(saldoIcep, Constantes.MovsIcep.CARGO_DEVCOMPS_ANTERIORES, "");
        movsADesistir.addAll(daoMovSaldo.selecOrderXSaldoicepMovsicep(saldoIcep, Constantes.MovsIcep.CARGO_ACREDITAMIENTO, ""));

        for (DyctMovSaldoDTO movDesistir : movsADesistir) {
            DyctMovSaldoDTO abonoDesistir = new DyctMovSaldoDTO();
            abonoDesistir.setDyctSaldoIcepDTO(saldoIcep);
            abonoDesistir.setImporte(movDesistir.getImporte());
            abonoDesistir.setFechaRegistro(new Date());
            abonoDesistir.setFechaOrigen(movDesistir.getFechaOrigen());
            abonoDesistir.setDyccMovIcepDTO(Constantes.MovsIcep.ABONO_DESISTIMIENTO);
            abonoDesistir.setIdOrigen(numControlDoc);
            daoMovSaldo.insertar(abonoDesistir);
        }

    }

    /**
     * Recupera la solicitud segun el numero de control
     *
     * @param numeroControl numero de control del documento
     * @return regresa un objeto de tipo DycpSolicitudDTO
     */
    @Override
    public DycpSolicitudDTO getSolicitud(final String numeroControl) {
        return daoSolicitud.encontrar(numeroControl);
    }
}
