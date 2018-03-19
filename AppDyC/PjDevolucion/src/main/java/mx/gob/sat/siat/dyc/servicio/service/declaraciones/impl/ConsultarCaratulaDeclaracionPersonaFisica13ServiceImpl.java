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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionPersonaFisica13DAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionfisica.ConsultarCaratulaDeclaracionPersonaFisica13DTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarCaratulaDeclaracionPersonaFisica13Service;

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
@Service(value = "consultarCaratulaDeclaracionPersonaFisica13Service")
public class ConsultarCaratulaDeclaracionPersonaFisica13ServiceImpl implements ConsultarCaratulaDeclaracionPersonaFisica13Service {

    private Logger logger = Logger.getLogger(ConsultarCaratulaDeclaracionPersonaFisica13ServiceImpl.class);

    @Autowired
    private ConsultarCaratulaDeclaracionPersonaFisica13DAO consultarCaratulaDeclaracionPersonaFisica13DAO;

    @Override
    public List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> consultaDatosDeclaracion(ConsultarCaratulaDeclaracionPersonaFisica13DTO consultarCaratulaDeclaracionPersonaFisica13DTO) {
        List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> declaracionPersonaFisica13Array =
            new ArrayList<ConsultarCaratulaDeclaracionPersonaFisica13DTO>();
        try {
            declaracionPersonaFisica13Array =
                    consultarCaratulaDeclaracionPersonaFisica13DAO.consultaDatosDeclaracion(consultarCaratulaDeclaracionPersonaFisica13DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionPersonaFisica13Array;
    }

    public void setConsultarCaratulaDeclaracionPersonaFisica13DAO(ConsultarCaratulaDeclaracionPersonaFisica13DAO consultarCaratulaDeclaracionPersonaFisica13Dao) {
        this.consultarCaratulaDeclaracionPersonaFisica13DAO = consultarCaratulaDeclaracionPersonaFisica13Dao;
    }

    public ConsultarCaratulaDeclaracionPersonaFisica13DAO getConsultarCaratulaDeclaracionPersonaFisica13DAO() {
        return consultarCaratulaDeclaracionPersonaFisica13DAO;
    }

}
