package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapea la informacion de la tabla: DYCC_FILESYSTEM
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public class DyccFileSystemMapper implements RowMapper<DyccFileSystemDTO> {
    public DyccFileSystemMapper() {
        super();
    }

    @Override
    public DyccFileSystemDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccFileSystemDTO objeto = new DyccFileSystemDTO();
        objeto.setIdFileSystem(rs.getInt("IDFILESYSTEM"));
        objeto.setPuntoDeMontaje(rs.getString("PUNTODEMONTAJE"));
        objeto.setEspacioEnDisco(rs.getDouble("ESPACIOENDISCO"));
        objeto.setActivo(rs.getBoolean("ACTIVO"));
        return objeto;
    }
}
