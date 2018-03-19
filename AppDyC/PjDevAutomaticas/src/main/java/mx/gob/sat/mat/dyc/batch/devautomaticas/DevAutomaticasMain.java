package mx.gob.sat.mat.dyc.batch.devautomaticas;

import mx.gob.sat.mat.dyc.batch.devautomaticas.service.DycAutomaticasDel;
import mx.gob.sat.mat.dyc.batch.devautomaticas.util.FechaUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Implementa el caso de uso Devolucion automatica.
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public final class DevAutomaticasMain {

    private static final Logger LOGGER = Logger.getLogger(DevAutomaticasMain.class);

    private DevAutomaticasMain() {
    }

    public static void main(String args[]) {
        try {            
            LOGGER.info(">>>>>>>>>>EJECUTANDO PjDevAutomaticas v1.1 el dia " + FechaUtil.getFechaHora() + "<<<<<<<<<<");

            ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            appContext.registerShutdownHook();
            DycAutomaticasDel dycAutomaticasDel = (DycAutomaticasDel) appContext.getBean("dycAutomaticasDel");
            dycAutomaticasDel.exec();

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
