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
import mx.gob.sat.mat.buzonnotif.util.excepcion.BuzonNotifExcepcion;
import mx.gob.sat.mat.buzonnotif.ws.IBuzonNotifWs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author GAER8674
 */
@Service("buzonNotifService")
public class BuzonNotifServiceImpl implements IBuzonNotifService{
    
    private final Logger log = Logger.getLogger(BuzonNotifServiceImpl.class.getName());
    
    @Autowired
    @Qualifier("buzonNotifWs")
    private IBuzonNotifWs buzonNotifWs;

    /**@Value(BuzonNotifConstantes.PROPERTIES_KEY_WSDL)**/
    private String wsdl;

    
    @Override
    public ObtieneMediosComunicacionResponse obtieneMediosComunicacion(ObtieneMediosComunicacion request) {
        return buzonNotifWs.obtieneMediosComunicacion(request);
    }

    @Override
    public List<MedioComunicacion> obtieneMediosComunicacion(String rfc) throws BuzonNotifExcepcion{
        log.info("obtieneMediosComunicacion - Inicio || wsdl="+wsdl + " || rfc="+rfc);
        List<MedioComunicacion> mediosResponse = null;
        
        try{
            ObtieneMediosComunicacion request = (new ObjectFactory()).createObtieneMediosComunicacion();

            request.setRfc(rfc);
            ObtieneMediosComunicacionResponse response = obtieneMediosComunicacion(request);
            if(response.getObtieneMediosComunicacionResult()!=null 
               && response.getObtieneMediosComunicacionResult().getMedioComunicacion()!=null){
                mediosResponse = response.getObtieneMediosComunicacionResult().getMedioComunicacion();
            }
        } catch(RuntimeException e){
            throw new BuzonNotifExcepcion(e);
        }
        
        log.info("obtieneMediosComunicacion - Fin");
        return mediosResponse;
    }

    /**
     * @param wsdl the wsdl to set
     */
    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
    }
}
