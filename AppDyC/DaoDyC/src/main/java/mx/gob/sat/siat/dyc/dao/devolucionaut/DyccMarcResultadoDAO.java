package mx.gob.sat.siat.dyc.dao.devolucionaut;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccMarcResultado;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;


/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DyccMarcResultadoDAO {

    DyccMarcResultado consultarPorId(String idMarcResultado) throws DAOException;

    DyccMarcResultado consultarPorDescripcion(String descripcion) throws DAOException;

    List<DyccMarcResultado> consultarPorEstado(boolean activo) throws DAOException;

    List<DyccMarcResultado> consultarTodos() throws DAOException;
}
