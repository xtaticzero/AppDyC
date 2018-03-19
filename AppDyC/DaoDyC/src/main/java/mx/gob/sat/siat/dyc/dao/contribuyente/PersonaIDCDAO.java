/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Paola Rivera
 */
public interface PersonaIDCDAO {

    /**
     * @param persona
     * @return
     * @throws Exception
     */
    PersonaDTO buscaPersonaPorRFC(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     * @throws Exception
     */
    PersonaIdentificacionDTO buscaPersonaPorBOID(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     * @throws Exception
     */
    PersonaUbicacionDTO buscaUbicacionBOID(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     */
    PersonaRolDTO buscaRolBOID(PersonaDTO persona) throws SIATException;

    /**
     * @param persona
     * @return
     */
    List<PersonaRolDTO> buscaRoles(PersonaDTO persona) throws SIATException;
}
