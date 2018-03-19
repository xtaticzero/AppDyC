package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento;

import java.math.BigDecimal;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.CatalogoAprobadorDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 12/01/2014
 *
 * */
public interface ComentarioService {
    /**
     * Metodo que se utiliza para buscar los aprobadores que son de mayor rago al escalar
     *
     * @param cveADM clave de administracion desconcentrada
     * @param numEmpleado numero de empleado del aprobador que va a escalar la solicitud
     * @param claveNivel Es el puesto o nivel que tene el empleado (mientras mas bajo sea el numero, mayor es el rango)
     * @param centroCostoOP Es el centro de costos en el cual esta trabajando el empleado actualmente
     * @return lista de aprobadores de mayor rango en comparacion del que escala
     * @throws SIATException
     */
    List<CatalogoAprobadorDTO> buscarAprobador(int cveADM, String numEmpleado, Integer claveNivel, Integer centroCostoOP) throws SIATException;
    List<DyccMatrizDocVO> buscarMatrizRRSer() throws SIATException;
    List<DyccTipoTramiteDTO> obtenerTipoTramite(String numControlDoc);    
    void actualizadDocuentoReqS(Integer numEmpleado, String numControlDoc) throws SIATException;
    void agregarTarea(DyctDocumentoDTO documento,BandejaDocumentosDTO andejaDoc);
    void registrarPistaAuditoria(PistaAuditoriaVO pistasAuditoria);
    void aprobarDocumento(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException;
    void guardarFirma(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException;
    
    /**
     * Se utiliza cuando el documento es escalado por el aprobador, inserta datos en bitacora y registra en pistas
     * de auditoria.
     *
     * @param aprobarDocumentoDTO
     * @throws SIATException
     */
    void guardarTipoOpcEscalar(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException;
    void guardarDatosFiel(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException;
    DyccAprobadorDTO buscarAprobador(Integer numEmpleado);
    void insertaSeguimiento(SeguimientoDTO seguimientoDTO) throws SIATException;
    DycvEmpleadoDTO consultarXIDEmpleado(Integer idEmpleado) throws SIATException;
    
    /**
     * Busca si la firma autógrafa esta activada o no en el sistema.
     *
     * @return objeto con los datos del estado de la firma
     * @throws SIATException
     */
    DyccParametrosAppDTO consultarEstatusDeFirmaAutografa() throws SIATException;
    
     /**
      * <html><body>
      * Obtiene el monto que tiene permitido para aprobar un aprobador a partir de su nivel y la clave de administraci&oacute;n a la que
      * pertenece
      * </body></html>
      * 
      * @param claveNivel Nivel del empleado que pretende aprobar.
      * @param claveADM Clave de administracion a la que pertenece el empleado.
      * @return Lista de objetos con los valores encontrados.
      */
    BigDecimal consultarMontosLimitesPorNivel(Integer claveADM, Integer claveNivel, Integer devolucion) throws SIATException;
    
    void insertarSeguimientoSinDocumento(SeguimientoDTO seguimientoDTO, PistaAuditoriaVO pistaAuditoria) throws SIATException;
}
