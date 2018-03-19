package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos;

import java.math.BigDecimal;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMontoSaldoAFavorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 *
 * @author Jose Luis Aguilar
 */
public interface MontoSaldoAFavorService {

    void registrar(DycMontoSaldoAFavorDTO montoSaldoAFavor, AccesoUsr accesoUsr) throws SIATException;
    
    void actualizar(DycMontoSaldoAFavorDTO montoSaldoAFavor, AccesoUsr accesoUsr) throws SIATException;
    
    void inActivar(DycMontoSaldoAFavorDTO montoSaldoAFavor, AccesoUsr accesoUsr) throws SIATException;
    
    List<DycMontoSaldoAFavorDTO> consultarTodos() throws SIATException;
    
    List<DycMontoSaldoAFavorDTO> consultarFiltro(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    
    List<DycMontoSaldoAFavorDTO> existe(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException;
    
    BigDecimal getLimiteSaldoFavor(int idTipoTrmite);
    
}
