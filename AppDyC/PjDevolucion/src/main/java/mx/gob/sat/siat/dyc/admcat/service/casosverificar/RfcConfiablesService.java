package mx.gob.sat.siat.dyc.admcat.service.casosverificar;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface RfcConfiablesService {
    
    List<DycpRfcDTO> mostrarRfcConfiables(Integer activo, Integer inactivo, Integer padron);
    
    List<DycbEstadoRfcDTO> listaBitacoraCOnfiables(String rfc);
    
    void insertarConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException;
    
    void modificarRfcConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException;
}
