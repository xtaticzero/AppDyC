/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.creditosfiscales;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 25/07/2013
 *
 */
public interface ConsultarDetalleCreditosFiscalesService {

    List<ConsultarDetalleCreditosFiscalesDTO> consultaDetalleCreditosFiscales(ConsultarDetalleCreditosFiscalesDTO consultarDetalleCreditosFiscalesDto);

}
