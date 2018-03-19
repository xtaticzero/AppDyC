package mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;

import org.springframework.jdbc.core.RowMapper;


public class ActividadEconomicaMapper implements RowMapper<DyccActividadDTO> {
    public ActividadEconomicaMapper() {
        super();
    }

    @Override
    public DyccActividadDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccActividadDTO actividad = new DyccActividadDTO();
        actividad.setIdActividad(rs.getInt("IDACTIVIDAD"));
        actividad.setDescripcion(rs.getString("DESCRIPCION"));
        return actividad;
    }
}
