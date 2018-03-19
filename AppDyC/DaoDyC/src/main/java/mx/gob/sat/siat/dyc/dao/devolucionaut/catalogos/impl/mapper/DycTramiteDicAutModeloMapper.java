package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Jose Luis Aguilar
 */

public class DycTramiteDicAutModeloMapper implements RowMapper<DycTramiteDictaminacionAutomaticaDTO> {

    public DycTramiteDicAutModeloMapper() {
        super();
    }
    
    @Override
    public DycTramiteDictaminacionAutomaticaDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica = new DycTramiteDictaminacionAutomaticaDTO();
        tramiteDictaminacionAutomatica.setModelo(rs.getString("MODELO"));
        return tramiteDictaminacionAutomatica;
    }
    
}
