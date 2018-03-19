/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.personafisica1317;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirez
 * @since   13/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317Forma13Mapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317Forma13DTO> {

    public ConsultarDeclaracionISRPersonaFisica1317Forma13DTO mapRow(ResultSet resultSet,
                                                                      int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317Forma13DTO declaracionISRPersonaFisica1317Forma13Dto =
            new ConsultarDeclaracionISRPersonaFisica1317Forma13DTO();

        declaracionISRPersonaFisica1317Forma13Dto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317Forma13Dto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317Forma13Dto.setFDecFperceh1(resultSet.getDate("f_dec_fperceh1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111837(resultSet.getBigDecimal("i_111837"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111838(resultSet.getBigDecimal("i_111838"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111810(resultSet.getBigDecimal("i_111810"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111811(resultSet.getBigDecimal("i_111811"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111812(resultSet.getBigDecimal("i_111812"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111813(resultSet.getBigDecimal("i_111813"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecSeumbps1(resultSet.getString("i_dec_seumbps1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111848(resultSet.getBigDecimal("i_111848"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111817(resultSet.getBigDecimal("i_111817"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111815(resultSet.getBigDecimal("i_111815"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111818(resultSet.getBigDecimal("i_111818"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111860(resultSet.getBigDecimal("i_111860"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecEpidtsr1(resultSet.getString("i_dec_epidtsr1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecEpipcns1(resultSet.getString("i_dec_epipcns1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecPeefafn1(resultSet.getString("i_dec_peefafn1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111820(resultSet.getBigDecimal("i_111820"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111819(resultSet.getBigDecimal("i_111819"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111822(resultSet.getBigDecimal("i_111822"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecCfietud1(resultSet.getString("i_dec_cfietud1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecIirrpmn1(resultSet.getString("i_dec_iirrpmn1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111888(resultSet.getBigDecimal("a_111888"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111889(resultSet.getBigDecimal("a_111889"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111021(resultSet.getBigDecimal("i_111021"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111827(resultSet.getBigDecimal("i_111827"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecIademce1(resultSet.getString("i_dec_iademce1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecDciafre1(resultSet.getString("i_dec_dciafre1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111023(resultSet.getBigDecimal("i_111023"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecIrpeaie1(resultSet.getString("i_dec_irpeaie1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI111024(resultSet.getBigDecimal("i_111024"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111201(resultSet.getBigDecimal("a_111201"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111202(resultSet.getBigDecimal("a_111202"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111207(resultSet.getBigDecimal("a_111207"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111206(resultSet.getBigDecimal("a_111206"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111401(resultSet.getBigDecimal("a_111401"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111421(resultSet.getBigDecimal("a_111421"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111402(resultSet.getBigDecimal("a_111402"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecIlpioug1(resultSet.getString("i_dec_ilpioug1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecEfpdama1(resultSet.getString("i_dec_efpdama1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111422(resultSet.getBigDecimal("a_111422"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111407(resultSet.getBigDecimal("a_111407"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111404(resultSet.getBigDecimal("a_111404"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111938(resultSet.getBigDecimal("a_111938"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111501(resultSet.getBigDecimal("a_111501"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111504(resultSet.getBigDecimal("a_111504"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111523(resultSet.getBigDecimal("a_111523"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecIliebim1(resultSet.getString("i_dec_iliebim1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111544(resultSet.getBigDecimal("a_111544"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111840(resultSet.getBigDecimal("a_111840"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111550(resultSet.getBigDecimal("a_111550"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111551(resultSet.getBigDecimal("a_111551"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111552(resultSet.getBigDecimal("a_111552"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111553(resultSet.getBigDecimal("a_111553"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111842(resultSet.getBigDecimal("a_111842"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111743(resultSet.getBigDecimal("a_111743"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111730(resultSet.getBigDecimal("a_111730"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111732(resultSet.getBigDecimal("a_111732"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111744(resultSet.getBigDecimal("a_111744"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111734(resultSet.getBigDecimal("a_111734"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111728(resultSet.getBigDecimal("a_111728"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111740(resultSet.getBigDecimal("a_111740"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111741(resultSet.getBigDecimal("a_111741"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111742(resultSet.getBigDecimal("a_111742"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111724(resultSet.getBigDecimal("a_111724"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111927(resultSet.getBigDecimal("a_111927"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111726(resultSet.getBigDecimal("a_111726"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111850(resultSet.getBigDecimal("a_111850"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111851(resultSet.getBigDecimal("a_111851"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111852(resultSet.getBigDecimal("a_111852"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111856(resultSet.getBigDecimal("a_111856"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111853(resultSet.getBigDecimal("a_111853"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111880(resultSet.getBigDecimal("a_111880"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111881(resultSet.getBigDecimal("a_111881"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111882(resultSet.getBigDecimal("a_111882"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111883(resultSet.getBigDecimal("a_111883"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111884(resultSet.getBigDecimal("a_111884"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecPutpeat1(resultSet.getString("i_dec_putpeat1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecDuapspa1(resultSet.getString("i_dec_duapspa1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecDpapspa1(resultSet.getString("i_dec_dpapspa1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111885(resultSet.getBigDecimal("a_111885"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111886(resultSet.getBigDecimal("a_111886"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111142(resultSet.getBigDecimal("a_111142"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111143(resultSet.getBigDecimal("a_111143"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111192(resultSet.getBigDecimal("a_111192"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecIliaemo1(resultSet.getString("i_dec_iliaemo1"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecEfpdams1(resultSet.getString("i_dec_efpdams1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111144(resultSet.getBigDecimal("a_111144"));
        declaracionISRPersonaFisica1317Forma13Dto.setI3DecUftiisl1(resultSet.getString("i3_dec_uftiisl1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI3DecPfeirsd1(resultSet.getString("i3_dec_pfeirsd1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI3DecPutpeat1(resultSet.getString("i3_dec_putpeat1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111145(resultSet.getBigDecimal("a_111145"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111146(resultSet.getBigDecimal("a_111146"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111147(resultSet.getBigDecimal("a_111147"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111148(resultSet.getBigDecimal("a_111148"));
        declaracionISRPersonaFisica1317Forma13Dto.setA116392(resultSet.getBigDecimal("a_116392"));
        declaracionISRPersonaFisica1317Forma13Dto.setA116393(resultSet.getBigDecimal("a_116393"));
        declaracionISRPersonaFisica1317Forma13Dto.setA116399(resultSet.getBigDecimal("a_116399"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecGridtae1(resultSet.getString("i_dec_gridtae1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117918(resultSet.getBigDecimal("a_117918"));
        declaracionISRPersonaFisica1317Forma13Dto.setA118952(resultSet.getBigDecimal("a_118952"));
        declaracionISRPersonaFisica1317Forma13Dto.setA118954(resultSet.getBigDecimal("a_118954"));
        declaracionISRPersonaFisica1317Forma13Dto.setIDecPuteatr1(resultSet.getString("i_dec_puteatr1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI4DecDuitfie1(resultSet.getString("i4_dec_duitfie1"));
        declaracionISRPersonaFisica1317Forma13Dto.setI4DecDpiefre1(resultSet.getString("i4_dec_dpiefre1"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111005(resultSet.getBigDecimal("a_111005"));
        declaracionISRPersonaFisica1317Forma13Dto.setA118955(resultSet.getBigDecimal("a_118955"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117001(resultSet.getBigDecimal("a_117001"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117073(resultSet.getBigDecimal("a_117073"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117071(resultSet.getBigDecimal("a_117071"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117076(resultSet.getBigDecimal("a_117076"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117285(resultSet.getBigDecimal("a_117285"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117065(resultSet.getBigDecimal("a_117065"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117066(resultSet.getBigDecimal("a_117066"));
        declaracionISRPersonaFisica1317Forma13Dto.setA117889(resultSet.getBigDecimal("a_117889"));
        declaracionISRPersonaFisica1317Forma13Dto.setA201356(resultSet.getBigDecimal("a_201356"));
        declaracionISRPersonaFisica1317Forma13Dto.setA111191(resultSet.getBigDecimal("a_111191"));

        return declaracionISRPersonaFisica1317Forma13Dto;
    }

}
