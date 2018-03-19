package mx.gob.sat.siat.pjadministradorprocesosdyc.dao.impl;

import java.util.List;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.mapper.DyctAdminProcesosMapper;
import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctAdminProcesosDAO")
public class DyctAdminProcesosDAOImpl implements DyctAdminProcesosDAO {
    
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    public DyctAdminProcesosDAOImpl(){}
    
    private static final Logger LOGGER = Logger.getLogger(DyctAdminProcesosDAOImpl.class);
    
    private static final String ACTUALIZAR_EJECUCION = "UPDATE dycc_segProceso SET ejecucion = ?  WHERE idproceso = ?";
    private static final String ACTUALIZAR_STATUS = "UPDATE dycc_segProceso SET idStatusProceso = ?  WHERE idproceso = ?";
    private static final String ACTUALIZAR_HORAEJECUCION = "UPDATE dycc_segProceso SET horaejecucion = sysdate  WHERE idproceso = ?";
    
    private static final String CONSULTA_MONITOR  = "select c.idAdministrador as idServicio, b.nombre, b.descripcion, a.idStatusProceso as status, a.intentos, a.numMaxIntentos, a.prioridad, a.ejecucion as EJECUCIONCORRECTA,\n" + 
                                                    "c.horarioejecucion as HORARIOPROGRAMADO, c.NOMBREJOB, c.nombreTrigger\n" + 
                                                    "from dycc_segProceso a \n" + 
                                                    "inner join dycc_PROCESOS b on (a.idproceso=b.idProceso) \n" + 
                                                    "inner join dycc_tareas   c on (a.idproceso=c.idProceso) \n" + 
                                                    "where a.idStatusProceso = 1 and b.idProceso=1";
    
    private static final String CONSULTA_PROCESOS = "select c.idAdministrador as idServicio, b.nombre, b.descripcion, a.idStatusProceso as status, a.intentos, a.numMaxIntentos, a.prioridad, a.ejecucion as EJECUCIONCORRECTA,\n" + 
                                                    "c.horarioejecucion as HORARIOPROGRAMADO, c.NOMBREJOB, c.nombreTrigger\n" + 
                                                    "from dycc_segProceso a \n" + 
                                                    "inner join dycc_PROCESOS b on (a.idproceso=b.idProceso) \n" + 
                                                    "inner join dycc_tareas   c on (a.idproceso=c.idProceso) \n" + 
                                                    "where a.idStatusProceso = 1";
    
    /**
     * Consulta todos los procesos que se encuentran dados de alta en base de datos.
     *
     * @return
     */
    @Override
    public List<DyctAdminProcesosDTO> consultar() throws SIATException {
        List<DyctAdminProcesosDTO> listaProcesos = null;
        try {
            listaProcesos = jdbcTemplateDYC.query(CONSULTA_PROCESOS, new DyctAdminProcesosMapper());
            
        } catch (Exception dae) {
            LOGGER.error("consultar(); Causa="+dae.getCause());
            throw new SIATException(dae);
        }
        return listaProcesos;
    }
    
    /**
     * Obtiene los datos del proceso de monitor para ejecutar en un inicio soo este proceso.
     *
     * @return DTO con los datos del proceso de monitor
     */
    @Override
    public DyctAdminProcesosDTO obtenerProcesoMonitor() throws SIATException {
        DyctAdminProcesosDTO procesoMonitor = null;
        try {
            procesoMonitor = (DyctAdminProcesosDTO)jdbcTemplateDYC.queryForObject(CONSULTA_MONITOR, new BeanPropertyRowMapper(DyctAdminProcesosDTO.class));
            
        } catch (Exception dae) {
            LOGGER.error("obtenerProcesoMonitor(); causa="+dae.getCause());
            throw new SIATException(dae);
        }
        
        return procesoMonitor;
    }

    /**
     * <pre>
     * Actualiza el estatus del proceso que se esta ejecutando; cuando un proceso inicia por primera vez, debe de tener
     * un status de uno para poder iniciar. Una vez que se inicia la ejecucion del status se debe de cambiar a 2 el cual
     * es un estatus de ejecucion.<br />
     * <br />
     * Los status quedarian de la siguiente manera:<br />
     *  - (0) Deshabilitado.<br />
     *  - (1) Habilitado (listo para ser ejecutado).<br />
     *  - (2) Ejecutandose.<br />
     *  </pre>
     *
     * @param status
     * @param idProceso
     * @throws SIATException
     */
    @Override
    public void actualizarStatus(Integer status, Integer idProceso) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_STATUS, new Object[] { status, idProceso });
            
        } catch (Exception dae) {
            LOGGER.error("actualizarStatus(); parametros = status:"+status+", idProceso:"+idProceso+"; causa="+dae);
            throw new SIATException(dae);
        }
    }
    
    
    @Override
    public void actualizarHorarioEjecucion(Integer idProceso) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_HORAEJECUCION, new Object[] { idProceso });
            
        } catch (Exception dae) {
            LOGGER.error("actualizarHorarioEjecucion(); parametros = idProceso:"+idProceso+", causa="+dae);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public void actualizarEjecucion(Integer status, Integer idProceso) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_EJECUCION, new Object[] { status, idProceso });
            
        } catch (Exception dae) {
            LOGGER.error("actualizarEjecucion(); parametros = status:"+status+", idProceso="+idProceso);
            throw new SIATException(dae);
        }
    }
}
