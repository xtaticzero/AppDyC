package mx.gob.sat.siat.migradordyc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.migradordyc.dto.SecuenciaDTO;

import org.springframework.jdbc.core.RowMapper;


public class SecuenciaMapper implements RowMapper<SecuenciaDTO>
{
    @Override
    public SecuenciaDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        SecuenciaDTO secuencia = new SecuenciaDTO();
        secuencia.setSEQUENCE_OWNER(rs.getString("SEQUENCE_OWNER"));
        secuencia.setSEQUENCE_NAME(rs.getString("SEQUENCE_NAME"));
        secuencia.setMIN_VALUE(rs.getInt("MIN_VALUE"));
      //  secuencia.setMAX_VALUE(rs.getLong("MAX_VALUE"));
        secuencia.setINCREMENT_BY(rs.getInt("INCREMENT_BY"));
        secuencia.setCYCLE_FLAG(rs.getString("CYCLE_FLAG").equals("Y"));
        secuencia.setORDER_FLAG(rs.getString("ORDER_FLAG").equals("Y"));
        secuencia.setCACHE_SIZE(rs.getLong("CACHE_SIZE"));
        secuencia.setLAST_NUMBER(rs.getLong("LAST_NUMBER"));
        return secuencia;
    }
    
}
