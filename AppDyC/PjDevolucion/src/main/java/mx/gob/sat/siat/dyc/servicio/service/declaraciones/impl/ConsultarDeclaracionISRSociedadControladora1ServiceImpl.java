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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRSociedadControladora1DAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora2DTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarDeclaracionISRSociedadControladora1Service;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@Service(value = "declaracionISRSociedadControladora1Service")
public class ConsultarDeclaracionISRSociedadControladora1ServiceImpl implements ConsultarDeclaracionISRSociedadControladora1Service {

    private Logger logger = Logger.getLogger(ConsultarDeclaracionISRSociedadControladora1ServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionISRSociedadControladora1DAO consultarDeclaracionISRSociedadControladora1DAO;

    @Override
    public List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1(ConsultarDeclaracionISRSociedadControladora1DTO declaracionISRSociedadControladora1DTO) {
        List<ConsultarDeclaracionISRSociedadControladora1DTO> declaracionISRSociedadControladora1List =
            new ArrayList<ConsultarDeclaracionISRSociedadControladora1DTO>();
        try {
            declaracionISRSociedadControladora1List =
                    consultarDeclaracionISRSociedadControladora1DAO.consultarDeclaracionISRSociedadControladora1(declaracionISRSociedadControladora1DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRSociedadControladora1List;
    }

    @Override
    public List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2(ConsultarDeclaracionISRSociedadControladora2DTO declaracionISRSociedadControladora2DTO) {
        List<ConsultarDeclaracionISRSociedadControladora2DTO> declaracionISRSociedadControladora2List =
            new ArrayList<ConsultarDeclaracionISRSociedadControladora2DTO>();
        try {
            declaracionISRSociedadControladora2List =
                    consultarDeclaracionISRSociedadControladora1DAO.consultarDeclaracionISRSociedadControladora2(declaracionISRSociedadControladora2DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRSociedadControladora2List;
    }

    public void setConsultarDeclaracionISRSociedadControladora1DAO(ConsultarDeclaracionISRSociedadControladora1DAO consultarDeclaracionISRSociedadControladora1Dao) {
        this.consultarDeclaracionISRSociedadControladora1DAO = consultarDeclaracionISRSociedadControladora1Dao;
    }

    public ConsultarDeclaracionISRSociedadControladora1DAO getConsultarDeclaracionISRSociedadControladora1DAO() {
        return consultarDeclaracionISRSociedadControladora1DAO;
    }

}
