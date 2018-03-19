package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRMoralDTO;

import org.springframework.jdbc.core.RowMapper;


/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/


/**
 * TODO
 * @author Israel Chavez
 * @since 07/08/2013
 *
 */
public class ConsultarDeclaracionISRMoralMapper implements RowMapper<ConsultarDeclaracionISRMoralDTO> {

    public ConsultarDeclaracionISRMoralMapper() {
        super();
    }

    public ConsultarDeclaracionISRMoralDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeclaracionISRMoralDTO currentDeclaracionISRMoralDTO = new ConsultarDeclaracionISRMoralDTO();
        
        
        currentDeclaracionISRMoralDTO.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
                currentDeclaracionISRMoralDTO.setVIapidne1(resultSet.getInt("v_iapidne1"));
                currentDeclaracionISRMoralDTO.setVCDecCpl(resultSet.getInt("v_c_dec_cpl"));
                currentDeclaracionISRMoralDTO.setVTdiepco1(resultSet.getString("v_tdiepco1"));
                currentDeclaracionISRMoralDTO.setVFperceh1(resultSet.getDate("v_fperceh1"));
                currentDeclaracionISRMoralDTO.setI111923(resultSet.getBigDecimal("i_111923"));
                currentDeclaracionISRMoralDTO.setI121019(resultSet.getBigDecimal("i_121019"));
                currentDeclaracionISRMoralDTO.setVI800601(resultSet.getBigDecimal("v_i_800601"));
                currentDeclaracionISRMoralDTO.setVI800701(resultSet.getString("v_i_800701"));
                currentDeclaracionISRMoralDTO.setVP6a118216(resultSet.getString("v_p6a_118216"));
                currentDeclaracionISRMoralDTO.setVI111000(resultSet.getBigDecimal("v_i_111000"));
                currentDeclaracionISRMoralDTO.setVI111001(resultSet.getBigDecimal("v_i_111001"));
                currentDeclaracionISRMoralDTO.setVI3110201(resultSet.getBigDecimal("v_i_3110201"));
                currentDeclaracionISRMoralDTO.setVI3110301(resultSet.getBigDecimal("v_i_3110301"));
                currentDeclaracionISRMoralDTO.setVI111003(resultSet.getBigDecimal("v_i_111003"));
                currentDeclaracionISRMoralDTO.setVI111004(resultSet.getBigDecimal("v_i_111004"));
                currentDeclaracionISRMoralDTO.setVI111005(resultSet.getBigDecimal("v_i_111005"));
                currentDeclaracionISRMoralDTO.setVI111006(resultSet.getBigDecimal("v_i_111006"));
                currentDeclaracionISRMoralDTO.setVI111007(resultSet.getBigDecimal("v_i_111007"));
                currentDeclaracionISRMoralDTO.setVI3110601(resultSet.getString("v_i_3110601"));
                currentDeclaracionISRMoralDTO.setVI3110701(resultSet.getString("v_i_3110701"));
                currentDeclaracionISRMoralDTO.setVI111010(resultSet.getBigDecimal("v_i_111010"));
                currentDeclaracionISRMoralDTO.setVI111009(resultSet.getBigDecimal("v_i_111009"));
                currentDeclaracionISRMoralDTO.setVI111008(resultSet.getString("v_i_111008"));
                currentDeclaracionISRMoralDTO.setVI3105001(resultSet.getBigDecimal("v_i_3105001"));
                currentDeclaracionISRMoralDTO.setVI3110801(resultSet.getString("v_i_3110801"));
                currentDeclaracionISRMoralDTO.setVI3110501(resultSet.getBigDecimal("v_i_3110501"));
                currentDeclaracionISRMoralDTO.setVI3110901(resultSet.getString("v_i_3110901"));
                currentDeclaracionISRMoralDTO.setVI3105301(resultSet.getBigDecimal("v_i_3105301"));
                currentDeclaracionISRMoralDTO.setVI111013(resultSet.getString("v_i_111013"));
                currentDeclaracionISRMoralDTO.setVI111014(resultSet.getString("v_i_111014"));
                currentDeclaracionISRMoralDTO.setVI111016(resultSet.getBigDecimal("v_i_111016"));
                currentDeclaracionISRMoralDTO.setVI111015(resultSet.getBigDecimal("v_i_111015"));
                currentDeclaracionISRMoralDTO.setVI111017(resultSet.getBigDecimal("v_i_111017"));
                currentDeclaracionISRMoralDTO.setVI3111101(resultSet.getString("v_i_3111101"));
                currentDeclaracionISRMoralDTO.setVI121011(resultSet.getBigDecimal("v_i_121011"));
                currentDeclaracionISRMoralDTO.setVI111011(resultSet.getString("v_i_111011"));
                currentDeclaracionISRMoralDTO.setVI111018(resultSet.getString("v_i_111018"));
                currentDeclaracionISRMoralDTO.setVI111019(resultSet.getString("v_i_111019"));
                currentDeclaracionISRMoralDTO.setVI3106501(resultSet.getBigDecimal("v_i_3106501"));
                currentDeclaracionISRMoralDTO.setVI3106601(resultSet.getBigDecimal("v_i_3106601"));
                currentDeclaracionISRMoralDTO.setVI3111201(resultSet.getString("v_i_3111201"));
                currentDeclaracionISRMoralDTO.setVI3106701(resultSet.getBigDecimal("v_i_3106701"));
                currentDeclaracionISRMoralDTO.setVI111021(resultSet.getBigDecimal("v_i_111021"));
                currentDeclaracionISRMoralDTO.setVI111023(resultSet.getBigDecimal("v_i_111023"));
                currentDeclaracionISRMoralDTO.setV3111801(resultSet.getString("v_i_3111801"));
                currentDeclaracionISRMoralDTO.setVI111024(resultSet.getString("v_i_111024"));
                currentDeclaracionISRMoralDTO.setVI3107201(resultSet.getBigDecimal("v_i_3107201"));
                currentDeclaracionISRMoralDTO.setVI3106801(resultSet.getBigDecimal("v_i_3106801"));
                currentDeclaracionISRMoralDTO.setVI111058(resultSet.getBigDecimal("v_i_111058"));
                currentDeclaracionISRMoralDTO.setVI111020(resultSet.getString("v_i_111020"));
                currentDeclaracionISRMoralDTO.setVI111904(resultSet.getString("v_i_111904"));

        return currentDeclaracionISRMoralDTO;
    }
}
