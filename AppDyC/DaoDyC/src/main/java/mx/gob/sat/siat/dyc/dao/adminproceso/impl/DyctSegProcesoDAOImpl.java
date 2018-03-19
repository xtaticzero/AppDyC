package mx.gob.sat.siat.dyc.dao.adminproceso.impl;

import mx.gob.sat.siat.dyc.dao.adminproceso.DyctSegProcesoDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.Procesos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctSegProcesoDAO")
public class DyctSegProcesoDAOImpl implements DyctSegProcesoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DyctSegProcesoDAOImpl.class);

    /**
     * Actualiza el estatus del proceso de activo a pausado y viceverza...
     *
     * @param proceso
     * @throws SIATException
     */
    @Override
    public void actualizarStatusProceso(Procesos proceso) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_STATUSPROCESO.toString(),
                                   new Object[] { proceso.getDyctSegProcesoDTO().getDyccStatusProcesoDTO().getIdStatusProceso(),
                                                  proceso.getDyctSegProcesoDTO().getDyctProcesosDTO().getIdProceso() });
        } catch (Exception e) {
            LOGGER.error("actualizarStatusProceso(); causa: "+e);
            throw new SIATException(e);
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
