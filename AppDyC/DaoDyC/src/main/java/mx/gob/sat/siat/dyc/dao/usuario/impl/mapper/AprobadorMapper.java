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

import mx.gob.sat.siat.dyc.vo.AprobadorVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla [DYCC_APROBADOR] + Descripcion Administracion Comision.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date aGOSTO 18, 2014
 * */
public class AprobadorMapper implements RowMapper<AprobadorVO> {
    public AprobadorMapper() {
        super();
    }

    @Override
    public AprobadorVO mapRow(ResultSet rs, int i) throws SQLException {

        AprobadorVO aprobador = new AprobadorVO();
        aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        aprobador.setActivo(rs.getInt("ACTIVO"));
        aprobador.setObservaciones(null != rs.getString("OBSERVACIONES") ? rs.getString("OBSERVACIONES") : "");
        aprobador.setClaveDepto(rs.getString("CLAVEDEPTO"));
        aprobador.setNombre(rs.getString("NOMBRE"));
        aprobador.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
        aprobador.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
        aprobador.setEmail(null != rs.getString("EMAIL") ? rs.getString("EMAIL") : "");
        aprobador.setRfc(rs.getString("RFC"));
        aprobador.setNumEmpleadoJefe(rs.getInt("NUMEMPLEADOJEFE"));
        aprobador.setClaveAdm(rs.getInt("CLAVEADM"));

        return aprobador;
    }
}
