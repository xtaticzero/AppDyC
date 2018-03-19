package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccAfectaIcepMapper  implements RowMapper<DyccAfectaIcepDTO> {
    public DyccAfectaIcepMapper() {
        super();
    }
    
    public DyccAfectaIcepDTO mapRow(ResultSet rs, int i) throws SQLException {

        
        DyccAfectaIcepDTO dyccAfectaIcepDTO = new DyccAfectaIcepDTO();
 
        dyccAfectaIcepDTO.setIdAfectacion (rs.getInt("IDAFECTACION"));
        dyccAfectaIcepDTO.setDescripcion  (rs.getString("AFECTACION"));

        return dyccAfectaIcepDTO;
    }

}
