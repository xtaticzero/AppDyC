/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.periodovacacional.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DyctReinicioSecParamDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Orlando Tepoz Z.
 */
public class DyctReinicioSecParamMapper implements RowMapper<DyctReinicioSecParamDTO> {

    public DyctReinicioSecParamDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyctReinicioSecParamDTO dyctReinicioSecParamDTO = new DyctReinicioSecParamDTO();
        dyctReinicioSecParamDTO.setIdProgreInicio(rs.getInt("IDPROGREINICIO"));
        dyctReinicioSecParamDTO.setFechaProgramacion(rs.getTimestamp("FECHAPROGRAMACION"));
        dyctReinicioSecParamDTO.setEstado(rs.getBoolean("ESTADO"));
        dyctReinicioSecParamDTO.setFechaFin(rs.getDate("FECHAFIN"));

        return dyctReinicioSecParamDTO;
    }
}
