package mx.gob.sat.mat.dyc.ws.impl;

import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.util.ValidadorWS;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class DicAutHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = Logger.getLogger(DicAutHandler.class);

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        try {
            SOAPMessage soapMsg = context.getMessage();
            if (soapMsg != null) {
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPBody soapBody = soapEnv.getBody() == null ? soapEnv.addBody() : soapEnv.getBody();
                Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                if (!outboundProperty) {
                    try {
                        QName operacion = (QName) context.get(MessageContext.WSDL_OPERATION);
                        ValidadorWS.validarRequest(soapBody, operacion.getLocalPart());
                    } catch (ServiceException e) {
                        LOGGER.error("Handler ServiceException:: " + e.getMessage());
                        HttpSession httpSession = ((HttpServletRequest) context.get(MessageContext.SERVLET_REQUEST)).getSession();
                        httpSession.setAttribute("ServiceException", e);
                    }
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
