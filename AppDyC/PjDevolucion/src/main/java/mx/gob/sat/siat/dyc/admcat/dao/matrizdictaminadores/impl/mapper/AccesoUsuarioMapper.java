package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.SegtAccesoUsuarioVO;

import org.springframework.jdbc.core.RowMapper;


public class AccesoUsuarioMapper implements RowMapper<SegtAccesoUsuarioVO> {
    public AccesoUsuarioMapper() {
        super();
    }

    @Override
    public SegtAccesoUsuarioVO mapRow(ResultSet rs, int i) throws SQLException {
        SegtAccesoUsuarioVO au = new SegtAccesoUsuarioVO();
        au.setRfc(rs.getString("RFCEMPLEADO"));
        au.setIdMotivo(rs.getInt("IDMOTIVO"));
        au.setObservaciones(rs.getString("OBSERVACIONES"));
        au.setFechaInicio(rs.getDate("FECHAINICIO"));
        au.setFechaFin(rs.getDate("FECHAFIN"));
        return au;
    }
}
