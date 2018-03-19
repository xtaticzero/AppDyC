package mx.gob.sat.siat.pjadministradorprocesosdyc.service.impl;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.service.CrearProcesosService;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Comun;

import org.apache.log4j.Logger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service(value = "crearProcesosService")
public class CrearProcesosServiceImpl implements CrearProcesosService {
    
    private static final Logger LOGGER = Logger.getLogger(CrearProcesosServiceImpl.class);
    
    @Autowired
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    /**
     * <pre>
     * Crea los distintos procesos que conformaran en administrador de procesos.
     * - El proceso principal que se ejecutara a un tiempo determinado y que sera el encargado de ejecutar a los demas procesos.
     * - Los procesos que estan registrados en base de datos y que posteriormente un procesos administrador, dira si se ejecuta o no.
     * </pre>
     *
     * @throws SIATException
     */
    @Override
    public void crearProcesos() throws SIATException {
        try {
            Scheduler planificador = (Scheduler)applicationContext.getBean("schedulerFactory");
            ejecutarProcesos(planificador);
            
        } catch (Exception e) {
            LOGGER.error("crearProcesos(); causa="+e.getCause());
            throw new SIATException(e);
        }
    }
    
    /**
     * Lee cada uno de los procesos que se encuentran alojados en la base de datos.
     *
     * @param planificador
     * @throws SIATException
     */
    @Override
    public void ejecutarProcesos(Scheduler planificador) throws SIATException {
        
        CronTrigger lanzador = null;
        JobDetail   trabajo  = null;
        
        DyctAdminProcesosDTO objetoMonitor = dyctAdminProcesosDAO.obtenerProcesoMonitor();
        
        try {
            trabajo = new JobDetail();
            trabajo.setName(objetoMonitor.getNombreJob());
            
            Comun.asignarClaseAJobs(objetoMonitor, trabajo);
            
            lanzador = new CronTrigger();
            lanzador.setName(objetoMonitor.getNombreTrigger());
            lanzador.setCronExpression(objetoMonitor.getHorarioProgramado());
            
            planificador.scheduleJob(trabajo, lanzador);
            planificador.start();    
            
        } catch (Exception e) {
            LOGGER.error("crearProcesos(); causa="+e.getCause());
            throw new SIATException(e);
        }
    }

    public void setDyctAdminProcesosDAO(DyctAdminProcesosDAO dyctAdminProcesosDAO) {
        this.dyctAdminProcesosDAO = dyctAdminProcesosDAO;
    }

    public DyctAdminProcesosDAO getDyctAdminProcesosDAO() {
        return dyctAdminProcesosDAO;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
