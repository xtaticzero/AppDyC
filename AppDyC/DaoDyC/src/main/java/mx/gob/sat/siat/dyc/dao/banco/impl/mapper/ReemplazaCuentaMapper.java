package mx.gob.sat.siat.dyc.dao.banco.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;

import org.springframework.jdbc.core.RowMapper;


public class ReemplazaCuentaMapper implements RowMapper<RemplazaCuentaClabeVO> {
    public ReemplazaCuentaMapper() {
        super();
    }

    @Override
    public RemplazaCuentaClabeVO mapRow(ResultSet rs, int i) throws SQLException {
        RemplazaCuentaClabeVO cuentaClabeVO = new RemplazaCuentaClabeVO();
        cuentaClabeVO.setBanco(rs.getString("descripcion"));
        cuentaClabeVO.setCuenta(rs.getString("clabe"));
        cuentaClabeVO.setFechaRegistro(rs.getDate("fecharegistro"));
        cuentaClabeVO.setFechaUltima(rs.getDate("fechaultima"));
        cuentaClabeVO.setCuentaValida(rs.getInt("valida"));       
        cuentaClabeVO.setIdArchivo(rs.getInt("idarchivo"));
        return cuentaClabeVO;
    }
}
