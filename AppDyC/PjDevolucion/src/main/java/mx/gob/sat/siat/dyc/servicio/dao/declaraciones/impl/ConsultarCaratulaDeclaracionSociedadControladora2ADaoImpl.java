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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionSociedadControladora2ADAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2AForma2Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARegMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARenglonMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO;
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
 *
 * @author Israel Chavez
 * @since 10/07/2013
 * TODO retiras los SOP
 */
@Repository(value = "consultarCaratulaDeclaracionSociedadControladora2ADAO")
@Transactional
public class ConsultarCaratulaDeclaracionSociedadControladora2ADaoImpl implements ConsultarCaratulaDeclaracionSociedadControladora2ADAO,
                                                                                  SQLInformixSIAT {

    private static final Logger LOGGER =
        Logger.getLogger(ConsultarCaratulaDeclaracionSociedadControladora2ADaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadControladora2RenglonDTO}
     *
     * @param consultarCaratulaDeclaracionSociedadControladora2RenglonDTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return Regresa el resultado de la consulta realizada de los pagos que
     * se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO consultarCaratulaDeclaracionSociedadControladora2RenglonDTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_RENGLON,
                                               new Object[] { consultarCaratulaDeclaracionSociedadControladora2RenglonDTO.getCStiCcloanv1() },
                                               new ConsultarCaratulaDeclaracionSociedadControladora2ARenglonMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_RENGLON + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadControladora2RenglonDTO));
        }
        return declaracionSociedadArray;
    }

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarPagosElectronicosAnioDTO}
     *
     * @param consultarCaratulaDeclaracionSociedadControladora2AForma2DTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> consultaDatosForma2(ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO consultarCaratulaDeclaracionSociedadControladora2AForma2DTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_FORMA2A,
                                               new Object[] { consultarCaratulaDeclaracionSociedadControladora2AForma2DTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionSociedadControladora2AForma2DTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionSociedadControladora2AForma2DTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionSociedadControladora2AForma2DTO.getIapidne1(),
                                                              consultarCaratulaDeclaracionSociedadControladora2AForma2DTO.getIapfdne1(),
                                                              consultarCaratulaDeclaracionSociedadControladora2AForma2DTO.getCDecCplearv1() },
                                               new ConsultarCaratulaDeclaracionSociedadControladora2AForma2Mapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_FORMA2A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadControladora2AForma2DTO));
        }
        return declaracionSociedadArray;
    }

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO}
     *
     * @param consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO Define el año los parametros de restriccion que debe adjuntar la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_REGISTRO2002,
                                               new Object[] { consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getCNPeriodo(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getNEjercicio(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getNRenglon() },
                                               new ConsultarCaratulaDeclaracionSociedadControladora2ARegMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_REGISTRO2002 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO));
        }
        return declaracionSociedadArray;
    }

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO}
     *
     * @param consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO>();
        try {
            declaracionSociedadArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_REGISTRO.replace("{0}",
                                                                                                                   String.valueOf(consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getAnio())),
                                               new Object[] { consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getCNPeriodo(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getNEjercicio(),
                                                              consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO.getNRenglon() },
                                               new ConsultarCaratulaDeclaracionSociedadControladora2ARegMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_REGISTRO + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO));
        }
        return declaracionSociedadArray;
    }

}
