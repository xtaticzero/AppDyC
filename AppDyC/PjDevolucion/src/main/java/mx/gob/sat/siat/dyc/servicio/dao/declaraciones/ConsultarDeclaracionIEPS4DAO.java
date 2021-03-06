package mx.gob.sat.siat.dyc.servicio.dao.declaraciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIEPS4DTO;


/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/


/**
 *
 * @author Israel Chàvez
 * @since 13/08/2013
 *
 */
public interface ConsultarDeclaracionIEPS4DAO {


    List<ConsultarDeclaracionIEPS4DTO> consultarDeclaracionIEPS4(ConsultarDeclaracionIEPS4DTO consultarDeclaracionIEPS4DTO);

}
