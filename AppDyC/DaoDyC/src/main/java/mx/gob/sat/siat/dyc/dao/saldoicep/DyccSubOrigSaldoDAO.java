package mx.gob.sat.siat.dyc.dao.saldoicep;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 04, Diciembre 2013
 */
public interface DyccSubOrigSaldoDAO {
    DyccSubOrigSaldoDTO encontrar(Integer id);

    /**
     * @param idTipoTramite
     * @return
     */
    List<DyccSubOrigSaldoDTO> obtieneSubOrigSaldo(long idTipoTramite);

    List<DyccSubOrigSaldoDTO> consultarSuborigenesDeSaldos();

    int insertarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                 List<DyccTipoTramiteDTO> selectedTramites);
        
    int delete(DyccSubOrigSaldoDTO subOrigSaldo);

    int actualizarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                   List<DyccTipoTramiteDTO> selectedTramitesToShow);

    List<DyccSubOrigSaldoDTO> obtenerSubOrigenesDeSaldosPorTramite(int idOrigenSaldo,

        int idTipoTramite);

    List<DyccActividadDTO> getActividadesEconomicas(int idSubOrigSaldo) throws SIATException;
}
