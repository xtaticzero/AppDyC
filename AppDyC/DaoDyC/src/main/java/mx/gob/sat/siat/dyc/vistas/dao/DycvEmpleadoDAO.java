package mx.gob.sat.siat.dyc.vistas.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vistas.vo.DycvEmpleadoVO;


public interface DycvEmpleadoDAO {
    
    DycvEmpleadoDTO consultarXIDEmpleado(Integer idEmpleado) throws SIATException;

    DycvEmpleadoVO consultaInformacionEmpleado(DycvEmpleadoVO empleado) throws SIATException;

    List<DycvEmpleadoVO> consultaNivelDireccion() throws SIATException;
    
    DycvEmpleadoDTO encontrar(Integer numEmpleado);

    DycvEmpleadoDTO selecXRfc(String rfc);

    DycvEmpleadoDTO selecXRfccorto(String rfcCorto);
    
    DycvEmpleadoDTO encontrarxRfcoNum(Object param);
        
}
