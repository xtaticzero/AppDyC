/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Interface DAO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public interface EmpleadoDAO {

    EmpleadoVO consultaInformacionEmpleado(EmpleadoVO empleado) throws SIATException;
    EmpleadoVO consultaInfoEmpleadoSinAdm(EmpleadoVO empleado) throws SIATException;

    List<EmpleadoVO> consultaNivelDireccion() throws SIATException;

}
