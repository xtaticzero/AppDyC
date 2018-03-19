package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 12/01/2014
 *
 * */

public interface BandejaDocumentosService {

    /**
     * Consulta la los datos que irán mostrados en la lista de documentos pendientes de aprobar por parte del dictaminador.
     *
     * @param numEmpleado
     * @param numControl
     * @param rfc
     * @param paginador
     * @return
     * @throws SIATException
     */
    List<BandejaDocumentosDTO> buscarBandejaDoc ( String numEmpleado, String numControl, String rfc, Paginador paginador ) throws SIATException;
    Long countBuscarBandejaDoc ( String numEmpleado, String numControl, String rfc ) throws SIATException;
    
    List<BandejaDocumentosDTO> buscarBandejaSivadMorsa ( int idPlantilla, String numControl, String rfc, Paginador paginador, String claveADM ) throws SIATException;
    Long countBuscarBandejaSivadMorsa ( int idPlantilla, String numControl, String rfc, String claveADM ) throws SIATException;
}
