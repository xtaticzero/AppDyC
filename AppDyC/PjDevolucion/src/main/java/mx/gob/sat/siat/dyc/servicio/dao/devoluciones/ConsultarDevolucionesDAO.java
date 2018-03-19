/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.devoluciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;


/**
 * TODO
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
public interface  ConsultarDevolucionesDAO {

    List<ConsultarDevolucionesDTO> consultarDevoluciones(ConsultarDevolucionesDTO consultarDevolucionesDTO);

}

