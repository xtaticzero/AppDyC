/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.expediente;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;

import org.xml.sax.SAXException;


/**
 * Interface de servicio para la inserciòn de expedientes
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @version 0.2 J. Isaac Carbajal Bernal [ IsaacCB ]
 */
public interface IntegrarExpedienteService {

    void integrarExpediente(DycpSolicitudDTO dYCPSolicitudDTO) throws FileNotFoundException, ClassNotFoundException,
                                                                      SQLException, JAXBException, SAXException;

    void crearExpediente(DycpSolicitudDTO solicitudDTO, PersonaDTO persona, DiotDTO diot,SpConsultarAltexDTO altex) throws FileNotFoundException, ClassNotFoundException,
                                                                   SQLException, JAXBException, SAXException;

    void validaNumControl(Object tramiteDTO, int tipoServicio) throws FileNotFoundException, ClassNotFoundException,
                                                                      SQLException, JAXBException, SAXException;
}
