package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccMovIcepMapper implements RowMapper<DyccMovIcepDTO>{
    @Override
    public DyccMovIcepDTO mapRow(ResultSet rs, int i) throws SQLException {
        


        DyccMovIcepDTO dyccMovIcepDTO = new DyccMovIcepDTO();     
        dyccMovIcepDTO.setIdMovimiento          (rs.getInt("IDMOVIMIENTO"));
        dyccMovIcepDTO.setDescripcion           (rs.getString("DESCRIPCION"));
        dyccMovIcepDTO.setDescripcionLarga      (rs.getString("DESCRIPCIONLARGA"));
        
        DyccAfectaIcepDTO dyccAfectaIcepDTO = new DyccAfectaIcepDTO();
        dyccAfectaIcepDTO.setIdAfectacion       (rs.getInt("IDAFECTACION"));
        
        dyccMovIcepDTO.setDyccAfectaIcepDTO(dyccAfectaIcepDTO);
        
      
        
        return dyccMovIcepDTO;
    }
}
