package mx.gob.sat.siat.dyc.dao.matriz.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoPlantillaDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccMatDocumentosMapper implements RowMapper<DyccMatDocumentosDTO> {
    public DyccMatDocumentosMapper() {
        super();
    }

    @Override
    public DyccMatDocumentosDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        
        DyccMatDocumentosDTO objetoMatDocumentos = new DyccMatDocumentosDTO();
        
        objetoMatDocumentos.setIdPlantilla(resultSet.getInt("IDPLANTILLA"));
        objetoMatDocumentos.setNombreDocumento(resultSet.getString("NOMBREDOCUMENTO"));
        objetoMatDocumentos.setDescripcionDoc(resultSet.getString("DESCRIPCIONDOC"));
        objetoMatDocumentos.setFechaInicio(resultSet.getDate("FECHAINICIO"));
        objetoMatDocumentos.setFechaFin(resultSet.getDate("FECHAFIN"));
        
        DyccTipoPlantillaDTO objetoTipoPlantilla = new DyccTipoPlantillaDTO();
        objetoTipoPlantilla.setIdTipo(resultSet.getInt("IDTIPO"));
        objetoTipoPlantilla.setDescripcion(resultSet.getString("DESCRIPCION"));
        objetoMatDocumentos.setDyccTipoPlantillaDTO(objetoTipoPlantilla);
        
        objetoMatDocumentos.setIdUnidad(resultSet.getInt("IDUNIDAD")); 
        
        return objetoMatDocumentos;
    }
}
