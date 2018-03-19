/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.anexo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 * */
public interface DyctAnexoReqDAO {

    void insertar(List<DyctAnexoReqDTO> lDyctAnexoReqDTO) throws SIATException;

    void insertar(DyctAnexoReqDTO anexoReq) throws SIATException;

    List<DyctAnexoReqDTO> selecXReqinfo(DyctReqInfoDTO reqInfo);
    
    Integer buscaArchivoEnAnexoReq(String numControl, String archivo);
    
    void actualizarUrl(String numControl, String url);
    
    List<DyctAnexoReqDTO> buscarDocsAnexosRequeridos(String numControl) throws SIATException;
    
    /**
     * <html>
     * <body>
     * Se utiliza para realizar cualquier consulta, la ventaja de utilizar este mÃ©todo es que se puede ejecutar
     * cualquier query bajo cualquier criterio siempre y cuando no se necesite enviarle parÃ¡metros.
     * </body>
     * </html>
     *
     * @param query
     * @return
     * @throws SIATException
     */
    List<DyctAnexoReqDTO> consultar(String query) throws SIATException;
    
    /**
     * Una vez que han sido leidos los registros se procede a marcarlos como procesados
     *
     * @param numControlDoc
     * @throws SIATException
     */
    void actualizarAProcesado(String numControlDoc) throws SIATException;
    
}
