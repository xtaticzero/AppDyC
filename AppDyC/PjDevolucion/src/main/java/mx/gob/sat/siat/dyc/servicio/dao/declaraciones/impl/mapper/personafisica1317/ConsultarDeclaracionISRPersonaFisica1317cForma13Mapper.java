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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317cForma13Mapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO> {

    public ConsultarDeclaracionISRPersonaFisica1317cForma13DTO mapRow(ResultSet resultSet,
                                                                       int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317cForma13DTO declaracionISRPersonaFisica1317aDecDto =
            new ConsultarDeclaracionISRPersonaFisica1317cForma13DTO();

        declaracionISRPersonaFisica1317aDecDto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317aDecDto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317aDecDto.setFperceh1(resultSet.getDate("fperceh1"));
        declaracionISRPersonaFisica1317aDecDto.setTipoIngreso(resultSet.getString("tipo_ingreso"));
        declaracionISRPersonaFisica1317aDecDto.setRfcRetExp(resultSet.getString("rfc_ret_exp"));
        declaracionISRPersonaFisica1317aDecDto.setImporte1(resultSet.getBigDecimal("importe1"));
        declaracionISRPersonaFisica1317aDecDto.setImporte4(resultSet.getBigDecimal("importe4"));

        return declaracionISRPersonaFisica1317aDecDto;
    }

}
