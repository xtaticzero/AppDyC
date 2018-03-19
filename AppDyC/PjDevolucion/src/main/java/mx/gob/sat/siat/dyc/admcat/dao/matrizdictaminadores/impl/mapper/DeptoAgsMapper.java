package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;

import org.springframework.jdbc.core.RowMapper;


public class DeptoAgsMapper implements RowMapper<DyccDeptAgsDTO> {

    @Override
    public DyccDeptAgsDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccDeptAgsDTO deptoAgs = new DyccDeptAgsDTO();
        deptoAgs.setDeptId(rs.getString("DEPTID"));
        deptoAgs.setDescripcion(rs.getString("DESCRIPCION"));
        deptoAgs.setClaveAdm(rs.getInt("CLAVEADM"));
        deptoAgs.setIdTipoDept(rs.getInt("IDTIPODEPT"));
        deptoAgs.setFechaInicio(rs.getDate("FECHAINICIO"));
        deptoAgs.setFechaFin(rs.getDate("FECHAFIN"));
        return deptoAgs;
    }
}
