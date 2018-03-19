package pjretroalimentaciontesofe;

import mx.gob.sat.siat.dyc.tesofe.service.RetroalimentarTESOFEService;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    /**
     * Implementa el caso de uso EnviarRetroalimentacionDeLaTESOFE
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            LOGGER.info(">>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE PjRetroalimentacionTESOFE<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContextTESOFE.xml");
            appContext.registerShutdownHook();
            RetroalimentarTESOFEService casoCompe = (RetroalimentarTESOFEService)appContext.getBean("retroalimentarTESOFEService");
            
            casoCompe.retroalimentarTESOFE();
            
        } catch(Exception e) {
            LOGGER.error("Error al ejecutar el programa: "+e.getCause()+", error: "+e);
        }
    }

}
