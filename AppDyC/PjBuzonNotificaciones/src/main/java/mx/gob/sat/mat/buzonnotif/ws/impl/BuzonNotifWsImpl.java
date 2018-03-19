/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzonnotif.ws.impl;

import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacionResponse;
import mx.gob.sat.mat.buzonnotif.ws.IBuzonNotifWs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 *
 * @author GAER8674
 */
@Service("buzonNotifWs")
public class BuzonNotifWsImpl implements IBuzonNotifWs{

    @Autowired
    @Qualifier("webServiceTemplateBN")
    private WebServiceTemplate webServiceTemplateBN;
    
    @Override
    public ObtieneMediosComunicacionResponse obtieneMediosComunicacion(ObtieneMediosComunicacion request) {
        return (ObtieneMediosComunicacionResponse) webServiceTemplateBN.marshalSendAndReceive(request);
    }
}
