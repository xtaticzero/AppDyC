/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzonnotif.service.impl;

import java.util.List;
import mx.gob.sat.mat.buzonnotif.client.MedioComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObjectFactory;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacionResponse;
import mx.gob.sat.mat.buzonnotif.service.IBuzonNotifService;
import mx.gob.sat.mat.buzonnotif.ws.IBuzonNotifWs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author GAER8674
 */
public class BuzonNotifServiceTestImpl implements IBuzonNotifService{
    private final Logger log = Logger.getLogger(BuzonNotifServiceTestImpl.class.getName());
    
    @Autowired
    private IBuzonNotifWs buzonNotifWs;

    private final String wsdl = "http://99.90.29.84/wsBuzonNotificaciones.Proxy/BuzonNotificaciones.asmx?WSDL";

    
    @Override
    public ObtieneMediosComunicacionResponse obtieneMediosComunicacion(ObtieneMediosComunicacion request) {
        return buzonNotifWs.obtieneMediosComunicacion(request);
    }

    @Override
    public List<MedioComunicacion> obtieneMediosComunicacion(String rfc) {
        log.info("obtieneMediosComunicacion - Inicio || wsdl="+wsdl + " || rfc="+rfc);
        List<MedioComunicacion> mediosResponse = null;
        ObtieneMediosComunicacion request = (new ObjectFactory()).createObtieneMediosComunicacion();
        
        request.setRfc(rfc);
        ObtieneMediosComunicacionResponse response = obtieneMediosComunicacion(request);
        if(response.getObtieneMediosComunicacionResult()!=null 
           && response.getObtieneMediosComunicacionResult().getMedioComunicacion()!=null){
            mediosResponse = response.getObtieneMediosComunicacionResult().getMedioComunicacion();
        }
                
        log.info("obtieneMediosComunicacion - Fin");
        return mediosResponse;
    }
}
