package mx.gob.sat.siat.dyc.servicio.dao.immex.impl.mapper;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */
public class ConsultarImmexMapper implements RowMapper<ImmexDTO> {
    
    private Logger log = Logger.getLogger(ConsultarImmexMapper.class.getName());
    
    public ConsultarImmexMapper() {
        super();
    }
    
    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
    
    @Override
    public ImmexDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        ImmexDTO immexDTO = new ImmexDTO();        
        
        immexDTO.setVdInivig(null != rs.getString(ConstantesDyCNumerico.VALOR_1) ? rs.getString(ConstantesDyCNumerico.VALOR_1) : null);
        immexDTO.setVdFinvig(null != rs.getString(ConstantesDyCNumerico.VALOR_2) ? rs.getString(ConstantesDyCNumerico.VALOR_2) : null);
        immexDTO.setVdBandera(null != rs.getString(ConstantesDyCNumerico.VALOR_3) ? rs.getString(ConstantesDyCNumerico.VALOR_3) : null);
        return immexDTO;
    }

}


