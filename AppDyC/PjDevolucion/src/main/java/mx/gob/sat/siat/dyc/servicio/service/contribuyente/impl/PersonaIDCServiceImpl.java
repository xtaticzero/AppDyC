/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.service.contribuyente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.contribuyente.PersonaIDCDAO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Clase que se utiliza para implementar negocio relacionado a Persona en BD IDC
 * @version 1.0
 * @author Paola R.H
 */
@Service(value = "personaIDCService")
public class PersonaIDCServiceImpl implements PersonaIDCService {

    @Autowired
    private PersonaIDCDAO personaIDCDAO;
    private List<PersonaRolDTO> personaRol = new ArrayList<PersonaRolDTO>();

    public PersonaIDCServiceImpl() {
        super();
    }

    /**
     * Busca si existe una persona por el RFC, si existe traera el BoId de la persona
     * @param persona
     * @return
     * @throws Exception
     */
    @Override
    public PersonaDTO buscaPersona(PersonaDTO personaDTO) throws SIATException {
        return personaIDCDAO.buscaPersonaPorRFC(personaDTO);
    }

    /**
     * Metodo que sirve para obtener los datos de identificacion de una persona ya sea moral o
     * fisica
     * @param persona
     * @return
     * @throws Exception
     */
    @Override
    public PersonaIdentificacionDTO buscaPersonaBoId(PersonaDTO persona) throws SIATException{
        PersonaIdentificacionDTO personaBoId = null;
        if (null != persona.getBoId()) {
            personaBoId = personaIDCDAO.buscaPersonaPorBOID(persona);
        }
        return personaBoId;
    }


    /**
     * Metodo que obtiene la ubicacion de una persona apartir de su BoId
     * @param persona
     * @return
     * @throws Exception
     */
    @Override
    public PersonaUbicacionDTO buscaUbicacion(PersonaDTO persona) throws SIATException{
        PersonaUbicacionDTO personaUbicacion = null;
        if (null != persona.getBoId()) {
            personaUbicacion = personaIDCDAO.buscaUbicacionBOID(persona);
        }
        return personaUbicacion;
    }

    public PersonaRolDTO buscaRolPorBoId(PersonaDTO persona) throws SIATException{
        PersonaRolDTO personaRolD = null;
        if (null != persona.getBoId()) {
            personaRolD = personaIDCDAO.buscaRolBOID(persona);
        }
        return personaRolD;
    }

    public List<PersonaRolDTO> buscaRolesPorBoId(PersonaDTO persona) throws SIATException{
        personaRol = null;
        if (null != persona.getBoId() && !(persona.getBoId().equals(""))) {
            personaRol = personaIDCDAO.buscaRoles(persona);
        }
        return personaRol;
    }

    @Override
    public ParamOutputTO<PersonaDTO> findContribuyente(String rfcContribuyente) throws SIATException{
        PersonaDTO persona = null;
        if (null != rfcContribuyente) {
            persona = personaIDCDAO.buscaPersonaPorRFC(new PersonaDTO(rfcContribuyente));
            if (null != persona) {
                persona.setDomicilio(this.personaIDCDAO.buscaUbicacionBOID(persona));
                persona.setPersonaIdentificacion(this.personaIDCDAO.buscaPersonaPorBOID(persona));
            }
        }
        return new ParamOutputTO<PersonaDTO>(persona);
    }

    public void setPersonaRol(List<PersonaRolDTO> personaRol) {
        this.personaRol = personaRol;
    }

    public List<PersonaRolDTO> getPersonaRol() {
        return personaRol;
    }
}
