package mx.gob.sat.siat.migradordyc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.migradordyc.TablaDTO;

import org.springframework.jdbc.core.RowMapper;


public class TablaColumnaMapper implements RowMapper<TablaDTO>
{
  
    @Override
    public TablaDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        //TABLE_NAME, COLUMN_NAME, DATA_LENGTH 
        TablaDTO tabla = new TablaDTO();
        tabla.setNombre(rs.getString("TABLE_NAME"));
        return tabla;
    }
}