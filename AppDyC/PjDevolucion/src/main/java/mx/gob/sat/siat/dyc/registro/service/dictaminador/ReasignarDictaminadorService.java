package mx.gob.sat.siat.dyc.registro.service.dictaminador;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DictaminadorSearchParams;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface ReasignarDictaminadorService {
    List<DictaminadorVO> consultarDictaminadoresCveSIR(Integer cveSIR,
                                                       DictaminadorSearchParams dictaminadorSearchParams,
                                                       String claveAgrs) throws SIATException;

    List<DictaminadorVO> consultarDictaminadoresCveSIRTodos(Integer cveSIR,
                                                            DictaminadorSearchParams dictaminadorSearchParams,
                                                            String claveAgrs) throws SIATException;

    List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(Long numEmpleado);

    List<DictaminadorSolBean> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador) throws SIATException;

    List<DictaminadorSolBean> consultarSolicitudesAsociadasDictXFecha(DyccDictaminadorDTO selectedDictaminador,
                                                                      Date fechaInicio,
                                                                      Date fechaFin) throws SIATException;

}
