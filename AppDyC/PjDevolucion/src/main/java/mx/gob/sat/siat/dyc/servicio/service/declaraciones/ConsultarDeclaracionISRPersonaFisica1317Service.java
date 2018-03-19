/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.declaraciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   06/08/2013
 *
 */
public interface ConsultarDeclaracionISRPersonaFisica1317Service {

    List<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO> consultaDeclaracionesIsrForma13Declarasat(ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO declaracionISRPersonaFisica1317DeclarasatDTO);

    List<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO> consultaDeclaracionesIsrForma13(ConsultarDeclaracionISRPersonaFisica1317Forma13DTO declaracionISRPersonaFisica1317Forma13DTO);

    List<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO> consultaDeclaracionesIsrForma13a(ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO declaracionISRPersonaFisica1317Forma13aDTO);

}
