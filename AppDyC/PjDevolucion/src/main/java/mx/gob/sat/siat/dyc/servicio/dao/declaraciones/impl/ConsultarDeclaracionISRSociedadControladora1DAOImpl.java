/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRSociedadControladora1DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeclaracionISRSociedadControladora1Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeclaracionISRSociedadControladora2Mapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora2DTO;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cddisrd3.sql
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 *  Consulta de Datos de la Determinacion del Impuesto Sobre la
 *  Renta en la Declaracion de Consolidacion Presentada por
 *  Sociedades Controladoras. Interfaz 27.
 *
 * @author Israel Chavez Chavez
 * @since 06/08/2013
 *
 */
@Repository(value = "consultarDeclaracionISRSociedadControladora1DAO")
@Transactional
public class ConsultarDeclaracionISRSociedadControladora1DAOImpl implements ConsultarDeclaracionISRSociedadControladora1DAO,
                                                                            SQLInformixSIAT {

    private Logger log = Logger.getLogger(ConsultarDeclaracionISRSociedadControladora1DAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param declaracionISRSociedadControladora1DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1(ConsultarDeclaracionISRSociedadControladora1DTO declaracionISRSociedadControladora1DTO) {
        List<ConsultarDeclaracionISRSociedadControladora1DTO> declaracionISRSociedadControladora1List =
            jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_SOCIEDAD_CONTROLADORA_1,
                                       new Object[] { declaracionISRSociedadControladora1DTO.getRfceeog1(),
                                                      declaracionISRSociedadControladora1DTO.getRfceeog2(),
                                                      declaracionISRSociedadControladora1DTO.getRfceeog3(),
                                                      declaracionISRSociedadControladora1DTO.getIapidne1(),
                                                      declaracionISRSociedadControladora1DTO.getIapfdne1(),
                                                      declaracionISRSociedadControladora1DTO.getCDecCplearv1() },
                                       new ConsultarDeclaracionISRSociedadControladora1Mapper());
        if (declaracionISRSociedadControladora1List == null) {
            log.debug("No hay registros en la base de datos");
        } else {
            log.debug("Resultado del query: " + declaracionISRSociedadControladora1List.size());
        }

        return declaracionISRSociedadControladora1List;
    }

    /**
     * TODO
     * @param declaracionISRSociedadControladora2DTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2(ConsultarDeclaracionISRSociedadControladora2DTO declaracionISRSociedadControladora2DTO) {
        List<ConsultarDeclaracionISRSociedadControladora2DTO> declaracionISRSociedadControladora2List =
            jdbcTemplateInformix.query(CONSULTAR_DECLARACION_ISR_SOCIEDAD_CONTROLADORA_2,
                                       new Object[] { declaracionISRSociedadControladora2DTO.getRfceeog1(),
                                                      declaracionISRSociedadControladora2DTO.getRfceeog2(),
                                                      declaracionISRSociedadControladora2DTO.getRfceeog3(),
                                                      declaracionISRSociedadControladora2DTO.getIapidne1(),
                                                      declaracionISRSociedadControladora2DTO.getIapfdne1(),
                                                      declaracionISRSociedadControladora2DTO.getCDecCplearv1() },
                                       new ConsultarDeclaracionISRSociedadControladora2Mapper());

        if (declaracionISRSociedadControladora2List == null) {
            log.debug("No hay registros en la base de datos");
        } else {
            log.debug("Resultado del query: " + declaracionISRSociedadControladora2List.size());
        }

        return declaracionISRSociedadControladora2List;
    }
}
