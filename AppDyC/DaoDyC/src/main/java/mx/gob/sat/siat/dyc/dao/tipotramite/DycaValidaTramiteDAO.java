package mx.gob.sat.siat.dyc.dao.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccValidacionDTO;


/**
 *
 * @author Softtek
 */
public interface DycaValidaTramiteDAO {

    List selecXValidacion(DyccValidacionDTO validacion);

}
