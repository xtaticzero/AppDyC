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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.personafisica1317.ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author  Alfredo Ramirez
 * @since   13/08/2013
 *
 */
public class ConsultarDeclaracionISRPersonaFisica1317DeclarasatMapper implements RowMapper<ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO> {

    public ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO mapRow(ResultSet resultSet,
                                                                        int rowNum) throws SQLException {
        ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO declaracionISRPersonaFisica1317DeclarasatDto =
            new ConsultarDeclaracionISRPersonaFisica1317DeclarasatDTO();

        declaracionISRPersonaFisica1317DeclarasatDto.setNumeroOperacion(resultSet.getBigDecimal("numero_operacion"));
        declaracionISRPersonaFisica1317DeclarasatDto.setTdiepco1(resultSet.getInt("tdiepco1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setFDecFperceh1(resultSet.getDate("f_dec_fperceh1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111837(resultSet.getBigDecimal("i_111837"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111838(resultSet.getBigDecimal("i_111838"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111810(resultSet.getBigDecimal("i_111810"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111811(resultSet.getBigDecimal("i_111811"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111812(resultSet.getBigDecimal("i_111812"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111813(resultSet.getBigDecimal("i_111813"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecSeumbps1(resultSet.getBigDecimal("i_dec_seumbps1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111848(resultSet.getBigDecimal("i_111848"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111817(resultSet.getBigDecimal("i_111817"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111815(resultSet.getBigDecimal("i_111815"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111818(resultSet.getBigDecimal("i_111818"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111860(resultSet.getBigDecimal("i_111860"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecEpidtsr1(resultSet.getBigDecimal("i_dec_epidtsr1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecEpipcns1(resultSet.getBigDecimal("i_dec_epipcns1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecPeefafn1(resultSet.getBigDecimal("i_dec_peefafn1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111820(resultSet.getBigDecimal("i_111820"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111819(resultSet.getBigDecimal("i_111819"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111822(resultSet.getBigDecimal("i_111822"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecCfietud1(resultSet.getBigDecimal("i_dec_cfietud1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecIirrpmn1(resultSet.getBigDecimal("i_dec_iirrpmn1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111888(resultSet.getBigDecimal("a_111888"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111889(resultSet.getBigDecimal("a_111889"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111021(resultSet.getBigDecimal("i_111021"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111827(resultSet.getBigDecimal("i_111827"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecIademce1(resultSet.getBigDecimal("i_dec_iademce1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecDciafre1(resultSet.getBigDecimal("i_dec_dciafre1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111023(resultSet.getBigDecimal("i_111023"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecIrpeaie1(resultSet.getBigDecimal("i_dec_irpeaie1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI111024(resultSet.getBigDecimal("i_111024"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111201(resultSet.getBigDecimal("a_111201"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111202(resultSet.getBigDecimal("a_111202"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111207(resultSet.getBigDecimal("a_111207"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111206(resultSet.getBigDecimal("a_111206"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111401(resultSet.getBigDecimal("a_111401"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111421(resultSet.getBigDecimal("a_111421"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111402(resultSet.getBigDecimal("a_111402"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecIlpioug1(resultSet.getBigDecimal("i_dec_ilpioug1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecEfpdama1(resultSet.getBigDecimal("i_dec_efpdama1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111422(resultSet.getBigDecimal("a_111422"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111407(resultSet.getBigDecimal("a_111407"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111404(resultSet.getBigDecimal("a_111404"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111938(resultSet.getBigDecimal("a_111938"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111501(resultSet.getBigDecimal("a_111501"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111504(resultSet.getBigDecimal("a_111504"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111523(resultSet.getBigDecimal("a_111523"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecIliebim1(resultSet.getBigDecimal("i_dec_iliebim1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111544(resultSet.getBigDecimal("a_111544"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111535(resultSet.getBigDecimal("a_111535"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111840(resultSet.getBigDecimal("a_111840"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111550(resultSet.getBigDecimal("a_111550"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111551(resultSet.getBigDecimal("a_111551"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111552(resultSet.getBigDecimal("a_111552"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111553(resultSet.getBigDecimal("a_111553"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111842(resultSet.getBigDecimal("a_111842"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111743(resultSet.getBigDecimal("a_111743"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111730(resultSet.getBigDecimal("a_111730"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111732(resultSet.getBigDecimal("a_111732"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111744(resultSet.getBigDecimal("a_111744"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111734(resultSet.getBigDecimal("a_111734"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111728(resultSet.getBigDecimal("a_111728"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111740(resultSet.getBigDecimal("a_111740"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111741(resultSet.getBigDecimal("a_111741"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111742(resultSet.getBigDecimal("a_111742"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111724(resultSet.getBigDecimal("a_111724"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111927(resultSet.getBigDecimal("a_111927"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111726(resultSet.getBigDecimal("a_111726"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111850(resultSet.getBigDecimal("a_111850"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111851(resultSet.getBigDecimal("a_111851"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111852(resultSet.getBigDecimal("a_111852"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111856(resultSet.getBigDecimal("a_111856"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111853(resultSet.getBigDecimal("a_111853"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111880(resultSet.getBigDecimal("a_111880"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111881(resultSet.getBigDecimal("a_111881"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111882(resultSet.getBigDecimal("a_111882"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111883(resultSet.getBigDecimal("a_111883"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111884(resultSet.getBigDecimal("a_111884"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecPutpeat1(resultSet.getBigDecimal("i_dec_putpeat1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecDuapspa1(resultSet.getBigDecimal("i_dec_duapspa1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecDpapspa1(resultSet.getBigDecimal("i_dec_dpapspa1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111885(resultSet.getBigDecimal("a_111885"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111886(resultSet.getBigDecimal("a_111886"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111142(resultSet.getBigDecimal("a_111142"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111143(resultSet.getBigDecimal("a_111143"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111192(resultSet.getBigDecimal("a_111192"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecIliaemo1(resultSet.getBigDecimal("i_dec_iliaemo1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecEfpdams1(resultSet.getBigDecimal("i_dec_efpdams1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111144(resultSet.getBigDecimal("a_111144"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI3DecUftiisl1(resultSet.getBigDecimal("i3_dec_uftiisl1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI3DecPfeirsd1(resultSet.getBigDecimal("i3_dec_pfeirsd1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI3DecPutpeat1(resultSet.getBigDecimal("i3_dec_putpeat1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111145(resultSet.getBigDecimal("a_111145"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111146(resultSet.getBigDecimal("a_111146"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111147(resultSet.getBigDecimal("a_111147"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111148(resultSet.getBigDecimal("a_111148"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA116392(resultSet.getBigDecimal("a_116392"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA116393(resultSet.getBigDecimal("a_116393"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA116399(resultSet.getBigDecimal("a_116399"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecGridtae1(resultSet.getBigDecimal("i_dec_gridtae1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117918(resultSet.getBigDecimal("a_117918"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA118952(resultSet.getBigDecimal("a_118952"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA118954(resultSet.getBigDecimal("a_118954"));
        declaracionISRPersonaFisica1317DeclarasatDto.setIDecPuteatr1(resultSet.getBigDecimal("i_dec_puteatr1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI4DecDuitfie1(resultSet.getBigDecimal("i4_dec_duitfie1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setI4DecDpiefre1(resultSet.getBigDecimal("i4_dec_dpiefre1"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111005(resultSet.getBigDecimal("a_111005"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA118955(resultSet.getBigDecimal("a_118955"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117001(resultSet.getBigDecimal("a_117001"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117073(resultSet.getBigDecimal("a_117073"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117071(resultSet.getBigDecimal("a_117071"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117076(resultSet.getBigDecimal("a_117076"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117285(resultSet.getBigDecimal("a_117285"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117065(resultSet.getBigDecimal("a_117065"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117066(resultSet.getBigDecimal("a_117066"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA117889(resultSet.getBigDecimal("a_117889"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA201356(resultSet.getBigDecimal("a_201356"));
        declaracionISRPersonaFisica1317DeclarasatDto.setA111191(resultSet.getBigDecimal("a_111191"));

        return declaracionISRPersonaFisica1317DeclarasatDto;
    }

}
