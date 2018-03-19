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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonMapper implements RowMapper<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO>  {
    
    public ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonMapper() {
        super();
    }
    
    public ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO  datosCaratulaDeclaracionSociedadIntegradora3RenglonDTO = new ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO();

        datosCaratulaDeclaracionSociedadIntegradora3RenglonDTO.setCStiRpeangg1(resultado.getInt("c_sti_rpeangg1"));
        
        return datosCaratulaDeclaracionSociedadIntegradora3RenglonDTO;
    }
}
