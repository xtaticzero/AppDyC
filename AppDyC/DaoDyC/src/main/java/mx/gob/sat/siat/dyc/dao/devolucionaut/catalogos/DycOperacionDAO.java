package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycOperacionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Mario Lizaola Ruiz
 */
public interface DycOperacionDAO {
    void insertar(DycOperacionDTO operacion) throws SIATException;
}
