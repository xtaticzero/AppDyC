package pjgestionpagotesofe;

import java.util.Date;

import mx.gob.sat.siat.dyc.tesofe.service.GestionPagosTESOFEService;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    public Main() {
        super();
    }
    
    /**
     * Implementa el caso de uso EnviarRetroalimentacionDeLaTESOFE
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            LOGGER.info(">>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE PjGestionPagoTESOFE<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContextTESOFE.xml");
            appContext.registerShutdownHook();
            GestionPagosTESOFEService casoCompe = (GestionPagosTESOFEService)appContext.getBean("gestionPagosTESOFEService");

            casoCompe.gestionarPagoAnteTESOFE(new Date(), new Date());
         
        } catch(Exception e) {
            LOGGER.error("Error al ejecutar el programa PjGestionPagoTESOFE: "+e);
        }
    }

}
