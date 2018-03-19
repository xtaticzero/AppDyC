package mx.gob.sat.siat.pjadministradorprocesosdyc.service;

import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;

public interface ManejadorProcesosService {
    void ejecutarProcesos() throws SIATException;
}
