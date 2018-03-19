/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.compensaciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.compensaciones.ConsultarCompensacionesDao;
import mx.gob.sat.siat.dyc.servicio.dao.compensaciones.impl.mapper.ConsultarCompensacionesFechasMapper;
import mx.gob.sat.siat.dyc.servicio.dao.compensaciones.impl.mapper.ConsultarCompensacionesInformacionMapper;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * TODO
 * @author Israel Chavez
 */
@Repository(value = "consultarCompensaciones")
public class ConsultarCompensacionesImpl implements ConsultarCompensacionesDao, SQLInformixSIAT {

    private static final Logger LOGGER = Logger.getLogger(ConsultarCompensacionesImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO Obtiene informacion de la tabla de pagos del a�o indicado en el
     * parametro {@code consultarPagosElectronicosAnioDto}
     *
     * @param consultarCompensacionesFechasDto  Define el a�o de la tabla a laq que debe realizarse la consulta
     *
     * @return  Regresa el resultado de la consulta realizada de los pagos que
     *                  se hayan realizado de acuerdo al a�o indicado
     */
    @Override
    public List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechas(ConsultarCompensacionesFechasDTO consultarCompensacionesFechasDto) {
        List<ConsultarCompensacionesFechasDTO> informacionRecuperada =
            new ArrayList<ConsultarCompensacionesFechasDTO>();
        try {
            informacionRecuperada =
                    this.jdbcTemplateInformix.query(CONSULTAR_COMPENSACIONES_FECHAS, new Object[] { consultarCompensacionesFechasDto.getCDecCplearv1() },
                                                    new ConsultarCompensacionesFechasMapper());

        } catch (DataAccessException e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_COMPENSACIONES_FECHAS + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCompensacionesFechasDto));
        }
        return informacionRecuperada;
    }

    @Override
    public List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacionCompensaciones(ConsultarCompensacionesInformacionDTO consultarCompensacionesInformacionDto) {
        List<ConsultarCompensacionesInformacionDTO> informacionRecuperada =
            new ArrayList<ConsultarCompensacionesInformacionDTO>();
        try {
            informacionRecuperada =
                    this.jdbcTemplateInformix.query(CONSULTAR_COMPENSACIONES_INFORMACION_COMPENSACIONES,
                                                    new Object[] { consultarCompensacionesInformacionDto.getRfceeog1(),
                                                                   consultarCompensacionesInformacionDto.getRfceeog2(),
                                                                   consultarCompensacionesInformacionDto.getRfceeog3(),
                                                                   consultarCompensacionesInformacionDto.getPsfieaa1(),
                                                                   consultarCompensacionesInformacionDto.getPsffeaa1(),
                                                                   consultarCompensacionesInformacionDto.getCStiCcloanv1() },
                                                    new ConsultarCompensacionesInformacionMapper());

        } catch (DataAccessException e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_COMPENSACIONES_INFORMACION_COMPENSACIONES + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarCompensacionesInformacionDto));
        }
        return informacionRecuperada;
    }

}
