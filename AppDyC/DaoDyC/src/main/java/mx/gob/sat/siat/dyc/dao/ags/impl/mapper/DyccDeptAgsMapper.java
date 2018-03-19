package mx.gob.sat.siat.dyc.dao.ags.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccDeptAgsMapper implements RowMapper<DyccDeptAgsDTO>
{
    @Override
    public DyccDeptAgsDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyccDeptAgsDTO deptAgs = new DyccDeptAgsDTO();
        deptAgs.setDeptId(rs.getString("DEPTID"));
        deptAgs.setDescripcion(rs.getString("DESCRIPCION"));
        deptAgs.setClaveAdm(rs.getInt("CLAVEADM"));
        deptAgs.setIdTipoDept(rs.getInt("IDTIPODEPT"));
        deptAgs.setFechaInicio(rs.getDate("FECHAINICIO"));
        deptAgs.setFechaFin(rs.getDate("FECHAFIN"));
        return deptAgs;
    }
}
