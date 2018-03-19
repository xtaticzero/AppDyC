package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctFacturaReqMapper implements RowMapper<DyctFacturaReqDTO>
{
    private DyctReqConfProvDTO reqConfProv;
      
    @Override
    public DyctFacturaReqDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyctFacturaReqDTO dyctFacturaReqDTO = new DyctFacturaReqDTO();
        dyctFacturaReqDTO.setNumeroFactura   (resultSet.getString("NUMEROFACTURA"));
        dyctFacturaReqDTO.setFechaEmision    (resultSet.getDate("FECHAEMISION"));
        dyctFacturaReqDTO.setConcepto        (resultSet.getString("CONCEPTO"));
        dyctFacturaReqDTO.setImporte         (resultSet.getBigDecimal("IMPORTE"));
        dyctFacturaReqDTO.setIvaTrasladado   (resultSet.getBigDecimal("IVATRASLADADO"));
        dyctFacturaReqDTO.setTotal           (resultSet.getBigDecimal("TOTAL"));
        if(reqConfProv != null)
        {
            dyctFacturaReqDTO.setDyctReqConfProvDTO(reqConfProv);        
        }
        else
        {
            DyctReqConfProvDTO reqConfProvV = new DyctReqConfProvDTO();
            DyctDocumentoDTO documento = new DyctDocumentoDTO();
            documento.setNumControlDoc(resultSet.getString("NUMCONTROLDOC"));
            reqConfProvV.setDyctDocumentoDTO(documento);
            dyctFacturaReqDTO.setDyctReqConfProvDTO(reqConfProvV);
        }
        return dyctFacturaReqDTO;
    }

    public DyctReqConfProvDTO getReqConfProv() {
        return reqConfProv;
    }

    public void setReqConfProv(DyctReqConfProvDTO reqConfProv) {
        this.reqConfProv = reqConfProv;
    }
}
