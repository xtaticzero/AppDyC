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

import mx.gob.sat.siat.dyc.dao.contribuyente.impl.mapper.PersonaUbicacionMapper;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesStoredPersona;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * Clase que hace llamado a un store procedure para obtener los datos de ubicacion de una persona
 * @author Paola Rivera.
 */
public class PersonaUbicacionStoreProcedure extends StoredProcedure {

    public PersonaUbicacionStoreProcedure(JdbcTemplate jdbcTemplate, String string) {
        super(jdbcTemplate, string);
        declareParameter(new SqlParameter(ConstantesStoredPersona.BO_ID, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesStoredPersona.V_TRACE, Types.VARCHAR));
    }

    public PersonaUbicacionDTO buscaUbicacionBOID(String boId) throws SQLException {
        PersonaUbicacionDTO personaUbicacion;
        declaraParametroMapper(new PersonaUbicacionMapper());
        Map inParameters = new HashMap();
        inParameters.put(ConstantesStoredPersona.BO_ID, boId);
        inParameters.put(ConstantesStoredPersona.V_TRACE, ConstantesStoredPersona.VALOR_VO_TRACE);
        Map out = execute(inParameters);
        List<PersonaUbicacionDTO> lista = (List<PersonaUbicacionDTO>)out.get(ConstantesStoredPersona.RESULT_UBICACION);
        personaUbicacion = lista.get(0);
        return personaUbicacion;
    }

    private void declaraParametroMapper(RowMapper rowMapper) {
        declareParameter(new SqlOutParameter(ConstantesStoredPersona.RESULT_UBICACION, OracleTypes.CURSOR, rowMapper));
        compile();
    }
}
