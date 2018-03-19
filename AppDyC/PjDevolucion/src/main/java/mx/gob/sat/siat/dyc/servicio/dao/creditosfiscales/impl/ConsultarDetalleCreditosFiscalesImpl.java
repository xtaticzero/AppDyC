/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.creditosfiscales.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.creditosfiscales.ConsultarDetalleCreditosFiscalesDAO;
import mx.gob.sat.siat.dyc.servicio.dao.creditosfiscales.impl.mapper.ConsultarDetalleCreditosFiscalesMapper;
import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cdcfcoe1.sql
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 * Consulta el detalle de los créditos fiscales del Contribuyente operación 45.
 *
 * txtrfc varchar(13) RFC
 * txtusr varchar(20) Usuario
 *
 *
 * @author Israel Chavez Chavez
 * @since 23/07/2013
 *
 */
@Repository(value = "consultarDetalleCreditosFiscalesDAO")
public class ConsultarDetalleCreditosFiscalesImpl implements ConsultarDetalleCreditosFiscalesDAO, SQLInformixSIAT {

    private static final Logger LOG = Logger.getLogger(ConsultarDetalleCreditosFiscalesImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param consultarDetalleCreditosFiscalesDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDetalleCreditosFiscalesDTO> consultaDetalleCreditosFiscales(ConsultarDetalleCreditosFiscalesDTO consultarDetalleCreditosFiscalesDTO) {
        List<ConsultarDetalleCreditosFiscalesDTO> detalleCreditosArray =
            new ArrayList<ConsultarDetalleCreditosFiscalesDTO>();
        try {
            detalleCreditosArray =
                    jdbcTemplateInformix.query(CONSULTAR_DETALLE_CREDITOS_FISCALES_OPERACION45, new Object[] { consultarDetalleCreditosFiscalesDTO.getRfceeog1(),
                                                                                                               consultarDetalleCreditosFiscalesDTO.getRfceeog2(),
                                                                                                               consultarDetalleCreditosFiscalesDTO.getRfceeog3() },
                                               new ConsultarDetalleCreditosFiscalesMapper());
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DETALLE_CREDITOS_FISCALES_OPERACION45 + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarDetalleCreditosFiscalesDTO));
        }
        return detalleCreditosArray;
    }

}
