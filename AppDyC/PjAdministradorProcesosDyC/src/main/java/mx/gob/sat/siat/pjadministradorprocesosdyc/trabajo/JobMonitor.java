package mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Comun;

import org.apache.log4j.Logger;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;


public class JobMonitor implements Job {
    private static final Logger LOGGER  = Logger.getLogger(JobMonitor.class); 
    
    private static final String DEFAULT                       = "DEFAULT";
    private static final String MONITORJOB                    = "monitorJob";
    
    private static final int STATUSACTIVO = 1;
    
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
            
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        try {
            LOGGER.info("EJECUTA EL PROCESO DE MONITOR...");
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)context.getScheduler().getContext().get("dyctAdminProcesosDAO");
            List<DyctAdminProcesosDTO> listaProcesos = dyctAdminProcesosDAO.consultar();
            crearProcesosDeBaseDeDatos(context.getScheduler(), listaProcesos);
            recalendarizar(context.getScheduler(), listaProcesos);
            
        } catch (Exception be) {
            LOGGER.error("execute(); causa="+be.getCause());
            throw new JobExecutionException(be);
        }
    }
    
    /**
     * Deshabilita los lanzadores actuales
     *
     * @param planificador
     * @param listaProcesos
     */
    private void recalendarizar(Scheduler planificador, List<DyctAdminProcesosDTO> listaProcesos) {        
        JobDetail   trabajo   = null;
        Iterator    it        = null;
        CronTrigger lanzador  = null;
        DyctAdminProcesosDTO objetoProceso = null;
        
        try {
            it = listaProcesos.iterator();
            
            while(it.hasNext()) {
                lanzador      = new CronTrigger();
                objetoProceso = (DyctAdminProcesosDTO)it.next();
                trabajo       = planificador.getJobDetail(objetoProceso.getNombreJob(),DEFAULT);
                
                if(!(objetoProceso.getNombreJob().equals(MONITORJOB)) && planificador.unscheduleJob(objetoProceso.getNombreTrigger(), DEFAULT) && (objetoProceso.getStatus()==STATUSACTIVO)) {                    
                    lanzador.setName(objetoProceso.getNombreTrigger());
                    lanzador.setCronExpression(objetoProceso.getHorarioProgramado());
                    lanzador.setJobName(objetoProceso.getNombreJob());                    
                    
                    planificador.scheduleJob(trabajo, lanzador);
                    planificador.start();    
                }
            }
                        
        } catch (Exception pe) {
            LOGGER.error("recalendarizar(); causa="+pe);
        }
    }
    
    /**
     * Crea a partir de la base de datos, los procesos dentro del planificador, este, solo tomara en cuenta los status 
     * que no esten declarados actualmente en el planificador y que ademas esten en status uno (activo) para poder 
     * agregarlos.
     *
     * @param planificador
     * @throws Exception
     */
    private void crearProcesosDeBaseDeDatos(Scheduler planificador, List<DyctAdminProcesosDTO> listaProcesos) throws SIATException {
        
        CronTrigger lanzador = null;
        Iterator    it       = null;
        JobDetail   trabajo  = null;
        DyctAdminProcesosDTO objetoProceso = null;
        
        try {        
            List<String> listaProcesosActivos  = Arrays.asList(planificador.getJobNames(DEFAULT));
            it = listaProcesos.iterator();

            while (it.hasNext()) {
                objetoProceso = (DyctAdminProcesosDTO)it.next();
                if (!listaProcesosActivos.contains(objetoProceso.getNombreJob())) {
                    trabajo = new JobDetail();
                    trabajo.setName(objetoProceso.getNombreJob());

                    Comun.asignarClaseAJobs(objetoProceso, trabajo);

                    lanzador = new CronTrigger();
                    lanzador.setName(objetoProceso.getNombreTrigger());
                    lanzador.setCronExpression(objetoProceso.getHorarioProgramado());

                    planificador.scheduleJob(trabajo, lanzador);
                    planificador.start();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Hubo un error al crear los procesos a partir de la base de datos. \n:"+
                         "Nombre del proceso ejecutado: "+objetoProceso.getNombre()+
                         "\nError: "+e);  
            throw new SIATException(e);
        } 
    }
    
    public void setDyctAdminProcesosDAO(DyctAdminProcesosDAO dyctAdminProcesosDAO) {
        this.dyctAdminProcesosDAO = dyctAdminProcesosDAO;
    }

    public DyctAdminProcesosDAO getDyctAdminProcesosDAO() {
        return dyctAdminProcesosDAO;
    }
}
