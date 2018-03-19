package mx.gob.sat.siat.dyc.comunica.service;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface EjecutaAccDocService {
    void consultarTramiteyDocumento(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException;
}
