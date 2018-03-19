package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDidDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
public class ConsultarDeterminacionImpuestoDidMapper implements RowMapper<ConsultarDeterminacionImpuestoDidDTO> {

    public ConsultarDeterminacionImpuestoDidMapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoDidDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoDidDTO currentDeterminacionImpuestoDidDTO = new ConsultarDeterminacionImpuestoDidDTO();
        
        currentDeterminacionImpuestoDidDTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoDidDTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoDidDTO.setTdiepco1(resultSet.getInt("tdiepco1"));
        currentDeterminacionImpuestoDidDTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoDidDTO.setNNumeroOperacion(resultSet.getBigDecimal("n_numero_operacion"));
        currentDeterminacionImpuestoDidDTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoDidDTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoDidDTO.setI111024(resultSet.getBigDecimal("i_111024"));

        return currentDeterminacionImpuestoDidDTO;
    }
        
}
