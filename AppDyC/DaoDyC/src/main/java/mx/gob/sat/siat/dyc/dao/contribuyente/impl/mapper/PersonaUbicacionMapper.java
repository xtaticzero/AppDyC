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

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el dto de PersonaUbicacionDTO
 * @author Paola Rivera
 */
public class PersonaUbicacionMapper implements RowMapper<PersonaUbicacionDTO> {
    public PersonaUbicacionMapper() {
        super();
    }

    @Override
    public PersonaUbicacionDTO mapRow(ResultSet rs, int i) throws SQLException {
        PersonaUbicacionDTO personaUbicacion = new PersonaUbicacionDTO();
        personaUbicacion.setClaveAdmonLocal(rs.getInt("CLAVEALR"));
        personaUbicacion.setAdmonLocal(rs.getString("DESCRIPCIONALR"));
        personaUbicacion.setCalle(rs.getString("CALLE"));
        personaUbicacion.setNumeroInt(rs.getString("NUMEROINTERIOR"));
        personaUbicacion.setNumeroExt(rs.getString("NUMEROEXTERIOR"));
        personaUbicacion.setClaveColonia(rs.getString("CLAVECOLONIA"));
        personaUbicacion.setColonia(rs.getString("DESCRIPCIONCOLONIA"));
        personaUbicacion.setClaveLocalidad(rs.getString("CLAVELOCALIDAD"));
        personaUbicacion.setLocalidad(rs.getString("DESCRIPCONLOCALIDAD"));
        personaUbicacion.setEntreCalle1(rs.getString("ENTRECALLE1"));
        personaUbicacion.setEntreCalle2(rs.getString("ENTRECALLE2"));
        personaUbicacion.setCodigoPostal(rs.getString("CODIGOPOSTAL"));
        personaUbicacion.setClaveMunicipio(rs.getString("CLAVEMUNICIPIO"));
        personaUbicacion.setMunicipio(rs.getString("DESCRIPCIONMUNICIPIO"));
        personaUbicacion.setClaveEntFed(rs.getString("CLAVEENTIDADFEDERATIVA"));
        personaUbicacion.setEntFed(rs.getString("DESCRIPCIONENTIDADFEDERATIVA"));
        personaUbicacion.setPais(rs.getString("PAISRESIDENCIA"));
        personaUbicacion.setCentroRegional(rs.getString("REGIONID"));
        personaUbicacion.setDescCentroRegional(rs.getString("REGIOSDESC"));
        personaUbicacion.setTelefono1(rs.getString("TELEFONO1"));
        personaUbicacion.setTelefono2(rs.getString("TELEFONO2"));
        personaUbicacion.setEmail(rs.getString("EMAIL"));

        return personaUbicacion;
    }
}
