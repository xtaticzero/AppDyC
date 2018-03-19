package pjnotificacionesyverificaciones;

import mx.gob.sat.siat.dyc.comunica.service.ProcesarDatosARCAService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private Main() {
    }
    public static void main(String[]args) {
        LOGGER.info(">>>>>>>>>>EJECUTANDO LA VERSION 1.0 DE retroalimentacion de NyV<<<<<<<<<<");
        
        try {
            LOGGER.info("JAHO - Obtiene informacion del contexto..");  
            ConfigurableApplicationContext appContext =  new ClassPathXmlApplicationContext(new String[] {"applicationContextNyF.xml"});
            appContext.registerShutdownHook();
            LOGGER.info("JAHO - Crea el bean del servicio...");  
            ProcesarDatosARCAService casoCompe = (ProcesarDatosARCAService)appContext.getBean("procesarDatosARCAService");
            LOGGER.info("JAHO - Ejecuta el servicio...");  
            casoCompe.procesarDatosArca();
         
        } catch(BeansException e) {
            LOGGER.error("BeansException: Error de ejecucion en el proceso PjNotificacionesYVerificaciones: --->", e.getCause());   
        } catch (SIATException e) {
            LOGGER.error("SIATException: Error de ejecucion en el proceso PjNotificacionesYVerificaciones: --->", e.getCause());
        }
    }

}
