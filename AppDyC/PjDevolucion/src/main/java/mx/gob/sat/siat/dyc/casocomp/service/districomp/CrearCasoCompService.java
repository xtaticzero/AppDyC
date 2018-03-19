package mx.gob.sat.siat.dyc.casocomp.service.districomp;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.xml.sax.SAXException;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * CrearCasoCompService
 * @author [LADP] Luis Alberto Dominguez Palomino
 *
 */

public interface CrearCasoCompService {

    void creaCasoCompensacion(CasoCompensacionVO declaraciones) throws SIATException, JAXBException, SAXException,
                                                                       FileNotFoundException, ClassNotFoundException,
                                                                       SQLException;


    ParamOutputTO<String> insertaCCyAContribuyente(String numControl, TramiteDTO persona) throws SIATException,
                                                                                                 JAXBException,
                                                                                                 SAXException;
    
    void bloquedeInserciones(CasoCompensacionVO casoCompensacionVO, TramiteDTO input) throws SIATException,
                                                                                                     JAXBException,
                                                                                                     SAXException,
                                                                                                     FileNotFoundException,
                                                                                                     ClassNotFoundException,
                                                                                                     SQLException ;
    

}
