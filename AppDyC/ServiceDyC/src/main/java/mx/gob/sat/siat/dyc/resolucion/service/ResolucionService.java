package mx.gob.sat.siat.dyc.resolucion.service;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author gregorio.serapio
 */
public interface ResolucionService {
    DyctResolucionDTO calcularActualizacionAutorizacionTotal(DycpSolicitudDTO dycpSolicitudDTO) throws SIATException;
    
    void calcularImportesControlDeSaldos(DycpSolicitudDTO dycpSolicitudDTO) throws SIATException;
}
