package mx.gob.sat.siat.dyc.dao.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccTtSubTramiteDAO {
    void insertar(DyccTtSubtramiteDTO  subTramite) throws SIATException;
    List<DyccTtSubtramiteDTO> consultarXIdTipoTramite (Integer idTipoTramite) throws SIATException;
    boolean consultarXTipoTramiteSubTramite(Integer idTipoTramite, Integer idTipoUnidadAdmva);
    void actualizarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException;
    void colocarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException;
}
