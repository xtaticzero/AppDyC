package mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo;


import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.constantes.Constantes;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Ejecutor;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobNyV implements Job {
    private static final Logger LOGGER = Logger.getLogger(JobNyV.class); 
    
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {  
        LOGGER.info("JAHO - EJECUTA EL PROCESO DE NOTIFICACIONES Y VERIFICACIONES...");
        
        try {
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)jobExecutionContext.getScheduler().getContext().get("dyctAdminProcesosDAO");      
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSEJECUTANDOSE, Constantes.IDPROCESO_RETRO_NYV);
            dyctAdminProcesosDAO.actualizarHorarioEjecucion(Constantes.IDPROCESO_RETRO_NYV);
            Ejecutor.main(new String[]{String.valueOf(Constantes.IDPROCESO_RETRO_NYV)});                    
            
            jobExecutionContext.setResult(0);
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSACTIVO, Constantes.IDPROCESO_RETRO_NYV);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONCORRECTA, Constantes.IDPROCESO_RETRO_NYV);
            
        } catch (Exception e) {
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSERROREJECUCION, Constantes.IDPROCESO_RETRO_NYV);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONERRONEA, Constantes.IDPROCESO_RETRO_NYV);
            LOGGER.error("Descripcion del error " + e.getMessage());
            throw new JobExecutionException(e);
        }
    }

    public void setDyctAdminProcesosDAO(DyctAdminProcesosDAO dyctAdminProcesosDAO) {
        this.dyctAdminProcesosDAO = dyctAdminProcesosDAO;
    }

    public DyctAdminProcesosDAO getDyctAdminProcesosDAO() {
        return dyctAdminProcesosDAO;
    }
}
