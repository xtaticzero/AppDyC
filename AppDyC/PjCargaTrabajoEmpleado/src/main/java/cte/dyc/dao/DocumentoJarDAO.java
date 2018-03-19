package cte.dyc.dao;


import cte.dyc.dto.DyctDocumentoDTO;
import cte.dyc.generico.util.common.SIATException;

import java.util.List;

public interface DocumentoJarDAO {
    
    List<DyctDocumentoDTO> consultarDocumentoAprobador(Integer empleado) throws SIATException;
    
}
