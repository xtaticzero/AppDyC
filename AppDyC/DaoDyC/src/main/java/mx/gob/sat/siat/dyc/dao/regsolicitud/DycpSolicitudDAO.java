package mx.gob.sat.siat.dyc.dao.regsolicitud;

import java.sql.Date;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.vo.DycpSolicitudDevolucionDTO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;


public interface DycpSolicitudDAO {
    DycpSolicitudDTO consultarSolicitud(String query, String numControl) throws SIATException;
    
    DycpSolicitudDTO consultarSolicitud(String numControl) throws SIATException;

    int actualizarStatus(Integer status, String numControl) throws SIATException;

    void actualizarFechaFinTramite(Date fecha, String numControl) throws SIATException;

    SolicitudConsultarExpedienteVO buscarNumcontrol(String numControl);

    SolicitudConsultarExpedienteVO buscarNumcontrolComp(String numControl);

    void insertaServicioSolicitud(DycpSolicitudDTO input) throws SIATException, SQLException;

    void insertaServicioSol(DycpSolicitudDTO input, Integer numEmpleado) throws SIATException, SQLException;

    void insertaSolicitud(DycpSolicitudDTO input) throws DycDaoExcepcion;

    Long countSolicitudesDictaminador(String numEmpleado, String estados, String numControl, String rfc);

    List<SolicitudAdministrarSolVO> buscarSolicitudesDictaminador(String numEmpleado, int estado);

    List<SolicitudAdministrarSolVO> buscarSolicitudesDictaminadorPaginador(String numEmpleado, String estado,
                                                                           String numControl, String rfc,
                                                                           Paginador paginador);

    Long countSolicitudesAdministrador(int unidad, String numControl, String rfc);

    List<SolicitudAdministrarSolVO> buscarSolicitudesAdministrador(int unidad);

    List<SolicitudAdministrarSolVO> buscarSolicitudesAdministradorPaginador(int unidad, String numControl, String rfc,
                                                                            Paginador paginador);

    List<SolicitudAdministrarSolVO> buscarSolicitudesAdministradorFacultades(int unidad, String numempleado);

    List<SolicitudAdministrarSolVO> buscarSolicitudesFiscalizador(int unidad);

    List<DycpSolicitudDevolucionDTO> buscarFacturas(String rfcProveedor);

    void borrarFactura(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO);

    boolean insertaFactura(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO);

    boolean buscarNumFactura(String numFactura, String rfcProveedor);

    boolean existenFacturas(String rfcProveedor);

    // LADP --> Consulta Devolucion Simultaneas

    String consultaDevolucionSimultaneas(String rfc, int idConcepto, int idEjercicio, int idPeriodo);

    Integer obtenRFCBancario(String cuentaBancaria, String rfc);

    void createSolicitudTemp(DyctSolicitudTempDTO paramInputTO) throws SIATException;

    DyctSolicitudTempDTO findSolicitudTemp(int folio) throws SIATException;

    void deleteSolicitudTep(String folio);

    DycpSolicitudDTO encontrar(String numControl);
    
    DycpSolicitudDTO encontrar(String numControl, String rfc, Integer idSaldoIcep) throws SIATException;

    void actualizarResolucionAutomatica(Integer solAutomatic, String numControl) throws SIATException;

    boolean existeXId(String numControl) throws SIATException;

    boolean existeTramite(String rfc, int idTipoTramite) throws SIATException;

    List<DycpSolicitudDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep) throws SIATException;
    
    List<Map<String, Object>> obtenerNoControlNYVProcesado(int maximoNYVRegresar) throws SIATException;
    
    List<Map<String, Object>> obtenerNoControlParaHistorico(int maximoTramitesProcesar) throws SIATException;
    
    int actualizarStatusPago(Integer status, String numControl) throws SIATException;
    
    void actualizarIdEstadoSol(final int idEstadoSol, final String numControl) throws SIATException;
    
    void actualizarIdEstadoSolDic(final int idEstadoSol, final String numControl) throws SIATException;
}
