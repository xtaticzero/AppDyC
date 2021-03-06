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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
public class ConsultarDeterminacionImpuestoForma13Mapper implements RowMapper<ConsultarDeterminacionImpuestoForma13DTO> {

    public ConsultarDeterminacionImpuestoForma13Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma13DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma13DTO currentDeterminacionImpuestoForma13DTO = new ConsultarDeterminacionImpuestoForma13DTO();
        
        currentDeterminacionImpuestoForma13DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoForma13DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma13DTO.setTdiepco1(resultSet.getInt("tdiepco1"));
        currentDeterminacionImpuestoForma13DTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoForma13DTO.setNNumeroOperacion(resultSet.getBigDecimal("n_numero_operacion"));
        currentDeterminacionImpuestoForma13DTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoForma13DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoForma13DTO.setI111024(resultSet.getBigDecimal("i_111024"));

        return currentDeterminacionImpuestoForma13DTO;
    }
    
}
