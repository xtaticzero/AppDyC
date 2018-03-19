/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2Mapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO> {

    public ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO mapRow(ResultSet resultSet,
                                                                      int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO declaracionISRPersonaFisica1317aDecDto =
            new ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO();

        declaracionISRPersonaFisica1317aDecDto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317aDecDto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317aDecDto.setFFperceh1(resultSet.getDate("fperceh1"));
        declaracionISRPersonaFisica1317aDecDto.setNC180218(resultSet.getInt("n_c1_80218"));
        declaracionISRPersonaFisica1317aDecDto.setIC105223(resultSet.getString("i_c1_05223"));
        declaracionISRPersonaFisica1317aDecDto.setDC105736(resultSet.getString("d_c1_05736"));
        declaracionISRPersonaFisica1317aDecDto.setDC105246(resultSet.getBigDecimal("d_c1_05246"));
        declaracionISRPersonaFisica1317aDecDto.setIC13109205(resultSet.getBigDecimal("i_c1_3109205"));
        declaracionISRPersonaFisica1317aDecDto.setIC13109305(resultSet.getBigDecimal("i_c1_3109305"));
        declaracionISRPersonaFisica1317aDecDto.setIC13109405(resultSet.getBigDecimal("i_c1_3109405"));
        
        return declaracionISRPersonaFisica1317aDecDto;
    }

}
