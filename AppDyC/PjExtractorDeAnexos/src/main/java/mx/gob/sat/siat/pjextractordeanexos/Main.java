package mx.gob.sat.siat.pjextractordeanexos;

import mx.gob.sat.siat.pjextractordeanexos.service.ObtenerInformacionAProcesarService;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    
    private static final Integer CUARENTAYDOS=42;
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    public Main() {
        super();
    }
    
    public static void main(String[]args) {
        LOGGER.info(">>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE Extractor de anexos<<<<<<<<<<");
        
        try {
            LOGGER.info("JAHO - Obtiene informacion del contexto..");  
            ConfigurableApplicationContext appContext =  new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
            appContext.registerShutdownHook();
            LOGGER.info("JAHO - Crea el bean del servicio...");  
            ObtenerInformacionAProcesarService objeto = (ObtenerInformacionAProcesarService)appContext.getBean("obtenerInformacionAProcesarService");
            LOGGER.info("JAHO - Ejecuta el servicio...");  
            objeto.extraerDocumentos();
            System.exit(CUARENTAYDOS);
         
        } catch(Exception e) {
            LOGGER.error("Error de ejecucion en el proceso PjExtractorDeAnexos: "+e.getCause());   
        }
    }

}
