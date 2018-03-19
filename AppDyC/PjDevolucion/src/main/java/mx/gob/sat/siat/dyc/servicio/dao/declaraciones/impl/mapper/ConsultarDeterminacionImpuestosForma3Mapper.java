package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosForma3DTO;

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
public class ConsultarDeterminacionImpuestosForma3Mapper implements RowMapper<ConsultarDeterminacionImpuestosForma3DTO> {

    public ConsultarDeterminacionImpuestosForma3Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestosForma3DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestosForma3DTO currentDeterminacionImpuestosForma3DTO = new ConsultarDeterminacionImpuestosForma3DTO();
        
        currentDeterminacionImpuestosForma3DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestosForma3DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestosForma3DTO.setTdiepco1(resultSet.getString("tdiepco1"));
        currentDeterminacionImpuestosForma3DTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestosForma3DTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        currentDeterminacionImpuestosForma3DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestosForma3DTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestosForma3DTO.setI111021(resultSet.getInt("i_111021"));
        currentDeterminacionImpuestosForma3DTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return currentDeterminacionImpuestosForma3DTO;
    }
    
}
