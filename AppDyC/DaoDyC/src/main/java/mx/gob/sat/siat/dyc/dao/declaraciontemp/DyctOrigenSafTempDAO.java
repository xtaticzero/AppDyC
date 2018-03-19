/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.declaraciontemp;

import mx.gob.sat.siat.dyc.domain.compensacion.DyctOrigenSafTempDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author  Alfredo Ramirez
 * @since   16/07/2014
 */
public interface DyctOrigenSafTempDAO {

    void insertar(DyctOrigenSafTempDTO dyctOrigenSafTempDTO) throws SIATException;

}
