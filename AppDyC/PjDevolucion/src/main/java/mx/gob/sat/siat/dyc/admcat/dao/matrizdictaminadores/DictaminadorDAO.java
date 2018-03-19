/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DictaminadorVO;


/**
 * Implementaci&oacute;n DAO para Mantener Matriz Dictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Julio 29, 2014
 * @since 0.1
 *
 * */
public interface DictaminadorDAO {

    boolean dictActivo(DictaminadorVO dictaminadorCmb) throws SQLException;

    DictaminadorVO dictAdscripcion(DictaminadorVO dictaminadorCmb) throws SQLException;

}
