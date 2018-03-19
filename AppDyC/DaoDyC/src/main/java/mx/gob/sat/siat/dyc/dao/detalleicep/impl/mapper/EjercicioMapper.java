package mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;

import org.springframework.jdbc.core.RowMapper;


public class EjercicioMapper implements RowMapper<DyccEjercicioDTO> {
    
    @Override
    public DyccEjercicioDTO mapRow(ResultSet rs, int i) throws SQLException{
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        
        if (null != rs.getString("IDEJERCICIO")) {
            dyccEjercicioDTO.setIdEjercicio(!rs.getString("IDEJERCICIO").equals("") ? rs.getInt("IDEJERCICIO") : 0);
        }
        if (null != rs.getString("PERMITESELECCION")) {
            /**dyccEjercicioDTO.setPermiteSeleccion(!rs.getString("PERMITESELECCION").equals("") ? rs.getInt("PERMITESELECCION") : null);*/
        }
        dyccEjercicioDTO.setFechaInicio(rs.getDate("FECHAINICIO"));
        dyccEjercicioDTO.setFechaFin(rs.getDate("FECHAFIN"));
        return dyccEjercicioDTO;
        
    }

}
