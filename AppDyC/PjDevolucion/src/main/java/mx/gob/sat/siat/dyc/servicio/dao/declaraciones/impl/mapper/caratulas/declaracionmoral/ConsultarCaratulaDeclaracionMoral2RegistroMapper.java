/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionmoral;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RegistroDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarCaratulaDeclaracionMoral2RegistroMapper implements RowMapper<ConsultarCaratulaDeclaracionMoral2RegistroDTO>  {
    
    public ConsultarCaratulaDeclaracionMoral2RegistroMapper() {
        super();
    }
    
    public ConsultarCaratulaDeclaracionMoral2RegistroDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarCaratulaDeclaracionMoral2RegistroDTO datosCaratulaDeclaracionMoral2RegistroDTO = new ConsultarCaratulaDeclaracionMoral2RegistroDTO();

        datosCaratulaDeclaracionMoral2RegistroDTO.setVNNumeroOperacion(resultado.getBigDecimal("v_n_numero_operacion"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVFperceh1(resultado.getDate("v_fperceh1"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVImpidee1(resultado.getInt("v_impidee1"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVIapidne1(resultado.getInt("v_iapidne1"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVImpfdee1(resultado.getInt("v_impfdee1"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVIapfdne1(resultado.getInt("v_iapfdne1"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVTdiepco1(resultado.getInt("v_tdiepco1"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC118228(resultado.getString("v_c_118228"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC110001(resultado.getBigDecimal("v_c_110001"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC120007(resultado.getBigDecimal("v_c_120007"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC130004(resultado.getBigDecimal("v_c_130004"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201010(resultado.getBigDecimal("v_i_201010"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC100025(resultado.getBigDecimal("v_c_100025"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC100009(resultado.getBigDecimal("v_c_100009"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC100013(resultado.getBigDecimal("v_c_100013"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201011(resultado.getString("v_i_201011"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950018(resultado.getBigDecimal("v_c_950018"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201012(resultado.getBigDecimal("v_i_201012"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201013(resultado.getBigDecimal("v_i_201013"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950047(resultado.getBigDecimal("v_c_950047"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950048(resultado.getBigDecimal("v_c_950048"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950049(resultado.getBigDecimal("v_c_950049"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950052(resultado.getBigDecimal("v_c_950052"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950022(resultado.getBigDecimal("v_c_950022"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950019(resultado.getBigDecimal("v_c_950019"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC950020(resultado.getBigDecimal("v_c_950020"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201014(resultado.getString("v_i_201014"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201015(resultado.getBigDecimal("v_i_201015"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI205004(resultado.getBigDecimal("v_i_205004"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201016(resultado.getBigDecimal("v_i_201016"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201017(resultado.getBigDecimal("v_i_201017"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201018(resultado.getString("v_i_201018"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVI201019(resultado.getString("v_i_201019"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC910004(resultado.getString("v_c_910004"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC900000(resultado.getBigDecimal("v_c_900000"));
        datosCaratulaDeclaracionMoral2RegistroDTO.setVC205001(resultado.getInt("v_c_205001"));
        
        return datosCaratulaDeclaracionMoral2RegistroDTO;
    }
}