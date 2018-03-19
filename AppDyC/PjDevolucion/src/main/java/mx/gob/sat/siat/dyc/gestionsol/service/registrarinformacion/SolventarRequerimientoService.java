/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.EstatusRequerimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author David Guerrero Reyes
 * @author Jesus Alfredo Hernandez Orozco
 * @since 19/11/2013
 */
public interface SolventarRequerimientoService {

    /**
     * Consulta el estatus en el que se encuentra el requerimiento.
     *
     * @param numeroControlDoc
     * @param numeroControl
     * @return
     * @throws SIATException
     */
    EstatusRequerimientoDTO consultaEstatusReq(String numeroControlDoc, String numeroControl) throws SIATException;

    /**
     * Consulta los documentos requeridos e inicializa la lista de los archivos
     * solventados
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    List<DocRequeridoDTO> consultaDocumentosReq(String numeroControl, String numControlDoc) throws SIATException;

    /**
     * Consulta los anexos requeridos para solventar el requerimiento e
     * inicializa las listas de los archivos solventados
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    List<AnexoRequeridoDTO> consultaAnexoReq(String numeroControl, String numControlDoc) throws SIATException;

    /**
     * Guarda las notas ingresadas por el contribuyente al adjuntar información
     * adicional a la solicitud o compensación.
     *
     * @param notasExpediente
     * @throws SIATException
     */
    void insertaComentarioSolventacion(NotaExpedienteDTO notasExpediente) throws SIATException;

    /**
     * Actualiza el requerimiento cuando este se encuentra vencido.
     *
     * @param idEstadoReq
     * @param numControl
     * @param numControlDoc
     * @throws SIATException
     */
    void actualizaRequerimientoVencido(Integer idEstadoReq, String numControl,
            String numControlDoc) throws SIATException;

    /**
     * Actualiza los siguientes datos: - El estado de la solicitud. - El
     * requerimiento vencido. - La informacion del requerimiento. - El
     * seguimiento. - Los comentarios para la solventacion
     *
     * @param
     * @throws SIATException
     */
    void actualizarInformacion(SolventacionRequerimientoVO objetoSolventar) throws SIATException;

    /**
     * Consulta los datos del contribuyente a traves del numero de control
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    DyctContribuyenteDTO encontrarContribuyente(String numControl) throws SIATException;

    /**
     * Consulta para obtener el documento a solventar, para sustitución de
     * Cuenta Clabe
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    DyctDocumentoDTO consultaDocCuentaClabeXSolventar(String numControl) throws SIATException;
}
