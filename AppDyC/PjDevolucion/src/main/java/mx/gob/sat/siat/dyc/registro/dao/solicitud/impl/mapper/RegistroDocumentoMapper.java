package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;

import org.springframework.jdbc.core.RowMapper;


public class RegistroDocumentoMapper implements RowMapper<DyctDocumentoDTO> {
    public RegistroDocumentoMapper() {
        super();
    }

    @Override
    public DyctDocumentoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctDocumentoDTO documento = new DyctDocumentoDTO();
        
        documento.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
       /**documento.setNombreDocumento(rs.getString("NOMBREDOCUMENTO"));*/
        documento.setUrl(rs.getString("URL"));
       /** documento.setNumControl(rs.getString("NUMCONTROL"));
        documento.setIdEstadoDoc(rs.getInt("IDESTADODOC"));*/
        documento.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
       /** documento.setEstadoDesc(rs.getString("DESCRIPCION"));*/
        return documento;
    }
}
