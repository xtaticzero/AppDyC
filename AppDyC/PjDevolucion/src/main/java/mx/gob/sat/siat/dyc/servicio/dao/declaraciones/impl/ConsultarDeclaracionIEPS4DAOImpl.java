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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionIEPS4DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeclaracionIEPS4Mapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIEPS4DTO;
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
@Repository(value = "consultarDeclaracionIEPS4DAO")
public class ConsultarDeclaracionIEPS4DAOImpl implements ConsultarDeclaracionIEPS4DAO, SQLInformixSIAT {

    private static final Logger LOGGER = Logger.getLogger(ConsultarDeclaracionIEPS4DAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param consultarDeclaracionIEPS4DTO
     * @return
     */
    @Override
    public List<ConsultarDeclaracionIEPS4DTO> consultarDeclaracionIEPS4(ConsultarDeclaracionIEPS4DTO consultarDeclaracionIEPS4DTO) {

        List<ConsultarDeclaracionIEPS4DTO> declaracionIEPS4List = new ArrayList<ConsultarDeclaracionIEPS4DTO>();

        try {
            declaracionIEPS4List =
                    jdbcTemplateInformix.query(CONSULTAR_DECLARACION_IEPS4_CONSULTA_DATOS_DECLARACION, new Object[] { consultarDeclaracionIEPS4DTO.getRfceeog1(),
                                                                                                                   consultarDeclaracionIEPS4DTO.getRfceeog2(),
                                                                                                                   consultarDeclaracionIEPS4DTO.getRfceeog3(),
                                                                                                                   consultarDeclaracionIEPS4DTO.getIapidne1(),
                                                                                                                   consultarDeclaracionIEPS4DTO.getIapfdne1(),
                                                                                                                   consultarDeclaracionIEPS4DTO.getCDecCplearv1() },
                                               new ConsultarDeclaracionIEPS4Mapper());

            LOGGER.debug("La ejecucion del query arrojo:: " + declaracionIEPS4List.size() + " resultados ");
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_IEPS4_CONSULTA_DATOS_DECLARACION + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarDeclaracionIEPS4DTO));
        }
        return declaracionIEPS4List;
    }

}


