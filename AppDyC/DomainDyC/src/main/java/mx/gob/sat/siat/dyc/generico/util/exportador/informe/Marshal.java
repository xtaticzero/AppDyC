/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.generico.util.exportador.informe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;


/**
 *
 * @author Alfredo Ramirez
 * @since 12/07/2013
 *
 */
public class Marshal {
private static final String ENCODING_UTF8 = "UTF-8";
    public synchronized byte[] generateXML(String nameXsd) throws JAXBException, SAXException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("/siat/dyc/xsd/" + nameXsd));
        JAXBContext jc = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setSchema(schema);
        marshaller.marshal(this, os);
        return os.toByteArray();
    }

    public static synchronized String objectToXMLString(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_ENCODING, ENCODING_UTF8);
    
        StringWriter sw = new StringWriter();
        m.marshal(object, sw);
        
        return sw.toString();
    }
}
