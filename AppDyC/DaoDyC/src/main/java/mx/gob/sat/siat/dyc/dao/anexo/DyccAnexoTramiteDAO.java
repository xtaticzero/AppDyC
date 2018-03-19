package mx.gob.sat.siat.dyc.dao.anexo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccAnexoTramiteDAO
{
    List<DyccAnexoTramiteDTO> selecXTipotramite(DyccTipoTramiteDTO tipoTramite);
    void insertar(List<DyccAnexoTramiteDTO> listaAnexos) throws SIATException;
    void insertar(DyccAnexoTramiteDTO anexo) throws SIATException;
    boolean consultarAnexoXTramiteyAnexo(Integer idTipoTramite, Integer idAnexo);
    void actualizarFechaFin(Integer idTipoTramite, Integer idAnexo) throws SIATException;
    void colocarFechaFin(Integer idTipoTramite, Integer idAnexo) throws SIATException;
}
