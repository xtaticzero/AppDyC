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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionMoral2DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2Forma2AMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RegistroMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RenglonMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2Forma2ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RegistroDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RenglonDTO;
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
 * @since 11/07/2013
 * TODO retirar los SOP's
 */
@Repository(value = "consultarCaratulaDeclaracionMoral2DAO")
@Transactional
public class ConsultarCaratulaDeclaracionMoral2DaoImpl implements ConsultarCaratulaDeclaracionMoral2DAO,
                                                                  SQLInformixSIAT {

    private static final Logger LOGGER = Logger.getLogger(ConsultarCaratulaDeclaracionMoral2DaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionMoral2RenglonDTO consultarCaratulaDeclaracionMoral2RenglonDTO) {
        List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> declaracionMoral2RenglonArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2RenglonDTO>();
        try {
            declaracionMoral2RenglonArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_MORAL12_RENGLON, new Object[] { consultarCaratulaDeclaracionMoral2RenglonDTO.getCStiCcloanv1() },
                                               new ConsultarCaratulaDeclaracionMoral2RenglonMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_MORAL12_RENGLON + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionMoral2RenglonDTO));
        }
        return declaracionMoral2RenglonArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> consultaDatosForma2(ConsultarCaratulaDeclaracionMoral2Forma2ADTO consultarCaratulaDeclaracionMoral2Forma2ADTO) {
        List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> declaracionMoral2FormaArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2Forma2ADTO>();
        try {
            declaracionMoral2FormaArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_MORAL12_FORMA2A, new Object[] { consultarCaratulaDeclaracionMoral2Forma2ADTO.getRfceeog1(),
                                                                                                              consultarCaratulaDeclaracionMoral2Forma2ADTO.getRfceeog2(),
                                                                                                              consultarCaratulaDeclaracionMoral2Forma2ADTO.getRfceeog3(),
                                                                                                              consultarCaratulaDeclaracionMoral2Forma2ADTO.getIapidne1(),
                                                                                                              consultarCaratulaDeclaracionMoral2Forma2ADTO.getIapfdne1(),
                                                                                                              consultarCaratulaDeclaracionMoral2Forma2ADTO.getCDecCplearv1() },
                                               new ConsultarCaratulaDeclaracionMoral2Forma2AMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_MORAL12_FORMA2A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionMoral2Forma2ADTO));
        }
        return declaracionMoral2FormaArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionMoral2RegistroDTO consultarCaratulaDeclaracionMoral2RegistroDTO) {
        List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> declaracionMoral2RegistroArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2RegistroDTO>();
        try {
            declaracionMoral2RegistroArray =
                    jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_MORAL12_FORMA2A, new Object[] { consultarCaratulaDeclaracionMoral2RegistroDTO.getRfceeog1(),
                                                                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getRfceeog2(),
                                                                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getRfceeog3(),
                                                                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getCNPeriodo(),
                                                                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getNEjercicio(),
                                                                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getNRenglon() },
                                               new ConsultarCaratulaDeclaracionMoral2RegistroMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_MORAL12_FORMA2A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionMoral2RegistroDTO));
        }
        return declaracionMoral2RegistroArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionMoral2RegistroDTO consultarCaratulaDeclaracionMoral2RegistroDTO) {
        List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> declaracionMoral2RegistroArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2RegistroDTO>();
        try {
            declaracionMoral2RegistroArray =
                    jdbcTemplateInformix.query((CONSULTAR_CARATULA_DECLARACION_MORAL12_REGISTRO.replace("{0}",
                                                                                                        String.valueOf(consultarCaratulaDeclaracionMoral2RegistroDTO.getAnio()))),
                                               new Object[] { consultarCaratulaDeclaracionMoral2RegistroDTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getRfceeog2(),
                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getRfceeog3(),
                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getCNPeriodo(),
                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getNEjercicio(),
                                                              consultarCaratulaDeclaracionMoral2RegistroDTO.getNRenglon() },
                                               new ConsultarCaratulaDeclaracionMoral2RegistroMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_MORAL12_REGISTRO + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionMoral2RegistroDTO));
        }
        return declaracionMoral2RegistroArray;
    }

}
