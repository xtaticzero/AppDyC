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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirezl
 * @since   13/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317Forma13aMapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO> {

    public ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO mapRow(ResultSet resultSet,
                                                                       int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO declaracionISRPersonaFisica1317Forma13aDto =
            new ConsultarDeclaracionISRPersonaFisica1317Forma13aDTO();

        declaracionISRPersonaFisica1317Forma13aDto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317Forma13aDto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317Forma13aDto.setFDecFperceh1(resultSet.getDate("f_dec_fperceh1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111837(resultSet.getString("i_111837"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111838(resultSet.getString("i_111838"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111810(resultSet.getString("i_111810"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111811(resultSet.getBigDecimal("i_111811"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111812(resultSet.getBigDecimal("i_111812"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111813(resultSet.getBigDecimal("i_111813"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecSeumbps1(resultSet.getString("i_dec_seumbps1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111848(resultSet.getBigDecimal("i_111848"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111817(resultSet.getBigDecimal("i_111817"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111815(resultSet.getString("i_111815"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111818(resultSet.getBigDecimal("i_111818"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111860(resultSet.getString("i_111860"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecEpidtsr1(resultSet.getString("i_dec_epidtsr1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecEpipcns1(resultSet.getString("i_dec_eepipcns1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecPeefafn1(resultSet.getString("i_dec_peefafn1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111820(resultSet.getBigDecimal("i_111820"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111819(resultSet.getBigDecimal("i_111819"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111822(resultSet.getBigDecimal("i_111822"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecCfietud1(resultSet.getString("i_dec_cfietud1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecIirrpmn1(resultSet.getString("i_dec_iirrpmn1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111888(resultSet.getBigDecimal("a_111888"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111889(resultSet.getBigDecimal("a_111889"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111021(resultSet.getBigDecimal("i_111021"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111827(resultSet.getString("i_111827"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecIademce1(resultSet.getString("i_dec_iademce1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecDciafre1(resultSet.getString("i_dec_dciafre1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111023(resultSet.getString("i_111023"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecIrpeaie1(resultSet.getString("i_dec_irpeaie1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI111024(resultSet.getBigDecimal("i_111024"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111201(resultSet.getBigDecimal("a_111201"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111202(resultSet.getBigDecimal("a_111202"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111207(resultSet.getBigDecimal("a_111207"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111206(resultSet.getBigDecimal("a_111206"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111401(resultSet.getString("a_111401"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111421(resultSet.getString("a_111421"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111402(resultSet.getString("a_111402"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecIlpioug1(resultSet.getString("i_dec_ilpioug1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecEfpdama1(resultSet.getString("i_dec_efpdama1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111422(resultSet.getString("a_111422"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111407(resultSet.getString("a_111407"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111404(resultSet.getString("a_111404"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111938(resultSet.getString("a_111938"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111501(resultSet.getString("a_111501"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111504(resultSet.getString("a_111504"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111523(resultSet.getString("a_111523"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecIliebim1(resultSet.getString("i_dec_iliebim1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111544(resultSet.getString("a_111544"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111535(resultSet.getString("a_111535"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111840(resultSet.getString("a_111840"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111550(resultSet.getString("a_111550"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111551(resultSet.getString("a_111551"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111552(resultSet.getString("a_111552"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111553(resultSet.getString("a_111553"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111842(resultSet.getString("a_111842"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111743(resultSet.getString("a_111743"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111730(resultSet.getString("a_111730"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111732(resultSet.getString("a_111732"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111744(resultSet.getString("a_111744"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111734(resultSet.getString("a_111734"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111728(resultSet.getString("a_111728"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111740(resultSet.getString("a_111740"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111741(resultSet.getString("a_111741"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111742(resultSet.getString("a_111742"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111724(resultSet.getString("a_111724"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111927(resultSet.getString("a_111927"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111726(resultSet.getString("a_111726"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111850(resultSet.getString("a_111850"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111851(resultSet.getString("a_111851"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111852(resultSet.getString("a_111852"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111856(resultSet.getString("a_111856"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111853(resultSet.getString("a_111853"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111880(resultSet.getString("a_111880"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111881(resultSet.getString("a_111881"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111882(resultSet.getString("a_111882"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111883(resultSet.getString("a_111883"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111884(resultSet.getString("a_111884"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecPutpeat1(resultSet.getString("i_dec_putpeat1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecDuapspa1(resultSet.getString("i_dec_duapspa1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecDpapspa1(resultSet.getString("i_dec_dpapspa1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111885(resultSet.getString("a_111885"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111886(resultSet.getString("a_111886"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111142(resultSet.getString("a_111142"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111143(resultSet.getString("a_111143"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111192(resultSet.getString("a_111192"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecIliaemo1(resultSet.getString("i_dec_iliaemo1"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecEfpdams1(resultSet.getString("i_dec_efpdams1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111144(resultSet.getString("a_111144"));
        declaracionISRPersonaFisica1317Forma13aDto.setI3DecUftiisl1(resultSet.getString("i3_dec_uftiisl1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI3DecPfeirsd1(resultSet.getString("i3_dec_pfeirsd1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI3DecPutpeat1(resultSet.getString("i3_dec_putpeat1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111145(resultSet.getString("a_111145"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111146(resultSet.getString("a_111146"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111147(resultSet.getString("a_111147"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111148(resultSet.getString("a_111148"));
        declaracionISRPersonaFisica1317Forma13aDto.setA116392(resultSet.getString("a_116392"));
        declaracionISRPersonaFisica1317Forma13aDto.setA116393(resultSet.getString("a_116393"));
        declaracionISRPersonaFisica1317Forma13aDto.setA116399(resultSet.getString("a_116399"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecGridtae1(resultSet.getString("i_dec_gridtae1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117918(resultSet.getString("a_117918"));
        declaracionISRPersonaFisica1317Forma13aDto.setA118952(resultSet.getString("a_118952"));
        declaracionISRPersonaFisica1317Forma13aDto.setA118954(resultSet.getString("a_118954"));
        declaracionISRPersonaFisica1317Forma13aDto.setIDecPuteatr1(resultSet.getString("i_dec_puteatr1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI4DecDuitfie1(resultSet.getString("i4_dec_duitfie1"));
        declaracionISRPersonaFisica1317Forma13aDto.setI4DecDpiefre1(resultSet.getString("i4_dec_dpiefre1"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111005(resultSet.getString("a_111005"));
        declaracionISRPersonaFisica1317Forma13aDto.setA118955(resultSet.getString("a_118955"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117001(resultSet.getString("a_117001"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117073(resultSet.getString("a_117073"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117071(resultSet.getString("a_117071"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117076(resultSet.getString("a_117076"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117285(resultSet.getString("a_117285"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117065(resultSet.getString("a_117065"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117066(resultSet.getString("a_117066"));
        declaracionISRPersonaFisica1317Forma13aDto.setA117889(resultSet.getString("a_117889"));
        declaracionISRPersonaFisica1317Forma13aDto.setA201356(resultSet.getString("a_201356"));
        declaracionISRPersonaFisica1317Forma13aDto.setA111191(resultSet.getString("a_111191"));

        return declaracionISRPersonaFisica1317Forma13aDto;
    }

}
