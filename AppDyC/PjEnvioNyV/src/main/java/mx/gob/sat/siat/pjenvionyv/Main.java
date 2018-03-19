package mx.gob.sat.siat.pjenvionyv;

import mx.gob.sat.siat.pjenvionyv.service.EnvioANyVService;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    public Main() {
        super();
    }
    
    public static void main (String args[]) {
        try {
            LOGGER.info(">>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE Enviar a NyV<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContextEnvioNyV.xml");
            appContext.registerShutdownHook();
            EnvioANyVService enviarANyV = (EnvioANyVService)appContext.getBean("envioANyVService");
            enviarANyV.buscarDocumentosAEnviar();
            System.exit(0);
        } catch(Exception e) {
            LOGGER.error("Error al ejecutar el programa PjEnvioNyV: "+e);
        }
    }

}
