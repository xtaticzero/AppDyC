/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctOrigenAvisoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author Alfredo Ramirez
 * @since  04/07/2014
 *
 *
 *
 *
 */
public interface DyctOrigenAvisoDAO {

    void insertar(String numControl, DyctOrigenAvisoDTO dyctOrigenAvisoDTO) throws SIATException;

}
