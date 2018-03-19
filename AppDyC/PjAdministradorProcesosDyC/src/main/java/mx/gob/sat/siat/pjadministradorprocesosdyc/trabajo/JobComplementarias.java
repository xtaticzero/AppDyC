package mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.constantes.Constantes;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Ejecutor;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobComplementarias implements Job {
    public JobComplementarias() {
        super();
    }
    private static final Logger LOGGER = Logger.getLogger(JobComplementarias.class);
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("EJECUTA EL PROCESO DE COMPLEMENTARIAS...");
        
        try {
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)context.getScheduler().getContext().get("dyctAdminProcesosDAO");
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSEJECUTANDOSE, Constantes.IDPROCESOCOMPLEMENTARIAS);
            dyctAdminProcesosDAO.actualizarHorarioEjecucion(Constantes.IDPROCESOCOMPLEMENTARIAS);
            Ejecutor.main(new String[]{String.valueOf(Constantes.IDPROCESOCOMPLEMENTARIAS)});    
            
            context.setResult(0);
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSACTIVO, Constantes.IDPROCESOCOMPLEMENTARIAS);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONCORRECTA, Constantes.IDPROCESOCOMPLEMENTARIAS);
            
        } catch (Exception e) {
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSERROREJECUCION, Constantes.IDPROCESOCOMPLEMENTARIAS);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONERRONEA, Constantes.IDPROCESOCOMPLEMENTARIAS);
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
