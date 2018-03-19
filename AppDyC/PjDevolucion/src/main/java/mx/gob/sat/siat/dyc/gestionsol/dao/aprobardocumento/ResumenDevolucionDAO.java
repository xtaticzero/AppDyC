package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Dao para el Resumen de devolucion
 * @author Ericka Janeth Ibarra Ponce
 * @since 10/01/2014
 */
public interface ResumenDevolucionDAO {
    /**
     * Obtiene la información de los datos que aparecerán en pantalla en el resumen de la resolución.
     *
     * @param numControl
     * @param nombreDoc
     * @return
     * @throws SIATException
     */
    List<ResumenDevolucionDTO> buscarResumen(String numControl, String nombreDoc, boolean sinDoc) throws SIATException;
}
