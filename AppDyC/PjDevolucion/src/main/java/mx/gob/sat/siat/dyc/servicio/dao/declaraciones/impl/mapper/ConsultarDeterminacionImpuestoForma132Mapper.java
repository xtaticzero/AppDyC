package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma132DTO;

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
public class ConsultarDeterminacionImpuestoForma132Mapper implements RowMapper<ConsultarDeterminacionImpuestoForma132DTO> {

    public ConsultarDeterminacionImpuestoForma132Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma132DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma132DTO currentDeterminacionImpuestoForma132DTO = new ConsultarDeterminacionImpuestoForma132DTO();
        
        currentDeterminacionImpuestoForma132DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoForma132DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma132DTO.setTdiepco1(resultSet.getInt("tdiepco1"));
        currentDeterminacionImpuestoForma132DTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoForma132DTO.setNNumeroOperacion(resultSet.getBigDecimal("n_numero_operacion"));
        currentDeterminacionImpuestoForma132DTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoForma132DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoForma132DTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return currentDeterminacionImpuestoForma132DTO;
    }
    
}

