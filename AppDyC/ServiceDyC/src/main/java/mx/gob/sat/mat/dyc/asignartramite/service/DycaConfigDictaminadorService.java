package mx.gob.sat.mat.dyc.asignartramite.service;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DycaConfigDictaminadorService {
    DycaConfigDictaminadorDTO insertar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws SIATException;
    
    List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar) throws SIATException;

    int actualizar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws SIATException;

    List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar, boolean activo) throws SIATException;
}
