package mx.gob.sat.siat.dyc.dao.archivo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoTempDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctArchivoTempMapper implements RowMapper<DyctArchivoTempDTO> {
    public DyctArchivoTempMapper() {
        super();
    }

    @Override
    public DyctArchivoTempDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctArchivoTempDTO archivoTemp = new DyctArchivoTempDTO();
        DyctServicioTempDTO servicioTemp = new DyctServicioTempDTO();

        servicioTemp.setFolioServTemp(rs.getInt("FOLIOSERVTEMP"));
        archivoTemp.setDyctServicioTempDTO(servicioTemp);
        archivoTemp.setIdArchivoTemp(rs.getInt("IDARCHIVOTEMP"));
        archivoTemp.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        archivoTemp.setTipoArchivo(rs.getInt("TIPOARCHIVO"));
        archivoTemp.setUrl(rs.getString("URL"));
        archivoTemp.setDescripcion(rs.getString("DESCRIPCION"));
        return archivoTemp;
    }
}
