package mx.gob.sat.siat.dyc.dao.catalogo;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DyccMontoDAO {

    DyccMontoDTO obtenerMontoXId(Integer id) throws DAOException;

    List<DyccMontoDTO> obtenerMontos() throws DAOException;
}
