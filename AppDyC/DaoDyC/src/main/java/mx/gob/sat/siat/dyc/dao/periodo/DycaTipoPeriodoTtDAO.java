package mx.gob.sat.siat.dyc.dao.periodo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DycaTipoPeriodoTtDAO {
    void insertar (DycaTipoPeriodoTtDTO tipoPeriodo) throws SIATException;
    List<DycaTipoPeriodoTtDTO> consultarXIdTipoTramite (Integer idTipoTramite) throws SIATException;
    boolean consultarXTipoTramiteTipoPeriodo(Integer idTipoTramite, String idTipoPeriodo);
    void actualizarFechaFin(Integer idTipoTramite, String idTipoPeriodo) throws SIATException;
    void colocarFechaFin(Integer idTipoTramite, String idTipoPeriodo) throws SIATException;
}
