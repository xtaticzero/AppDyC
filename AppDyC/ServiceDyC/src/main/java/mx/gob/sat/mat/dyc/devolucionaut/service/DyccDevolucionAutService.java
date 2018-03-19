package mx.gob.sat.mat.dyc.devolucionaut.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;

public interface DyccDevolucionAutService {

    String getNumeroConsecutivoDevolucionAutomatica(String claveSir);

    String obtenerRfcAmpliadoClaveSir(IdCInterno rfcAmpliado);

    void guardarRegistroSaldoIcep(DyctSaldoIcepDTO registroSaldoIcep) throws SIATException;

    int actualizaEstatus(Integer estatus, String folioMATDyC) throws SIATException;

    void guardarDeclaracion(DyctDeclaracionDTO declaracion) throws DycDaoExcepcion;

    void guardarMovientoSaldoIcep(DyctMovSaldoDTO movimientoSaldoIcep) throws SIATException;

    boolean bloqueaRegistroSaldoIcep(DyctSaldoIcepDTO saldoIcep);

    boolean insertarInconsistencia(DycaSolInconsistDTO inconsistencia) throws SIATException;

    Integer insertarCuentaBancoArchivoDevAut(String numeroControl, Date fechaPresentacion);

    int asignarDictaminador(DycpSolicitudDTO tramiteDTO) throws SQLException;

    void generarServicioSolicitud(DycpSolicitudDTO solicitudDev, Integer numEmpleado) throws SIATException;

    DycpSolicitudDTO existeRegistroSolicitud(String numeroControl, String rfc, Integer icep) throws SIATException;

    DyctSaldoIcepDTO encontrarRegistroIcepContribuyente(String rfc, DyccConceptoDTO concepto,
            DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo, Integer origenSaldo) throws DycDaoExcepcion;

    List<DycpDatosSolicitudDTO> obtenerDatosSolicitud(Integer idSaldoIcep) throws DycDaoExcepcion;

    void insertaServicioSol(DycpSolicitudDTO input, Integer numEmpleado) throws SIATException;

    void guardarDeclaracionIcep(DyctDeclaraIcepDTO declaracion) throws SIATException;

    String getCompensacionPorIcep(Integer idSaldoIcep);

    BigDecimal obtenerRemante(Integer idSaldoIcep) throws SIATException;

    ControlSaldoImportesDTO obtenerSaldoImportes(Integer idSaldoIcep) throws SIATException;

    List<DyctDeclaracionDTO> obtenerDeclaracion(String numeroOperacion, String rfc);

    DycpServicioDTO consultarTramiteSinDictaminador(String numeroControl) throws SIATException;

    DycpSolicitudDTO consultarSolicitud(String numeroControl) throws SIATException;

    BigDecimal obtenerImporteResuelto(Integer idSaldoIcep) throws SIATException;
}
