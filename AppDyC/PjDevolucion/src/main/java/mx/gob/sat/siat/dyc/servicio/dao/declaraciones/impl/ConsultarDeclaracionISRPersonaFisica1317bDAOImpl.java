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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRPersonaFisica1317bDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo1Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2Mapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO;
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
@Repository(value = "consultarDeclaracionISRPersonaFisica13_17bDAO")
public class ConsultarDeclaracionISRPersonaFisica1317bDAOImpl implements ConsultarDeclaracionISRPersonaFisica1317bDAO,
                                                                          SQLInformixSIAT {

    private static final Logger LOG = Logger.getLogger(ConsultarDeclaracionISRPersonaFisica1317bDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;


    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO> consultaConsultaDeclaracionesIsrAnexo5a2(ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO declaracionISRPersonaFisica1317bAnexo5a2DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO> declaracionISRPersonaFisica1317bAnexo5a2Dto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2DTO>();
        try {
            declaracionISRPersonaFisica1317bAnexo5a2Dto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17B_ANEXO5_A2,
                                               new Object[] { declaracionISRPersonaFisica1317bAnexo5a2DTO.getCRfceeog1a(),
                                                              declaracionISRPersonaFisica1317bAnexo5a2DTO.getCRfceeog1b(),
                                                              declaracionISRPersonaFisica1317bAnexo5a2DTO.getNPeriodo1(),
                                                              declaracionISRPersonaFisica1317bAnexo5a2DTO.getNEjercic1() },
                                               new ConsultarDeclaracionISRPersonaFisica1317bAnexo5a2Mapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317bAnexo5a2Dto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17B_ANEXO5_A2 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317bAnexo5a2DTO));
        }
        return declaracionISRPersonaFisica1317bAnexo5a2Dto;
    }

    @Override
    public List<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO> consultaconsultaConsultaDeclaracionesIsrAnexo1(ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO declaracionISRPersonaFisica1317bAnexo1DTO) {
        List<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO> declaracionISRPersonaFisica1317bAnexo1Dto =
            new ArrayList<ConsultarDeclaracionISRPersonaFisica1317bAnexo1DTO>();
        try {
            declaracionISRPersonaFisica1317bAnexo1Dto =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_PF13_17B_ANEX0_1,
                                               new Object[] { declaracionISRPersonaFisica1317bAnexo1DTO.getDDecRfceeog1(),
                                                              declaracionISRPersonaFisica1317bAnexo1DTO.getCDecCplearv1(),
                                                              declaracionISRPersonaFisica1317bAnexo1DTO.getNDecEjercic1() },
                                               new ConsultarDeclaracionISRPersonaFisica1317bAnexo1Mapper());
            LOG.debug("El Query Arrojo " + declaracionISRPersonaFisica1317bAnexo1Dto.size() + " resultados.");
        } catch (DataAccessException e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_ISR_PF13_17B_ANEX0_1 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionISRPersonaFisica1317bAnexo1DTO));
        }
        return declaracionISRPersonaFisica1317bAnexo1Dto;
    }

}
