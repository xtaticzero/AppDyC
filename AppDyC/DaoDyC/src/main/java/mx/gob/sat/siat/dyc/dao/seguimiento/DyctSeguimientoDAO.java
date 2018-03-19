package mx.gob.sat.siat.dyc.dao.seguimiento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctSeguimientoDAO {
    DyctSeguimientoDTO encontrar(Integer id);

    List<DyctSeguimientoDTO> selecXCompensacion(DycpCompensacionDTO compensacion);

    List<DyctSeguimientoDTO> selecXDocumentocc(DyctDocumentoDTO dyctDocumentoCcDTO);

    List<DyctSeguimientoDTO> selecXServicio(DycpServicioDTO servicio);
    
    void insertar(DyctSeguimientoDTO objetoDyctSeguimiento) throws SIATException;
    
    void borrarSeguimientos(DyctDocumentoDTO dyctDocumentoDTO, List<DyctSeguimientoDTO> lista) throws SIATException;
    
    void limpiarSeguimientos(DyctDocumentoDTO dyctDocumentoDTO) throws SIATException;
    
}
