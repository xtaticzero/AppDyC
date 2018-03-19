/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public class EmpleadoVOMapper implements RowMapper<EmpleadoVO> {

    @Override
    public EmpleadoVO mapRow(ResultSet rs, int i) throws SQLException {
        EmpleadoVO empl = new EmpleadoVO();

        empl.setNumEmpleado(rs.getString("NUMEMPLEADO"));
        /**empl.setRfcCorto(rs.getString("RFC_CORTO"));*/
        empl.setRfc(rs.getString("RFC"));
        /**empl.setCurp(rs.getString("CURP"));*/
        empl.setUn(rs.getString("UN"));
        empl.setAdmGral(rs.getString("ADMON_GRAL"));
        empl.setNombreCompleto(rs.getString("NOMBRE") + " " + rs.getString("PATERNO") + " " + rs.getString("MATERNO"));
        empl.setNombre(rs.getString("NOMBRE"));
        empl.setPaterno(rs.getString("PATERNO"));
        empl.setMaterno(rs.getString("MATERNO"));
        /**empl.setPuesto(rs.getString("NOMBRE_PUESTO"));*/
        empl.setClaveNivel(rs.getInt("CLAVENIVEL"));
        empl.setDescClaveNivel(rs.getString("NIVEL_DIRECCION"));
        empl.setClaveDepto(rs.getString("CLAVEDEPTO"));
        /**empl.setDescDepto(rs.getString("DESCR_DEPTO"));*/
        empl.setCcosto(rs.getInt("CENTROCOSTO"));
        empl.setCentroCostoDescr254(rs.getString("CENTRO_COSTO_DESCR254"));
        empl.setEmail(rs.getString("EMAIL"));

        empl.setActivo(rs.getInt("ACTIVO_PORTAL"));
        empl.setClaveComision(rs.getString("CCOMISION"));
        empl.setDescComision(rs.getString("DCOMISION"));
        empl.setClaveAdm(rs.getInt("CLAVEADM"));
        empl.setClaveAdmOp(rs.getInt("CLAVEADMOP"));

        return empl;
    }
}
