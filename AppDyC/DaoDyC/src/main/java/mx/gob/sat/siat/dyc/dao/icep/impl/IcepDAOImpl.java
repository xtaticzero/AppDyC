/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.icep.impl;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.icep.IcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.impl.mapper.ObtieneIcepUrdcsilMapper;
import mx.gob.sat.siat.dyc.dao.icep.procedures.IcepSpSioUrucple1StoredProcedure;
import mx.gob.sat.siat.dyc.dao.icep.procedures.IcepUrdcFat1StoreProcedure;
import mx.gob.sat.siat.dyc.dao.icep.procedures.IcepUrdcFatStoreProcedure;
import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author  Israel Chavez
 */
@Repository(value = "icepDAO")
public class IcepDAOImpl implements IcepDAO, SQLInformixSIAT, SQLOracleSIAT {

    private Logger log = Logger.getLogger(IcepDAOImpl.class.getName());

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplateSIAT;

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplateDwhOra2;

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplateInformix;
    
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplateDYCDB2;

    public IcepUrdcFatDTO obtieneIcepUrdFat(IcepUrdcFatDTO icepDTO) throws SQLException
    {
        try {
            IcepUrdcFatStoreProcedure icepSP = new IcepUrdcFatStoreProcedure(jdbcTemplateInformix, STORE_PROCEDURE_BUSCA_ICEP_URDC_FAT);
            return icepSP.buscaIcep(icepDTO);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ICEP_URDC_FAT + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(icepDTO));
            throw dae;
        }
    }

    public List<IcepUrdcsilDTO> obtieneIcepUrdcsil(IcepUrdcsilDTO icep) throws SQLException
    {
        try {
            if (Integer.valueOf(icep.getEjercicio()) < ConstantesDyCNumerico.VALOR_2008) {
                return
                        jdbcTemplateSIAT.query(OBTIENEN_ICEP_URDCSIL_ORACLE, new Object[] { icep.getRfc(),
                                                                                            icep.getRfc1(),
                                                                                            icep.getRfc2(),
                                                                                            icep.getPeriodo(),
                                                                                            icep.getEjercicio() },
                                               new ObtieneIcepUrdcsilMapper());
            } else {
                return
                        jdbcTemplateDwhOra2.query(OBTIENEN_ICEP_URDCSIL_ORACLE, new Object[] { icep.getRfc(),
                                                                                               icep.getRfc1(),
                                                                                               icep.getRfc2(),
                                                                                               icep.getPeriodo(),
                                                                                               icep.getEjercicio() },
                                                  new ObtieneIcepUrdcsilMapper());
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      OBTIENEN_ICEP_URDCSIL_ORACLE + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(icep));
            throw dae;
        }
    }

    public IcepSioUrucple1DTO retrieveIcepSpSioUrucple1(IcepSioUrucple1DTO icepDTO) throws SQLException {

        IcepSpSioUrucple1StoredProcedure icepSP;

        icepSP = new IcepSpSioUrucple1StoredProcedure(jdbcTemplateInformix, STORE_PROCEDURE_BUSCA_ICEP_URUCPLE);

        IcepSioUrucple1DTO icep = new IcepSioUrucple1DTO();
        log.info(icep);

        try {

            icep = icepSP.buscaIcep(icepDTO);

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ICEP_URUCPLE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(icepDTO));
            throw dae;
        } catch (SQLException sqle) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ICEP_URUCPLE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(icepDTO));
            throw sqle;

        }
        return icep;

    }

    public IcepSioUrucple1DTO obtieneIcepUrdcsil1(IcepSioUrucple1DTO icepDTO) throws SQLException {

        IcepSpSioUrucple1StoredProcedure icepSP;

        if (Integer.valueOf(icepDTO.getEjercicio()) < ConstantesDyCNumerico.VALOR_2008) {
            icepSP = new IcepSpSioUrucple1StoredProcedure(jdbcTemplateSIAT, STORE_PROCEDURE_BUSCA_ICEP_URDCSIL1);
        } else {
            icepSP = new IcepSpSioUrucple1StoredProcedure(jdbcTemplateDwhOra2, STORE_PROCEDURE_BUSCA_ICEP_URDCSIL1);
        }

        IcepSioUrucple1DTO icep = new IcepSioUrucple1DTO();
        log.info(icep);

        try {

            icep = icepSP.buscaIcep(icepDTO);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                      STORE_PROCEDURE_BUSCA_ICEP_URDCSIL1 + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(icepDTO));
            throw dae;

        }
        return icep;

    }

    public IcepUrdcFatDTO  obtieneIcepUrdcFat1(IcepUrdcFatDTO icepDTO, String callStoreProcedure) throws SQLException
    {
        IcepUrdcFat1StoreProcedure storeProcedure = new IcepUrdcFat1StoreProcedure(jdbcTemplateDYCDB2, callStoreProcedure);

        try {
            return  storeProcedure.buscaIcep(icepDTO);
        }
        catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                   callStoreProcedure + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                   ExtractorUtil.ejecutar(icepDTO));
            ManejadorLogException.getTraceError(dae);
            throw dae;
        }
    }
}
