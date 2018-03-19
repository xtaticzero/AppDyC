/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.util.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class DycaReasignacionMapper implements RowMapper<ReasignacionDTO> {
    @Override
    public ReasignacionDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        ReasignacionDTO reasignacion = new ReasignacionDTO();
        reasignacion.setIdReasignacion(resultSet.getInt("IDREASIGNACION"));
        reasignacion.setEmpleadoAsignado(resultSet.getInt("EMPLEADOASIGNADO"));
        reasignacion.setEmpleadoResponsable(resultSet.getInt("RESPONSABLEDEASIGNAR"));
        reasignacion.setFechaFin(resultSet.getDate("FECHAFIN"));
        reasignacion.setFechaReasignacion(resultSet.getDate("FECHAREASIGNACION"));
        reasignacion.setOrigen(resultSet.getString("ORIGEN"));
        reasignacion.setNumcontrol(resultSet.getString("NUMCONTROL"));
       
        return reasignacion;
    }
}
