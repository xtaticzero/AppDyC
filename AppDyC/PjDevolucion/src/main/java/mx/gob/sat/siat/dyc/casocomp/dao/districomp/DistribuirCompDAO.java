package mx.gob.sat.siat.dyc.casocomp.dao.districomp;

import mx.gob.sat.siat.dyc.casocomp.dto.districomp.DistribuirCompVO;


public interface DistribuirCompDAO {
    
    void actualizarEstado(Integer estado, String numControl);
    
    Integer encontrarIcep(DistribuirCompVO distribuirCompDTO);
}
