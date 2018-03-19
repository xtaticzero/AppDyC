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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317DeclarasatMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13aMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO;
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
 * @since   06/08/2013
 *
 */
@Repository(value = "consultarDeclaracionISRPersonaFisica13_17DAO")
public class ConsultarDeclaracionISRPersonaFisica1317DAOImpl implements ConsultarDeclaracionISRPersonaFisica1317DAO,
                                                                         SQLInformixSIAT {

    private static final Logger LOG = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317DAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO> consultaDeclaracionesIsrForma13Declarasat(ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO declaracionISRPersonaFisica1317DeclarasatDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO> declaracionISRPersonaFisica1317DeclarasatDto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO>();
        StringBuffer query = new StringBuffer("");
        query.append(CONSULTAR_DECLARACION_ISR_PF13_17_DECLARASAT_PART1);
        query.append(CONSULTAR_DECLARACION_ISR_PF13_17_DECLARASAT_PART2);
        
        try {
            
            declaracionISRPersonaFisica1317DeclarasatDto =
                    jdbcTemplateInformix.query(query.toString(),
                                               new Object[] { declaracionISRPersonaFisica1317DeclarasatDTO.getDDecRfceeog1(),
                                                              declaracionISRPersonaFisica1317DeclarasatDTO.getNDecEjercic1(),
                                                              declaracionISRPersonaFisica1317DeclarasatDTO.getCDecCplearv1() },
                                               new ConsultarDeclaracionISRPersonaFisica1317DeclarasatMapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317DeclarasatDto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    query.toString() + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317DeclarasatDTO));
        }
        return declaracionISRPersonaFisica1317DeclarasatDto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO> consultaDeclaracionesIsrForma13(ConsultarDeclaracionISRPersonaFisica1317Forma13DTO declaracionISRPersonaFisica1317Forma13DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO> declaracionISRPersonaFisica1317Forma13Dto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO>();
        try {
            declaracionISRPersonaFisica1317Forma13Dto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17_FORMA13,
                                               new Object[] { declaracionISRPersonaFisica1317Forma13DTO.getRfceeog1(),
                                                              declaracionISRPersonaFisica1317Forma13DTO.getNEjercicio(),
                                                              declaracionISRPersonaFisica1317Forma13DTO.getCNPeriodo() },
                                               new ConsultarDeclaracionISRPersonaFisica1317Forma13Mapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317Forma13Dto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17_FORMA13 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317Forma13DTO));
        }
        return declaracionISRPersonaFisica1317Forma13Dto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO> consultaDeclaracionesIsrForma13a(ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO declaracionISRPersonaFisica1317Forma13aDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO> declaracionISRPersonaFisica1317Forma13aDto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO>();
        try {
            declaracionISRPersonaFisica1317Forma13aDto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17_FORMA13_A,
                                               new Object[] { declaracionISRPersonaFisica1317Forma13aDTO.getRfceeog1(),
                                                              declaracionISRPersonaFisica1317Forma13aDTO.getNEjercicio(),
                                                              declaracionISRPersonaFisica1317Forma13aDTO.getCNPeriodo() },
                                               new ConsultarDeclaracionISRPersonaFisica1317Forma13aMapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317Forma13aDto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17_FORMA13_A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317Forma13aDTO));
        }
        return declaracionISRPersonaFisica1317Forma13aDto;
    }

}
