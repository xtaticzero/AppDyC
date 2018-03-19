/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.compensaciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 18/07/2013
 *
 */
public interface ConsultarCompensacionesService {

    List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechas(ConsultarCompensacionesFechasDTO consultarCompensacionesFechasDto);

    List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacion(ConsultarCompensacionesInformacionDTO consultarCompensacionesInformacionDto);

}
