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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02Mapper implements RowMapper<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO>  {
    
    public ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02Mapper() {
        super();
    }
    
    public ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO
                currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO = new ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO();

        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setNDecNoupmee1(resultado.getBigDecimal("n_dec_noupmee1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setCDecCtdalie1(resultado.getInt("c_dec_ctdalie1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setFDecFperceh1(resultado.getDate("f_dec_fperceh1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setNDecSoaa5li1(resultado.getInt("n_dec_soaa5li1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecPtreorm1(resultado.getBigDecimal("i_dec_ptreorm1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecPafdrci1(resultado.getBigDecimal("i_dec_pafdrci1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecPdreoum1(resultado.getBigDecimal("i_dec_pdreoum1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecVctaael1(resultado.getBigDecimal("i_dec_vctaael1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecTbeoijt1(resultado.getBigDecimal("i_dec_tbeoijt1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecDsmgvea1(resultado.getBigDecimal("i_dec_dsmgvea1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecVaaclto1(resultado.getBigDecimal("i_dec_vaaclto1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIdmeptu1(resultado.getBigDecimal("i_dec_idmeptu1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIdaa5li1(resultado.getBigDecimal("i_dec_idaa5li1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIaicmcm1(resultado.getBigDecimal("i_dec_iaicmcm1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIraemec1(resultado.getBigDecimal("i_dec_iraemec1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIraeame1(resultado.getBigDecimal("i_dec_iraeame1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecOatcrro1(resultado.getBigDecimal("i_dec_oatcrro1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIaaefad1(resultado.getBigDecimal("i_dec_iaaefad1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecPpepaar1(resultado.getBigDecimal("i_dec_ppepaar1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecDciafre1(resultado.getBigDecimal("i_dec_dciafre1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIasfemc1(resultado.getBigDecimal("i_dec_iasfemc1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIrpeaia2(resultado.getBigDecimal("i_dec_irpeaia2"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIaaoccc1(resultado.getBigDecimal("i_dec_iaaoccc1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIaaocfc1(resultado.getBigDecimal("i_dec_iaaocfc1"));
        currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO.setIDecIaaicem1(resultado.getBigDecimal("i_dec_iaaicem1"));
    
        return currentDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO;
    }
}

