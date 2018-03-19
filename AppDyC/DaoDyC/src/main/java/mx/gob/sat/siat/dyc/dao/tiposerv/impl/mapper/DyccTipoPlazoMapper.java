package mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 * @since 08/12/2014
 */
public class DyccTipoPlazoMapper implements RowMapper<DyccTipoPlazoDTO> {
    
    @Override
    public DyccTipoPlazoDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyccTipoPlazoDTO objeto = new DyccTipoPlazoDTO();
        objeto.setIdTipoPlazo(rs.getInt("IDTIPOPLAZO"));
        objeto.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setFechaInicio(rs.getDate("FECHAINICIO"));
        objeto.setFechaFin(rs.getDate("FECHAFIN"));
        return objeto;
    }
}
