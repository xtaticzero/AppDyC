package mx.gob.sat.siat.dyc.dao.resolucion.impl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.DyctResolCompMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctResolCompDAOImpl implements DyctResolCompDAO {
    private Logger logger = Logger.getLogger(DyctResolCompDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctResolCompDTO resolComp) throws SIATException {
        String sentencia =
            " INSERT INTO DYCT_RESOLCOMP (NUMCONTROL, IDACCIONSEG, FECHARESOLUCION, OBSERVACIONES, IDESTRESOL, IDTIPORESOL) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            int[] tiposCampos = new int[ConstantesDyCNumerico.VALOR_6];
            tiposCampos[ConstantesDyCNumerico.VALOR_0] = Types.VARCHAR;
            tiposCampos[ConstantesDyCNumerico.VALOR_1] = Types.INTEGER;
            tiposCampos[ConstantesDyCNumerico.VALOR_2] = Types.DATE;
            tiposCampos[ConstantesDyCNumerico.VALOR_3] = Types.VARCHAR;
            tiposCampos[ConstantesDyCNumerico.VALOR_4] = Types.INTEGER;
            tiposCampos[ConstantesDyCNumerico.VALOR_5] = Types.INTEGER;

            jdbcTemplateDYC.update(sentencia,
                                   new Object[] { resolComp.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl(),
                                                  resolComp.getDyccAccionSegDTO().getIdAccionSeg(),
                                                  resolComp.getFechaResolucion(), resolComp.getObservaciones(),
                                                  resolComp.getDyccEstResolDTO().getIdEstResol(),
                                                  resolComp.getDyccTipoResolDTO().getIdTipoResol() }, tiposCampos);
        } catch (DataAccessException dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         sentencia + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(resolComp) +
                         ConstantesDyC1.TEXTO_8_CAUSAS + dae);
            throw new SIATException(dae);
        }
    }

    @Override
    public void actualizarEstado(DyctResolCompDTO resolComp) {
        String sentencia = " UPDATE DYCT_RESOLCOMP SET IDESTRESOL = ? WHERE NUMCONTROL = ?";
        jdbcTemplateDYC.update(sentencia,
                               new Object[] { resolComp.getDyccEstResolDTO().getIdEstResol(), resolComp.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl() });
    }

    @Override
    public void actualizar(DyctResolCompDTO resolComp) {
        String camposActualizar = "";

        boolean comma = false;
        List<Object> params = new ArrayList<Object>();

        if (resolComp.getDyccAccionSegDTO() != null) {
            camposActualizar += " IDACCIONSEG = ?";
            params.add(resolComp.getDyccAccionSegDTO().getIdAccionSeg());
            comma = Boolean.TRUE;
        }

        if (resolComp.getFechaResolucion() != null) {
            if (comma) {
                camposActualizar += ",";
            }
            camposActualizar += " FECHARESOLUCION = ?";
            params.add(resolComp.getFechaResolucion());
            comma = Boolean.TRUE;
        }

        if (resolComp.getObservaciones() != null) {
            if (comma) {
                camposActualizar += ",";
            }
            camposActualizar += " OBSERVACIONES = ?";
            params.add(resolComp.getObservaciones());
            comma = Boolean.TRUE;
        }

        if (resolComp.getDyccEstResolDTO() != null) {
            if (comma) {
                camposActualizar += ",";
            }
            camposActualizar += " IDESTRESOL = ?";
            params.add(resolComp.getDyccEstResolDTO().getIdEstResol());
        }

        params.add(resolComp.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl());

        String sentencia = " UPDATE DYCT_RESOLCOMP SET " + camposActualizar + " WHERE NUMCONTROL = ?";
        Object[] p1 = new Object[params.size()];
        int i = 0;
        for (Object o : params) {
            p1[i] = o;
            i++;
        }

        jdbcTemplateDYC.update(sentencia, p1);
    }

    /*
     * Soporta el NO encontrar ninguna resolucion devuelve null en ese caso
     */

    @Override
    public DyctResolCompDTO encontrar(DycpCompensacionDTO compensacion) throws SIATException {
        try {
            String query = "SELECT * FROM DYCT_RESOLCOMP WHERE NUMCONTROL = ?";
            DyctResolCompMapper mapper = new DyctResolCompMapper();
            mapper.setCompensacion(compensacion);

            String numControl = compensacion.getDycpServicioDTO().getNumControl();
            if(numControl == null){
                numControl = compensacion.getNumControl();
            }

            return jdbcTemplateDYC.queryForObject(query,
                                                  new Object[] { numControl },
                                                  mapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            logger.info("NO se encontro ResolComp para la compensacion ->" +
                        compensacion.getDycpServicioDTO().getNumControl() + "<-" + exEmpty.getMessage());
            return null;
        } catch (Exception dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         "CONSULTA_DYCP_COMPENSACION" + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numcontrol" + "; causa=" +
                         dae.getCause());
            throw new SIATException(dae);
        }
    }

    @Override
    public void insertResolCompensacionSimulador(String numControl) throws SIATException {
        String sql = "INSERT INTO DYCT_RESOLCOMP\n" +
            "(NUMCONTROL, IDACCIONSEG, FECHARESOLUCION\n" +
            ",OBSERVACIONES, IDESTRESOL, IDTIPORESOL)\n" +
            "VALUES \n" +
            "(?, 1, SYSDATE,' ', 2, 8)";

        try {
            jdbcTemplateDYC.update(sql, new Object[] { numControl });
        } catch (DataAccessException dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.EMITIRRESOLUCION_BUSCAROTRAINFOREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                         " numControl " + numControl);
            throw new SIATException(dae);
        }
    }

    @Override
    public void actualizarTipoResol(DyctResolCompDTO resolComp) {
        String sentencia = " UPDATE DYCT_RESOLCOMP SET IDTIPORESOL = ? WHERE NUMCONTROL = ?";
        jdbcTemplateDYC.update(sentencia,
                               new Object[] { resolComp.getDyccTipoResolDTO().getIdTipoResol(), resolComp.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl() });
    }
}
