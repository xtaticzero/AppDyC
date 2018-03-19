package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import mx.gob.sat.siat.dyc.dao.compensacion.DyctLiquidacionDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DyctLiquidacionMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctLiquidacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctLiquidacionDAOImpl implements DyctLiquidacionDAO {
    private static final Logger LOG = Logger.getLogger(DyctLiquidacionDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctLiquidacionDTO liquidacion) throws SIATException {
        String sentSQLInsert =
            " INSERT INTO DYCT_LIQUIDACION (NUMCONTROL, IMPORTEACTUALIZA, IMPORTERECARGO, IMPORTEMULTA, NUMDOCDETERMINANTE, MESINICIOINPC," +
            " MESINICIOTASAREC, FUNDAMENTACION, ANIOINICIALINPC, MESFINALINPC, ANIOFINALINPC, ANIOINICIALTASAREC, MESFINALTASAREC, ANIOFINALTASAREC,IMPORTEIMPRO)" +
            " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_15];
            parametros[ConstantesDyCNumerico.VALOR_0] =
                    liquidacion.getDyctResolCompDTO().getDycpCompensacionDTO().getDycpServicioDTO().getNumControl();
            parametros[ConstantesDyCNumerico.VALOR_1] = liquidacion.getImporteActualizar();
            parametros[ConstantesDyCNumerico.VALOR_2] = liquidacion.getImporteRecargo();
            parametros[ConstantesDyCNumerico.VALOR_3] = liquidacion.getImporteMulta();
            parametros[ConstantesDyCNumerico.VALOR_4] = liquidacion.getNumDocDeterminante();
            parametros[ConstantesDyCNumerico.VALOR_5] = liquidacion.getMesInicioInpc();
            parametros[ConstantesDyCNumerico.VALOR_6] = liquidacion.getMesInicioTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_7] = liquidacion.getFundamentacion();
            parametros[ConstantesDyCNumerico.VALOR_8] = liquidacion.getAnioInicialInpc();
            parametros[ConstantesDyCNumerico.VALOR_9] = liquidacion.getMesFinalInpc();
            parametros[ConstantesDyCNumerico.VALOR_10] = liquidacion.getAnioFinalInpc();
            parametros[ConstantesDyCNumerico.VALOR_11] = liquidacion.getAnioInicialTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_12] = liquidacion.getMesInicioTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_13] = liquidacion.getAnioFinalTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_14] = liquidacion.getImporteImprocedente();
            this.jdbcTemplateDYC.update(sentSQLInsert, parametros);
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      sentSQLInsert + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(liquidacion) +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae);
            throw new SIATException(dae);
        }
    }

    @Override
    public DyctLiquidacionDTO encontrar(DyctResolCompDTO resolComp) {
        try {
            String query = "SELECT * FROM DYCT_LIQUIDACION WHERE NUMCONTROL = ? ";
            DyctLiquidacionMapper mapper = new DyctLiquidacionMapper();
            mapper.setResolComp(resolComp);
            return jdbcTemplateDYC.queryForObject(query,
                                                  new Object[] { resolComp.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl() },
                                                  mapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            LOG.info("NO se encontro Liquidacion para la resolComp ->" +
                     resolComp.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl() + "<-" +
                     exEmpty.getMessage());
            return null;
        }
    }

    @Override
    public void actualizar(DyctLiquidacionDTO liquidacion) throws SIATException {
        try {
            String sentSQLActualiza =
                "UPDATE DYCT_LIQUIDACION SET NUMCONTROL = ?, IMPORTEACTUALIZA = ?, IMPORTERECARGO = ?, " +
                "IMPORTEMULTA = ?, NUMDOCDETERMINANTE = ?, MESINICIOINPC = ?, MESINICIOTASAREC = ?, FUNDAMENTACION = ?, \n" +
                " ANIOINICIALINPC = ?, MESFINALINPC = ?, ANIOFINALINPC = ?, ANIOINICIALTASAREC = ?, MESFINALTASAREC = ?, \n" +
                " ANIOFINALTASAREC = ?, IMPORTEIMPRO = ? WHERE NUMCONTROL = ?";

            Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_16];
            parametros[ConstantesDyCNumerico.VALOR_0] =
                    liquidacion.getDyctResolCompDTO().getDycpCompensacionDTO().getDycpServicioDTO().getNumControl();
            parametros[ConstantesDyCNumerico.VALOR_1] = liquidacion.getImporteActualizar();
            parametros[ConstantesDyCNumerico.VALOR_2] = liquidacion.getImporteRecargo();
            parametros[ConstantesDyCNumerico.VALOR_3] = liquidacion.getImporteMulta();
            parametros[ConstantesDyCNumerico.VALOR_4] = liquidacion.getNumDocDeterminante();
            parametros[ConstantesDyCNumerico.VALOR_5] = liquidacion.getMesInicioInpc();
            parametros[ConstantesDyCNumerico.VALOR_6] = liquidacion.getMesInicioTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_7] = liquidacion.getFundamentacion();
            parametros[ConstantesDyCNumerico.VALOR_8] = liquidacion.getAnioInicialInpc();
            parametros[ConstantesDyCNumerico.VALOR_9] = liquidacion.getMesFinalInpc();
            parametros[ConstantesDyCNumerico.VALOR_10] = liquidacion.getAnioFinalInpc();
            parametros[ConstantesDyCNumerico.VALOR_11] = liquidacion.getAnioInicialTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_12] = liquidacion.getMesFinalTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_13] = liquidacion.getAnioFinalTasaRec();
            parametros[ConstantesDyCNumerico.VALOR_14] = liquidacion.getImporteImprocedente();
            parametros[ConstantesDyCNumerico.VALOR_15] =
                    liquidacion.getDyctResolCompDTO().getDycpCompensacionDTO().getDycpServicioDTO().getNumControl();

            jdbcTemplateDYC.update(sentSQLActualiza, parametros);

        } catch (Exception se) {
            throw new SIATException(se);
        }
    }

}
