package mx.gob.sat.siat.dyc.controlsaldos.service.icep;

import java.util.Map;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface AfectarSaldosXCompensacionesService
{
    Map<String, Object> afectarXLiquidacion(String numControlDoc) throws SIATException;
    
    Map<String, Object> afectarXDesistimiento(String numControl) throws SIATException;
    
    Map<String, Object> afectarXDesistimientoCasoCompensacion(String numControl) throws SIATException;
}
