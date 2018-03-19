/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.creditosfiscales.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Israel Chavez
 * @since 23/07/2013
 *
 */
public class ConsultarDetalleCreditosFiscalesMapper implements RowMapper<ConsultarDetalleCreditosFiscalesDTO> {

    public ConsultarDetalleCreditosFiscalesMapper() {
        super();
    }

    public ConsultarDetalleCreditosFiscalesDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        ConsultarDetalleCreditosFiscalesDTO currentDetalleCreditosFiscalesDTO =
            new ConsultarDetalleCreditosFiscalesDTO();

        currentDetalleCreditosFiscalesDTO.setNcurmee1(resultSet.getInt("ncurmee1"));
        currentDetalleCreditosFiscalesDTO.setIpmapgo1(resultSet.getBigDecimal("ipmapgo1"));

        return currentDetalleCreditosFiscalesDTO;
    }

}
