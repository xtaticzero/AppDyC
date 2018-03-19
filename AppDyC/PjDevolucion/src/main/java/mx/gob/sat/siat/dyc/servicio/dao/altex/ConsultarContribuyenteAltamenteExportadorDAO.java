/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.altex;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * TODO
 * @author Israel Chavez
 * @since 23/07/2013
 *
 */
public interface  ConsultarContribuyenteAltamenteExportadorDAO {
    
    List<ConsultarContribuyenteAltamenteExportadorDTO> consultarContribuyenteAltamenteExportador(ConsultarContribuyenteAltamenteExportadorDTO consultarContribuyenteAltamenteExportadorDTO);
    
    SpConsultarAltexDTO obtieneDatosAltexSP(SpConsultarAltexDTO spConsultarAltexDTO) throws SIATException ;

}
