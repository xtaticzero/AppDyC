package mx.gob.sat.siat.dyc.dao.parametros.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DyccParametrosAppMapper implements RowMapper<DyccParametrosAppDTO> {

    @Override
    public DyccParametrosAppDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccParametrosAppDTO parametroApp = new DyccParametrosAppDTO();
        parametroApp.setIdParametro(rs.getInt("IDPARAMETRO"));
        parametroApp.setDescripcion(rs.getString("DESCRIPCION"));
        parametroApp.setValor(rs.getBigDecimal("VALOR"));
        parametroApp.setFechaInicio(rs.getDate("FECHAINICIO"));
        parametroApp.setFechaFin(rs.getDate("FECHAFIN"));
        parametroApp.setValorStr(rs.getString("VALORSTR"));
        return parametroApp;
    }
}
