package mx.gob.sat.siat.dyc.adminprocesos.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.Procesos;


public interface AdministrarProcesosService {
    List<Procesos> consultar() throws SIATException;
    void guardarCron(Procesos proceso) throws SIATException;
    List<DyccStatusProcesoDTO> listarStatusProceso() throws SIATException;
    void actualizarStatusProceso(Procesos proceso) throws SIATException;
    String consultarFechaEjecucion() throws SIATException;
}
