/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzonnotif.service;

import java.util.List;
import mx.gob.sat.mat.buzonnotif.client.MedioComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacion;
import mx.gob.sat.mat.buzonnotif.service.IBuzonNotifService;
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
@ContextConfiguration("file:src/main/resources/applicationContext-BuzonNotif.xml")
//@ContextConfiguration("file:src/test/resources/applicationContext-BuzonNotifTst.xml")
public class BuzonNotifServiceTest {

    private final Logger log = Logger.getLogger(BuzonNotifServiceTest.class.getName());
    
    @Autowired
    @Qualifier("buzonNotifService")
    private IBuzonNotifService buzonNotifService;
    
    
    @Ignore
    @Test
    public void obtieneMediosComunicacionVacio(){
        try {
            buzonNotifService.obtieneMediosComunicacion(new ObtieneMediosComunicacion());
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(Boolean.FALSE);
        }
    }
    
    @Ignore
    @Test
    public void obtieneMediosComunicacion(){
        try {
            //List<MedioComunicacion> response = buzonNotifService.obtieneMediosComunicacion("TIM840630BS2");
            List<MedioComunicacion> response = buzonNotifService.obtieneMediosComunicacion("NOT_VALID");
            for(MedioComunicacion medio : response){
                log.info("Medio:           " + medio.getMedio());
                log.info("TipoMedio:       " + medio.getTipoMedio());
                log.info("DescMedio:       " + medio.getDescMedio());
                log.info("PrioridadCorreo: " + medio.getPrioridadCorreo());
                log.info("Amparado:        " + medio.getAmparado());
            }
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(Boolean.FALSE);
        }
    }
}
