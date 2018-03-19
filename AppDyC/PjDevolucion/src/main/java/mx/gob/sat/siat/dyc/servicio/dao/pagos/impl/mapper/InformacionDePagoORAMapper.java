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

import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoORADTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Israel Chavez
 * @since 09/07/2013
 *
 */
public class InformacionDePagoORAMapper implements RowMapper<InformacionDePagoORADTO> {

    public InformacionDePagoORAMapper() {
        super();
    }

    public InformacionDePagoORADTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        InformacionDePagoORADTO pagosOra = new InformacionDePagoORADTO();
        pagosOra.setFDecFcieamc1(resultSet.getDate("f_dec_fcieamc1"));
        pagosOra.setFDecFperceh1(resultSet.getDate("f_dec_fperceh1"));
        pagosOra.setIPagIcmapru1(resultSet.getInt("i_pag_icmapru1"));
        pagosOra.setIPagIfmapvu1(resultSet.getInt("i_pag_ifmapvu1"));
        pagosOra.setIPagAcpolmi1(resultSet.getInt("i_pag_acpolmi1"));
        pagosOra.setCDecCtdliea1(resultSet.getInt("c_dec_ctdliea1"));
        return pagosOra;
    }

}
