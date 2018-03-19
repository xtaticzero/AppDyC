package mx.gob.sat.siat.dyc.dao.documento.impl;


import mx.gob.sat.siat.dyc.dao.documento.DyctExpedienteDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *@author J. Isaac Carbajal Bernal
 */
@Repository
public class DyctExpedienteDAOImpl implements DyctExpedienteDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctExpedienteDAOImpl.class.getName());

    @Override
    public int encontrar(String numControl) {
        DyctExpedienteDTO expediente = null;
        int count = 0;
        log.info(expediente);
        try {
            count =
this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DYCT_EXPEDIENTE_COUNT.toString(), new Object[] { numControl }, Integer.class);
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCT_EXPEDIENTE_COUNT + ConstantesDyC1.TEXTO_3_ERROR_DAO + "NUMCONTROL=" + numControl);
        }
        return count;
    }

    @Override
    public void actualizaManifiestaXNumCtrl(Integer manifest, String numCtrl) throws SIATException{
        String sql = "UPDATE DYCT_EXPEDIENTE SET MANIFIESTAEDOCTA = ? WHERE NUMCONTROL = ?";
        try {
            jdbcTemplateDYC.update(sql, new Object[] { manifest, numCtrl });
        } catch (DataAccessException siatE) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + "NUMCONTROL=" + numCtrl);
            throw new SIATException(siatE);
        }
    }
}
