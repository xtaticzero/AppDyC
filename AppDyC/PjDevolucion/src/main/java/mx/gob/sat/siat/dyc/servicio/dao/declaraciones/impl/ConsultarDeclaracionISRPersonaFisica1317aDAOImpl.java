/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317aDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aDecMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13aMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aDecDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author  Alfredo Ramirez
 * @since   12/08/2013
 *
 */
@Repository(value = "consultarDeclaracionISRPersonaFisica13_17aDAO")
public class ConsultarDeclaracionISRPersonaFisica1317aDAOImpl implements ConsultarDeclaracionISRPersonaFisica1317aDAO,
                                                                         SQLInformixSIAT {

    private static final Logger LOG = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317aDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO> consultaImpuestosIsrforma13ane(ConsultarDeclaracionISRPersonaFisica1317aForma13DTO declaracionISRPersonaFisica1317aForma13DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO> declaracionISRPersonaFisica1317aForma13Dto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317aForma13DTO>();
        try {
            declaracionISRPersonaFisica1317aForma13Dto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17A_FORMA13_ANE,
                                               new Object[] { declaracionISRPersonaFisica1317aForma13DTO.getRfceeog1(),
                                                              declaracionISRPersonaFisica1317aForma13DTO.getCNPeriodo(),
                                                              declaracionISRPersonaFisica1317aForma13DTO.getNEjercicio() },
                                               new ConsultarDeclaracionISRPersonaFisica1317aForma13Mapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317aForma13Dto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17A_FORMA13_ANE + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317aForma13DTO));
        }
        return declaracionISRPersonaFisica1317aForma13Dto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO> consultaImpuestosIsrforma13aane(ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO declaracionISRPersonaFisica1317aForma13aDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO> declaracionISRPersonaFisica1317aForma13aDto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317aForma13aDTO>();
        try {
            declaracionISRPersonaFisica1317aForma13aDto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17A_FORMA13_A_ANE,
                                               new Object[] { declaracionISRPersonaFisica1317aForma13aDTO.getRfceeog1(),
                                                              declaracionISRPersonaFisica1317aForma13aDTO.getCNPeriodo(),
                                                              declaracionISRPersonaFisica1317aForma13aDTO.getNEjercicio() },
                                               new ConsultarDeclaracionISRPersonaFisica1317aForma13aMapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317aForma13aDto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17A_FORMA13_A_ANE + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317aForma13aDTO));
        }
        return declaracionISRPersonaFisica1317aForma13aDto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317aDecDTO> consultaImpuestosIsrdddecdida1dd1(ConsultarDeclaracionISRPersonaFisica1317aDecDTO declaracionISRPersonaFisica1317aDecDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317aDecDTO> declaracionISRPersonaFisica1317aDecDto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317aDecDTO>();
        try {
            declaracionISRPersonaFisica1317aDecDto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17A_DD_DEC_DIDA1DD1,
                                               new Object[] { declaracionISRPersonaFisica1317aDecDTO.getDDecRfceeog1(),
                                                              declaracionISRPersonaFisica1317aDecDTO.getNDecEjercic1(),
                                                              declaracionISRPersonaFisica1317aDecDTO.getCDecCplearv1() },
                                               new ConsultarDeclaracionISRPersonaFisica1317aDecMapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317aDecDto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17A_DD_DEC_DIDA1DD1 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317aDecDTO));
        }
        return declaracionISRPersonaFisica1317aDecDto;
    }

}
