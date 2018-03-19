/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   22/08/2013
 *
 */
public interface DyccRolService {

    List<DyccRolDTO> consultarRoles();

    /**
     * @return ArrayList DyccRolDTO
     */
    List<DyccRolDTO> obtieneRolesPorAnexo(int anexo);

    /**
     * @param IdRol
     * @return ArrayList DyccRolDTO
     */
    List<DyccRolDTO> consultarRoles(int idRol);

    /**
     * @param cero
     * @return
     */
    List<DyccRolDTO> consultarRolesCero(boolean cero);

}
