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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.DeclaracionProvicionalODefinitivaDeImpuestosFederales36AMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.DeclaracionProvicionalODefinitivaDeImpuestrosFederalesOp36BMapper;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.DeclaracionProvisionalODefinitivaDeImpuestosFederales36Mapper;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalIntVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * TODO
 * @author J. Isaac Carbajal Bernal
 * @since 07/05/2014
 *
 */
@Repository(value = "consultarDeclaracionProvisionalODefinitivaDeImpuestosFederales_36DAO")
@Transactional
public class ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAOImpl implements ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO,
                                                                                                SQLOracleSIAT {

    private Logger log =
        Logger.getLogger(ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAOImpl.class);


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateSIAT;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateDwhOra2;


    /**
     * TODO
     * @param declaracionProvisionalODefinitivaDeImpuestosFederales_36DTO
     * @return
     */
    @Override
    public List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> consultaDeImpuestos(DeclaracionProvicionalIntVO declaracionProvisionalODefinitivaDeImpuestosFederales36) {

        List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> impuestosFederalesArray =
            new ArrayList<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO>();

        Map<String, Object> nameParameters = new HashMap<String, Object>();
        String sqlAddRfc =
            String.format(CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36, declaracionProvisionalODefinitivaDeImpuestosFederales36.getRfc1(),
                          declaracionProvisionalODefinitivaDeImpuestosFederales36.getRfc2(),
                          declaracionProvisionalODefinitivaDeImpuestosFederales36.getRfc3());
        nameParameters.put("ejercicio", declaracionProvisionalODefinitivaDeImpuestosFederales36.getEjercicio());
        nameParameters.put("periodo", declaracionProvisionalODefinitivaDeImpuestosFederales36.getPeriodo());

        try {
            if (declaracionProvisionalODefinitivaDeImpuestosFederales36.getEjercicio() < ConstantesDyCNumerico.VALOR_2008) {
                impuestosFederalesArray =
                        namedParameterJdbcTemplateSIAT.query(sqlAddRfc, nameParameters, new DeclaracionProvisionalODefinitivaDeImpuestosFederales36Mapper());
            } else {
                impuestosFederalesArray =
                        namedParameterJdbcTemplateDwhOra2.query(sqlAddRfc, nameParameters, new DeclaracionProvisionalODefinitivaDeImpuestosFederales36Mapper());

            }
        } catch (DataAccessException e) {

            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionProvisionalODefinitivaDeImpuestosFederales36));

        } catch (Exception e) {

            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionProvisionalODefinitivaDeImpuestosFederales36));

        }

        return impuestosFederalesArray;
    }

    @Override
    public List<DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO> consultaDeImpuestosOpB(DeclaracionProvicionalIntVO declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B) {

        List<DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO> impuestosFederalesOp36BArray =
            new ArrayList<DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO>();
        Map<String, Object> nameParameters = new HashMap<String, Object>();
        String sqlAddRfc =
            String.format(CONSULTAR_DECLARACION_PROVICIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_OP_36B, declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B.getRfc1(),
                          declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B.getRfc2(),
                          declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B.getRfc3());
        nameParameters.put("ejercicio", declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B.getEjercicio());
        nameParameters.put("periodo", declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B.getPeriodo());

        try {
            if (declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B.getEjercicio() < ConstantesDyCNumerico.VALOR_2008) {
                impuestosFederalesOp36BArray =
                        namedParameterJdbcTemplateSIAT.query(sqlAddRfc, nameParameters, new DeclaracionProvicionalODefinitivaDeImpuestrosFederalesOp36BMapper());
            } else {
                impuestosFederalesOp36BArray =
                        namedParameterJdbcTemplateDwhOra2.query(sqlAddRfc, nameParameters, new DeclaracionProvicionalODefinitivaDeImpuestrosFederalesOp36BMapper());
            }
        } catch (DataAccessException e) {

            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_PROVICIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_OP_36B + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionProvisionalODefinitivaDeImpuestosFederalesOp36B));

        }

        return impuestosFederalesOp36BArray;
    }

    @Override
    public List<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO> consultaDeImpuestosA(DeclaracionProvicionalIntVO declaracionProvisionalDefinitivaImpuestosFederalesA) {
        List<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO> impuestosFederales36AArray =
            new ArrayList<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO>();

        Map<String, Object> nameParameters = new HashMap<String, Object>();
        String sqlAddRfc =
            String.format(CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36A, declaracionProvisionalDefinitivaImpuestosFederalesA.getRfc1(),
                          declaracionProvisionalDefinitivaImpuestosFederalesA.getRfc2(),
                          declaracionProvisionalDefinitivaImpuestosFederalesA.getRfc3());
        nameParameters.put("ejercicio", declaracionProvisionalDefinitivaImpuestosFederalesA.getEjercicio());
        nameParameters.put("periodo", declaracionProvisionalDefinitivaImpuestosFederalesA.getPeriodo());


        try {
            if (declaracionProvisionalDefinitivaImpuestosFederalesA.getEjercicio() < ConstantesDyCNumerico.VALOR_2008) {
                impuestosFederales36AArray =
                        namedParameterJdbcTemplateSIAT.query(sqlAddRfc, nameParameters, new DeclaracionProvicionalODefinitivaDeImpuestosFederales36AMapper());
            } else {
                impuestosFederales36AArray =
                        namedParameterJdbcTemplateDwhOra2.query(sqlAddRfc, nameParameters, new DeclaracionProvicionalODefinitivaDeImpuestosFederales36AMapper());
            }
        } catch (DataAccessException e) {

            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36A + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(declaracionProvisionalDefinitivaImpuestosFederalesA));

        }

        return impuestosFederales36AArray;
    }
}
