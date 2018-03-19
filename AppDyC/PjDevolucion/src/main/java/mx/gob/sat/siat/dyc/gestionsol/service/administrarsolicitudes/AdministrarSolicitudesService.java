/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DocumentoAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 11, 2013
 *
 * */
public interface AdministrarSolicitudesService {
    
    AdministrarSolicitudesVO obtenerDiferencia(String numControlDoc);
    Integer obtenerDiasHabiles(Date fNotificacion, Date fSolventacion);   
    Integer obtenerIdEstadoDocumento(String numControl);
    void actualizarEstados(String numControl, String numControlDoc, boolean esActualizable);
    void actualizarEstadosComp(String numControl, String numControlDoc);
    Integer obtenerEstadoReq(String idDocReq);
    Integer obtenerNumeroReq(String idDocReq);    
    Date obtenerFechaSolventacion(String idDocReq);    
    void insertarInformacion(List<DyctInfoRequeridaDTO> lInformacion, List<DyctAnexoReqDTO> lAnexos, List<DyctOtraInfoReqDTO> lOtros, DyctDocumentoDTO dyctDocumentoDTO, DyctReqInfoDTO dyctReqInfoDTO, String usuarioPistasAuditoria, Integer opcionCombo) throws SIATException;
    Long getIdDocumento();
    void insertarDocumentoAprobacion(DyctDocumentoDTO dyctDocumentoDTO, String nombreCompleto, Integer opcionCombo) throws SIATException;
    void actualizarDocumentoAprobacion(Long numEmpleadoAprob, Long idDocumento);
    List<SeguimientoAdministrarSolVO> selecXServicio(DycpServicioDTO servicio);
    String obtenerNumControlDoc(String numControl);
    List<String> obtenerNumControlListDocs(String numControl);
    List<DocumentoAdministrarSolVO> buscarDocsDictaminador(String numEmpleado);
    void actualizarDocumento(String numControlDoc, Integer numEmp) throws SIATException;
    void iniciarFlujoFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException;  
    void actualizarEstadoSolicitud(String numControl, Integer estadoSolicitud) throws SIATException;
    void accionAprobarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException;
    void accionFinalizarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO,  DyctArchivoDTO dyctArchivoDTO) throws SIATException;
    Integer obtenerDiasFacultades(String numControl, long idFacultades) throws SIATException; 
    Integer verificarExistenciaInicioFacultades(String numControl) throws SIATException;
    
    /**
     * Actualiza el estado del requerimiento a vencido(4).
     * @param numControlDoc Numero de control del documento.
     * @throws SIATException Excepcion si ocurre akgun error al realizar la actualizacion.
     */
    void actualizarEstadoReq(String numControlDoc) throws SIATException;
    
    /**
     * Consulta si el numero de control recibido ya cuenta con una resolucion.
     * @param numControl Numero de control.
     * @param isCompensacion True si el tipo de tramite es compensacion en caso contrario False.
     * @return True si existe resolucion en caso contrario se retorna False.
     * @throws SIATException Lanza excepcion de tipo SIATException si existe error al consultar la resolucion.
     */
    boolean existeResolucion(String numControl, boolean isCompensacion) throws SIATException;
    
    /**
     * Actualiza el estado de la compensacion a desisitido (id 7), actuaiza estado del requerimiento
     * a vendico (id 4).
     * @param numControl Numero de control de la compensacion.
     * @param numControlDoc Numero de control del documento.
     * @throws SIATException Se lanza excepcion si no se actualiza la compensacion de manera correcta.
     */
    void actualizarEdoCompDesistido(String numControl, String numControlDoc, boolean esActualizable) throws SIATException;
    
}
