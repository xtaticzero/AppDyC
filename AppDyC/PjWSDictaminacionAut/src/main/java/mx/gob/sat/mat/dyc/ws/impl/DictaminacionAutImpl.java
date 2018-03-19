package mx.gob.sat.mat.dyc.ws.impl;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.DictaminacionAut;
import mx.gob.sat.mat.dyc.ws.DictaminacionDelegate;
import mx.gob.sat.mat.dyc.ws.domain.Dictaminacion;
import mx.gob.sat.mat.dyc.ws.domain.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Gregorio Serapio Ramirez
 */
@WebService(serviceName = "DictaminacionAut", endpointInterface = "mx.gob.sat.mat.dyc.ws.DictaminacionAut", wsdlLocation = "WEB-INF/wsdl/DictaminacionAut.wsdl")
@HandlerChain(file = "DictaminacionAut_handler.xml")
public class DictaminacionAutImpl extends SpringBeanAutowiringSupport implements DictaminacionAut {

    @Resource
    private WebServiceContext wsContext;

    @Autowired
    private DictaminacionDelegate dictaminacionDelegate;

    @Override
    public Notificacion obtenerResulDictAut(Dictaminacion dictaminacion) {

        return dictaminacionDelegate.procesar(dictaminacion, wsContext);

    }
}
