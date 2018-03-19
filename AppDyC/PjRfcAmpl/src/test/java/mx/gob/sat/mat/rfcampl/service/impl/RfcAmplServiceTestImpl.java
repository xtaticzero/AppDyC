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
import mx.gob.sat.mat.rfcampl.service.RfcAmplServiceTest;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;
import mx.gob.sat.mat.rfcampl.util.recurso.RfcAmplUtil;
import mx.gob.sat.mat.rfcampl.ws.IRfcAmplWs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author GAER8674
 */
public class RfcAmplServiceTestImpl implements IRfcAmplService{
    private final Logger log = Logger.getLogger(RfcAmplServiceTest.class.getName());
    
    @Autowired
    private IRfcAmplWs rfcAmplWs;

    private final String serviceUrl = "http://10.183.14.102:9080/axis2/services/IdCInterno?wsdl";
    private final String user = "pruebasCiecAPPS";
    private final String password = "N3t1q2015";


    @Override
    public IdCInterno getIdcInterno(DatosEntrada request){
        if(request!=null){
            log.info("URI: " + serviceUrl);
            log.info("RFC: " + request.getRfc());
            log.info("Usuario: " + request.getUsuario());
            log.info("Password: " + request.getPassword());
        }
        
        return rfcAmplWs.getIdcInterno(request);
    }

    @Override
    public IdCInterno consultarXRfc(String rfc) throws RfcAmplExcepcion {
        final String secciones = "UBICACION";
        RfcAmplUtil.validarDatosRequest( new RfcAmplValidarDatosTO(rfc, user, password, secciones) );

        ObjectFactory factory = new ObjectFactory();
        DatosEntrada request = factory.createDatosEntrada();
        request.setRfc(rfc);
        request.setUsuario(user);
        request.setPassword(password);
        request.getSecciones().addAll(Arrays.asList(secciones.split(",")));

        return getIdcInterno(request);
    }

}
