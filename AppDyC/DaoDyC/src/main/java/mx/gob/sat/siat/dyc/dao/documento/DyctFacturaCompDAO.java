package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;


public interface DyctFacturaCompDAO
{
    void insertar(DyctFacturaReqDTO factura);
    
    List<DyctFacturaReqDTO> selecXDocumentoRfcprov(DyctDocumentoDTO casoComp, String rfcProveedor);
}