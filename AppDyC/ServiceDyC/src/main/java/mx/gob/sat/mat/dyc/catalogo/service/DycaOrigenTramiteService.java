package mx.gob.sat.mat.dyc.catalogo.service;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DycaOrigenTramiteService {
    List<DycaOrigenTramiteDTO> selectXTipoTramite(Integer idTipoTramite) throws SIATException;
    
    void insertar(DycaOrigenTramiteDTO objeto) throws SIATException;
    
    void actualizar(DycaOrigenTramiteDTO objeto) throws SIATException;
    
    List<DycaOrigenTramiteDTO> selectXIdServicio(Integer idServicio) throws SIATException;
}
