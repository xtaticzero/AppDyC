package mx.gob.sat.siat.dyc.trabajo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.vo.DictaminadorSearchParams;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;


public interface DyccDictaminadorService{ 
    List<DyccDictaminadorDTO> consultarDictaminadores(int unidad);
    
    List<DyccDictaminadorDTO> consultarDictaminadoresCveSIR(AdmcUnidadAdmvaDTO cbzcUnidadadmvaDTO, DictaminadorSearchParams dictaminadorSearchParams); 
    
    List<DictaminadorSolBean> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador); 
    
    List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(long numEmpleado) ; 
 
}
