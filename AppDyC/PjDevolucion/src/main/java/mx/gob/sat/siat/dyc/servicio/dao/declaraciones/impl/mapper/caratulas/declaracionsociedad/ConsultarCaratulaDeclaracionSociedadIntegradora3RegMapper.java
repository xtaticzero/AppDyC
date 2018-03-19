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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez 23/07/2013
 */
public class ConsultarCaratulaDeclaracionSociedadIntegradora3RegMapper implements RowMapper<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>  {
    
    public ConsultarCaratulaDeclaracionSociedadIntegradora3RegMapper() {
        super();
    }
    
    public ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO  datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO = new ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO();
        
        datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVNNumeroOperacion(resultado.getBigDecimal("v_n_numero_operacion"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVFperceh1(resultado.getDate("v_fperceh1"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVImpidee1(resultado.getInt("v_impidee1"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVIapidne1(resultado.getInt("v_iapidne1"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVImpfdee1(resultado.getInt("v_impfdee1"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVIapfdne1(resultado.getInt("v_iapfdne1"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVTdiepco1(resultado.getInt("v_tdiepco1"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC118228(resultado.getString("v_c_118228"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC110004(resultado.getBigDecimal("v_c_110004"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC120007(resultado.getBigDecimal("v_c_120007"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC130005(resultado.getBigDecimal("v_c_130005"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201010(resultado.getBigDecimal("v_i_201010"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC100025(resultado.getBigDecimal("v_c_100025"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC100009(resultado.getBigDecimal("v_c_100009"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC100013(resultado.getBigDecimal("v_c_100013"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201011(resultado.getString("v_i_201011"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950018(resultado.getBigDecimal("v_c_950018"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201012(resultado.getBigDecimal("v_i_201012"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201013(resultado.getBigDecimal("v_i_201013"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950047(resultado.getBigDecimal("v_c_950047"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950048(resultado.getBigDecimal("v_c_950048"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950049(resultado.getBigDecimal("v_c_950049"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950052(resultado.getBigDecimal("v_c_950052"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950022(resultado.getBigDecimal("v_c_950022"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950019(resultado.getBigDecimal("v_c_950019"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC950020(resultado.getBigDecimal("v_c_950020"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201014(resultado.getString("v_i_201014"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201015(resultado.getBigDecimal("v_i_201015"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI205004(resultado.getBigDecimal("v_i_205004"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201016(resultado.getBigDecimal("v_i_201016"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201017(resultado.getBigDecimal("v_i_201017"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201018(resultado.getString("v_i_201018"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVI201019(resultado.getString("v_i_201019"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC910004(resultado.getString("v_c_910004"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC900000(resultado.getBigDecimal("v_c_900000"));
                datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO.setVC205001(resultado.getInt("v_c_205001"));
        
        
        
        return datosCaratulaDeclaracionSociedadIntegradoraRegistroDTO;
    }
}
