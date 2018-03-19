package mx.gob.sat.siat.dyc.dao.adminproceso;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.Procesos;

public interface DyctSegProcesoDAO {
    void actualizarStatusProceso(Procesos proceso) throws SIATException;
}
