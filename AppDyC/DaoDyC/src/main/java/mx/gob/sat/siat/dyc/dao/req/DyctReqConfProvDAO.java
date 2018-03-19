package mx.gob.sat.siat.dyc.dao.req;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctReqConfProvDAO {
    List<DyctReqConfProvDTO> selecXServicioProveedor(DycpServicioDTO servicio, String rfcProveedor);

    void insertar(DyctReqConfProvDTO reqConfProv) throws SIATException;
}
