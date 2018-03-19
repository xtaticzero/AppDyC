/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.service.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Paola Rivera
 */
public interface PersonaIDCService {
    /**
     * @param persona
     * @return
     * @throws Exception
     */
    PersonaDTO buscaPersona(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     * @throws Exception
     */
    PersonaIdentificacionDTO buscaPersonaBoId(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     * @throws Exception
     */
    PersonaUbicacionDTO buscaUbicacion(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     */
    PersonaRolDTO buscaRolPorBoId(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     */
    List<PersonaRolDTO> buscaRolesPorBoId(PersonaDTO persona) throws SIATException;

    /**
     * @param rfcContribuyente
     * @return
     * @throws SIATException
     */

    ParamOutputTO<PersonaDTO> findContribuyente(String rfcContribuyente) throws SIATException;
}
