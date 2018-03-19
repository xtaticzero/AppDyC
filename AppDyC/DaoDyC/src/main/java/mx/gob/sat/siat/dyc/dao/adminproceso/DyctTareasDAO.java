package mx.gob.sat.siat.dyc.dao.adminproceso;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.Procesos;

public interface DyctTareasDAO {
    void guardarCron(Procesos proceso) throws SIATException;
}
