package mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyctSegProcesoDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctSegProcesoMapper implements RowMapper<DyctSegProcesoDTO> {

    @Override
    public DyctSegProcesoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctSegProcesoDTO dyctSegProcesoDTO = new DyctSegProcesoDTO();
        
        dyctSegProcesoDTO.setIntentos(rs.getInt("INTENTOS"));
        dyctSegProcesoDTO.setNumMaxIntentos(rs.getInt("NUMMAXINTENTOS"));
        dyctSegProcesoDTO.setPrioridad(rs.getInt("PRIORIDAD"));
        dyctSegProcesoDTO.setHoraEjecucion(rs.getTimestamp("HORAEJECUCION"));
        dyctSegProcesoDTO.setEjecucion(rs.getInt("EJECUCION"));
        
        return dyctSegProcesoDTO;
    }
}
