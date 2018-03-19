package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.registro.bean.ReglaRnfdcVO;

import org.springframework.jdbc.core.RowMapper;


public class ReglaRnfdcMapper implements RowMapper<ReglaRnfdcVO> {
    public ReglaRnfdcMapper() {
        super();
    }

    @Override
    public ReglaRnfdcVO mapRow(ResultSet rs, int i) throws SQLException {
        ReglaRnfdcVO reglaRnfdcVO = new ReglaRnfdcVO();

        reglaRnfdcVO.setNumControl(rs.getString("control"));
        reglaRnfdcVO.setEmpleadoDicta(rs.getInt("numempleadodict"));


        return reglaRnfdcVO;
    }
}
