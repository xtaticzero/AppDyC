package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.Map;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface AprobarResolucionService
{
    Map<String, Object> determinarResolucion(Map<String, Object> params) throws SIATException;
    
    Map<String, Object> escalarAprobacion(Map<String, Object> params) throws SIATException;
    
    boolean puedeEscalar(Map<String, Object> params);
        
}
