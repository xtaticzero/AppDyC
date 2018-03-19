package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.Map;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface FacturasProvService
{
    Map<String, Object> obtenerInfoReqConfOpProvs(String numControl, String rfcProveedor);
    
    Map<String, Object> generarRequerimientoFacturas(Map<String, Object> params);
    
    Map<String, Object> enviarAAprobacionReqFacturas(Map<String, Object> params) throws SIATException ;
}
