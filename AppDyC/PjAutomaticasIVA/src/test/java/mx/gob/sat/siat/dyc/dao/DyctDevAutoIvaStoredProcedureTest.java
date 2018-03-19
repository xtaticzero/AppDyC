/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.constante.EDycAutomaticasIvaCodigoError;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
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
public class DyctDevAutoIvaStoredProcedureTest {

    private final Logger LOG = Logger.getLogger(DyctDevAutoIvaStoredProcedureTest.class.getName());
    
    @Autowired
    @Qualifier("dyctDevIvaDAO")
    private DyctDevIvaDAO dyctDevIvaDAO;
    
    
    @Ignore
    @Test
    public void selectXNumeroLote(){
        LOG.info("selectXNumeroLote() - Inicio");
        boolean finalizadoCorrectamente = Boolean.FALSE;
        Long numLote = 1L;
        
        try{
            List<DyctDevAutoIvaDTO> devoluciones = dyctDevIvaDAO.selectXNumeroLote(numLote);
            for(DyctDevAutoIvaDTO devolucion : devoluciones){
                LOG.info(devolucion);
            }
            finalizadoCorrectamente = Boolean.TRUE;
            
            //throw new RuntimeException("Prueba de excepcion");
        }catch(RuntimeException e){
            List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
            estadoVariables.add( new DycLogEstadoVariable("numLote", numLote.toString()) );
            
            LOG.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.AUTOMATICAS_GENERAL, estadoVariables), e);
            finalizadoCorrectamente = Boolean.FALSE;
        }
        
        assertTrue(finalizadoCorrectamente);
        LOG.info("selectXNumeroLote() - Fin");
    }
}
