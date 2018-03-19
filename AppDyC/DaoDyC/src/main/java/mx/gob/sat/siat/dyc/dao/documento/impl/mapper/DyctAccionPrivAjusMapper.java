package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author softtek
 */
public class DyctAccionPrivAjusMapper implements RowMapper<DyctAccionPrivAjusDTO>
{
    private Boolean mapearUsuarios;
    private Boolean mapearEmpleado;

    @Override
    public DyctAccionPrivAjusDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctAccionPrivAjusDTO accionMovSaldo = new DyctAccionPrivAjusDTO();
        accionMovSaldo.setIdAccionPrivAjus(rs.getInt("IDACCIONPRIVAJUS"));
        accionMovSaldo.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        accionMovSaldo.setRespPriv(rs.getInt("RESPPRIV"));
        accionMovSaldo.setFechaRegistroPriv(rs.getTimestamp("FECHAREGISTROPRIV"));
        accionMovSaldo.setTipoAccionPriv(rs.getInt("TIPOACCIONPRIV"));

        if(mapearUsuarios != null && mapearUsuarios){
            mapearUsuarios(accionMovSaldo, rs);
        }
        else if(mapearEmpleado != null && mapearEmpleado){
            mapearEmpleado(accionMovSaldo, rs);
        }

        return accionMovSaldo;
    }
    
    public void mapearUsuarios(DyctAccionPrivAjusDTO accionMovSaldo, ResultSet rs) throws SQLException
    {
        DycvEmpleadoDTO empleado = new DycvEmpleadoDTO();
        empleado.setNoEmpleado(rs.getInt("NUMEMPLEADO"));
        empleado.setRfc(rs.getString("RFC_EMP"));
        empleado.setNombreCompleto(rs.getString("NOMBRE_COMPLETO_EMP"));
        empleado.setCentroCostoDescr(rs.getString("CENTRO_COSTO_DESCR_EMP"));

        DycvEmpleadoDTO responsable = new DycvEmpleadoDTO();
        responsable.setNoEmpleado(rs.getInt("RESPPRIV"));
        responsable.setRfc(rs.getString("RFC_RESP"));
        responsable.setNombreCompleto(rs.getString("NOMBRE_COMPLETO_RESP"));
        responsable.setCentroCostoDescr(rs.getString("CENTRO_COSTO_DESCR_RESP"));

        accionMovSaldo.setEmpleado(empleado);
        accionMovSaldo.setResponsable(responsable);
    }
    
     public void mapearEmpleado(DyctAccionPrivAjusDTO accionMovSaldo, ResultSet rs) throws SQLException
    {
        DycvEmpleadoDTO empleado = new DycvEmpleadoDTO();
        empleado.setNoEmpleado(rs.getInt("NUMEMPLEADO"));
        empleado.setRfc(rs.getString("RFC"));
        empleado.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
        empleado.setCentroCostoDescr(rs.getString("CENTRO_COSTO_DESCR"));

        accionMovSaldo.setEmpleado(empleado);
    }

    public Boolean getMapearUsuarios() {
        return mapearUsuarios;
    }

    public void setMapearUsuarios(Boolean mapearUsuarios) {
        this.mapearUsuarios = mapearUsuarios;
    }

    public Boolean getMapearEmpleado() {
        return mapearEmpleado;
    }

    public void setMapearEmpleado(Boolean mapearEmpleado) {
        this.mapearEmpleado = mapearEmpleado;
    }
}
