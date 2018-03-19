/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.VistaAprobadorVO;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla [DYCC_APROBADOR] + Descripcion Administracion Comision.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Octubre 24, 2013
 * */
public class VistaAprobadorMapper implements RowMapper<VistaAprobadorVO> {
    private static final Logger LOG = Logger.getLogger(VistaAprobadorMapper.class.getName());

    public VistaAprobadorMapper() {
        super();
    }

    @Override
    public VistaAprobadorVO mapRow(ResultSet rs, int i) throws SQLException {

        VistaAprobadorVO aprobador = new VistaAprobadorVO();

        try {
            aprobador.setDescAdministracion(rs.getString("DESCOMISIONADO"));
            aprobador.setClaveAdm(rs.getInt("CLAVEADM"));
            /**             aprobador.setClaveAdmComision(rs.getInt("CLAVEADMCOMISION")); */

        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        /**         aprobador.setNombreCompleto(rs.getString("NOMBRECOMPLETO"));
        aprobador.setIdEstado(rs.getString("IDESTADO")); */
        aprobador.setObservaciones(rs.getString("OBSERVACIONES"));
        /**         aprobador.setJefeInmediato(rs.getString("JEFEINMEDIATO"));
        aprobador.setPuesto(rs.getString("PUESTO")); */
        aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        /**         aprobador.setComisionado(rs.getInt("COMISIONADO")); */

        return aprobador;
    }
}
