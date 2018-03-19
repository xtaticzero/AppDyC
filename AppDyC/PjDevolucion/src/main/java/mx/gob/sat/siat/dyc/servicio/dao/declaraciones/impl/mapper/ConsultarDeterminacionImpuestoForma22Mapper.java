package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma22DTO;

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
public class ConsultarDeterminacionImpuestoForma22Mapper implements RowMapper<ConsultarDeterminacionImpuestoForma22DTO> {

    public ConsultarDeterminacionImpuestoForma22Mapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoForma22DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        ConsultarDeterminacionImpuestoForma22DTO consultarDeterminacionImpuestoForma22DTO =
            new ConsultarDeterminacionImpuestoForma22DTO();

        consultarDeterminacionImpuestoForma22DTO.setNEjercicio(resultSet.getInt("n_ejercicio"));
        consultarDeterminacionImpuestoForma22DTO.setCNPeriodo(resultSet.getInt("c_n_periodo"));
        consultarDeterminacionImpuestoForma22DTO.setTdiepco1(resultSet.getString("tdiepco1"));
        consultarDeterminacionImpuestoForma22DTO.setFperceh1(resultSet.getDate("fperceh1"));
        consultarDeterminacionImpuestoForma22DTO.setNNumeroOperacion(resultSet.getString("n_numero_operacion"));
        consultarDeterminacionImpuestoForma22DTO.setForma(resultSet.getString("forma"));
        consultarDeterminacionImpuestoForma22DTO.setI111021(resultSet.getBigDecimal("i_111021"));
        consultarDeterminacionImpuestoForma22DTO.setI111024(resultSet.getBigDecimal("i_111024"));

        return consultarDeterminacionImpuestoForma22DTO;
    }

}
