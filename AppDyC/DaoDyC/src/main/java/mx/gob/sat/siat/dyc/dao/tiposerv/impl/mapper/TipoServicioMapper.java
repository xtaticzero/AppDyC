package mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class TipoServicioMapper implements RowMapper<DyccTipoServicioDTO>{
    
    @Override
    public DyccTipoServicioDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        return BuscadorConstantes.obtenerTipoServicio(rs.getInt("IDTIPOSERVICIO"));
    }
}
