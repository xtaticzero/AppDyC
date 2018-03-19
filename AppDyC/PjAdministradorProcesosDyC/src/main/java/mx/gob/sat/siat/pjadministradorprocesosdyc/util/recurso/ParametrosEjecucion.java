package mx.gob.sat.siat.pjadministradorprocesosdyc.util.recurso;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.apache.log4j.Logger;


public final class ParametrosEjecucion {
    private static Logger log = Logger.getLogger(ParametrosEjecucion.class);

    private static String pathProps = "";
    private static Properties props = null;
    private static InputStream fis  = null;

    private ParametrosEjecucion() {
    }

    public static String getRutaJava() {
        if (props == null) {
            return null;
        }
        return props.getProperty("rutaJAVA");
    }

    public static String getJarRetroalimentacionTESOFE() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosRetroalimentacionTESOFE")).append(" ").append(props.getProperty("retroalimentacionTESOFEJAR")).toString();
    }
    
    public static String getJarGestionPagoTESOFE() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosGestionPagosTESOFE")).append(" ").append(props.getProperty("gestionPagosTESOFEJAR")).toString();
    }
    
    public static String getJarNotificacionesYVerificaciones() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosNotificacionesYVerificaciones")).append(" ").append(props.getProperty("notificacionesYVerificacionesJAR")).toString();
    }
    
    public static String getJarProcesosDYC() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosProcesosDyC")).append(" ").append(props.getProperty("procesosDYCJAR")).toString();
    }
    
    public static String getJarValidarReq() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosValidarReq")).append(" ").append(props.getProperty("validarReqJAR")).toString();
    }
    
    public static String getJarAdminDocDYC() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosAdminDocDYC")).append(" ").append(props.getProperty("adminDocDYCJAR")).toString();
    }
    
    public static String getJarEnvioNyV() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosEnvioNyV")).append(" ").append(props.getProperty("envioNyVJAR")).toString();
    }
    
    public static String getJarComplementarias() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosComplementarias")).append(" ").append(props.getProperty("complementariasJAR")).toString();
    }
    
    public static String getJarExtractor() {
        if (props == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(props.getProperty("parametrosExtractor")).append(" ").append(props.getProperty("extractorJAR")).toString();
    }

    static {
        String os = System.getProperty("os.name");
        if ((os.contains("Windows")) || (os.contains("windows"))) {
            pathProps = "C:\\siat\\dyc\\configuraciondyc\\parametrosdyc.properties";
        } else {
            pathProps = "/siat/dyc/configuraciondyc/parametrosdyc.properties";
        }
        try {
            props = new Properties();
            fis = new FileInputStream(pathProps);
            props.load(fis);
            
        } catch (IOException e) {
            log.error("Error en inicializacion de carga de properties de compatibilidad: "+e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ioex) {
                log.error("Error al tratar de realizar el cierre de input stream: " + ioex.getMessage());
            }
        }
    }
}
