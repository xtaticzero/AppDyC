package mx.gob.sat.mat.dyc.asignartramite.service;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaTipoServicioAsignarDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DycaTipoServicioAsignarService {
  
    void insertar(Integer idEmpleado, Integer idMonto, List<DyccTipoServicioDTO> tipoServicioList, List<DycaOrigenTramiteDTO> tramitesList) throws SIATException;

    List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado) throws SIATException;

    List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado, boolean activo) throws SIATException;
        
}
