package mx.gob.sat.siat.dyc.registro.util.validador.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class TramiteMapper implements RowMapper<Integer> {


    public TramiteMapper() {
        super();
    }

    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("IDTIPOTRAMITE");
    }
}
