/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.service.impl;

import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicadoResponse;
import mx.gob.sat.mat.buzontrib.domain.BuzonTribValidarDatosTO;
import mx.gob.sat.mat.buzontrib.service.IBuzonTribService;
import mx.gob.sat.mat.buzontrib.util.excepcion.BuzonTribExcepcion;
import mx.gob.sat.mat.buzontrib.util.recurso.BuzonTribUtil;
import mx.gob.sat.mat.buzontrib.ws.IBuzonTribWs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author GAER8674
 */
public class BuzonTribServiceTestImpl implements IBuzonTribService{
    private final Logger log = Logger.getLogger(BuzonTribServiceTestImpl.class.getName());
    
    @Autowired
    private IBuzonTribWs buzonTribWs;

    private final String serviceUrl = "http://99.90.29.84/wsBuzonTributario.Proxy/Terminos.asmx?WSDL";

    
    @Override
    public RegistraComunicadoResponse registraComunicado(RegistraComunicado request) {
        log.info("URI: " + serviceUrl);
        return buzonTribWs.registraComunicado(request);
    }

    @Override
    public RegistraComunicadoResponse enviarNotificacion(RegistraComunicado request) throws BuzonTribExcepcion{
        BuzonTribValidarDatosTO buzonTribValidarDatosDTO = null;
        
        if(request!=null){
            // El estado actual del WS Buzon Tributario es no permitir parametros nulos.
            request = BuzonTribUtil.nullToEmpty(request);
            buzonTribValidarDatosDTO = new BuzonTribValidarDatosTO(request.getIdComunicado(), 
                request.getRFC(), request.getVigenciaIni(), request.getVigenciaFin());
        }
        BuzonTribUtil.validarDatosRequest(buzonTribValidarDatosDTO);
        
        return registraComunicado(request);
    }
}
