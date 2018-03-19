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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317aForma13aMapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO> {

    public ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO mapRow(ResultSet resultSet,
                                                                        int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO declaracionISRPersonaFisica1317aForma13aDto =
            new ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO();

        declaracionISRPersonaFisica1317aForma13aDto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317aForma13aDto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317aForma13aDto.setFperceh1(resultSet.getDate("fperceh1"));
        declaracionISRPersonaFisica1317aForma13aDto.setTipoIngreso(resultSet.getString("tipo_ingreso"));
        declaracionISRPersonaFisica1317aForma13aDto.setRfcRetExp(resultSet.getString("rfc_ret_exp"));
        declaracionISRPersonaFisica1317aForma13aDto.setImporte1(resultSet.getInt("importe1"));
        declaracionISRPersonaFisica1317aForma13aDto.setImporte4(resultSet.getInt("importe4"));

        return declaracionISRPersonaFisica1317aForma13aDto;
    }

}
