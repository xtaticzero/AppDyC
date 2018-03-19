package mx.gob.sat.siat.dyc.admcat.dao.contribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.vo.DyccRfcSectorAgroVO;

import org.springframework.jdbc.core.RowMapper;


public class DyccRfcSectorAgroMapper implements RowMapper<DyccRfcSectorAgroVO> {

    @Override
    public DyccRfcSectorAgroVO mapRow(ResultSet rs, int i) throws SQLException {

        DyccRfcSectorAgroVO dyccRfcSectorAgroVO = new DyccRfcSectorAgroVO();

        dyccRfcSectorAgroVO.setIdContribuyente (rs.getInt("IDCONTRIBUYENTE"));
        dyccRfcSectorAgroVO.setRfc             (rs.getString("RFC"));
        dyccRfcSectorAgroVO.setNombre          (rs.getString("NOMBRE"));
        dyccRfcSectorAgroVO.setActivo          (rs.getInt("ACTIVO"));

        return dyccRfcSectorAgroVO;
    }

}
