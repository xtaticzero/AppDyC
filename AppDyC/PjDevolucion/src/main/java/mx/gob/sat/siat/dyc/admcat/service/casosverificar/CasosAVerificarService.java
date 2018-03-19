package mx.gob.sat.siat.dyc.admcat.service.casosverificar;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface CasosAVerificarService {
    
    List<DyccMotivoRfcDTO> mostrarMotivos(Integer tipoAccion);
    
    List<DycbEstadoRfcDTO> mostrarRfcNoConfiables(String rfc,Integer activo, Integer inactivo, Integer padron);
    
    boolean insertarRfcNoConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException;
    
    String buscaRfcEnAMpliado(String rfc);
    
    void modificarRfcNoConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException;
    
    List<DycbEstadoRfcDTO> bitacoraXRFC(String rfc);
    
    DycpRfcDTO encontrarRfc(String rfc);
 
}
