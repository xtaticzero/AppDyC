package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;


/**
 *
 * @author Softtek
 */
public interface DyctAccionPrivAjusDAO {
    
    int insertar(DyctAccionPrivAjusDTO accionPrivAjus);
    
    List<DyctAccionPrivAjusDTO> seleccionar();

    List<DyctAccionPrivAjusDTO> selecUltimasAccionesXEmpleado();

    DyctAccionPrivAjusDTO selecUltimaAccionEmpleado(Integer numEmpleado);
}
