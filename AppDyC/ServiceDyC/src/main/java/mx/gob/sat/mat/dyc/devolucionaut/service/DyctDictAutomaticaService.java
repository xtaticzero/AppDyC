/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.devolucionaut.service;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DetalleTramGenerarRepoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author MLIL
 */
public interface DyctDictAutomaticaService {
    void insertarDictAutomatica (DyctDictAutomaticaDTO dictAutomaticoDTO) throws SIATException;
    DyctDictAutomaticaDTO buscarDictAutomatica (String numControl) throws SIATException;
    Integer buscarGenerarReportePorFiltro (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte) throws SIATException;
    Integer buscarGenerarReportePorFiltroMotivoRiesgo (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, String idMarcResultado) throws SIATException;

    Integer buscarGenerarReportePorFiltroFechaRegistro (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, int idEstadoSol) throws SIATException;

    Integer buscarPendientesDictaminar (int idEstadoSol, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte) throws SIATException;

    List<DetalleTramGenerarRepoDTO> consultarDetalleTramGenRepoPorFiltro (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, Paginador paginador) throws SIATException;

    List<DetalleTramGenerarRepoDTO> consultarDetalleTramGenRepoPorFiltroMotivoRiesgo (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, String idMarcResultado, Paginador paginador) throws SIATException;
    
    List<DetalleTramGenerarRepoDTO> consultarDetalleTramGenRepoPorFiltroFechaRegistro (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, int idEstadoSol, Paginador paginador) throws SIATException;

    List<DetalleTramGenerarRepoDTO> consultarPendientesDictaminar (int idEstadoSol, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, Paginador paginador) throws SIATException;
}
