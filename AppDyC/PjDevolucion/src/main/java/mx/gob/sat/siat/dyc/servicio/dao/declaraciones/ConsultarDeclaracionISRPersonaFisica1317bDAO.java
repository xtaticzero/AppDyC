/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   06/08/2013
 *
 */
public interface ConsultarDeclaracionISRPersonaFisica1317bDAO {

    List<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO> consultaConsultaDeclaracionesIsrAnexo5a2(ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO declaracionISRPersonaFisica1317bAnexo5a2DTO);

    List<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO> consultaconsultaConsultaDeclaracionesIsrAnexo1(ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO declaracionISRPersonaFisica1317bAnexo1DTO);

}
