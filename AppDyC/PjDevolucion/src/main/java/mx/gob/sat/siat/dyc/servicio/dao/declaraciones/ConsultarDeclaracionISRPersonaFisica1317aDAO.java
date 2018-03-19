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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aDecDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
public interface ConsultarDeclaracionISRPersonaFisica1317aDAO {
    List<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO> consultaImpuestosIsrforma13ane(ConsultarDeclaracionISRPersonaFisica1317aForma13DTO declaracionISRPersonaFisica1317aForma13DTO);

    List<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO> consultaImpuestosIsrforma13aane(ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO declaracionISRPersonaFisica1317aForma13aDTO);

    List<ConsultarDeclaracionISRPersonaFisica1317aDecDTO> consultaImpuestosIsrdddecdida1dd1(ConsultarDeclaracionISRPersonaFisica1317aDecDTO declaracionISRPersonaFisica1317aDecDTO);
}
