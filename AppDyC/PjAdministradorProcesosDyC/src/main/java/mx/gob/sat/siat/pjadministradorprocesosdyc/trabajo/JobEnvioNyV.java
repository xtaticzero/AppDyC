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

public class JobEnvioNyV implements Job {
    public JobEnvioNyV() {
        super();
    }
    private static final Logger LOGGER = Logger.getLogger(JobEnvioNyV.class);
    
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("EJECUTA EL PROCESO DE ENVIO DE NYV...");
        
        try {
            dyctAdminProcesosDAO = (DyctAdminProcesosDAO)context.getScheduler().getContext().get("dyctAdminProcesosDAO");
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSEJECUTANDOSE, Constantes.IDPROCESOENVNIO_NYV);
            dyctAdminProcesosDAO.actualizarHorarioEjecucion(Constantes.IDPROCESOENVNIO_NYV);
            Ejecutor.main(new String[]{String.valueOf(Constantes.IDPROCESOENVNIO_NYV)});  
            
            context.setResult(0);
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSACTIVO, Constantes.IDPROCESOENVNIO_NYV);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONCORRECTA, Constantes.IDPROCESOENVNIO_NYV);
            
        } catch (Exception e) {
            dyctAdminProcesosDAO.actualizarStatus(Constantes.STATUSERROREJECUCION, Constantes.IDPROCESOENVNIO_NYV);
            dyctAdminProcesosDAO.actualizarEjecucion(Constantes.EJECUCIONERRONEA, Constantes.IDPROCESOENVNIO_NYV);
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
