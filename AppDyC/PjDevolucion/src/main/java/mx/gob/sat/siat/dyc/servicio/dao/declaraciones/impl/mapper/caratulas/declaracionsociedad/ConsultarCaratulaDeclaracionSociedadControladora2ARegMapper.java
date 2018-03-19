/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionsociedad;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 */
public class ConsultarCaratulaDeclaracionSociedadControladora2ARegMapper implements RowMapper<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> {
    
    public ConsultarCaratulaDeclaracionSociedadControladora2ARegMapper() {
        super();
    }
    
    public ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO  datosCaratulaDeclaracionSociedadIntegradora2ARegDTO = new ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO();
        
        datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVNNumeroOperacion(resultado.getBigDecimal("v_n_numero_operacion"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVFperceh1(resultado.getDate("v_fperceh1"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVImpidee1(resultado.getInt("v_impidee1"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVIapidne1(resultado.getInt("v_iapidne1"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVImpfdee1(resultado.getInt("v_impfdee1"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVIapfdne1(resultado.getInt("v_iapfdne1"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVTdiepco1(resultado.getInt("v_tdiepco1"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC118228(resultado.getString("v_c_118228"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC110001(resultado.getBigDecimal("v_c_110001"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC120007(resultado.getBigDecimal("v_c_120007"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC130004(resultado.getBigDecimal("v_c_130004"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201010(resultado.getBigDecimal("v_i_201010"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC100025(resultado.getBigDecimal("v_c_100025"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC100009(resultado.getBigDecimal("v_c_100009"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC100013(resultado.getBigDecimal("v_c_100013"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201011(resultado.getString("v_i_201011"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950018(resultado.getBigDecimal("v_c_950018"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201012(resultado.getBigDecimal("v_i_201012"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201013(resultado.getBigDecimal("v_i_201013"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950047(resultado.getBigDecimal("v_c_950047"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950048(resultado.getBigDecimal("v_c_950048"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950049(resultado.getBigDecimal("v_c_950049"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950052(resultado.getBigDecimal("v_c_950052"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950022(resultado.getBigDecimal("v_c_950022"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950019(resultado.getBigDecimal("v_c_950019"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC950020(resultado.getBigDecimal("v_c_950020"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201014(resultado.getString("v_i_201014"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201015(resultado.getBigDecimal("v_i_201015"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI205004(resultado.getBigDecimal("v_i_205004"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201016(resultado.getBigDecimal("v_i_201016"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201017(resultado.getBigDecimal("v_i_201017"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201018(resultado.getString("v_i_201018"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVI201019(resultado.getString("v_i_201019"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC910004(resultado.getString("v_c_910004"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC900000(resultado.getBigDecimal("v_c_900000"));
                datosCaratulaDeclaracionSociedadIntegradora2ARegDTO.setVC205001(resultado.getInt("v_c_205001"));

        return datosCaratulaDeclaracionSociedadIntegradora2ARegDTO;
    }
}
