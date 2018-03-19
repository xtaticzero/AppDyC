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

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.MatrizDictaminadorVO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;


/**
 * Implementaci&oacute;n DAO para Mantener Matriz Dictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 5, 2013
 * @since 0.1
 *
 * */
public interface MatrizDictaminadoresDAO {

    /**
     * @return Lista ArrayList [MatrizDictaminadorVO]
     * @throws Exception
     */
    List<MatrizDictaminadorVO> obtenerListaDictaminadores(AdmcUnidadAdmvaDTO admva) throws SQLException;

    MatrizDictaminadorVO buscaDictaminador(MatrizDictaminadorVO dictaminadorDTO) throws SQLException;

    /**
     * @param dictaminador
     * @throws Exception
     */
    void insertarDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException;

    /**     void actualizarDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException;

    void comisionarDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException; */

    /**
     * @param dictaminador
     * @throws Exception
     */
    void terminarComisionDictaminador(MatrizDictaminadorVO dictaminador) throws SQLException;

}
