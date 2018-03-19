package mx.gob.sat.siat.dyc.dao.rfc;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DycbEstadoRfcDAO {
    
    List<DycbEstadoRfcDTO> buscaRegistros(String rfc, Integer alta, Integer baja);
    
    void insertar(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException ;    
}
