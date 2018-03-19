/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.devoluciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.devoluciones.ConsultarDevolucionesDAO;
import mx.gob.sat.siat.dyc.servicio.dao.devoluciones.impl.mapper.ConsultarDevolucionesDAOMapper;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cdroeen1.sql
 * Consultar las devoluciones registradas para el mismo
 * periodo y ejercicio de la solicitud.
 * Interfase 13.
 *
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
@Repository("consultarDevolucionesDAO")
public class ConsultarDevolucionesDAOImpl implements ConsultarDevolucionesDAO, SQLInformixSIAT {

    private Logger logger = Logger.getLogger(ConsultarDevolucionesDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param consultarDevolucionesDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDevolucionesDTO> consultarDevoluciones(ConsultarDevolucionesDTO consultarDevolucionesDTO) {
        List<ConsultarDevolucionesDTO> detalleDevoluciones = new ArrayList<ConsultarDevolucionesDTO>();
        try {
            detalleDevoluciones =
                    jdbcTemplateInformix.query(CONSULTA_DEVOLUCIONES_REGISTRADAS, new Object[] { consultarDevolucionesDTO.getRfceeog1(),
                                                                                                                     consultarDevolucionesDTO.getRfceeog2(),
                                                                                                                     consultarDevolucionesDTO.getRfceeog3(),
                                                                                                                     consultarDevolucionesDTO.getCDecCplearv1(),
                                                                                                                     consultarDevolucionesDTO.getPsfieaa1(),
                                                                                                                     consultarDevolucionesDTO.getCDecCplearv1(),
                                                                                                                     consultarDevolucionesDTO.getPsffeaa1(),
                                                                                                                     consultarDevolucionesDTO.getCStiCtslria1() },
                                               new ConsultarDevolucionesDAOMapper());
        } catch (DataAccessException e) {
            logger.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTA_DEVOLUCIONES_REGISTRADAS + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarDevolucionesDTO));
        }
        return detalleDevoluciones;
    }

}
