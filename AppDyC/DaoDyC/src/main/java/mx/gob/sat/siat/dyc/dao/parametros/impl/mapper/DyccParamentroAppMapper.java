package mx.gob.sat.siat.dyc.dao.parametros.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccParamentroAppMapper implements RowMapper<DyccParametrosAppDTO> {

    @Override
    public DyccParametrosAppDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        DyccParametrosAppDTO dyccParamentroApp = new DyccParametrosAppDTO();
        dyccParamentroApp.setIdParametro(resultSet.getInt("IDPARAMETRO"));
        dyccParamentroApp.setDescripcion(resultSet.getString("DESCRIPCION"));
        dyccParamentroApp.setValor(resultSet.getBigDecimal("VALOR"));
        dyccParamentroApp.setFechaInicio(resultSet.getDate("FECHAINICIO"));
        dyccParamentroApp.setFechaFin(resultSet.getDate("FECHAFIN"));

        return dyccParamentroApp;
    }

}
