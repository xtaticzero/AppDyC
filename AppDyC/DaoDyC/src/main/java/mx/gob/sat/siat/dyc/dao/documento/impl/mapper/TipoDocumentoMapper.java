package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;

import org.springframework.jdbc.core.RowMapper;


public class TipoDocumentoMapper implements RowMapper<DyccTipoDocumentoDTO>
{
    public DyccTipoDocumentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {  
        DyccTipoDocumentoDTO tipoDocumento = new DyccTipoDocumentoDTO();
        tipoDocumento.setIdTipoDocumento(rs.getInt("IDTIPODOCUMENTO"));
        tipoDocumento.setDescripcion(rs.getString("DESCRIPCION"));
        return tipoDocumento;
    }
}