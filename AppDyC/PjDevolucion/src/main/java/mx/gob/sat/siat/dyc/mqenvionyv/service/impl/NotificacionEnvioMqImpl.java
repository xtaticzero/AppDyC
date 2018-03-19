package mx.gob.sat.siat.dyc.mqenvionyv.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import mx.gob.sat.siat.dyc.mqenvionyv.service.NotificacionEnvioMq;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


/**
 *
 * @author christian.ventura
 */
@Service
public class NotificacionEnvioMqImpl implements NotificacionEnvioMq{
    
    private static final Logger LOGGER = Logger.getLogger(NotificacionEnvioMqImpl.class);
    
    @Autowired(required = false)
    @Qualifier(value = "invoiceQueueTemplate")
    private JmsTemplate jmsTemplate;
    
    @Value("#{prop['weblogicQueue']?: 'mq' }")
    private String nombreQueue;
    
    /**
     * 
     * @param mensaje 
     */
    @Override
    public void envioMensajeMq(final String mensaje) {
        LOGGER.debug("envioMensajeMq:"+mensaje+", queue:"+nombreQueue);
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session sn) throws JMSException {
                return sn.createTextMessage(mensaje);
            }
        };
        
        if(null != jmsTemplate) {
            jmsTemplate.send(nombreQueue,messageCreator);   
        }
    }
    
}
