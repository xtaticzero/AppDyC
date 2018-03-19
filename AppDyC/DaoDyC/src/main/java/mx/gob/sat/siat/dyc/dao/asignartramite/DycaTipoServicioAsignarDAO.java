package mx.gob.sat.siat.dyc.dao.asignartramite;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaTipoServicioAsignarDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DycaTipoServicioAsignarDAO {
    DycaTipoServicioAsignarDTO insertar(DycaTipoServicioAsignarDTO dycaTipoServicioAsignarDTO) throws DAOException;
    
    List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado) throws DAOException;

    int actualizar(DycaTipoServicioAsignarDTO dycaTipoServicioAsignarDTO) throws DAOException;

    List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado, boolean activo) throws DAOException;
}
