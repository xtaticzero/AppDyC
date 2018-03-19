package mx.gob.sat.siat.archivoshistorico.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ArchivoHistoricoMapper implements RowMapper<ArchivoHistoricoDto>{

    @Override
    public ArchivoHistoricoDto mapRow(ResultSet rs, int i) throws SQLException {
        ArchivoHistoricoDto archivoHistoricoDto = new ArchivoHistoricoDto();
        archivoHistoricoDto.setTabla(rs.getString("TABLA"));
        archivoHistoricoDto.setUrl(rs.getString("URL"));
        archivoHistoricoDto.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        archivoHistoricoDto.setId1(rs.getString("ID1"));
        archivoHistoricoDto.setCampo1(rs.getString("CAMPO1"));
        archivoHistoricoDto.setId2(rs.getString("ID2"));
        archivoHistoricoDto.setCampo2(rs.getString("CAMPO2"));
        archivoHistoricoDto.setId3(rs.getString("ID3"));
        archivoHistoricoDto.setCampo3(rs.getString("CAMPO3"));
        return archivoHistoricoDto;
    }
    
}