package mx.gob.sat.siat.dyc.tesofe.service.cliente;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import org.apache.log4j.Logger;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.8 Generated source
 * version: 2.2
 *
 */
@WebServiceClient(name = "MATSADRetroalimentaPagoService", targetNamespace = "http://impl.services.matdyc.retropago.pf.isr.sad.dyc.sat.gob.mx/", wsdlLocation = "file:/home/usuario/Escritorio/MATSADRetroalimentaPagoService_1.wsdl")
public class MATSADRetroalimentaPagoService
        extends Service {

    private static final Logger LOG = Logger.getLogger(MATSADRetroalimentaPagoService.class);

    private static final String QNAMETARGET = "http://impl.services.matdyc.retropago.pf.isr.sad.dyc.sat.gob.mx/";
    private static final String RETROALIMENTAPAGOSERVICEIMPLPORT = "RetroalimentaPagoMATDyCServiceImplPort";
    private static final URL MATSADRETROALIMENTAPAGOSERVICE_WSDL_LOCATION;
    private static final WebServiceException MATSADRETROALIMENTAPAGOSERVICE_EXCEPTION;
    private static final QName MATSADRETROALIMENTAPAGOSERVICE_QNAME = new QName(QNAMETARGET, "MATSADRetroalimentaPagoService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            Properties prop = new Properties();
            InputStream input = null;
            String pUrl = null;
            try {
                String propiedad = "retropago.tesofe";
                input = new FileInputStream("/siat/dyc/configuraciondyc/automaticasdyc.properties");
                prop.load(input);
                pUrl = prop.getProperty(propiedad);
            } catch (IOException ioe) {
                LOG.error("Error abrir archivo properties: automaticasdyc.properties " + ioe.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ioe2) {
                        LOG.error("Error abrir archivo properties: automaticasdyc.properties " + ioe2.getMessage());
                    }
                }
            }

            url = new URL(pUrl);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MATSADRETROALIMENTAPAGOSERVICE_WSDL_LOCATION = url;
        MATSADRETROALIMENTAPAGOSERVICE_EXCEPTION = e;
    }

    public MATSADRetroalimentaPagoService() {
        super(getWsdlLocation(), MATSADRETROALIMENTAPAGOSERVICE_QNAME);
    }

    public MATSADRetroalimentaPagoService(URL wsdlLocation) {
        super(wsdlLocation, MATSADRETROALIMENTAPAGOSERVICE_QNAME);
    }

    public MATSADRetroalimentaPagoService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return returns RetroalimentaPagoMATDyCServiceImpl
     */
    @WebEndpoint(name = RETROALIMENTAPAGOSERVICEIMPLPORT)
    public RetroalimentaPagoMATDyCServiceImpl getRetroalimentaPagoMATDyCServiceImplPort() {
        return super.getPort(new QName(QNAMETARGET, RETROALIMENTAPAGOSERVICEIMPLPORT), RetroalimentaPagoMATDyCServiceImpl.class);
    }

    /**
     *
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
     * configure on the proxy. Supported features not in the
     * <code>features</code> parameter will have their default values.
     * @return returns RetroalimentaPagoMATDyCServiceImpl
     */
    @WebEndpoint(name = RETROALIMENTAPAGOSERVICEIMPLPORT)
    public RetroalimentaPagoMATDyCServiceImpl getRetroalimentaPagoMATDyCServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName(QNAMETARGET, RETROALIMENTAPAGOSERVICEIMPLPORT), RetroalimentaPagoMATDyCServiceImpl.class, features);
    }

    private static URL getWsdlLocation() {
        if (MATSADRETROALIMENTAPAGOSERVICE_EXCEPTION != null) {
            throw MATSADRETROALIMENTAPAGOSERVICE_EXCEPTION;
        }
        return MATSADRETROALIMENTAPAGOSERVICE_WSDL_LOCATION;
    }

}
