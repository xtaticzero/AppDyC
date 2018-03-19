package mx.gob.sat.siat.dyc.dao.periodo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;


public interface DyccPeriodoDAO {
    DyccPeriodoDTO encontrar(Integer id);

    List<DyccPeriodoDTO> selecXTipoPeriodo(DyccTipoPeriodoDTO tipoPeriodo);

    /**
     * @param idTipoPeriodo
     * @return
     */
    List<DyccPeriodoDTO> obtienePeriodoPorTipoPeriodo(String idTipoPeriodo);

    /**
     * @param dyccPeriodo
     * @return
     */
    DyccPeriodoDTO obtienePeriodoPorId(DyccPeriodoDTO dyccPeriodo);

    DyccPeriodoDTO getFIniFFinPeriodo(int idPeriodo, int ejercicio);

    List<DyccPeriodoDTO> obtenerPeriodos();

    List<DyccPeriodoDTO> obtenerPeriodosPorTipoDePeriodo(String tipoPeriodo);

    String obtenerPeriodoDiot(int idPeriodo);
    
    String obtenerFinPeriodo(int idPeriodo);
}
