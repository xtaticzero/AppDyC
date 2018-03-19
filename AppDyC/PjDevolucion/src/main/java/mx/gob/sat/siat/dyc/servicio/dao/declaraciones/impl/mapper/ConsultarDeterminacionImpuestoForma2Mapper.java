package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2DTO;

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
 * @since 29/07/2013
 *
 */
public class ConsultarDeterminacionImpuestoForma2Mapper implements RowMapper<ConsultarDeterminacionImpuestoForma2DTO> {

    public ConsultarDeterminacionImpuestoForma2Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma2DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma2DTO currentDeterminacionImpuestoForma2DTO = new ConsultarDeterminacionImpuestoForma2DTO();
        
        currentDeterminacionImpuestoForma2DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoForma2DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma2DTO.setTdiepco1(resultSet.getString("tdiepco1"));
        currentDeterminacionImpuestoForma2DTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoForma2DTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        currentDeterminacionImpuestoForma2DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma2DTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoForma2DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoForma2DTO.setI111024(resultSet.getBigDecimal("i_111024"));
        return currentDeterminacionImpuestoForma2DTO;
    }
    
}
