/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;


/**
 * @author  Alfredo Ramirez
 * @since   21/11/2013
 */
public interface DyccTipoAvisoService {

    List<DyccTipoAvisoDTO> obtenerTiposDeAviso();

}
