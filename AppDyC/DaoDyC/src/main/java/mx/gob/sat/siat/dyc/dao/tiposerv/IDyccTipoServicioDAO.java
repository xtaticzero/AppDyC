package mx.gob.sat.siat.dyc.dao.tiposerv;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface IDyccTipoServicioDAO {
    
    DyccTipoServicioDTO encontrar(Integer idTipoServicio);
    List<DyccTipoServicioDTO> seleccionar();
}
