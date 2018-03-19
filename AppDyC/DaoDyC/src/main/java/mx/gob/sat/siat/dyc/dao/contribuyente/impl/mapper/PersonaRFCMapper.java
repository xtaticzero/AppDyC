/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.contribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper que llena los datos basicos de la persona
 * @version 1.0
 * @author Paola R.H
 */
public class PersonaRFCMapper implements RowMapper<PersonaDTO> {
    public PersonaRFCMapper() {
        super();
    }

    public PersonaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getString(1) == null && rs.getString(2) == null) {
            return null;
        }

        PersonaDTO persona = new PersonaDTO();
        persona.setRfc(rs.getString("RFC_ORIGINAL"));
        persona.setRfcVigente(rs.getString("RFC_VIGENTE"));
        persona.setRfcSolicitado(rs.getString("RFC_SOLICITADO"));
        persona.setBoId(rs.getString("BOID"));
        persona.setIdPersona(rs.getString("PERSONID"));
        return persona;
    }
}
