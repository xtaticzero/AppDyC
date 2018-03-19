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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317cDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarDeclaracionISRPersonaFisica1317cService;

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
@Service(value = "consultarDeclaracionISRPersonaFisica13_17cService")
public class ConsultarDeclaracionISRPersonaFisica1317cServiceImpl implements ConsultarDeclaracionISRPersonaFisica1317cService {

    private Logger logger = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317cServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionISRPersonaFisica1317cDAO consultarDeclaracionISRPersonaFisica1317DAO;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO> consultaDeclaracionesIsrForma13(ConsultarDeclaracionISRPersonaFisica1317cForma13DTO declaracionISRPersonaFisica1317cForma13DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO> declaracionISRPersonaFisica1317cForma13Array =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO>();
        try {
            declaracionISRPersonaFisica1317cForma13Array =
                    consultarDeclaracionISRPersonaFisica1317DAO.consultaDeclaracionesIsrForma13(declaracionISRPersonaFisica1317cForma13DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317cForma13Array;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO> consultaDeclaracionesIsrForma13a(ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO declaracionISRPersonaFisica1317cForma13aDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO> declaracionISRPersonaFisica1317cForma13aArray =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO>();
        try {
            declaracionISRPersonaFisica1317cForma13aArray =
                    consultarDeclaracionISRPersonaFisica1317DAO.consultaDeclaracionesIsrForma13a(declaracionISRPersonaFisica1317cForma13aDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317cForma13aArray;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO> consultaDeclaracionesIsrFormaDid(ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO declaracionISRPersonaFisica1317cForma13DidDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO> declaracionISRPersonaFisica1317cForma13DidArray =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO>();
        try {
            declaracionISRPersonaFisica1317cForma13DidArray =
                    consultarDeclaracionISRPersonaFisica1317DAO.consultaDeclaracionesIsrFormaDid(declaracionISRPersonaFisica1317cForma13DidDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317cForma13DidArray;
    }

    public void setConsultarDeclaracionISRPersonaFisica1317DAO(ConsultarDeclaracionISRPersonaFisica1317cDAO consultarDeclaracionISRPersonaFisica1317cDao) {
        this.consultarDeclaracionISRPersonaFisica1317DAO = consultarDeclaracionISRPersonaFisica1317cDao;
    }

    public ConsultarDeclaracionISRPersonaFisica1317cDAO getConsultarDeclaracionISRPersonaFisica1317DAO() {
        return consultarDeclaracionISRPersonaFisica1317DAO;
    }

}
