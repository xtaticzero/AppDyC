/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT consIDerada
* ConfIDencial. Queda totalmente prohibIDo su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO ISCC
 */
public class ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAMapper implements RowMapper<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO>  {
    
    public ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAMapper() {
        super();
    }
    
    public DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO = new DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO();

        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVNDecNoupmee1(resultado.getBigDecimal("v_n_dec_noupmee1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVFDecFcieamc1(resultado.getDate("v_f_dec_fcieamc1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTaapt1i1(resultado.getBigDecimal("v_i_dec_taapt1i1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTaapt1i2(resultado.getBigDecimal("v_i_dec_taapt1i2"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTaapibs1(resultado.getBigDecimal("v_i_dec_taapibs1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTaapibs2(resultado.getBigDecimal("v_i_dec_taapibs2"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTdaapt01(resultado.getBigDecimal("v_i_dec_tdaapt01"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTaapqsp1(resultado.getBigDecimal("v_i_dec_taapqsp1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTiaapt11(resultado.getBigDecimal("v_i_dec_tiaapt11"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTiaapt12(resultado.getBigDecimal("v_i_dec_tiaapt12"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTiaapib1(resultado.getBigDecimal("v_i_dec_tiaapib1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTiaapib2(resultado.getBigDecimal("v_i_dec_tiaapib2"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTitacep1(resultado.getBigDecimal("v_i_dec_titacep1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecItabdia1(resultado.getBigDecimal("v_i_dec_itabdia1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIpiabdi1(resultado.getBigDecimal("v_i_dec_ipiabdi1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecItaider1(resultado.getBigDecimal("v_i_dec_itaider1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIpiider1(resultado.getBigDecimal("v_i_dec_ipiider1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTicaago1(resultado.getBigDecimal("v_i_dec_ticaago1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecItpiabd1(resultado.getBigDecimal("v_i_dec_itpiabd1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecItpiide1(resultado.getBigDecimal("v_i_dec_itpiide1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecPucaa5l1(resultado.getBigDecimal("v_i_dec_pucaa5l1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecPucaa5l2(resultado.getBigDecimal("v_i_dec_pucaa5l2"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIabuira1(resultado.getBigDecimal("v_i_dec_iabuira1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIavcare1(resultado.getBigDecimal("v_i_dec_iavcare1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecMaaidao1(resultado.getBigDecimal("v_i_dec_maaidao1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTiapovc1(resultado.getBigDecimal("v_i_dec_tiapovc1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecVaagt1a1(resultado.getBigDecimal("v_i_dec_vaagt1a1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecVaagt1a2(resultado.getBigDecimal("v_i_dec_vaagt1a2"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecVaagt0e1(resultado.getBigDecimal("v_i_dec_vaagt0e1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecVaagt0o1(resultado.getBigDecimal("v_i_dec_vaagt0o1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecSaagucc1(resultado.getBigDecimal("v_i_dec_saagucc1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecVaaqsdp1(resultado.getBigDecimal("v_i_dec_vaaqsdp1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIcmapuu1(resultado.getBigDecimal("v_i_dec_icmapuu1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecCardaac1(resultado.getBigDecimal("v_i_dec_cardaac1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIracvel1(resultado.getBigDecimal("v_i_dec_iracvel1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecTiaovct1(resultado.getBigDecimal("v_i_dec_tiaovct1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecOccctaa1(resultado.getBigDecimal("v_i_dec_occctaa1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecOcfctaa1(resultado.getBigDecimal("v_i_dec_ocfctaa1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecCcaanrt1(resultado.getBigDecimal("v_i_dec_ccaanrt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecSfaalvd1(resultado.getBigDecimal("v_i_dec_sfaalvd1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecDioenbv1(resultado.getBigDecimal("v_i_dec_dioenbv1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecSfpaael1(resultado.getBigDecimal("v_i_dec_sfpaael1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecAsfpaE1(resultado.getBigDecimal("v_i_dec_asfpa_e1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIaaadmi1(resultado.getBigDecimal("v_i_dec_iaaadmi1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecIcmapru1(resultado.getBigDecimal("v_i_dec_icmapru1"));
        declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO.setVIDecRsfiaaa1(resultado.getBigDecimal("v_i_dec_rsfiaaa1"));
        
        return declaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO;
    }
}