package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;

import org.springframework.jdbc.core.RowMapper;


public class InfoARequerirMapper implements RowMapper<DyccInfoARequerirDTO>
{
    public DyccInfoARequerirDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyccInfoARequerirDTO infoARequerir = new DyccInfoARequerirDTO();
        infoARequerir.setIdInfoArequerir(rs.getInt("IDINFOAREQUERIR"));
        infoARequerir.setDescripcion(rs.getString("DESCRIPCION"));
        infoARequerir.setFechaInicio(rs.getDate("FECHAINICIO"));
        Date fechaFin = rs.getDate("FECHAFIN");
        if(fechaFin != null){
            infoARequerir.setFechaFin(fechaFin);
        }
        return infoARequerir;
    }            
}