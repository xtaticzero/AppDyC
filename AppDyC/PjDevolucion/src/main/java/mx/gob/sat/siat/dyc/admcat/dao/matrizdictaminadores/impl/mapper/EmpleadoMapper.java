/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public class EmpleadoMapper implements RowMapper<EmpleadoDTO> {
    public EmpleadoMapper() {
        super();
    }

    @Override
    public EmpleadoDTO mapRow(ResultSet rs, int i) throws SQLException {
        EmpleadoDTO empl = new EmpleadoDTO();

        empl.setIdEmpleado(rs.getString("EMPLID"));
        empl.setCiudad(rs.getString("COUNTRY_NM_FORMAT"));
        empl.setNombreCompleto(rs.getString("NAME"));
        empl.setNombre(rs.getString("FIRST_NAME"));
        empl.setApellidoPaterno(rs.getString("LAST_NAME"));
        empl.setApellidoMaterno(rs.getString("SECOND_LAST_NAME"));
        empl.setFecha(rs.getDate("ASOFDATE"));

        return empl;
    }
}
