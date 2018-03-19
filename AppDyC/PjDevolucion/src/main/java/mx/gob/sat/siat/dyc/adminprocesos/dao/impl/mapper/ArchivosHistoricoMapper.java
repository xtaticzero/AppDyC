package mx.gob.sat.siat.dyc.adminprocesos.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.adminprocesos.dto.RegistroFallidoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class ArchivosHistoricoMapper implements RowMapper<RegistroFallidoDTO> {
    public ArchivosHistoricoMapper() {
        super();
    }

    @Override
    public RegistroFallidoDTO mapRow(ResultSet rs, int i) throws SQLException {
        RegistroFallidoDTO objeto = new RegistroFallidoDTO();
        objeto.setNumControl(rs.getString("NUMCONTROL"));
        objeto.setCausaDeFallo(rs.getString("CAUSA"));
        objeto.setPuntoDeMontaje(rs.getString("PUNTODEMONTAJE"));
        return objeto;
    }
}
