package mx.gob.sat.siat.dyc.dao.tiposerv;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;


public interface DyccTipoDeclaracionDAO {
    DyccTipoDeclaraDTO encontrar(Integer idTipoDeclaracion);
    
    List<DyccTipoDeclaraDTO> obtenerTiposDeDeclaraciones();
}
