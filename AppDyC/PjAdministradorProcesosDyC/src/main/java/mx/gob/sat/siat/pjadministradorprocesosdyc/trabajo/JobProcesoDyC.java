package mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.constantes.Constantes;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Ejecutor;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobProcesoDyC implements Job {
    private static final Logger LOGGER = Logger.getLogger(JobProcesoDyC.class); 
    
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("EJECUTA EL PROCESO DYC...");
                
        try {
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)jobExecutionContext.getScheduler().getContext().get("dyctAdminProcesosDAO");
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSEJECUTANDOSE, Constantes.IDPROCESODYC);
            dyctAdminProcesosDAO.actualizarHorarioEjecucion(Constantes.IDPROCESODYC);
            Ejecutor.main(new String[]{String.valueOf(Constantes.IDPROCESODYC)});    
                
            jobExecutionContext.setResult(0);
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSACTIVO, Constantes.IDPROCESODYC);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONCORRECTA, Constantes.IDPROCESODYC);
        
        } catch (Exception e) {
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSERROREJECUCION, Constantes.IDPROCESODYC);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONERRONEA, Constantes.IDPROCESODYC);
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
