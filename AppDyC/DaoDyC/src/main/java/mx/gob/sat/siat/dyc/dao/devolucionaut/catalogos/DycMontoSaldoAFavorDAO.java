package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos;

import java.math.BigDecimal;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMontoSaldoAFavorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DycMontoSaldoAFavorDAO {
    void insertar(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    void modificar(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    void inactivar(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    List<DycMontoSaldoAFavorDTO> consultarTodos() throws SIATException;
    List<DycMontoSaldoAFavorDTO> consultarFiltro(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    List<DycMontoSaldoAFavorDTO> existe(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    BigDecimal getLimiteSaldoFavor(int idTipoTramite);
}
