package mx.gob.sat.mat.dyc.ws.process;

import mx.gob.sat.mat.dyc.ws.domain.DevolucionManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.utils.TipoTramiteEnum;

public interface ProcesoDictaminadorAut {

    NotificacionDevManual execute(DevolucionManual devolucionManual, TipoTramiteEnum tipoTramite) throws ServiceException;

}
