package mx.gob.sat.siat.pjadministradorprocesosdyc;

import mx.gob.sat.siat.pjadministradorprocesosdyc.service.CrearProcesosService;

import org.apache.log4j.Logger;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main  {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    public static void main( String[] args ) {
        try {
            LOGGER.info(">>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE PjAdministradorProcesos<<<<<<<<<<");
            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
            appContext.registerShutdownHook();
            CrearProcesosService objetoServicio = (CrearProcesosService)appContext.getBean("crearProcesosService");

            objetoServicio.crearProcesos();
         
        } catch(Exception e) {
            LOGGER.error("Error al ejecutar el programa: "+e);
        }
    }

}
