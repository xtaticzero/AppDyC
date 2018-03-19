package mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.gob.sat.siat.pjadministradorprocesosdyc.util.constantes.Constantes;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;

import org.apache.log4j.Logger;


public class Ejecutor {
    
    private static final Logger LOGGER = Logger.getLogger(Ejecutor.class);
    
    public Ejecutor() {
        super();
    }

    public static void main(String[] args) throws IOException {
        Ejecutor objeto = new Ejecutor();
        String[] informacionJar = objeto.decidirQueJARSeEjecutara(args[0]);
        File archivoJAR = new File(informacionJar[0]);

        if (archivoJAR.exists()) {
            try {
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec(informacionJar[1]);
                InputStream stdin = proc.getInputStream();
                InputStreamReader isr = new InputStreamReader(stdin);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    LOGGER.debug(line);
                }
                int exitVal = proc.waitFor();
                LOGGER.debug("Process exitValue: " + exitVal);
            } catch (Throwable t) {
                LOGGER.error("hubo un error en la ejecucion del proceso: "+args[0]+", debido al siguiente error: "+t);
            }
            LOGGER.info("Se ejecuto satisfactoriamente el proceso: "+args[0]);

        } else {
            throw new SIATException("Error en "+args[0]+", No se encuentra el JAR: "+archivoJAR.getAbsolutePath());
        }
    }
    
    /**
     * Busca cual es el proceso que se va a ejecutar y retorna el comando a ejecutar y el jar para validar su existencia.
     *
     * @param identificador
     * @return
     */
    private String[] decidirQueJARSeEjecutara(String identificador) {
        String[] comandoAEjecutar= new String[2];
        if(identificador.equals(String.valueOf(Constantes.IDPROCESO_RETRO_NYV))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarNotificacionesYVerificaciones().substring((ParametrosEjecucion.getJarNotificacionesYVerificaciones().lastIndexOf(" ")+1), ParametrosEjecucion.getJarNotificacionesYVerificaciones().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarNotificacionesYVerificaciones();
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESODYC))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarProcesosDYC().substring((ParametrosEjecucion.getJarProcesosDYC().lastIndexOf(" ")+1), ParametrosEjecucion.getJarProcesosDYC().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarProcesosDYC();
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESORETROTESOFE))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarRetroalimentacionTESOFE().substring((ParametrosEjecucion.getJarRetroalimentacionTESOFE().lastIndexOf(" ")+1), ParametrosEjecucion.getJarRetroalimentacionTESOFE().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarRetroalimentacionTESOFE();
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESOVALIDAREQ))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarValidarReq().substring((ParametrosEjecucion.getJarValidarReq().lastIndexOf(" ")+1), ParametrosEjecucion.getJarValidarReq().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarValidarReq();
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESOADMINDOCDYC))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarAdminDocDYC().substring((ParametrosEjecucion.getJarAdminDocDYC().lastIndexOf(" ")+1), ParametrosEjecucion.getJarAdminDocDYC().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarAdminDocDYC();
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESOENVNIO_NYV))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarEnvioNyV().substring((ParametrosEjecucion.getJarEnvioNyV().lastIndexOf(" ")+1), ParametrosEjecucion.getJarEnvioNyV().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarEnvioNyV();
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESOCOMPLEMENTARIAS))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarComplementarias().substring((ParametrosEjecucion.getJarComplementarias().lastIndexOf(" ")+1), ParametrosEjecucion.getJarComplementarias().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarComplementarias();            
            
        } else if(identificador.equals(String.valueOf(Constantes.IDPROCESOEXTRACTOR))) {
            comandoAEjecutar[0]=ParametrosEjecucion.getJarExtractor().substring((ParametrosEjecucion.getJarExtractor().lastIndexOf(" ")+1), ParametrosEjecucion.getJarExtractor().length());
            comandoAEjecutar[1]=ParametrosEjecucion.getRutaJava() + " " + ParametrosEjecucion.getJarExtractor();            
        }
        return comandoAEjecutar;
    }
}


