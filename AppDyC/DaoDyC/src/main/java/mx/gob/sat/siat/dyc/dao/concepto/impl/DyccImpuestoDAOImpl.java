package mx.gob.sat.siat.dyc.dao.concepto.impl;


import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.concepto.DyccImpuestoDAO;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ImpuestoMapper;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccImpuestoDAOImpl implements DyccImpuestoDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private final Logger log = Logger.getLogger(DyccImpuestoDAOImpl.class.getName());

    @Override
    public DyccImpuestoDTO encontrar(Integer id)
    {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTADYCC_IMPUESTO.toString(), new Object[] { id }, new ImpuestoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTADYCC_IMPUESTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
            return null;
        }
    }

    @Override
    public DyccImpuestoDTO seleccionarXconcepto(DyccConceptoDTO concepto) {
        DyccImpuestoDTO impuesto = null;
        log.debug(impuesto);
        try {
            impuesto = jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECCIONAR_DYCC_IMPUESTO_X_CONCEPTO.toString(), new Object[] { concepto.getIdConcepto() },
                                                             new ImpuestoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.SELECCIONAR_DYCC_IMPUESTO_X_CONCEPTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(concepto));
        }
        return impuesto;
    }

    /**
     * @param idTipoTramite
     * @return
     */
    @Override
    public DyccImpuestoDTO obtieneImpuestoPorTramite(long idTipoTramite) {
        DyccImpuestoDTO impuesto = null;
        log.debug(impuesto);
        try {
            impuesto =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEIMPUESTOPORTRAMITE.toString(), new Object[] { idTipoTramite },
                                                   new ImpuestoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEIMPUESTOPORTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
            throw dae;
        }
        return impuesto;
    }

    @Override
    public List<DyccImpuestoDTO> obtieneImpuestos() {
        List<DyccImpuestoDTO> listaImpuestos = new ArrayList<DyccImpuestoDTO>();
        try {
            String query = "SELECT IDIMPUESTO, DESCRIPCION, FECHAINICIO, FECHAFIN FROM DYCC_IMPUESTO ORDER BY DESCRIPCION";
            listaImpuestos = jdbcTemplateDYC.query(query, new ImpuestoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEIMPUESTOS);
        }
        return listaImpuestos;
    }

    @Override
    public List<DyccImpuestoDTO> obtieneImpuestos(int idImpuesto) {
        List<DyccImpuestoDTO> listaImpuestos = new ArrayList<DyccImpuestoDTO>();
        try {
            listaImpuestos =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEIMPUESTOS.toString().concat(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEIMPUESTOSWHEREID),
                                          new Object[] { idImpuesto }, new ImpuestoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_4_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEIMPUESTOS);
        }
        return listaImpuestos;
    }

}
