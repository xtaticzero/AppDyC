/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarDeclaracionISRSociedadControladora1Mapper implements RowMapper<ConsultarDeclaracionISRSociedadControladora1DTO>  {
    
    public ConsultarDeclaracionISRSociedadControladora1Mapper() {
        super();
    }
    
    public ConsultarDeclaracionISRSociedadControladora1DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarDeclaracionISRSociedadControladora1DTO  currentDeclaracionISRSociedadControladora1DTO = new ConsultarDeclaracionISRSociedadControladora1DTO();

        currentDeclaracionISRSociedadControladora1DTO.setVNNumeroOperacion(resultado.getString("v_n_numero_operacion"));
                currentDeclaracionISRSociedadControladora1DTO.setVIapidne1(resultado.getInt("v_iapidne1"));
                currentDeclaracionISRSociedadControladora1DTO.setVCplearv1(resultado.getInt("v_cplearv1"));
                currentDeclaracionISRSociedadControladora1DTO.setVTdiepco1(resultado.getString("v_tdiepco1"));
                currentDeclaracionISRSociedadControladora1DTO.setVFperceh1(resultado.getDate("v_fperceh1"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111025(resultado.getBigDecimal("v_i_111025"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111026(resultado.getInt("v_i_111026"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111027(resultado.getBigDecimal("v_i_111027"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111028(resultado.getBigDecimal("v_i_111028"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111029(resultado.getBigDecimal("v_i_111029"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3100302(resultado.getString("v_i_3100302"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3100402(resultado.getString("v_i_3100402"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110202(resultado.getString("v_i_3110202"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110602(resultado.getString("v_i_3110602"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110302(resultado.getString("v_i_3110302"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111030(resultado.getBigDecimal("v_i_111030"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3101702(resultado.getString("v_i_3101702"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111031(resultado.getBigDecimal("v_i_111031"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111032(resultado.getBigDecimal("v_i_111032"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111033(resultado.getBigDecimal("v_i_111033"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111034(resultado.getBigDecimal("v_i_111034"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111035(resultado.getBigDecimal("v_i_111035"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111036(resultado.getBigDecimal("v_i_111036"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111037(resultado.getBigDecimal("v_i_111037"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3102402(resultado.getString("v_i_3102402"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3102502(resultado.getString("v_i_3102502"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3102702(resultado.getString("v_i_3102702"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3102802(resultado.getString("v_i_3102802"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111038(resultado.getBigDecimal("v_i_111038"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111039(resultado.getInt("v_i_111039"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111040(resultado.getBigDecimal("v_i_111040"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111041(resultado.getBigDecimal("v_i_111041"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111042(resultado.getBigDecimal("v_i_111042"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111043(resultado.getBigDecimal("v_i_111043"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111044(resultado.getBigDecimal("v_i_111044"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111045(resultado.getBigDecimal("v_i_111045"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3103702(resultado.getString("v_i_3103702"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111003(resultado.getBigDecimal("v_i_111003"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111004(resultado.getBigDecimal("v_i_111004"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111046(resultado.getBigDecimal("v_i_111046"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111005(resultado.getBigDecimal("v_i_111005"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111006(resultado.getBigDecimal("v_i_111006"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111007(resultado.getBigDecimal("v_i_111007"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3107302(resultado.getString("v_i_3107302"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3107402(resultado.getString("v_i_3107402"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3104602(resultado.getString("v_i_3104602"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111008(resultado.getBigDecimal("v_i_111008"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111009(resultado.getBigDecimal("v_i_111009"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111047(resultado.getBigDecimal("v_i_111047"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111048(resultado.getBigDecimal("v_i_111048"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3105002(resultado.getString("v_i_3105002"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3107502(resultado.getString("v_i_3107502"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110502(resultado.getString("v_i_3110502"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3107602(resultado.getString("v_i_3107602"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111049(resultado.getBigDecimal("v_i_111049"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111050(resultado.getBigDecimal("v_i_111050"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111013(resultado.getBigDecimal("v_i_111013"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111051(resultado.getBigDecimal("v_i_111051"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111052(resultado.getBigDecimal("v_i_111052"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111014(resultado.getBigDecimal("v_i_111014"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111053(resultado.getBigDecimal("v_i_111053"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111054(resultado.getBigDecimal("v_i_111054"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111055(resultado.getBigDecimal("v_i_111055"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111056(resultado.getBigDecimal("v_i_111056"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111017(resultado.getInt("v_i_111017"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111057(resultado.getBigDecimal("v_i_111057"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110702(resultado.getString("v_i_3110702"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110802(resultado.getString("v_i_3110802"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110902(resultado.getString("v_i_3110902"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3106502(resultado.getString("v_i_3106502"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3106602(resultado.getString("v_i_3106602"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3107702(resultado.getString("v_i_3107702"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3111002(resultado.getString("v_i_3111002"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111058(resultado.getBigDecimal("v_i_111058"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3108002(resultado.getString("v_i_3108002"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3110402(resultado.getString("v_i_3110402"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111018(resultado.getBigDecimal("v_i_111018"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111019(resultado.getBigDecimal("v_i_111019"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3111202(resultado.getString("v_i_3111202"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3107202(resultado.getString("v_i_3107202"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111020(resultado.getBigDecimal("v_i_111020"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111904(resultado.getBigDecimal("v_i_111904"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111021(resultado.getBigDecimal("v_i_111021"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111022(resultado.getBigDecimal("v_i_111022"));
                currentDeclaracionISRSociedadControladora1DTO.setVI11109(resultado.getString("v_i_11109"));
                currentDeclaracionISRSociedadControladora1DTO.setVI3111102(resultado.getString("v_i_3111102"));
                currentDeclaracionISRSociedadControladora1DTO.setVI111024(resultado.getBigDecimal("v_i_111024"));

        return currentDeclaracionISRSociedadControladora1DTO;
    }
}
