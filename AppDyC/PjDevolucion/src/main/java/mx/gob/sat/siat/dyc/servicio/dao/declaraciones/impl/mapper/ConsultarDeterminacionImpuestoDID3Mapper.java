package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDID3DTO;

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
public class ConsultarDeterminacionImpuestoDID3Mapper implements RowMapper<ConsultarDeterminacionImpuestoDID3DTO> {

    public ConsultarDeterminacionImpuestoDID3Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoDID3DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoDID3DTO currentDeterminacionImpuestoDID3DTO = new ConsultarDeterminacionImpuestoDID3DTO();
        
        currentDeterminacionImpuestoDID3DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoDID3DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoDID3DTO.setTdiepco1(resultSet.getInt("tdiepco1"));
        currentDeterminacionImpuestoDID3DTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoDID3DTO.setNNumeroOperacion(resultSet.getBigDecimal("n_numero_operacion"));
        currentDeterminacionImpuestoDID3DTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoDID3DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoDID3DTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return currentDeterminacionImpuestoDID3DTO;
    }
    
}
