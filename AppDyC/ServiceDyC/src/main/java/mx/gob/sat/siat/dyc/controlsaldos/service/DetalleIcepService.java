package mx.gob.sat.siat.dyc.controlsaldos.service;

import java.util.Map;

import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDeclaracionesBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


public interface DetalleIcepService {
    
    String KEY_SUCCESS = "success";
    String KEY_MENSAJE = "mensaje";

    Map<String, Object> obtenerInfoInicial(Integer idSaldoIcep, AccesoUsr accesoUsrL) throws SIATException;

    Map<String, Object> buscarDeclaraciones(Integer idSaldoIcep) throws SIATException;

    Map<String, Object> insertarDeclaracion(FilaGridDeclaracionesBean filaGridDeclaracionesBean, Integer idSaldoIcep, String usuario);

    Map<String, Object> eliminarDeclaraHist(Integer idDeclaraIcep);

    Map<String, Object> reactivarDeclaraHist(Integer idDeclaraIcep);

    Map<String, Object> eliminarCompensacionHist(Integer idMovCompensacion);

    Map<String, Object> eliminarDevolucionHist(Integer idMovDevolucion);
    
    Map<String, Object> obtenerInfoInicialConsulta(String numControl) throws SIATException;
    
}
