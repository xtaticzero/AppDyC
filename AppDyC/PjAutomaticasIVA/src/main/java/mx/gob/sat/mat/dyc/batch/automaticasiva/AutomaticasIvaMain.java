/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.batch.automaticasiva;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.gob.sat.mat.dyc.batch.automaticasiva.service.impl.DycAutomaticasIvaDel;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.constante.EDycAutomaticasIvaCodigoError;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion.DycAutomaticasIvaExcepcion;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaLoteProcesadoDTO;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaProcesadoDTO;
import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Implementa el caso de uso Devolucion automatica de IVA.
 * @author GAER8674
 */
public final class AutomaticasIvaMain {
    private static final Logger LOGGER = Logger.getLogger(AutomaticasIvaMain.class);
    
    private AutomaticasIvaMain(){}
    
    
    public static void main(String args[]) {
        try {
            DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            LOGGER.info(">>>>>>>>>>EJECUTANDO PjAutomaticasIVA v1.0 el dia " + formateador.format(new Date()) + "<<<<<<<<<<");
            
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            appContext.registerShutdownHook();
            DycAutomaticasIvaDel dycAutomaticasIvaDel = (DycAutomaticasIvaDel)appContext.getBean("dycAutomaticasIvaDel");
            
            DycAutomaticasIvaLoteProcesadoDTO resultado = dycAutomaticasIvaDel.procesarLoteDevolucionesAutomaticasIva();
            LOGGER.error("DEVOLUCIONES PROCESADAS: "+resultado.getDevolucionesProcesadas());
            LOGGER.error("   AUTORIZADAS/NEGADAS (POR MORSA): "+resultado.getDatosDevolucionesProcesadas().size());
            for(DycAutomaticasIvaProcesadoDTO devolucionProcesada : resultado.getDatosDevolucionesProcesadas()){
                LOGGER.error("      Rfc="+devolucionProcesada.getRfc() + " || NumeroOperacion=" + devolucionProcesada.getNumeroOperacion()
                        + " || IdSaldoIcep="+devolucionProcesada.getIdSaldoIcep() 
                        + " || NumeroControl="+devolucionProcesada.getNumeroControl());
            }
            LOGGER.error("   DESISTIDAS: "+resultado.getDatosDevolucionesProcesadasDesistidas().size());
            for(DycAutomaticasIvaProcesadoDTO devolucionProcesada : resultado.getDatosDevolucionesProcesadasDesistidas()){
                LOGGER.error("      Rfc="+devolucionProcesada.getRfc() + " || NumeroOperacion=" + devolucionProcesada.getNumeroOperacion()
                        + " || IdSaldoIcep="+devolucionProcesada.getIdSaldoIcep() 
                        + " || NumeroControl="+devolucionProcesada.getNumeroControl());
            }
            LOGGER.error("DEVOLUCIONES NO PROCESADAS: "+resultado.getDevolucionesNoProcesadas());
            for(DycAutomaticasIvaProcesadoDTO devolucionNoProcesada : resultado.getDatosDevolucionesNoProcesadas()){
                LOGGER.error("   Rfc="+devolucionNoProcesada.getRfc() + " || NumeroOperacion=" + devolucionNoProcesada.getNumeroOperacion()
                        + " || IdSaldoIcep="+devolucionNoProcesada.getIdSaldoIcep() 
                        + " || NumeroControl="+devolucionNoProcesada.getNumeroControl());
            }
            
            LOGGER.info(">>>>>>>>>>TERMINO DE EJECUTAR PjAutomaticasIVA v1.0 el dia " + formateador.format(new Date()) + "<<<<<<<<<<");
        } catch (DycAutomaticasIvaExcepcion ex) {
            LOGGER.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.AUTOMATICAS_GENERAL, null), ex);
        } catch(RuntimeException e) {
            LOGGER.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.AUTOMATICAS_GENERAL, null), e);
        }
    }

}
