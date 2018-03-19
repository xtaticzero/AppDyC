package mx.gob.sat.siat.dyc.dao.periodo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccTipoPeriodoDAO {
    DyccTipoPeriodoDTO encontrar(String id);

    List<DyccTipoPeriodoDTO> obtieneTipoPeriodo();

    List<DyccTipoPeriodoDTO> tipoPeriodo(String filtro);

    CatalogoTO findTipoPeriodo(int idPeriodo);
    
    DyccTipoPeriodoDTO obtenerTipoPeriodoPorIdPeriodo(Integer idPeriodo);
 
    /**
     * Consulta los periodos asociados a un tramite (esta consulta se utiliza para administrar el catalogo de tipos de 
     * tramites).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    List<DyccTipoPeriodoDTO> consultaXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    
    /**
     * Consulta los periodos asociados a un tramite (esta consulta se utiliza para administrar el catalogo de tipos de 
     * tramites) considerando la fecha fin igual a null.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    List<DyccTipoPeriodoDTO> consultaXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException;
}
