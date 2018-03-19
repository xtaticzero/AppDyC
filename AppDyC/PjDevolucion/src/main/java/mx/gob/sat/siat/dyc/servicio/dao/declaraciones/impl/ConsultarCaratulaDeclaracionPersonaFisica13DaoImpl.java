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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionPersonaFisica13DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionfisica.ConsultarCaratulaDeclaracionPersonaFisica13Mapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionfisica.ConsultarCaratulaDeclaracionPersonaFisica13DTO;
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
 * TODO REMOVE SOP
 */
@Repository(value = "consultarCaratulaDeclaracionPersonaFisica13DAO")
@Transactional
public class ConsultarCaratulaDeclaracionPersonaFisica13DaoImpl implements ConsultarCaratulaDeclaracionPersonaFisica13DAO,
                                                                           SQLInformixSIAT {

    private static final Logger LOGGER = Logger.getLogger(ConsultarCaratulaDeclaracionPersonaFisica13DaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Override
    public List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> consultaDatosDeclaracion(ConsultarCaratulaDeclaracionPersonaFisica13DTO consultarCaratulaDeclaracionPersonaFisica13DTO) {
        List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> declaracionPersonaFisica13Array =
            new ArrayList<ConsultarCaratulaDeclaracionPersonaFisica13DTO>();
        try {
            declaracionPersonaFisica13Array =
                jdbcTemplateInformix.query(CONSULTAR_CARATULA_DECLARACION_PF_13_CONSULTA_DATOS_DECLARACION,
                                               new Object[] { consultarCaratulaDeclaracionPersonaFisica13DTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionPersonaFisica13DTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionPersonaFisica13DTO.getRfceeog1(),
                                                              consultarCaratulaDeclaracionPersonaFisica13DTO.getCStiRpeangg1(),
                                                              consultarCaratulaDeclaracionPersonaFisica13DTO.getCNPeriodo(),
                                                              consultarCaratulaDeclaracionPersonaFisica13DTO.getNEjercicio() },
                                               new ConsultarCaratulaDeclaracionPersonaFisica13Mapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_CARATULA_DECLARACION_PF_13_CONSULTA_DATOS_DECLARACION + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCaratulaDeclaracionPersonaFisica13DTO));
        }
        return declaracionPersonaFisica13Array;
    }

}
