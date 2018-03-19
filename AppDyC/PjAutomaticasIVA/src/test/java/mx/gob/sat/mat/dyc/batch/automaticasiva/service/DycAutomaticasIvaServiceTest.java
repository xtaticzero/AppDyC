package mx.gob.sat.mat.dyc.batch.automaticasiva.service;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.dyc.batch.automaticasiva.service.impl.DycAutomaticasIvaDelTestImpl;
import mx.gob.sat.mat.dyc.batch.automaticasiva.service.impl.DycAutomaticasIvaServiceTestImpl;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion.DycAutomaticasIvaExcepcion;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.DycConstantesAutomaticasIva;
import mx.gob.sat.siat.dyc.util.recurso.DycFechaUtil;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaLoteProcesadoDTO;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaProcesadoDTO;
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
public class DycAutomaticasIvaServiceTest {

    private final Logger log = Logger.getLogger(DycAutomaticasIvaServiceTest.class.getName());
    
    @Autowired
    @Qualifier("dycAutomaticasIvaService")
    private DycAutomaticasIvaServiceTestImpl dycAutomaticasIvaServiceTest;

    @Autowired
    @Qualifier("dycAutomaticasIvaDel")
    private DycAutomaticasIvaDelTestImpl dycAutomaticasIvaServiceTestDel;
    
    @Ignore
    @Test
    public void procesarDevolucionesAutomaticasIvaNuevas(){
        boolean finalizadoCorrectamente = Boolean.TRUE;
        DycAutomaticasIvaLoteProcesadoDTO resultado = null;
        
        try {
            resultado = dycAutomaticasIvaServiceTestDel.procesarLoteDevolucionesAutomaticasIva();
        } catch (DycAutomaticasIvaExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(ex.getCodigoError(), ex.getEstadoVariables()), ex);
            finalizadoCorrectamente = Boolean.FALSE;
        }
        
        if(null!=resultado){
            log.info("DevolucionesProcesadas: "+resultado.getDevolucionesProcesadas());
            for(DycAutomaticasIvaProcesadoDTO devolucionProcesada : resultado.getDatosDevolucionesProcesadas()){
                log.info("Rfc="+devolucionProcesada.getRfc() + " || IdSaldoIcep="+devolucionProcesada.getIdSaldoIcep() 
                        + " || NumeroControl="+devolucionProcesada.getNumeroControl());
            }
            
            log.info("DevolucionesNoProcesadas: "+resultado.getDevolucionesNoProcesadas());
            for(DycAutomaticasIvaProcesadoDTO devolucionNoProcesada : resultado.getDatosDevolucionesNoProcesadas()){
                log.info("Rfc="+devolucionNoProcesada.getRfc() + " || IdSaldoIcep="+devolucionNoProcesada.getIdSaldoIcep() 
                        + " || NumeroControl="+devolucionNoProcesada.getNumeroControl());
            }
        }
        assertTrue(finalizadoCorrectamente);
    }

    @Ignore
    @Test
    public void esMorsaMarcaResultadoPositiva(){
        try {
            Long numeroLote = 1L;
            
            List<DyctDevAutoIvaDTO> devolucion = dycAutomaticasIvaServiceTest.consultarUnaDevolucionAutomaticaIVA(numeroLote);
            log.info("----------------- esMorsaMarcaResultadoPositiva: " + dycAutomaticasIvaServiceTest.esMorsaMarcaResultadoPositiva(devolucion.get(0)) );
        } catch (DycAutomaticasIvaExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(ex.getCodigoError(), ex.getEstadoVariables()), ex);
        }
    }

    @Ignore
    @Test
    public void obtenerRfcAmpliadoClaveSir(){
        Long numeroLote = 1L;
        boolean finalizadoCorrectamente = Boolean.TRUE;
        
        try {
            List<DyctDevAutoIvaDTO> devolucion = dycAutomaticasIvaServiceTest.consultarUnaDevolucionAutomaticaIVA(numeroLote);
            String rfc = devolucion.get(0).getRfc();
            
            IdCInterno rfcAmpliado = dycAutomaticasIvaServiceTest.consultarRfcAmpliado(rfc);
            String claveSir = dycAutomaticasIvaServiceTest.obtenerRfcAmpliadoClaveSir(rfc, rfcAmpliado);
            log.info("adr="+claveSir);
        } catch (DycAutomaticasIvaExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(ex.getCodigoError(), ex.getEstadoVariables()), ex);
            finalizadoCorrectamente = Boolean.FALSE;
        }
        
        assertTrue(finalizadoCorrectamente);
    }

    @Ignore
    @Test
    public void generarNumeroControl(){
        boolean finalizadoCorrectamente = Boolean.TRUE;
        String claveSir = "01" ;
        
        try {
            log.info( "numeroControl="+dycAutomaticasIvaServiceTest.generarNumeroControl(claveSir, new Date()) );
         } catch (DycAutomaticasIvaExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(ex.getCodigoError(), ex.getEstadoVariables()), ex);
            finalizadoCorrectamente = Boolean.FALSE;
        }
        
        assertTrue(finalizadoCorrectamente);
   }

    
    @Ignore
    @Test
    public void existeIcepCargadoEnSaldosDyc(){
        boolean finalizadoCorrectamente = Boolean.TRUE;
        DyctDevAutoIvaDTO registroMora = new DyctDevAutoIvaDTO();
        //BIN040804L69	17302	301	2001	1
        registroMora.setRfc("BIN040804L69");
        registroMora.setConcepto(301);
        registroMora.setEjercicio(2001);
        registroMora.setPeriodo(1);
        
        try {
            log.info( "-----------------existeIcepCargadoEnSaldosDyc="
                    +dycAutomaticasIvaServiceTest.obtenerSaldoIcep(registroMora));
         } catch (DycAutomaticasIvaExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(ex.getCodigoError(), ex.getEstadoVariables()), ex);
            finalizadoCorrectamente = false;
        }
        
        assertTrue(finalizadoCorrectamente);
   }

    @Ignore
    @Test
    public void enviarNotificacion(){
        boolean finalizadoCorrectamente = Boolean.TRUE;
        int idComunicado = 0;//81;
        Date hoy = new Date();
        Date vigenciaFin = DycFechaUtil.sumarDias(hoy, DycConstantesAutomaticasIva.NOTIFICACION_CASODEVOLUCION_VIGENCIA);
        
        try {
            log.info( "-----------------existeIcepCargadoEnSaldosDyc="
                    +dycAutomaticasIvaServiceTest.enviarNotificacion(idComunicado, "TIM840630BS2", "OCC CEL", hoy, vigenciaFin));
         } catch (DycAutomaticasIvaExcepcion ex) {
            log.error(DycLogUtil.generarMsgError(ex.getCodigoError(), ex.getEstadoVariables()), ex);
            finalizadoCorrectamente = false;
        }
        
        assertTrue(finalizadoCorrectamente);
    
    }
}