/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.service;

import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;
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
//@ContextConfiguration("file:src/test/resources/applicationContext-RfcAmplTst.xml")
@ContextConfiguration("file:src/main/resources/applicationContext-RfcAmpl.xml")
public class RfcAmplServiceTest {

    private final Logger log = Logger.getLogger(RfcAmplServiceTest.class.getName());
    
    @Autowired
    @Qualifier("rfcAmplService")
    IRfcAmplService rfcAmplService;

    
    @Ignore
    @Test
    public void getIdcInterno() throws RfcAmplExcepcion {
        try {
            rfcAmplService.getIdcInterno(null);
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(Boolean.FALSE);
        }
    }
    
    @Ignore
    @Test
    public void consultarXRfc() throws RfcAmplExcepcion {
        try {
            //IdCInterno response = rfcAmplService.consultarXRfc(null); //Entrada no valida
            IdCInterno response = rfcAmplService.consultarXRfc("MAGF560103SD1"); //Persona fisica
            //IdCInterno response = rfcAmplService.consultarXRfc("BIN040804L69"); //Persona moral
            //IdCInterno response = rfcAmplService.consultarXRfc("CAGJ6610293H7"); //Empleado
            log.info(response.getUbicacion().getCALR());
            log.info(response.getUbicacion().getDALR());
            log.info(response.getUbicacion().getDEntFed());
            log.info(response.getUbicacion().getDMunicipio());
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(Boolean.FALSE);
        }
    }

}
