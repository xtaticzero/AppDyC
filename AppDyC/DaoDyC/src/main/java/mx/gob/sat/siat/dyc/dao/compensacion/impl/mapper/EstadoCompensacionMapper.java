package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class EstadoCompensacionMapper implements RowMapper<DyccEstadoCompDTO>
{
    public DyccEstadoCompDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BuscadorConstantes.obtenerEstadoComp(rs.getInt("IDESTADOCOMP"));
    }
}
