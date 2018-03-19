package mx.gob.sat.siat.dyc.dao.declaraciontemp.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctSolicitudTempMapper implements RowMapper<DyctSolicitudTempDTO> {
    public DyctSolicitudTempMapper() {
        super();
    }

    @Override
    public DyctSolicitudTempDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctSolicitudTempDTO dyctSolicitudTempDTO = new DyctSolicitudTempDTO();
        dyctSolicitudTempDTO.setClaveAdm(rs.getInt("ADMINISTRACION"));
        dyctSolicitudTempDTO.setRfcContribuyente(rs.getString("RFC"));
        dyctSolicitudTempDTO.setClabeBancaria(rs.getString("URL"));
        dyctSolicitudTempDTO.setFechaPresentacion(rs.getDate("FECHA"));
        return dyctSolicitudTempDTO;
    }
}
