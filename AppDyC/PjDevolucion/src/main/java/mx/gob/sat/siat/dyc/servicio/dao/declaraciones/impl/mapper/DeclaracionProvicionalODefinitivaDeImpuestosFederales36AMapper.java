package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO;

import org.springframework.jdbc.core.RowMapper;


public class DeclaracionProvicionalODefinitivaDeImpuestosFederales36AMapper implements RowMapper<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO> {
    @Override
    public DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO mapRow(ResultSet rs, int i) throws SQLException {
        DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO declaracionProvicionalODefinitivaDeImpuestosFederales36AVO = new DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO();
        
        
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setFDecFcieamc1(rs.getDate("f_dec_fcieamc1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setFDecFperceh1(rs.getDate("f_dec_fperceh1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setCDecCtdliea1(rs.getString("c_dec_ctdliea1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setCOblCcloanv1(rs.getString("c_obl_ccloanv1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setDDecDrceeos1(rs.getString("d_dec_drceeos1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setDOblDpeesrc1(rs.getString("d_obl_dpeesrc1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setCOblCcloanv2(rs.getString("c_obl_ccloanv2"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setIDecIsqamau1(rs.getBigDecimal("i_dec_isqamau1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setIDecIosfmra1(rs.getLong("i_dec_iosfmra1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setFDecFddmsfe1(rs.getDate("f_dec_fddmsfe1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setIDecRvhaaea1(rs.getLong("i_dec_rvhaaea1"));
        declaracionProvicionalODefinitivaDeImpuestosFederales36AVO.setIDecRvaaaea1(rs.getLong("i_dec_rvaaaea1"));        
        
        return declaracionProvicionalODefinitivaDeImpuestosFederales36AVO;
    }
    
}
