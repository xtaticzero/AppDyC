package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author David Guerrero Reyes
 */
public class IdDocConsecutivoMapper implements RowMapper {
    public IdDocConsecutivoMapper() {
        super();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) {
        Integer idDocumento = null;
        try {
            idDocumento = resultSet.getInt("IDDOCUMENTO");
        } catch (SQLException e) {
            e.getMessage();
        }
        if (null != idDocumento) {
            return idDocumento;
        }
        return null;
    }
}

