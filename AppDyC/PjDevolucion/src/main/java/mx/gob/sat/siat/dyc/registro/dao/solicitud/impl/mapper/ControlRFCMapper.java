package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ControlRFCMapper implements RowMapper<String> {
    public ControlRFCMapper() {
        super();
    }

    @Override
    public String mapRow(ResultSet rs, int i) throws SQLException {
        String tramite = null;
        tramite = rs.getString("RFC");
        return tramite;
    }
}
