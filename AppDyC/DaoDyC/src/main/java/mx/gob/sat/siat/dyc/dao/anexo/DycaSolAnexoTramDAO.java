package mx.gob.sat.siat.dyc.dao.anexo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DycaSolAnexoTramDAO {
    
    /**
     * <html>
     * <body>
     * Consulta los diferentes anexos a los tramites. Para evitar el uso de reglas de negocio, se le tienen que enviar
     * la consulta para evitar reglas de negocio implementada dentro de la capa de negocio.
     * </body>
     * </html>
     *
     * @param query
     * @return
     * @throws SIATException
     */
    List<DycaSolAnexoTramDTO> listarTramites(String query) throws SIATException;
    
    /**
     * Una vez que han sido leidos los registros se procede a marcarlos como procesados
     *
     * @param numControl
     * @throws SIATException
     */
    void actualizarAProcesado(String numControl) throws SIATException;
}
