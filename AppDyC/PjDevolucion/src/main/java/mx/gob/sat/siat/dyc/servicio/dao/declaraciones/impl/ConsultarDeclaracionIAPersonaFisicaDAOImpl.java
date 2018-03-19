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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionIAPersonaFisicaDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02Mapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * TODO eliminar SOP's
 * @author Israel Chavez
 * @since 13/08/2013
 *
 */
@Repository(value = "consultarDeclaracionIAPersonaFisicaDAO")
public class ConsultarDeclaracionIAPersonaFisicaDAOImpl implements ConsultarDeclaracionIAPersonaFisicaDAO,
                                                                   SQLInformixSIAT {

    private static final Logger LOGGER = Logger.getLogger(ConsultarDeclaracionIAPersonaFisicaDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO
     * @return
     */
    @Override
    public List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01(ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO) {

        List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> declaracionIAPersonaFisicaDatosDeclaraciones01List =
            new ArrayList<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO>();

        try {
            declaracionIAPersonaFisicaDatosDeclaraciones01List =
                    jdbcTemplateInformix.query(CONSULTA_DECLARACION_IA_PF_DATOS_DECLARACIONES_01,
                                               new Object[] { consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.getRfceeog1(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.getRfceeog2(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.getRfceeog3(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.getCNPeriodo(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.getNEjercicio() },
                                               new ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01Mapper());

            LOGGER.debug("La ejecucion del query arrojo:: " +
                         declaracionIAPersonaFisicaDatosDeclaraciones01List.size() + " resultados ");
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTA_DECLARACION_IA_PF_DATOS_DECLARACIONES_01 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO));
        }
        return declaracionIAPersonaFisicaDatosDeclaraciones01List;
    }

    @Override
    public List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02(ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO) {


        List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List =
            new ArrayList<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO>();

        try {

            consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List =
                    jdbcTemplateInformix.query(CONSULTA_DECLARACION_IA_PF_DATOS_DECLARACIONES_02,
                                               new Object[] { consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.getDDecRfceeog1(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.getDDecRfceeog2(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.getDDecRfceeog3(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.getCDecCplearv1(),
                                                              consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.getNDecEjercic1() },
                                               new ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02Mapper());

            LOGGER.debug("La ejecucion del query arrojo:: " +
                         consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List.size() + " resultados ");

        } catch (DataAccessException e) {

            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTA_DECLARACION_IA_PF_DATOS_DECLARACIONES_02 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO));

        }
        return consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List;
    }

}

