package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DycaSolInconsistDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DycaSolInconsistMapper;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Repository
public class DycaSolInconsistDAOImpl implements DycaSolInconsistDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycaSolInconsistDAOImpl.class.getName());

    @Override
    public void insertar(DycaSolInconsistDTO solInconsist) {
        String sentSQLInsert =
            " INSERT INTO DYCA_SOLINCONSIST (IDINCONSISTENCIA, DESCRIPCION, FECHAREGISTRO, NUMCONTROL) VALUES(?, ?, ?, ?) ";
        try {
            Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_4];
            parametros[ConstantesDyCNumerico.VALOR_0] = solInconsist.getDyccInconsistDTO().getIdInconsistencia();
            parametros[ConstantesDyCNumerico.VALOR_1] = solInconsist.getDescripcion();
            parametros[ConstantesDyCNumerico.VALOR_2] = solInconsist.getFechaRegistro();
            parametros[ConstantesDyCNumerico.VALOR_3] =
                    solInconsist.getDycpSolicitudDTO().getDycpServicioDTO().getNumControl();
            this.jdbcTemplateDYC.update(sentSQLInsert, parametros);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      sentSQLInsert + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(solInconsist));
        }
    }


    @Override
    public List<DycaSolInconsistDTO> selectXNumControl(String numControl) {
        List<DycaSolInconsistDTO> solInconsistL = null;
        String query = "SELECT * FROM DYCA_SOLINCONSIST WHERE NUMCONTROL=?";
        try {
            solInconsistL =
                    this.jdbcTemplateDYC.query(query, new Object[] { numControl }, new DycaSolInconsistMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
        }
        return solInconsistL;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

}
