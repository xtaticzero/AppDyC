/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.ags.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmvaMapper;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author root
 */
public class SatAgsEmpleadosMVMapper implements RowMapper<SatAgsEmpleadosMVDTO> {

    private AdmcUnidadAdmvaMapper mapperAdmcUnidadAdmva;

    @Override
    public SatAgsEmpleadosMVDTO mapRow(ResultSet rs, int i) throws SQLException {
        SatAgsEmpleadosMVDTO empleado = new SatAgsEmpleadosMVDTO();

        empleado.setEmpleado(rs.getString("EMPLEADO"));
        empleado.setRfcCorto(rs.getString("RFC_CORTO"));
        empleado.setDeptid(rs.getString("DEPTID"));
        empleado.setEstatus(rs.getString("ESTATUS"));
        empleado.setNoEmpleado(rs.getInt("NO_EMPLEADO"));
        empleado.setJobcode(rs.getString("JOBCODE"));
        empleado.setLocation(rs.getString("LOCATION"));
        empleado.setUn(rs.getString("UN"));
        empleado.setAdmonGral(rs.getString("ADMON_GRAL"));
        empleado.setNombre(rs.getString("NOMBRE"));
        empleado.setPaterno(rs.getString("PATERNO"));
        empleado.setMaterno(rs.getString("MATERNO"));
        empleado.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
        empleado.setDomicilio(rs.getString("DOMICILIO"));
        empleado.setColonia(rs.getString("COLONIA"));
        empleado.setCp(rs.getString("CP"));
        empleado.setMunicipio(rs.getString("MUNICIPIO"));
        empleado.setCiudad(rs.getString("CIUDAD"));
        empleado.setEstado(rs.getString("ESTADO"));
        empleado.setEdoDescripcion(rs.getString("EDO_DESCRIPCION"));
        empleado.setRfc(rs.getString("RFC"));
        empleado.setCurp(rs.getString("CURP"));
        empleado.setNombrePuesto(rs.getString("NOMBRE_PUESTO"));
        empleado.setClaveNivelDireccion(rs.getString("CLAVE_NIVEL_DIRECCION"));
        empleado.setDescrDepto(rs.getString("DESCR_DEPTO"));
        empleado.setNivelDireccion(rs.getString("NIVEL_DIRECCION"));
        empleado.setCentroCosto(rs.getString("CENTRO_COSTO"));
        empleado.setCentroCostoDescr(rs.getString("CENTRO_COSTO_DESCR"));
        empleado.setCentroCostoDescr254(rs.getString("CENTRO_COSTO_DESCR254"));
        empleado.setEmail(rs.getString("EMAIL"));
        empleado.setGeneralDeptid(rs.getString("GENERAL_DEPTID"));
        empleado.setDescrGeneral(rs.getString("DESCR_GENERAL"));
        empleado.setEmplStatusGeneral(rs.getString("EMPL_STATUS_GENERAL"));
        empleado.setCentralDeptid(rs.getString("CENTRAL_DEPTID"));
        empleado.setDescrCentral(rs.getString("DESCR_CENTRAL"));
        empleado.setEmplStatusCentral(rs.getString("EMPL_STATUS_CENTRAL"));
        empleado.setAdminDeptid(rs.getString("ADMIN_DEPTID"));
        empleado.setDescrAdministrador(rs.getString("DESCR_ADMINISTRADOR"));
        empleado.setEmplStatusAdmin(rs.getString("EMPL_STATUS_ADMIN"));
        empleado.setSubadminDeptid(rs.getString("SUBADMIN_DEPTID"));
        empleado.setDescrSubadministrador(rs.getString("DESCR_SUBADMINISTRADOR"));
        empleado.setEmplStatusSubadmin(rs.getString("EMPL_STATUS_SUBADMIN"));
        empleado.setJefeDeptoDeptId(rs.getString("JEFEDEPTO_DEPTID"));
        empleado.setDescrJefeDepto(rs.getString("DESCR_JEFEDEPTO"));
        empleado.setEmplStatusJefedepto(rs.getString("EMPL_STATUS_JEFEDEPTO"));
        empleado.setEnlaceDeptid(rs.getString("ENLACE_DEPTID"));
        empleado.setDescrEnlace(rs.getString("DESCR_ENLACE"));
        empleado.setEmplStatusEnlace(rs.getString("EMPL_STATUS_ENLACE"));

        if (mapperAdmcUnidadAdmva != null) {
            empleado.setAdmcUnidadAdmvaDTO(mapperAdmcUnidadAdmva.mapRow(rs, i));
        }

        return empleado;
    }

    public AdmcUnidadAdmvaMapper getMapperAdmcUnidadAdmva() {
        return mapperAdmcUnidadAdmva;
    }

    public void setMapperAdmcUnidadAdmva(AdmcUnidadAdmvaMapper mapperAdmcUnidadAdmva) {
        this.mapperAdmcUnidadAdmva = mapperAdmcUnidadAdmva;
    }
}
