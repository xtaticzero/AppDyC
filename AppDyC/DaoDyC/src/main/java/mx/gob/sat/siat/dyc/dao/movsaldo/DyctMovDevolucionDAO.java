package mx.gob.sat.siat.dyc.dao.movsaldo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyctResolucionAuxVO;


public interface DyctMovDevolucionDAO {
    DyctMovDevolucionDTO consultar (String numControl) throws SIATException;
      
    void actualizarMovDevolucion(DyctMovDevolucionDTO movDevolucion);
    List<DyctResolucionAuxVO> obtenerMovResolucion(String rfc);    
    List<DyctResolucionAuxVO> obtenerMovResDev(String rfc, int tipo);    
    List<DyctMovDevolucionDTO> obtenerXidSaldoIcep(int idSaldoIcep)  throws SIATException;
    void insertar(DyctMovDevolucionDTO devolucionManual) throws SIATException ;
    
    void borrar(Integer idMovDevolucion) throws SIATException ;
    
    DyctMovDevolucionDTO encontrar(Integer idMovCompensacion);
}
