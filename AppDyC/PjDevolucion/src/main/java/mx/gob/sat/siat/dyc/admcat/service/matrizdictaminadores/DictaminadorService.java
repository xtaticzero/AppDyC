/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores;


import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;


/**
 * Service para modulo MantenerMatrizDictaminadores
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Julio 29, 2014
 * @since 0.1
 *
 * */
public interface DictaminadorService {

    List<DictaminadorVO> obtenerDictaminadores ( DictaminadorVO dictaminador ) throws SQLException;
    List<DictaminadorVO> obtenerDictaminadoresActivos ( DictaminadorVO dictaminador ) throws SQLException;

    /**
     * @param empleado
     * @param cCostosUsrSession
     * @param isNum
     * @param claveAdmon
     * @return
     */
    EmpleadoVO consultarInformacionEmpleado(EmpleadoVO empleado, Integer cCostosUsrSession, boolean isNum, Integer claveAdmon);

    void insertarDictaminador(EmpleadoVO empleado);

    /**
     * @param dictaminador 
     */
    void actualizarDictaminador(DictaminadorVO dictaminador);

    void eliminarDictaminador(DictaminadorVO dictaminador);

    void consultaDictaminadorPA(Map<String, String> remplaceMensajes);

    boolean inactivarComisionarRNFDC615(DyccDictaminadorDTO dic, boolean baja);

    /**
     * @return
     */
    String obtenerMensaje();

    String obtenerMensaje(Integer msg);
    
    DyccDictaminadorDTO encontrarActivo(Integer numEmpleado);
    
    DictaminadorVO obtenerDictaminador ( Integer numeroEmpleadoDictaminador );
    
    String obtenerNombreUnidadAdmva( Integer claveAdmon );
}
