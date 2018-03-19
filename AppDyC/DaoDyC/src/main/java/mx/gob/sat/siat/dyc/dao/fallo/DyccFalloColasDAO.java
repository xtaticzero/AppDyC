package mx.gob.sat.siat.dyc.dao.fallo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloColasDTO;


/**
 * @author Softtek
 */
public interface DyccFalloColasDAO {
    DyccFalloColasDTO find(Integer id);

    void insertFalloColas(DyccFalloColasDTO dyccFalloColasDTO);

    List<DyccFalloColasDTO> getAllFalloColas();
}
