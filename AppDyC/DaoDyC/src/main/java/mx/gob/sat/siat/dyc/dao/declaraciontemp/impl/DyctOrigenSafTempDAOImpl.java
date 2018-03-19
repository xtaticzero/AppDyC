/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.declaraciontemp.impl;

import mx.gob.sat.siat.dyc.dao.declaraciontemp.DyctOrigenSafTempDAO;
import mx.gob.sat.siat.dyc.domain.compensacion.DyctOrigenSafTempDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author  Alfredo Ramirez
 * @since   16/07/2014
 */
@Repository
public class DyctOrigenSafTempDAOImpl implements DyctOrigenSafTempDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctOrigenSafTempDAOImpl.class);

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    @Override
    public void insertar(DyctOrigenSafTempDTO dyctOrigenSafTempDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_DYCT_ORIGENSAFTEMP.toString(),
                                   new Object[] { dyctOrigenSafTempDTO.getDycpCompTempDTO().getDyctServicioTempDTO().getFolioServTemp(),
                                                  dyctOrigenSafTempDTO.getIdPeriodo(),
                                                  dyctOrigenSafTempDTO.getIdEjercicio(),
                                                  dyctOrigenSafTempDTO.getSaldoAplicar(),
                                                  dyctOrigenSafTempDTO.getMontoSaldoAfavor(),
                                                  dyctOrigenSafTempDTO.getRemanenteHistorico(),
                                                  dyctOrigenSafTempDTO.getFechaCausacion(),
                                                  dyctOrigenSafTempDTO.getRemanenteAct(),                                                  
                                                  dyctOrigenSafTempDTO.getIdOrigenSsldo(),
                                                  dyctOrigenSafTempDTO.getIdTipoTramite(),
                                                  dyctOrigenSafTempDTO.getImporteSolicitado(),
                                                  dyctOrigenSafTempDTO.getImpCompensadoDec(),
                                                  dyctOrigenSafTempDTO.getImpActualizado(),
                                                  dyctOrigenSafTempDTO.getImpRemanente(),
                                                  dyctOrigenSafTempDTO.getDiotNumOperacion(),
                                                  dyctOrigenSafTempDTO.getDiotFechaPresenta(),
                                                  dyctOrigenSafTempDTO.getNumOperacionDec(),
                                                  dyctOrigenSafTempDTO.getTipoSaldo(),
                                                  dyctOrigenSafTempDTO.getEspSuborigen(),
                                                  dyctOrigenSafTempDTO.getPresentoDiot(),
                                                  dyctOrigenSafTempDTO.getNumControlRem(),
                                                  dyctOrigenSafTempDTO.getEsRemanente() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCT_ORIGENSAFTEMP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctOrigenSafTempDTO));
            throw dae;
        }
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
}
