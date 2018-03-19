package mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobAdminDocDYC;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobComplementarias;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobEnvioNyV;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobExtractorAnexos;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobMonitor;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobNyV;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobProcesoDyC;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobRetroTESOFE;
import mx.gob.sat.siat.pjadministradorprocesosdyc.trabajo.JobValidarReq;

import org.quartz.JobDetail;


public final class Comun {
    private Comun() {
        super();
    }
    
    private static final String ADMINDOCDYC                   = "PjAdminDocDYC";
    private static final String MONITOR                       = "Monitor";
    private static final String NOTIFICACIONESYVERIFICACIONES = "PjNotificacionesYVerificaciones";
    private static final String PROCESOSDYC                   = "PjProcesosDyC";
    private static final String RETROALIMENTACIONTESOFE       = "PjRetroalimentacionTESOFE";
    private static final String VALIDADORREQUERIMIENTO        = "PjValidarReq";
    private static final String ENVIONYV                      = "PjEnvioNyV";
    private static final String COMPLEMENTARIAS               = "PjComplementarias";
    private static final String EXTRACTORDEANEXOS             = "PjExtractorDeAnexos";
    
    /**
     * Asigna las clases a los Job que realizan la funcionalidad de cada uno de los servicios.
     *
     * @param objetoProceso es el objeto que contiene la descripcion de cada uno de los procesos y que es obtenido a traves de la base de datos.
     * @param trabajo es el trabajo que se crea a partir de un servicio como lo es (PjRetroalimentacionTESOFE)
     */
    public static void asignarClaseAJobs(DyctAdminProcesosDTO objetoProceso, JobDetail trabajo) {
        
        if(objetoProceso.getNombre().equals(MONITOR)) {
            trabajo.setJobClass(JobMonitor.class);
            
        } else if(objetoProceso.getNombre().equals(NOTIFICACIONESYVERIFICACIONES)) {
            trabajo.setJobClass(JobNyV.class);
            
        } else if(objetoProceso.getNombre().equals(PROCESOSDYC)) {
            trabajo.setJobClass(JobProcesoDyC.class);
            
        } else if(objetoProceso.getNombre().equals(RETROALIMENTACIONTESOFE)) {
            trabajo.setJobClass(JobRetroTESOFE.class);
            
        } else if(objetoProceso.getNombre().equals(VALIDADORREQUERIMIENTO)) {
            trabajo.setJobClass(JobValidarReq.class);
            
        } else if(objetoProceso.getNombre().equals(ADMINDOCDYC)) {
            trabajo.setJobClass(JobAdminDocDYC.class);
            
        } else if(objetoProceso.getNombre().equals(ENVIONYV)) {
            trabajo.setJobClass(JobEnvioNyV.class);
            
        } else if(objetoProceso.getNombre().equals(COMPLEMENTARIAS)) {
            trabajo.setJobClass(JobComplementarias.class);
        
        } else if(objetoProceso.getNombre().equals(EXTRACTORDEANEXOS)) {
            trabajo.setJobClass(JobExtractorAnexos.class);
        }
    }
}
