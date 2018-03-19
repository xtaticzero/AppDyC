/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.emitirresolucion;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpCredFisDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 25, 2014
 *
 * */
public interface EmitirResolucionDAO {
    
    void insertarResolucion(DyctResolucionDTO dyctResolucionDTO) throws SIATException;
    
    void actualizarResolucion(DyctResolucionDTO dyctResolucionDTO) throws SIATException;
    
    Integer buscarMotivoPadre(Integer idMotivo) throws SIATException;
    
    String buscarDescripcionMotivoPadre(Integer idMotivo) throws SIATException;
    
    String buscarDescripcionMotivoDesistida(Integer idMotivo) throws SIATException;
    
    void actualizarExpediente(DyctExpCredFisDTO expediente) throws SIATException;
    
    void insertarExpediente(DyctExpCredFisDTO expediente) throws SIATException;
    
}
