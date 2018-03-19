/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.pagos.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.pagos.ConsultarPagosElectronicosDAO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf02DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoORADTO;
import mx.gob.sat.siat.dyc.servicio.service.pagos.ConsultarPagosElectronicosService;

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
@Service(value = "consultarPagosElectronicosService")
public class ConsultarPagosElectronicosServiceImpl implements ConsultarPagosElectronicosService {

    private Logger logger = Logger.getLogger(ConsultarPagosElectronicosServiceImpl.class);

    @Autowired
    private ConsultarPagosElectronicosDAO consultarPagosElectronicosDAO;

    @Override
    public List<ConsultarPagosElectronicosAnioDTO> consultaRegistrosPorAnno(ConsultarPagosElectronicosAnioDTO consultarPagosElectronicosAnioDto) {
        List<ConsultarPagosElectronicosAnioDTO> pagosAnnoArray = new ArrayList<ConsultarPagosElectronicosAnioDTO>();
        try {
            pagosAnnoArray = consultarPagosElectronicosDAO.consultaRegistrosPorAnio(consultarPagosElectronicosAnioDto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return pagosAnnoArray;
    }

    @Override
    public List<InformacionDePagoInf01DTO> informacionDePagoInf01(InformacionDePagoInf01DTO consultarInformacionDePagoInf01Dto) {
        List<InformacionDePagoInf01DTO> pagosInf01Array = new ArrayList<InformacionDePagoInf01DTO>();
        try {
            pagosInf01Array = consultarPagosElectronicosDAO.informacionDePagoInf01(consultarInformacionDePagoInf01Dto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return pagosInf01Array;
    }

    @Override
    public List<InformacionDePagoInf02DTO> informacionDePagoInf02(InformacionDePagoInf02DTO consultarInformacionDePagoInf02Dto) {
        List<InformacionDePagoInf02DTO> pagosInf02Array = new ArrayList<InformacionDePagoInf02DTO>();
        try {
            pagosInf02Array = consultarPagosElectronicosDAO.informacionDePagoInf02(consultarInformacionDePagoInf02Dto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return pagosInf02Array;
    }

    @Override
    public List<InformacionDePagoORADTO> informacionDePagoOra(InformacionDePagoORADTO consultarInformacionDePagoORADto) {
        List<InformacionDePagoORADTO> pagosOraArray = new ArrayList<InformacionDePagoORADTO>();
        try {
            pagosOraArray = consultarPagosElectronicosDAO.informacionDePagoOra(consultarInformacionDePagoORADto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return pagosOraArray;
    }

    public void setConsultarPagosElectronicosDAO(ConsultarPagosElectronicosDAO consultarPagosElectronicosDao) {
        this.consultarPagosElectronicosDAO = consultarPagosElectronicosDao;
    }

    public ConsultarPagosElectronicosDAO getConsultarPagosElectronicosDAO() {
        return consultarPagosElectronicosDAO;
    }

}
