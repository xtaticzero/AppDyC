/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import mx.gob.sat.siat.dyc.dao.compensacion.DycpAvisoCompTempDAO;
import mx.gob.sat.siat.dyc.domain.compensacion.DycpAvisoCompTempDTO;
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
public class DycpAvisoCompTempDAOImpl implements DycpAvisoCompTempDAO{

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
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENER_SECUENCIA_DYCP_AVISOCOMPTEMP.toString(), new Object[] { },
                                                   Integer.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.OBTENER_SECUENCIA_DYCP_AVISOCOMPTEMP);
            throw dae;
        }
        return secuencia;
    }

    @Override
    public void insertar(DycpAvisoCompTempDTO dycpAvisoCompTempDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_DYCP_AVISOCOMPTEMP.toString(),
                                   new Object[] { dycpAvisoCompTempDTO.getFolioAvisoTemp(),
                                                  dycpAvisoCompTempDTO.getFolioAvisoNormal(),
                                                  dycpAvisoCompTempDTO.getTipoAviso() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCP_AVISOCOMPTEMP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycpAvisoCompTempDTO));
            throw dae;
        }
    }
    
   @Override
   public void update(DycpAvisoCompTempDTO dycpAvisoCompTempDTO) {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.UPDATE_DYCP_AVISOCOMTEMP.toString(),
                                   new Object[] { dycpAvisoCompTempDTO.getFolioAvisoTemp(),
                                                  dycpAvisoCompTempDTO.getFolioAvisoNormal(),
                                                  dycpAvisoCompTempDTO.getTipoAviso() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.UPDATE_DYCP_AVISOCOMTEMP+
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycpAvisoCompTempDTO));
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
