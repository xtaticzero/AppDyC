package mx.gob.sat.siat.dyc.dao.devolucionaut.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctWsAutomaticaDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Mario Lizaola Ruiz
 */
public class DyctWsAutomaticaMapper implements RowMapper<DyctWsAutomaticaDTO>{

    @Override
    public DyctWsAutomaticaDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctWsAutomaticaDTO wsAutomatica = new DyctWsAutomaticaDTO();
        wsAutomatica.setIdWsAutomatica(rs.getInt("IDWSAUTOMATICA"));
        wsAutomatica.setIdOperacion(rs.getInt("IDOPERACION"));
        wsAutomatica.setFechaRegistro(rs.getTimestamp("FECHAREGISTRO"));
        wsAutomatica.setXmlRequest(rs.getString("REQUEST"));
        wsAutomatica.setXmlResponse(rs.getString("RESPONSE"));
        wsAutomatica.setMensaje(rs.getString("MENSAJE"));
        return wsAutomatica;
    }
    
}
