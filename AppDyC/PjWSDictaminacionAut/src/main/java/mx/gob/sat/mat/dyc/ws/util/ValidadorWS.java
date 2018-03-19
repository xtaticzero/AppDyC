package mx.gob.sat.mat.dyc.ws.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import mx.gob.sat.mat.dyc.ws.DictaminacionAut;
import mx.gob.sat.mat.dyc.ws.domain.ServiceFault;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.siat.dyc.domain.devolucionaut.constante.MarcResultado;
import mx.gob.sat.mat.dyc.ws.util.constante.TipoExcepcion;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public final class ValidadorWS {

    private static final Logger LOGGER = Logger.getLogger(ValidadorWS.class);

    private ValidadorWS() {
    }

    public static void validarRequest(SOAPBody soapBody, String nombreOperacion) throws ServiceException {

        Method operacion = null;
        for (Method method : DictaminacionAut.class.getDeclaredMethods()) {
            if (method.getName().equals(nombreOperacion)) {
                operacion = method;
                break;
            }
        }

        if (operacion == null) {
            throw new ServiceException(TipoExcepcion.DATA_OPERATION.getCodigo(), TipoExcepcion.DATA_OPERATION.getDescripcion() + " '" + nombreOperacion + "'");

        } else {
            SOAPBodyElement nodoOperacion = buscarNodoEnArbol(soapBody.getChildElements(), operacion.getName());
            
            Class<?> classDTO = operacion.getParameterTypes()[0];
            validarParametro(nodoOperacion, classDTO);
        }
    }

    private static void validarParametro(SOAPBodyElement nodoOperacion, Class<?> classDTO) throws ServiceException {
        SOAPBodyElement nodoParametro = buscarNodoEnArbol(nodoOperacion.getChildElements(), classDTO.getSimpleName());

        if (nodoParametro == null) {
            throw new ServiceException(TipoExcepcion.DATA_PARAMETER.getCodigo(), TipoExcepcion.DATA_PARAMETER.getDescripcion() + " '" + classDTO.getSimpleName() + "'");

        } else {

            String pathXSD = "/" + classDTO.getName().replace(".", "/").replace("domain", "xsd") + ".xsd";
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream xsdDTO = ValidadorWS.class.getResourceAsStream(pathXSD);
            
            if (xsdDTO != null) {
                try {
                    Schema schema = sf.newSchema(new StreamSource(xsdDTO));
                    Validator validator = schema.newValidator();
                    validator.validate(new DOMSource(nodoParametro));

                    if (nodoOperacion.getLocalName().equals("obtenerResulDictAut")) {
                        validarObtenerResulDictAut(nodoParametro);
                    }

                } catch (SAXException e) {
                    LOGGER.error("SAXException " + TipoExcepcion.DATA_VALUE.getCodigo() + ":: " + e.getMessage());
                    String nodoError = buscarNodoError(e.getMessage(), classDTO);
                    SOAPBodyElement element = buscarNodoEnArbol(nodoParametro.getChildElements(), nodoError);

                    ServiceFault serviceFault;
                    if (element == null) {
                        serviceFault = new ServiceFault(TipoExcepcion.DATA_ELEMENT.getCodigo(), TipoExcepcion.DATA_ELEMENT.getDescripcion() + " '" + nodoError + "' ");
                    } else {
                        serviceFault = new ServiceFault(TipoExcepcion.DATA_VALUE.getCodigo(), TipoExcepcion.DATA_VALUE.getDescripcion() + (element.getValue() == null ? "" : " '" + element.getValue() + "'") + " PARA EL ELEMENTO '" + element.getLocalName() + "' ");
                    }

                    throw new ServiceException(serviceFault.getFaultString(), serviceFault, e);
                } catch (IOException e) {
                    LOGGER.error("IOException error: " + e.getMessage());
                }
            }
        }
    }

    private static void validarObtenerResulDictAut(SOAPBodyElement nodoParametro) throws ServiceException {
        SOAPBodyElement marcResultado = buscarNodoEnArbol(nodoParametro.getChildElements(), "MarcResultado");
        if (marcResultado.getValue().equals(MarcResultado.CON_RIESGO.getCodigo())) {
            SOAPBodyElement motRiesgo = buscarNodoEnArbol(nodoParametro.getChildElements(), "MotRiesgo");
            if (motRiesgo.getValue() == null || motRiesgo.getValue().isEmpty()) {
                throw new ServiceException(TipoExcepcion.DATA_REQUIRED.getCodigo(), TipoExcepcion.DATA_REQUIRED.getDescripcion() + " PARA EL ELEMENTO '" + motRiesgo.getLocalName() + "' ");
            }
        }
    }

    private static SOAPBodyElement buscarNodoEnArbol(Iterator ite, String nombreNodo) {

        if (ite != null) {
            SOAPBodyElement soapElement;
            Object objTemp;
            while (ite.hasNext()) {
                objTemp = ite.next();
                if (objTemp instanceof SOAPBodyElement) {
                    soapElement = (SOAPBodyElement) objTemp;
                    if (soapElement.getLocalName().equals(nombreNodo)) {
                        return soapElement;
                    }
                }
            }
        }

        return null;
    }

    private static String buscarNodoError(String mensajeError, Class<?> classDTO) {
        String llaveIni = "";
        String llaveFin = "";
        if (mensajeError.contains("cvc-complex-type")) {
            llaveIni = "{";
            llaveFin = "}";
        }

        XmlElement xmlElement;
        for (Field field : classDTO.getDeclaredFields()) {
            xmlElement = (XmlElement) field.getAnnotation(XmlElement.class);
            if (mensajeError.contains(llaveIni + xmlElement.name() + llaveFin)) {
                return xmlElement.name();
            }
        }
        return null;
    }
}
