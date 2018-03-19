package mx.gob.sat.siat.dyc.dao.inconsistencia;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DyccInconsistenciaDAO {

    DyccInconsistDTO encontrar(Integer idInconsistencia);

    DycaSolInconsistDTO buscarSolicitudInconsitencia(Integer idInconsistencia, String numeroControl);

    void insertarInconsistencia(DycaSolInconsistDTO dycaSolInconsistDTO);

    void actualizarSolicitudInconsistencia(DycaSolInconsistDTO dTO); 
}
