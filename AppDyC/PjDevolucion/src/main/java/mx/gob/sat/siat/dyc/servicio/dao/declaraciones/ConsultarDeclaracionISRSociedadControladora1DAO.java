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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora2DTO;


/**
 *
 * @author Israel Chàvez
 * @since 06/08/2013
 *
 */
public interface ConsultarDeclaracionISRSociedadControladora1DAO {

    List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1(ConsultarDeclaracionISRSociedadControladora1DTO declaracionISRSociedadControladora1DTO);

    List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2(ConsultarDeclaracionISRSociedadControladora2DTO declaracionISRSociedadControladora2DTO);

}

