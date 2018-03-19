package mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.constantes.Constantes;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Ejecutor;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobAdminDocDYC implements Job {
    private static final Logger LOGGER = Logger.getLogger(JobAdminDocDYC.class); 
    
    
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("EJECUTA EL PROCESO DE ADMINISTRACION DE DOCUMENTOS...");
        
        try {
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)context.getScheduler().getContext().get("dyctAdminProcesosDAO");
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSEJECUTANDOSE, Constantes.IDPROCESOADMINDOCDYC);
            dyctAdminProcesosDAO.actualizarHorarioEjecucion(Constantes.IDPROCESOADMINDOCDYC);
            Ejecutor.main(new String[]{String.valueOf(Constantes.IDPROCESOADMINDOCDYC)});     
            
            context.setResult(0);
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSACTIVO, Constantes.IDPROCESOADMINDOCDYC);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONCORRECTA, Constantes.IDPROCESOADMINDOCDYC);
            
        } catch (Exception e) {
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSERROREJECUCION, Constantes.IDPROCESOADMINDOCDYC);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONERRONEA, Constantes.IDPROCESOADMINDOCDYC);
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
