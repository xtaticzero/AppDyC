/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.vistas.dao;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


/**
 * ManagedBean pantalla MantenerMatrizDictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Abril 16, 2014
 */
public interface SegbMovimientoDAO {

    void agregaIdentificador(SegbMovimientoDTO segbMovimiento) throws SIATException;

}
