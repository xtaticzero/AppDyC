package mx.gob.sat.siat.dyc.dao.devolucionaut;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctWsAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Mario Lizaola Ruiz
 */
public interface DyctWsAutomaticaDAO {
    void insertar(DyctWsAutomaticaDTO wsAutomatica) throws SIATException;
}
