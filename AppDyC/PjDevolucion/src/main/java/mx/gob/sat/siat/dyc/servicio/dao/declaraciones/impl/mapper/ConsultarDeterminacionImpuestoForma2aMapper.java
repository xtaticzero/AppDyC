package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2aDTO;

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
 * @since 02/08/2013
 *
 */
public class ConsultarDeterminacionImpuestoForma2aMapper implements RowMapper<ConsultarDeterminacionImpuestoForma2aDTO> {

    public ConsultarDeterminacionImpuestoForma2aMapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma2aDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma2aDTO currentDeterminacionImpuestoForma2aDTO = new ConsultarDeterminacionImpuestoForma2aDTO();
        
        currentDeterminacionImpuestoForma2aDTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoForma2aDTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma2aDTO.setTdiepco1(resultSet.getString("tdiepco1"));
        currentDeterminacionImpuestoForma2aDTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoForma2aDTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        currentDeterminacionImpuestoForma2aDTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma2aDTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoForma2aDTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoForma2aDTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return currentDeterminacionImpuestoForma2aDTO;
    }
    
}