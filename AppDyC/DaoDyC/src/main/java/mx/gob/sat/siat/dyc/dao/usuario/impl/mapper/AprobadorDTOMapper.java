package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;

import org.springframework.jdbc.core.RowMapper;


public class AprobadorDTOMapper implements RowMapper<DyccAprobadorDTO> {
    @Override
    public DyccAprobadorDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
        aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        aprobador.setActivo(rs.getInt("ACTIVO"));
        aprobador.setObservaciones(rs.getString("OBSERVACIONES"));
        aprobador.setClaveDepto(rs.getString("CLAVEDEPTO"));
        aprobador.setNombre(rs.getString("NOMBRE"));
        aprobador.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
        aprobador.setEmail(rs.getString("EMAIL"));
        aprobador.setRfc(rs.getString("RFC"));
        aprobador.setClaveNivel(rs.getInt("CLAVENIVEL"));
        aprobador.setCentroCosto(rs.getInt("CENTROCOSTO"));
        aprobador.setNumEmpleadoJefe(rs.getInt("NUMEMPLEADOJEFE"));
        aprobador.setClaveAdm(rs.getInt("CLAVEADM"));
        aprobador.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
        
        return aprobador;
    }
}
