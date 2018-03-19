package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.Map;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface EmitirRequerimientoService
{
    Map<String, Object> obtenerInfoIniEmitirReq(String numControl);
    
    Map<String, Object> generarRequerimiento(Map<String, Object> params) throws SIATException;
}
