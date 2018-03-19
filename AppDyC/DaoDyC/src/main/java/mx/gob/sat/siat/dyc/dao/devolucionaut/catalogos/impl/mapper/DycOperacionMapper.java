package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycOperacionDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Mario Lizaola Ruiz
 */
public class DycOperacionMapper implements RowMapper<DycOperacionDTO>{

    public DycOperacionMapper() {
        super();
    }
    
    @Override
    public DycOperacionDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycOperacionDTO operacion = new DycOperacionDTO();
        operacion.setIdOperacion(rs.getInt("IDOPERACION"));
        operacion.setDescripcion(rs.getString("DESCRIPCION"));
        operacion.setFechaFin(rs.getDate("FECHAFIN"));
        return operacion;
    }
    
}
