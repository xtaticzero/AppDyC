package mx.gob.sat.siat.dyc.dao.inconsistencia.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 11/12/2013
 */
public class InconsistenciaMapper implements RowMapper<DyccInconsistDTO> {

    @Override
    public DyccInconsistDTO mapRow(ResultSet rs, int i) throws SQLException{
        DyccInconsistDTO dyccInconsistencia = new DyccInconsistDTO();
        dyccInconsistencia.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
        dyccInconsistencia.setDescripcion(rs.getString("DESCRIPCION"));
        dyccInconsistencia.setFechaInicio(rs.getDate("FECHAINICIO"));
        Date fechaFin = rs.getDate("FECHAFIN");
        if(fechaFin != null){
            dyccInconsistencia.setFechaFin(fechaFin);
        }
        
        return dyccInconsistencia;
    }
}
