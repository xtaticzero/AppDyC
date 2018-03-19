/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIEPS4DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarDeclaracionIEPS4Mapper implements RowMapper<ConsultarDeclaracionIEPS4DTO>  {
    
    public ConsultarDeclaracionIEPS4Mapper() {
        super();
    }
    
    public ConsultarDeclaracionIEPS4DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarDeclaracionIEPS4DTO
                currentDeclaracionIEPS4DTO = new ConsultarDeclaracionIEPS4DTO();

        currentDeclaracionIEPS4DTO.setLdleacv1(resultado.getString("ldleacv1"));
        currentDeclaracionIEPS4DTO.setVP2141906(resultado.getBigDecimal("v_p2_141906"));
        currentDeclaracionIEPS4DTO.setVP2141907(resultado.getBigDecimal("v_p2_141907"));
        currentDeclaracionIEPS4DTO.setVP2141908(resultado.getBigDecimal("v_p2_141908"));
        currentDeclaracionIEPS4DTO.setVP2141917(resultado.getBigDecimal("v_p2_141917"));
        currentDeclaracionIEPS4DTO.setVP2141918(resultado.getBigDecimal("v_p2_141918"));
        currentDeclaracionIEPS4DTO.setVP2141910(resultado.getBigDecimal("v_p2_141910"));
        currentDeclaracionIEPS4DTO.setVP2141911(resultado.getBigDecimal("v_p2_141911"));
        currentDeclaracionIEPS4DTO.setVP2141521(resultado.getBigDecimal("v_p2_141521"));
        currentDeclaracionIEPS4DTO.setVP2141912(resultado.getBigDecimal("v_p2_141912"));
        currentDeclaracionIEPS4DTO.setVP2141919(resultado.getBigDecimal("v_p2_141919"));
        currentDeclaracionIEPS4DTO.setVP2141920(resultado.getBigDecimal("v_p2_141920"));
        currentDeclaracionIEPS4DTO.setVP2141914(resultado.getBigDecimal("v_p2_141914"));
        currentDeclaracionIEPS4DTO.setVP2141915(resultado.getBigDecimal("v_p2_141915"));
        currentDeclaracionIEPS4DTO.setVP2141916(resultado.getBigDecimal("v_p2_141916"));
        
        return currentDeclaracionIEPS4DTO;
    }
}

