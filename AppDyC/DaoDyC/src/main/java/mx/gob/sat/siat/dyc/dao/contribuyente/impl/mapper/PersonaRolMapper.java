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

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;

import org.springframework.jdbc.core.RowMapper;


public class PersonaRolMapper implements RowMapper<PersonaRolDTO> {
    public PersonaRolMapper() {
        super();
    }

    @Override
    public PersonaRolDTO mapRow(ResultSet rs, int i) throws SQLException {
        PersonaRolDTO personaRol = new PersonaRolDTO();
        personaRol.setDescripcionRol(rs.getString("DESC_ROL"));
        personaRol.setFechaAltaRol(rs.getDate("FEC_ALTA_ROL"));
        personaRol.setFechaBajaRol(rs.getDate("FEC_BAJA_ROL"));
        if (rs.getString("CVE_ROL") != null && !rs.getString("CVE_ROL").equals("")) {
            personaRol.setClaveRol(rs.getInt("CVE_ROL"));
        }
        personaRol.setDescripcionTipo(rs.getString("DESC_TIPO"));

        return personaRol;
    }
}
