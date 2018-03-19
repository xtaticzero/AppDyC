package mx.gob.sat.siat.dyc.vistas.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vistas.vo.DycvEmpleadoVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 20, 2013
 * @since 0.1
 *
 * */

public class DycvEmpleadoVOMapper implements RowMapper<DycvEmpleadoVO> {

     @Override
     public DycvEmpleadoVO mapRow(ResultSet rs, int i) throws SQLException {
         DycvEmpleadoVO obj = new DycvEmpleadoVO();

         obj.setNoEmpleado(rs.getInt("EMPLEADO"));
         obj.setRfc(rs.getString("RFC"));
         obj.setUn(rs.getString("UN"));
         obj.setAdmonGral(rs.getString("ADMON_GRAL"));
         obj.setNombreCompleto(rs.getString("NOMBRE") + " " + rs.getString("PATERNO") + " " + rs.getString("MATERNO"));
         obj.setNombre(rs.getString("NOMBRE"));
         obj.setPaterno(rs.getString("PATERNO"));
         obj.setMaterno(rs.getString("MATERNO"));
         obj.setIntClaveNivelDireccion(rs.getInt("CLAVENIVEL"));
         obj.setNivelDireccion(rs.getString("NIVEL_DIRECCION"));
         obj.setClaveDepto(rs.getString("CLAVEDEPTO"));
         obj.setIntCentroCosto(rs.getInt("CENTROCOSTO"));
         obj.setClaveComision(rs.getString("CCOMISION"));
         obj.setDescComision(rs.getString("DCOMISION"));
         obj.setActivoPortal(rs.getInt("ACTIVO_PORTAL"));
         obj.setClaveAdm(rs.getInt("CLAVEADM"));
         obj.setClaveAdmOp(rs.getInt("CLAVEADMOP"));
         obj.setEmail(rs.getString("EMAIL"));

         return obj;
     }
}
