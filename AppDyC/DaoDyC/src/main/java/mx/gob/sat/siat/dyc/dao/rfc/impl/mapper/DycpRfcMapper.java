package mx.gob.sat.siat.dyc.dao.rfc.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycpRfcMapper implements RowMapper<DycpRfcDTO> {
    public DycpRfcMapper() {
        super();
    }

    @Override
    public DycpRfcDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycpRfcDTO dycpRfcDTO = new DycpRfcDTO();
        dycpRfcDTO.setRfc(rs.getString("RFC"));
        dycpRfcDTO.setNombreCompleto(rs.getString("NOMBRECOMPLETO"));
        dycpRfcDTO.setEsConfiable(rs.getInt("ESCONFIABLE"));
        dycpRfcDTO.setEsNoConfiable(rs.getInt("ESNOCONFIABLE"));
        dycpRfcDTO.setPadronConfiable(rs.getInt("PADRONCONFIABLE"));
        dycpRfcDTO.setPadronNoConfiable(rs.getInt("PADRONNOCONFIABLE"));
        return dycpRfcDTO;
    }
}
