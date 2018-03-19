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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCompetenciaAgaff;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @since 09/10/2013
 * @author ISCC
 */
public class ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36Mapper implements RowMapper<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO>  {
    
    public ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36Mapper() {
        super();
    }
    
    public DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO declaracionProvisionalODefinitivaDeImpuestosFederales36DTO = new DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO();

        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setNDecNoupmee1(resultado.getInt("n_dec_noupmee1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setFDecFperceh1(resultado.getDate("f_dec_fperceh1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setFDecFcieamc1(resultado.getDate("f_dec_fcieamc1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setCDecCtdliea1(resultado.getString("c_dec_ctdliea1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setCOblCcloanv1(resultado.getString("c_obl_ccloanv1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setDDecDrceeos1(resultado.getString("d_dec_drceeos1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagIfmapvu1(null != resultado.getBigDecimal("i_pag_ifmapvu1") ? resultado.getBigDecimal("i_pag_ifmapvu1") : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagIcmapru1(null != resultado.getBigDecimal("i_pag_icmapru1") ? resultado.getBigDecimal("i_pag_icmapru1") : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCapaada1(resultado.getInt("i_pag_capaada1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCarader1(resultado.getInt("i_pag_carader1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCamcadu1(resultado.getInt("i_pag_camcadu1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagTcootna1(resultado.getInt("i_pag_tcootna1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAcasprl1(resultado.getInt("i_pag_acasprl1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagSeumbps1(resultado.getInt("i_pag_seumbps1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAcpolmi1(null != resultado.getBigDecimal("i_pag_acpolmi1") ? resultado.getBigDecimal("i_pag_acpolmi1") : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAcidspm1(resultado.getInt("i_pag_acidspm1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAdatpiu1(resultado.getInt("i_pag_adatpiu1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAuiccps1(resultado.getInt("i_pag_auiccps1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagApaprgl1(resultado.getInt("i_pag_apaprgl1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAoeptsl1(resultado.getInt("i_pag_aoeptsl1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagActpeel1(resultado.getInt("i_pag_actpeel1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAdmpial1(resultado.getInt("i_pag_admpial1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagTaoptla1(resultado.getInt("i_pag_taoptla1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setFPagFpraeae1(resultado.getDate("f_pag_fpraeae1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagMpaoann1(resultado.getInt("i_pag_mpaoann1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagMpatoan1(resultado.getInt("i_pag_mpatoan1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCcaanrt1(resultado.getInt("i_pag_ccaanrt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setDPagAppalri1(resultado.getString("d_pag_appalri1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagPi1pamr1(resultado.getInt("i_pag_pi1pamr1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagPi1pamr2(resultado.getInt("i_pag_pi1pamr2"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCfaanvt1(resultado.getInt("i_pag_cfaanvt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCpaangt1(resultado.getInt("i_pag_cpaangt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setCUbiCeflnea1(resultado.getString("c_ubi_ceflnea1"));

        
        return declaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
    }
}

