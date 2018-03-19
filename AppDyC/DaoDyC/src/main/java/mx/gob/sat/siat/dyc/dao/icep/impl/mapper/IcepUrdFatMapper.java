/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.icep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Israel Chavez
 */
public class IcepUrdFatMapper implements RowMapper<IcepUrdcFatDTO> {
    
    private Logger log = Logger.getLogger(IcepUrdFatMapper.class.getName());
    
    public IcepUrdFatMapper() {
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

        icep.setEstatus(String.valueOf(rs.getInt(ConstantesDyCNumerico.VALOR_1)));
        icep.setTipoDeclaracion(rs.getInt(ConstantesDyCNumerico.VALOR_2) != ConstantesDyCNumerico.VALOR_0 ? String.valueOf(rs.getInt(ConstantesDyCNumerico.VALOR_2)) : null);
        icep.setFechPresentacion(rs.getDate(ConstantesDyCNumerico.VALOR_3) != null ? String.valueOf(rs.getDate(ConstantesDyCNumerico.VALOR_3)) : null);
        icep.setNumOper(rs.getString(ConstantesDyCNumerico.VALOR_4) != null ? rs.getString(ConstantesDyCNumerico.VALOR_4) : null);
        icep.setSaldoFavor(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_5) != null ? String.valueOf(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_5)) : null);
        
        return icep;
    }

}
