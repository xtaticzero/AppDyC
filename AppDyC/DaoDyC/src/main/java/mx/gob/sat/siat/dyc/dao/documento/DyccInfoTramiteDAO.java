package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccInfoTramiteDAO {
    List<DyccInfoTramiteDTO> selecXTipotramite(DyccTipoTramiteDTO tipoTramite);
    void insertar(DyccInfoTramiteDTO info) throws SIATException;
    boolean consultarTramiteRolXTipoTramiteInfo(Integer idTipoTramite, Integer idRol);
    void actualizarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException;
    void colocarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException;
}
