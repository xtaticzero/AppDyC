package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosDID2DTO;

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
public class ConsultarDeterminacionImpuestosDID2Mapper implements RowMapper<ConsultarDeterminacionImpuestosDID2DTO> {

    public ConsultarDeterminacionImpuestosDID2Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestosDID2DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestosDID2DTO currentDeterminacionImpuestosDID2DTO = new ConsultarDeterminacionImpuestosDID2DTO();
        
        currentDeterminacionImpuestosDID2DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestosDID2DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestosDID2DTO.setTdiepco1(resultSet.getInt("tdiepco1"));
        currentDeterminacionImpuestosDID2DTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestosDID2DTO.setNNumeroOperacion(resultSet.getBigDecimal("n_numero_operacion"));
        currentDeterminacionImpuestosDID2DTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestosDID2DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestosDID2DTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return currentDeterminacionImpuestosDID2DTO;
    }
    
}
