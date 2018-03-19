package mx.gob.sat.mat.dyc.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import mx.gob.sat.mat.dyc.ws.domain.Dictaminacion;
import mx.gob.sat.mat.dyc.ws.domain.Notificacion;

/**
 *
 * @author root
 */
@WebService
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public interface DictaminacionAut {    

    @WebMethod(action = "obtenerResulDictAut")
    @WebResult(name = "Notificacion")
    Notificacion obtenerResulDictAut(@WebParam(name = "Dictaminacion") Dictaminacion dictaminacion);

}