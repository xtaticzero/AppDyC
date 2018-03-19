/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author ISCC
 */
public class ConsultarCaratulaDeclaracionSociedadControladora2ARenglonMapper implements RowMapper<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO>  {
    
    public ConsultarCaratulaDeclaracionSociedadControladora2ARenglonMapper() {
        super();
    }
    public ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO datosCaratulaDeclaracionSociedadControladora2RenglonDTO = new ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO();
        
        datosCaratulaDeclaracionSociedadControladora2RenglonDTO.setCStiRpeangg1(resultado.getInt("c_sti_rpeangg1"));


        return datosCaratulaDeclaracionSociedadControladora2RenglonDTO;
    }
}
