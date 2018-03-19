/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.declaraciontemp;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author  Alfredo Ramirez
 * @since   16/07/2014
 */
public interface DyctServicioTempDAO {

    Integer obtenerFolio();

    void insertar(DyctServicioTempDTO dyctServicioTempDTO) throws SIATException;
    
    void actulizarFechaFinTemp(String folioAvisoTemp) throws SIATException;
}
