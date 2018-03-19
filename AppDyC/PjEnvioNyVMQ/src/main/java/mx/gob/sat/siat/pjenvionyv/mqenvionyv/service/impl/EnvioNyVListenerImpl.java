package mx.gob.sat.siat.pjenvionyv.mqenvionyv.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import mx.gob.sat.siat.pjenvionyv.mqenvionyv.service.EnvioNyVListener;
import mx.gob.sat.siat.pjenvionyv.mqenvionyv.service.ProcesoEnvioNyVRunnable;
import mx.gob.sat.siat.pjenvionyv.service.RealizarEnvioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service
public class EnvioNyVListenerImpl implements EnvioNyVListener, MessageListener {

    private static final Logger LOGGER = Logger.getLogger(EnvioNyVListenerImpl.class);

    @Autowired
    @Qualifier("realizarEnvioService")
    private RealizarEnvioService realizarEnvioService;
    
    @Autowired
    @Qualifier("taskExecutorEnvioNyV")
    private ThreadPoolTaskExecutor taskExecutor;
    
    /**
     * 
     * @param message 
     */
    @Override
    public void onMessage(Message message) {
        LOGGER.debug("onMessage");
        try {
            String mensaje = ((TextMessage) message).getText();
            LOGGER.debug(mensaje);
            taskExecutor.execute(new ProcesoEnvioNyVRunnable(realizarEnvioService,mensaje));
        } catch (JMSException ex) {
            LOGGER.error(ex);
        }
    }
}