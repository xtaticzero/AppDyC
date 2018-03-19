package mx.gob.sat.siat.dyc.dao.accion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccAccionSegDTO;

import org.springframework.jdbc.core.RowMapper;


public class AccionSegMapper implements RowMapper<DyccAccionSegDTO>
{
    public DyccAccionSegDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {   
        DyccAccionSegDTO accionSeg = new DyccAccionSegDTO();
        accionSeg.setIdAccionSeg(rs.getInt("IDACCIONSEG"));
        accionSeg.setDescripcion(rs.getString("DESCRIPCION"));
        return accionSeg;
    }
}