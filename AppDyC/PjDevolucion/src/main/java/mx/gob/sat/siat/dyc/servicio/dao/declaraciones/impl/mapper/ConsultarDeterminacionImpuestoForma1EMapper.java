package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma1EDTO;

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
public class ConsultarDeterminacionImpuestoForma1EMapper implements RowMapper<ConsultarDeterminacionImpuestoForma1EDTO> {

    public ConsultarDeterminacionImpuestoForma1EMapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma1EDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma1EDTO currentDeterminacionImpuestoForma1EDTO = new ConsultarDeterminacionImpuestoForma1EDTO();
        
        currentDeterminacionImpuestoForma1EDTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoForma1EDTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma1EDTO.setTdiepco1(resultSet.getString("tdiepco1"));
        currentDeterminacionImpuestoForma1EDTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoForma1EDTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        currentDeterminacionImpuestoForma1EDTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoForma1EDTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoForma1EDTO.setI111024(resultSet.getBigDecimal("i_111024"));
        
        return currentDeterminacionImpuestoForma1EDTO;
    }
    
}
