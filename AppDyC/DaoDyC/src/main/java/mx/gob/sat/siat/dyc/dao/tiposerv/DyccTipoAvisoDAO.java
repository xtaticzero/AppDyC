package mx.gob.sat.siat.dyc.dao.tiposerv;

import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;

/**
 * @author J. Isaac Carbajal Bernal
 * @since 03, Diciembre 2013
 */

public interface DyccTipoAvisoDAO {
    
    DyccTipoAvisoDTO encontrar(Integer id);
}
