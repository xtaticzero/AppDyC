package mx.gob.sat.siat.dyc.dao.matriz;

import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;


/**
 * @author Ericka Janeth Ibarra Ponce
 * @date Febreo 04, 2014
 *
 * */
public interface DyccMatrizDocDAO {
    DyccMatrizDocVO buscarMatrizDoc(int idPlantilla) throws SIATException;
    DyccMatDocumentosDTO consultarMatrizConTipoPlantilla(int idPlantilla) throws SIATException;
    DyccMatrizDocVO consultarDocumentoRequerimientoXIdDocumentoReq(Long idDocumentoReq) throws SIATException;
}
