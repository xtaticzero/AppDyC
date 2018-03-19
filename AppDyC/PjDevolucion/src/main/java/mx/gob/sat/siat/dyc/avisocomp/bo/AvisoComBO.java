package mx.gob.sat.siat.dyc.avisocomp.bo;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.exception.FolioComplementarioException;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;


public interface AvisoComBO {
    
    DycpAvisoCompDTO buscarAvisoNormal(String folioAvisoNormal) throws FolioComplementarioException;
    
    DycpAvisoCompDTO validarFolioAvisoNormal(String folioAvisoNormal, String rfcSession) throws FolioComplementarioException;
    
    List<DyccTipoDeclaraDTO> obtenerTiposDeclaracion();
    
    DyccTipoDeclaraDTO encontrarTipoDeclaracion(Integer idTipoDeclaracion);
}
