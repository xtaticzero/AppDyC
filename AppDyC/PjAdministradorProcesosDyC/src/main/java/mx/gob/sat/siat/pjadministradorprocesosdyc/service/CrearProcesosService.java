package mx.gob.sat.siat.pjadministradorprocesosdyc.service;

import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;

import org.quartz.Scheduler;

public interface CrearProcesosService {
    void crearProcesos() throws SIATException;
    void ejecutarProcesos(Scheduler planificador) throws SIATException;
}
