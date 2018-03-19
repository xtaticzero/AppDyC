/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionmoral;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RenglonDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarCaratulaDeclaracionMoral2RenglonMapper implements RowMapper<ConsultarCaratulaDeclaracionMoral2RenglonDTO>  {
    
    public ConsultarCaratulaDeclaracionMoral2RenglonMapper() {
        super();
    }
    
    public ConsultarCaratulaDeclaracionMoral2RenglonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsultarCaratulaDeclaracionMoral2RenglonDTO consultarCaratulaDeclaracionMoral2RenglonDTO = new ConsultarCaratulaDeclaracionMoral2RenglonDTO();

        consultarCaratulaDeclaracionMoral2RenglonDTO.setCStiRpeangg1(rs.getInt("c_sti_rpeangg1"));
        
        return consultarCaratulaDeclaracionMoral2RenglonDTO;
    }
}
