package mx.gob.sat.siat.dyc.dao.documento;

import java.sql.Date;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyctDocumentoVO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;

/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DyctDocumentoDAO {

    void insertar(DyctDocumentoDTO documento) throws SIATException;

    void actualizarDocumentoAprobacion(Long numEmpleadoAprob, Long idDocumento);

    void actualizarEstadoDoc(Integer idEstadoDoc, String numControlDoc);

    Integer actualizarEstadoreq(DyctDocumentoDTO documento) throws SIATException;

    void insertarE(DyctDocumentoDTO documento) throws SIATException;

    DyctDocumentoDTO consultarXNumControlDoc(String numControlDoc) throws SIATException;

    void actualizarStatusRequerimiento(Integer status, String numControlDoc) throws SIATException;

    void actualizarStatusYFirmaXidDocumentoRequerimiento(Integer status, String numControlDoc,
            Integer idTipoFirma) throws SIATException;

    int buscarAutorizacionTotalOParcial(String numControl, Integer idEstadoDoc, String ids) throws SIATException;

    int buscarResolucionLiquidacionDevolucion(String numControl, String ids) throws SIATException;

    int buscarDocumentoEnPlantillas(String numControlDoc, String ids) throws SIATException;

    void actualizarFechaInicioPlazoLegal(String numControlDoc, Date fechaInicioPlazoLegal) throws SIATException;

    void actualizarFechaFinPlazoLegal(String numControlDoc, Date fechaFinPlazoLegal) throws SIATException;

    List<DyctDocumentoDTO> selecXServicioTipodocumento(DycpServicioDTO servicio, DyccTipoDocumentoDTO tipoDocumento);

    List<DyctDocumentoDTO> ultimoDocumentoServicioTipoDoc(DyctDocumentoDTO documento);

    List<DyctDocumentoDTO> seleccionar();

    List<DyctDocumentoDTO> selecUnionCompensacion();

    List<DyctDocumentoDTO> selecXNumControl(String numControl);

    List<SolicitudAdministrarSolVO> selecXNumControlEstAprobado(String numControl);

    List<DyctDocumentoDTO> selecUnionCompensacionXEstadoDictaminador(DyccEstadoDocDTO estado,
            DyccDictaminadorDTO dictaminador);

    List<DycpCompensacionDTO> buscarDocumentoXEstadoCompensacion();

    List<DycpServicioDTO> buscarDocumentoXEstadoSolicitudes();

    Integer buscarDocumento(String nombreDoc, String numControl);

    void actualizarUrl(String numControl, String url);

    void actualizar(DyctDocumentoDTO documento) throws SIATException;

    DyctDocumentoDTO encontrar(String numControlDoc) throws SIATException;

    void actualizarDocumento(DyctDocumentoDTO documento) throws SIATException;

    void actualizarFolioNYV(String folioNYV, String numControlDoc) throws SIATException;

    void actualizarDocumentoAPdf(String nombreDocumentoPdf, String numControlDoc) throws SIATException;

    List<DyctDocumentoDTO> consultarDocumentoAprobador(DyccAprobadorDTO aprobador) throws SIATException;

    List<DyctDocumentoVO> buscarFolioNYV(int numeroRegistros, int intervalo) throws SIATException;

    DyctDocumentoDTO encontrarUltimoRegistroXNumControl(String numControl) throws SIATException;

    /**
     * Actualiza dentro del registro del documento la cadena original, sello
     * digital y firma digital.
     *
     * @param numControlDoc N&uacute;mero de control de documento a acutalizar.
     * @param cadena Cadena Original
     * @param sello Sello digital
     * @param firma Firma ditigal
     * @throws SIATException
     */
    void actualizarCadenaSelloYFirma(String numControlDoc, String cadena, String sello, String firma) throws SIATException;

    List<DyctDocumentoDTO> buscaDocumentosAprobador(Integer numempleadoAprob);

    void actualizarNumEmpleadoAprob(Integer numEmpleadoAprob, String numControlDoc) throws SIATException;

    Integer contarPlantillasGeneradas22o66(String numControl) throws SIATException;

    List<DyctDocumentoDTO> buscaDocumentosParaHistorico(Integer maximo) throws SIATException;

    List<DyctDocumentoDTO> consultaNumeroDoc(String numControl) throws SIATException;

    void reasignacionManualAprobador(String numControl, Integer numEmpleado, Integer empleadoResponsable) throws SIATException;

    DyctDocumentoDTO consultarDocumentoSolventarCtaClabe(String numControl) throws SIATException;

    boolean agregaMarcaFechaNotif(String folioNyV, Integer marcaFechaNotif) throws SIATException;

    void agregaMarcaFechaNotificacionByNumControlDoc(String numcontrolDoc, Integer marcaFechaNotif) throws SIATException;

    void actualizarFolioNYVFechaNoti(String numControlDoc, String numControl, String folio, String fecha) throws SIATException;

    void dyctDocumentoupdateIdEstadoReq(final int idEstadoReq, final String numControl) throws SIATException;

    void dyctDocumentoupdateIdEstadoReqDic(final int idEstadoReq, final String numControl) throws SIATException;
}
