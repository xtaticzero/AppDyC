/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @since 0.1
 *
 * @date Noviembre 26, 2013
 * */
public class DyccAprobadorAdministrarSolMApper implements RowMapper<DyccAprobadorDTO> {

    @Override
    public DyccAprobadorDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccAprobadorDTO aprobador = new DyccAprobadorDTO();

        aprobador.setNombre(rs.getString("NOMBRE"));
        aprobador.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
        aprobador.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
        aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));

        return aprobador;
    }

}
