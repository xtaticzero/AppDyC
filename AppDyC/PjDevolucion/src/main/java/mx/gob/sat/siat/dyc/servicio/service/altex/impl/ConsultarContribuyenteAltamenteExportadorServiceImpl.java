/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.altex.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.servicio.dao.altex.ConsultarContribuyenteAltamenteExportadorDAO;
import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;
import mx.gob.sat.siat.dyc.servicio.service.altex.ConsultarContribuyenteAltamenteExportadorService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesXSD;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.xml.sax.SAXException;


/**
 *
 * @author Alfredo Ramirez
 * @since 25/07/2013
 * @modificacion Israel Chàvez 25/09/2013 (se añade metodos de consulta via SP.)
 */
@Service(value = "consultarContribuyenteAltamenteExportadorService")
public class ConsultarContribuyenteAltamenteExportadorServiceImpl implements ConsultarContribuyenteAltamenteExportadorService {

    private Logger logger = Logger.getLogger(ConsultarContribuyenteAltamenteExportadorServiceImpl.class);

    @Autowired
    private ConsultarContribuyenteAltamenteExportadorDAO consultarContribuyenteAltamenteExportadorDAO;

    @Override
    public List<ConsultarContribuyenteAltamenteExportadorDTO> consultarContribuyenteAltamenteExportador(ConsultarContribuyenteAltamenteExportadorDTO consultarContribuyenteAltamenteExportadorDto) {
        List<ConsultarContribuyenteAltamenteExportadorDTO> detalleContribuyenteALTEX =
            new ArrayList<ConsultarContribuyenteAltamenteExportadorDTO>();
        try {
            detalleContribuyenteALTEX =
                    consultarContribuyenteAltamenteExportadorDAO.consultarContribuyenteAltamenteExportador(consultarContribuyenteAltamenteExportadorDto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleContribuyenteALTEX;
    }

    /**
     * TODO
     * @param spConsultarAltexDTO
     */

    public SpConsultarAltexDTO obtieneDatosAltexSP(SpConsultarAltexDTO spConsultarAltexDTO) throws SIATException {

        SpConsultarAltexDTO spConsultarAltex =
                this.consultarContribuyenteAltamenteExportadorDAO.obtieneDatosAltexSP(spConsultarAltexDTO);

        return spConsultarAltex;
    }

    public byte[] obtieneDatosAltexXMLByteArray(SpConsultarAltexDTO spConsultarAltexDTO) throws JAXBException,
                                                                                                SAXException,
                                                                                                SIATException {

        SpConsultarAltexDTO spConsultarAltex =
                this.consultarContribuyenteAltamenteExportadorDAO.obtieneDatosAltexSP(spConsultarAltexDTO);

        return spConsultarAltex.generateXML(ConstantesXSD.ALTEX_XSD);


    }

    public void setConsultarContribuyenteAltamenteExportadorDAO(ConsultarContribuyenteAltamenteExportadorDAO consultarContribuyenteAltamenteExportadorDao) {
        this.consultarContribuyenteAltamenteExportadorDAO = consultarContribuyenteAltamenteExportadorDao;
    }

    public ConsultarContribuyenteAltamenteExportadorDAO getConsultarContribuyenteAltamenteExportadorDAO() {
        return consultarContribuyenteAltamenteExportadorDAO;
    }

}
