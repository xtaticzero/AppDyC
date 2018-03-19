package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DycaSolInconsistDAO {
    void insertar(DycaSolInconsistDTO solInconsist);
    List<DycaSolInconsistDTO> selectXNumControl(String numControl);
}
