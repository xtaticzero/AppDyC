package mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyctProcesosDTO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyctSegProcesoDTO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyctTareasDTO;
import mx.gob.sat.siat.dyc.vo.Procesos;

import org.springframework.jdbc.core.RowMapper;


public class ProcesosMapper implements RowMapper<Procesos> {
    
    @Override
    public Procesos mapRow(ResultSet rs, int i) throws SQLException {
        
        Procesos procesos = new Procesos();
        DyccStatusProcesoDTO dyccStatusProcesoDTO = new DyccStatusProcesoMapper().mapRow(rs, i);
        
        DyctSegProcesoDTO dyctSegProcesoDTO = new DyctSegProcesoMapper().mapRow(rs, i);
        dyctSegProcesoDTO.setDyccStatusProcesoDTO(dyccStatusProcesoDTO);
        
        DyctProcesosDTO dyctProcesosDTO = new DyctProcesosMapper().mapRow(rs, i);
        dyctSegProcesoDTO.setDyctProcesosDTO(dyctProcesosDTO);
        
        DyctTareasDTO dyctTareasDTO = new DyctTareasMapper().mapRow(rs, i);
        dyctTareasDTO.setDyctProcesosDTO(dyctProcesosDTO);
        
        procesos.setDyctSegProcesoDTO(dyctSegProcesoDTO);
        procesos.setDyctTareasDTO(dyctTareasDTO);
        
        return procesos;
    }
}
