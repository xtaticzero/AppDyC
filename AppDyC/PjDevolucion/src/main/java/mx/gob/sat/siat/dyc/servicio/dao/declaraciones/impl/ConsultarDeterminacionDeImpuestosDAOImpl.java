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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeterminacionDeImpuestosDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionDeImpuestosCdiDpdifDAOMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoCdiDpdifAnioMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoCdiImpuestosMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoDID3Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoDidMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma132Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma13AMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma13Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma1EMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma22Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma2Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma2aMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoForma32Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestoforma2a2Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestosDID2Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeterminacionImpuestosForma3Mapper;
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
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cddpcio1.sql
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 * Consulta los datos de la declaraciones presentadas por el
 * contribuyente para el Impuesto.
 *
 *
 *
 * @author Israel Chàvez
 * @since 26/07/2013
 *
 */
@Repository(value = "consultarDeterminacionDeImpuestosDAO")
public class ConsultarDeterminacionDeImpuestosDAOImpl implements ConsultarDeterminacionDeImpuestosDAO, SQLInformixSIAT,
                                                                 SQLOracleSIAT {

    private Logger log = Logger.getLogger(ConsultarDeterminacionDeImpuestosDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Autowired
    private JdbcTemplate jdbcTemplateSIAT;

    /**
     * TODO
     * @param determinacionDeImpuestosCdiDpdifDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImpuestosCdiDpdif(ConsultarDeterminacionDeImpuestosCdiDpdifDTO determinacionDeImpuestosCdiDpdifDTO) {
        List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> detalleDeterminacionDeImpto =
            new ArrayList<ConsultarDeterminacionDeImpuestosCdiDpdifDTO>();
        try {
            detalleDeterminacionDeImpto =
                    jdbcTemplateSIAT.query(CONSULTA_DETERMINACION_IMPUESTO_CDI_DPDIF, new Object[] { determinacionDeImpuestosCdiDpdifDTO.getCIdeRfceeog1(),
                                                                                                     determinacionDeImpuestosCdiDpdifDTO.getCIdeRfceeog2(),
                                                                                                     determinacionDeImpuestosCdiDpdifDTO.getCIdeRfceeog3(),
                                                                                                     determinacionDeImpuestosCdiDpdifDTO.getCDecCplearv1(),
                                                                                                     determinacionDeImpuestosCdiDpdifDTO.getNDecEjercic1(),
                                                                                                     determinacionDeImpuestosCdiDpdifDTO.getCOblCcloanv1() },
                                           new ConsultarDeterminacionDeImpuestosCdiDpdifDAOMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTA_DETERMINACION_IMPUESTO_CDI_DPDIF + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionDeImpuestosCdiDpdifDTO));
        }
        return detalleDeterminacionDeImpto;
    }

    /**
     * TODO
     * @param determinacionImpuestoCdiDpdifAnioDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionDeImpuestosCdiDpdifAnio(ConsultarDeterminacionImpuestoCdiDpdifAnioDTO determinacionImpuestoCdiDpdifAnioDTO) {
        List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> detalleDeterminacionImpuestoCdiDpdifAnioDTO =
            new ArrayList<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO>();
        try {
            detalleDeterminacionImpuestoCdiDpdifAnioDTO =
                    jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_CDIDPDIFANIO.replace("{0}",
                                                                                                     String.valueOf(determinacionImpuestoCdiDpdifAnioDTO.getNEjercicio())),
                                               new Object[] { determinacionImpuestoCdiDpdifAnioDTO.getRfceeog1(),
                                                              determinacionImpuestoCdiDpdifAnioDTO.getRfceeog2(),
                                                              determinacionImpuestoCdiDpdifAnioDTO.getRfceeog3(),
                                                              determinacionImpuestoCdiDpdifAnioDTO.getCStiCcloanv1(),
                                                              determinacionImpuestoCdiDpdifAnioDTO.getCNPeriodo(),
                                                              determinacionImpuestoCdiDpdifAnioDTO.getNEjercicio(), },
                                               new ConsultarDeterminacionImpuestoCdiDpdifAnioMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETERMINACION_IMPUESTO_CDIDPDIFANIO + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionImpuestoCdiDpdifAnioDTO));
        }
        return detalleDeterminacionImpuestoCdiDpdifAnioDTO;
    }

    /**
     * TODO
     * @param determinacionImpuestoCdiImpuestosDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestos(ConsultarDeterminacionImpuestoCdiImpuestosDTO determinacionImpuestoCdiImpuestosDTO) {
        List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> detalleDeterminacionImpuestoCdiImpuestos =
            new ArrayList<ConsultarDeterminacionImpuestoCdiImpuestosDTO>();
        try {
            detalleDeterminacionImpuestoCdiImpuestos =
                    jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_CDI_IMPUESTOS,
                                               new Object[] { determinacionImpuestoCdiImpuestosDTO.getCStiCcloanv1(), },
                                               new ConsultarDeterminacionImpuestoCdiImpuestosMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETERMINACION_IMPUESTO_CDI_IMPUESTOS + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionImpuestoCdiImpuestosDTO));
        }
        return detalleDeterminacionImpuestoCdiImpuestos;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma13DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13(ConsultarDeterminacionImpuestoForma13DTO determinacionImpuestoForma13DTO) {
        List<ConsultarDeterminacionImpuestoForma13DTO> detalleDeterminacionImpuestoForma13 =
            new ArrayList<ConsultarDeterminacionImpuestoForma13DTO>();
        try {
            detalleDeterminacionImpuestoForma13 =
                    jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA13, new Object[] { determinacionImpuestoForma13DTO.getRfceeog1(),
                                                                                                        determinacionImpuestoForma13DTO.getRfceeog2(),
                                                                                                        determinacionImpuestoForma13DTO.getRfceeog3(),
                                                                                                        determinacionImpuestoForma13DTO.getCNPeriodo(),
                                                                                                        determinacionImpuestoForma13DTO.getNEjercicio() },
                                               new ConsultarDeterminacionImpuestoForma13Mapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETERMINACION_IMPUESTO_FORMA13 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionImpuestoForma13DTO));
        }
        return detalleDeterminacionImpuestoForma13;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma13ADTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13A(ConsultarDeterminacionImpuestoForma13ADTO determinacionImpuestoForma13ADTO) {
        List<ConsultarDeterminacionImpuestoForma13ADTO> detalleDeterminacionImpuestoForma13A =
            new ArrayList<ConsultarDeterminacionImpuestoForma13ADTO>();
        try {
            detalleDeterminacionImpuestoForma13A =
                    jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA13A,
                                               new Object[] { determinacionImpuestoForma13ADTO.getRfceeog1(),
                                                              determinacionImpuestoForma13ADTO.getRfceeog2(),
                                                              determinacionImpuestoForma13ADTO.getRfceeog3(),
                                                              determinacionImpuestoForma13ADTO.getCNPeriodo(),
                                                              determinacionImpuestoForma13ADTO.getNEjercicio() },
                                               new ConsultarDeterminacionImpuestoForma13AMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETERMINACION_IMPUESTO_FORMA13A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionImpuestoForma13ADTO));
        }
        return detalleDeterminacionImpuestoForma13A;
    }

    /**
     * TODO
     * @param determinacionImpuestoDidDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDid(ConsultarDeterminacionImpuestoDidDTO determinacionImpuestoDidDTO) {
        List<ConsultarDeterminacionImpuestoDidDTO> detalleDeterminacionImpuestoDidDTO =
            new ArrayList<ConsultarDeterminacionImpuestoDidDTO>();
        try {
            detalleDeterminacionImpuestoDidDTO =
                    jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_DID, new Object[] { determinacionImpuestoDidDTO.getDDecRfceeog1(),
                                                                                                    determinacionImpuestoDidDTO.getDDecRfceeog2(),
                                                                                                    determinacionImpuestoDidDTO.getDDecRfceeog3(),
                                                                                                    determinacionImpuestoDidDTO.getCNPeriodo(),
                                                                                                    determinacionImpuestoDidDTO.getNEjercicio() },
                                               new ConsultarDeterminacionImpuestoDidMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETERMINACION_IMPUESTO_DID + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionImpuestoDidDTO));
        }
        return detalleDeterminacionImpuestoDidDTO;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2(ConsultarDeterminacionImpuestoForma2DTO determinacionImpuestoForma2DTO) {
        List<ConsultarDeterminacionImpuestoForma2DTO> detalleDeterminacionImpuestoForma2List =
            new ArrayList<ConsultarDeterminacionImpuestoForma2DTO>();
        try {
            detalleDeterminacionImpuestoForma2List =
                    jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA2,
                                               new Object[] { determinacionImpuestoForma2DTO.getCDecCplearv1(),
                                                              determinacionImpuestoForma2DTO.getIapidne1(),
                                                              determinacionImpuestoForma2DTO.getIapfdne1(),
                                                              determinacionImpuestoForma2DTO.getRfceeog1(),
                                                              determinacionImpuestoForma2DTO.getRfceeog2(),
                                                              determinacionImpuestoForma2DTO.getRfceeog3() },
                                               new ConsultarDeterminacionImpuestoForma2Mapper());
        } catch (DataAccessException e) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETERMINACION_IMPUESTO_FORMA2 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(determinacionImpuestoForma2DTO));
        }
        return detalleDeterminacionImpuestoForma2List;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma2aDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2a(ConsultarDeterminacionImpuestoForma2aDTO determinacionImpuestoForma2aDTO) {

        List<ConsultarDeterminacionImpuestoForma2aDTO> detalleDeterminacionImpuestoForma2aList =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA2A,
                                       new Object[] { determinacionImpuestoForma2aDTO.getCDecCplearv1(),
                                                      determinacionImpuestoForma2aDTO.getIapidne1(),
                                                      determinacionImpuestoForma2aDTO.getIapfdne1(),
                                                      determinacionImpuestoForma2aDTO.getRfceeog1(),
                                                      determinacionImpuestoForma2aDTO.getRfceeog2(),
                                                      determinacionImpuestoForma2aDTO.getRfceeog3() },
                                       new ConsultarDeterminacionImpuestoForma2aMapper());

        if (detalleDeterminacionImpuestoForma2aList == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoForma2aList.size());
        }
        return detalleDeterminacionImpuestoForma2aList;
    }

    /**
     * TODO
     * @param determinacionImpuestosForma3DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3(ConsultarDeterminacionImpuestosForma3DTO determinacionImpuestosForma3DTO) {
        List<ConsultarDeterminacionImpuestosForma3DTO> detalleDeterminacionImpuestosForma3List =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA3,
                                       new Object[] { determinacionImpuestosForma3DTO.getCDecCplearv1(),
                                                      determinacionImpuestosForma3DTO.getIapidne1(),
                                                      determinacionImpuestosForma3DTO.getIapfdne1(),
                                                      determinacionImpuestosForma3DTO.getRfceeog1(),
                                                      determinacionImpuestosForma3DTO.getRfceeog2(),
                                                      determinacionImpuestosForma3DTO.getRfceeog3() },
                                       new ConsultarDeterminacionImpuestosForma3Mapper());

        if (detalleDeterminacionImpuestosForma3List == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestosForma3List.size());
        }
        return detalleDeterminacionImpuestosForma3List;
    }


    /**
     * TODO
     * @param determinacionImpuestoForma13_2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132(ConsultarDeterminacionImpuestoForma132DTO determinacionImpuestoForma132DTO) {
        List<ConsultarDeterminacionImpuestoForma132DTO> detalleDeterminacionImpuestoForma132List =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA13_2,
                                       new Object[] { determinacionImpuestoForma132DTO.getRfceeog1(),
                                                      determinacionImpuestoForma132DTO.getRfceeog2(),
                                                      determinacionImpuestoForma132DTO.getRfceeog3(),
                                                      determinacionImpuestoForma132DTO.getCNPeriodo(),
                                                      determinacionImpuestoForma132DTO.getNEjercicio() },
                                       new ConsultarDeterminacionImpuestoForma132Mapper());

        if (detalleDeterminacionImpuestoForma132List == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoForma132List.size());
        }
        return detalleDeterminacionImpuestoForma132List;
    }

    /**
     * TODO
     * @param determinacionImpuestosDID2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2(ConsultarDeterminacionImpuestosDID2DTO determinacionImpuestosDID2DTO) {
        List<ConsultarDeterminacionImpuestosDID2DTO> detalleDeterminacionImpuestosDID2DTO =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_DID2,
                                       new Object[] { determinacionImpuestosDID2DTO.getDDecRfceeog1(),
                                                      determinacionImpuestosDID2DTO.getDDecRfceeog2(),
                                                      determinacionImpuestosDID2DTO.getDDecRfceeog3(),
                                                      determinacionImpuestosDID2DTO.getCDecCplearv1(),
                                                      determinacionImpuestosDID2DTO.getNDecEjercic1() },
                                       new ConsultarDeterminacionImpuestosDID2Mapper());

        if (detalleDeterminacionImpuestosDID2DTO == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestosDID2DTO.size());
        }

        return detalleDeterminacionImpuestosDID2DTO;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma2_2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22(ConsultarDeterminacionImpuestoForma22DTO determinacionImpuestoForma22DTO) {
        List<ConsultarDeterminacionImpuestoForma22DTO> detalleDeterminacionImpuestoForma22DTO =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA2_2,
                                       new Object[] { determinacionImpuestoForma22DTO.getCDecCplearv1(),
                                                      determinacionImpuestoForma22DTO.getIapidne1(),
                                                      determinacionImpuestoForma22DTO.getIapfdne1(),
                                                      determinacionImpuestoForma22DTO.getRfceeog1(),
                                                      determinacionImpuestoForma22DTO.getRfceeog2(),
                                                      determinacionImpuestoForma22DTO.getRfceeog3() },
                                       new ConsultarDeterminacionImpuestoForma22Mapper());

        if (detalleDeterminacionImpuestoForma22DTO == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoForma22DTO.size());
        }
        return detalleDeterminacionImpuestoForma22DTO;
    }

    /**
     * TODO
     * @param determinacionImpuesto_forma2a_2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoforma2a2(ConsultarDeterminacionImpuestoForma2a2DTO determinacionImpuestoForma2a2DTO) {
        List<ConsultarDeterminacionImpuestoForma2a2DTO> detalleDeterminacionImpuestoForma2a2List =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA2A_2,
                                       new Object[] { determinacionImpuestoForma2a2DTO.getCDecCplearv1(),
                                                      determinacionImpuestoForma2a2DTO.getIapidne1(),
                                                      determinacionImpuestoForma2a2DTO.getIapfdne1(),
                                                      determinacionImpuestoForma2a2DTO.getRfceeog1(),
                                                      determinacionImpuestoForma2a2DTO.getRfceeog2(),
                                                      determinacionImpuestoForma2a2DTO.getRfceeog3() },
                                       new ConsultarDeterminacionImpuestoforma2a2Mapper());

        if (detalleDeterminacionImpuestoForma2a2List == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoForma2a2List.size());
        }
        return detalleDeterminacionImpuestoForma2a2List;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma3_2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32(ConsultarDeterminacionImpuestoForma32DTO determinacionImpuestoForma32DTO) {
        List<ConsultarDeterminacionImpuestoForma32DTO> detalleDeterminacionImpuestoForma32List =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA3_2,
                                       new Object[] { determinacionImpuestoForma32DTO.getCDecCplearv1(),
                                                      determinacionImpuestoForma32DTO.getIapidne1(),
                                                      determinacionImpuestoForma32DTO.getIapfdne1(),
                                                      determinacionImpuestoForma32DTO.getRfceeog1(),
                                                      determinacionImpuestoForma32DTO.getRfceeog2(),
                                                      determinacionImpuestoForma32DTO.getRfceeog3() },
                                       new ConsultarDeterminacionImpuestoForma32Mapper());

        if (detalleDeterminacionImpuestoForma32List == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoForma32List.size());
        }
        return detalleDeterminacionImpuestoForma32List;
    }

    /**
     * TODO
     * @param determinacionImpuestoDID3DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3(ConsultarDeterminacionImpuestoDID3DTO determinacionImpuestoDID3DTO) {
        List<ConsultarDeterminacionImpuestoDID3DTO> detalleDeterminacionImpuestoDID3DTO =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_DID3,
                                       new Object[] { determinacionImpuestoDID3DTO.getDDecRfceeog1(),
                                                      determinacionImpuestoDID3DTO.getDDecRfceeog2(),
                                                      determinacionImpuestoDID3DTO.getDDecRfceeog3(),
                                                      determinacionImpuestoDID3DTO.getCDecCplearv1(),
                                                      determinacionImpuestoDID3DTO.getNDecEjercic1() },
                                       new ConsultarDeterminacionImpuestoDID3Mapper());

        if (detalleDeterminacionImpuestoDID3DTO == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoDID3DTO.size());
        }
        return detalleDeterminacionImpuestoDID3DTO;
    }

    /**
     * TODO
     * @param determinacionImpuestoForma1EDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1E(ConsultarDeterminacionImpuestoForma1EDTO determinacionImpuestoForma1EDTO) {
        List<ConsultarDeterminacionImpuestoForma1EDTO> detalleDeterminacionImpuestoForma1EList =
            jdbcTemplateInformix.query(CONSULTAR_DETERMINACION_IMPUESTO_FORMA_1E,
                                       new Object[] { determinacionImpuestoForma1EDTO.getCDecCplearv1(),
                                                      determinacionImpuestoForma1EDTO.getIapidne1(),
                                                      determinacionImpuestoForma1EDTO.getIapfdne1(),
                                                      determinacionImpuestoForma1EDTO.getRfceeog1(),
                                                      determinacionImpuestoForma1EDTO.getRfceeog2(),
                                                      determinacionImpuestoForma1EDTO.getRfceeog3() },
                                       new ConsultarDeterminacionImpuestoForma1EMapper());

        if (detalleDeterminacionImpuestoForma1EList == null) {
            log.debug(ConstantesErrorTextos.TEXTO_5_ERROR_DAO);
        } else {
            log.debug(ConstantesErrorTextos.TEXTO_6_ERROR_DAO + detalleDeterminacionImpuestoForma1EList.size());
        }
        return detalleDeterminacionImpuestoForma1EList;
    }

}
