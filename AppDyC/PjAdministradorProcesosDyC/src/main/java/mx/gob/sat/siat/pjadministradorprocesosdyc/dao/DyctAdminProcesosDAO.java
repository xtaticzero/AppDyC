package mx.gob.sat.siat.pjadministradorprocesosdyc.dao;

import java.util.List;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;

public interface DyctAdminProcesosDAO {
    List<DyctAdminProcesosDTO> consultar () throws SIATException;
    DyctAdminProcesosDTO obtenerProcesoMonitor() throws SIATException;
    void actualizarStatus(Integer status, Integer idProceso) throws SIATException;
    void actualizarEjecucion(Integer status, Integer idProceso) throws SIATException;
    void actualizarHorarioEjecucion(Integer idProceso) throws SIATException;
}