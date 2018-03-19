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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   06/08/2013
 *
 */
public interface ConsultarDeclaracionISRPersonaFisica1317cService {

    List<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO> consultaDeclaracionesIsrForma13(ConsultarDeclaracionISRPersonaFisica1317cForma13DTO declaracionISRPersonaFisica1317cForma13DTO);

    List<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO> consultaDeclaracionesIsrForma13a(ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO declaracionISRPersonaFisica1317cForma13aDTO);

    List<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO> consultaDeclaracionesIsrFormaDid(ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO declaracionISRPersonaFisica1317cForma13DidDTO);

}
