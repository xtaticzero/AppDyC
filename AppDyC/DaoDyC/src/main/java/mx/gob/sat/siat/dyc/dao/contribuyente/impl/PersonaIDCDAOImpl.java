/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.contribuyente.impl;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.contribuyente.PersonaIDCDAO;
import mx.gob.sat.siat.dyc.dao.contribuyente.procedures.PersonaIdentificacionStoreProcedure;
import mx.gob.sat.siat.dyc.dao.contribuyente.procedures.PersonaRFCStoreProcedure;
import mx.gob.sat.siat.dyc.dao.contribuyente.procedures.PersonaRolStoreProcedure;
import mx.gob.sat.siat.dyc.dao.contribuyente.procedures.PersonaUbicacionStoreProcedure;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLOracleIDC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clase para acceder a los store procedure de IDC
 * @version 1.0
 * @author Paola R.H
 */
@Repository("personaIDCDAO")
public class PersonaIDCDAOImpl implements PersonaIDCDAO, SQLOracleIDC {

    @Autowired
    private JdbcTemplate jdbcTemplateDYCRFC;

    private Logger log = Logger.getLogger(PersonaIDCDAOImpl.class);

    /**
     * Método que obtiene el RFC de una persona
     * @param personaDTO
     * @return
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    @Transactional(readOnly =true)
    public PersonaDTO buscaPersonaPorRFC(PersonaDTO personaDTO) throws SIATException {
        PersonaRFCStoreProcedure personaST;
        log.error("Inicio personaST");
        personaST = new PersonaRFCStoreProcedure(jdbcTemplateDYCRFC, STORE_PROCEDURE_BUSCA_POR_FRC);
        log.error("Inicio buscaPersonaPorRFC");
        try {
            log.error("Inicio buscaPersonaPorRFC");
            return personaST.buscaPersonaPorRFC(personaDTO.getRfc().toUpperCase());
            
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_POR_FRC + ConstantesDyC1.TEXTO_3_ERROR_DAO + personaDTO.getRfc());
            throw new SIATException(dae);
        } catch (SQLException sqle) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_POR_FRC + ConstantesDyC1.TEXTO_3_ERROR_DAO + personaDTO.getRfc());
            throw new SIATException(sqle);
        }
    }

    /**
     * Método que obtiene los datos de identificacion de una persona por medio de su Bo_Id
     * @param persona
     * @return
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    @Transactional(readOnly = true)
    public PersonaIdentificacionDTO buscaPersonaPorBOID(PersonaDTO persona) throws SIATException {
        PersonaIdentificacionDTO personaBoIdTmp = null;
        PersonaIdentificacionStoreProcedure personaST;
        personaST = new PersonaIdentificacionStoreProcedure(jdbcTemplateDYCRFC, STORE_PROCEDURE_BUSCA_POR_BOID);
        try {
            personaBoIdTmp = personaST.buscaPersonaPorBOID(persona.getBoId());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_POR_BOID + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(dae);
        } catch (SQLException sqle) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_POR_BOID + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(sqle);
        }
        return personaBoIdTmp;
    }

    /**
     * Método que obtiene los datos de ubicacion de una persona por medio de su Bo_Id
     * @param persona
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PersonaUbicacionDTO buscaUbicacionBOID(PersonaDTO persona) throws SIATException {
        PersonaUbicacionDTO personaUbicacionTmp = null;
        PersonaUbicacionStoreProcedure personaST;

        personaST = new PersonaUbicacionStoreProcedure(jdbcTemplateDYCRFC, STORE_PROCEDURE_BUSCA_UBICACION);
        try {
            personaUbicacionTmp = personaST.buscaUbicacionBOID(persona.getBoId());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_UBICACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(dae);
        } catch (SQLException sqle) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_UBICACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(sqle);
        }
        return personaUbicacionTmp;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaRolDTO buscaRolBOID(PersonaDTO persona) throws SIATException{
        PersonaRolStoreProcedure personaRolST;
        personaRolST =
                new PersonaRolStoreProcedure(jdbcTemplateDYCRFC, SQLOracleIDC.STORE_PROCEDURE_BUSCA_ROL_BOID);
        try {
            return personaRolST.buscaRolPorBOID(persona.getBoId());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ROL_BOID + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(dae);
        } catch (SQLException sqle) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ROL_BOID + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(sqle);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaRolDTO> buscaRoles(PersonaDTO persona) throws SIATException{
        PersonaRolStoreProcedure personaRolST;
        personaRolST =
                new PersonaRolStoreProcedure(jdbcTemplateDYCRFC, SQLOracleIDC.STORE_PROCEDURE_BUSCA_ROL_BOID);
        try {
            return personaRolST.buscaRolesPorBOID(persona.getBoId());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ROL_BOID + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(dae);
        } catch (SQLException sqle) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ROL_BOID + ConstantesDyC1.TEXTO_3_ERROR_DAO + persona.getBoId());
            throw new SIATException(sqle);
        }
    }

}
