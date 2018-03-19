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
import mx.gob.sat.mat.buzontrib.util.constante.BuzonTribConstantes;
import mx.gob.sat.mat.buzontrib.util.excepcion.BuzonTribExcepcion;
import mx.gob.sat.mat.buzontrib.util.recurso.BuzonTribUtil;
import mx.gob.sat.mat.buzontrib.ws.IBuzonTribWs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author GAER8674
 */
@Service("buzonTribService")
public class BuzonTribServiceImpl implements IBuzonTribService{
    
    private final Logger log = Logger.getLogger(BuzonTribServiceImpl.class.getName());
    
    @Autowired
    @Qualifier("buzonTribWs")
    private IBuzonTribWs buzonTribWs;

    @Value(BuzonTribConstantes.PROPERTIES_KEY_WSDL)
    private String wsdl;

    
    @Override
    public RegistraComunicadoResponse registraComunicado(RegistraComunicado request) {
        log.info("registraComunicado - Inicio || wsdl="+wsdl);
        
        RegistraComunicadoResponse response = buzonTribWs.registraComunicado(request);
        
        log.info("registraComunicado - Fin");
        return response;
    }

    @Override
    public RegistraComunicadoResponse enviarNotificacion(RegistraComunicado request) throws BuzonTribExcepcion{
        log.info("enviarNotificacion - Inicio");
        BuzonTribValidarDatosTO buzonTribValidarDatosDTO = null;
        RegistraComunicado requestSinNulos = null;
        
        if(request!=null){
            buzonTribValidarDatosDTO = new BuzonTribValidarDatosTO(request.getIdComunicado(), 
                request.getRFC(), request.getVigenciaIni(), request.getVigenciaFin());
        }
        BuzonTribUtil.validarDatosRequest(buzonTribValidarDatosDTO);
        
        // Actualmente WS Buzon Tributario no permite parametros nulos.
        requestSinNulos = new RegistraComunicado(BuzonTribUtil.nullToEmpty(request));
        RegistraComunicadoResponse response = registraComunicado(requestSinNulos);
                
        log.info("enviarNotificacion - Fin");
        return response;
    }
}
