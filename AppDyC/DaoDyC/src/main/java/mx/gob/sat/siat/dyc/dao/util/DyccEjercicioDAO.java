package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;


public interface DyccEjercicioDAO {
    DyccEjercicioDTO encontrar(Integer id);

    List<DyccEjercicioDTO> seleccionar();

    /**
     * @return
     */
    List<DyccEjercicioDTO> obtieneEjercicio();


    /**
     * @return
     */
    DyccEjercicioDTO obtieneEjercicioPorId(DyccEjercicioDTO ejercicio);
}
