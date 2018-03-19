package mx.gob.sat.siat.dyc.dao.icep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class IcepUrdFatDb2Mapper  implements RowMapper<IcepUrdcFatDTO>{
   
   
    private Logger log = Logger.getLogger(IcepUrdFatDb2Mapper.class.getName());
    
    public IcepUrdFatDb2Mapper() {
        super();
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
    
    @Override
    public IcepUrdcFatDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        IcepUrdcFatDTO icep = new IcepUrdcFatDTO();
        icep.setTipoDeclaracion(rs.getString(ConstantesDyCNumerico.VALOR_1) != null? String.valueOf(rs.getString(ConstantesDyCNumerico.VALOR_1)) : null);
        try{
            icep.setFechPresentacion(rs.getDate(ConstantesDyCNumerico.VALOR_2) != null ? String.valueOf(rs.getDate(ConstantesDyCNumerico.VALOR_2)) : null);
        }catch (SQLException se){
            icep.setFechPresentacion(rs.getString(ConstantesDyCNumerico.VALOR_2) != null ? rs.getString(ConstantesDyCNumerico.VALOR_2) : null);
        }
        icep.setNumOper(rs.getString(ConstantesDyCNumerico.VALOR_3) != null ? rs.getString(ConstantesDyCNumerico.VALOR_3) : null);
        icep.setSaldoFavor(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_4) != null ? String.valueOf(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_4)) : null);
        
        return icep;
    }
}
