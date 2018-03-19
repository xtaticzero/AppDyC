/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.mat.dyc.integrarexpediente.service;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;

import org.xml.sax.SAXException;


/**
 *
 * @author AdrianAguilar
 */
public interface IntegrarExpedienteService {
 
    void crearExpediente(DycpSolicitudDTO solicitudDTO, PersonaDTO persona, DiotDTO diot,SpConsultarAltexDTO altex) throws FileNotFoundException, ClassNotFoundException,
                                                                   SQLException, JAXBException, SAXException;
}
