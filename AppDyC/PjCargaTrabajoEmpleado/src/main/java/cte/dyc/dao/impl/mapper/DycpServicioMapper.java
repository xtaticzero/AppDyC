package cte.dyc.dao.impl.mapper;

import cte.dyc.dto.DycpServicioDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DycpServicioMapper implements RowMapper<DycpServicioDTO> {
    @Override
    public DycpServicioDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycpServicioDTO servicioL = new DycpServicioDTO();

        servicioL.setNumControl(rs.getString("NUMCONTROL"));
        servicioL.setClaveAdm(rs.getInt("CLAVEADM"));
        servicioL.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        servicioL.setIdTipoServicio(rs.getInt("IDTIPOSERVICIO"));
        servicioL.setNumEmpleadoDict(rs.getInt("NUMEMPLEADODICT"));
        servicioL.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        
        return servicioL;
    }
}
