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
 * @date Octubre 24, 2013
 * */
public class MAprobadorMapper implements RowMapper<AprobadorVO> {
    public MAprobadorMapper() {
        super();
    }

    @Override
    public AprobadorVO mapRow(ResultSet rs, int i) throws SQLException {

        AprobadorVO aprobador = new AprobadorVO();

        aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        aprobador.setUn(rs.getString("UN"));
        aprobador.setAdmonGral(rs.getString("ADMON_GRAL"));

        aprobador.setNombre(rs.getString("NOMBRE"));
        aprobador.setApellidoPaterno(rs.getString("PATERNO"));
        aprobador.setApellidoMaterno(rs.getString("MATERNO"));
        aprobador.setNombreCompleto(rs.getString("NOMBRE") + " " + rs.getString("PATERNO") + " " +
                                    rs.getString("MATERNO"));
        aprobador.setRfc(rs.getString("RFC"));

        aprobador.setClaveNivel(rs.getInt("CLAVENIVELAP"));

        aprobador.setClaveDepto(rs.getString("CLAVEDEPTO"));
        aprobador.setDescClaveNivel(rs.getString("NIVEL_DIRECCION"));

        aprobador.setCentroCosto(rs.getInt("CENTROCOSTO"));
        aprobador.setClaveAdm(rs.getInt("CLAVEADM"));

        aprobador.setObservaciones(null != rs.getString("OBSERVACIONES") ? rs.getString("OBSERVACIONES") : "");
        aprobador.setActivoPortal(rs.getInt("ACTIVO_PORTAL"));
        aprobador.setActivo(rs.getInt("ACTIVO"));
        aprobador.setClaveComision(rs.getString("CCOMISION"));
        aprobador.setDescComision(rs.getString("DCOMISION"));

        aprobador.setCentroCostoDescr254(rs.getString("CENTRO_COSTO_DESCR254"));


        aprobador.setEmail(null != rs.getString("EMAIL") ? rs.getString("EMAIL") : "");

        return aprobador;
    }
}
