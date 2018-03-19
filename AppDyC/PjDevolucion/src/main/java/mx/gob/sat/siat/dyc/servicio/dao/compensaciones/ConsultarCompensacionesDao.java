/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.compensaciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;


/**
 * TODO
 * @author Israel Chavez
 */
public interface ConsultarCompensacionesDao {

    List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechas(ConsultarCompensacionesFechasDTO consultarCompensacionesFechasDto);

    List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacionCompensaciones(ConsultarCompensacionesInformacionDTO consultarCompensacionesFechasDto);

}


