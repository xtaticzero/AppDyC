package cte.dyc.dao;


import cte.dyc.dto.DycpServicioDTO;
import cte.dyc.dto.EmpleadoDTO;
import cte.dyc.generico.util.common.SIATException;

import java.util.List;


public interface DycpServicioDAO {

    List<DycpServicioDTO> obtenerSolicitudServicio(Integer empleado) throws SIATException;
    
    Integer consultaDictaminador(EmpleadoDTO empleado);
    
    Integer consultaAprobador(EmpleadoDTO empleado);

}
