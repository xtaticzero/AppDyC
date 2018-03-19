package mx.gob.sat.siat.dyc.dao.adminproceso;

import java.util.List;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.Procesos;


public interface DyctProcesosDAO {
    List<Procesos> consultar() throws SIATException;
    void guardarCron(Procesos proceso) throws SIATException;
}
