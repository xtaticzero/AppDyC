/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.ws.impl;

import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicadoResponse;
import mx.gob.sat.mat.buzontrib.ws.IBuzonTribWs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 *
 * @author GAER8674
 */
@Service("buzonTribWs")
public class BuzonTribWsImpl implements IBuzonTribWs{

    @Autowired
    @Qualifier("webServiceTemplateBZ")
    private WebServiceTemplate webServiceTemplateBZ;
    
    @Override
    public RegistraComunicadoResponse registraComunicado(RegistraComunicado request) {
        return (RegistraComunicadoResponse) webServiceTemplateBZ.marshalSendAndReceive(request);
    }
    
}
