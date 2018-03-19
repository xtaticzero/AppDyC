/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut;


import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DetalleTramGenerarRepoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;


/**
 *
 * @author MLIL
 */
public interface DyctDicAutomaticaDAO {
    
    void insertarDicAutomatica (DyctDictAutomaticaDTO dicAutomatiocaDTO) throws DAOException;
    DyctDictAutomaticaDTO buscarDicAutomatica (String numControl) throws DAOException;
    List<DyctDictAutomaticaDTO> getTramitesDictaminados();
    List<DyctDictAutomaticaDTO> getAllTramitesNoProcesados();
    int actualizarMarca(String numControl);
    Long buscarGenerarReportePorFiltro (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte) throws DAOException;
    Long buscarGenerarReportePorFiltroMotivoRiesgo(int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, String idMarcResultado) throws DAOException;

    Long buscarGenerarReportePorFiltroFechaRegistro (int idModelo, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte, int idEstadoSolicitud) throws DAOException;

    Long buscarPendientesDictaminar (int idEstadoSol, Date fechaInicioGenerarReporte,
            Date fechaFinGenerarReporte) throws DAOException;

    List<DetalleTramGenerarRepoDTO> consultarDetalleTramiteGenRepoPorFiltro (int idModelo, 
            Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, Paginador paginador) throws DAOException;
    
    List<DetalleTramGenerarRepoDTO> consultarDetalleTramiteGenRepoPorFiltroMotivoRiesgo (int idModelo, 
            Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, String idMarcResultado, Paginador paginador) throws DAOException;
    
    List<DetalleTramGenerarRepoDTO> consultarDetalleTramiteGenRepoPorFiltroFechaRegistro (int idModelo, 
            Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, int idEstadoSol, Paginador paginador) throws DAOException;

    List<DetalleTramGenerarRepoDTO> consultarPendientesDictaminar (int idEstadoSol,
            Date fechaInicioGenerarReporte, Date fechaFinGenerarReporte, Paginador paginador) throws DAOException;
}
