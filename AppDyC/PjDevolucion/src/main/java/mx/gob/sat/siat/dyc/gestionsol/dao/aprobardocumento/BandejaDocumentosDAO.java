package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Dao para el Bandeja de Documentos
 * @author Ericka Janeth Ibarra Ponce
 * @since 10/01/2014
 */
public interface BandejaDocumentosDAO {
    List<BandejaDocumentosDTO> buscarBandejaDoc(String numEmpleado, String numControl, String rfc, Paginador paginador) throws SIATException;
    Long countBuscarBandejaDoc( String numEmpleado, String numControl, String rfc ) throws SIATException;    
    List<BandejaDocumentosDTO> buscarBandejaSivadMorsa ( int idPlantilla, String numControl, String rfc, Paginador paginador, String claveADM ) throws SIATException;
    Long countBuscarBandejaSivadMorsa ( int idPlantilla, String numControl, String rfc, String claveADM ) throws SIATException;
}
