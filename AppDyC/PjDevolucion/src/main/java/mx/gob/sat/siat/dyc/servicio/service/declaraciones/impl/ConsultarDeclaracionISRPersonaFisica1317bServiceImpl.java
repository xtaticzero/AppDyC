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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317bDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarDeclaracionISRPersonaFisica1317bService;

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
@Service(value = "consultarDeclaracionISRPersonaFisica13_17bService")
public class ConsultarDeclaracionISRPersonaFisica1317bServiceImpl implements ConsultarDeclaracionISRPersonaFisica1317bService {

    private Logger logger = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317bServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionISRPersonaFisica1317bDAO consultarDeclaracionISRPersonaFisica1317bDAO;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO> consultaConsultaDeclaracionesIsrAnexo5a2(ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO declaracionISRPersonaFisica1317bAnexo5a2DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO> declaracionISRPersonaFisica1317bAnexo5a2Array =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO>();
        try {
            declaracionISRPersonaFisica1317bAnexo5a2Array =
                    consultarDeclaracionISRPersonaFisica1317bDAO.consultaConsultaDeclaracionesIsrAnexo5a2(declaracionISRPersonaFisica1317bAnexo5a2DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317bAnexo5a2Array;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO> consultaconsultaConsultaDeclaracionesIsrAnexo1(ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO declaracionISRPersonaFisica1317bAnexo1DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO> declaracionISRPersonaFisica1317bAnexo1Array =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO>();
        try {
            declaracionISRPersonaFisica1317bAnexo1Array =
                    consultarDeclaracionISRPersonaFisica1317bDAO.consultaconsultaConsultaDeclaracionesIsrAnexo1(declaracionISRPersonaFisica1317bAnexo1DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionISRPersonaFisica1317bAnexo1Array;
    }

    public void setConsultarDeclaracionISRPersonaFisica1317bDAO(ConsultarDeclaracionISRPersonaFisica1317bDAO consultarDeclaracionISRPersonaFisica1317bDao) {
        this.consultarDeclaracionISRPersonaFisica1317bDAO = consultarDeclaracionISRPersonaFisica1317bDao;
    }

    public ConsultarDeclaracionISRPersonaFisica1317bDAO getConsultarDeclaracionISRPersonaFisica1317bDAO() {
        return consultarDeclaracionISRPersonaFisica1317bDAO;
    }

}
