/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.service.diot;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.xml.sax.SAXException;


/**
 * @author Israel Chavez
 */
public interface DiotService {

    DiotDTO obtieneDiotSp(DiotDTO diotDTO) throws SIATException;

    byte[] obtieneDiotSpXMLByteArray(DiotDTO diotDTO) throws JAXBException, SAXException;

}
