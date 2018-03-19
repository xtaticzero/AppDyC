package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroDeclaracionBean;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.CuadroDeclaracionDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl.mapper.CuadroDeclaracionMapper;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CuadroDeclaracionDAOImpl implements CuadroDeclaracionDAO {
    private Logger log = Logger.getLogger(CuadroDeclaracionDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List<CuadroDeclaracionBean> buscarCuadroDec(Integer idDetalleaviso) {
        List<CuadroDeclaracionBean> lCuadroDec = new ArrayList<CuadroDeclaracionBean>();
        log.info(lCuadroDec);
        try {
            lCuadroDec =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_CUADRODECLARACION.toString(), new Object[] { idDetalleaviso }, new CuadroDeclaracionMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTA_CUADRODECLARACION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    "IDDETALLEAVISO= " +
                     idDetalleaviso);
        }
        return lCuadroDec;
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
