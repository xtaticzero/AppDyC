/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.altex;

import java.util.List;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.xml.sax.SAXException;


/**
 *
 * @author Alfredo Ramirez
 * @since 25/07/2013
 *
 */
public interface ConsultarContribuyenteAltamenteExportadorService {

    List<ConsultarContribuyenteAltamenteExportadorDTO> consultarContribuyenteAltamenteExportador(ConsultarContribuyenteAltamenteExportadorDTO consultarContribuyenteAltamenteExportadorDto);

    /**
     * @param spConsultarAltexDTO
     */
    SpConsultarAltexDTO obtieneDatosAltexSP(SpConsultarAltexDTO spConsultarAltexDTO) throws SIATException;

    byte[] obtieneDatosAltexXMLByteArray(SpConsultarAltexDTO spConsultarAltexDTO) throws JAXBException, SAXException,
                                                                                         SIATException;

}
