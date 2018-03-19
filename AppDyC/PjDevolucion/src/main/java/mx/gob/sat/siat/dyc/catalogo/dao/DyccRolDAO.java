/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author  Alfredo Ramirez
 * @since   22/08/2013
 *
 */
public interface DyccRolDAO {

    List<DyccRolDTO> consultarRoles();

    /**
     * @param idRol
     * @return ArrayList DyccRolDTO
     */
    List<DyccRolDTO> consultarRoles(int idRol);


    /**
     * @param cero
     * @return ArrayList DyccRolDTO
     */
    List<DyccRolDTO> consultarRolesCero(boolean cero);

    List<DyccRolDTO> obtieneRolesPorAnexo(int anexo);
    
    /**
     * Se utiliza para consultar los roles que estan asociados a un tipo de tramite (consulta que se utiliza para dar 
     * mantenimiento a los catalogos).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    List<DyccRolDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    
    /**
     * Se utiliza para consultar los roles que estan asociados a un tipo de tramite tomando en cuenta la fecha fin 
     * igual a null(consulta que se utiliza para dar mantenimiento a los catalogos en la pantalla de modificacion de 
     * tipos de tramite).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    List<DyccRolDTO> consultarXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException;

}
