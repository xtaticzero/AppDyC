package mx.gob.sat.siat.dyc.dao.anexo;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccMatrizAnexosDAO {
    List<DyccMatrizAnexosDTO> seleccionar();

    DyccMatrizAnexosDTO getAnexo(int idAnexo) throws SQLException;
    List<DyccMatrizAnexosDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    List<DyccMatrizAnexosDTO> consultarMatrizAnexos();

    int consultarExisteNombreAnexo(String nombre);

    void adicionarAnexo(DyccMatrizAnexosDTO dyccMatrizAnexosDto, DyccTipoTramiteDTO[] tramites,
                        DyccRolDTO[] roles);

    void modificarAnexo(DyccMatrizAnexosDTO dyccMatrizAnexosDto, DyccTipoTramiteDTO[] tramites,
                        DyccRolDTO[] roles);

    void eliminarAnexo(int idAnexo);

    List<DyccMatrizAnexosDTO> buscarAnexosARequerir(int idTipoTramite);
    
    DyccMatrizAnexosDTO buscaAnexoPorId(Integer idAnexo);
    
    List<DyccMatrizAnexosDTO> consultarXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException;

}
