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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAMapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * TODO
 * @author Israel Chavez
 * @since 09/07/2013
 *
 */
@Repository(value = "consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO")
@Transactional
public class ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADaoImpl implements ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO,
                                                                                                 SQLOracleSIAT {

    private Logger logger =
        Logger.getLogger(ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * Obtiene informacion de la tabla de pagos del año indicado en el
     * parametro {@code consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto}
     *
     * @param consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto Define el año de la tabla a laq que debe realizarse la consulta
     *
     * @return Regresa el resultado de la consulta realizada de los pagos que
     * se hayan realizado de acuerdo al año indicado
     */
    @Override
    public List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> consultaDeImpuestos(DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto) {
        List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> impuestosFederalesIVAArray =
            new ArrayList<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO>();
        try {
            impuestosFederalesIVAArray =
                    jdbcTemplateInformix.query(CONSULTA_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOSFED_IVA_CONSULTA_IMPUESTOS,
                                               new Object[] { consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto.getCIdeRfceeog1(),
                                                              consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto.getCIdeRfceeog2(),
                                                              consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto.getCIdeRfceeog3(),
                                                              consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto.getNDecEjercic1(),
                                                              consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto.getCDecCplearv1() },
                                               new ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAMapper());
        } catch (DataAccessException e) {
            logger.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTA_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOSFED_IVA_CONSULTA_IMPUESTOS + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto));
        }
        return impuestosFederalesIVAArray;
    }

}
