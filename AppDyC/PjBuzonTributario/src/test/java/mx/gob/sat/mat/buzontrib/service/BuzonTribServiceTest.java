/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.service;

import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicadoResponse;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author GAER8674
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/applicationContext-BuzonTrib.xml")
//@ContextConfiguration("file:src/test/resources/applicationContext-BuzonTribTst.xml")
public class BuzonTribServiceTest {

    private final Logger log = Logger.getLogger(BuzonTribServiceTest.class.getName());
    
    @Autowired
    @Qualifier("buzonTribService")
    //@Qualifier("buzonTribServiceTest")
    private IBuzonTribService buzonTribService;
    
    
    @Ignore
    @Test
    public void registraComunicado(){
        try {
            buzonTribService.registraComunicado(null);
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(Boolean.FALSE);
        }
    }
    
    @Ignore
    @Test
    public void enviarNotificacion(){
        try {
            RegistraComunicado request = new RegistraComunicado();
            request.setIdComunicado(1);
            request.setRFC("GAER860704JX2");
            //request.setRFC("NOTVALID            ");
            //request.setRazonSocial("");
            //request.setCorreo("");
            request.setVigenciaIni("NOTVALID");//"24/02/2016");
            request.setVigenciaFin("NOTVALID");//"24/02/2026");
            //request.setParametros("");
            
            //request = null;
            RegistraComunicadoResponse response = buzonTribService.enviarNotificacion(request);
            
            if(response!=null && response.getRegistraComunicadoResult()!=null){
                log.info("------------"+response.getRegistraComunicadoResult().getCveError());
                log.info("------------"+response.getRegistraComunicadoResult().getDescError());
            }
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(Boolean.FALSE);
        }
    }
}
