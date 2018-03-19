package mx.gob.sat.siat.dyc.dao.usuario;

import java.sql.SQLException;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.DyccDictaminadorSolDTO;


/**
 * @version 0.2 J. Isaac Carbajal Bernal
 */
public interface DyccDictaminadorDAO {

    DyccDictaminadorDTO encontrar(Integer numEmpleado);
    
    DyccDictaminadorDTO encontrarEmpDic(Integer numEmpleado);

    List<DyccDictaminadorDTO> seleccionar();

    List<DyccDictaminadorDTO> seleccionarOrder(String order);

    List<DyccDictaminadorDTO> selecOrderXClaveadm(Integer claveAdm, String order);

    List<DyccDictaminadorDTO> consultarDictaminadores(int unidad);

    List<DictaminadorVO> consultarDictaminadorPorCargaAleatorio(int idUnidadAdmva, String centroCosto);

    List<DictaminadorVO> consultarDictaminadoresPorCveSIR(int cveSIR, String queryParams, String claveAgrs);

    List<DictaminadorVO> consultarDictaminadoresPorCveSIRTodos(int cveSIR, String queryParams, String claveAgrs);

    List<DyccDictaminadorSolDTO> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador);

    List<DictaminadorSolBean> consultarSolicitudesAsociadasDictaminador(DyccDictaminadorDTO selectedDictaminador);

    List<DictaminadorSolBean> consultarSolicitudesAsociadasDictaminadorXFecha(DyccDictaminadorDTO selectedDictaminador,
                                                                              Date fechaInicio, Date fechaFin);

    List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(long numEmpleado);

    List<DictaminadorVO> obtenerDictaminadores(DictaminadorVO dictaminador, Boolean activo) throws SQLException;

    List<DictaminadorVO> obtenerDictaminadoresActivos(DictaminadorVO dictaminador) throws SQLException;

    List<DictaminadorVO> obtenerDictaminadoresAgs(DictaminadorVO dictaminador, Boolean activo) throws SIATException;

    DictaminadorVO obtenerDyccDictaminador(Integer numEmpleadoDict) throws SQLException;

    void insertarDictaminador(DictaminadorVO dictaminador) throws SQLException;

    void actualizarDictaminador(DictaminadorVO dictaminador) throws SQLException;

    DictaminadorVO obtenerDictaminador(Integer numeroEmpleadoDictaminador) throws SQLException;

    Integer obtenerClaveAdmDictaminador(Integer numEmpleadoDict) throws SQLException;

}

