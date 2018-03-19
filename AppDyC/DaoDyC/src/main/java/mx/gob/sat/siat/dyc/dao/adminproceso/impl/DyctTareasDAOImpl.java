package mx.gob.sat.siat.dyc.dao.adminproceso.impl;

import mx.gob.sat.siat.dyc.dao.adminproceso.DyctTareasDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.Procesos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctTareasDAO")
public class DyctTareasDAOImpl implements DyctTareasDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DyctTareasDAOImpl.class);
    
    /**
     * Actualiza la el periodo de ejecucucion de un proceso dado.
     *
     * @param proceso
     */
    @Override
    public void guardarCron(Procesos proceso) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_HORARIO_EJEC_PROCESO.toString(), new Object[] {proceso.getDyctTareasDTO().getHorarioEjecucion(), proceso.getDyctTareasDTO().getDyctProcesosDTO().getIdProceso()});
        } catch (Exception e) {
            LOGGER.error("guardarCron(); Error al actualizar el periodo de ejecucion del proceso. "+e);
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
