/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.contribuyente.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.contribuyente.impl.mapper.PersonaRolMapper;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesStoredPersona;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * @author Paola Rivera
 */
public class PersonaRolStoreProcedure extends StoredProcedure {

    public PersonaRolStoreProcedure() {
        super();
    }

    public PersonaRolStoreProcedure(JdbcTemplate jdbcTemplate, String string) {
        super(jdbcTemplate, string);
        declareParameter(new SqlParameter(ConstantesStoredPersona.BO_ID, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesStoredPersona.V_TRACE, Types.VARCHAR));
    }

    public PersonaRolDTO buscaRolPorBOID(String boId) throws SQLException {
        PersonaRolDTO personaRol;
        declaraParametroMapper(new PersonaRolMapper());
        Map inParameters = new HashMap();
        inParameters.put(ConstantesStoredPersona.BO_ID, boId);
        inParameters.put(ConstantesStoredPersona.V_TRACE, 1);
        Map out = execute(inParameters);
        List<PersonaRolDTO> lista = (List<PersonaRolDTO>)out.get(ConstantesStoredPersona.RESULT_ROL);
        personaRol = lista.get(0);
        return personaRol;
    }

    public List<PersonaRolDTO> buscaRolesPorBOID(String boId) throws SQLException {
        declaraParametroMapper(new PersonaRolMapper());
        Map inParameters = new HashMap();
        inParameters.put(ConstantesStoredPersona.BO_ID, boId);
        inParameters.put(ConstantesStoredPersona.V_TRACE, 1);
        Map out = execute(inParameters);
        return (List<PersonaRolDTO>)out.get(ConstantesStoredPersona.RESULT_ROL);
    }

    private void declaraParametroMapper(RowMapper rowMapper) {
        declareParameter(new SqlOutParameter(ConstantesStoredPersona.RESULT_ROL, OracleTypes.CURSOR, rowMapper));
        compile();
    }
}
