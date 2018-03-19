package mx.gob.sat.siat.dyc.controlsaldos.service;

import java.util.Date;
import java.util.Map;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface RegistrosManualesService {
    Map<String, Object> obtenerInfoInicial();
    Map<String, Object> insertarDevolucion(Map<String, Object> params)  throws SIATException;
    Double calcularActualizacion(Integer idSaldoIcep, Date fechaDestino, Double importeAutorizado)  throws SIATException; 
    Map<String, Object> insertarCompensacion(Map<String, Object> params) throws SIATException;
    Boolean existeNumControl(String numControl);
}
