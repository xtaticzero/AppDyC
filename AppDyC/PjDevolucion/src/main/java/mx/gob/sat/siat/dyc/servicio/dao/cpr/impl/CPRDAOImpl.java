/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.dao.cpr.impl;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;
import mx.gob.sat.siat.dyc.servicio.dao.cpr.CPRDAO;
import mx.gob.sat.siat.dyc.servicio.dao.cpr.procedures.CPRSpConsultarDatosCPRxStoredProcedure;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author  Israel Chavez
 */
@Repository(value = "cprDao")
public class CPRDAOImpl implements CPRDAO, SQLInformixSIAT {

    private Logger log = Logger.getLogger(CPRDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    public CPRDAOImpl() {
        super();
    }

    /**
     * @param icep
     * @return
     */
    public CPRDTO obtenerCPR(CPRDTO cprAConsultarDTO) {

        CPRSpConsultarDatosCPRxStoredProcedure cprSP;

        cprSP = new CPRSpConsultarDatosCPRxStoredProcedure(jdbcTemplateInformix, STORE_PROCEDURE_BUSCA_CPR);
        
        CPRDTO cprAConsultar = new CPRDTO();
        
        log.info(cprAConsultar);

        try {

            cprAConsultar = cprSP.buscaCPR(cprAConsultarDTO);

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_CPR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(cprAConsultarDTO));

        } catch (SQLException sqle) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_CPR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(cprAConsultarDTO));

        }
        return cprAConsultar;

    }

}

