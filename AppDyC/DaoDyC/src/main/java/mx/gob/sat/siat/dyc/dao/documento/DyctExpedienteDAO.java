package mx.gob.sat.siat.dyc.dao.documento;

import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface DyctExpedienteDAO {
    int encontrar (String numControl);
    
    void actualizaManifiestaXNumCtrl(Integer manifest, String numCtrl) throws SIATException;
}
