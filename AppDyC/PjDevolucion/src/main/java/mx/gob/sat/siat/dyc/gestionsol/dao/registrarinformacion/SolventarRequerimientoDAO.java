/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaCompensacionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaSolicitudDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.EstatusRequerimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author David Guerrero Reyes
 * @author Jesus Alfredo Hernandez Orozco
 * @since 19/11/2013
 */
public interface SolventarRequerimientoDAO {

    EstatusRequerimientoDTO consultaEstatusReq(String numeroControlDoc, String numeroControl);

    /**
     * Consulta los documentos requeridos que son necesarios para solventar el
     * requerimiento.
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    List<DocRequeridoDTO> consultaDocumentosReq(String numeroControl, String numControlDoc) throws SIATException;

    /**
     * Consulta los anexos requeridos necesarios para solventar el
     * requerimiento.
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    List<AnexoRequeridoDTO> consultaAnexoReq(String numeroControl, String numControlDoc) throws SIATException;

    /**
     * Genera un id de documento al momento de solventar el requerimiento por
     * cada uno de los documentos que se suben.
     *
     * @return
     */
    int getIdDocumento() throws SIATException;

    /**
     * Genera un ID de cocumento para cada documento que es anexado al momento
     * de solventar una devolución.
     *
     * @return
     * @throws SIATException
     */
    int getIdAnexo() throws SIATException;

    /**
     * Actualiza el nombre y la ruta del archivo anexo a partir de: - El número
     * de control. - El id del anexo. - El id del tipo de trámite.
     *
     * @param dyctAnexoReqDTO
     * @throws SIATException
     */
    void actualizaAnexo(DyctAnexoReqDTO dyctAnexoReqDTO) throws SIATException;

    /**
     * Actualiza el estado de la solicitud
     *
     * @param isEstado
     * @param numControl
     * @throws SIATException
     */
    void actualizaSolicitud(Integer isEstado, String numControl) throws SIATException;

    /**
     * Actualiza la compensación al momento de ser solventada.
     *
     * @param isEstado
     * @param numControl
     * @throws SIATException
     */
    void actualizaCompensacion(Integer isEstado, String numControl) throws SIATException;

    /**
     * Asigna la fecha de solventación
     *
     * @param numControlDoc
     * @throws SIATException
     */
    void actualizaReqInfo(String numControlDoc) throws SIATException;

    /**
     * Inserta los registros en bitácora.
     *
     * @param seguimientoDTO
     * @throws SIATException
     */
    void insertaSeguimiento(SeguimientoDTO seguimientoDTO) throws SIATException;

    /**
     * Inserta las notas registradas por el contribuyente en BD
     *
     * @param notaExpediente
     * @throws SIATException
     */
    void insertaComentarioSolventacion(NotaExpedienteDTO notaExpediente) throws SIATException;

    /**
     * Actualiza el estado del requerimiento que se encuentra en la tabla del
     * documento
     *
     * @param idEstadoReq
     * @param numControl
     * @param numControlDoc
     * @throws SIATException
     */
    void actualizaRequerimientoVencido(Integer idEstadoReq, String numControl,
            String numControlDoc) throws SIATException;

    /**
     * Obtiene el numero maximo de documento que se debe de insertar en
     * dyct_otraarchivo
     *
     * @param idOtraInfoReq
     * @return
     * @throws SIATException
     */
    Integer retornarNumeroDeArchivo(Integer idOtraInfoReq) throws SIATException;

    /**
     * Consulta los datos de una compensacion para generar una cadena original
     * al solventar un requerimiento.
     *
     * @param numControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    CadenaCompensacionDTO consultarDatosDeCadenaDeUnaCompensacion(String numControl, String numControlDoc) throws SIATException;

    /**
     * Consulta los datos de una solicitud para generar una cadena original al
     * solventar un requerimiento.
     *
     * @param numControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    CadenaSolicitudDTO consultarDatosDeCadenaDeUnaSolicitud(String numControl, String numControlDoc) throws SIATException;

    /**
     * Actualiza la cadena original y el sello digital
     *
     * @param numControlDoc
     * @param cadenaOriginal
     * @param selloDigital
     * @throws SIATException
     */
    void actualizaCadenaYSello(String numControlDoc, String cadenaOriginal, String selloDigital) throws SIATException;

}
