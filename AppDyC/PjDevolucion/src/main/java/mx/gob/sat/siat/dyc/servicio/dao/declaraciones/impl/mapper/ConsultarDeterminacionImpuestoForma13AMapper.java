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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13ADTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
public class ConsultarDeterminacionImpuestoForma13AMapper implements RowMapper<ConsultarDeterminacionImpuestoForma13ADTO> {

    public ConsultarDeterminacionImpuestoForma13AMapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma13ADTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoForma13ADTO currentDeterminacionImpuestoForma13ADTO = new ConsultarDeterminacionImpuestoForma13ADTO();
        
        currentDeterminacionImpuestoForma13ADTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        currentDeterminacionImpuestoForma13ADTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        currentDeterminacionImpuestoForma13ADTO.setTdiepco1(resultSet.getString("tdiepco1"));
        currentDeterminacionImpuestoForma13ADTO.setFperceh1(resultSet.getDate("fperceh1"));
        currentDeterminacionImpuestoForma13ADTO.setNNumeroOperacion(resultSet.getBigDecimal("n_numero_operacion"));
        currentDeterminacionImpuestoForma13ADTO.setForma(resultSet.getString("forma"));
        currentDeterminacionImpuestoForma13ADTO.setI111021(resultSet.getBigDecimal("i_111021"));
        currentDeterminacionImpuestoForma13ADTO.setI111024(resultSet.getBigDecimal("i_111024"));

        return currentDeterminacionImpuestoForma13ADTO;
    }
    
}

