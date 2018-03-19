package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccStatusProcesoDAO {
    List<DyccStatusProcesoDTO> listarStatusProceso(String consulta) throws SIATException;
}