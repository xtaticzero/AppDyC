package mx.gob.sat.siat.dyc.dao.inconsistencia.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 11/12/2013
 */
public class AvInconsistMapper implements RowMapper<DycaAvInconsistDTO>{

    @Override
    public DycaAvInconsistDTO mapRow(ResultSet rs, int i)  throws SQLException{
        DycaAvInconsistDTO dycaAvInconsistDTO= new DycaAvInconsistDTO();
        
        DyccInconsistDTO dyccInconsistenciaDTO= new DyccInconsistDTO(); 
        dyccInconsistenciaDTO.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
       
        dycaAvInconsistDTO.setDescripcion(rs.getString("DESCRIPCION"));
        dycaAvInconsistDTO.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl(rs.getString("NUMCONTROL"));
        dycaAvInconsistDTO.getDycpAvisoCompDTO().getDycpCompensacionDTO().setDycpServicioDTO(servicio);
             
        return dycaAvInconsistDTO;
    }
}