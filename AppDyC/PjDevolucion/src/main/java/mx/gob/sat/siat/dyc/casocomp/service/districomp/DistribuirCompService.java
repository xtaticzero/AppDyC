package mx.gob.sat.siat.dyc.casocomp.service.districomp;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;


public interface DistribuirCompService {
    void distribuirCompensacion(DycpCompensacionDTO compensacion);
    boolean isAutomatica(DycpCompensacionDTO compensacion);
}
