/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.service.diot.impl;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.servicio.dao.diot.DiotDAO;
import mx.gob.sat.siat.dyc.servicio.service.diot.DiotService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesXSD;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.xml.sax.SAXException;


/**
 * TODO
 * @author Israel Chavez
 */
@Service(value = "diotService")
public class DiotServiceImpl implements DiotService {

    private static final Logger LOGGER = Logger.getLogger(DiotServiceImpl.class);

    @Autowired
    private DiotDAO diotDAO;

    @Override
    public DiotDTO obtieneDiotSp(DiotDTO diotDTO) throws SIATException {
        DiotDTO diot = null;
        try {
            diot = diotDAO.obtieneDiotSP(diotDTO);
        } catch (DataAccessException dae) {
            throw new SIATException(dae);
        } catch (SQLException sqle) {
            throw new SIATException(sqle);
        }
        return diot;
    }

    public byte[] obtieneDiotSpXMLByteArray(DiotDTO diotDTO) throws JAXBException, SAXException {

        DiotDTO diot = null;

        try {
            diot = diotDAO.obtieneDiotSP(diotDTO);
        } catch (SQLException e) {
            LOGGER.error(e.getCause());
        }
        return diot.generateXML(ConstantesXSD.DIOT_XSD);

    }

}

