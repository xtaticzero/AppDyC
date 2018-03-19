package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycOperacionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Mario Lizaola Ruiz
 */
public interface OperacionService {
    void insertar(DycOperacionDTO operacion) throws SIATException;
}
