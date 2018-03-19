package mx.gob.sat.siat.dyc.dao.usuario;

import java.sql.SQLException;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;


public interface DyccAprobadorDAO {
    List<DyccAprobadorDTO> seleccionar();

    DyccAprobadorDTO encontrar(Integer numEmpleado);
    
    DyccAprobadorDTO encontrarEmpAp(Integer numEmpleado);

    List<DyccAprobadorDTO> obtenerListaAprobadores(DyccAprobadorDTO aprobador);

    List<AprobadorVO> obtenerAprobadores(AprobadorVO aprobador, boolean isActivo);

    AprobadorVO obtenerAprobadoresDycc(Integer numEmpleadoDict);

    void insertarAprobador(AprobadorVO aprobador) throws SQLException;

    void actualizarAprobador(AprobadorVO aprobador) throws SQLException;

    List<DyccAprobadorDTO> consultarAprobadores(int unidad, int centroCosto);

    List<DyccAprobadorDTO> consultarAprobadoresAprobarResol(int unidad, int centroCosto, int numEmpleado, int nivel);
    
    DyccAprobadorDTO consultarAprobadorActivoXClaveAdm(Integer claveAdm);

    DyccAprobadorDTO consultarAprobadorPorClaveAdm(Integer claveAdm);

    List<DyccAprobadorDTO> consultaAprobadorConCargaTrabajo(Integer claveAdm, Integer centroCostoOp, Integer opcion);

    List<DictaminadorSolBean> consultarTramitesAsociadosAprobador(Integer numEmpleado);

    List<DictaminadorSolBean> consultarTramitesAsociadosAprobadorPorFecha(Integer numEmpleado, Date fechaInicio,
                                                                  Date fechaFin);
}
