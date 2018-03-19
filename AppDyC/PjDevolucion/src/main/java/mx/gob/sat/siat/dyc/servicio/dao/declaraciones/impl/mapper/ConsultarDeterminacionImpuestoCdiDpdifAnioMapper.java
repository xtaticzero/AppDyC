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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiDpdifAnioDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 26/07/2013
 *
 */
public class ConsultarDeterminacionImpuestoCdiDpdifAnioMapper implements RowMapper<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> {

    public ConsultarDeterminacionImpuestoCdiDpdifAnioMapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoCdiDpdifAnioDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoCdiDpdifAnioDTO currentDeterminacionImpuestoCdiDpdifAnioDTO = new ConsultarDeterminacionImpuestoCdiDpdifAnioDTO();
        
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setNEjercicio(resultSet.getInt("N_EJERCICIO"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setCNPeriodo(resultSet.getInt("C_N_PERIODO"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setTdiepco1(resultSet.getInt("TDIEPCO1"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setFperceh1(resultSet.getDate("FPERCEH1"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setNNumeroOperacion(resultSet.getBigDecimal("N_NUMERO_OPERACION"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setForma(resultSet.getString("FORMA"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setI111021(resultSet.getBigDecimal("I_111021"));
        currentDeterminacionImpuestoCdiDpdifAnioDTO.setI111024(resultSet.getBigDecimal("I_111024"));

        return currentDeterminacionImpuestoCdiDpdifAnioDTO;
    }
    
}