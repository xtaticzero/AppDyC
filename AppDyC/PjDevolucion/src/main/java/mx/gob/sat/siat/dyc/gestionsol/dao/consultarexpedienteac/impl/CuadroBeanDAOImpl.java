package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.CuadroBeanDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl.mapper.CuadroMapper;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CuadroBeanDAOImpl implements CuadroBeanDAO {

    private Logger log = Logger.getLogger(CuadroBeanDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List<CuadroBean> selecXNumControl(String numControl) {
        List<CuadroBean> lDetCompuesto = new ArrayList<CuadroBean>();
        log.info(lDetCompuesto);
        try {
            lDetCompuesto =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTADYCT_DETALLECOMPUESTO.toString(), new Object[] { numControl },
                                               new CuadroMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTADYCT_DETALLECOMPUESTO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                     numControl);
        }
        return lDetCompuesto;
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
