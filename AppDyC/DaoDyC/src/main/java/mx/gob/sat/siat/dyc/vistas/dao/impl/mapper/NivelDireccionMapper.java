package mx.gob.sat.siat.dyc.vistas.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vistas.vo.DycvEmpleadoVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public class NivelDireccionMapper implements RowMapper<DycvEmpleadoVO> {

    @Override
    public DycvEmpleadoVO mapRow(ResultSet rs, int i) throws SQLException {
        DycvEmpleadoVO obj = new DycvEmpleadoVO();
        obj.setIntClaveNivelDireccion(rs.getInt("CLAVE_NIVEL_DIRECCION"));
        obj.setClaveNivelDireccion(rs.getString("NIVEL_DIRECCION"));
        return obj;
    }
}
