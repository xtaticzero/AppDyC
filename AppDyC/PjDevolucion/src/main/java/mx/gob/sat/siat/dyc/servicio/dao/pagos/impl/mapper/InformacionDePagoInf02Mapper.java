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

import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf02DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Israel Chavez
 * @since 09/07/2013
 *
 */
public class InformacionDePagoInf02Mapper implements RowMapper<InformacionDePagoInf02DTO> {

    public InformacionDePagoInf02Mapper() {
        super();
    }

    public InformacionDePagoInf02DTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        InformacionDePagoInf02DTO pagosInf02 = new InformacionDePagoInf02DTO();
        pagosInf02.setNDecImpidee1(resultSet.getInt("n_dec_impidee1"));
        pagosInf02.setNDecImpfdee1(resultSet.getInt("n_dec_impfdee1"));
        return pagosInf02;
    }

}
