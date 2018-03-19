/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DocumentoAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;


/**
 * Interface DAO para ECU Administrar Solicitudes
 * @author Federico Chopin Gachuz
 *
 * */
public interface AdministrarSolicitudesDAO {
    
    AdministrarSolicitudesVO obtenerDiferencia(String numControlDoc);
    Integer obtenerDiasHabiles(Date fNotificacion, Date fSolventacion);  
    Integer obtenerIdEstadoDocumento(String numControl);
    void actualizarEstados(String numControl, String numControlDoc, boolean esActualizable);
    void actualizarEstadosComp(String numControl, String numControlDoc);
    Integer obtenerEstadoReq(String idDocReq);
    Integer obtenerNumeroReq(String idDocReq);
    Date obtenerFechaSolventacion(String idDocReq);
    Long getIdDocumento();
    List<SeguimientoAdministrarSolVO> selecXServicio(DycpServicioDTO servicio);
    String obtenerNumControlDoc(String numControl);
    List<String> obtenerNumControlListDocs(String numControl);
    List<DocumentoAdministrarSolVO> buscarDocsDictaminador(String numEmpleado);
    void actualizarDocumento(String numControlDoc, Integer numEmp) throws SIATException;
    List<DyctSeguimientoDTO> obtenerIdentificadores(DyctDocumentoDTO dyctDocumentoDTO);
    void iniciarFlujoFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException;
    void actualizarEstadoSolicitud(String numControl, Integer estadoSolicitud) throws SIATException;
    void actualizarFechaFinFacultades(String numControl) throws SIATException;
    void accionAprobarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException;
    void accionFinalizarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException;
    Integer obtenerDiasFacultades(String numControl, long idFacultades) throws SIATException;  
    Integer verificarExistenciaInicioFacultades(String numControl) throws SIATException;
    
    /**
     * Metodo que actualiza el estado de un requerimeinto cuando no se solvento a tiempo. 
     * Actualiza a estado 4(Vencido).
     * @param numControlDoc Numero de control del documento.
     * @throws SIATException se lanza excepcion si existe error al ejecutar la actualizacion.
     */
    void actualizarEstadoReq(String numControlDoc) throws SIATException;
    
    /**
     * Actualiza el estado de la compensacion a desistido (id 7) y actualiza el estado del requerimiento a 
     * vencido (id 4).
     * @param numControl Numero de control de la compensacion.
     * @param numControlDoc Numero de control del documento.
     * @throws SIATException Se lanza excepcion si no se actualiza la compensacion de manera correcta
     */
    void actualizarEdoCompDesistido(String numControl, String numControlDoc, boolean esActualizable) throws SIATException;
}
