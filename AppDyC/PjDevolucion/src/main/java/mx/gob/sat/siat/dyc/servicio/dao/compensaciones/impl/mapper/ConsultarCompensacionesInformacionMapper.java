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

import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;

import org.springframework.jdbc.core.RowMapper;


public class ConsultarCompensacionesInformacionMapper implements RowMapper<ConsultarCompensacionesInformacionDTO>{
    
    public ConsultarCompensacionesInformacionMapper() {
        super();
    }
    
    public ConsultarCompensacionesInformacionDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarCompensacionesInformacionDTO datosCompensacionesInfoDto = new ConsultarCompensacionesInformacionDTO();
        
        datosCompensacionesInfoDto.setNcuomne1(resultSet.getString("ncuomne1"));
        datosCompensacionesInfoDto.setFperceh1(resultSet.getDate("fperceh1"));
        datosCompensacionesInfoDto.setIsmoplo1(resultSet.getBigDecimal("ismoplo1"));
        datosCompensacionesInfoDto.setNruemse1(resultSet.getString("nruemse1"));
        datosCompensacionesInfoDto.setFreecsh1(resultSet.getDate("freecsh1"));
        
        return datosCompensacionesInfoDto;
    }
}
