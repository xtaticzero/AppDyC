/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.servicio.dao.diot.impl;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.servicio.dao.diot.DiotDAO;
import mx.gob.sat.siat.dyc.servicio.dao.diot.procedures.ConsultarDiotStoreProcedure;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author  Israel Chavez
 */
@Repository(value = "diotDAO")
public class DiotDAOImpl implements DiotDAO, SQLInformixSIAT {

    private Logger log = Logger.getLogger(DiotDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    public DiotDAOImpl() {
        super();
    }

    /**
     * @param icep
     * @return
     */
    public DiotDTO obtieneDiotSP(DiotDTO diotDTO) throws SQLException {

        ConsultarDiotStoreProcedure diotSP;
        DiotDTO diot = new DiotDTO();
        log.info(diot);

        diotSP = new ConsultarDiotStoreProcedure(jdbcTemplateInformix, STORE_PROCEDURE_BUSCA_DIOT);

        try {

            diot = diotSP.buscaDiot(diotDTO);

        } catch (DataAccessException dae) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesErrorTextos.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_DIOT + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(diotDTO));
            throw dae;
        } catch (SQLException sqle) {
            log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesErrorTextos.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_DIOT + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(diotDTO));
            throw sqle;

        }
        return diot;

    }
}

