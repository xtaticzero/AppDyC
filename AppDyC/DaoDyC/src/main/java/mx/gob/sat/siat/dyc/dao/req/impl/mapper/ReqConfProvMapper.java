package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;

import org.springframework.jdbc.core.RowMapper;


public class ReqConfProvMapper implements RowMapper<DyctReqConfProvDTO>
{
    private DyctDocumentoMapper mapperDocumento;

    public DyctReqConfProvDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {   
        DyctReqConfProvDTO reqConfProv = new DyctReqConfProvDTO();
        if(mapperDocumento != null)
        {
            reqConfProv.setDyctDocumentoDTO(mapperDocumento.mapRow(rs, rowNum));
        }
        else
        {
            reqConfProv = new DyctReqConfProvDTO();
            DyctDocumentoDTO documento = new DyctDocumentoDTO();
            documento.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
            reqConfProv.setDyctDocumentoDTO(documento);
        }
        reqConfProv.setRfcProveedor(rs.getString("RFCPROVEEDOR"));
        return reqConfProv;
    }

    public DyctDocumentoMapper getMapperDocumento() {
        return mapperDocumento;
    }

    public void setMapperDocumento(DyctDocumentoMapper mapperDocumento) {
        this.mapperDocumento = mapperDocumento;
    }
}
