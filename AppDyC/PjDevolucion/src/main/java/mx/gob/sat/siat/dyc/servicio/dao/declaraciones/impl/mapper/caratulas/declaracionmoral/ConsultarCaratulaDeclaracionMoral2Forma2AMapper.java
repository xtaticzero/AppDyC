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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2Forma2ADTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarCaratulaDeclaracionMoral2Forma2AMapper implements RowMapper<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> {

    public ConsultarCaratulaDeclaracionMoral2Forma2AMapper() {
        super();
    }

    public ConsultarCaratulaDeclaracionMoral2Forma2ADTO mapRow(ResultSet resultado, int rowNum) throws SQLException {

        ConsultarCaratulaDeclaracionMoral2Forma2ADTO datosCaratulaDeclaracionMoral2Forma2ADTO =
            new ConsultarCaratulaDeclaracionMoral2Forma2ADTO();

        datosCaratulaDeclaracionMoral2Forma2ADTO.setVNNumeroOperacion(resultado.getString("v_n_numero_operacion"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVFperceh1(resultado.getDate("v_fperceh1"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVImpidee1(resultado.getInt("v_impidee1"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVIapidne1(resultado.getInt("v_iapidne1"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVImpfdee1(resultado.getInt("v_impfdee1"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVIapfdne1(resultado.getInt("v_iapfdne1"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVTdiepco1(resultado.getString("v_tdiepco1"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC118228(resultado.getString("v_c_118228"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC110001(resultado.getString("v_c_110001"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC120007(resultado.getInt("v_c_120007"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC130004(resultado.getInt("v_c_130004"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201010(resultado.getInt("v_i_201010"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC100025(resultado.getInt("v_c_100025"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC100009(resultado.getInt("v_c_100009"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC100013(resultado.getInt("v_c_100013"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201011(resultado.getBigDecimal("v_i_201011"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950018(resultado.getInt("v_c_950018"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201012(resultado.getBigDecimal("v_i_201012"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201013(resultado.getInt("v_i_201013"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950047(resultado.getInt("v_c_950047"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950048(resultado.getInt("v_c_950048"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950049(resultado.getInt("v_c_950049"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950052(resultado.getBigDecimal("v_c_950052"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950022(resultado.getInt("v_c_950022"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950019(resultado.getInt("v_c_950019"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC950020(resultado.getInt("v_c_950020"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201014(resultado.getBigDecimal("v_i_201014"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201015(resultado.getBigDecimal("v_i_201015"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI205004(resultado.getBigDecimal("v_i_205004"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201016(resultado.getBigDecimal("v_i_201016"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201017(resultado.getBigDecimal("v_i_201017"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201018(resultado.getString("v_i_201018"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVI201019(resultado.getInt("v_i_201019"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC910004(resultado.getBigDecimal("v_c_910004"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC900000(resultado.getInt("v_c_900000"));
        datosCaratulaDeclaracionMoral2Forma2ADTO.setVC205001(resultado.getString("v_c_205001"));

        return datosCaratulaDeclaracionMoral2Forma2ADTO;
    }
}
