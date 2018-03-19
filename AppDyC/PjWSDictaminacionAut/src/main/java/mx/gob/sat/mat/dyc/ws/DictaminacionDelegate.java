package mx.gob.sat.mat.dyc.ws;

import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.domain.Dictaminacion;
import mx.gob.sat.mat.dyc.ws.domain.Notificacion;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DictaminacionDelegate {

    Notificacion procesar(Dictaminacion dictaminacion, WebServiceContext wsContext);
}
