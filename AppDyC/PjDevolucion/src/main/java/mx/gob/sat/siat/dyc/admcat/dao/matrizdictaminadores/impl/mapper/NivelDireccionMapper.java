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

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public class NivelDireccionMapper implements RowMapper<EmpleadoVO> {

    @Override
    public EmpleadoVO mapRow(ResultSet rs, int i) throws SQLException {
        EmpleadoVO empl = new EmpleadoVO();
        empl.setClaveNivel(rs.getInt("CLAVE_NIVEL_DIRECCION"));
        empl.setDescClaveNivel(rs.getString("NIVEL_DIRECCION"));
        return empl;
    }
}
