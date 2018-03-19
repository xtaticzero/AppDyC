/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.compensaciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chàvez
 */
public class ConsultarCompensacionesFechasMapper implements RowMapper<ConsultarCompensacionesFechasDTO>  {
    
    public ConsultarCompensacionesFechasMapper() {
        super();
    }
    
    public ConsultarCompensacionesFechasDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarCompensacionesFechasDTO datosCompensacionesFechasDto = new ConsultarCompensacionesFechasDTO();
        
        datosCompensacionesFechasDto.setNDecImpidee1(resultado.getInt("n_dec_impidee1"));
        datosCompensacionesFechasDto.setNDecImpfdee1(resultado.getInt("n_dec_impfdee1"));

        return datosCompensacionesFechasDto;
    }

}
