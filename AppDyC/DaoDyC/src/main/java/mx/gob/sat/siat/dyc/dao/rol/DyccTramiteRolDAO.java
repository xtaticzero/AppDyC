package mx.gob.sat.siat.dyc.dao.rol;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccTramiteRolDAO {
    void insertar(DyccTramiteRolDTO tramiteRol) throws SIATException;
    List<DyccTramiteRolDTO> consultarTramiteRolXIDTipoTramite (Integer idTipoTramite) throws SIATException;
    boolean consultarTramiteRolXTipoTramiteRol(Integer idTipoTramite, Integer idRol);
    void actualizarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException;
    void colocarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException;
    
    boolean consultarTramiteRolXTipoTramiteRolXFechaFin(Integer idTipoTramite, Integer idRol);
}
