/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.secuencia.solicitud;

import mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion.DycAutomaticasIvaExcepcion;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertTrue;
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
@ContextConfiguration(locations = { "file:src/test/resources/applicationContextTst.xml" })
public class SolNumConsecutivoDAOTest {
    private final Logger log = Logger.getLogger(SolNumConsecutivoDAOTest.class.getName());
    
    @Autowired
    @Qualifier("solNumConsecutivoDAOTest")
    private SolNumConsecutivoDAO solNumConsecutivoDAO;

    @Ignore
    @Test
    public void getNumConsecutivoDevIva(){
        boolean finalizadoCorrectamente = Boolean.TRUE;
        final String claveSir = "01";
        
        try {
            log.info("local      : "+claveSir);
            log.info("consecutivo: "+solNumConsecutivoDAO.getNumConsecutivoDevIva(claveSir));
        } catch (Exception ex) {
            log.error("", ex);
            finalizadoCorrectamente = Boolean.FALSE;
        }
        
        assertTrue(finalizadoCorrectamente);
    }
}
