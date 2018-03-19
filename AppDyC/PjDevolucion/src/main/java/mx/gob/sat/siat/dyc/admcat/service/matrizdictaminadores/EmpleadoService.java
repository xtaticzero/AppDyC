/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;


/**
 * Interface Service para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public interface EmpleadoService {

    EmpleadoVO consultaInformacionEmpleado(EmpleadoVO empleado);
    
    EmpleadoVO consultaInfoEmpleadoSinAdm(EmpleadoVO empleado);

    List<EmpleadoVO> consultaNivelDireccion();

}
