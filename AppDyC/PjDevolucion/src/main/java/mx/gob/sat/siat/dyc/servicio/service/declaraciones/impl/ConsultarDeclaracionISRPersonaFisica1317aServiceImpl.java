/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.declaraciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317aDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aDecDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarDeclaracionISRPersonaFisica1317aService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
@Service(value = "consultarDeclaracionISRPersonaFisica13_17aService")
public class ConsultarDeclaracionISRPersonaFisica1317aServiceImpl implements ConsultarDeclaracionISRPersonaFisica1317aService {

    private Logger logger = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317aServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionISRPersonaFisica1317aDAO consultarDeclaracionISRPersonaFisica1317aDAO;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO> consultaImpuestosIsrforma13ane(ConsultarDeclaracionISRPersonaFisica1317aForma13DTO declaracionISRPersonaFisica1317aForma13DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO> declaracionISRPersonaFisica1317aForma13Array =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO>();
        try {
            declaracionISRPersonaFisica1317aForma13Array =
                    consultarDeclaracionISRPersonaFisica1317aDAO.consultaImpuestosIsrforma13ane(declaracionISRPersonaFisica1317aForma13DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317aForma13Array;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO> consultaImpuestosIsrforma13aane(ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO declaracionISRPersonaFisica1317aForma13aDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO> declaracionISRPersonaFisica1317aForma13aArray =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO>();
        try {
            declaracionISRPersonaFisica1317aForma13aArray =
                    consultarDeclaracionISRPersonaFisica1317aDAO.consultaImpuestosIsrforma13aane(declaracionISRPersonaFisica1317aForma13aDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317aForma13aArray;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317aDecDTO> consultaImpuestosIsrdddecdida1dd1(ConsultarDeclaracionISRPersonaFisica1317aDecDTO declaracionISRPersonaFisica1317aDecDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317aDecDTO> declaracionISRPersonaFisica1317aDecArray =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317aDecDTO>();
        try {
            declaracionISRPersonaFisica1317aDecArray =
                    consultarDeclaracionISRPersonaFisica1317aDAO.consultaImpuestosIsrdddecdida1dd1(declaracionISRPersonaFisica1317aDecDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317aDecArray;
    }

    public void setConsultarDeclaracionISRPersonaFisica1317aDAO(ConsultarDeclaracionISRPersonaFisica1317aDAO consultarDeclaracionISRPersonaFisica1317aDao) {
        this.consultarDeclaracionISRPersonaFisica1317aDAO = consultarDeclaracionISRPersonaFisica1317aDao;
    }

    public ConsultarDeclaracionISRPersonaFisica1317aDAO getConsultarDeclaracionISRPersonaFisica1317aDAO() {
        return consultarDeclaracionISRPersonaFisica1317aDAO;
    }

}
