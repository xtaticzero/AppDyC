package mx.gob.sat.siat.dyc.dao.parametros;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DyccParametrosAppDAO {
    DyccParametrosAppDTO encontrar(Integer idParametro);

    List<DyccParametrosAppDTO> obtieneParametroDto(String idParametro) throws SQLException;

}
