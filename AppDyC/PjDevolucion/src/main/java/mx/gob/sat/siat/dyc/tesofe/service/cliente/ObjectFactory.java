
package mx.gob.sat.siat.dyc.tesofe.service.cliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.sat.siat.dyc.tesofe.service.cliente package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName RETROALIMENTAPAGO_QNAME = new QName("http://impl.services.matdyc.retropago.pf.isr.sad.dyc.sat.gob.mx/", "retroalimentaPago");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.sat.siat.dyc.tesofe.service.cliente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RetroalimentaPago }
     * 
     */
    public RetroalimentaPago createRetroalimentaPago() {
        return new RetroalimentaPago();
    }

    /**
     * Create an instance of {@link RetroalimentaPagoMATDyCIn }
     * 
     */
    public RetroalimentaPagoMATDyCIn createRetroalimentaPagoMATDyCIn() {
        return new RetroalimentaPagoMATDyCIn();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetroalimentaPago }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.services.matdyc.retropago.pf.isr.sad.dyc.sat.gob.mx/", name = "retroalimentaPago")
    public JAXBElement<RetroalimentaPago> createRetroalimentaPago(RetroalimentaPago value) {
        return new JAXBElement<RetroalimentaPago>(RETROALIMENTAPAGO_QNAME, RetroalimentaPago.class, null, value);
    }

}
