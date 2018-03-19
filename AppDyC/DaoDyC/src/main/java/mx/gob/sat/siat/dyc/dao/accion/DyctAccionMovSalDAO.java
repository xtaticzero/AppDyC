package mx.gob.sat.siat.dyc.dao.accion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;


/**
 *
 * @author softtek
 */
public interface DyctAccionMovSalDAO {
    int insertar(DyctAccionMovSalDTO accionMovSal);

    List<DyctAccionMovSalDTO> seleccionarOrder(String orderBy);
    
    List<DyctAccionMovSalDTO> selecBitacora();

    List<DyctAccionMovSalDTO> selecOrderXSaldoicep(DyctSaldoIcepDTO saldoIcep, String orderBy);
}
