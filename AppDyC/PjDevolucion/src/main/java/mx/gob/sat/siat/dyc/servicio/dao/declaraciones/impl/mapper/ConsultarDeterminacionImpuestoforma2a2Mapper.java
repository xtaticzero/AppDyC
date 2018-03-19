package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2a2DTO;

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
public class ConsultarDeterminacionImpuestoforma2a2Mapper implements RowMapper<ConsultarDeterminacionImpuestoForma2a2DTO> {

    public ConsultarDeterminacionImpuestoforma2a2Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma2a2DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma2a2DTO consultarDeterminacionImpuestoForma2a2DTO = new ConsultarDeterminacionImpuestoForma2a2DTO();
        
        consultarDeterminacionImpuestoForma2a2DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        consultarDeterminacionImpuestoForma2a2DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        consultarDeterminacionImpuestoForma2a2DTO.setTdiepco1(resultSet.getString("tdiepco1"));
        consultarDeterminacionImpuestoForma2a2DTO.setFperceh1(resultSet.getDate("fperceh1"));
        consultarDeterminacionImpuestoForma2a2DTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        consultarDeterminacionImpuestoForma2a2DTO.setForma(resultSet.getString("forma"));
        consultarDeterminacionImpuestoForma2a2DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        consultarDeterminacionImpuestoForma2a2DTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return consultarDeterminacionImpuestoForma2a2DTO;
    }
    
}