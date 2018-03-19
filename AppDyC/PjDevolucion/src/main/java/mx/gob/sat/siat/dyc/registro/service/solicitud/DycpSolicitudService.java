/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/

package mx.gob.sat.siat.dyc.registro.service.solicitud;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevTempVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.util.common.AsignarException;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DycpSolicitudDevolucionDTO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;


/**
 * Interface de servicio para consulta de tramite
 *
 * @date Octubre 3, 2013
 *
 */
public interface DycpSolicitudService {

    SolicitudConsultarExpedienteVO buscarNumcontrol(String numControl);

    SolicitudConsultarExpedienteVO buscarNumcontrolComp(String numControl);

    ParamOutputTO<DycpSolicitudDTO> insertaSolicitud(TramiteDTO input, String sello,
                                                     String cadena) throws SIATException, AsignarException;
    
    ParamOutputTO<DycpSolicitudDTO> insertaSolicitudSinIcept(TramiteDTO input, String sello,
                                                     String cadena) throws SIATException, AsignarException;
    
    ParamOutputTO<DycpSolicitudDTO> insertaSolicitudISR(TramiteDTO input, String sello,
                                                     String cadena) throws SIATException, AsignarException;

    void insertaSolicitudTemporal(TramiteDTO input) throws SIATException;

    Long countSolicitudesDictaminador(String numEmpleado, String estado, String numControl, String rfc);

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

    // LADP --> Consulta Devoluciones Simultaneas

    String consultaDevolucionSimultaneas(String rfc, int idImpuesto, int idConcepto, int idEjercicio, int idPeriodo);

    Integer obtenRFCBancario(String cuentaBancaria, String rfc);

    // LADP

    List<DycpSolicitudDevolucionDTO> buscarFacturas(String rfcProveedor);

    boolean insertaFactura(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO);

    boolean buscarNumFactura(String numFactura, String rfcProveedor) throws SIATException;

    ParamOutputTO<SolicitudDevTempVO> completarSolicitudTemp(SolicitudDevolucionRegistroVO pramInput) throws SIATException;

    boolean existenFacturas(String numControl, String rfcProveedor) throws SIATException;

    List<DyctFacturaReqDTO> buscarFacturas(String numControl, String rfcProveedor) throws SIATException;

    boolean borrarFactura(String numFactura);

    boolean insertarFacturas(DyctDocumentoDTO dyctDocumentoReqDTO, String usuarioSolvento,
                             List<DyctFacturaReqDTO> facturas, String rfcProveedor,
                             String nombreCompleto) throws SIATException;
    
    int actualizarStatus(Integer status, String numControl) throws SIATException;
    
    int actualizarStatusPago(Integer status, String numControl) throws SIATException;
    
    void actualizarIdEstadoSol(final int idEstadoSol, final String numControl, final String numDoc, final int idEstadoReq)throws SIATException;
    
    void actualizarIdEstadoSolDic(final int idEstadoSol, final String numControlDictaminar,final int idEstadoReq)throws SIATException;
}
