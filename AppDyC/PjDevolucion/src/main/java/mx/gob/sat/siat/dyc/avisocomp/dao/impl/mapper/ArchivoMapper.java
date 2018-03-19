package mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;

import org.springframework.jdbc.core.RowMapper;


public class ArchivoMapper implements RowMapper<ArchivoVO> {
    public ArchivoMapper() {
        super();
    }

    @Override
    public ArchivoVO mapRow(ResultSet rs, int i) throws SQLException {
        ArchivoVO archivo = new ArchivoVO();
        
        archivo.setIdArchivoAviso(rs.getInt("IDARCHIVOAVISO"));
        archivo.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        archivo.setUrl(rs.getString("URL"));
        archivo.setDescripcion(rs.getString("DESCRIPCION"));
        archivo.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        
        return archivo;
    }
}
