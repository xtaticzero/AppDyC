package mx.gob.sat.siat.dyc.pjadminprocesosdyc.ejb.main;

import weblogic.cluster.singleton.SingletonService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christian.ventura
 */
public class SingletonServiceMain implements SingletonService {

    private final Logger logger = Logger.getLogger(SingletonServiceMain.class);
    
    /**
     *
     */
    @Override
    public void activate() {
        try {
            logger.info("SingletonServiceMain activate()...");
            System.setProperty("mail.mime.charset", "utf8");
            ClassPathXmlApplicationContext applicationContext = 
                 new ClassPathXmlApplicationContext("ejb-application-context.xml");
            applicationContext.registerShutdownHook();
            logger.info("applicationContext:"+applicationContext.isRunning());
        } catch (BeansException e) {
            logger.error("***** ERROR SingletonServiceMain ****** : " + e);
        }
    }

    /**
     *
     */
    @Override
    public void deactivate() {
        logger.info("SingletonServiceMain deactivate()....");
    }

}
