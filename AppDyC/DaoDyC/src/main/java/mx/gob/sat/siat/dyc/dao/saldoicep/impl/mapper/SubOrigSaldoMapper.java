package mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 03, Dicimebre 2013
 */
public class SubOrigSaldoMapper implements RowMapper<DyccSubOrigSaldoDTO>{
  
    @Override
    public DyccSubOrigSaldoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccSubOrigSaldoDTO subOrigSaldo = new DyccSubOrigSaldoDTO();
        
        subOrigSaldo.setIdSuborigenSaldo(rs.getInt("IDSUBORIGENSALDO"));
        subOrigSaldo.setDescripcion(rs.getString("DESCRIPCION"));
        if (null != rs.getString("REQUIERECAPTURA")) {
            subOrigSaldo.setRequiereCaptura(!rs.getString("REQUIERECAPTURA").equals("") ? rs.getInt("REQUIERECAPTURA") : 0);
        }
        subOrigSaldo.setLeyendaCaptura(rs.getString("LEYENDACAPTURA"));
        subOrigSaldo.setFechaInicio(rs.getDate("FECHAINICIO"));
        subOrigSaldo.setFechaFin(rs.getDate("FECHAFIN"));
            
        return subOrigSaldo;
    }
}





