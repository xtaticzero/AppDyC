/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.devoluciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.devoluciones.ConsultarDevolucionesDAO;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;
import mx.gob.sat.siat.dyc.servicio.service.devoluciones.ConsultarDevolucionesService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 26/07/2013
 *
 */
@Service(value = "consultarDevolucionesService")
public class ConsultarDevolucionesServiceImpl implements ConsultarDevolucionesService {

    private Logger logger = Logger.getLogger(ConsultarDevolucionesServiceImpl.class);

    @Autowired
    private ConsultarDevolucionesDAO consultarDevolucionesDAO;

    @Override
    public List<ConsultarDevolucionesDTO> consultarDevoluciones(ConsultarDevolucionesDTO consultarDevolucionesDto) {
        List<ConsultarDevolucionesDTO> detalleDevoluciones = new ArrayList<ConsultarDevolucionesDTO>();
        try {
            detalleDevoluciones = consultarDevolucionesDAO.consultarDevoluciones(consultarDevolucionesDto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDevoluciones;
    }

    public void setConsultarDevolucionesDAO(ConsultarDevolucionesDAO consultarDevolucionesDao) {
        this.consultarDevolucionesDAO = consultarDevolucionesDao;
    }

    public ConsultarDevolucionesDAO getConsultarDevolucionesDAO() {
        return consultarDevolucionesDAO;
    }

}
