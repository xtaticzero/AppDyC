package mx.gob.sat.siat.migradordyc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.migradordyc.TablaDTO;

import org.springframework.jdbc.core.RowMapper;


public class TablaMapper implements RowMapper<TablaDTO>
{
    @Override
    public TablaDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        TablaDTO tabla = new TablaDTO();
        tabla.setNombre(rs.getString("TABLE_NAME"));
        return tabla;
    }
}
