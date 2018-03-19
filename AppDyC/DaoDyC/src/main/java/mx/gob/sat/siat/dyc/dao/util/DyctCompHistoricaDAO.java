package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctCompHistoricaDAO
{
    void actualizarMovCompensacion(DyctCompHistoricaDTO movCompensacion) throws SIATException;
    
    List<DyctCompHistoricaDTO> obtenerXidSaldoIcep(int idSaldoIcep);

    void insertar(DyctCompHistoricaDTO movCompensacionDTO) throws SIATException ;
    
    DyctCompHistoricaDTO encontrarXNumcontrol(String numControl) throws SIATException;
    
    void borrar(Integer idMovCompensacion) throws SIATException;
    
    DyctCompHistoricaDTO encontrar(Integer idMovCompensacion);

}
