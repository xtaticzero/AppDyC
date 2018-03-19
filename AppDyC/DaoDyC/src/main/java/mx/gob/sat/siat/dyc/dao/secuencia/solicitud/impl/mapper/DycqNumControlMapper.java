package mx.gob.sat.siat.dyc.dao.secuencia.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycqNumControlMapper implements RowMapper {
    
    public DycqNumControlMapper() {
        super();
    }


    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        DycqNumControlDTO outPut = new DycqNumControlDTO();
        outPut.setSecuencia(resultSet.getString("SECUENCIA"));
        return outPut;
    }
}
