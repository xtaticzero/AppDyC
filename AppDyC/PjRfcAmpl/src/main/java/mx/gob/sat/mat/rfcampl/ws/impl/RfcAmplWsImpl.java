/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.ws.impl;

import mx.gob.sat.mat.rfcampl.ws.IRfcAmplWs;


import mx.gob.sat.mat.rfcampl.client.DatosEntrada;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 *
 * @author GAER8674
 */
@Service("rfcAmplWs")
public class RfcAmplWsImpl implements IRfcAmplWs {

    @Autowired
    @Qualifier("webServiceTemplateIDC")
    private transient WebServiceTemplate webServiceTemplateIDC;


    @Override
    public IdCInterno getIdcInterno(DatosEntrada request){
        return (IdCInterno) webServiceTemplateIDC.marshalSendAndReceive(request);
    }
}
