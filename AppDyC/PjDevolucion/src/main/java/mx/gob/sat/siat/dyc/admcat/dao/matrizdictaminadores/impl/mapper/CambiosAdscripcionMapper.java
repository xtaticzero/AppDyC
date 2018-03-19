package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.SegtCambioAdscripcionVO;

import org.springframework.jdbc.core.RowMapper;


public class CambiosAdscripcionMapper implements RowMapper<SegtCambioAdscripcionVO> {

    @Override
    public SegtCambioAdscripcionVO mapRow(ResultSet rs, int i) throws SQLException {
        SegtCambioAdscripcionVO ca = new SegtCambioAdscripcionVO();
        ca.setRfc(rs.getString("RFCEMPLEADO"));
        ca.setCcosto(rs.getInt("CCOSTO"));
        ca.setDepid(rs.getString("DEPID"));
        ca.setCcostoop(rs.getInt("CCOSTOOP"));
        ca.setDepidop(rs.getString("DEPIDOP"));
        ca.setObservaciones(rs.getString("OBSERVACIONES"));
        ca.setFechaInicio(rs.getDate("FECHAINICIO"));
        ca.setFechaFin(rs.getDate("FECHAFIN"));
        return ca;
    }
}
