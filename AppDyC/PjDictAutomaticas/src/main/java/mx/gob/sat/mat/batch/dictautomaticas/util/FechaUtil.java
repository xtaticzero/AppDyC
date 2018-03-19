package mx.gob.sat.mat.batch.dictautomaticas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public final class FechaUtil {

    private FechaUtil() {
    }
    
    public static String getFechaHora() {
        DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formateador.format(new Date());
    }
    
    public static String getFechaHoraSinFormato() {
        DateFormat formateador = new SimpleDateFormat("ddMMyyyy_HHmmss");
        return formateador.format(new Date());
    }
    
    public static String getFecha() {
        DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(new Date());
    }
    
    public static String getHora() {
        DateFormat formateador = new SimpleDateFormat("HH:mm:ss");
        return formateador.format(new Date());
    }
}
