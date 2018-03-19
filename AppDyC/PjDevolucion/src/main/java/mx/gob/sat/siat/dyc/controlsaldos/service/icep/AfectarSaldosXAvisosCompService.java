package mx.gob.sat.siat.dyc.controlsaldos.service.icep;

import java.util.Map;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface AfectarSaldosXAvisosCompService {
    Map<String, Object> afectarXRegistro(DycpCompensacionDTO aviso) throws SIATException;
}
