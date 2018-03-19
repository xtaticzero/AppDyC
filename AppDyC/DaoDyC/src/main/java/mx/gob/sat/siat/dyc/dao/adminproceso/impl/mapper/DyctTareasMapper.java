package mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyctTareasDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctTareasMapper implements RowMapper<DyctTareasDTO> {

    @Override
    public DyctTareasDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyctTareasDTO dyctTareasDTO = new DyctTareasDTO();
        
        dyctTareasDTO.setNombreJOB(rs.getString("NOMBREJOB"));
        dyctTareasDTO.setNombreTRIGGER(rs.getString("NOMBRETRIGGER"));
        dyctTareasDTO.setHorarioEjecucion(rs.getString("HORARIOEJECUCION"));
        
        return dyctTareasDTO;
    }
}
