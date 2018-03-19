/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.vo.DictaminadorSearchParams;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.DyccDictaminadorSolDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since  16/08/2013
 *
 */
public interface DyccDictaminadorDAOService {

    List<DyccDictaminadorDTO> consultarDictaminadores(int unidad);

    List<DictaminadorVO> consultarDictaminadoresCveSIR(AdmcUnidadAdmvaDTO cbzcUnidadadmvaDTO,
                                                       DictaminadorSearchParams dictaminadorSearchParams, String claveAgrs);

    List<DyccDictaminadorSolDTO> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador);

    List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(long numEmpleado);

}
