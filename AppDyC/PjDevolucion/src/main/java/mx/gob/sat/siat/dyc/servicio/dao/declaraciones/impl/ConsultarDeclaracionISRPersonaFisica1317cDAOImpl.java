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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317cDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DidMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13aMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO;
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
@Repository(value = "consultarDeclaracionISRPersonaFisica13_17cDAO")
public class ConsultarDeclaracionISRPersonaFisica1317cDAOImpl implements ConsultarDeclaracionISRPersonaFisica1317cDAO,
                                                                         SQLInformixSIAT {

    private static final Logger LOG = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317cDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO> consultaDeclaracionesIsrForma13(ConsultarDeclaracionISRPersonaFisica1317cForma13DTO declaracionISRPersonaFisica1317cForma13DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO> declaracionISRPersonaFisica1317cForma13Dto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317cForma13DTO>();
        try {
            declaracionISRPersonaFisica1317cForma13Dto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA13,
                                               new Object[] { declaracionISRPersonaFisica1317cForma13DTO.getRfceeog1(),
                                                              declaracionISRPersonaFisica1317cForma13DTO.getCNPeriodo(),
                                                              declaracionISRPersonaFisica1317cForma13DTO.getNEjercicio() },
                                               new ConsultarDeclaracionISRPersonaFisica1317cForma13Mapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317cForma13Dto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA13 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317cForma13DTO));
        }
        return declaracionISRPersonaFisica1317cForma13Dto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO> consultaDeclaracionesIsrForma13a(ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO declaracionISRPersonaFisica1317cForma13aDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO> declaracionISRPersonaFisica1317cForma13aDto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317cForma13aDTO>();
        try {
            declaracionISRPersonaFisica1317cForma13aDto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA13_A,
                                               new Object[] { declaracionISRPersonaFisica1317cForma13aDTO.getRfceeog1(),
                                                              declaracionISRPersonaFisica1317cForma13aDTO.getCNPeriodo(),
                                                              declaracionISRPersonaFisica1317cForma13aDTO.getNEjercicio(),
                                                              declaracionISRPersonaFisica1317cForma13aDTO.getRetencionDeduccion() },
                                               new ConsultarDeclaracionISRPersonaFisica1317cForma13aMapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317cForma13aDto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA13_A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317cForma13aDTO));
        }
        return declaracionISRPersonaFisica1317cForma13aDto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO> consultaDeclaracionesIsrFormaDid(ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO declaracionISRPersonaFisica1317cForma13DidDTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO> declaracionISRPersonaFisica1317cForma13DidDto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317cForma13DidDTO>();
        try {
            declaracionISRPersonaFisica1317cForma13DidDto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA_DID,
                                               new Object[] { declaracionISRPersonaFisica1317cForma13DidDTO.getDDecRfceeog1(),
                                                              declaracionISRPersonaFisica1317cForma13DidDTO.getNDecEjercic1(),
                                                              declaracionISRPersonaFisica1317cForma13DidDTO.getCDecCplearv1() },
                                               new ConsultarDeclaracionISRPersonaFisica1317cForma13DidMapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317cForma13DidDto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA_DID + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317cForma13DidDTO));
        }
        return declaracionISRPersonaFisica1317cForma13DidDto;
    }

}
