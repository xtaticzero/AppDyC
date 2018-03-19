/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.regsolicitud;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;


/**
 * Clase Interface DAO para la tabla []
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 24, 2013
 * @since 0.1
 *
 * */
public interface DyccEstadoSolDAO {

    /**
     * @return Lista ArrayList [DYCC_ESTADOSOL]
     * @throws Exception
     */
    List<DyccEstadoSolDTO> obtenerLista();

}
