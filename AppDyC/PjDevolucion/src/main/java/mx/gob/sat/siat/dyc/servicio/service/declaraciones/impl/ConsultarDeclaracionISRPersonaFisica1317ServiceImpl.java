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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317DAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarDeclaracionISRPersonaFisica1317Service;

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
@Service(value = "consultarDeclaracionISRPersonaFisica13_17Service")
public class ConsultarDeclaracionISRPersonaFisica1317ServiceImpl implements ConsultarDeclaracionISRPersonaFisica1317Service {

    private Logger logger = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317ServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionISRPersonaFisica1317DAO consultarDeclaracionISRPersonaFisica1317DAO;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO> consultaDeclaracionesIsrForma13Declarasat(ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO declaracionISRPersonaFisica1317DeclarasatDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO> declaracionISRPersonaFisica1317DeclarasatArray =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO>();
        try {
            declaracionISRPersonaFisica1317DeclarasatArray =
                    consultarDeclaracionISRPersonaFisica1317DAO.consultaDeclaracionesIsrForma13Declarasat(declaracionISRPersonaFisica1317DeclarasatDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317DeclarasatArray;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO> consultaDeclaracionesIsrForma13(ConsultarDeclaracionISRPersonaFisica1317Forma13DTO declaracionISRPersonaFisica1317Forma13DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO> declaracionISRPersonaFisica1317Forma13Array =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO>();
        try {
            declaracionISRPersonaFisica1317Forma13Array =
                    consultarDeclaracionISRPersonaFisica1317DAO.consultaDeclaracionesIsrForma13(declaracionISRPersonaFisica1317Forma13DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317Forma13Array;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO> consultaDeclaracionesIsrForma13a(ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO declaracionISRPersonaFisica1317Forma13aDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO> declaracionISRPersonaFisica1317Forma13aArray =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO>();
        try {
            declaracionISRPersonaFisica1317Forma13aArray =
                    consultarDeclaracionISRPersonaFisica1317DAO.consultaDeclaracionesIsrForma13a(declaracionISRPersonaFisica1317Forma13aDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317Forma13aArray;
    }

    public void setConsultarDeclaracionISRPersonaFisica1317DAO(ConsultarDeclaracionISRPersonaFisica1317DAO consultarDeclaracionISRPersonaFisica1317Dao) {
        this.consultarDeclaracionISRPersonaFisica1317DAO = consultarDeclaracionISRPersonaFisica1317Dao;
    }

    public ConsultarDeclaracionISRPersonaFisica1317DAO getConsultarDeclaracionISRPersonaFisica1317DAO() {
        return consultarDeclaracionISRPersonaFisica1317DAO;
    }

}
