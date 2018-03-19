package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccInfoARequerirDAO
{
    List<DyccInfoARequerirDTO> seleccionar();
    /**
     * Consulta la informacion a requerir que esta asociada a un tramite (esta consulta se utiliza para administrar
     * el catalogo de tramites).
     *
     * @param idTipoTramite
     * @return
     */
    List<DyccInfoARequerirDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    
    List<DyccInfoARequerirDTO> buscarInfoARequerir(int idTipoTramite);
    
    /**
     * Consulta la informacion a requerir que esta asociada a un tramite (esta consulta se utiliza para administrar
     * el catalogo de tramites) considerando que la fecha fin es null.
     *
     * @param idTipoTramite
     * @return
     */
    List<DyccInfoARequerirDTO> consultarXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException;
    
    /**
     * <pre>
     * Consulta la tabla de Dycc_InfoARequerir por el id: idInfoARequerir
     * </pre>
     *
     * @param idInfoARequerir
     * @return
     */
    DyccInfoARequerirDTO consultarXIdInfoARequerir(Integer idInfoARequerir);
}
