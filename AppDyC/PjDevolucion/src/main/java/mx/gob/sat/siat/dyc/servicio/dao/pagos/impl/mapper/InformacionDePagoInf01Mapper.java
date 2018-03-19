/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.pagos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf01DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Israel Chavez
 * @since 09/07/2013
 *
 */
public class InformacionDePagoInf01Mapper implements RowMapper<InformacionDePagoInf01DTO> {

    public InformacionDePagoInf01Mapper() {
        super();
    }

    public InformacionDePagoInf01DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        InformacionDePagoInf01DTO pagosInf01 = new InformacionDePagoInf01DTO();
        pagosInf01.setCStiRpeangg1((resultSet.getInt("c_sti_rpeangg1")));
        return pagosInf01;
    }

}
