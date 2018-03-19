/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctCasoPendienteDAO {
    
    List<DyctCasoPendienteDTO> obtenerDeclaraciones();
    
    void insertar(DyctCasoPendienteDTO dyctCasoPendienteDTO) throws SIATException;
    
    void actualizar(Integer idDeclaracion);
    
    Integer buscarCasoPendiente(DyctCasoPendienteDTO dyctCasoPendienteDTO) throws SIATException;
}
