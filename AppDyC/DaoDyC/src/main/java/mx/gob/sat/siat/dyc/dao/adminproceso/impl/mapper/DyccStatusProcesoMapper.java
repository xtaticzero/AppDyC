package mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccStatusProcesoMapper implements RowMapper<DyccStatusProcesoDTO> {

    @Override
    public DyccStatusProcesoDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyccStatusProcesoDTO dyccStatusProcesoDTO = new DyccStatusProcesoDTO();
        dyccStatusProcesoDTO.setIdStatusProceso(rs.getInt("IDSTATUSPROCESO"));
        dyccStatusProcesoDTO.setNombre(rs.getString("NOMBRESTATUS"));
        dyccStatusProcesoDTO.setFechaInicio(rs.getDate("FECHAINICIO"));
        dyccStatusProcesoDTO.setFechaFin(rs.getDate("FECHAFIN"));
        
        return dyccStatusProcesoDTO;
    }
}
