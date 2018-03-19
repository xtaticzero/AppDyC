package mx.gob.sat.mat.dyc.ws.impl;

import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.*;
import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class DevAutHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = Logger.getLogger(DevAutHandler.class);

    private static final String ENCODING_UTF8 = "UTF-8";

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        try {
            SOAPMessage soapMsg = context.getMessage();
            if (soapMsg != null) {
                try {
                    Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                    if (!outboundProperty) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        soapMsg.writeTo(stream);
                        String message = new String(stream.toByteArray(), ENCODING_UTF8);
                        /**LOGGER.info(message);*/
                        
                        HttpSession httpSession = ((HttpServletRequest) context.get(MessageContext.SERVLET_REQUEST)).getSession();
                        httpSession.setAttribute("xmlRequestStream", stream);
                        httpSession.setAttribute("xmlRequestString", message);
                    }
                } catch (IOException e) {
                    LOGGER.error("Handler IOException:: " + e.getMessage());
                }
            }
        } catch (SOAPException e) {
            LOGGER.error("Handler SOAPException:: " + e.getMessage());
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return Boolean.TRUE;
    }

    @Override
    public void close(MessageContext context) {
    }
}
