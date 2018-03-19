package mx.gob.sat.siat.dyc.dao.adminproceso.impl;

import mx.gob.sat.siat.dyc.dao.adminproceso.DyctAdminProcesosDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctAdminProcesosDAO")
public class DyctAdminProcesosDAOImpl implements DyctAdminProcesosDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DyctSegProcesoDAOImpl.class);
    
    /**
     * Arroja la ultima fecha de ejecucion del administrador de procesos con formato:
     * DD/MM/YYYY HH24:MI
     *
     * @return fecha con formato:DD/MM/YYYY HH24:MI
     * @throws SIATException
     */
    @Override
    public String consultarFechaEjecucion() throws SIATException {
        String ultimaFechaEjecucion = "";
        try {
            ultimaFechaEjecucion = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ULTIMAFECHAEJECUCION.toString(), String.class);
        } catch (DataAccessException dae) {
            LOGGER.error("consultarFechaEjecucion(); "+dae);
        }
        return ultimaFechaEjecucion;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
