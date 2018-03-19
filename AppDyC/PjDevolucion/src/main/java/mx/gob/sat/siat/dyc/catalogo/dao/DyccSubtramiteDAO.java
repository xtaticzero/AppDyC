/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Paola Rivera
 */
public interface DyccSubtramiteDAO {
    /**
     * @return
     */
    List<DyccSubTramiteDTO> obtieneSubtramite();
    
    /**
     * Consulta los subtramites asociados al tipo de tramite dado de alta (Esta consulta se realiza para administrar 
     * los catalogos de tipo de tramite).
     *
     * @param idTipoTramite
     * @return
     */
    List<DyccSubTramiteDTO> consultaXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    
    /**
     * Consulta los subtramites asociados al tipo de tramite dado de alta (Esta consulta se realiza para administrar 
     * los catalogos de tipo de tramite) considerando la fecha fin igual a null.
     *
     * @param idTipoTramite
     * @return
     */
    List<DyccSubTramiteDTO> consultaXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException;

}
