package mx.gob.sat.siat.dyc.dao.asignartramite;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DycaConfigDictaminadorDAO {
    DycaConfigDictaminadorDTO insertar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws DAOException;
    
    List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar) throws DAOException;

    int actualizar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws DAOException;

    List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar, boolean activo) throws DAOException;
}
