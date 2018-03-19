/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.sindocumento;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolSinDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author root
 */
public interface DyctResolSinDocumentoDAO {
    
    void insertarResolucionSinDoc(DyctResolSinDocumentoDTO resolucion) throws SIATException;
    
    void aprobarResolucionSinDoc(String numControl, Integer idEstadoReq) throws SIATException;
  
    void updateSinDocumento(Integer numAprobador, String numControl, Integer idEstdoDocumento) throws SIATException; 
    
}
