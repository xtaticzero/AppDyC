/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.service.cpr;


import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;

import org.xml.sax.SAXException;


/**
 * @author Israel Chavez
 */
public interface CPRService {
    
    CPRDTO obtieneCPR(CPRDTO cprDto);
    
    byte[] obtieneCprXmlByteArray(CPRDTO cprDto) throws JAXBException, SAXException ;
    
    
}
