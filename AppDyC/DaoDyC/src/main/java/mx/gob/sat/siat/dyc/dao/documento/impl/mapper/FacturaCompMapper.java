package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;

import org.springframework.jdbc.core.RowMapper;


public class FacturaCompMapper implements RowMapper<DyctFacturaReqDTO>
{
    public DyctFacturaReqDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyctFacturaReqDTO facturaComp = new DyctFacturaReqDTO();
        
        DyctDocumentoDTO documentoComp = new DyctDocumentoDTO();
        documentoComp.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        /**facturaComp.setDocumentoComp(documentoComp);*/
        
        facturaComp.setNumeroFactura(rs.getString("NUMEROFACTURA"));
       /** facturaComp.setRfcProveedor(rs.getString("RFCPROVEEDOR"));*/
        facturaComp.setFechaEmision(rs.getDate("FECHAEMISION"));
        facturaComp.setConcepto(rs.getString("CONCEPTO"));
        facturaComp.setImporte(rs.getBigDecimal("IMPORTE"));
        facturaComp.setIvaTrasladado(rs.getBigDecimal("IVATRASLADADO"));
        facturaComp.setTotal(rs.getBigDecimal("TOTAL"));
        return facturaComp;
    }
}