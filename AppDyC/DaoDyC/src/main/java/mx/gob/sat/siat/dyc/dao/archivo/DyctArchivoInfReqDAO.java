package mx.gob.sat.siat.dyc.dao.archivo;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctArchivoInfReqDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface DyctArchivoInfReqDAO {
    void insertar (DyctArchivoInfReqDTO dyctArchivoInfReqDTO) throws SIATException;
}
