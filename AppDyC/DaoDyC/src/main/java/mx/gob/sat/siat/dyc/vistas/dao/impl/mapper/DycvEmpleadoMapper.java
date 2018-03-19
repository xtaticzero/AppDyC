package mx.gob.sat.siat.dyc.vistas.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycvEmpleadoMapper implements RowMapper<DycvEmpleadoDTO>
{
    private AdmcUnidadAdmvaMapper mapperAdmcUnidadAdmva;

    @Override
    public DycvEmpleadoDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DycvEmpleadoDTO empleado = new DycvEmpleadoDTO();

        empleado.setEmpleado(rs.getString("EMPLEADO"));
        empleado.setRfcCorto(rs.getString("RFC_CORTO"));
        empleado.setDeptid(rs.getString("DEPTID"));
        empleado.setEstatus(rs.getString("ESTATUS"));
        empleado.setNoEmpleado(rs.getInt("NO_EMPLEADO"));
        empleado.setJobcode(rs.getString("JOBCODE"));
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
        empleado.setClaveDepto1(rs.getString("GENERAL_DEPTID"));
        empleado.setDescrDepto1(rs.getString("DESCR_GENERAL"));
        empleado.setClaveDepto2(rs.getString("CENTRAL_DEPTID"));
        empleado.setDescrDepto2(rs.getString("DESCR_CENTRAL"));
        empleado.setClaveDepto3(rs.getString("ADMIN_DEPTID"));
        empleado.setDescrDepto3(rs.getString("DESCR_ADMINISTRADOR"));
        empleado.setClaveDepto4(rs.getString("SUBADMIN_DEPTID"));
        empleado.setDescrDepto4(rs.getString("DESCR_SUBADMINISTRADOR"));
        

        if(mapperAdmcUnidadAdmva != null)
        {
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