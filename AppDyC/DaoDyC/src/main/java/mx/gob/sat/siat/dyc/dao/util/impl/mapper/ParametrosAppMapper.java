package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;

import org.springframework.jdbc.core.RowMapper;


public class ParametrosAppMapper  implements RowMapper<DyccParametrosAppDTO>
{
    @Override
    public DyccParametrosAppDTO mapRow(ResultSet resultSet, int i) throws SQLException
    {
        DyccParametrosAppDTO parametro = new DyccParametrosAppDTO();
        parametro.setIdParametro(resultSet.getInt("IDPARAMETRO")); 
        parametro.setDescripcion(resultSet.getString("DESCRIPCION"));
        parametro.setValor(resultSet.getBigDecimal("VALOR"));
        parametro.setFechaInicio(resultSet.getDate("FECHAINICIO"));
        parametro.setFechaFin(resultSet.getDate("FECHAFIN"));
        
        return parametro;
    }
}