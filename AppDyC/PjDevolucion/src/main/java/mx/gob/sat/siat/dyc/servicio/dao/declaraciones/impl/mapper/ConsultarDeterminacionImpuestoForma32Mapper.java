package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma32DTO;

import org.springframework.jdbc.core.RowMapper;


/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/


/**
 * TODO
 * @author Israel Chavez
 * @since 05/08/2013
 *
 */
public class ConsultarDeterminacionImpuestoForma32Mapper implements RowMapper<ConsultarDeterminacionImpuestoForma32DTO> {

    public ConsultarDeterminacionImpuestoForma32Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma32DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma32DTO consultarDeterminacionImpuestoForma32DTO = new ConsultarDeterminacionImpuestoForma32DTO();
        
        consultarDeterminacionImpuestoForma32DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        consultarDeterminacionImpuestoForma32DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        consultarDeterminacionImpuestoForma32DTO.setTdiepco1(resultSet.getString("tdiepco1"));
        consultarDeterminacionImpuestoForma32DTO.setFperceh1(resultSet.getDate("fperceh1"));
        consultarDeterminacionImpuestoForma32DTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        consultarDeterminacionImpuestoForma32DTO.setForma(resultSet.getString("forma"));
        consultarDeterminacionImpuestoForma32DTO.setI111021(resultSet.getInt("i_111021"));
        consultarDeterminacionImpuestoForma32DTO.setI111024(resultSet.getInt("i_111024"));
        
        return consultarDeterminacionImpuestoForma32DTO;
    }
    
}
