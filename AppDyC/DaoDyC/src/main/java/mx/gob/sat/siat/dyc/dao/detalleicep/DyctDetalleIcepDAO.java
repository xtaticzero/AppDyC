package mx.gob.sat.siat.dyc.dao.detalleicep;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDetalleIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctDetalleIcepDAO {

    List<DyctDetalleIcepDTO> getMovimientoDeResolucionxIdSaldoIcep(Integer idSaldoICep) throws SIATException;

    void insertar(DyctDetalleIcepDTO dyctDetalleIcepDTO) throws SIATException;

    List<DyctDetalleIcepDTO> obtenerPorIdICepNumControl(Integer idSaldoICep, String numControl ) throws SIATException;
    
    void actualizarNumControlDetall(String numcontrol, Integer idSaldoIcep, Integer idDetalleIcep) throws SIATException;
    
    List<DyctDetalleIcepDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep);
}
