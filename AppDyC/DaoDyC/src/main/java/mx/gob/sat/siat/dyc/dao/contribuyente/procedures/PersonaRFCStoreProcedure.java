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

import mx.gob.sat.siat.dyc.dao.contribuyente.impl.mapper.PersonaRFCMapper;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesStoredPersona;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * Clase donde se hace llamado a Store procedure para saber si existe o no el RFC de una persona
 * @version 1.0
 * @author Paola R.H
 */
public class PersonaRFCStoreProcedure extends StoredProcedure {
    
    
    public PersonaRFCStoreProcedure(JdbcTemplate jdbcTemplateIDC, String spCall) {
        super(jdbcTemplateIDC, spCall);
        declareParameter(new SqlParameter(ConstantesStoredPersona.RFC,Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesStoredPersona.CURP,Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesStoredPersona.X,Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesStoredPersona.VO_TRACE,Types.CHAR));
    }
    
    public PersonaDTO buscaPersonaPorRFC(String rfc) throws SQLException{
        PersonaDTO persona = null;
        
        declaraParametroMapper(new PersonaRFCMapper());
        Map inParameters = new HashMap();
        inParameters.put(ConstantesStoredPersona.RFC, rfc);
        inParameters.put(ConstantesStoredPersona.CURP,ConstantesStoredPersona.VALOR_CURP);
        inParameters.put(ConstantesStoredPersona.X, ConstantesStoredPersona.VALOR_X);
        inParameters.put(ConstantesStoredPersona.VO_TRACE,ConstantesStoredPersona.VALOR_VO_TRACE);
        Map out = execute(inParameters);
        List<PersonaDTO> lista = ( List<PersonaDTO> )out.get(ConstantesStoredPersona.RESULT_RFC);
        persona = lista.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA);
       return persona;  
    }
    
    
    private void declaraParametroMapper(RowMapper rowMapper){
        declareParameter(new SqlOutParameter(ConstantesStoredPersona.RESULT_RFC, OracleTypes.CURSOR, rowMapper));    
        compile();
    }
}
