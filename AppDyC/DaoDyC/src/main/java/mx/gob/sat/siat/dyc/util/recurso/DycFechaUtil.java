/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.util.recurso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author GAER8674
 */
public final class DycFechaUtil {
    private DycFechaUtil(){}
    
    public static Date sumarDias(Date fechaOrigen, int cantidad){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaOrigen);
        calendar.add(Calendar.DAY_OF_YEAR, cantidad);
        
        return calendar.getTime();
    }
    
    public static Date stringToDate(String fechaOrigen, String formatoFecha){
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
        try {
            return sdf.parse(fechaOrigen);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date fechaOrigen, String formatoFecha){
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
        return sdf.format(fechaOrigen);
    }
}
