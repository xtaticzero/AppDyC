package mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstResolDTO;

import org.springframework.jdbc.core.RowMapper;


public class EstatusResolucionMapper implements RowMapper<DyccEstResolDTO>
{
    public DyccEstResolDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyccEstResolDTO estatusResolucion = new DyccEstResolDTO();
        estatusResolucion.setIdEstResol(rs.getInt("IDESTRESOL"));
        estatusResolucion.setDescripcion(rs.getString("DESCRIPCION"));
        return estatusResolucion;
    }        
}