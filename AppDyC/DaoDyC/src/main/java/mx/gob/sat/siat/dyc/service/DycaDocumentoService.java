/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Interface de servicio para consulta de documentos adjuntos
 * @author Federico Chopin Gachuz
 *
 */
public interface DycaDocumentoService {
    
    void insertarDocumentoComentario(DyctArchivoDTO dyctArchivoDTO, Long idSequencia) throws SIATException;
    void actualizarDocumentoAprobacion(Integer numEmpleadoAprob, Long idDocumento) throws SIATException;

}
