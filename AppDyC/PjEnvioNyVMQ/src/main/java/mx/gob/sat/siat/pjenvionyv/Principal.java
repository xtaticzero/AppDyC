package mx.gob.sat.siat.pjenvionyv;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christian.ventura
 */
public final class Principal {

    private static final Logger LOGGER = Logger.getLogger(Principal.class);

    private Principal() {
    }

    public static void main(String args[]) {
        ClassPathXmlApplicationContext appContext
                = new ClassPathXmlApplicationContext("applicationContextEnvioNyV.xml");
        LOGGER.debug("isRunning:" + appContext.isRunning());
    }

}
