/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Interface DAO catalogo DYCC_UNIDADTRAMITE
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 9, 2013
 * @since 0.1
 *
 * */
public interface DyccUnidadTramiteDAO {

    /**
     * @return Lista ArrayList [DyccUnidadTramiteDTO]
     * @throws Exception
     */
    List<DyccUnidadTramiteDTO> obtieneLista();

    /**
     * @param dyccUnidadTramiteDTO
     * @throws Exception
     */
    void insertarUnidadTramite(DyccUnidadTramiteDTO dyccUnidadTramiteDTO) throws SIATException;

    /**
     * @param listaUnidadt
     * @throws Exception
     */
    void insertarUnidadTramite(List<DyccUnidadTramiteDTO> listaUnidadt) throws SIATException;
    
    List<DyccUnidadTramiteDTO> consultarUnidadTramiteXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    
    /**
     * Consulta si existe algun tramite dado de alta con una unidad administrativa en base de datos sin tomar en cuenta 
     * la fecha fin.
     *
     * @param idTipoTramite 
     * @param idTipoUnidadAdmva
     * @return
     */
    boolean consultarXTipoTramiteUnidadAdmva(Integer idTipoTramite, Integer idTipoUnidadAdmva);
    
    /**
     * Consulta si existe algun tramite dado de alta con una unidad administrativa en base de datos tomando en cuenta 
     * la fecha fin en null
     *
     * @param idTipoTramite 
     * @param idTipoUnidadAdmva
     * @return
     */
    boolean consultarXTipoTramiteUnidadAdmvaFechaFin(Integer idTipoTramite, Integer idTipoUnidadAdmva);
    
    void actualizarFechaFin (Integer idTipoTramite, Integer idTipoUnidadAdmva) throws SIATException;
    
    void colocarFechaFin (Integer idTipoTramite, Integer idTipoUnidadAdmva) throws SIATException;
}
