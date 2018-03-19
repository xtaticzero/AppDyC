/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.compensaciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.compensaciones.ConsultarCompensacionesDao;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;
import mx.gob.sat.siat.dyc.servicio.service.compensaciones.ConsultarCompensacionesService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 19/07/2013
 *
 */
@Service(value = "consultarCompensacionesService")
public class ConsultarCompensacionesServiceImpl implements ConsultarCompensacionesService {

    private Logger logger = Logger.getLogger(ConsultarCompensacionesServiceImpl.class);

    @Autowired
    private ConsultarCompensacionesDao consultarCompensacionesDAO;

    @Override
    public List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechas(ConsultarCompensacionesFechasDTO consultarCompensacionesFechasDto) {
        List<ConsultarCompensacionesFechasDTO> informacionRecuperada =
            new ArrayList<ConsultarCompensacionesFechasDTO>();
        try {
            informacionRecuperada =
                    consultarCompensacionesDAO.consultarCompensacionesFechas(consultarCompensacionesFechasDto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return informacionRecuperada;
    }

    @Override
    public List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacion(ConsultarCompensacionesInformacionDTO consultarCompensacionesInformacionDto) {
        List<ConsultarCompensacionesInformacionDTO> informacionRecuperada =
            new ArrayList<ConsultarCompensacionesInformacionDTO>();
        try {
            informacionRecuperada =
                    consultarCompensacionesDAO.consultarCompensacionesInformacionCompensaciones(consultarCompensacionesInformacionDto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return informacionRecuperada;
    }

    public void setConsultarCompensacionesDAO(ConsultarCompensacionesDao consultarCompensacionesDao) {
        this.consultarCompensacionesDAO = consultarCompensacionesDao;
    }

    public ConsultarCompensacionesDao getConsultarCompensacionesDAO() {
        return consultarCompensacionesDAO;
    }

}
