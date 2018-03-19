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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO;

import org.springframework.jdbc.core.RowMapper;


public class ConsultarCaratulaDeclaracionSociedadControladora2AForma2Mapper implements RowMapper<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO>  {

    public ConsultarCaratulaDeclaracionSociedadControladora2AForma2Mapper() {
        super();
    }
    /**
     * TODO
     * @param resultado
     * @param rowNum
     * @return
     * @throws SQLException
     * @autor ISCC
     */
    public ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {

        ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO datosCaratulaDeclaracionSociedadControladora2AForma2DTO = new ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO();

        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVNNumeroOperacion(resultado.getString("v_n_numero_operacion"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVFperceh1(resultado.getDate("v_fperceh1"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVImpidee1(resultado.getInt("v_impidee1"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVIapidne1(resultado.getInt("v_iapidne1"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVImpfdee1(resultado.getInt("v_impfdee1"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVIapfdne1(resultado.getInt("v_iapfdne1"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVTdiepco1(resultado.getString("v_tdiepco1"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC118228(resultado.getString("v_c_118228"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC110001(resultado.getString("v_c_110001"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC120007(resultado.getInt("v_c_120007"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC130004(resultado.getInt("v_c_130004"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201010(resultado.getInt("v_i_201010"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC100025(resultado.getInt("v_c_100025"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC100009(resultado.getInt("v_c_100009"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC100013(resultado.getInt("v_c_100013"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201011(resultado.getBigDecimal("v_i_201011"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950018(resultado.getInt("v_c_950018"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201012(resultado.getBigDecimal("v_i_201012"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201013(resultado.getInt("v_i_201013"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950047(resultado.getInt("v_c_950047"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950048(resultado.getInt("v_c_950048"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950049(resultado.getInt("v_c_950049"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950052(resultado.getBigDecimal("v_c_950052"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950022(resultado.getInt("v_c_950022"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950019(resultado.getInt("v_c_950019"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC950020(resultado.getInt("v_c_950020"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201014(resultado.getBigDecimal("v_i_201014"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201015(resultado.getBigDecimal("v_i_201015"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI205004(resultado.getBigDecimal("v_i_205004"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201016(resultado.getBigDecimal("v_i_201016"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201017(resultado.getBigDecimal("v_i_201017"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201018(resultado.getString("v_i_201018"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVI201019(resultado.getInt("v_i_201019"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC910004(resultado.getBigDecimal("v_c_910004"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC900000(resultado.getInt("v_c_900000"));
        datosCaratulaDeclaracionSociedadControladora2AForma2DTO.setVC205001(resultado.getString("v_c_205001"));


        return datosCaratulaDeclaracionSociedadControladora2AForma2DTO;
    }
}
