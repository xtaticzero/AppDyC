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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarDeclaracionISRsociedadIntegradoraMapper implements RowMapper<ConsultarDeclaracionISRsociedadIntegradoraDTO>  {
    
    public ConsultarDeclaracionISRsociedadIntegradoraMapper() {
        super();
    }
    
    public ConsultarDeclaracionISRsociedadIntegradoraDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarDeclaracionISRsociedadIntegradoraDTO currentDeclaracionISRsociedadIntegradoraDTO = new ConsultarDeclaracionISRsociedadIntegradoraDTO();

        currentDeclaracionISRsociedadIntegradoraDTO.setVLdleacv1(resultado.getString("v_ldleacv1"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVFperceh1(resultado.getDate("v_fperceh1"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVTdiepco1(resultado.getString("v_tdiepco1"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111101(resultado.getBigDecimal("v_i_111101"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111102(resultado.getBigDecimal("v_i_111102"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111103(resultado.getBigDecimal("v_i_111103"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111104(resultado.getBigDecimal("v_i_111104"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111105(resultado.getBigDecimal("v_i_111105"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111106(resultado.getBigDecimal("v_i_111106"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111107(resultado.getBigDecimal("v_i_111107"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111108(resultado.getBigDecimal("v_i_111108"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111109(resultado.getBigDecimal("v_i_111109"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111110(resultado.getBigDecimal("v_i_111110"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111111(resultado.getInt("v_i_111111"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111112(resultado.getBigDecimal("v_i_111112"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111113(resultado.getBigDecimal("v_i_111113"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111114(resultado.getBigDecimal("v_i_111114"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111115(resultado.getInt("v_i_111115"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111904(resultado.getBigDecimal("v_i_111904"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI121904(resultado.getBigDecimal("v_i_121904"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI131904(resultado.getBigDecimal("v_i_131904"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111116(resultado.getInt("v_i_111116"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111117(resultado.getBigDecimal("v_i_111117"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111023(resultado.getInt("v_i_111023"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVI111118(resultado.getBigDecimal("v_i_111118"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVP2115134(resultado.getBigDecimal("v_p2_115134"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVP2115135(resultado.getBigDecimal("v_p2_115135"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVP2115136(resultado.getBigDecimal("v_p2_115136"));
        currentDeclaracionISRsociedadIntegradoraDTO.setVP2115137(resultado.getBigDecimal("v_p2_115137"));
    
        return currentDeclaracionISRsociedadIntegradoraDTO;
    }
}
