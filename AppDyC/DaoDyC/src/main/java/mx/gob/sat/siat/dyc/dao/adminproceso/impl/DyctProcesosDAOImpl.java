package mx.gob.sat.siat.dyc.dao.adminproceso.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.adminproceso.DyctProcesosDAO;
import mx.gob.sat.siat.dyc.dao.adminproceso.impl.mapper.ProcesosMapper;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.Procesos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctProcesosDAOImpl implements DyctProcesosDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DyctProcesosDAOImpl.class);
    
    /**
     * Consulta los procesos que actualmente se encuentran en base de datos.
     *
     * @return
     * @throws SIATException
     */
    @Override
    public List<Procesos> consultar() throws SIATException {
        
        List<Procesos> listaProcesos = null;
        try{
            listaProcesos = jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_PROCESOS.toString(), new ProcesosMapper());
            
        } catch(Exception e){
            LOGGER.error("consultar(), Causa: "+e);
            throw new SIATException(e);
        }
        
        return listaProcesos;
    }
    
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
