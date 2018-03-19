/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.service.impl;

import java.util.Arrays;
import mx.gob.sat.mat.rfcampl.client.DatosEntrada;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.client.ObjectFactory;
import mx.gob.sat.mat.rfcampl.domain.RfcAmplValidarDatosTO;
import mx.gob.sat.mat.rfcampl.service.IRfcAmplService;
import mx.gob.sat.mat.rfcampl.util.constante.RfcAmplConstantes;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;
import mx.gob.sat.mat.rfcampl.util.recurso.RfcAmplUtil;
import mx.gob.sat.mat.rfcampl.ws.IRfcAmplWs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author GAER8674
 */
@Service("rfcAmplService")
public class RfcAmplServiceImpl implements IRfcAmplService {

    private final Logger log = Logger.getLogger(RfcAmplServiceImpl.class.getName());
    
    @Autowired
    @Qualifier("rfcAmplWs")
    private IRfcAmplWs rfcAmplWs;

    @Value(RfcAmplConstantes.PROPERTIES_KEY_WSDL)
    private String wsdl;

    @Value(RfcAmplConstantes.PROPERTIES_KEY_USER)
    private String user;

    @Value(RfcAmplConstantes.PROPERTIES_KEY_PASSWORD)
    private String password;

    @Value(RfcAmplConstantes.PROPERTIES_KEY_SECCIONES)
    private String secciones;

    
    @Override
    public IdCInterno getIdcInterno(DatosEntrada request){
        log.info("getIdcInterno - Inicio || wsdl="+wsdl);

        IdCInterno response = rfcAmplWs.getIdcInterno(request);

        log.info("getIdcInterno - Fin");
        return response;
    }

    @Override
    public IdCInterno consultarXRfc(String rfc) throws RfcAmplExcepcion{
        log.info("consultarXRfc - Inicio || rfc="+rfc+" || secciones="+secciones);

        RfcAmplUtil.validarDatosRequest( new RfcAmplValidarDatosTO(rfc, user, password, secciones) );
        
        DatosEntrada request = construirRequest(rfc);
        
        IdCInterno response = getIdcInterno(request);
        
        log.info("consultarXRfc - Fin");
        return response;
    }
    
    private DatosEntrada construirRequest(String rfc){
        ObjectFactory factory = new ObjectFactory();
        DatosEntrada request = factory.createDatosEntrada();
        request.setRfc(rfc);
        request.setUsuario(user);
        request.setPassword(password);
        request.getSecciones().addAll(Arrays.asList(secciones.split(",")));
        request.getSeccionesHistoricas().addAll(Arrays.asList(secciones.split(",")));
        
        return request;
    }
}
