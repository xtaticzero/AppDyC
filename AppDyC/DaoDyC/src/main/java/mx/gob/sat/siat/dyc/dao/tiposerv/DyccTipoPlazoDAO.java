package mx.gob.sat.siat.dyc.dao.tiposerv;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccTipoPlazoDAO {
    List<DyccTipoPlazoDTO> consultar() throws SIATException;
    DyccTipoPlazoDTO consultar(Integer idTipoPlazo) throws SIATException;
}
