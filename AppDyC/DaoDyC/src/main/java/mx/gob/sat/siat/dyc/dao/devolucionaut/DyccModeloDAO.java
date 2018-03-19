package mx.gob.sat.siat.dyc.dao.devolucionaut;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;


/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DyccModeloDAO {

    DyccModelo consultarPorId(Integer idModelo) throws DAOException;

    DyccModelo consultarPorDescripcion(String descripcion) throws DAOException;

    List<DyccModelo> consultarPorEstado(boolean activo) throws DAOException;

    List<DyccModelo> consultarTodos() throws DAOException;
}
