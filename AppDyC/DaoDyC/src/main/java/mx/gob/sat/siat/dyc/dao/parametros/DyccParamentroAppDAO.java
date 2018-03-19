package mx.gob.sat.siat.dyc.dao.parametros;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;


public interface DyccParamentroAppDAO {

    List<DyccParametrosAppDTO> buscarParametrosApp();

    DyccParametrosAppDTO encontrar(Integer idParametro);
}
