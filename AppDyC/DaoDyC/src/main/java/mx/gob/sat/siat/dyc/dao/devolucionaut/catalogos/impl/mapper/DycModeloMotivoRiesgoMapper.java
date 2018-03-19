package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Jose Luis Aguilar
 */
public class DycModeloMotivoRiesgoMapper implements RowMapper<DycMotivoRiesgoDTO> {

    public DycModeloMotivoRiesgoMapper() {
        super();
    }
    
    @Override
    public DycMotivoRiesgoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycMotivoRiesgoDTO motivoRiesgo = new DycMotivoRiesgoDTO();
        motivoRiesgo.setModelo(rs.getString("MODELO"));
        return motivoRiesgo;
    }
    
}