/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.creditosfiscales.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.creditosfiscales.ConsultarDetalleCreditosFiscalesDAO;
import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;
import mx.gob.sat.siat.dyc.servicio.service.creditosfiscales.ConsultarDetalleCreditosFiscalesService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 25/07/2013
 *
 */
@Service(value = "consultarDetalleCreditosFiscalesService")
public class ConsultarDetalleCreditosFiscalesServiceImpl implements ConsultarDetalleCreditosFiscalesService {

    private Logger logger = Logger.getLogger(ConsultarDetalleCreditosFiscalesServiceImpl.class);

    @Autowired
    private ConsultarDetalleCreditosFiscalesDAO consultarDetalleCreditosFiscalesDAO;

    @Override
    public List<ConsultarDetalleCreditosFiscalesDTO> consultaDetalleCreditosFiscales(ConsultarDetalleCreditosFiscalesDTO consultarDetalleCreditosFiscalesDto) {
        List<ConsultarDetalleCreditosFiscalesDTO> detalleCreditosArray =
            new ArrayList<ConsultarDetalleCreditosFiscalesDTO>();
        try {
            detalleCreditosArray =
                    consultarDetalleCreditosFiscalesDAO.consultaDetalleCreditosFiscales(consultarDetalleCreditosFiscalesDto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleCreditosArray;
    }

    public void setConsultarDetalleCreditosFiscalesDAO(ConsultarDetalleCreditosFiscalesDAO consultarDetalleCreditosFiscalesDao) {
        this.consultarDetalleCreditosFiscalesDAO = consultarDetalleCreditosFiscalesDao;
    }

    public ConsultarDetalleCreditosFiscalesDAO getConsultarDetalleCreditosFiscalesDAO() {
        return consultarDetalleCreditosFiscalesDAO;
    }

}
