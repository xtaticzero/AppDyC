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

import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Israel Chavez
 */
public class IcepSpSioUrucple1Mapper implements RowMapper<IcepSioUrucple1DTO> {
    
    private Logger log = Logger.getLogger(IcepSpSioUrucple1Mapper.class.getName());
    
    public IcepSpSioUrucple1Mapper() {
        super();
    }
    
    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
    
    @Override
    public IcepSioUrucple1DTO mapRow(ResultSet rs, int i) throws SQLException {
        
        IcepSioUrucple1DTO icep = new IcepSioUrucple1DTO();

        icep.setEstatus(String.valueOf(rs.getInt(ConstantesDyCNumerico.VALOR_1)));
        icep.setTipoDeclaracion(String.valueOf(rs.getInt(ConstantesDyCNumerico.VALOR_2)));
        icep.setFechPresentacion(String.valueOf(rs.getDate(ConstantesDyCNumerico.VALOR_3)));
        icep.setNumOper(String.valueOf(rs.getString(ConstantesDyCNumerico.VALOR_4)));
        icep.setSaldoFavor(String.valueOf(rs.getBigDecimal(ConstantesDyCNumerico.VALOR_5)));
        
        return icep;
    }

}
