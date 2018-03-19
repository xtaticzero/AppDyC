package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCompetenciaAgaff;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 07/05/2014
 */
public class DeclaracionProvisionalODefinitivaDeImpuestosFederales36Mapper implements RowMapper<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> {
    @Override
    public DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO mapRow(ResultSet rs, int i) throws SQLException {
        DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO declaracionProvisionalODefinitivaDeImpuestosFederales36DTO =
            new DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO();

        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setNDecNoupmee1(rs.getInt("n_dec_noupmee1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setFDecFperceh1(rs.getDate("f_dec_fperceh1"));
        rs.getDate("f_dec_fcieamc1");
        if (!rs.wasNull()) {
            declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setFDecFcieamc1(rs.getDate("f_dec_fcieamc1"));
        }

        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setCDecCtdliea1(rs.getString("c_dec_ctdliea1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setCOblCcloanv1(rs.getString("c_obl_ccloanv1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setDDecDrceeos1(rs.getString("d_dec_drceeos1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagIfmapvu1(null !=
                                                                                   rs.getBigDecimal("i_pag_ifmapvu1") ?
                                                                                   rs.getBigDecimal("i_pag_ifmapvu1") : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagIcmapru1(null !=
                                                                                   rs.getBigDecimal("i_pag_icmapru1") ?
                                                                                   rs.getBigDecimal("i_pag_icmapru1") : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCapaada1(rs.getInt("i_pag_capaada1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCarader1(rs.getInt("i_pag_carader1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCamcadu1(rs.getInt("i_pag_camcadu1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagTcootna1(rs.getInt("i_pag_tcootna1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAcasprl1(rs.getInt("i_pag_acasprl1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagSeumbps1(rs.getInt("i_pag_seumbps1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAcpolmi1(null !=
                                                                                   rs.getBigDecimal("i_pag_acpolmi1") ?
                                                                                   rs.getBigDecimal("i_pag_acpolmi1") : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAcidspm1(rs.getInt("i_pag_acidspm1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAdatpiu1(rs.getInt("i_pag_adatpiu1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAuiccps1(rs.getInt("i_pag_auiccps1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagApaprgl1(rs.getInt("i_pag_apaprgl1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAoeptsl1(rs.getInt("i_pag_aoeptsl1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagActpeel1(rs.getInt("i_pag_actpeel1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagAdmpial1(rs.getInt("i_pag_admpial1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagTaoptla1(rs.getInt("i_pag_taoptla1"));
        rs.getDate("f_pag_fpraeae1");
        if (!rs.wasNull()) {
            declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setFPagFpraeae1(rs.getDate("f_pag_fpraeae1"));
        }
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagMpaoann1(rs.getInt("i_pag_mpaoann1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagMpatoan1(rs.getInt("i_pag_mpatoan1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCcaanrt1(rs.getInt("i_pag_ccaanrt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setDPagAppalri1(rs.getString("d_pag_appalri1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagPi1pamr1(rs.getInt("i_pag_pi1pamr1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagPi1pamr2(rs.getInt("i_pag_pi1pamr2"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCfaanvt1(rs.getInt("i_pag_cfaanvt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setIPagCpaangt1(rs.getInt("i_pag_cpaangt1"));
        declaracionProvisionalODefinitivaDeImpuestosFederales36DTO.setCUbiCeflnea1(rs.getString("c_ubi_ceflnea1"));


        return declaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
    }

}
