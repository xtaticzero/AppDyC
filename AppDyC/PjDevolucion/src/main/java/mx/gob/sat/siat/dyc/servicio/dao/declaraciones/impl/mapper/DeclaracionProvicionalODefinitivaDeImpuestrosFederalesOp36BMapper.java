package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 07/05/2014
 */
public class DeclaracionProvicionalODefinitivaDeImpuestrosFederalesOp36BMapper implements RowMapper<DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO> {
    @Override
    public DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO mapRow(ResultSet rs, int i) throws SQLException {
        DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO = new DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO();
        rs.getDate("f_dec_fcieamc1");
        if(!rs.wasNull()){            
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setFDecFcieamc1(rs.getDate("f_dec_fcieamc1"));
        }
        rs.getDate("f_dec_fperceh1");
        if(!rs.wasNull()){            
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setFDecFperceh1(rs.getDate("f_dec_fperceh1"));
        }
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setCDecCtdliea1(rs.getString("c_dec_ctdliea1"));
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setCOblCcloanv1(rs.getString("c_obl_ccloanv1"));
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setDDecDrceeos1(rs.getString("d_dec_drceeos1"));
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setCDecCeflsia1(rs.getString("c_dec_ceflsia1"));
        declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO.setIDecMeosntt1(rs.getLong("i_dec_meosntt1"));
        
        return declaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO;
    }
}
