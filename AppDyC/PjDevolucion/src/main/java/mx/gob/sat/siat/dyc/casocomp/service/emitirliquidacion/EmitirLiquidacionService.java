package mx.gob.sat.siat.dyc.casocomp.service.emitirliquidacion;


import java.util.Map;

import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Huetzin Cruz Lozano
 */
public interface EmitirLiquidacionService 
{
    Map<String, Object> obtenerInfoInicial(Map<String, Object> params);
    
    Map<String, Object> emitirLiquidacion(Map<String, Object> params) throws SIATException;
    
    String enviarAAprobacion(Map<String, Object> params) throws SIATException ;
}

