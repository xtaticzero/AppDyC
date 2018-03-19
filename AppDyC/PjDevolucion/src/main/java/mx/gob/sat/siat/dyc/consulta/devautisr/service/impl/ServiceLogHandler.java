/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public class ServiceLogHandler implements SOAPHandler<SOAPMessageContext> {

    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public boolean handleMessage(final SOAPMessageContext context) {
        final SOAPMessage msg = context.getMessage();
        final boolean request = ((Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY));
        if (request) {
            // This is a request message.
            logger.error("This is a request message.");
        } else {
            // This is the response message
            logger.error("This is the response message.");
        }
        logMessage(msg);
        return true;
    }

    @Override
    public boolean handleFault(final SOAPMessageContext context) {
        logMessage(context.getMessage());
        return true;
    }

    private void logMessage(final SOAPMessage msg) {
        try {
            // Write the message to the output stream
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            msg.writeTo(baos);
            logger.error(baos.toString("UTF-8"));
            baos.close();
        } catch (Exception e) {
            logger.error("Caught exception: " + e.getMessage(), e);
        }
    }

    @Override
    public void close(final MessageContext context) {
        // Not required for logging
    }

    @Override
    public Set<QName> getHeaders() {
        // Not required for logging
        return null;
    }
}
