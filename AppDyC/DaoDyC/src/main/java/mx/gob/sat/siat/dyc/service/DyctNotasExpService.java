/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 07, 2013
 */
public interface DyctNotasExpService {
    
    void insertarNota(DyctNotaDTO dyctNotaDTO) throws SIATException; 
    List<DyctNotaDTO> buscarNotasXNumControl(String numControl);
    
}
