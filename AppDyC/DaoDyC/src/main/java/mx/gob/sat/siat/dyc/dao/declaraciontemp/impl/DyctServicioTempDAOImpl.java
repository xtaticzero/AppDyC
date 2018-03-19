/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.declaraciontemp.impl;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.DycpAvisoCompTempDAOImpl;
import mx.gob.sat.siat.dyc.dao.declaraciontemp.DyctServicioTempDAO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;
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
public class DyctServicioTempDAOImpl implements DyctServicioTempDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycpAvisoCompTempDAOImpl.class);

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    @Override
    public Integer obtenerFolio() {
        Integer secuencia;
        try {
            secuencia =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENER_SECUENCIA_DYCT_SERVICIOTEMP.toString(), new Object[] { },
                                                   Integer.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.OBTENER_SECUENCIA_DYCT_SERVICIOTEMP);
            throw dae;
        }
        return secuencia;
    }

    @Override
    public void insertar(DyctServicioTempDTO dyctServicioTempDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_DYCT_SERVICIOTEMP.toString(),
                                   new Object[] { dyctServicioTempDTO.getFolioServTemp(),
                                                  dyctServicioTempDTO.getIdTipoServicio() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCT_SERVICIOTEMP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctServicioTempDTO));
            throw dae;
        }
    }

    @Override
    public void actulizarFechaFinTemp(String folioAvisoTemp) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_FECHAFIN_AVISOTEMP.toString(), new Object[] { folioAvisoTemp });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      "update dyct_serviciotemp set fechafin = SYSDATE where folioservtemp = ? " +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + folioAvisoTemp);
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
