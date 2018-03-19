/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.matrizaprobador;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;


/**
 * Clase Interface service para la tabla [DYCC_APROBADOR].
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Octubre 14, 2013
 * */
public interface AprobadorService {

    List<AprobadorVO> obtenerAprobadores(AprobadorVO aprobador, boolean isActivo);

    /**
     * @param aprobador objeto tipo [DyccAprobadorDTO] base para busquedas o nulo para todos los parametros
     * @return Lista de tipo ArrayList [DyccAprobadorDTO]
     */
    List<DyccAprobadorDTO> obtenerListaAprobadores(DyccAprobadorDTO aprobador);

    List<EmpleadoVO> obtenerListaNivelDireccion();

    List<DyccAprobadorDTO> consultarAprobadores(int unidad, int centroCosto);

    DyccAprobadorDTO consultarAprobadorPorClaveAdm(Integer claveAdm);

   /**
     * @param empleado
     * @param cCostosUsrSession
     * @param isNum
     * @param claveAdmon
     * @return
     */
    EmpleadoVO consultarInformacionEmpleado(EmpleadoVO empleado, Integer cCostosUsrSession, boolean isNum, Integer claveAdmon);


    void insertarAprobador(EmpleadoVO empleado);

    void actualizarAprobador(AprobadorVO aprobador);

    boolean inactivarComisionarRNFDC615(DyccAprobadorDTO aprobador, boolean baja);

    void eliminarAprobador(AprobadorVO aprobador);

    String obtenerMensaje();

    String obtenerMensaje(Integer msg);

    void setSessionID(HttpSession ss);

    void consultaAprobadorPA(Map<String, String> remplaceMensajes);
    
    DyccAprobadorDTO encontrarActivo(Integer numEmpleado);

}
