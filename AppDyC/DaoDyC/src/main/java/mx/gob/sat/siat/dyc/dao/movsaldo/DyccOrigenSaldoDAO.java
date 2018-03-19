package mx.gob.sat.siat.dyc.dao.movsaldo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface DyccOrigenSaldoDAO {

    DyccOrigenSaldoDTO encontrar(Integer id);

    /**
     * @return
     */
    List<DyccOrigenSaldoDTO> obtieneTodos();

    /**
     * @param origenSaldo
     */
    void agrega(DyccOrigenSaldoDTO origenSaldo);

    /**
     * @param origenSaldo
     */
    void actualiza(DyccOrigenSaldoDTO origenSaldo);

    /**
     * @param idOrigenSaldo
     */
    void elimina(int idOrigenSaldo);

    List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion();

    /**
     * @see
     * {@link mx.gob.sat.siat.dyc.dao.impl.DyccOrigenSaldoDAOImpl.obtieneOrigenesDeSaldosPorAvisoCompensacion}
     */
    List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion(final String param1);

    List<DyccOrigenSaldoDTO> obtieneTodos(String idConsulta);

    List<DyccOrigenSaldoDTO> consultaXTipoServicio(Integer idTipoServicio) throws SIATException;

    DyccOrigenSaldoDTO obtenerOrigenSaldoPorIdTipoTramite(Integer idTipoTramite);

    DyccOrigenSaldoDTO getOrigenSaldo(int idOrigen);

    List<DyccOrigenSaldoDTO> obtenerOrigenSaldoTramitesSinOficio();

}
