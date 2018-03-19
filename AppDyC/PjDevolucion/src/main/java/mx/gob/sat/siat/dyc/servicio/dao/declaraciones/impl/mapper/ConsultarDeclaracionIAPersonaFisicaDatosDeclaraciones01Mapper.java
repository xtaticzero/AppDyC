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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01Mapper implements RowMapper<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO>  {
    
    public ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01Mapper() {
        super();
    }
    
    public ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO
                currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO = new ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO();

        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setCNPeriodo(resultado.getInt("c_n_periodo"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setNEjercicio(resultado.getInt("n_ejercicio"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setTdiepco1(resultado.getInt("tdiepco1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setFperceh1(resultado.getDate("fperceh1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121026(resultado.getBigDecimal("i_121026"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121003(resultado.getBigDecimal("i_121003"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121004(resultado.getBigDecimal("i_121004"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121006(resultado.getBigDecimal("i_121006"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setA121102(resultado.getBigDecimal("a_121102"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setA121101(resultado.getBigDecimal("a_121101"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121039(resultado.getBigDecimal("i_121039"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121007(resultado.getBigDecimal("i_121007"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121008(resultado.getBigDecimal("i_121008"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121009(resultado.getBigDecimal("i_121009"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121012(resultado.getBigDecimal("i_121012"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121013(resultado.getBigDecimal("i_121013"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121014(resultado.getBigDecimal("i_121014"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121015(resultado.getBigDecimal("i_121015"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121860(resultado.getBigDecimal("i_121860"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121017(resultado.getBigDecimal("i_121017"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121016(resultado.getBigDecimal("i_121016"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121019(resultado.getBigDecimal("i_121019"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI111923(resultado.getBigDecimal("i_111923"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setA121048(resultado.getBigDecimal("a_121048"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setA121049(resultado.getBigDecimal("a_121049"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setI121021(resultado.getBigDecimal("i_121021"));
        
    
        return currentDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO;
    }
}

