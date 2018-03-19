package mx.gob.sat.siat.dyc.dao.icep.impl;

import mx.gob.sat.siat.dyc.dao.icep.DyccMovIcepDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccMovIcepDAO")
public class DyccMovIcepDAOImpl implements DyccMovIcepDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccMovIcepDAOImpl.class);

    public boolean existe(long idMovimiento, int idAfectacion) throws SIATException {


        Integer countReg = 0;
        boolean existe = false;

        try {

            countReg =
                    getJdbcTemplateDYC().queryForObject(SQLOracleDyC.DYCC_MOVICEP_EXISTE_REG.toString(), new Object[] { idMovimiento,
                                                                                                             idAfectacion },
                                                        Integer.class);

        } catch (DataAccessException dae) {
            log.error(dae.toString());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_MOVICEP_EXISTE_REG + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idMovimiento " +
                      idMovimiento + " idAfectacion " + idAfectacion);
            throw new SIATException("Error en DAO DyccMovIcepDAOImpl.existe", dae);
        }


        if (countReg > 0) {
            existe = Boolean.TRUE;
        }

        return existe;

    }


    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
