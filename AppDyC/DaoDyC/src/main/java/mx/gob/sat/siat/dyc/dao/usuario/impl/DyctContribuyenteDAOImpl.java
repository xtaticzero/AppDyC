package mx.gob.sat.siat.dyc.dao.usuario.impl;

import mx.gob.sat.siat.dyc.dao.usuario.DyctContribuyenteDAO;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.ContribuyenteMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Repository
public class DyctContribuyenteDAOImpl implements DyctContribuyenteDAO {
    private Logger log = Logger.getLogger(DyctContribuyenteDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DyctContribuyenteDTO encontrar(String numControl) {
        DyctContribuyenteDTO contribuyente = null;
        try {
            contribuyente =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DYCT_CONTRIBUYENTE.toString(), new Object[] { numControl },
                                                        new ContribuyenteMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCT_CONTRIBUYENTE + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
        }

        return contribuyente;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
