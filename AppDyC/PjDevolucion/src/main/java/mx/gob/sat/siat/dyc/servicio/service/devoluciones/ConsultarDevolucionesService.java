/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.devoluciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 26/07/2013
 *
 */
public interface ConsultarDevolucionesService {

    List<ConsultarDevolucionesDTO> consultarDevoluciones(ConsultarDevolucionesDTO consultarDevolucionesDTO);
    
}

