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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeterminacionDeImpuestosDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionDeImpuestosCdiDpdifDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiDpdifAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiImpuestosDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDID3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDidDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma132DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma1EDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma22DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2a2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2aDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma32DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosDID2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosForma3DTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarDeterminacionDeImpuestosService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 31/07/2013
 *
 */
@Service(value = "consultarDeterminacionDeImpuestosService")
public class ConsultarDeterminacionDeImpuestosServiceImpl implements ConsultarDeterminacionDeImpuestosService {

    private Logger logger = Logger.getLogger(ConsultarDeterminacionDeImpuestosServiceImpl.class);

    @Autowired
    private ConsultarDeterminacionDeImpuestosDAO consultarDeterminacionDeImpuestosDAO;

    @Override
    public List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImpuestosCdiDpdif(ConsultarDeterminacionDeImpuestosCdiDpdifDTO consultarDeterminacionDeImpuestosCdiDpdifDTO) {
        List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> detalleDeterminacionDeImpto =
            new ArrayList<ConsultarDeterminacionDeImpuestosCdiDpdifDTO>();
        try {
            detalleDeterminacionDeImpto =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionDeImpuestosCdiDpdif(consultarDeterminacionDeImpuestosCdiDpdifDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionDeImpto;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionDeImpuestosCdiDpdifAnio(ConsultarDeterminacionImpuestoCdiDpdifAnioDTO determinacionImpuestoCdiDpdifAnioDTO) {
        List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> detalleDeterminacionImpuestoCdiDpdifAnioDTO =
            new ArrayList<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO>();
        try {
            detalleDeterminacionImpuestoCdiDpdifAnioDTO =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionDeImpuestosCdiDpdifAnio(determinacionImpuestoCdiDpdifAnioDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoCdiDpdifAnioDTO;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestos(ConsultarDeterminacionImpuestoCdiImpuestosDTO determinacionImpuestoCdiImpuestosDTO) {
        List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> detalleDeterminacionImpuestoCdiImpuestos =
            new ArrayList<ConsultarDeterminacionImpuestoCdiImpuestosDTO>();
        try {
            detalleDeterminacionImpuestoCdiImpuestos =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoCdiImpuestos(determinacionImpuestoCdiImpuestosDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoCdiImpuestos;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13(ConsultarDeterminacionImpuestoForma13DTO determinacionImpuestoForma13DTO) {
        List<ConsultarDeterminacionImpuestoForma13DTO> detalleDeterminacionImpuestoForma13 =
            new ArrayList<ConsultarDeterminacionImpuestoForma13DTO>();
        try {
            detalleDeterminacionImpuestoForma13 =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma13(determinacionImpuestoForma13DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma13;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13A(ConsultarDeterminacionImpuestoForma13ADTO determinacionImpuestoForma13ADTO) {
        List<ConsultarDeterminacionImpuestoForma13ADTO> detalleDeterminacionImpuestoForma13A =
            new ArrayList<ConsultarDeterminacionImpuestoForma13ADTO>();
        try {
            detalleDeterminacionImpuestoForma13A =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma13A(determinacionImpuestoForma13ADTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma13A;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDid(ConsultarDeterminacionImpuestoDidDTO determinacionImpuestoDidDTO) {
        List<ConsultarDeterminacionImpuestoDidDTO> detalleDeterminacionImpuestoDidDTO =
            new ArrayList<ConsultarDeterminacionImpuestoDidDTO>();
        try {
            detalleDeterminacionImpuestoDidDTO =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoDid(determinacionImpuestoDidDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoDidDTO;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2(ConsultarDeterminacionImpuestoForma2DTO determinacionImpuestoForma2DTO) {
        List<ConsultarDeterminacionImpuestoForma2DTO> detalleDeterminacionImpuestoForma2List =
            new ArrayList<ConsultarDeterminacionImpuestoForma2DTO>();
        try {
            detalleDeterminacionImpuestoForma2List =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma2(determinacionImpuestoForma2DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma2List;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2a(ConsultarDeterminacionImpuestoForma2aDTO determinacionImpuestoForma2aDTO) {
        List<ConsultarDeterminacionImpuestoForma2aDTO> detalleDeterminacionImpuestoForma2aList =
            new ArrayList<ConsultarDeterminacionImpuestoForma2aDTO>();
        try {
            detalleDeterminacionImpuestoForma2aList =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma2a(determinacionImpuestoForma2aDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma2aList;
    }

    @Override
    public List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3(ConsultarDeterminacionImpuestosForma3DTO determinacionImpuestosForma3DTO) {
        List<ConsultarDeterminacionImpuestosForma3DTO> detalleDeterminacionImpuestosForma3List =
            new ArrayList<ConsultarDeterminacionImpuestosForma3DTO>();
        try {
            detalleDeterminacionImpuestosForma3List =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestosForma3(determinacionImpuestosForma3DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestosForma3List;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132(ConsultarDeterminacionImpuestoForma132DTO determinacionImpuestoForma132DTO) {
        List<ConsultarDeterminacionImpuestoForma132DTO> detalleDeterminacionImpuestoForma132List =
            new ArrayList<ConsultarDeterminacionImpuestoForma132DTO>();
        try {
            detalleDeterminacionImpuestoForma132List =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma132(determinacionImpuestoForma132DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma132List;
    }

    @Override
    public List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2(ConsultarDeterminacionImpuestosDID2DTO determinacionImpuestosDID2DTO) {
        List<ConsultarDeterminacionImpuestosDID2DTO> detalleDeterminacionImpuestosDID2DTO =
            new ArrayList<ConsultarDeterminacionImpuestosDID2DTO>();
        try {
            detalleDeterminacionImpuestosDID2DTO =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestosDID2(determinacionImpuestosDID2DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestosDID2DTO;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22(ConsultarDeterminacionImpuestoForma22DTO determinacionImpuestoForma22DTO) {
        List<ConsultarDeterminacionImpuestoForma22DTO> detalleDeterminacionImpuestoForma22DTO =
            new ArrayList<ConsultarDeterminacionImpuestoForma22DTO>();
        try {
            detalleDeterminacionImpuestoForma22DTO =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma22(determinacionImpuestoForma22DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma22DTO;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoforma2a2(ConsultarDeterminacionImpuestoForma2a2DTO determinacionImpuestoForma2a2DTO) {
        List<ConsultarDeterminacionImpuestoForma2a2DTO> detalleDeterminacionImpuestoForma2a2List =
            new ArrayList<ConsultarDeterminacionImpuestoForma2a2DTO>();
        try {
            detalleDeterminacionImpuestoForma2a2List =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoforma2a2(determinacionImpuestoForma2a2DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma2a2List;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32(ConsultarDeterminacionImpuestoForma32DTO determinacionImpuestoForma32DTO) {
        List<ConsultarDeterminacionImpuestoForma32DTO> detalleDeterminacionImpuestoForma32List =
            new ArrayList<ConsultarDeterminacionImpuestoForma32DTO>();
        try {
            detalleDeterminacionImpuestoForma32List =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma32(determinacionImpuestoForma32DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma32List;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3(ConsultarDeterminacionImpuestoDID3DTO determinacionImpuestoDID3DTO) {
        List<ConsultarDeterminacionImpuestoDID3DTO> detalleDeterminacionImpuestoDID3DTO =
            new ArrayList<ConsultarDeterminacionImpuestoDID3DTO>();
        try {
            detalleDeterminacionImpuestoDID3DTO =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoDID3(determinacionImpuestoDID3DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoDID3DTO;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1E(ConsultarDeterminacionImpuestoForma1EDTO determinacionImpuestoForma1EDTO) {
        List<ConsultarDeterminacionImpuestoForma1EDTO> detalleDeterminacionImpuestoForma1EList =
            new ArrayList<ConsultarDeterminacionImpuestoForma1EDTO>();
        try {
            detalleDeterminacionImpuestoForma1EList =
                    consultarDeterminacionDeImpuestosDAO.consultarDeterminacionImpuestoForma1E(determinacionImpuestoForma1EDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return detalleDeterminacionImpuestoForma1EList;
    }

    public void setConsultarDeterminacionDeImpuestosDAO(ConsultarDeterminacionDeImpuestosDAO consultarDeterminacionDeImpuestosDao) {
        this.consultarDeterminacionDeImpuestosDAO = consultarDeterminacionDeImpuestosDao;
    }

    public ConsultarDeterminacionDeImpuestosDAO getConsultarDeterminacionDeImpuestosDAO() {
        return consultarDeterminacionDeImpuestosDAO;
    }

}
