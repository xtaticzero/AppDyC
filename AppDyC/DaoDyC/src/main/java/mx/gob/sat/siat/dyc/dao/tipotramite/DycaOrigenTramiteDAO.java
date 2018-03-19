package mx.gob.sat.siat.dyc.dao.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DycaOrigenTramiteDAO {
    List<DycaOrigenTramiteDTO> selectXTipoTramite(Integer idTipoTramite) throws SIATException;
    void insertar (DycaOrigenTramiteDTO objeto) throws SIATException;
    
    /**
     * Actualiza los datos que se necesitan para el origen del tramite a partir del identificador del tramite.
     *
     * @param objeto
     * @throws SIATException
     */
    void actualizar(DycaOrigenTramiteDTO objeto) throws SIATException;
    
    List<DycaOrigenTramiteDTO> selectXIdServicio(Integer idServicio) throws DAOException;
    
}
