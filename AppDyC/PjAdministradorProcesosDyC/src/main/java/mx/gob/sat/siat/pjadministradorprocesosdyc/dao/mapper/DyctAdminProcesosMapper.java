package mx.gob.sat.siat.pjadministradorprocesosdyc.dao.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;

import org.springframework.jdbc.core.RowMapper;

public class DyctAdminProcesosMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        DyctAdminProcesosDTO objeto = new DyctAdminProcesosDTO();
        objeto.setIdServicio(rs.getInt("IDSERVICIO"));
        objeto.setNombre(rs.getString("NOMBRE"));
        objeto.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setStatus(rs.getInt("STATUS"));
        objeto.setIntentos(rs.getInt("INTENTOS"));
        objeto.setNumMaxIntentos(rs.getInt("NUMMAXINTENTOS"));
        objeto.setPrioridad(rs.getInt("PRIORIDAD"));
        objeto.setEjecucionCorrecta(rs.getInt("EJECUCIONCORRECTA"));
        objeto.setHorarioProgramado(rs.getString("HORARIOPROGRAMADO"));
        objeto.setNombreJob(rs.getString("NOMBREJOB"));
        objeto.setNombreTrigger(rs.getString("NOMBRETRIGGER"));
        
        return objeto;
    }
}