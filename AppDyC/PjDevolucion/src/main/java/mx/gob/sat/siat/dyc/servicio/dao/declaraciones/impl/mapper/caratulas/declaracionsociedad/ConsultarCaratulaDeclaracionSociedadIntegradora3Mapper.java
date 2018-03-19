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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez 23/07/2013
 */
public class ConsultarCaratulaDeclaracionSociedadIntegradora3Mapper implements RowMapper<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO>  {
    
    public ConsultarCaratulaDeclaracionSociedadIntegradora3Mapper() {
        super();
    }
    
    public ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO  datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO = new ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO();
        
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVNNumeroOperacion(resultado.getString("v_n_numero_operacion"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVFperceh1(resultado.getDate("v_fperceh1"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVImpidee1(resultado.getInt("v_impidee1"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVIapidne1(resultado.getInt("v_iapidne1"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVImpfdee1(resultado.getInt("v_impfdee1"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVIapfdne1(resultado.getInt("v_iapfdne1"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVTdiepco1(resultado.getString("v_tdiepco1"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC118228(resultado.getString("v_c_118228"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC110004(resultado.getString("v_c_110004"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC120007(resultado.getInt("v_c_120007"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC130005(resultado.getInt("v_c_130005"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201010(resultado.getInt("v_i_201010"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC100025(resultado.getInt("v_c_100025"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC100009(resultado.getInt("v_c_100009"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC100013(resultado.getInt("v_c_100013"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201011(resultado.getBigDecimal("v_i_201011"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950018(resultado.getInt("v_c_950018"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201012(resultado.getBigDecimal("v_i_201012"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201013(resultado.getInt("v_i_201013"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950047(resultado.getInt("v_c_950047"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950048(resultado.getInt("v_c_950048"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950049(resultado.getInt("v_c_950049"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950052(resultado.getBigDecimal("v_c_950052"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950022(resultado.getInt("v_c_950022"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950019(resultado.getInt("v_c_950019"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC950020(resultado.getInt("v_c_950020"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201014(resultado.getBigDecimal("v_i_201014"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201015(resultado.getBigDecimal("v_i_201015"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI205004(resultado.getBigDecimal("v_i_205004"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201016(resultado.getBigDecimal("v_i_201016"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201017(resultado.getBigDecimal("v_i_201017"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201018(resultado.getString("v_i_201018"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVI201019(resultado.getInt("v_i_201019"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC910004(resultado.getBigDecimal("v_c_910004"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC900000(resultado.getInt("v_c_900000"));
        datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO.setVC205001(resultado.getString("v_c_205001"));
        
        
        
        return datosCaratulaDeclaracionSociedadIntegradora3Forma3DTO;
    }
}
