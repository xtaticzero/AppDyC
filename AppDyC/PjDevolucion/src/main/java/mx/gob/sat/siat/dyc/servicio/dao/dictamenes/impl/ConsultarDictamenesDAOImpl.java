/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.dictamenes.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.dictamenes.ConsultarDictamenesDAO;
import mx.gob.sat.siat.dyc.servicio.dao.dictamenes.impl.mapper.ConsultarDictamenesDAOMapper;
import mx.gob.sat.siat.dyc.servicio.dto.dictamenes.ConsultarDictamenesDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cdpcqas1.sql
 * Consulta los dictamenes presentados por el contribuyente que
 * avalan la solicitud de devolucisn registrada.
 * Interfaz. 15
 *
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 * txtrfc varchar(15,0)    -- RFC Contribuyente parametro
 * numeje integer          -- Ejercicio parametro
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
@Repository(value = "consultarDictamenesDAO")
public class ConsultarDictamenesDAOImpl implements ConsultarDictamenesDAO, SQLInformixSIAT {

    private static final Logger LOGGER = Logger.getLogger(ConsultarDictamenesDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param consultarDictamenesDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDictamenesDTO> consultarDictamenes(ConsultarDictamenesDTO consultarDictamenesDTO) {
        List<ConsultarDictamenesDTO> detalleDictamenes = new ArrayList<ConsultarDictamenesDTO>();
        try {
            detalleDictamenes =
                    jdbcTemplateInformix.query(CONSULTAR_DICTAMENES_CONSULTA_DICTAMEN_CONTRIBUYENTE, new Object[] { consultarDictamenesDTO.getRfcContribuyente(),
                                                                                                                    consultarDictamenesDTO.getRfcContribuyente2(),
                                                                                                                    consultarDictamenesDTO.getRfcContribuyente3(),
                                                                                                                    consultarDictamenesDTO.getEjercicioFiscal() },
                                               new ConsultarDictamenesDAOMapper());
        } catch (DataAccessException e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         CONSULTAR_DICTAMENES_CONSULTA_DICTAMEN_CONTRIBUYENTE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                         ExtractorUtil.ejecutar(consultarDictamenesDTO));
        }
        return detalleDictamenes;
    }

}
