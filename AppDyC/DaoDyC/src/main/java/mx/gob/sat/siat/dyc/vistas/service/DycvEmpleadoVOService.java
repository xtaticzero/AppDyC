package mx.gob.sat.siat.dyc.vistas.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.vistas.vo.DycvEmpleadoVO;


public interface DycvEmpleadoVOService {
    
    DycvEmpleadoVO consultaInformacionEmpleado(DycvEmpleadoVO empleado);

    List<DycvEmpleadoVO> consultaNivelDireccion();
    
    DycvEmpleadoDTO encontrarxRfcoNum(Object param);
}
