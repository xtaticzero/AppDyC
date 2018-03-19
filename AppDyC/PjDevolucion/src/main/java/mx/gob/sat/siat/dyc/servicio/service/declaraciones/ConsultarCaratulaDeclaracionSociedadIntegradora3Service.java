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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
public interface ConsultarCaratulaDeclaracionSociedadIntegradora3Service {

    List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO);

    List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> consultaDatosForma3(ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO);

    List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO);

    List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO consultarCaratulaDeclaracionSociedadIntegradora3RegDTO);

}
