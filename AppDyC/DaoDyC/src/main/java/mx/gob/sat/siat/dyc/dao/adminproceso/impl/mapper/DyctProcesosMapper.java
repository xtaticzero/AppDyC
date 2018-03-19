package mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyctAdminProcesosDTO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyctProcesosDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctProcesosMapper implements RowMapper<DyctProcesosDTO>{
    
    @Override
    public DyctProcesosDTO mapRow(ResultSet rs, int i) throws SQLException{
        
        DyctProcesosDTO dyctProcesosDTO = new DyctProcesosDTO();
        
        DyctAdminProcesosDTO dyctAdminProcesosDTO = new DyctAdminProcesosDTO();
        dyctAdminProcesosDTO.setIdAdministrador(rs.getInt("IDADMINISTRADOR"));
        dyctProcesosDTO.setDyctAdminProcesosDTO(dyctAdminProcesosDTO);
        
        dyctProcesosDTO.setIdProceso(rs.getInt("IDPROCESO"));
        dyctProcesosDTO.setNombre(rs.getString("NOMBRE"));
        
        return dyctProcesosDTO;
    }
}
