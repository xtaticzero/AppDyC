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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
public interface ConsultarCaratulaDeclaracionSociedadControladora2AService {

    List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO consultarCaratulaDeclaracionSociedadControladora2RenglonDTO);

    List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> consultaDatosForma2(ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO consultarCaratulaDeclaracionSociedadControladora2AForma2DTO);

    List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO);

    List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO);

}
