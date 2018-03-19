package mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo;


import java.io.File;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.constantes.Constantes;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.Ejecutor;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso.ParametrosEjecucion;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobValidarReq implements Job {
    private static final Logger LOGGER = Logger.getLogger(JobValidarReq.class); 
    
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {  
        LOGGER.info("EJECUTA EL PROCESO DE VALIDAR REQUERIMIENTO...");
        
        try {
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)jobExecutionContext.getScheduler().getContext().get("dyctAdminProcesosDAO");
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSEJECUTANDOSE, Constantes.IDPROCESOVALIDAREQ);
            dyctAdminProcesosDAO.actualizarHorarioEjecucion(Constantes.IDPROCESOVALIDAREQ);
            Ejecutor.main(new String[]{String.valueOf(Constantes.IDPROCESOVALIDAREQ)});   
                
            jobExecutionContext.setResult(0);
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSACTIVO, Constantes.IDPROCESOVALIDAREQ);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONCORRECTA, Constantes.IDPROCESOVALIDAREQ);
        
        } catch (Exception e) {
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSERROREJECUCION, Constantes.IDPROCESOVALIDAREQ);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONERRONEA, Constantes.IDPROCESOVALIDAREQ);
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

