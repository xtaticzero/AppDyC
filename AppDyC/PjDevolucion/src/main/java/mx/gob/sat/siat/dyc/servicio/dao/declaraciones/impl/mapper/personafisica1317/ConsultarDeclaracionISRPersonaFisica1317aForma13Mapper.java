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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317aForma13Mapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO> {

    public ConsultarDeclaracionISRPersonaFisica1317aForma13DTO mapRow(ResultSet resultSet,
                                                                       int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317aForma13DTO declaracionISRPersonaFisica1317aForma13Dto =
            new ConsultarDeclaracionISRPersonaFisica1317aForma13DTO();

        declaracionISRPersonaFisica1317aForma13Dto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317aForma13Dto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317aForma13Dto.setFperceh1(resultSet.getDate("fperceh1"));
        declaracionISRPersonaFisica1317aForma13Dto.setTipoIngreso(resultSet.getString("tipo_ingreso"));
        declaracionISRPersonaFisica1317aForma13Dto.setRfcRetExp(resultSet.getString("rfc_ret_exp"));
        declaracionISRPersonaFisica1317aForma13Dto.setImporte1(resultSet.getBigDecimal("importe1"));
        declaracionISRPersonaFisica1317aForma13Dto.setImporte4(resultSet.getBigDecimal("importe4"));

        return declaracionISRPersonaFisica1317aForma13Dto;
    }

}
