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

import mx.gob.sat.siat.dyc.dao.contribuyente.impl.mapper.PersonaIdentificacionIDCMapper;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesStoredPersona;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * Clase para realizar el llamado al Store procedure y obtener los datos de identificación de la persona
 * @version 1.0
 * @author Paola R.H
 */
public class PersonaIdentificacionStoreProcedure extends StoredProcedure {

    public PersonaIdentificacionStoreProcedure() {
        super();
    }

    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    public PersonaIdentificacionStoreProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        super(jdbcTemplate, callStoreProcedure);
        declareParameter(new SqlParameter(ConstantesStoredPersona.BO_ID_IDENTIFICACION, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesStoredPersona.VO_TRACE, Types.CHAR));
    }

    public PersonaIdentificacionDTO buscaPersonaPorBOID(String boId) throws SQLException {
        PersonaIdentificacionDTO personaIdentificacion;
        declaraParametroMapper(new PersonaIdentificacionIDCMapper());
        Map inParameters = new HashMap();
        inParameters.put(ConstantesStoredPersona.BO_ID_IDENTIFICACION, boId);
        inParameters.put(ConstantesStoredPersona.VO_TRACE, ConstantesStoredPersona.VALOR_VO_TRACE);
        Map out = execute(inParameters);
        List<PersonaIdentificacionDTO> lista =
            (List<PersonaIdentificacionDTO>)out.get(ConstantesStoredPersona.RESULT_IDENTIFICACION);
        personaIdentificacion = lista.get(0);
        return personaIdentificacion;
    }

    private void declaraParametroMapper(RowMapper rowMapper) {
        declareParameter(new SqlOutParameter(ConstantesStoredPersona.RESULT_IDENTIFICACION, OracleTypes.CURSOR, rowMapper));
        compile();
    }
}
