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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionSociedadIntegradora3DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RegMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * TODO eliminar SOP's
 * @author Israel Chavez
 * @since 10/07/2013
 *
 */
@Repository(value = "consultarCaratulaDeclaracionSociedadIntegradora3DAO")
@Transactional
public class ConsultarCaratulaDeclaracionSociedadIntegradora3DaoImpl implements ConsultarCaratulaDeclaracionSociedadIntegradora3DAO,
                                                                                SQLInformixSIAT {

    private static final Logger LOGGER =
        Logger.getLogger(ConsultarCaratulaDeclaracionSociedadIntegradora3DaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO}
     *
     * @param consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return Regresa el resultado de la consulta realizada de los pagos que
     * se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3,
                                               new Object[] { consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO.getCStiCcloanv1() },
                                               new ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO));
        }
        return declaracionSociedadArray;
    }

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO}
     *
     * @param consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> consultaDatosForma3(ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_FORMA3,
                                               new Object[] { consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO.getIapidne1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO.getIapfdne1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO.getCDecCplearv1() },
                                               new ConsultarCaratulaDeclaracionSociedadIntegradora3Mapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_FORMA3 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO));
        }
        return declaracionSociedadArray;
    }

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO}
     *
     * @param consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>();
        try {
            declaracionSociedadArray =
                            jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_REGISTRO2002,
                                               new Object[] { consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO.getCNPeriodo(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO.getNEjercicio(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO.getNRenglon() },
                                               new ConsultarCaratulaDeclaracionSociedadIntegradora3RegMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_REGISTRO2002 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO));
        }
        return declaracionSociedadArray;
    }

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadIntegradora3RegDTO}
     *
     * @param consultarCaratulaDeclaracionSociedadIntegradora3RegDTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO consultarCaratulaDeclaracionSociedadIntegradora3RegDTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_REGISTRO.replace("{0}",
                                                                                                                 String.valueOf(consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getAnio())),
                                               new Object[] { consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getCNPeriodo(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getNEjercicio(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora3RegDTO.getNRenglon() },
                                               new ConsultarCaratulaDeclaracionSociedadIntegradora3RegMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_REGISTRO + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadIntegradora3RegDTO));
        }
        return declaracionSociedadArray;
    }

}
