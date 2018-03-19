package mx.gob.sat.mat.dyc.catalogo.service;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DyccMontoService {
    DyccMontoDTO obtenerMontoXId(Integer id) throws SIATException;
    
    List<DyccMontoDTO> obtenerMontos() throws SIATException;
}
