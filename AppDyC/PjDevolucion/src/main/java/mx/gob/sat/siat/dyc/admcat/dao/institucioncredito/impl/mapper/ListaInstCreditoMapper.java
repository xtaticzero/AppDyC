/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.institucioncredito.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.ListaInstitucionCreditoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO Caso de uso Instituciones de Credito DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
public class ListaInstCreditoMapper implements RowMapper<ListaInstitucionCreditoDTO> {
    public ListaInstCreditoMapper() {
        super();
    }

    @Override
    public ListaInstitucionCreditoDTO mapRow(ResultSet rs, int i) throws SQLException {
        ListaInstitucionCreditoDTO insCredito = new ListaInstitucionCreditoDTO();

        insCredito.setIdInstCredito(rs.getInt("IDINSTCREDITO"));
        insCredito.setDescripcion(rs.getString("DESCRIPCION"));
        insCredito.setOrdenSec(rs.getInt("ORDENSEC"));
        insCredito.setFechaInicio(rs.getDate("FECHAINICIO"));
        insCredito.setFechaFin(rs.getDate("FECHAFIN"));
        insCredito.setEstado(rs.getString("ESTADO"));

        return insCredito;
    }
}
