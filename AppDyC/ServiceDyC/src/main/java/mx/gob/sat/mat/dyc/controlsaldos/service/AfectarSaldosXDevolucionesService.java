package mx.gob.sat.mat.dyc.controlsaldos.service;

import java.util.Map;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface AfectarSaldosXDevolucionesService {

    String SALDO_ICEP = "idSaldoIcep";
    String SE_AFECTO_SALDO = "seAfectoSaldos";

    Map<String, Object> afectarXRegistro(DycpSolicitudDTO solicitud) throws SIATException;

    Map<String, Object> afectarXRegistroISR(DycpSolicitudDTO solicitud) throws SIATException;

    Map<String, Object> afectarXRegistroDevAutomaticasIva(DycpSolicitudDTO solicitud) throws SIATException;

    Map<String, Object> afectarXResolucion(String numControlDoc) throws SIATException;

    Map<String, Object> afectarXResolucionDevAutomaticasIva(String numControl) throws SIATException;

    Map<String, Object> afectarXResolucionDevAutomaticasIva(DycpSolicitudDTO solicitud, DyctResolucionDTO resolucion) throws SIATException;

    Map<String, Object> afectarXResolucionDevAutomaticasWS(String numControl) throws SIATException;

    void afectarXDesistimiento(String numControlDoc) throws SIATException;

    boolean implicaDeclaracion(DycpSolicitudDTO solicitud) throws SIATException;

    DycpSolicitudDTO getSolicitud(final String numeroControl);

    DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo);

    DyctSaldoIcepDTO encontrarISR(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo, DyccOrigenSaldoDTO saldo);

}
