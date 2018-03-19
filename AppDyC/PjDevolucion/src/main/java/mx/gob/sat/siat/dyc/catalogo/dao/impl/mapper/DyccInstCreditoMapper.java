/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO catalogo DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
public class DyccInstCreditoMapper implements RowMapper<DyccInstCreditoDTO> {
    public DyccInstCreditoMapper() {
        super();
    }

    @Override
    public DyccInstCreditoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccInstCreditoDTO insCredito = new DyccInstCreditoDTO();

        insCredito.setIdInstCredito(rs.getInt("IDINSTCREDITO"));
        insCredito.setDescripcion(rs.getString("DESCRIPCION"));
        insCredito.setOrdenSec(rs.getInt("ORDENSEC"));
        insCredito.setFechaInicio(rs.getDate("FECHAINICIO"));
        insCredito.setFechaFin(rs.getDate("FECHAFIN"));
        /**insCredito.setDyctCuentabancoList(Collections.singletonList(new DyctCuentaBancoDTO(rs.getString("CLABE"), null,
                                                                                           null, null)));*/
        return insCredito;
    }

}
