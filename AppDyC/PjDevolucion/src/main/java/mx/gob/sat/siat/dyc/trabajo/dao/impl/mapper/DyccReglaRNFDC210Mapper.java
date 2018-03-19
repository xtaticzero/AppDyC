package mx.gob.sat.siat.dyc.trabajo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.DyccReglaRNFDC210VO;

import org.springframework.jdbc.core.RowMapper;


public class DyccReglaRNFDC210Mapper implements RowMapper<DyccReglaRNFDC210VO> {
    @Override
    public DyccReglaRNFDC210VO mapRow(ResultSet resultSet, int i) throws SQLException{
    
            DyccReglaRNFDC210VO reglaRNFDC210 = new DyccReglaRNFDC210VO();
        
            reglaRNFDC210.setNumEmpleado(resultSet.getInt("NUMEMPLEADO")); 
            reglaRNFDC210.setNombreCompleto(resultSet.getString("NOMBRECOMPLETO"));
            reglaRNFDC210.setCveNivel(resultSet.getInt("CLAVENIVEL")); 
            
            return reglaRNFDC210;
        }
}